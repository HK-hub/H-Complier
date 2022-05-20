package com.hk.compiler.test;

import com.hk.complier.common.FileReaderUtil;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

/**
 * @author : HK意境
 * @ClassName : GrammarTest
 * @date : 2022/5/11 14:15
 * @description :
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
public class GrammarTest {

    @Test
    public void testGrammar() throws IOException {

        // 按行读取文件
        String filePath = "F:\\JavaCode\\H-Complier\\core\\src\\test\\resources\\h-grammar_back.txt";
        List<String> lines = FileReaderUtil.readFileByLines(filePath);

        for (String line : lines) {
            String[] split = line.split("->");
            System.out.println(split[0]+"\t"+split[1]);
        }


    }


}
