package com.hk.complier.lexer.strategy;

import com.hk.complier.lexer.constant.InitialStatus;
import com.hk.complier.lexer.constant.Word;
import com.hk.complier.lexer.constant.WordDefinition;
import com.hk.complier.lexer.exception.WordAnalyzerException;
import com.hk.complier.lexer.lexicalnalyzer.LexicalAnalyzer;

/**
 * @author : HK意境
 * @ClassName : CharacterResolveStrategy
 * @date : 2022/3/14 8:52
 * @description : 字符解析策略
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
public class CharacterResolveStrategy extends ResolveStrategy{


    // 判断是否是符合我们的状态条件
    @Override
    public Object resolveWord(int status, LexicalAnalyzer analyzer) {

        if(status == InitialStatus.SingleQuotes_Status.statusCode){
            // 单引号
            int index = analyzer.getCurrentCursor();
            int preIndex = index ;

            // 获取下一个字符
            String source = analyzer.getSource();
            char currentChar = source.charAt(index);

            while(currentChar != '\'' && index < source.length() -1){
                ++index ;
                currentChar = source.charAt(index);
            }

            // 获取字符单词
            String substring = source.substring(preIndex, index);

            try{
                if(substring.startsWith("'") && substring.endsWith("'") && substring.length() == 3){

                    // 可以成为字符
                    analyzer.getWordPool().getWordDefinitionList()
                            .add(new WordDefinition(
                                    new Word(substring,substring,"_Character_")));
                }else {
                    //字符匹配异常
                    analyzer.getWordAnalyzerExceptionList().add(
                            new WordAnalyzerException(index,"Single quotation marks do not match an exception"));

                }
            }catch(Exception e){
                e.printStackTrace();
            }

            // 同步
            ++index;
            analyzer.setCurrentCursor(index);
            return substring;
        }


        return null;
    }




}
