package cn.biketomotor.xh.xuanhu.Api.Beans;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Comment {
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

    //更新评论的父评论
    private void updateParents(){
        if(hasUpdatedParent)return;
        hasUpdatedParent = true;
        if(this.nestedComment == null)return;
        for(Comment child : this.nestedComment){
            child.parent = this;
            child.updateParents();
        }
    }

    //获取评论的父评论数
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

    //添加评论的回复
    public void addReply(Comment item){
        item.parent = this;
        nestedComment.add(item);
    }

    //获取评论创建的时间，以String类型返回
    public String getCreateAtAsString(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(created_at);
    }
}
