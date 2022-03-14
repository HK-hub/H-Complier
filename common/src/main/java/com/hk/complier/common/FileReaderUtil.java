package com.hk.complier.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
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


}
