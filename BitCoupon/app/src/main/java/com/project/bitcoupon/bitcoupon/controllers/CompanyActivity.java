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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.project.bitcoupon.bitcoupon.R;
import com.project.bitcoupon.bitcoupon.models.Company;
import com.project.bitcoupon.bitcoupon.models.Coupon;
import com.project.bitcoupon.bitcoupon.service.ServiceRequest;
import com.project.bitcoupon.bitcoupon.singletons.CompanyFeed;
import com.project.bitcoupon.bitcoupon.singletons.CouponFeed;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class CompanyActivity extends ActionBarActivity {private ListView mCompanyList;

    private static final String TAG = "CompanyActivity_Tag";
    private EditText mFilter;
    private CompanyAdapter mAdapter;
    private Button mCoupon;
    public static final String MyPREFERENCES = "MyPrefs" ;
    static ArrayList<Company> companies = new ArrayList<Company>();
    private SharedPreferences mSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company);

        mCompanyList= (ListView)findViewById(R.id.list_view_companies);
        CompanyFeed companyFeed = CompanyFeed.getInstance();

        //if mobile orientation is changed don't take a new list
        if(companyFeed.getFeed().size() == 0){
            companyFeed.getFeed(getString(R.string.service_companies));
            companies = companyFeed.getFeed();
        }

        mAdapter = new CompanyAdapter(CompanyActivity.this, companies);

        mCompanyList.setAdapter(mAdapter);
        mCompanyList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Company clicked = companies.get(position);
                // int companyId = clicked.getmId();
                String url = getString(R.string.service_single_company);
                JSONObject clickedCompany = new JSONObject();
                try {
                    //    clickedCompany.put("id", Integer.toString(companyId));
                    clickedCompany.put("email", clicked.getmEmail());
                    Log.d(TAG, "json email for post");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                String json = clickedCompany.toString();
                Log.d(TAG, "Json: " + json);
                ServiceRequest.post(url, json, getCompany());
            }
        });

        mCompanyList.setAdapter(mAdapter);

        mFilter = (EditText)findViewById(R.id.edit_text_filter);
        mFilter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ( (ArrayAdapter<Company>)mCompanyList.getAdapter()).getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mCoupon = (Button)findViewById(R.id.buttonCoupon);
        mCoupon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CompanyActivity.this, CouponActivity.class);
                startActivity(intent);
            }
        });
    }

    private Callback getCompany() {
        return new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                makeToast(R.string.toast_try_again);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                String responseJson = response.body().string();

                try {
                    JSONObject company = new JSONObject(responseJson);
                    Intent goToCompany = new Intent(CompanyActivity.this, SingleCompanyActivity.class);
                    goToCompany.putExtra("id", company.getInt("id") );
                    goToCompany.putExtra("name", company.getString("name"));
                    goToCompany.putExtra("email", company.getString("email"));
                    goToCompany.putExtra("address", company.getString("address"));
                    goToCompany.putExtra("city", company.getString("city"));
                    goToCompany.putExtra("contact", company.getString("contact"));
                    goToCompany.putExtra("logo", company.getString("logo"));
                    startActivity(goToCompany);
                } catch (JSONException e) {
                    makeToast(R.string.toast_try_again);
                    e.printStackTrace();
                }
            }
        };
    }

    private void makeToast(final int messageId){

        new Handler(Looper.getMainLooper())
                .post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(CompanyActivity.this,
                                messageId,
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_coupon, menu);
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
            //TODO
            SharedPreferences sharedpreferences = getSharedPreferences
                    (CompanyActivity.MyPREFERENCES, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.clear();
            editor.commit();
            moveTaskToBack(true);
            CompanyActivity.this.finish();

            return true;
        }

        if (id == R.id.action_profile) {
            //TODO
            mSharedPreferences = getPreferences(Context.MODE_PRIVATE);
            String email = mSharedPreferences.getString(
                    getString(R.string.key_user_email),
                    null
            );

            if(email != null ){
                String url = getString(R.string.service_user_profile);
                JSONObject profile= new JSONObject();
                try {
                    profile.put("email", email);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                String json = profile.toString();
                Log.d(TAG, "JSON :" + json);
                ServiceRequest.post(url, json, getProfile());
            }
            return true;
        }


        return super.onOptionsItemSelected(item);
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
                    Log.d(TAG, "profile");
                    Intent goToProfile = new Intent(CompanyActivity.this, UserProfileActivity.class);
                    goToProfile.putExtra("id", profile.getString("id"));
                    goToProfile.putExtra("name", profile.getString("name"));
                    goToProfile.putExtra("surname", profile.getString("surname"));
                    goToProfile.putExtra("email",profile.getString("email"));
                    goToProfile.putExtra("address", profile.getString("address"));
                    goToProfile.putExtra("city",profile.getString("city"));
                    String pic = profile.getString("picture");
                    Log.d("TAG", pic);
                    goToProfile.putExtra("picture", profile.getString("picture"));
                    startActivity(goToProfile);

                } catch (JSONException e) {
                    makeToast(R.string.toast_try_again);
                    e.printStackTrace();
                }
            }
        };
    }

   /* private  class CompanyAdapter extends ArrayAdapter<Company>{
        public CompanyAdapter(ArrayList<Company> companies){
            super(CompanyActivity.this, 0,  companies );
        }

        @Override
        public View getView(int position, View convertView, ViewGroup viewGroup) {

            Company current = getItem(position);
            if (convertView == null) {
                convertView = CompanyActivity.this.getLayoutInflater()
                        .inflate(R.layout.row, null);
            }

            TextView companyName = (TextView) convertView.findViewById(R.id.textview_name);
            companyName.setText(current.getmName());

            TextView companyEmail = (TextView) convertView.findViewById(R.id.textview_price);
            companyEmail.setText(current.getmEmail());


            ImageView companyImage = (ImageView) convertView.findViewById(R.id.imageview_image);
            String img =getString(R.string.image_path) + current.getmLogo();
            img = img.replaceAll("\\\\","/");
            Log.d(TAG, "Image:" + img);
            Picasso.with(getContext()).load(img).into(companyImage);
            return convertView;

        }
    }*/


    private  class CompanyAdapter extends ArrayAdapter<Company> {

        private final Context context;
        private ArrayList<Company> origin;
        private ArrayList<Company> mListToShow;
        private Filter mFilter;

        public CompanyAdapter(Context context, ArrayList<Company> origin) {
            super(context, R.layout.row, origin);
            this.context = context;
            this.mListToShow = origin;
        }

        @Override
        public Filter getFilter() {
            if (mFilter == null) {
                mFilter = new CompaniesFilter();
            }
            return mFilter;
        }

        @Override
        public int getCount() {
            return mListToShow.size();
        }

        @Override
        public Company getItem(int position) {
            return mListToShow.get(position);
        }


        private class CompaniesFilter extends Filter {

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                FilterResults results = new FilterResults();

                if (constraint == null || constraint.length() == 0) {
                    ArrayList<Company> origin = CompanyFeed.getInstance().getFeed();
                    results.values = origin;
                    results.count = origin.size();
                } else {

                    String searchString = constraint.toString().toLowerCase();

                    ArrayList<Company> filteredList = new ArrayList<Company>();
                    for (int i = 0; i < mListToShow.size(); i++) {
                        Company c = mListToShow.get(i);
                        String postTitle = c.getmName().toLowerCase();

                        if (postTitle.contains(searchString)) {
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
                mListToShow = (ArrayList<Company>) results.values;
                notifyDataSetChanged();
            }
        }




        @Override
        public View getView(int position, View convertView, ViewGroup viewGroup) {

            Company current = getItem(position);
            if (convertView == null) {
                convertView = CompanyActivity.this.getLayoutInflater()
                        .inflate(R.layout.row, null);
            }

            TextView companyName = (TextView) convertView.findViewById(R.id.textview_name);
            companyName.setText(current.getmName());

            TextView companyEmail = (TextView) convertView.findViewById(R.id.textview_price);
            companyEmail.setText(current.getmEmail());


            ImageView companyImage = (ImageView) convertView.findViewById(R.id.imageview_image);
            String img =getString(R.string.image_path) + current.getmLogo();
            img = img.replaceAll("\\\\","/");
            Log.d(TAG, "Image:" + img);
            Picasso.with(getContext()).load(img).into(companyImage);
            return convertView;

        }
    }



}
