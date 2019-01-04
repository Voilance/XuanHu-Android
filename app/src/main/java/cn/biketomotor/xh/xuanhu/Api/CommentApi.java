package cn.biketomotor.xh.xuanhu.Api;

import java.util.List;

import cn.biketomotor.xh.xuanhu.Api.Beans.Comment;
import okhttp3.HttpUrl;

import static cn.biketomotor.xh.xuanhu.Api.Constants.COMMENT_LIST_ADAPTER;
import static cn.biketomotor.xh.xuanhu.Api.Constants.HOST;
import static cn.biketomotor.xh.xuanhu.Api.Constants.PROTOCOL;

/**
 * 提供评论相关的功能接口
 */
public enum CommentApi {
    INSTANCE;

    private static final String LATEST_PATH = "/api/latest";

    /**
     * 获取最近的评论
     * @param page 需要获取第几页的最近评论。page 为 1 即为最新的评论。
     * @return 评论列表的结果
     */
    public Result<List<Comment>> latest(int page) {
        HttpUrl path = new HttpUrl.Builder().scheme(PROTOCOL).host(HOST).encodedPath(LATEST_PATH).addQueryParameter("page", String.valueOf(page)).build();
        GeneralizedClient<Object, List<Comment>> client = new GeneralizedClient<>(null, COMMENT_LIST_ADAPTER, path);
        return client.get();
    }
}
