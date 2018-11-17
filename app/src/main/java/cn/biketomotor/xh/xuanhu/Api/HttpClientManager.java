package cn.biketomotor.xh.xuanhu.Api;

import okhttp3.OkHttpClient;

class HttpClientManager {
    private static OkHttpClient client;

    static synchronized OkHttpClient getClient() {
        if (client == null) {
            client = new OkHttpClient.Builder().build();
        }
        return client;
    }
}
