package com.hk.complier.lexer.status;

/**
 * @author : HK意境
 * @ClassName : CommentStatus
 * @date : 2022/3/13 16:20
 * @description :
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
public class CommentStatus {

    public int row ;
    public int column ;

    public CommentStatus(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public CommentStatus() {
    }
}
