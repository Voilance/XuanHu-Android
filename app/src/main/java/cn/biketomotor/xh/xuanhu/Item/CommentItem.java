package cn.biketomotor.xh.xuanhu.Item;

import java.util.ArrayList;
import java.util.List;

public class CommentItem {

    private int courseID;
    private String courseTitle;
    private String userName;
    private String content;
    private String createdAt;
    private int voteUp;
    private int voteDown;
    private List<CommentItem> replies = new ArrayList<>();
    //use parent count to solve the bug in course comments
    private CommentItem parent;

    public CommentItem(
            int courseID,
            String courseTitle,
            String userName,
            String content,
            String createdAt,
            int voteUp,
            int voteDown
    ) {
        this.courseID = courseID;
        this.courseTitle = courseTitle;
        this.userName = userName;
        this.content = content;
        this.createdAt = createdAt;
        this.voteUp = voteUp;
        this.voteDown = voteDown;
    }

    public int getCourseID() {
        return courseID;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public String getUserName() {
        return userName;
    }

    public String getContent() {
        return content;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public int getVoteUp() {
        return voteUp;
    }

    public int getVoteDown() {
        return voteDown;
    }

    public List<CommentItem> getReplies(){
        return replies;
    }

    public int getParentCommentCount(){
        int cnt = 0;
        CommentItem obj = parent;
        while(obj != null){
            cnt++;
            obj = obj.parent;
        }
        return cnt;
    }

    public void addReply(CommentItem item){
        item.parent = this;
        replies.add(item);
    }
}
