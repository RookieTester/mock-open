package com.mock.util.page;

/**
 * Created by Administrator on 2017/1/11.
 */
public class NodeTree {

    //节点ID
    private Integer id;
    //父节点ID
    private Integer pId;
    //节点显示名称
    private String name;
    //节点属性
    private String code;
    //父节点属性
    private String pCode;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getpId() {
        return pId;
    }

    public void setpId(Integer pId) {
        this.pId = pId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getpCode() {
        return pCode;
    }

    public void setpCode(String pCode) {
        this.pCode = pCode;
    }
    
}