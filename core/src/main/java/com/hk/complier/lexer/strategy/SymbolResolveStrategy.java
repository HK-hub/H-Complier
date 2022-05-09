package com.hk.complier.lexer.strategy;

import com.hk.complier.lexer.constant.InitialStatus;
import com.hk.complier.lexer.constant.Word;
import com.hk.complier.lexer.constant.WordDefinition;
import com.hk.complier.lexer.constant.WordMappingPool;
import com.hk.complier.lexer.lexicalnalyzer.LexicalAnalyzer;

/**
 * @author : HK意境
 * @ClassName : SymbolResolveStrategy
 * @date : 2022/3/14 10:30
 * @description : 运算符界符等符号
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
public class SymbolResolveStrategy extends ResolveStrategy{


    @Override
    public Object resolveWord(int status, LexicalAnalyzer analyzer) {

        // 运算符界符
        if(status == InitialStatus.Operator_Status.statusCode || status == InitialStatus.Delimiter_Status.statusCode){

            int index = analyzer.getCurrentCursor();
            int preIndex = index ;
            String source = analyzer.getSource();
            char preChar = source.charAt(preIndex);

            // 当前字符是界符
            if(WordMappingPool.isDelimiter(""+preChar)){
                // 界符
                // 获取token
                Integer tokenValue = WordMappingPool.getTokenValue("" + preChar);
                Word word = new Word("" + preChar, "" + preChar, tokenValue);
                word.setWeakClass("_Delimiter_");
                analyzer.getWordPool().getWordDefinitionList()
                        .add(new WordDefinition(word));

                // 同步
                analyzer.setCurrentCursor(index);
                return word+"";
            }



            String substring = source.substring(preIndex, index+1);
            // 当前字符已经不是界符拉，必须是运算符
            // 获取下一个符号进行判断
            if(index +1 < source.length()){
                ++index;
                char currentChar = source.charAt(index);
                // 运算符
                while(WordMappingPool.isOperator(""+currentChar)){
                    ++index ;
                    currentChar = source.charAt(index);
                }
                substring = source.substring(preIndex, index);
            }

            Word word = new Word(substring, substring, WordMappingPool.getTokenValue(substring));
            analyzer.getWordPool().getWordDefinitionList().add(new WordDefinition(word));

            // 同步
            analyzer.setCurrentCursor(index);

            return substring;
        }

        return null ;
    }
}
