package com.project.bitcoupon.bitcoupon.controllers;


import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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


public class MainActivity extends ActionBarActivity {

    private SharedPreferences mSharedPreferences;
    public static final String TAG = "MainActivity_TAG";
    public static final String SHARED_PREFERENCES = "ba.bitcoupon.shared_preferences";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSharedPreferences = this.getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE);
        String email = mSharedPreferences.getString(
                getString(R.string.key_user_email),
                null
        );

        String password = mSharedPreferences.getString(
                getString(R.string.key_user_password),
                null
        );

        if(email != null && password != null){
            setUserData(email, password);
            Log.d("LOGIN", "email> " + email + " password> " + password);
            loginUser();
        }

        final EditText editEmail = (EditText) findViewById(R.id.edit_text_email);
        final EditText editPassword = (EditText)findViewById(R.id.edit_text_password);

        Button buttonLogin = (Button) findViewById(R.id.button_login);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editEmail.getText().toString();
                String password = editPassword.getText().toString();

                setUserData(email, password);
                loginUser();
            }
        });
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

    private void loginUser(){
        String url = getString(R.string.service_login);
        Callback callback = loginVerification();
        String json = UserData.getInstance().toJson();

        ServiceRequest.post(url, json, callback);
    }

    private Callback loginVerification(){
        return new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                makeToast(R.string.toast_try_again);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                String responseJson = response.body().string();
                try {
                    JSONObject user = new JSONObject(responseJson);
                    int id = user.getInt("id");
                    if(id > 0){
                        Log.d(TAG, response.body().toString());

                        String username = user.getString("name");
                        Log.d(TAG, response.body().toString());
                        UserData userData = UserData.getInstance();
                        userData.setId(id);
                        userData.setUsername(username);
                        saveUserCredentials();
                        goToPosts();
                    }
                } catch (JSONException e) {
                    makeToast(R.string.toast_try_again);
                    e.printStackTrace();
                }
            }
        };
    }

    private void saveUserCredentials(){
        SharedPreferences.Editor editor = mSharedPreferences.edit();

        UserData userData = UserData.getInstance();

        editor.putString(
                getString(R.string.key_user_email),
                userData.getEmail()
        );

        editor.putString(
                getString(R.string.key_user_password),
                userData.getPassword()
        );
        editor.commit();
    }

    private void makeToast(final int messageId){

        new Handler(Looper.getMainLooper())
                .post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this,
                                messageId,
                                Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private void goToPosts(){
        Intent test = new Intent(this, CouponActivity.class);
        startActivity(test);
    }

    private void setUserData(String email, String password){
        UserData userData = UserData.getInstance();
        userData.setEmail(email);
        userData.setPassword(password);
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
                    Log.d(TAG, "profil");
                    Intent goToProfile = new Intent(MainActivity.this, UserProfileActivity.class);
                    goToProfile.putExtra("id", profile.getString("id"));
                    goToProfile.putExtra("name", profile.getString("name"));
                    goToProfile.putExtra("surname", profile.getString("surname"));
                    goToProfile.putExtra("email",profile.getString("email"));
                    goToProfile.putExtra("address", profile.getString("address"));
                    goToProfile.putExtra("city",profile.getString("city"));
                    String pic = profile.getString("picture");
                    Log.d(TAG, pic);
                    goToProfile.putExtra("picture", profile.getString("picture"));


                    startActivity(goToProfile);

                } catch (JSONException e) {
                    makeToast(R.string.toast_try_again);
                    e.printStackTrace();
                }
            }
        };
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
                Log.d(TAG, json);
                ServiceRequest.post(url, json,  getProfile());

            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
