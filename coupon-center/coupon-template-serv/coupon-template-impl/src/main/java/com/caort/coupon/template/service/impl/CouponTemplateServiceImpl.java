package com.caort.coupon.template.service.impl;

import com.caort.coupon.template.api.beans.CouponTemplateInfo;
import com.caort.coupon.template.api.beans.PagedCouponTemplateInfo;
import com.caort.coupon.template.api.beans.TemplateSearchParams;
import com.caort.coupon.template.api.enums.CouponType;
import com.caort.coupon.template.converter.CouponTemplateConverter;
import com.caort.coupon.template.dao.CouponTemplateDao;
import com.caort.coupon.template.dao.entity.CouponTemplate;
import com.caort.coupon.template.service.CouponTemplateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author caort
 * @date 2022/8/17 16:47
 */
@Service
@Slf4j
public class CouponTemplateServiceImpl implements CouponTemplateService {
    @Autowired
    private CouponTemplateDao couponTemplateDao;

    @Override
    @Transactional
    public CouponTemplateInfo createTemplate(CouponTemplateInfo request) {
        // 单个门店最多可以创建100张优惠券模板
        if (request.getShopId() != null) {
            Integer count = couponTemplateDao.countByShopIdAndAvailable(request.getShopId(), true);
            if (count >= 100) {
                log.error("the totals of coupon template exceeds maximum number");
                throw new UnsupportedOperationException("exceeded the maximum of coupon templates that you can create");
            }
        }

        CouponTemplate entity = CouponTemplateConverter.dto2Entity(request);
        couponTemplateDao.save(entity);
        return CouponTemplateConverter.entity2Dto(entity);
    }

    @Override
    @Transactional
    public CouponTemplateInfo cloneTemplate(Long templateId) {
        CouponTemplate couponTemplate = couponTemplateDao.findById(templateId)
                .orElseThrow(() -> new IllegalArgumentException("invalid template ID"));

        CouponTemplate clonedCouponTemplate = CouponTemplate.builder()
                .name(couponTemplate.getName())
                .description(couponTemplate.getDescription())
                .category(couponTemplate.getCategory())
                .shopId(couponTemplate.getShopId())
                .rule(couponTemplate.getRule())
                .available(true)
                .build();
        couponTemplateDao.save(clonedCouponTemplate);

        return CouponTemplateConverter.entity2Dto(clonedCouponTemplate);
    }

    @Override
    public PagedCouponTemplateInfo search(TemplateSearchParams request) {
        CouponTemplate root = CouponTemplate.builder()
                .name(request.getName())
                .category(CouponType.convert(request.getType()))
                .shopId(request.getShopId())
                .available(request.getAvailable())
                .build();

        Page<CouponTemplate> foundPage = couponTemplateDao.findAll(Example.of(root),
                PageRequest.of(request.getPage(), request.getPageSize()));
        List<CouponTemplate> couponTemplateList = foundPage.getContent();

        return PagedCouponTemplateInfo.builder()
                .templates(couponTemplateList.stream()
                        .map(CouponTemplateConverter::entity2Dto)
                        .collect(Collectors.toList()))
                .page(foundPage.getNumber())
                .total(foundPage.getTotalElements())
                .build();
    }

    @Override
    public CouponTemplateInfo loadTemplateInfo(Long id) {
        CouponTemplate foundEntity = couponTemplateDao.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("invalid template ID"));
        return CouponTemplateConverter.entity2Dto(foundEntity);
    }

    @Override
    public void deleteTemplate(Long id) {
        int rows = couponTemplateDao.makeCouponUnavailable(id);
        Assert.isTrue(rows != 0, "Template Not Found: " + id);
    }

    @Override
    public Map<Long, CouponTemplateInfo> getTemplateInfoMap(Collection<Long> ids) {
        List<CouponTemplate> couponTemplateList = couponTemplateDao.findAllById(ids);
        return couponTemplateList.stream()
                .collect(Collectors.toMap(CouponTemplate::getId, CouponTemplateConverter::entity2Dto));
    }
}
