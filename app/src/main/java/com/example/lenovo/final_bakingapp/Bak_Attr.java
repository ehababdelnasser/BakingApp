package com.example.lenovo.final_bakingapp;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by Lenovo on 03/11/2017.
 */

public class Bak_Attr implements Parcelable {

    private String id;
    private String n;
    private String serv;
    private String step_Id;
    private String Descrip;
    private String descrip;
    private String video_U_R_L;
    private String image_U_R_L;
    private String ingr;



    public String getImage() {
        return img;
    }

    public void setImage(String image) {
        this.img = image;
    }

    private String img;
    private ArrayList<String> ingr_List;

    ArrayList<Bak_Attr> getStepsAttrList() {
        return steps_List;
    }

    void setStepsAttrList(ArrayList<Bak_Attr> stepsAttrList) {
        this.steps_List = stepsAttrList;
    }

    private ArrayList<Bak_Attr> steps_List;






    Bak_Attr(String product_id, String name, String servings, String image) {
        this.id = product_id;
        this.n = name;
        this.serv = servings;
        this.img = image;
    }



    private Bak_Attr(Parcel in) {
        id = in.readString();
        n = in.readString();
        serv = in.readString();
        ingr_List = in.createStringArrayList();

        ingr = in.readString();
        step_Id = in.readString();
        Descrip = in.readString();
        descrip = in.readString();
        video_U_R_L = in.readString();
        image_U_R_L = in.readString();
    }

    public Bak_Attr() {

    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(n);
        dest.writeString(serv);
        dest.writeStringList(ingr_List);
        dest.writeString(ingr);
        dest.writeString(step_Id);
        dest.writeString(Descrip);
        dest.writeString(descrip);
        dest.writeString(video_U_R_L);
        dest.writeString(image_U_R_L);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Bak_Attr> CREATOR = new Creator<Bak_Attr>() {
        @Override
        public Bak_Attr createFromParcel(Parcel in) {
            return new Bak_Attr(in);
        }

        @Override
        public Bak_Attr[] newArray(int size) {
            return new Bak_Attr[size];
        }
    };



    public String getName() {
        return n;
    }

    ArrayList<String> getIngrediantList() {
        return ingr_List;
    }

    void setIngrediantList(ArrayList<String> ingrediantList) {
        this.ingr_List = ingrediantList;
    }

    String getStepId() {
        return step_Id;
    }

    void setStepId(String stepId) {
        this.step_Id = stepId;
    }

    public String getShortDescription() {
        return Descrip;
    }

    void setShortDescription(String shortDescription) {
        this.Descrip = shortDescription;
    }

    public String getDescription() {
        return descrip;
    }

    void setDescription(String description) {
        this.descrip = description;
    }

    public String getVideoURL() {
        return video_U_R_L;
    }

    public void setVideoURL(String videoURL) {
        this.video_U_R_L = videoURL;
    }

    public String getThumbnailURL() {
        return image_U_R_L;
    }

    public void setThumbnailURL(String thumbnailURL) {
        this.image_U_R_L = thumbnailURL;
    }


}