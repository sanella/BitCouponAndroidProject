package com.project.bitcoupon.bitcoupon.controllers;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.bitcoupon.bitcoupon.R;
import com.squareup.picasso.Picasso;

public class SingleCompanyActivity extends ActionBarActivity {

    private static final String TAG = "SingleCompanyActivity_Tag";

    private ImageView mLogo;
    private TextView mName;
    private TextView mEmail;
    private TextView mAddress;
    private TextView mCity;
    private TextView mContact;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_company);

        Intent it = getIntent();

        int id = it.getIntExtra("id", 0);
        String imgPath = it.getStringExtra("logo");
        String name = it.getStringExtra("name");
        String email = it.getStringExtra("email");
        String address = it.getStringExtra("address");
        String city = it.getStringExtra("city");
        String contact = it.getStringExtra("contact");


        mLogo = (ImageView) findViewById(R.id.imageview_singleCompanyImage);
        String img = getString(R.string.image_path) + imgPath;
        img = img.replaceAll("\\\\","/");
        Picasso.with(SingleCompanyActivity.this).load(img).into(mLogo);

        mName = (TextView) findViewById(R.id.textview_singleCompanyName);
        mName.setText(name);

        mEmail= (TextView)findViewById(R.id.textview_singleCompanyEmail);
        mEmail.setText(email);

        mAddress= (TextView)findViewById(R.id.textview_singleCompanyAdress);
        mAddress.setText(address);

        mCity= (TextView)findViewById(R.id.textview_singleCompanyCity);
        mCity.setText(city);

        mContact= (TextView)findViewById(R.id.textview_singleCompanyContact);
        mContact.setText(contact);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_single_company, menu);
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

        return super.onOptionsItemSelected(item);
    }

}
