package com.caort.netty.common.auth;

import com.caort.netty.common.OperationResult;
import lombok.Data;

@Data
public class AuthOperationResult extends OperationResult {

    private final boolean passAuth;

}
