package com.hk.complier.hcomplier.service;

import com.hk.complier.lexer.lexicalnalyzer.LexicalAnalyzer;
import org.springframework.stereotype.Service;

/**
 * @author : HK意境
 * @ClassName : SourceService
 * @date : 2022/3/14 15:04
 * @description :
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
@Service
public class SourceService {

    public LexicalAnalyzer lexicalAnalyzerService(String source){

        LexicalAnalyzer lexicalAnalyzer = new LexicalAnalyzer();
        lexicalAnalyzer.start(source);
        return lexicalAnalyzer ;
    }


}
