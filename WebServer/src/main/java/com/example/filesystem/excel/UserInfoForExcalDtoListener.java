package com.example.filesystem.excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.example.filesystem.pojo.UserInfoForExcal;

import java.util.ArrayList;
import java.util.List;

public class UserInfoForExcalDtoListener extends AnalysisEventListener<UserInfoForExcal> {
     public static List<UserInfoForExcal> list = new ArrayList<>();
    @Override
    public void invoke(UserInfoForExcal excelStudentDTO, AnalysisContext analysisContext) {
        list.add(excelStudentDTO);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        System.out.println("读取完毕");
    }
    public    List<UserInfoForExcal> get (){
        return this.list;
    }
}