package com.project.bitcoupon.bitcoupon.controllers;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.bitcoupon.bitcoupon.R;
import com.squareup.picasso.Picasso;

public class SingleCouponActivity extends ActionBarActivity {

    private static final String TAG = "SingleCouponActivity_Tag";

    private ImageView mPicture;
    private TextView mName;
    private TextView mDescription;
    private TextView mPrice;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_coupon);

        Intent it = getIntent();

        int id = it.getIntExtra("CouponId", 0);
        String imgPath = it.getStringExtra("picture");
        String name = it.getStringExtra("name");
        String description = it.getStringExtra("description");
        String price = it.getStringExtra("price");

        //For picture
        mPicture = (ImageView) findViewById(R.id.imageview_singleCouponImage);
        String img = getString(R.string.image_path) + imgPath;
        img = img.replaceAll("\\\\","/");
        Picasso.with(SingleCouponActivity.this).load(img).into(mPicture);

        mName = (TextView) findViewById(R.id.textview_singleCouponName);
        mName.setText(name);

        mDescription = (TextView) findViewById(R.id.textview_singleCouponDescription);
        mDescription.setText(description);

        mPrice = (TextView) findViewById(R.id.textview_singleCouponPrice);
        mPrice.setText("Price: " + price + getString(R.string.currency));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_single_coupon, menu);
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

            SharedPreferences sharedpreferences = getSharedPreferences
                    (CouponActivity.MyPREFERENCES, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.clear();
            editor.commit();
            moveTaskToBack(true);
            SingleCouponActivity.this.finish();

        }
        return super.onOptionsItemSelected(item);
    }
}
