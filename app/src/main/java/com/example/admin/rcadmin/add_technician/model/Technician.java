package com.example.admin.rcadmin.add_technician.model;


import android.os.Parcel;
import android.os.Parcelable;

public class Technician implements Parcelable {
    private String mobile;
    private String dob;
    private String gender;
    private String address;
    private String addedby;
    private String addeddate;
    private String villageid;
    private String tmobile;
    private String tech_id;
    private String techName;
    private String city_id;
    private String lang;
    private String lat;


    public Technician() {
    }

    public Technician(String tmobile,String techName, String tech_id, String mobile,
                      String dob, String gender, String address, String addedby,
                      String addeddate, String villageid, String city_id,
                      String lang,String lat)
    {
        this.tmobile = tmobile;
        this.techName = techName;
        this.tech_id = tech_id;
        this.mobile = mobile;
        this.dob = dob;
        this.gender = gender;
        this.address = address;
        this.addedby = addedby;
        this.addeddate = addeddate;
        this.villageid = villageid;
        this.city_id=city_id;
        this.lang=lang;
        this.lat=lat;
    }

    protected Technician(Parcel in) {
        mobile = in.readString();
        dob = in.readString();
        gender = in.readString();
        address = in.readString();
        addedby = in.readString();
        addeddate = in.readString();
        villageid = in.readString();
        tmobile = in.readString();
        tech_id = in.readString();
        techName = in.readString();
        city_id = in.readString();
        lang = in.readString();
        lat = in.readString();
    }

    public static final Creator<Technician> CREATOR = new Creator<Technician>() {
        @Override
        public Technician createFromParcel(Parcel in) {
            return new Technician(in);
        }

        @Override
        public Technician[] newArray(int size) {
            return new Technician[size];
        }
    };

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

    public String getTmobile() {
        return tmobile;
    }

    public void setTmobile(String tmobile) {
        this.tmobile = tmobile;
    }


    public String getCity_id() {
        return city_id;
    }

    public void setCity_id(String city_id) {
        this.city_id = city_id;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getTech_id() {
        return tech_id;
    }

    public void setTech_id(String tech_id) {
        this.tech_id = tech_id;
    }

    public String getTechName() {
        return techName;
    }

    public void setTechName(String techName) {
        this.techName = techName;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mobile);
        dest.writeString(dob);
        dest.writeString(gender);
        dest.writeString(address);
        dest.writeString(addedby);
        dest.writeString(addeddate);
        dest.writeString(villageid);
        dest.writeString(tmobile);
        dest.writeString(tech_id);
        dest.writeString(techName);
        dest.writeString(city_id);
        dest.writeString(lang);
        dest.writeString(lat);
    }
}
