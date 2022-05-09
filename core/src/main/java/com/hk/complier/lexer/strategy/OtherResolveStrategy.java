package com.hk.complier.lexer.strategy;

import com.hk.complier.lexer.constant.InitialStatus;
import com.hk.complier.lexer.exception.WordAnalyzerException;
import com.hk.complier.lexer.lexicalnalyzer.LexicalAnalyzer;

/**
 * @author : HK意境
 * @ClassName : OtherResolveStrategy
 * @date : 2022/3/14 14:01
 * @description :
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
public class OtherResolveStrategy extends ResolveStrategy{
    @Override
    public Object resolveWord(int status, LexicalAnalyzer analyzer) {

        if(status == InitialStatus.Other_Status.statusCode){

            // 其他字符，直接异常
            analyzer.getWordAnalyzerExceptionList().add(
                    new WordAnalyzerException(analyzer.getCurrentCursor(),"Symbol invalid error: An invalid symbol occurs instead of a defined symbol!"));
        }

        return null;
    }
}
