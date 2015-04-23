package com.project.bitcoupon.bitcoupon.models;

/**
 * Created by Sanela on 4/22/2015.
 */
public class Coupon {

    private int mId;
    private String mName;
    private double mPrice;
    private String mDateCreated;
    private String mDateExpire;
    private String mPicture;
    private String mCategory;
    private String mDescription;
    private String mRemark;
    private int mMinOrder;
    private int mMaxOrder;
    private String mUsage;
    private String mSeller;

    public Coupon(int id, String name, double price, String dateCreated,
                  String dateExpire, String picture, String category,
                  String description, String remark, int minOrder,
                  int maxOrder, String usage, String seller) {

        mId = id;
        mName = name;
        mPrice = price;
        mDateCreated = dateCreated;
        mDateExpire = dateExpire;
        mPicture = picture;
        mCategory = category;
        mDescription = description;
        mRemark = remark;
        mMinOrder = minOrder;
        mMaxOrder = maxOrder;
        mUsage = usage;
        mSeller = seller;
    }

    public Coupon(int id, String name, double price,  String picture) {

        mId = id;
        mName = name;
        mPrice = price;
        mPicture = picture;

    }

    @Override
    public String toString() {
        return "Coupon{" +
                "mName='" + mName + '\'' +
                ", mPric='" + mPrice + '\'' +
                ", mDateCreated=" + mDateCreated +
                ", mDateExpire=" + mDateExpire +
                ", mPicture='" + mPicture + '\'' +
                ", mCategory='" + mCategory + '\'' +
                ", mDescription='" + mDescription + '\'' +
                ", mRemark='" + mRemark + '\'' +
                ", mMinOrder=" + mMinOrder +
                ", mMaxOrder=" + mMaxOrder +
                ", mUsage=" + mUsage +
                ", mSeller='" + mSeller + '\'' +
                '}';
    }


    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getSeller() {
        return mSeller;
    }

    public void setSeller(String seller) {
        mSeller = seller;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public double getPrice() {
        return mPrice;
    }

    public void setPrice(double price) {
        mPrice = price;
    }

    public String getPicture() {
        return mPicture;
    }

    public void setPicture(String picture) {
        mPicture = picture;
    }

    public String getCategory() {
        return mCategory;
    }

    public void setCategory(String category) {
        mCategory = category;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public String getRemark() {
        return mRemark;
    }

    public void setRemark(String remark) {
        mRemark = remark;
    }

    public int getMinOrder() {
        return mMinOrder;
    }

    public void setMinOrder(int minOrder) {
        mMinOrder = minOrder;
    }

    public int getMaxOrder() {
        return mMaxOrder;
    }

    public void setMaxOrder(int maxOrder) {
        mMaxOrder = maxOrder;
    }

    public String getDateCreated() {
        return mDateCreated;
    }

    public void setDateCreated(String dateCreated) {
        mDateCreated = dateCreated;
    }

    public String getDateExpire() {
        return mDateExpire;
    }

    public void setDateExpire(String dateExpire) {
        mDateExpire = dateExpire;
    }

    public String getUsage() {
        return mUsage;
    }

    public void setUsage(String usage) {
        mUsage = usage;
    }
}
