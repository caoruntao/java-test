package com.caort.mail.pojo;

/**
 * @author Caort.
 * @date 2021/11/26 下午2:38
 */
public class CommonResponse<T> extends BaseResponse{
    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
