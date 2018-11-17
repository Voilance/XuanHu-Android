package cn.biketomotor.xh.xuanhu.Api;

import java.util.Date;

public enum CommentApi {
    INSTANCE;

    private static final String LATEST_PATH = "/api/latest";

    static class LastestCommentsForm {
        int page;

        LastestCommentsForm(int page) {
            this.page = page;
        }
    }

    static class LastestCommentsResult {
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

//    public Result<LoginResult> login(String email, String password) {
//        GeneralizedClient<LoginForm, LoginResult> client = new GeneralizedClient<>(LoginForm.class, LoginResult.class, LOGIN_PATH);
//        LoginForm form = new LoginForm(email, password);
//        return client.process(form);
//    }
}
