package com.example.filesystem.controller;

import com.alibaba.excel.EasyExcel;
import com.example.filesystem.excel.UserInfoForExcalDtoListener;
import com.example.filesystem.pojo.UserInfoForExcal;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

@Controller
public class FileController {

    @GetMapping("/goback")
    public String Hello(){
        return "Hi";
    }

    @GetMapping("/system/getimage")
    @ResponseBody
    public ResponseEntity<byte[]> down(@RequestParam("url") String url) throws IOException  {
        File file = new File("/Users/yangyida/Documents/project/PersonnelExtractionInExcel/WebServer/src/main/resources/files/excelFiles/" + url);

        if (!file.exists()) {
            // Handle the case where the file does not exist
            return ResponseEntity.notFound().build();
        }

        byte[] bytes;
        try (FileInputStream inputStream = new FileInputStream(file)) {
            bytes = new byte[inputStream.available()];
            inputStream.read(bytes);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getName());

        return ResponseEntity.ok().headers(headers).body(bytes);
    }

    @GetMapping("/Hi")
    public String Hi(){
        return "Hello";
    }

    @PostMapping("/Hello")
    public String Hello(@RequestParam("file") MultipartFile file, @RequestParam("numbers")Integer numbers, Model model) throws IOException {
        if (numbers == null){
            numbers = Integer.MAX_VALUE;
        }
        UserInfoForExcalDtoListener.list = new ArrayList<>();

        EasyExcel.read(file.getInputStream(), UserInfoForExcal.class, new UserInfoForExcalDtoListener()).sheet().doRead();
        List<UserInfoForExcal> list = UserInfoForExcalDtoListener.list;
        //随机抽取numbers个，如果numbers大于list.length则不处理
        Set<Integer> set = new HashSet<>();
        List<UserInfoForExcal> res = new ArrayList<>();

        //开始读取
        if (numbers < list.size()){
            int current = 0;//已经生成的数量
            while(current != numbers){
                Random random = new Random();
                int randomNumber = random.nextInt(list.size());

                if (set.add(randomNumber)){
                    current++;
                    String name = list.get(randomNumber).getName();
                    res.add(new UserInfoForExcal(name));
                }
            }
        }else{
            res = list;
        }


        // Specify the base directory where you want to save the files
        String name = UUID.randomUUID()+".xlsx";
        String baseDirectory = "/Users/yangyida/Documents/project/PersonnelExtractionInExcel/WebServer/src/main/resources/files/excelFiles/";
         //Create the directory if it doesn't exist
        File saveFilePath = new File(baseDirectory);
        if (!saveFilePath.exists()) {
            saveFilePath.mkdirs();
       }

        String filePath = baseDirectory + name;

        System.err.println(res);

        EasyExcel.write(filePath, UserInfoForExcal.class)
                .sheet("info")
                .doWrite(res);


        String downUrl = "http://114.116.223.181:8100/system/getimage?url=" + name;

        System.err.println("??");


        //封装返回内容
        model.addAttribute("list",res);
        model.addAttribute("downurl",downUrl);
        return "Res";
    }

}
