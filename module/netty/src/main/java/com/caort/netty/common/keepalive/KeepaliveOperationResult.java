package com.caort.netty.common.keepalive;

import com.caort.netty.common.OperationResult;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class KeepaliveOperationResult extends OperationResult {

    private final long time;

}
