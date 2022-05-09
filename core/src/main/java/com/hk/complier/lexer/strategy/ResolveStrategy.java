package com.hk.complier.lexer.strategy;

import com.hk.complier.lexer.lexicalnalyzer.LexicalAnalyzer;

/**
 * @author : HK意境
 * @ClassName : ResolveStrategy
 * @date : 2022/3/13 20:33
 * @description : 不同状态单词的解析策略
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
public abstract class ResolveStrategy {

    // 解析方法
    public abstract Object resolveWord(int status, LexicalAnalyzer analyzer);




}
