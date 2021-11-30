package com.caort.netty.dispatch;

import com.caort.netty.common.ResponseMessage;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Caort.
 * @date 2021/7/2 上午9:10
 */
public class DispatchCenter {
    private static final InternalLogger logger = InternalLoggerFactory.getInstance(DispatchCenter.class);

    private Map<Long, OperationFuture> dispatchCenter = new ConcurrentHashMap<>(16);

    public void add(Long streamId, OperationFuture future) {
        logger.info("DispatchCenter add streamId[{}]", streamId);
        dispatchCenter.put(streamId, future);
    }

    public void result(Long streamId, ResponseMessage responseMessage) {
        OperationFuture operationFuture = dispatchCenter.get(streamId);
        if (operationFuture != null) {
            operationFuture.setSuccess(responseMessage);
            dispatchCenter.remove(streamId);
            logger.info("DispatchCenter remove streamId[{}]", streamId);
        }
    }
}
