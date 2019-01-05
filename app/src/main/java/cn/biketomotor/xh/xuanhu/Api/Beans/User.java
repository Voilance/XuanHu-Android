package cn.biketomotor.xh.xuanhu.Api.Beans;

import java.io.Serializable;
import java.util.Date;

/**
 * 表示用户的模型类
 */
public class User implements Serializable {
    public int id;
    public String email;
    public String name;
    public Date created_at;
    public Date updated_at;
    public String avatar_url;
    public Integer teacher_id;
    public String description;
}
