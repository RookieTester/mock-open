package com.mock.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/12/1.
 * 前端ajax请求，将返回类型封装为JSON结果
 *
 * @author zdm
 */

/**
 * Include.NON_NULL 属性为NULL不序列化
 * Include.Include.ALWAYS 默认
 * Include.NON_DEFAULT 属性为默认值不序列化
 * Include.NON_EMPTY 属性为空字符串或者为NULL都不序列化
 *
 * @param <T>
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseResult<T> implements Serializable {

    private static final long serialVersionUID = -2834995594953446307L;

    private boolean success;

    private T data;

    private String errorMsg;

    public BaseResult(boolean success, String errorMsg) {
        this.success = success;
        this.errorMsg = errorMsg;
    }

    public BaseResult(boolean success, T data) {
        this.success = success;
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    @Override
    public String toString() {
        return "BaseResult [success=" + success + ", data=" + data + ", errorMsg=" + errorMsg + "]";
    }
}
