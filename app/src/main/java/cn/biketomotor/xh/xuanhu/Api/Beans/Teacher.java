package cn.biketomotor.xh.xuanhu.Api.Beans;

public class Teacher {
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
