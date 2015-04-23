package com.project.bitcoupon.bitcoupon.service;

import com.project.bitcoupon.bitcoupon.singletons.UserData;
import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;

/**
 * Created by Sanela on 4/22/2015.
 */
public class ServiceRequest {

    public static void get(String url, Callback callback){
        request(url, null, callback, false);
    }

    public static void post(String url, String json, Callback callback){
        request(url, json, callback, true);
    }

    private static void request(String url,
                                String json,
                                Callback callback,
                                boolean isPost){

        MediaType JSON = MediaType.parse("application/json");

        OkHttpClient client = new OkHttpClient();

        Request.Builder requestBuilder = new Request.Builder();

        if(isPost == true) {
            RequestBody requestBody = RequestBody.create(JSON,json);
            requestBuilder.post(requestBody);
        }
        else
            requestBuilder.get();

        Request request = requestBuilder
                .url(url)
                .addHeader("Authorization", UserData.getInstance().getBaseAuth())
                .addHeader("Accept", "application/json")
                .build();

        Call call = client.newCall(request);
        call.enqueue(callback);
    }

}
