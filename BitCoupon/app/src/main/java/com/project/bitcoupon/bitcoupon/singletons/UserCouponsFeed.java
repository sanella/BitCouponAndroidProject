package com.project.bitcoupon.bitcoupon.singletons;

import android.util.Log;

import com.project.bitcoupon.bitcoupon.models.CouponBought;
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
public class UserCouponsFeed {


    private static UserCouponsFeed ourInstance = new UserCouponsFeed();

    public static UserCouponsFeed getInstance() {
        return ourInstance;
    }

    private ArrayList<CouponBought> mFeed;

    private UserCouponsFeed() {
        mFeed = new ArrayList<CouponBought>();

    }
    public void postFeed(String url, String json ){
        ServiceRequest.post(url, json, parseResponse());
    }

    public ArrayList<CouponBought> getFeed(){
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
                String responseJson = response.body().string();
                Log.d("BENJO", responseJson);

                try {

                    Log.d("NIZ", "Nets");
                    JSONArray array = new JSONArray(responseJson);
                    Log.d("VELICINA", ""+array.length());
                    for(int i = 0; i < array.length(); i++){
                        Log.d("NARR", ""+i);
                        JSONObject postObj = array.getJSONObject(i);
                        int id = postObj.getInt("id");
                        String couponId = postObj.getString("couponId");
                        String couponName = postObj.getString("name");
                        String couponDescription = postObj.getString("description");
                        String couponPrice =  postObj.getString("price");
                        String couponQuantity =  postObj.getString("quantity");
                        String couponPicture =  postObj.getString("picture");
                        String couponTotalPrice =  postObj.getString("totalPrice");
                        String couponTransactionDate =  postObj.getString("transactionDate");
                        String couponPaymentId =  postObj.getString("paymentId");
                        String couponToken =  postObj.getString("token");

                        mFeed.add(new CouponBought(couponId, couponName, couponDescription, couponPrice, couponQuantity,
                                couponPicture,couponTotalPrice, couponTransactionDate, couponPaymentId, couponToken));
                        Log.d("TESTAG" ,postObj.toString());
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        };
    }
}

