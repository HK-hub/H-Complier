package com.hk.complier.lexer.strategy;

import com.hk.complier.lexer.constant.*;
import com.hk.complier.lexer.handle.StringStreamResolver;
import com.hk.complier.lexer.lexicalnalyzer.LexicalAnalyzer;

/**
 * @author : HK意境
 * @ClassName : IdentifierResolveStrategy
 * @date : 2022/3/13 20:36
 * @description :
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
public class IdentifierResolveStrategy extends ResolveStrategy{


    // 传入进来的第一个字符就是字母或者下划线了
    @Override
    public Object resolveWord(int status , LexicalAnalyzer analyzer) {

        if(status == InitialStatus.Char_Status.statusCode){
            // 符合状态
            int index = analyzer.getCurrentCursor();
            int preIndex = index ;
            String source = analyzer.getSource();

            // 判断是否还有元素
            if(StringStreamResolver.hashNextElement(source,index)){
                // 获取下一个字符
                ++index ;
                analyzer.cursorIncr();
                char currentChar = source.charAt(index);

                // 循环获取下一个字符
                while((Character.isLetterOrDigit(currentChar) || currentChar == '_') && index < source.length() -1){
                    // 如果当前字符是 字母，数字，下划线就继续, 并且数组下标不越界
                    // 如果当前字符为字母或者数字或下划线时,当前字符下标+1,否则将一直读到最后一个单词
                    ++index ;
                    analyzer.cursorIncr();
                    currentChar = source.charAt(index);
                }
            }
            // 同步光标
            analyzer.setCurrentCursor(index);

            // 提取标识符单词 word
            String word = source.substring(preIndex, index);
            // 判断是否是关键字
            if(WordMappingPool.isKeyWord(word)){
                // 是关键字
                Word key = new Word(word,word);
                key.setWeakClass(WordStatus.KEY);
                key.setToken(WordMappingPool.getTokenValue(word));

                // 将关键字加入 wordPool
                WordPool wordPool = analyzer.getWordPool();
                wordPool.getWordDefinitionList().add(new WordDefinition(key));
            }else {
                // 不是关键字,是标识符
                Word id = new Word(word, word);
                id.setWeakClass(WordStatus.ID);
                id.setToken(WordMappingPool.getTokenValue("_Identifier_"));

                // 将标识符加入 wordPool
                WordPool wordPool = analyzer.getWordPool();
                wordPool.getWordDefinitionList().add(new WordDefinition(id));
            }

            return ""+word ;
        }



        return null;
    }
}
