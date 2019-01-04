package cn.biketomotor.xh.xuanhu.Api;

import java.util.Date;

import okhttp3.HttpUrl;

import static cn.biketomotor.xh.xuanhu.Api.Constants.HOST;
import static cn.biketomotor.xh.xuanhu.Api.Constants.PROTOCOL;

/**
 * 提供登录状态相关的功能接口
 */
public enum SessionApi {
    INSTANCE;

    private static final String LOGIN_PATH = "/api/login";
    private static final String LOGOUT_PATH = "/api/logout";

    static class LoginForm {
        String email;
        String password;

        LoginForm(String email, String password) {
            this.email = email;
            this.password = password;
        }
    }

    public static class LoginResult {
        public boolean success = true;
        public int id;
        public String email;
        public String name;
        public Date created_at;
        public Date updated_at;
        public String avatar_url;
        public Integer teacher_id;
        public String description;
    }

    /**
     * 用户登录
     * @param email 用户的电子邮箱地址
     * @param password 用户的密码
     * @return 用户登录的结果
     */
    public Result<LoginResult> login(String email, String password) {
        HttpUrl path = new HttpUrl.Builder().scheme(PROTOCOL).host(HOST).encodedPath(LOGIN_PATH).build();
        GeneralizedClient<LoginForm, LoginResult> client = new GeneralizedClient<>(LoginForm.class, LoginResult.class, path);
        LoginForm form = new LoginForm(email, password);
        return client.post(form);
    }

    static class LogoutForm {
    }

    public static class LogoutResult {
        public boolean success = true;
    }

    /**
     * 用户登出系统
     * @return 用户登出的结果
     */
    public Result<LogoutResult> logout() {
        HttpUrl path = new HttpUrl.Builder().scheme(PROTOCOL).host(HOST).encodedPath(LOGOUT_PATH).build();
        GeneralizedClient<LogoutForm, LogoutResult> client = new GeneralizedClient<>(LogoutForm.class, LogoutResult.class, path);
        LogoutForm form = new LogoutForm();
        return client.post(form);
    }
}
