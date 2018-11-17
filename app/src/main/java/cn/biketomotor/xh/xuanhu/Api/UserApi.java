package cn.biketomotor.xh.xuanhu.Api;

public enum UserApi {
    INSTANCE;

    private static final String REGISTER_PATH = "/api/reg";

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

    public static class RegisterResult {
        public boolean success = true;
        public int id;
        public String email;
        public String name;
        public String avatar_url;
        public Integer teacher_id;
        public String description;
    }

    public Result<RegisterResult> register(String name, String email, String password) {
        GeneralizedClient<RegisterForm, RegisterResult> client = new GeneralizedClient<>(RegisterForm.class, RegisterResult.class, REGISTER_PATH);
        RegisterForm form = new RegisterForm(name, email, password);
        Result<RegisterResult> result = client.process(form);
        if (result.isOk()) {
            return result;
        } else if (result.getErrorMessage().contains("has already been taken")) {
            return Result.err("邮箱已被占用");
        } else {
            return result;
        }
    }
}
