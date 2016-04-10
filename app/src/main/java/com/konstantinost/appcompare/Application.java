package com.konstantinost.appcompare;

import android.graphics.drawable.Drawable;

/**
 * Created by Konstantinos on 9/12/2015.
 */
public class Application {
    private String phoneID;
    private String appID;
    private String name;
    private String pack;
    private String version;
    private Drawable icon;

    Application(String phoneID,String appID,String name,String pack,String version,Drawable icon){
        this.phoneID=phoneID;
        this.appID=appID;
        this.name=name;
        this.pack=pack;
        this.version=version;
        this.icon=icon;
    }

    public String getPhoneID() {
        return phoneID;
    }


    public String getAppID() {
        return appID;
    }



    public String getName() {
        return name;
    }


    public String getPack() {
        return pack;
    }


    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Drawable getIcon() {
        return icon;
    }

}
