package com.hk.complier.lexer.constant;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : HK意境
 * @ClassName : WordPool
 * @date : 2022/3/13 12:26
 * @description :
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */

public class WordPool {

    private List<WordDefinition> wordDefinitionList = new ArrayList<>() ;

    public List<WordDefinition> getWordDefinitionList() {
        return wordDefinitionList;
    }

    public void setWordDefinitionList(List<WordDefinition> wordDefinitionList) {
        this.wordDefinitionList = wordDefinitionList;
    }

    @Override
    public String toString() {
        return "WordPool{" +
                "wordDefinitionList=" + wordDefinitionList +
                '}';
    }
}
