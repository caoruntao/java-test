package com.caort.netty.common.keepalive;

import com.caort.netty.common.OperationResult;
import lombok.Data;

@Data
public class KeepaliveOperationResult extends OperationResult {

    private final long time;

}
