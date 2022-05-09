package com.hk.complier.lexer.strategy;

import com.hk.complier.lexer.constant.*;
import com.hk.complier.lexer.exception.WordAnalyzerException;
import com.hk.complier.lexer.handle.StringStreamResolver;
import com.hk.complier.lexer.lexicalnalyzer.LexicalAnalyzer;

/**
 * @author : HK意境
 * @ClassName : NumberResoleStrategy
 * @date : 2022/3/13 20:34
 * @description :
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
public class NumberResoleStrategy extends ResolveStrategy{

    @Override
    public Object resolveWord(int status, LexicalAnalyzer analyzer) {

        if(status == InitialStatus.Number_Status.statusCode){
            // 数字情况
            int index = analyzer.getCurrentCursor();
            int preIndex = index ;
            // 数字类型
            String kind = "_Real_Number_" ;
            String source = analyzer.getSource();
            char currentChar = source.charAt(index);


            // 判断字符下标是否超过源程序长度
            if(StringStreamResolver.hashNextElement(source,index)){

                // 必须是数字
                while(Character.isDigit(currentChar) && index < source.length() -1){

                    // 如果这个数字是 0
                    if(currentChar == '0'){
                        ++index;
                        currentChar = source.charAt(index);

                        // 第一种情况：后面是小数点
                        if(currentChar == '.'){
                            ++index;
                            currentChar = source.charAt(index);
                            while(Character.isDigit(currentChar)){
                                ++index;
                                currentChar = source.charAt(index);
                                if(Character.isDigit(currentChar)){
                                    // 是数字不影响
                                }
                                else if(currentChar == 'e' || currentChar == 'E'){
                                    ++index;
                                    currentChar = source.charAt(index);
                                    if(currentChar=='-' || currentChar =='+'){
                                        ++index;
                                        currentChar = source.charAt(index);
                                        while(Character.isDigit(currentChar)){
                                            ++index;
                                            currentChar = source.charAt(index);
                                        }

                                    }else if(Character.isDigit(currentChar)){
                                        // 数字
                                        while(Character.isDigit(currentChar)){
                                            ++index;
                                            currentChar = source.charAt(index);
                                        }
                                    }


                                }else if(WordMappingPool.properties.containsKey(currentChar) || Character.isSpaceChar(currentChar)){
                                    // 小数浮点数

                                }else {
                                    // 其他字符，错误
                                    analyzer.getWordAnalyzerExceptionList()
                                            .add(
                                                    new WordAnalyzerException(index,
                                                            "NumberMatchException:error character->" + currentChar));

                                }

                            }
                        }else if(currentChar >= '0' && currentChar <= '7'){
                            // 第三种情况：后面 0-7 的数字
                            ++index;
                            currentChar = source.charAt(index);
                            while(currentChar >= '0' && currentChar <= '7'){
                                ++index;
                                currentChar = source.charAt(index);
                            }
                            // 八进制的数

                        }else if(currentChar == 'x' || currentChar == 'X'){
                            // 第二种情况：x|X
                            ++index ;
                            currentChar = source.charAt(index);
                            while(Character.isDigit(currentChar) ||
                                    (currentChar >= 'a' && currentChar <= 'f') || (currentChar >= 'A' && currentChar <= 'Z')){
                                ++index ;
                                currentChar = source.charAt(index);
                            }
                            // 十六进制的数
                        }

                    }else {
                        // 这个数字为 1-9
                        ++index ;
                        currentChar = source.charAt(index);

                        while(Character.isDigit(currentChar)){
                            ++index ;
                            currentChar = source.charAt(index);
                        }

                        if(currentChar == '.'){
                            // 小数
                            ++index ;
                            currentChar = source.charAt(index);

                            while(Character.isDigit(currentChar)){
                                ++index ;
                                currentChar = source.charAt(index);
                            }

                            // 实数
                            if(currentChar == 'e' || currentChar == 'E'){
                                ++index ;
                                currentChar = source.charAt(index);

                                if(Character.isDigit(currentChar)){
                                    ++index ;
                                    currentChar = source.charAt(index);
                                    while(Character.isDigit(currentChar)){
                                        ++index ;
                                        currentChar = source.charAt(index);
                                    }
                                }else if(currentChar == '-' || currentChar == '+'){
                                    ++index ;
                                    currentChar = source.charAt(index);
                                    while(Character.isDigit(currentChar)){
                                        ++index ;
                                        currentChar = source.charAt(index);
                                    }
                                }else{
                                    // 错误
                                    // 其他字符，错误
                                    analyzer.getWordAnalyzerExceptionList()
                                            .add(
                                                    new WordAnalyzerException(index,
                                                            "NumberMatchException:error character->" + currentChar));
                                }

                            }else if(WordMappingPool.properties.containsKey(currentChar)){

                            }else {
                                // 其他字符，错误
                                analyzer.getWordAnalyzerExceptionList()
                                        .add(
                                                new WordAnalyzerException(index,
                                                        "NumberMatchException:error character->" + currentChar));
                            }


                        }else if(currentChar == 'e' || currentChar == 'E'){

                        }else if(WordMappingPool.properties.containsKey(currentChar)
                                || Character.isSpaceChar(currentChar)){
                            // 空白界符，运算符 ——> 整数
                            kind = "_Integer_";

                        }else {
                            // 其他字符，错误
                            analyzer.getWordAnalyzerExceptionList()
                                    .add(
                                            new WordAnalyzerException(index,
                                                    "NumberMatchException:error character->" + currentChar));

                        }
                    }
                }
            }

            // 截取数字
            String number = source.substring(preIndex, index);
            Word word = new Word("number",number);
            word.setToken(WordMappingPool.getTokenValue(kind));

            // 添加单词
            analyzer.getWordPool().getWordDefinitionList().add(new WordDefinition(word));

            // 同步下标
            analyzer.setCurrentCursor(index);

            return ""+word ;

        }

        return null;
    }
}
