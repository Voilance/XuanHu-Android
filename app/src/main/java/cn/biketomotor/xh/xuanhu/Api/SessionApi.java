package cn.biketomotor.xh.xuanhu.Api;

import java.net.MalformedURLException;
import java.net.URL;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

import static cn.biketomotor.xh.xuanhu.Api.Constants.*;

public enum SessionApi {
    INSTANCE;

    private static final String LOGIN_PATH = "/api/login";

    private String sessionCookie;

    public void setSessionCookie(String sessionCookie) {
        this.sessionCookie = sessionCookie;
    }

    public Result login(String username, String password) {
        try {
            OkHttpClient client = HttpClientManager.getClient();
//            RequestBody body = RequestBody.create(CONTENT_TYPE, )
            Request request = new Request.Builder().url(new URL(PROTOCOL, HOST, LOGIN_PATH)).build();

        } catch (MalformedURLException e) {
            e.printStackTrace();
            return Result.err("Malformed URL");
        }
        return null;
    }
}
