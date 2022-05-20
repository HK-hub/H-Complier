package com.hk.complier.common;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : HK意境
 * @ClassName : FileReaderUtil
 * @date : 2022/3/13 14:54
 * @description :
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
public class FileReaderUtil<T> {

    public static List<String> readFileByLines(String filePath) throws IOException {

        // 读取文件的一行内容
        String line = null;
        // 创建一个List集合来保存每一行读取到的内容
        List<String> list = new ArrayList<String>();
        // 读取文件
        try {
            // 获取文件输入流
            FileInputStream fis = new FileInputStream(filePath);
            // 获取文件输入流读取器
            InputStreamReader isr = new InputStreamReader(fis);
            // 获取缓冲区输入流
            BufferedReader br = new BufferedReader(isr);
            // 循环读取文件的每一行, 将每一行的内容添加到集合中
            while ((line = br.readLine()) != null) {
                list.add(line);
            }
        } finally {

        }

        return list;

    }



    public List readLineToListPool(InputStream is, List<Integer> list) throws IOException {

        StringBuilder sb = new StringBuilder();
        //起手转成字符流
        InputStreamReader isReader = new InputStreamReader(is, StandardCharsets.UTF_8);
        BufferedReader br = new BufferedReader(isReader);
        //循环逐行读取
        while (br.ready()) {
            //sb.append(br.readLine()).append('\n');
            list.add(Integer.parseInt(br.readLine()));
        }
        //关闭流，讲究
        br.close();

        return list;

    }


    public static StringBuilder readLineToListPool(File file) throws IOException {

        StringBuilder sb = new StringBuilder();
        //起手转成字符流
        InputStreamReader isReader = new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8);
        BufferedReader br = new BufferedReader(isReader);
        //循环逐行读取
        while (br.ready()) {
            sb.append(br.readLine()).append('\n');
            //list.add(Integer.parseInt(br.readLine()));
        }
        //关闭流，讲究
        br.close();

        return sb;

    }

}
