package com.hk.complier.core.constant;

import com.hk.complier.core.exception.WordAnalyzerException;

/**
 * @author : HK意境
 * @ClassName : WordDefinition
 * @date : 2022/3/13 14:16
 * @description :
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
public class WordDefinition {

    // 单词
    private Word word ;

    // 单词异常信息
    private WordAnalyzerException exception ;


    public WordDefinition(Word word) {
        this.word = word;
    }

    public WordDefinition() {
    }

    public WordDefinition(Word word, WordAnalyzerException exception) {
        this.word = word;
        this.exception = exception;
    }

    public Word getWord() {
        return word;
    }

    public void setWord(Word word) {
        this.word = word;
    }

    public WordAnalyzerException getException() {
        return exception;
    }

    public void setException(WordAnalyzerException exception) {
        this.exception = exception;
    }


    @Override
    public String toString() {
        return "WordDefinition{" +
                "word=" + word +
                ", exception=" + exception +
                '}';
    }
}
