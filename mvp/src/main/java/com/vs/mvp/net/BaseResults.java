package com.vs.mvp.net;

/**
 * Created by Administrator on 2017/9/6.
 */

public class BaseResults implements IModel {

    /**
     * code : 200
     * msg : ok
     */

//    private int code;
//    private String msg;
        private Response response;

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    public static class Response{
        private int response_code;
        private String response_msg;

        public int getResponse_code() {
            return response_code;
        }

        public void setResponse_code(int response_code) {
            this.response_code = response_code;
        }

        public String getResponse_msg() {
            return response_msg;
        }

        public void setResponse_msg(String response_msg) {
            this.response_msg = response_msg;
        }
    }

//    public int getCode() {
//        return code;
//    }
//
//    public void setCode(int code) {
//        this.code = code;
//    }
//
//    public String getMsg() {
//        return msg;
//    }
//
//    public void setMsg(String msg) {
//        this.msg = msg;
//    }

    @Override
    public boolean isNull() {
        return false;
    }

    @Override
    public boolean isAuthError() {
        return false;
    }

    @Override
    public boolean isBizError() {
        return false;
    }

    @Override
    public String getErrorMsg() {
        return null;
    }
}
