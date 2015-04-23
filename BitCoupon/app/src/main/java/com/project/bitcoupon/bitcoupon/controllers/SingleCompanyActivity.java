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

public class SingleCompanyActivity extends BaseActivity {

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



}
