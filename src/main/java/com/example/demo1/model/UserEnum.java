package com.example.demo1.model;

/**
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * <p>
 * Created by xiangjiangcheng on 2018/8/21 17:40.
 */
public enum UserEnum {
    /**
     * 用户禁用
     */
    USER_IS_CLOSE("用户禁用", 0);


    // 成员变量
    private String name;
    private int value;

    UserEnum(String name, int value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
