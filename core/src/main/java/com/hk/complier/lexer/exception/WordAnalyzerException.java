package com.hk.complier.lexer.exception;

/**
 * @author : HK意境
 * @ClassName : WordAnalyzerException
 * @date : 2022/3/13 14:21
 * @description :
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
public class WordAnalyzerException extends RuntimeException{

    // 错误出现的行列
    private int row ;
    private int column ;
    private int cursor ;
    // 出现错误的单词
    private String errorWords ;

    // 异常信息
    private String message ;

    public WordAnalyzerException() {
    }

    public WordAnalyzerException(String message){

        this.message = message ;
    }

    public WordAnalyzerException(int row, int column, int cursor, String message) {

        this.row = row;
        this.column = column;
        this.cursor = cursor;
        this.message = message;
    }

    public WordAnalyzerException(int row, int column, String message) {

        this.row = row;
        this.column = column;
        this.message = message;
    }
    public WordAnalyzerException(int cursor, String message) {

        this.cursor = cursor;
        this.message = message;
    }


    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public int getCursor() {
        return cursor;
    }

    public void setCursor(int cursor) {
        this.cursor = cursor;
    }

    public String getErrorWords() {
        return errorWords;
    }

    public void setErrorWords(String errorWords) {
        this.errorWords = errorWords;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    @Override
    public String toString() {
        return "WordAnalyzerException{" +
                "row=" + row +
                ", column=" + column +
                ", cursor=" + cursor +
                ", errorWords='" + errorWords + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}


