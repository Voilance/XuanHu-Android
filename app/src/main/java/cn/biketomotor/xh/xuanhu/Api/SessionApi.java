package cn.biketomotor.xh.xuanhu.Api;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

import static cn.biketomotor.xh.xuanhu.Api.Constants.*;

public enum SessionApi {
    INSTANCE;

    private static final String LOGIN_PATH = "/api/login";

    static class LoginJsonBuilder {
        static Moshi moshi;
        static JsonAdapter<LoginForm> jsonAdapter;

        static class LoginForm {
            String username;
            String password;

            LoginForm(String username, String password) {
                this.username = username;
                this.password = password;
            }
        }

        static {
            moshi = new Moshi.Builder().build();
            jsonAdapter = moshi.adapter(LoginForm.class);
        }

        private LoginForm form;

        LoginJsonBuilder(String username, String password) {
            form = new LoginForm(username, password);
        }

        String build() {
            return jsonAdapter.toJson(form);
        }
    }

    public Result login(String username, String password) {
        try {
            OkHttpClient client = HttpClientManager.getClient();
            String content = new LoginJsonBuilder(username, password).build();
            RequestBody body = RequestBody.create(CONTENT_TYPE, content);
            Request request = new Request.Builder().url(new URL(PROTOCOL, HOST, LOGIN_PATH)).post(body).build();
            Response response = client.newCall(request).execute();
            if (!response.isSuccessful()) {
                return Result.err("请求失败，HTTP状态码：" + response.code());
            }
            ResponseBody responseBody = response.body();
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return Result.err("连接URL格式错误");
        } catch (IOException e) {
            e.printStackTrace();
            return Result.err("HTTP请求IO错误");
        }
        return null;
    }
}
