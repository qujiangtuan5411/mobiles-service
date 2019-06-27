package com.sioo.log.bo;

/**
* @Description: 自定义日志信息表
* @Param:
* @return:
* @Author: fanghuaiming
* @Date:
*/
public class CustomLogInfo {
    
    private String description;

    private Object obj;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

}
