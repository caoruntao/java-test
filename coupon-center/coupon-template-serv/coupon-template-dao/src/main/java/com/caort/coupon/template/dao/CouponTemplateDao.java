package com.caort.coupon.template.dao;

import com.caort.coupon.template.dao.entity.CouponTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author caort
 * @date 2022/8/9 10:26
 */
public interface CouponTemplateDao extends JpaRepository<CouponTemplate, Long> {
    // 根据Shop ID查询出所有券模板
    List<CouponTemplate> findByShopId(Long shopId);

    // IN查询 + 分页支持的语法
    Page<CouponTemplate> findByIdIn(List<Long> idList, Pageable pageable);

    // 根据shop ID + 可用状态查询店铺有多少券模板
    Integer countByShopIdAndAvailable(Long shopId, Boolean available);

    // 将优惠券设置为不可用
    @Modifying
    @Query("UPDATE CouponTemplate c SET c.available = 0 WHERE c.id = :id")
    int makeCouponUnavailable(@Param("id") Long id);

}
