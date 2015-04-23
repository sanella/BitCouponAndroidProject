package com.project.bitcoupon.bitcoupon.controllers;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.project.bitcoupon.bitcoupon.R;
import com.project.bitcoupon.bitcoupon.models.Coupon;
import com.project.bitcoupon.bitcoupon.models.CouponBought;
import com.project.bitcoupon.bitcoupon.service.ServiceRequest;
import com.project.bitcoupon.bitcoupon.singletons.CouponFeed;
import com.project.bitcoupon.bitcoupon.singletons.UserCouponsFeed;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class UserCouponsActivity extends ActionBarActivity {

    private static final String TAG = "UserCouponActivity_Tag";

    private SharedPreferences mSharedPreferences;
    private ListView mCouponList;
    private EditText mFilter;
    private CouponAdapter mAdapter;
    public static final String MyPREFERENCES = "MyPrefs";
    static ArrayList<CouponBought> coupons = new ArrayList<CouponBought>();
    private  String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_coupons);

        Intent userCoupons = getIntent();
        userId = userCoupons.getStringExtra("userProfileActivity_user_id");

        Log.d(TAG, "userId: " + userId);

        String url = getString(R.string.service_user_coupons);
        JSONObject userCouponsJS = new JSONObject();
        try {
            userCouponsJS.put("userId", userId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String json = userCouponsJS.toString();
        Log.d(TAG, json);
        //ServiceRequest.post(url, json,  getCoupon());

        mCouponList = (ListView) findViewById(R.id.list_view_user_coupons);
        UserCouponsFeed couponFeed =UserCouponsFeed.getInstance();

        //if oriantation mobile is chabged - don't take a new list
        if (couponFeed.getFeed().size() == 0) {
            couponFeed.postFeed(getString(R.string.service_user_coupons), json);
            coupons = couponFeed.getFeed();
        }

        mAdapter = new CouponAdapter(this, coupons);
        mCouponList.setAdapter(mAdapter);

        mCouponList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CouponBought clicked = coupons.get(position);
                //int userId = UserData.getInstance().getId();
                String couponId = clicked.getCouponId();

                String url = getString(R.string.service_single_user_coupon);
                JSONObject clickedCoupon = new JSONObject();
                try {
                    clickedCoupon.put("couponId", couponId);
                    clickedCoupon.put("userId", userId);
                    Log.d(TAG, "JSON ID " +userId);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                String json = clickedCoupon.toString();
                Log.d(TAG, json);
                ServiceRequest.post(url, json, getCoupon());
            }
        });


        mFilter = (EditText) findViewById(R.id.edit_text_filter);
        mFilter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ((ArrayAdapter<CouponBought>) mCouponList.getAdapter()).getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    private Callback getCoupon() {
        return new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                makeToast(R.string.toast_try_again);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                String responseJson = response.body().string();
                Log.d(TAG, responseJson);

                try {
                    JSONObject coupon = new JSONObject(responseJson);
                    Intent goToCoupon = new Intent(UserCouponsActivity.this, SingleBoughtCoupon.class);
                    goToCoupon.putExtra("transactionId", coupon.getString("transactionId"));
                    goToCoupon.putExtra("couponId", coupon.getString("couponId"));
                    goToCoupon.putExtra("name", coupon.getString("name"));
                    goToCoupon.putExtra("description", coupon.getString("description"));
                    goToCoupon.putExtra("price", coupon.getString("price"));
                    goToCoupon.putExtra("quantity", coupon.getString("quantity"));
                    goToCoupon.putExtra("picture", coupon.getString("picture"));
                    goToCoupon.putExtra("totalPrice", coupon.getString("totalPrice"));
                    goToCoupon.putExtra("transactionDate", coupon.getString("transactionDate"));
                    goToCoupon.putExtra("paymentId", coupon.getString("paymentId"));
                    goToCoupon.putExtra("token", coupon.getString("token"));
                    startActivity(goToCoupon);

                } catch (JSONException e) {
                    makeToast(R.string.toast_try_again);
                    e.printStackTrace();
                }
            }
        };
    }

    private void makeToast(final int messageId) {

        new Handler(Looper.getMainLooper())
                .post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(UserCouponsActivity.this,
                                messageId,
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }



    private  class CouponAdapter extends ArrayAdapter<CouponBought> {

        private final Context context;
        private ArrayList<CouponBought> origin;
        private ArrayList<CouponBought> mListToShow;
        private Filter mFilter;

        public CouponAdapter(Context context, ArrayList<CouponBought> origin) {
            super(context, R.layout.row, origin);
            this.context = context;
            this.mListToShow = origin;
        }

        @Override
        public Filter getFilter() {
            if (mFilter == null) {
                mFilter = new CouponsFilter();
            }
            return mFilter;
        }

        @Override
        public int getCount() {
            return mListToShow.size();
        }

        @Override
        public CouponBought getItem(int position) {
            return mListToShow.get(position);
        }


        private class CouponsFilter extends Filter {

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                FilterResults results = new FilterResults();

                if (constraint == null || constraint.length() == 0) {
                    ArrayList<CouponBought> origin = UserCouponsFeed.getInstance().getFeed();
                    results.values = origin;
                    results.count = origin.size();
                } else {

                    String searchString = constraint.toString().toLowerCase();

                    ArrayList<CouponBought> filteredList = new ArrayList<CouponBought>();
                    for (int i = 0; i < mListToShow.size(); i++) {
                        CouponBought c = mListToShow.get(i);
                        String couponTitle = c.getName().toLowerCase();

                        if (couponTitle.contains(searchString)) {
                            filteredList.add(c);
                        }
                    }
                    results.values = filteredList;
                    results.count = filteredList.size();
                }

                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                mListToShow = (ArrayList<CouponBought>) results.values;
                notifyDataSetChanged();
            }
        }




        @Override
        public View getView(int position, View convertView, ViewGroup viewGroup) {

            CouponBought current = getItem(position);
            if (convertView == null) {
                convertView = UserCouponsActivity.this.getLayoutInflater()
                        .inflate(R.layout.row, null);
            }

            TextView couponName = (TextView) convertView.findViewById(R.id.textview_name);
            couponName.setText(current.getName());

            TextView couponPrice = (TextView) convertView.findViewById(R.id.textview_price);

            couponPrice.setText("" + current.getTotalPrice() + getString(R.string.currency));
            ImageView couponImage = (ImageView) convertView.findViewById(R.id.imageview_image);
            String img = getString(R.string.image_path) + current.getPicture();
            img = img.replaceAll("\\\\", "/");
            Log.d(TAG, "IMG"+ img);
            Picasso.with(getContext()).load(img).into(couponImage);
            return convertView;
        }
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
