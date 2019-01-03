package cn.biketomotor.xh.xuanhu.Api;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;

import cn.biketomotor.xh.xuanhu.Api.Beans.Comment;
import cn.biketomotor.xh.xuanhu.Api.Beans.Course;
import okhttp3.MediaType;

class Constants {
    static final String PROTOCOL = "https";
    static final String HOST = "xh.biketomotor.cn";
    static final MediaType CONTENT_TYPE = MediaType.parse("application/json;charset=UTF-8");
    static final JsonAdapter<List<Comment>> COMMENT_LIST_ADAPTER = getCommentListAdapter();
    static final JsonAdapter<List<SearchApi.CourseSearched>> COURSE_LIST_ADAPTER = getCourseListAdapter();

    private static JsonAdapter<List<Comment>> getCommentListAdapter() {
        Type type = Types.newParameterizedType(List.class, Comment.class);
        Moshi moshi = new Moshi.Builder().add(Date.class, new Rfc3339DateJsonAdapter()).build();
        return moshi.adapter(type);
    }

    //获取搜索结果适配器
    private static JsonAdapter<List<SearchApi.CourseSearched>> getCourseListAdapter(){
        Type type = Types.newParameterizedType(List.class, SearchApi.CourseSearched.class);
        Moshi moshi = new Moshi.Builder().add(Date.class, new Rfc3339DateJsonAdapter()).build();
        return moshi.adapter(type);
    }
}
