package cn.biketomotor.xh.xuanhu.Api;

import java.util.Date;

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

    public Result<LoginResult> login(String email, String password) {
        GeneralizedClient<LoginForm, LoginResult> client = new GeneralizedClient<>(LoginForm.class, LoginResult.class, LOGIN_PATH);
        LoginForm form = new LoginForm(email, password);
        return client.process(form);
    }

    static class LogoutForm {
    }

    public static class LogoutResult {
        public boolean success = true;
    }

    public Result<LogoutResult> logout() {
        GeneralizedClient<LogoutForm, LogoutResult> client = new GeneralizedClient<>(LogoutForm.class, LogoutResult.class, LOGOUT_PATH);
        LogoutForm form = new LogoutForm();
        return client.process(form);
    }
}
