package com.hk.complier.lexer.token;

import com.hk.complier.lexer.constant.Word;
import com.hk.complier.lexer.constant.WordDefinition;
import com.hk.complier.lexer.lexicalnalyzer.LexicalAnalyzer;

import java.util.List;

/**
 * @author : HK意境
 * @ClassName : TokenPool
 * @date : 2022/4/16 19:17
 * @description : token 池子
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
public class TokenPool {


    private Word currentToken ;

    private int cursor = 0 ;

    private List<WordDefinition> wordDefinitionList;

    public TokenPool(){

    }



    public TokenPool(List<WordDefinition> wordDefinitionList) {
        this.wordDefinitionList = wordDefinitionList;
    }

    public TokenPool(LexicalAnalyzer lexicalAnalyzer){
        this(lexicalAnalyzer.getWordPool().getWordDefinitionList());
    }

    // 是否还有下一个 token
    public Boolean hasNextToken(){

        // 如果说当前指针没有越界则可以获取元素
        return cursor >= 0 && cursor < wordDefinitionList.size() ;

    }

    // 获取下一个 token
    public Word getNextToken(){
        if (!this.hasNextToken()){
            return null ;
        }
        Word token = this.wordDefinitionList.get(cursor).getWord();
        this.cursor++;
        this.currentToken = token ;
        return token ;
    }

    // 获取下一个token 但是 index 不增加
    public Word getNextWordDecrease(){
        Word word = this.wordDefinitionList.get(cursor + 1).getWord();
        //this.currentToken = word;
        return word ;
    }

    // 获取指定位置的 token
    public Word getTheIndexToken(int index){
        if(index < 0 || index >= this.wordDefinitionList.size()){
            return null ;
        }
        return this.wordDefinitionList.get(index).getWord();
    }

    // 回退 cursor
    public boolean decreaseCursor(){
        --this.cursor ;
        return cursor < 0 ;
    }

    // 回退指定格子
    public boolean decreaseCursorBySteps(int steps){
        int old = this.cursor ;
        this.cursor -= steps;
        if(cursor < 0){
            this.cursor = 0 ;
            return false;
        }
        return old-cursor == steps ;
    }


    public int getCursor() {
        return cursor;
    }

    public void setCursor(int cursor) {
        this.cursor = cursor;
    }

    public List<WordDefinition> getWordDefinitionList() {
        return wordDefinitionList;
    }

    public void setWordDefinitionList(List<WordDefinition> wordDefinitionList) {
        this.wordDefinitionList = wordDefinitionList;
    }

    public Word getCurrentToken() {
        return currentToken;
    }

    public void setCurrentToken(Word currentToken) {
        this.currentToken = currentToken;
    }
}
