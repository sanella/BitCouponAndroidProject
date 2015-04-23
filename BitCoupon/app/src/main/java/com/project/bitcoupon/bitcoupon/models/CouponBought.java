package com.project.bitcoupon.bitcoupon.models;

/**
 * Created by Sanela on 4/22/2015.
 */
public class CouponBought {


        private int mId;
        private String mCouponId;
        private String mName;
        private String mDescription;
        private String mPrice;
        private String mQuantity;
        private String mPicture;
        private String mTotalPrice;
        private String mTransactionDate;
        private String mPaymentId;
        private String mToken;

        public CouponBought( String couponId, String name, String description, String price, String quantity, String picture, String totalPrice, String transactionDate, String paymentId, String token) {
        mCouponId = couponId;
        mName = name;
        mDescription = description;
        mPrice = price;
        mQuantity = quantity;
        mPicture = picture;
        mTotalPrice = totalPrice;
        mTransactionDate = transactionDate;
        mPaymentId = paymentId;
        mToken = token;
    }



    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getCouponId() {
        return mCouponId;
    }

    public void setCouponId(String couponId) {
        mCouponId = couponId;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public String getPrice() {
        return mPrice;
    }

    public void setPrice(String price) {
        mPrice = price;
    }

    public String getQuantity() {
        return mQuantity;
    }

    public void setQuantity(String quantity) {
        mQuantity = quantity;
    }

    public String getPicture() {
        return mPicture;
    }

    public void setPicture(String picture) {
        mPicture = picture;
    }

    public String getTotalPrice() {
        return mTotalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        mTotalPrice = totalPrice;
    }

    public String getTransactionDate() {
        return mTransactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        mTransactionDate = transactionDate;
    }

    public String getPaymentId() {
        return mPaymentId;
    }

    public void setPaymentId(String paymentId) {
        mPaymentId = paymentId;
    }

    public String getToken() {
        return mToken;
    }

    public void setToken(String token) {
        mToken = token;
    }
}

