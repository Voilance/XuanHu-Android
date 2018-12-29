package cn.biketomotor.xh.xuanhu.Api;

import java.util.ArrayList;
import java.util.List;

import cn.biketomotor.xh.xuanhu.Api.Beans.Comment;
import cn.biketomotor.xh.xuanhu.Api.Beans.Course;
import cn.biketomotor.xh.xuanhu.Api.Beans.Teacher;
import okhttp3.HttpUrl;

import static cn.biketomotor.xh.xuanhu.Api.Constants.COMMENT_LIST_ADAPTER;
import static cn.biketomotor.xh.xuanhu.Api.Constants.COURSE_LIST_ADAPTER;
import static cn.biketomotor.xh.xuanhu.Api.Constants.HOST;
import static cn.biketomotor.xh.xuanhu.Api.Constants.PROTOCOL;

public enum SearchApi {
    INSTANCE;
    public static class SearchReq{
        String keyword;
        public SearchReq(String keyword){
            this.keyword = keyword;
        }
    }

//    public static class SearchResp{
//        List<Course> courses;
//    }

    public static class CourseSearched{
        public int id;
        public String title;
        public String intro;
        public String course_type;
        public List<String> teachers;
        public String department;

        public String getNameOfTeachers(){
            if(teachers.isEmpty())return "暂无信息";
            String tmp = teachers.toString();
            return tmp.substring(tmp.indexOf('[') + 1, tmp.lastIndexOf(']'));
        }
    }

    private static final String SEARCH_PATH = "/api/search";
    public Result<List<CourseSearched>> searchCourse(String courseName){
        HttpUrl path = new HttpUrl.Builder().scheme(PROTOCOL).host(HOST).encodedPath(SEARCH_PATH).build();
        GeneralizedClient<SearchReq, List<CourseSearched>> client = new GeneralizedClient<>();
        client.set(SearchReq.class, COURSE_LIST_ADAPTER, path);
        SearchReq si = new SearchReq(courseName);
        return client.post(si);
    }

}
