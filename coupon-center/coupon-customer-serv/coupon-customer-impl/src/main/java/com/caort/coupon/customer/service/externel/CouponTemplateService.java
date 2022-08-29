package com.caort.coupon.customer.service.externel;

import com.caort.coupon.template.api.beans.CouponTemplateInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author caort
 * @date 2022/8/26 13:44
 */
@Service
public class CouponTemplateService {
    @Autowired
    private RestTemplate restTemplate;

    public CouponTemplateInfo loadTemplateInfo(Long id){
        URI uri = URI.create("http://coupon-template-serv/template/getTemplate?id=" + id);
        ResponseEntity<CouponTemplateInfo> responseEntity =
                restTemplate.getForEntity(uri, CouponTemplateInfo.class);

        return responseEntity.getBody();
    }

    public Map<Long, CouponTemplateInfo> getTemplateInfoMap(Collection<Long> ids){
        String idsStr = ids.stream()
                .map(String::valueOf)
                .distinct()
                .collect(Collectors.joining(","));

        URI uri = URI.create("http://coupon-template-serv/template/getBatch?ids=" + idsStr);
        ParameterizedTypeReference<Map<Long, CouponTemplateInfo>> resultClazz = new ParameterizedTypeReference<>() {};

        RequestEntity<Void> request = RequestEntity.get(uri)
                .accept(MediaType.APPLICATION_JSON)
                .build();

        ResponseEntity<Map<Long, CouponTemplateInfo>> responseEntity = restTemplate.exchange(request, resultClazz);
        return responseEntity.getBody();
    }
}
