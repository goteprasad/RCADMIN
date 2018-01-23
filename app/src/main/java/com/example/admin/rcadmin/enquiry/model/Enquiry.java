package com.example.admin.rcadmin.enquiry.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.admin.rcadmin.enquiry.listener.Enquiry_Listener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nikam on 19/01/2018.
 */

public class Enquiry implements Parcelable{
    private String id;
    private String kitchen_id;
    private String roofType;
    private String housetype;
    private String hieght;
    private String longitude;
    private String latitude;
    private String geoaddress;
    private String place_image;
    private String addeddate;
    private  String costofculha;
    private  String state;
    private  String step1image;
    private  String step2image;
    private  String updatedate;
    private  String addedbyid;
    private  String stime;
    private  String endtime;
    private String adminactiondate;
    private String comment;
    private String customerid;
    private  String adharid;
    private String name;
    private String address;
    private String age;
    private String gender;
    private  String mobile;
    private String villageid;
    private String citi_id;
    private  String added_date;
    private String uploaddate;
    private String updateDate;
    private String addedby_id;
    private String imagepath;


public static final String NEW="N",DENIED="D",MATERIALSEND="S",CONSTRUCTION_COMPLITED="C",FIRE="F";

    public Enquiry() {
    }

    public Enquiry(Parcel in) {
        id = in.readString();
        kitchen_id = in.readString();
        roofType = in.readString();
        housetype = in.readString();
        hieght = in.readString();
        longitude = in.readString();
        latitude = in.readString();
        geoaddress = in.readString();
        place_image = in.readString();
        addeddate = in.readString();
        costofculha = in.readString();
        state = in.readString();
        step1image = in.readString();
        step2image = in.readString();
        updatedate = in.readString();
        addedbyid = in.readString();
        stime = in.readString();
        endtime = in.readString();
        adminactiondate = in.readString();
        comment = in.readString();
        customerid = in.readString();
        adharid = in.readString();
        name = in.readString();
        address = in.readString();
        age = in.readString();
        gender = in.readString();
        mobile = in.readString();
        villageid = in.readString();
        citi_id = in.readString();
        added_date = in.readString();
        uploaddate = in.readString();
        updateDate = in.readString();
        addedby_id = in.readString();
        imagepath = in.readString();
    }

    public static final Creator<Enquiry> CREATOR = new Creator<Enquiry>() {
        @Override
        public Enquiry createFromParcel(Parcel in) {
            return new Enquiry(in);
        }

        @Override
        public Enquiry[] newArray(int size) {
            return new Enquiry[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKitchen_id() {
        return kitchen_id;
    }

    public void setKitchen_id(String kitchen_id) {
        this.kitchen_id = kitchen_id;
    }

    public String getRoofType() {
        return roofType;
    }

    public void setRoofType(String roofType) {
        this.roofType = roofType;
    }

    public String getHousetype() {
        return housetype;
    }

    public void setHousetype(String housetype) {
        this.housetype = housetype;
    }

    public String getHieght() {
        return hieght;
    }

    public void setHieght(String hieght) {
        this.hieght = hieght;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getGeoaddress() {
        return geoaddress;
    }

    public void setGeoaddress(String geoaddress) {
        this.geoaddress = geoaddress;
    }

    public String getPlace_image() {
        return place_image;
    }

    public void setPlace_image(String place_image) {
        this.place_image = place_image;
    }

    public String getAddeddate() {
        return addeddate;
    }

    public void setAddeddate(String addeddate) {
        this.addeddate = addeddate;
    }

    public String getCostofculha() {
        return costofculha;
    }

    public void setCostofculha(String costofculha) {
        this.costofculha = costofculha;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getStep1image() {
        return step1image;
    }

    public void setStep1image(String step1image) {
        this.step1image = step1image;
    }

    public String getStep2image() {
        return step2image;
    }

    public void setStep2image(String step2image) {
        this.step2image = step2image;
    }

    public String getUpdatedate() {
        return updatedate;
    }

    public void setUpdatedate(String updatedate) {
        this.updatedate = updatedate;
    }

    public String getAddedbyid() {
        return addedbyid;
    }

    public void setAddedbyid(String addedbyid) {
        this.addedbyid = addedbyid;
    }

    public String getStime() {
        return stime;
    }

    public void setStime(String stime) {
        this.stime = stime;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public String getAdminactiondate() {
        return adminactiondate;
    }

    public void setAdminactiondate(String adminactiondate) {
        this.adminactiondate = adminactiondate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getCustomerid() {
        return customerid;
    }

    public void setCustomerid(String customerid) {
        this.customerid = customerid;
    }

    public String getAdharid() {
        return adharid;
    }

    public void setAdharid(String adharid) {
        this.adharid = adharid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getVillageid() {
        return villageid;
    }

    public void setVillageid(String villageid) {
        this.villageid = villageid;
    }

    public String getCiti_id() {
        return citi_id;
    }

    public void setCiti_id(String citi_id) {
        this.citi_id = citi_id;
    }

    public String getUploaddate() {
        return uploaddate;
    }

    public void setUploaddate(String uploaddate) {
        this.uploaddate = uploaddate;
    }

    public String getImagepath() {
        return imagepath;
    }

    public void setImagepath(String imagepath) {
        this.imagepath = imagepath;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(kitchen_id);
        dest.writeString(roofType);
        dest.writeString(housetype);
        dest.writeString(hieght);
        dest.writeString(longitude);
        dest.writeString(latitude);
        dest.writeString(geoaddress);
        dest.writeString(place_image);
        dest.writeString(addeddate);
        dest.writeString(costofculha);
        dest.writeString(state);
        dest.writeString(step1image);
        dest.writeString(step2image);
        dest.writeString(updatedate);
        dest.writeString(addedbyid);
        dest.writeString(stime);
        dest.writeString(endtime);
        dest.writeString(adminactiondate);
        dest.writeString(comment);
        dest.writeString(customerid);
        dest.writeString(adharid);
        dest.writeString(name);
        dest.writeString(address);
        dest.writeString(age);
        dest.writeString(gender);
        dest.writeString(mobile);
        dest.writeString(villageid);
        dest.writeString(citi_id);
        dest.writeString(added_date);
        dest.writeString(uploaddate);
        dest.writeString(updateDate);
        dest.writeString(addedby_id);
        dest.writeString(imagepath);
    }
}
