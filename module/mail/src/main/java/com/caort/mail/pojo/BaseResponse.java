package com.caort.mail.pojo;

import java.util.List;

/**
 * @author Caort
 * @date 2021/11/26 下午1:28
 */
public class BaseResponse {
    private Integer code;
    private List<String> error;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public List<String> getError() {
        return error;
    }

    public void setError(List<String> error) {
        this.error = error;
    }
}
