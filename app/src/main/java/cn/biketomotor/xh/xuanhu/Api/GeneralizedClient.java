package cn.biketomotor.xh.xuanhu.Api;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.JsonDataException;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Date;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

import static cn.biketomotor.xh.xuanhu.Api.Constants.CONTENT_TYPE;

public class GeneralizedClient<Req, Resp> {
    private Moshi moshi;
    private JsonAdapter<Req> reqAdapter;
    private JsonAdapter<Resp> respAdapter;
    protected HttpUrl path;

    protected GeneralizedClient(JsonAdapter<Req> reqAdapter, JsonAdapter<Resp> respAdapter, HttpUrl path) {
        this.reqAdapter = reqAdapter;
        this.respAdapter = respAdapter;
        this.path = path;
    }

    protected GeneralizedClient(Class<Req> reqClass, Class<Resp> respClass, HttpUrl path) {
        moshi = new Moshi.Builder().add(Date.class, new Rfc3339DateJsonAdapter()).build();
        reqAdapter = moshi.adapter(reqClass);
        respAdapter = moshi.adapter(respClass);
        this.path = path;
    }

    protected Result<Resp> parse(String json) {
        try {
            return Result.ok(respAdapter.fromJson(json));
        } catch (JsonDataException | IOException e) {
            e.printStackTrace();
            return Result.err("JSON解析失败：" + json);
        }
    }

    protected Result<Resp> post(Req req) {
        try {
            OkHttpClient client = HttpClientManager.getClient();
            String content = reqAdapter.toJson(req);
            RequestBody body = RequestBody.create(CONTENT_TYPE, content);
            Request request = new Request.Builder().url(path).post(body).build();
            Response response = client.newCall(request).execute();
            ResponseBody responseBody = response.body();
            if (responseBody != null) {
                return parse(responseBody.string());
            } else {
                return Result.err("响应没有Body部分，状态码：" + response.code());
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return Result.err("连接URL格式错误");
        } catch (IOException e) {
            e.printStackTrace();
            return Result.err("HTTP请求IO错误");
        }
    }

    protected Result<Resp> get() {
        try {
            OkHttpClient client = HttpClientManager.getClient();
            Request request = new Request.Builder().url(path).get().build();
            Response response = client.newCall(request).execute();
            ResponseBody responseBody = response.body();
            if (responseBody != null) {
                return parse(responseBody.string());
            } else {
                return Result.err("响应没有Body部分，状态码：" + response.code());
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
