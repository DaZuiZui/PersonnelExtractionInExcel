package com.example.filesystem.pojo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;

import java.io.Serializable;

public class UserInfoForExcal implements Serializable {

    @ExcelProperty("name")
    private String name;


    @Override
    public String toString() {
        return "UserInfoForExcal{" +
                "name='" + name + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UserInfoForExcal() {
    }

    public UserInfoForExcal(String name) {
        this.name = name;
    }
}
