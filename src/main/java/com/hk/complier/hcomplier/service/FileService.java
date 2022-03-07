package com.hk.complier.hcomplier.service;

import com.hk.complier.hcomplier.common.file.FileContainReader;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;

/**
 * @author : HK意境
 * @ClassName : FileService
 * @date : 2022/3/7 9:24
 * @description :
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
@Service
public class FileService {

    public String readFileContent(MultipartFile file) throws IOException {

        FileContainReader fileContainReader = new FileContainReader();
        String content = fileContainReader.readFileContentToLines(file);

        return content ;
    }


}
