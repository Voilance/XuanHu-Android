package cn.biketomotor.xh.xuanhu.Api;

import java.util.List;

import cn.biketomotor.xh.xuanhu.Api.Beans.Comment;
import cn.biketomotor.xh.xuanhu.Api.Beans.Course;
import okhttp3.HttpUrl;

import static cn.biketomotor.xh.xuanhu.Api.Constants.COMMENT_LIST_ADAPTER;
import static cn.biketomotor.xh.xuanhu.Api.Constants.HOST;
import static cn.biketomotor.xh.xuanhu.Api.Constants.PROTOCOL;

/**
 * 提供课程相关的功能接口
 */
public enum CourseApi {
    INSTANCE;

    private static final String COURSE_PATH = "/api/courses";

    /**
     * 获取课程信息
     * @param id 课程的 id
     * @return 课程信息
     */
    public Result<Course> getCourse(int id) {
        HttpUrl path = new HttpUrl.Builder()
                .scheme(PROTOCOL)
                .host(HOST)
                .encodedPath(COURSE_PATH)
                .addEncodedPathSegments(String.valueOf(id))
                .build();
        GeneralizedClient<Object, Course> client = new GeneralizedClient<>(Object.class, Course.class, path);
        return client.get();
    }

    /**
     * 获取课程的评论
     * @param id 课程的 id
     * @return 课程的评论列表
     */
    public Result<List<Comment>> getCourseComments(int id) {
        HttpUrl path = new HttpUrl.Builder()
                .scheme(PROTOCOL)
                .host(HOST)
                .encodedPath(COURSE_PATH)
                .addEncodedPathSegments(String.valueOf(id))
                .addEncodedPathSegments("comments")
                .build();
        GeneralizedClient<Object, List<Comment>> client = new GeneralizedClient<>(null, COMMENT_LIST_ADAPTER, path);
        return client.get();
    }
}
