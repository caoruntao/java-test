package com.caort.netty.common.auth;

import com.caort.netty.common.OperationResult;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class AuthOperationResult extends OperationResult {

    private final boolean passAuth;

}
