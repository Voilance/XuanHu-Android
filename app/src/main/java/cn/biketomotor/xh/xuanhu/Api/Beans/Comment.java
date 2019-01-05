package cn.biketomotor.xh.xuanhu.Api.Beans;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 表示评论的模型类
 */
public class Comment implements Serializable {
    public int id;
    public String content;
    public Date created_at;
    public Date updated_at;
    public int voteUp;
    public int voteDown;
    public User user;
    public Course course;
    public int voteValue;
    public List<Comment> nestedComment;
    public Comment parent;
    private boolean hasUpdatedParent = false;

    /**
     * 将嵌套评论的父评论设为当前评论
     */
    private void updateParents(){
        if(hasUpdatedParent)return;
        hasUpdatedParent = true;
        if(this.nestedComment == null)return;
        for(Comment child : this.nestedComment){
            child.parent = this;
            child.updateParents();
        }
    }

    /**
     * 获取当前评论有多少级父评论
     * @return 父评论的数量
     */
    public int getParentCommentCount(){
        updateParents();
        int cnt = 0;
        Comment obj = parent;
        while(obj != null){
            cnt++;
            obj = obj.parent;
        }
        return cnt;
    }

    /**
     * 给当前评论加入子评论
     * @param item 要加入的评论
     */
    public void addReply(Comment item){
        item.parent = this;
        nestedComment.add(item);
    }

    /**
     * 获取评论创建时间的字符串
     * @return 表示创建时间的字符串
     */
    public String getCreateAtAsString(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(created_at);
    }
}
