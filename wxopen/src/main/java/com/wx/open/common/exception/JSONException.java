package com.wx.open.common.exception;

/**
 * <pre>
 *  JSON异常
 * </pre>
 *
 * @author jiejie.liao
 * @create 2014.10.24
 * @modify
 * @since JDK1.6
 */
public class JSONException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public JSONException(){
        super();
    }

    public JSONException(String message){
        super(message);
    }

    public JSONException(String message, Throwable cause){
        super(message, cause);
    }
}
