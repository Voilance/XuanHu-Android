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

/**
 * 提供搜索相关的功能接口
 */
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

        /**
         * 获取老师姓名列表
         * @return 老师姓名列表的字符串
         */
        public String getNameOfTeachers(){
            if(teachers.isEmpty())return "暂无信息";
            String tmp = teachers.toString();
            return tmp.substring(tmp.indexOf('[') + 1, tmp.lastIndexOf(']'));
        }
    }

    private static final String SEARCH_PATH = "/api/search";

    /**
     * 搜索课程
     * @param courseName 要搜索的课程名
     * @return 搜索结果
     */
    public Result<List<CourseSearched>> searchCourse(String courseName){
        HttpUrl path = new HttpUrl.Builder().scheme(PROTOCOL).host(HOST).encodedPath(SEARCH_PATH).build();
        GeneralizedClient<SearchReq, List<CourseSearched>> client = new GeneralizedClient<>();
        client.set(SearchReq.class, COURSE_LIST_ADAPTER, path);
        SearchReq si = new SearchReq(courseName);
        return client.post(si);
    }

}
