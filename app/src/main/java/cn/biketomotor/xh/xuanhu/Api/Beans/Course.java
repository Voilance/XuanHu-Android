package cn.biketomotor.xh.xuanhu.Api.Beans;

import android.util.Log;

import java.util.Date;
import java.util.List;

public class Course {
    public int id;
    public String title;
    public Date created_at;
    public Date updated_at;
    public int department_id;
    public String intro;
    public String course_type;
    public List<Teacher> teachers;
    public Department department;
    public String getNameOfTeachers(){
        if(teachers.isEmpty())return "暂无信息";
        String tmp = teachers.toString();
        return tmp.substring(tmp.indexOf('[') + 1, tmp.lastIndexOf(']'));
    }
}
