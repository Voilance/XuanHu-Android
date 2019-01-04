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
    private static final String COURSE_PATH = "/api/courses";

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

    static class CommentForm {
        String content;
        Integer parent_id;

        CommentForm(String content, Integer parent_id) {
            this.content = content;
            this.parent_id = parent_id;
        }
    }

    /**
     * 给课程添加评论，或给评论添加评论
     * @param courseId 课程的 id
     * @param content 评论内容
     * @param parentId 评论的父评论 id。如果这是对课程的直接评论，该参数为 null。
     * @return 刚刚发表的评论的信息
     */
    public Result<Comment> create(int courseId, String content, Integer parentId) {
        HttpUrl path = new HttpUrl.Builder()
                .scheme(PROTOCOL)
                .host(HOST)
                .encodedPath(COURSE_PATH)
                .addEncodedPathSegments(String.valueOf(courseId))
                .addEncodedPathSegments("comments")
                .build();
        GeneralizedClient<CommentForm, Comment> client = new GeneralizedClient<>(CommentForm.class, Comment.class, path);
        CommentForm form = new CommentForm(content, parentId);
        return client.post(form);
    }

    static class VoteForm {
        int value;

        public VoteForm(int value) {
            this.value = value;
        }
    }

    public Result<Comment> vote(int courseId, int commentId, int voteValue) {
        HttpUrl path = new HttpUrl.Builder()
                .scheme(PROTOCOL)
                .host(HOST)
                .encodedPath(COURSE_PATH)
                .addEncodedPathSegments(String.valueOf(courseId))
                .addEncodedPathSegments("comments")
                .addEncodedPathSegments(String.valueOf(commentId))
                .addEncodedPathSegments("vote")
                .build();
        GeneralizedClient<VoteForm, Comment> client = new GeneralizedClient<>(VoteForm.class, Comment.class, path);
        VoteForm form = new VoteForm(voteValue);
        return client.post(form);
    }
}
