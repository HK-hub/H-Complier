package com.hk.complier.core.handle;

import com.hk.complier.core.constant.InitialStatus;
import com.hk.complier.core.constant.WordMappingPool;
import com.hk.complier.core.constant.WordPool;
import com.hk.complier.core.exception.CommentMismatchException;
import com.hk.complier.core.exception.WordAnalyzerException;
import com.hk.complier.core.lexicalnalyzer.LexicalAnalyzer;
import com.hk.complier.core.status.CommentStatus;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;

/**
 * @author : HK意境
 * @ClassName : StringStreamResolver
 * @date : 2022/3/13 13:54
 * @description : 处理依次输入的字符串流
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
public class StringStreamResolver {


    private static int Status = 0 ;

    // 判断是否还有下一个字符
    public static boolean hashNextElement(String line, int index){

        if(index >= line.length() || index < 0){
            return false ;
        }

        return true ;
    }

    // 获取下一个字符，没有就返回null
    public static String nextElement(String line, int index){

        if(hashNextElement(line,index)){

            return String.valueOf(line.charAt(index));
        }
        return null ;
    }


    // 预处理，去除注释
    public static String preResolve(String filePath, LexicalAnalyzer lexicalAnalyzer) throws IOException {

        StringBuilder sb = new StringBuilder("");
        //起手转成字符流
        InputStreamReader isReader = new InputStreamReader(new FileInputStream(filePath), StandardCharsets.UTF_8);
        BufferedReader br = new BufferedReader(isReader);

        String line = "" ;
        int counter = 0;

        preResolveComment(filePath,lexicalAnalyzer);

        // 先读取每一行，看是否包含“//” ,如果包含，则去除注释
        while(line != null){

            // 按行读取文本信息,遇到单行注释时删除注释内容
            int index = line.indexOf("//");
            if(index != -1){
                // 如果找到"//"删除注释后面的内容，留下注释前面的内容
                line = line.substring(0,index -1) ;
                // 列指针偏移
                lexicalAnalyzer.setCurrentColumnIndex(index -1);
            }

            // 将每一行使用空格分割
            sb.append(" ").append(line);

            // 读取下一行
            line = br.readLine();
            // 行指针偏移
            lexicalAnalyzer.setCurrentRowIndex(++counter);
        }

        //关闭流，讲究
        br.close();
        // 删除多行注释：注意此处的异常抛出问题
        while(sb.indexOf("/*") != -1){

            if (sb.indexOf("*/") != -1) {
                // 如果找到注释符的闭包则将注释符连同注释符之间的内容删去
                sb = new StringBuilder(sb.substring(0,sb.indexOf("/*")).concat(sb.substring(sb.indexOf("*/")+2)));

            } else {// 如果没找到注释符的闭包,则将注释符及其后内容全部删去,抛出异常程序结束
                sb.substring(sb.indexOf("/*"));
                return null ;
            }
        }

        // 删除拼接时生成的多余的空格
        String docString = sb.toString();
        docString = docString.replaceAll(" {2,}","");

        // 删除首尾空格
        docString.trim();


        System.out.println(docString);
        return docString ;
    }


    // 检测多行注释是否存在错误
    protected static void preResolveComment(String filePath, LexicalAnalyzer lexicalAnalyzer) throws FileNotFoundException {

        //起手转成字符流
        InputStreamReader isReader = new InputStreamReader(new FileInputStream(filePath), StandardCharsets.UTF_8);
        BufferedReader br = new BufferedReader(isReader);

        int counter = 0;
        List<String> stringList = br.lines().collect(Collectors.toList());
        // 处理行注释的时候不会存在异常情况，所以不需要进行指针偏移,
        // 但是在处理多行注释的时候就需要记录 /* 和 */ 是否是匹配的了，我们可以使用一个 stack<String> 来记录他们
        // 当检测到 /* 时就入栈，当检测到 */ 时就出栈，如果最后数量不匹配
        Stack<CommentStatus> notes = new Stack<CommentStatus>();

        for (String s : stringList) {
            int start = s.indexOf("/*");
            int end = s.indexOf("*/");

            if(start != -1){
                notes.push(new CommentStatus(counter,start));
            }

            // 最后一个字符
            if(end != -1 && end+1 == s.length() -1){
                if(notes.empty()){
                    notes.push(new CommentStatus(counter,end));
                }else{
                    notes.pop() ;
                }
            }
            // 记录偏移量
            lexicalAnalyzer.setCurrentRowIndex(++counter);

        }

        // 记录异常
        if(!notes.empty()){
            for (CommentStatus note : notes) {
                lexicalAnalyzer.getWordAnalyzerExceptionList().add(new CommentMismatchException(note));
            }
        }
    }


    // 源程序源代码解析器: 词法分析器
    public static WordPool sourceCodeResolver(String source,LexicalAnalyzer lexicalAnalyzer){

        // lexicalAnalyzer.getCurrentCursor(); 当前光标所在字符串流中的位置

        // 当前字符输入后所处的状态：空白:0    字符:1 ,数字:2,运算符:3,界符:4,其他:5
        // 因为已经把注释去除掉了，所以不需要考虑第一个/ 为注释的可能性
        int counter = 0 ;
        // 源码数组
        char[] codeArray = source.toCharArray();

        while(lexicalAnalyzer.getCurrentCursor() < source.length() && counter < source.length()){
            // 循环对每一个输入的字符进行处理
            char currentChar = source.charAt(counter);
            int preIndex = lexicalAnalyzer.getCurrentCursor();
            // 判断这个字符是属于什么类型
            int statusByChar = getStatusByChar(currentChar,lexicalAnalyzer);
            // 判断是否出现异常，并且记录
            hasExceptionAndRecord(statusByChar, lexicalAnalyzer, currentChar);

            if(statusByChar == InitialStatus.Space_Status.getStatusCode()){
                // 空白，继续下一个
                // 下标+1
                lexicalAnalyzer.setCurrentCursor(lexicalAnalyzer.getCurrentCursor()+1);
                // 计数器+1
                counter++ ;
                currentChar = codeArray[counter] ;
                continue;
            }else {
                lexicalAnalyzer.setCurrentCursor(counter);
                // 对于每一种状态执行不同的策略
                AnalyzerHandler.runHandleStrategy(statusByChar,lexicalAnalyzer);
            }
            // 同步
            if(preIndex == lexicalAnalyzer.getCurrentCursor()){
                lexicalAnalyzer.setCurrentCursor(++preIndex);
            }
            counter = lexicalAnalyzer.getCurrentCursor();
        }


        return new WordPool();
    }


    // 根据字符获取下一个应该设置的状态
    public static int getStatusByChar(char currentChar, LexicalAnalyzer analyzer){

        int status = 0;

        if(Character.isSpaceChar(currentChar)){
            // 空白字符
            status = InitialStatus.Space_Status.statusCode;

        }else if (Character.isLetter(currentChar) || currentChar == '_'){
            // 字符或下划线
            status = InitialStatus.Char_Status.statusCode;
        }else if(Character.isDigit(currentChar)){
            // 数字
            status = InitialStatus.Number_Status.statusCode;
        }else if(currentChar == '\''){
            // 单引号
            status = InitialStatus.SingleQuotes_Status.statusCode;

        }else if(currentChar == '"'){
            // 双引号
            status = InitialStatus.DoubleQuotes_Status.statusCode;

        }else if(WordMappingPool.isOperator(""+currentChar)){
            // 运算符
            status = InitialStatus.Operator_Status.statusCode;

        }else if(WordMappingPool.isDelimiter(""+currentChar)){
            // 界符
            status = InitialStatus.Delimiter_Status.statusCode;
        }else if(WordMappingPool.specialList.contains(""+currentChar)){
            // 特殊字符:"`～·。！@@#$…×（）『』“”：；，《》？、￥（）——【】‘；：’。，、？]|\n|\r|\t";
            status = InitialStatus.Other_Status.statusCode;
        }else {
            // 错误，出现异常，需要记录
            status = -1;
        }

        return status;
    }

    // 异常状态监测
    public static boolean hasExceptionAndRecord(Integer status,LexicalAnalyzer lexicalAnalyzer,char currentChar){

        if(status == -1){
            // 出现异常，需要记录
            WordAnalyzerException ex = new WordAnalyzerException(lexicalAnalyzer.getCurrentCursor(), "CharacterStatusChooseException");
            ex.setErrorWords(""+currentChar);
            lexicalAnalyzer.getWordAnalyzerExceptionList().add(ex);
            return true ;
        }
        return false ;
    }



}
