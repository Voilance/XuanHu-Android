package cn.biketomotor.xh.xuanhu.Api;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;

import cn.biketomotor.xh.xuanhu.Api.Beans.Comment;
import okhttp3.HttpUrl;

import static cn.biketomotor.xh.xuanhu.Api.Constants.HOST;
import static cn.biketomotor.xh.xuanhu.Api.Constants.PROTOCOL;

public enum CommentApi {
    INSTANCE;

    private static final String LATEST_PATH = "/api/latest";

    public Result<List<Comment>> latest(int page) {
        Type type = Types.newParameterizedType(List.class, Comment.class);
        Moshi moshi = new Moshi.Builder().add(Date.class, new Rfc3339DateJsonAdapter()).build();
        JsonAdapter<List<Comment>> respAdapter = moshi.adapter(type);
        HttpUrl path = new HttpUrl.Builder().scheme(PROTOCOL).host(HOST).encodedPath(LATEST_PATH).addQueryParameter("page", String.valueOf(page)).build();
        GeneralizedClient<Object, List<Comment>> client = new GeneralizedClient<>(null, respAdapter, path);
        return client.get();
    }
}
