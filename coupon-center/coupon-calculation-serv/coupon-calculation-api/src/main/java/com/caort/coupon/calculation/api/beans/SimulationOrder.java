package com.caort.coupon.calculation.api.beans;

import com.caort.coupon.template.api.beans.CouponInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author caort
 * @date 2022/8/18 13:29
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SimulationOrder {
    @NotEmpty
    private List<ProductInfo> productList;

    @NotEmpty
    private List<Long> couponIdList;

    private List<CouponInfo> couponInfoList;

    @NotNull
    private Long userId;
}
