package com.hk.complier.lexer.handle;

import com.hk.complier.lexer.lexicalnalyzer.LexicalAnalyzer;
import com.hk.complier.lexer.strategy.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : HK意境
 * @ClassName : AnalyzerHandler
 * @date : 2022/3/13 13:56
 * @description : 具体的单词的识别处理器，实现不同的算法
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
public class AnalyzerHandler {

    public static List<ResolveStrategy> handleStrategies = new ArrayList<>();


    static {

        handleStrategies.add(new IdentifierResolveStrategy());
        handleStrategies.add(new NumberResoleStrategy());
        handleStrategies.add(new CharacterResolveStrategy());
        handleStrategies.add(new StringResolveStrategy());
        handleStrategies.add(new SymbolResolveStrategy());
        handleStrategies.add(new OtherResolveStrategy());

    }


    // 使用责任链模式，执行策略
    public static Object runHandleStrategy(int statusCode,LexicalAnalyzer analyzer){

        Object res = null;
        for (ResolveStrategy handleStrategy : handleStrategies) {
            Object o = handleStrategy.resolveWord(statusCode, analyzer);
            if (o != null){
                res = o ;
                break;
            }
        }
        return res ;
    }



    // 启动词法处理器：
    public static void run(String filePath ,LexicalAnalyzer lexicalAnalyzer) throws IOException {

        String source = StringStreamResolver.preResolve(filePath, lexicalAnalyzer);
        assert source != null;
        run(new StringBuilder(source),lexicalAnalyzer);
        lexicalAnalyzer.postHandler();
    }

    public static void run(StringBuilder source , LexicalAnalyzer analyzer){

        analyzer.setSource(source.toString());

        StringStreamResolver.sourceCodeResolver(source.toString(),analyzer) ;

    }









}
