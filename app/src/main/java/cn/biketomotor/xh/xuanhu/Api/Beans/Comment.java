package cn.biketomotor.xh.xuanhu.Api.Beans;

import java.util.Date;

public class Comment {
    public int id;
    public String content;
    public Date created_at;
    public Date updated_at;
    public int voteUp;
    public int voteDown;
    public User user;
    public Course course;
}
