package com.hk.complier.syntax;

import com.hk.complier.common.FileReaderUtil;
import com.hk.complier.lexer.constant.Word;
import com.hk.complier.lexer.handle.AnalyzerHandler;
import com.hk.complier.lexer.lexicalnalyzer.LexicalAnalyzer;
import com.hk.complier.lexer.token.TokenPool;
import com.hk.complier.syntax.parser.ll.ast.AbstractSyntaxTree;
import com.hk.complier.syntax.parser.ll.excption.SyntaxException;
import org.xml.sax.ext.LexicalHandler;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;
import java.util.concurrent.TimeUnit;

/**
 * @author : HK意境
 * @ClassName : ArithmeticExpressionParser
 * @date : 2022/4/16 19:33
 * @description : 抽象的算数表达式的分析器
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
public abstract class ArithmeticExpressionParser {

    // 词法分析器产生的 TokenPool
    protected TokenPool tokenPool ;
    // 词法分析器
    protected LexicalAnalyzer lexer ;
    // Token 池
    protected Word currentToken ;

    // PDA 的压栈式有限状态自动机
    protected Stack<ArithmeticGrammar> pdaStack = new Stack<>();

    // 语法错误列表
    protected List<SyntaxException> syntaxExceptions = new ArrayList<>();

    // 抽象语法树
    protected AbstractSyntaxTree ast = new AbstractSyntaxTree();

    // parse 方法，解析算数表达式
    public abstract void parseArithmeticExpr() ;

    // 处理乘除，取负和括号部分
    public abstract void term(Word token) ;

    // 处理因子
    protected abstract void factor(Word token);


    public void doParse() throws IOException, InterruptedException {

        this.lexer = new LexicalAnalyzer();

        System.out.println("输入测试数据文件路径：");
        Scanner scanner = new Scanner(System.in);

        // 输入算数表达式
        String filePath = scanner.nextLine();

        StringBuilder source = FileReaderUtil.readLineToListPool(new File(filePath));

        // 执行词法分析
        AnalyzerHandler.run(source,this.lexer);

        // 设置 TokenPool
        this.tokenPool = new TokenPool(this.lexer) ;

        // 输出 token
        this.tokenPool.getWordDefinitionList().forEach((item) -> {
            System.out.println(item.getWord());
        });

        //TimeUnit.MINUTES.sleep(2);

        // 执行算数表达式语法分析
        this.parseArithmeticExpr();


    }


    public ArithmeticExpressionParser() {
    }

    public ArithmeticExpressionParser(TokenPool tokenPool) {
        this.tokenPool = tokenPool;
    }

    public Stack<ArithmeticGrammar> getPadStack() {
        return this.pdaStack;
    }

    public void setPadStack(Stack<ArithmeticGrammar> padStack) {
        this.pdaStack = padStack;
    }

    public TokenPool getTokenPool() {
        return tokenPool;
    }

    public void setTokenPool(TokenPool tokenPool) {
        this.tokenPool = tokenPool;
    }

}

