package com.project.bitcoupon.bitcoupon.controllers;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.project.bitcoupon.bitcoupon.R;
import com.squareup.picasso.Picasso;

public class UserProfileActivity extends ActionBarActivity {

    private TextView mId;
    private TextView mName;
    private TextView mSurname;
    private TextView mEmail;
    private TextView mAdress;
    private TextView mCity;
    private ImageView mImage;
    private Button mUserCoupons;
    private  String id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        Intent it = getIntent();
        id = it.getStringExtra("id");
        String name = it.getStringExtra("name");
        String surname =it.getStringExtra("surname");
        String email = it.getStringExtra("email");
        String adress = it.getStringExtra("adress");
        String city = it.getStringExtra("city");
        String profilePicture = it.getStringExtra("picture");

        Toast.makeText(this,
                profilePicture,
                Toast.LENGTH_SHORT).show();
        mImage = (ImageView) findViewById(R.id.imageView_user_profilePicture);
        String img =getString(R.string.image_path) + profilePicture;
        img = img.replaceAll("\\\\","/");
        Log.d("IMGTAG", img);
        Picasso.with(UserProfileActivity.this).load(img).into(mImage);




        mId = (TextView) findViewById(R.id.textView_user_profile_id);
        mId.setText(id);

        mName = (TextView) findViewById(R.id.textView_user_profile_name);
        mName.setText(name);

        mSurname = (TextView) findViewById(R.id.textView_user_profile_surname);
        mSurname.setText(surname);

        mEmail = (TextView) findViewById(R.id.textView_user_profile_email);
        mEmail.setText(email);

        mAdress = (TextView) findViewById(R.id.textView_user_profile_address);
        mAdress.setText(adress);

        mCity = (TextView) findViewById(R.id.textView_user_profile_city);
        mCity.setText(city);

        mUserCoupons = (Button) findViewById(R.id.buttonUserCoupons);
        mUserCoupons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent userCoupons = new Intent(UserProfileActivity.this, UserCouponsActivity.class);
                userCoupons.putExtra("userProfileActivity_user_id", id);
                startActivity(userCoupons);
            }
        });



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
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
            MainActivity.logout(this);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
