package cn.biketomotor.xh.xuanhu.Api;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

import static cn.biketomotor.xh.xuanhu.Api.Constants.CONTENT_TYPE;
import static cn.biketomotor.xh.xuanhu.Api.Constants.HOST;
import static cn.biketomotor.xh.xuanhu.Api.Constants.PROTOCOL;

public enum SessionApi {
    INSTANCE;

    private static final String LOGIN_PATH = "/api/login";

    static class LoginForm {
        String email;
        String password;

        LoginForm(String email, String password) {
            this.email = email;
            this.password = password;
        }
    }

    static class LoginResult {
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
        HttpUrl path = new HttpUrl.Builder().scheme(PROTOCOL).host(HOST).encodedPath(LOGIN_PATH).build();
        GeneralizedClient<LoginForm, LoginResult> client = new GeneralizedClient<>(LoginForm.class, LoginResult.class, path);
        LoginForm form = new LoginForm(email, password);
        return client.post(form);
    }
}
