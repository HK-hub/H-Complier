package com.hk.complier.lexer.constant;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : HK意境
 * @ClassName : Word
 * @date : 2022/3/13 12:25
 * @description :
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
public class Word {

    // 单词名称
    private String name ;

    // 单词的值
    private String value ;

    // 单词的长度
    private int length ;

    // 单词所在位置
    private int rowIndex ;
    private int colIndex ;

    // 单词的第二类型
    private String weakClass ;

    // 单词种码
    private Integer token ;

    // 单词状态转换
    private List<WordStatus> wordStatusList = new ArrayList<>() ;


    public Word() {
    }

    public Word(String name, String value) {
        this.name = name;
        this.value = value;
        this.length = name.length();
    }

    public Word(String name, String value, String token) {
        this(name,value);
        this.token = Integer.parseInt((String) WordMappingPool.properties.get(token));
        this.weakClass = token ;
    }


    public Word(String name, String value, Integer token) {
        this(name,value);
        this.token = token;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getRowIndex() {
        return rowIndex;
    }

    public void setRowIndex(int rowIndex) {
        this.rowIndex = rowIndex;
    }

    public int getColIndex() {
        return colIndex;
    }

    public void setColIndex(int colIndex) {
        this.colIndex = colIndex;
    }

    public String getWeakClass() {
        return weakClass;
    }

    public void setWeakClass(String weakClass) {
        this.weakClass = weakClass;
    }

    public Integer getToken() {
        return token;
    }

    public void setToken(Integer token) {
        this.token = token;
    }

    public List<WordStatus> getWordStatusList() {
        return wordStatusList;
    }

    public void setWordStatusList(List<WordStatus> wordStatusList) {
        this.wordStatusList = wordStatusList;
    }

    @Override
    public String toString() {
        return "Word{" +
                "name='" + name + '\'' +
                ", value='" + value + '\'' +
                ", weakClass='" + weakClass + '\'' +
                ", token=" + token +
                '}';
    }
}
