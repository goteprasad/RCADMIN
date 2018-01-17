package com.example.admin.rcadmin;


public class ModelConstructionTeam {
    String name;
    int image;

    public ModelConstructionTeam(String name, int image) {
        this.name = name;
        this.image=image;
    }
    public String getName() {
        return name;
    }
    public int getImage() {
        return image;
    }
}
