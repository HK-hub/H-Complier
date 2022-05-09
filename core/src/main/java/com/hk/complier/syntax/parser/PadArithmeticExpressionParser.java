package com.hk.complier.syntax.parser;

import com.hk.complier.lexer.constant.Word;
import com.hk.complier.lexer.constant.WordMappingPool;
import com.hk.complier.lexer.token.TokenPool;
import com.hk.complier.syntax.ArithmeticExpressionParser;
import com.hk.complier.syntax.ArithmeticGrammar;
import com.hk.complier.syntax.parser.ll.excption.SyntaxException;
import lombok.Data;

import java.io.IOException;

/**
 * @author : HK意境
 * @ClassName : PadArithmeticExpressionParser
 * @date : 2022/4/16 19:52
 * @description :
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
@Data
public class PadArithmeticExpressionParser extends ArithmeticExpressionParser {

    // 启动
    public static void main(String[] args) throws IOException, InterruptedException {

        PadArithmeticExpressionParser parser = new PadArithmeticExpressionParser();

        // 开始词法分析，语法分析
        parser.doParse();

        // 输出结果
        parser.syntaxExceptions.forEach(System.out::println);


    }

    // 构造函数
    public PadArithmeticExpressionParser(TokenPool tokenPool) {
        super(tokenPool) ;
        // 将开始符号压入堆栈中
        this.pdaStack.push(ArithmeticGrammar.STMT);
    }

    public PadArithmeticExpressionParser() {

    }

    /**
     * @methodName : parseArithmeticExpr
     * @author : HK意境
     * @date : 2022/4/16 20:00
     * @description : 算数表达式语法分析的 PDA 压栈式有限状态自动机方法实现
     * @Todo : 处理算数表达式
     * @params :
         * @param : null
     * @return : null
     * @throws:
     * @Bug :
     * @Modified :
     * @Version : 1.0
     */
    @Override
    public void parseArithmeticExpr() {
        // 递归下降文法分析

        this.currentToken = this.tokenPool.getNextToken();

        // 处理乘法除法取负和括号部分
        this.term(this.currentToken);

        while(true){

            // 获取下一个 token
            this.currentToken = this.tokenPool.getNextToken();


            // 判断运算类型
            if ("+".equals(this.currentToken.getValue()) || "-".equals(this.currentToken.getValue())) {
                this.currentToken = this.tokenPool.getNextToken();

                // 处理加减之后的部分
                this.term(this.currentToken);

            }else {
                // 表达式处理完毕，回退指针
                this.tokenPool.decreaseCursor();
                break;
            }
        }
    }


    //处理乘除，取负和括号部分
    @Override
    public void term(Word token) {

        this.currentToken = this.tokenPool.getNextToken();
        // 处理因子
        this.factor(this.currentToken);

        while(true){

            this.currentToken = this.tokenPool.getNextToken();

            // 判断运算类型
            if ("*".equals(this.currentToken.getValue()) || "/".equals(this.currentToken.getValue())) {

                this.currentToken = this.tokenPool.getNextToken();

                // 递归调用 factor 处理 */ 后的第二个操作数
                this.factor(this.currentToken);
            }else {
                // 回退
                this.tokenPool.decreaseCursor();
                break;
            }
        }
    }


    // //处理单个因子:括号和单目取负、单个常量、变量
    @Override
    protected void factor(Word token) {

        this.currentToken = this.tokenPool.getNextToken();

        // 处理括号
        if ("(".equals(this.currentToken.getValue())) {

            // 调用表达式分析
            this.parseArithmeticExpr();
            this.currentToken = this.tokenPool.getNextToken();

            // 判断左括号，进行语法错误处理
            if (!")".equals(this.currentToken.getValue())) {
                this.syntaxExceptions.add(new SyntaxException(this.currentToken , this.currentToken.getColIndex(),
                        "No matching closing parenthesis found"));

            }else if("-".equals(this.currentToken.getValue())){
                // 判断负号 ,处理单目取负
                this.parseArithmeticExpr();

            }else if(!WordMappingPool.isIdentifier(this.currentToken) ||
                        !WordMappingPool.isConstValue(this.currentToken)){
                // 不是标识符，或者常量
                // 语法错误
                this.syntaxExceptions.add(new SyntaxException(this.currentToken , this.currentToken.getColIndex(),
                        "Operands are not legal variables or constants"));
            }
        }
    }
}
