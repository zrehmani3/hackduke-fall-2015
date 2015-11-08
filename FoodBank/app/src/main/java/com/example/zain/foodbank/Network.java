package com.example.zain.foodbank;

import com.squareup.okhttp.Headers;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Created by zain on 11/8/15.
 */
public class Network {

    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    public static final MediaType MEDIA_TYPE_JPEG
            = MediaType.parse("image/jpeg");

    private final OkHttpClient client;

    public Network() {
        client = new OkHttpClient();
        client.setConnectTimeout(60, TimeUnit.SECONDS);
        client.setReadTimeout(60, TimeUnit.SECONDS);
        client.setWriteTimeout(60, TimeUnit.SECONDS);
    }

    public String postFile(String fileName) throws Exception {
        // Use the imgur image upload API as documented at https://api.imgur.com/endpoints/image
        RequestBody requestBody = new MultipartBuilder()
                .type(MultipartBuilder.FORM)
                .addPart(
                        Headers.of("Content-Disposition", "form-data; name=\"file\"; filename=\"image.jpg\""),
                        RequestBody.create(MEDIA_TYPE_JPEG, new File(fileName)))
                .build();

        Request request = new Request.Builder()
                .url("http://ec2-52-23-206-188.compute-1.amazonaws.com/upload")
                .post(requestBody)
                .build();

        Response response = client.newCall(request).execute();
        if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

        return response.body().string();
    }

    String get(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    String post(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(MediaType.parse("application/x-www-form-urlencoded"), json);
        Request request = new Request.Builder()
                .url(url)
                .header("Content-Type", "application/x-www-form-urlencoded")
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }
}
