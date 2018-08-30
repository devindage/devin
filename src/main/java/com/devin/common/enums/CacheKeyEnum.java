package com.devin.common.enums;

public enum CacheKeyEnum {
    SYS_USER("sys_user", "用户表", "sysUserServiceImpl","load");

    private String key;
    private String tran;
    private String initBean;
    private String method;
    private CacheKeyEnum(String key, String tran, String initBean, String method) {
        this.key = key;
        this.tran = tran;
        this.initBean = initBean;
        this.method = method;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTran() {
        return tran;
    }

    public void setTran(String tran) {
        this.tran = tran;
    }

    public String getInitBean() {
        return initBean;
    }

    public void setInitBean(String initBean) {
        this.initBean = initBean;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }
}