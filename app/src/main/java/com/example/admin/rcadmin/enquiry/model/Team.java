package com.example.admin.rcadmin.enquiry.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Nikam on 24/02/2018.
 */

public class Team implements Parcelable {

    private String id;
    private String kechain_id;
    private String technitionid;
    private String customerid;
    private String starttime;
    private String endtime;
    private String addedby_id;
    private String name;
    private String mobile;
    private String dob;
    private String gender;
    private String address;
    private String addedby;
    private String addeddate;
    private String villageid;


    public Team() {
    }

    public Team(String id, String kechain_id, String technitionid, String customerid, String starttime, String endtime, String addedby_id, String name, String mobile, String dob, String gender, String address, String addedby, String addeddate, String villageid) {
        this.id = id;
        this.kechain_id = kechain_id;
        this.technitionid = technitionid;
        this.customerid = customerid;
        this.starttime = starttime;
        this.endtime = endtime;
        this.addedby_id = addedby_id;
        this.name = name;
        this.mobile = mobile;
        this.dob = dob;
        this.gender = gender;
        this.address = address;
        this.addedby = addedby;
        this.addeddate = addeddate;
        this.villageid = villageid;
    }


    protected Team(Parcel in) {
        id = in.readString();
        kechain_id = in.readString();
        technitionid = in.readString();
        customerid = in.readString();
        starttime = in.readString();
        endtime = in.readString();
        addedby_id = in.readString();
        name = in.readString();
        mobile = in.readString();
        dob = in.readString();
        gender = in.readString();
        address = in.readString();
        addedby = in.readString();
        addeddate = in.readString();
        villageid = in.readString();
    }

    public static final Creator<Team> CREATOR = new Creator<Team>() {
        @Override
        public Team createFromParcel(Parcel in) {
            return new Team(in);
        }

        @Override
        public Team[] newArray(int size) {
            return new Team[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKechain_id() {
        return kechain_id;
    }

    public void setKechain_id(String kechain_id) {
        this.kechain_id = kechain_id;
    }

    public String getTechnitionid() {
        return technitionid;
    }

    public void setTechnitionid(String technitionid) {
        this.technitionid = technitionid;
    }

    public String getCustomerid() {
        return customerid;
    }

    public void setCustomerid(String customerid) {
        this.customerid = customerid;
    }

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public String getAddedby_id() {
        return addedby_id;
    }

    public void setAddedby_id(String addedby_id) {
        this.addedby_id = addedby_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddedby() {
        return addedby;
    }

    public void setAddedby(String addedby) {
        this.addedby = addedby;
    }

    public String getAddeddate() {
        return addeddate;
    }

    public void setAddeddate(String addeddate) {
        this.addeddate = addeddate;
    }

    public String getVillageid() {
        return villageid;
    }

    public void setVillageid(String villageid) {
        this.villageid = villageid;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(kechain_id);
        dest.writeString(technitionid);
        dest.writeString(customerid);
        dest.writeString(starttime);
        dest.writeString(endtime);
        dest.writeString(addedby_id);
        dest.writeString(name);
        dest.writeString(mobile);
        dest.writeString(dob);
        dest.writeString(gender);
        dest.writeString(address);
        dest.writeString(addedby);
        dest.writeString(addeddate);
        dest.writeString(villageid);
    }
}
