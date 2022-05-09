package com.hk.complier.lexer.lexicalnalyzer;

import com.hk.complier.lexer.constant.Word;
import com.hk.complier.lexer.constant.WordDefinition;
import com.hk.complier.lexer.handle.AnalyzerHandler;
import com.hk.complier.lexer.token.TokenPool;

import java.io.IOException;

/**
 * @author : HK意境
 * @ClassName : Client
 * @date : 2022/3/22 11:48
 * @description : 词法分析器的本地测试客户端
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
public class Client {

    public static void main(String[] args) throws IOException {


        LexicalAnalyzer lexer = new LexicalAnalyzer();
        String file = "F:\\JavaCode\\H-Complier\\core\\src\\main\\resources\\data.c";
        AnalyzerHandler.run(file,lexer);

        // 获取 token 单词
        TokenPool tokenPool = new TokenPool(lexer);

        while(tokenPool.hasNextToken()){
            Word token = tokenPool.getNextToken();
            System.out.println(token.getValue()+"\t\t\t" + token.getWeakClass()  + "\t\t\t" + token.getToken());
        }

        //System.out.println(lexer.getWordText());

    }






}
