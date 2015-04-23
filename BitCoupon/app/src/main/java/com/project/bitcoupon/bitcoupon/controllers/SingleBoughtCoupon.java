package com.project.bitcoupon.bitcoupon.controllers;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.bitcoupon.bitcoupon.R;
import com.squareup.picasso.Picasso;

public class SingleBoughtCoupon extends ActionBarActivity {

    private static final String TAG = "SingleBought_Tag";

    private ImageView mPicture;
    private TextView mName;
    private TextView mTransactonId;
    private TextView mDescription;
    private TextView mPrice;
    private TextView mQuantity;
    private TextView mTotalPrice;
    private TextView mTrasactionDate;
    private TextView mPayemantId;
    private TextView mToken;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_bought_coupon);
        Intent it = getIntent();
        String transactionId = it.getStringExtra("transactionId");
        String couponId = it.getStringExtra("couponId");
        String name = it.getStringExtra("name");
        String description = it.getStringExtra("descripton");
        String imgPath = it.getStringExtra("picture");
        String price = it.getStringExtra("price");
        String quantity = it.getStringExtra("quantity");
        String totalPrice = it.getStringExtra("totalPrice");
        String transactionDate = it.getStringExtra("transactionDate");
        String paymentId = it.getStringExtra("paymentId");
        String token = it.getStringExtra("token");


        //For picture
        mPicture = (ImageView) findViewById(R.id.imageView_single_bought_CouponIMG);
        Log.d(TAG, "" + mPicture.isShown());
        String img = getString(R.string.image_path) + imgPath;
        img = img.replaceAll("\\\\","/");
        Picasso.with(this).load(img).into(mPicture);
        mName = (TextView) findViewById(R.id.textview_single_BoughtCouponName);
        mName.setText(name);
        mDescription = (TextView) findViewById(R.id.textview_single_BoughtCouponDescription);
        mDescription.setText(description);

        mTransactonId = (TextView) findViewById(R.id.textview_transaction);
        mTransactonId.setText("Transaction ID: " + transactionId);

        mQuantity = (TextView) findViewById(R.id.textview_quantity);
        mQuantity.setText("Quantity: " +quantity);

        mTotalPrice = (TextView) findViewById(R.id.textview_total_price);
        mTotalPrice.setText("Total price: " +totalPrice + getString(R.string.currency) );

        mPayemantId = (TextView) findViewById(R.id.textview_paymant_id);
        mTotalPrice.setText("Payemant ID: " +paymentId);

        mTrasactionDate = (TextView) findViewById(R.id.textview_transaction_date);
        mTrasactionDate.setText("Transaction Date: " + transactionDate);

        mPrice = (TextView) findViewById(R.id.textview_single_BoughtCouponPrice);
        mPrice.setText("Price: " + price + getString(R.string.currency));


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
