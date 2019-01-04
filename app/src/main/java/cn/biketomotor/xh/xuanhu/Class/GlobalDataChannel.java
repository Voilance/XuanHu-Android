package cn.biketomotor.xh.xuanhu.Class;

import java.util.List;

import cn.biketomotor.xh.xuanhu.Api.Beans.Comment;
//这个类主要是用来传递数据的
//如果要通过Intent来传递对象
//就要让对象实现serializable接口
//一开始我没想清楚，所以就使用了全局变量
//其实这个类可以去掉
//然后用Intent来传递数据
public class GlobalDataChannel {
    //嵌套评论过多时，要跳转到另一个活动查看评论
    //这时就要把 要查看的嵌套评论 的父评论传给MoreCommentsActivity
    public static Comment targetCommentItem;

}
