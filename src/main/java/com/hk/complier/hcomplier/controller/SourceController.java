package com.hk.complier.hcomplier.controller;

import com.hk.complier.hcomplier.common.res.ResponseResult;
import com.hk.complier.hcomplier.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author : HK意境
 * @ClassName : SourceController
 * @date : 2022/3/7 0:41
 * @description :
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
@CrossOrigin
@RestController
@RequestMapping(value = "/source")
public class SourceController {

    @Autowired
    private FileService fileService ;


    // 上传源程序文件返回文件内容
    @PostMapping("/upload")
    public ResponseResult<String> openSourceFile(MultipartFile file){

        String fileContent = "";

        // 获取文件内容
        try {
            fileContent = fileService.readFileContent(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ResponseResult.SUCCESS().setData(fileContent);
    }



    // 接收源程序字符串
    @PostMapping("/new")
    public ResponseResult<String> receiveCodeSourceString(@RequestBody String param){

        System.out.println(param);
        return ResponseResult.SUCCESS().setData("接受成功");
    }





}
