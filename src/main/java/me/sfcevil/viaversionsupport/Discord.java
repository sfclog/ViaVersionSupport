package me.sfcevil.viaversionsupport;

import okhttp3.*;

import java.io.IOException;

public class Discord {

    private static final OkHttpClient httpClient = new OkHttpClient();

    public static void sendmsg(String content) {
        String msg = "{\"content\":\"" + content + "\"}";

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), msg);
        Request request = new Request.Builder()
                .url(Main.web_hook)
                .post(requestBody)
                .build();

        httpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                response.close();
            }
        });
    }
}