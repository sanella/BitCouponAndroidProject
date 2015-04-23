package com.project.bitcoupon.bitcoupon.singletons;

import android.util.Log;

import com.project.bitcoupon.bitcoupon.models.Company;
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
public class CompanyFeed {
    private static CompanyFeed ourInstance = new CompanyFeed();

    public static CompanyFeed getInstance() {
        return ourInstance;
    }

    private ArrayList<Company> mFeedCompany;

    private CompanyFeed() {
        mFeedCompany = new ArrayList<Company>();
    }

    public void getFeed(String url){
        ServiceRequest.get(url, parseResponse());
    }

    public ArrayList<Company> getFeed(){
        return mFeedCompany;
    }

    private Callback parseResponse() {
        return new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                Log.e("RESPONSE", e.getMessage());
            }

            @Override
            public void onResponse(Response response) throws IOException {
                try {
                    String responseS= response.body().string();
                    Log.d("EDIBRespons", responseS);
                    JSONArray array = new JSONArray(responseS);
                    Log.d("SARR", "" + array.length());
                    for (int i = 0; i < array.length(); i++) {

                        JSONObject postObj = array.getJSONObject(i);
                        int id = postObj.getInt("id");
                        String name = postObj.getString("name");
                        String email = postObj.getString("email");
                        String logo = postObj.getString("logo");



                        mFeedCompany.add(new Company(id, name, email, logo));
                        Log.d("TESTAG", postObj.toString());
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        };
    }



}