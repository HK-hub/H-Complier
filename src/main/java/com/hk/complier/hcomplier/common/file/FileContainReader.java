package com.hk.complier.hcomplier.common.file;

import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * @author : HK意境
 * @ClassName : FileContainReader
 * @date : 2022/3/7 1:02
 * @description : 文件内容读取器
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
public class FileContainReader {

    public String readFileContentToLines(MultipartFile file) throws IOException {

        StringBuilder sb = new StringBuilder();
        //起手转成字符流
        InputStreamReader isReader = new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8);
        BufferedReader br = new BufferedReader(isReader);
        //循环逐行读取
        while (br.ready()) {

            // 需要对每一行进行词法分析
            String line = br.readLine();
            // 返回字符串
            sb.append(line).append('\n');
        }
        //关闭流，讲究
        br.close();



        return sb.toString();
    }


}
