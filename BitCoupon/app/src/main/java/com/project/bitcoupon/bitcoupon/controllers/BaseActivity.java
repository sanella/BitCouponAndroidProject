package com.project.bitcoupon.bitcoupon.controllers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.project.bitcoupon.bitcoupon.R;
import com.project.bitcoupon.bitcoupon.service.ServiceRequest;
import com.project.bitcoupon.bitcoupon.singletons.UserData;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by Sanela on 4/23/2015.
 */
public class BaseActivity extends ActionBarActivity {

    public static final String SHARED_PREFERENCES = "ba.bitcoupon.shared_preferences";

    private void makeToast(final int messageId){

        new Handler(Looper.getMainLooper())
                .post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                messageId,
                                Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private Callback getProfile() {
        return new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                makeToast(R.string.toast_try_again);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                String responseJson = response.body().string();

                try {

                    JSONObject profile = new JSONObject(responseJson);
                    Intent goToProfile = new Intent(getApplicationContext() , UserProfileActivity.class);
                    goToProfile.putExtra("id", profile.getString("id"));
                    goToProfile.putExtra("name", profile.getString("name"));
                    goToProfile.putExtra("surname", profile.getString("surname"));
                    goToProfile.putExtra("email",profile.getString("email"));
                    goToProfile.putExtra("address", profile.getString("address"));
                    goToProfile.putExtra("city",profile.getString("city"));
                    String pic = profile.getString("picture");
                    goToProfile.putExtra("picture", profile.getString("picture"));


                    startActivity(goToProfile);

                } catch (JSONException e) {
                    makeToast(R.string.toast_try_again);
                    e.printStackTrace();
                }
            }
        };
    }


    public static boolean checkIfLogged(Context ctx){
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE);
        String email = sharedPreferences.getString(
                ctx.getString(R.string.key_user_email),
                null
        );

        String password = sharedPreferences.getString(
                ctx.getString(R.string.key_user_password),
                null
        );

        if(email==null || password == null )
            return false;
        return true;
    }

    public static void createMenuItems(Activity activity, Menu menu){
        // Inflate the menu; this adds items to the action bar if it is present.
        activity.getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem logout = menu.findItem(R.id.action_logout);
        MenuItem profile = menu.findItem(R.id.action_profile);
        if(!checkIfLogged(activity)) {
            logout.setVisible(false);
            profile.setVisible(false);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        createMenuItems(this, menu);
        return true;
    }

    /**
     * Static method for loggining out.
     * @param ctx
     */
    public static void logout(Context ctx){
        SharedPreferences sharedpreferences = ctx.getSharedPreferences
                (SHARED_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.clear();
        editor.commit();
        UserData.getInstance().setEmail(null);
        UserData.getInstance().setPassword(null);
        Intent it = new Intent(ctx, MainActivity.class);
        ctx.startActivity(it);
        Log.d("LOGIN", "IN ON ITEM SELECT.");
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if (id == R.id.action_logout) {
            logout(this);
            return true;
        }

        if (id == R.id.action_profile) {

            String email = UserData.getInstance().getEmail().toString();

            if(email != null ){

                String url = getString(R.string.service_user_profile);
                JSONObject profile= new JSONObject();
                try {
                    profile.put("email", email);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                String json = profile.toString();
                ServiceRequest.post(url, json, getProfile());

            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
