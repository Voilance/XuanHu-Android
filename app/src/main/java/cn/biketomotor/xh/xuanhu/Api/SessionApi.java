package cn.biketomotor.xh.xuanhu.Api;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;

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

    static class LoginJsonBuilder {
        static Moshi moshi;
        static JsonAdapter<LoginForm> jsonAdapter;

        static class LoginForm {
            String email;
            String password;

            LoginForm(String email, String password) {
                this.email = email;
                this.password = password;
            }
        }

        static {
            moshi = new Moshi.Builder().build();
            jsonAdapter = moshi.adapter(LoginForm.class);
        }

        private LoginForm form;

        LoginJsonBuilder(String email, String password) {
            form = new LoginForm(email, password);
        }

        String build() {
            return jsonAdapter.toJson(form);
        }
    }

    static class LoginResultParser {
        static Moshi moshi;
        static JsonAdapter<LoginResult> jsonAdapter;

        static {
            moshi = new Moshi.Builder().add(Date.class, new Rfc3339DateJsonAdapter()).build();
            jsonAdapter = moshi.adapter(LoginResultParser.LoginResult.class);
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

        static Result<LoginResult> parse(String json) {
            try {
                return Result.ok(jsonAdapter.fromJson(json));
            } catch (IOException e) {
                e.printStackTrace();
                return Result.err("JSON解析失败");
            }
        }
    }

    public Result<LoginResultParser.LoginResult> login(String email, String password) {
        try {
            OkHttpClient client = HttpClientManager.getClient();
            String content = new LoginJsonBuilder(email, password).build();
            RequestBody body = RequestBody.create(CONTENT_TYPE, content);
            Request request = new Request.Builder().url(new URL(PROTOCOL, HOST, LOGIN_PATH)).post(body).build();
            Response response = client.newCall(request).execute();
            if (!response.isSuccessful()) {
                return Result.err("请求失败，HTTP状态码：" + response.code());
            }
            ResponseBody responseBody = response.body();
            if (responseBody != null) {
                return LoginResultParser.parse(responseBody.string());
            } else {
                return Result.err("响应没有Body部分");
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return Result.err("连接URL格式错误");
        } catch (IOException e) {
            e.printStackTrace();
            return Result.err("HTTP请求IO错误");
        }
    }
}
