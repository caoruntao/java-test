package com.caort.coupon.template.api.beans.rules;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author caort
 * @date 2022/8/9 09:18
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Discount {
    // 满减 - 减掉的钱数
    // 折扣 - 90 = 9折,  95=95折
    private Long quota;

    // 最低达到多少消费才能用
    private Long threshold;
}
