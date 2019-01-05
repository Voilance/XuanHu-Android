package cn.biketomotor.xh.xuanhu.Api.Beans;

import java.io.Serializable;

/**
 * 表示老师的模型类
 */
public class Teacher implements Serializable {
    public int id;
    public String name;
    public String intro;
    public int department_id;
    //返回教师名字
    @Override
    public String toString(){
        return name;
    }
}
