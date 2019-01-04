package cn.biketomotor.xh.xuanhu.Api;

import java.util.List;

import cn.biketomotor.xh.xuanhu.Api.Beans.Comment;
import okhttp3.HttpUrl;

import static cn.biketomotor.xh.xuanhu.Api.Constants.COMMENT_LIST_ADAPTER;
import static cn.biketomotor.xh.xuanhu.Api.Constants.HOST;
import static cn.biketomotor.xh.xuanhu.Api.Constants.PROTOCOL;

/**
 * 提供用户相关的功能接口
 */
public enum UserApi {
    INSTANCE;

    private static final String REGISTER_PATH = "/api/reg";
    private static final String USER_PATH = "/api/users";

    static class RegisterForm {
        String name;
        String email;
        String password;

        RegisterForm(String name, String email, String password) {
            this.name = name;
            this.email = email;
            this.password = password;
        }
    }

    /**
     * 注册用户
     * @param name 用户姓名
     * @param email 用户的电子邮件地址
     * @param password 用户的密码
     * @return 注册结果。如果注册成功将返回注册信息，否则会有错误信息。
     */
    public Result<UserInfo> register(String name, String email, String password) {
        HttpUrl path = new HttpUrl.Builder().scheme(PROTOCOL).host(HOST).encodedPath(REGISTER_PATH).build();
        GeneralizedClient<RegisterForm, UserInfo> client = new GeneralizedClient<>(RegisterForm.class, UserInfo.class, path);
        RegisterForm form = new RegisterForm(name, email, password);
        Result<UserInfo> result = client.post(form);
        if (result.isOk()) {
            return result;
        } else if (result.getErrorMessage().contains("has already been taken")) {
            return Result.err("邮箱已被占用");
        } else {
            return result;
        }
    }

    /**
     * 获取用户信息
     * @param id 用户的 id
     * @return 用户信息
     */
    public Result<UserInfo> getUserInfo(int id) {
        HttpUrl path = new HttpUrl.Builder()
                .scheme(PROTOCOL)
                .host(HOST)
                .addEncodedPathSegment(USER_PATH)
                .addEncodedPathSegment(String.valueOf(id))
                .build();
        GeneralizedClient<Object, UserInfo> client = new GeneralizedClient<>(Object.class, UserInfo.class, path);
        return client.get();
    }

    /**
     * 获取用户的评论列表
     * @param id 用户的 id
     * @return 用户的评论列表
     */
    public Result<List<Comment>> getUserComments(int id) {
        HttpUrl path = new HttpUrl.Builder()
                .scheme(PROTOCOL)
                .host(HOST)
                .addEncodedPathSegment(USER_PATH)
                .addEncodedPathSegment(String.valueOf(id))
                .addEncodedPathSegment("comments")
                .build();
        GeneralizedClient<Object, List<Comment>> client = new GeneralizedClient<>(null, COMMENT_LIST_ADAPTER, path);
        return client.get();
    }

    /**
     * 获取用户的投过票的评论列表
     * @param id 用户的 id
     * @return 用户投过票的评论列表
     */
    public Result<List<Comment>> getUserVotes(int id) {
        HttpUrl path = new HttpUrl.Builder()
                .scheme(PROTOCOL)
                .host(HOST)
                .addEncodedPathSegment(USER_PATH)
                .addEncodedPathSegment(String.valueOf(id))
                .addEncodedPathSegment("votes")
                .build();
        GeneralizedClient<Object, List<Comment>> client = new GeneralizedClient<>(null, COMMENT_LIST_ADAPTER, path);
        return client.get();
    }

    public static class UserInfo {
        public int id;
        public String email;
        public String name;
        public String avatar_url;
        public Integer teacher_id;
        public String description;
    }
}
