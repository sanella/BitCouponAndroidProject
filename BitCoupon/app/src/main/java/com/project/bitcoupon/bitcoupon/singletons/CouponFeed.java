package com.project.bitcoupon.bitcoupon.singletons;

import android.util.Log;

import com.project.bitcoupon.bitcoupon.models.Coupon;
import com.project.bitcoupon.bitcoupon.service.ServiceRequest;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Sanela on 4/22/2015.
 */
public class CouponFeed {
    private static CouponFeed ourInstance = new CouponFeed();

    public static CouponFeed getInstance() {
        return ourInstance;
    }

    private ArrayList<Coupon> mFeed;

    private CouponFeed() {
        mFeed = new ArrayList<Coupon>();
    }

    public void getFeed(String url){
        ServiceRequest.get(url, parseResponse());
    }

    public ArrayList<Coupon> getFeed(){
        return mFeed;
    }

    private Callback parseResponse(){
        return new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                Log.e("RESPONSE", e.getMessage());
            }

            @Override
            public void onResponse(Response response) throws IOException {
                try {

                    Log.d("SARR", "Nets");
                    JSONArray array = new JSONArray(response.body().string());
                    Log.d("SARR", ""+array.length());
                    for(int i = 0; i < array.length(); i++){
                        JSONObject postObj = array.getJSONObject(i);
                        int id = postObj.getInt("id");
                        String name = postObj.getString("name");
                        double price = postObj.getDouble("price");
                        String picture = postObj.getString("picture");

                        mFeed.add(new Coupon(id, name, price, picture));
                        Log.d("TESTAG" ,postObj.toString());
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        };
    }
}
