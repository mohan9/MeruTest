package com.mohan.merutest.entity;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "recipes")
public class RecipesData implements Parcelable {

    @ColumnInfo(name = "image_url")
    private String image_url;
    @ColumnInfo(name = "social_rank")
    private double social_rank;
    @ColumnInfo(name = "_id")
    private String _id;
    @ColumnInfo(name = "publisher")
    private String publisher;
    @ColumnInfo(name = "source_url")
    private String source_url;
    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "recipe_id")
    private String recipe_id;
    @ColumnInfo(name = "publisher_url")
    private String publisher_url;
    @ColumnInfo(name = "title")
    private String title;

    public RecipesData(String image_url, double social_rank, String _id, String publisher,
                       String source_url, String recipe_id, String publisher_url, String title) {
        this.recipe_id = recipe_id;
        this.title = title;
        this.publisher = publisher;
        this.image_url = image_url;
        this.social_rank = social_rank;
        this.source_url = source_url;
        this._id = _id;
        this.publisher_url = publisher_url;
    }

    public RecipesData() {
    }

    protected RecipesData(Parcel in) {
        recipe_id = in.readString();
        title = in.readString();
        publisher = in.readString();
        image_url = in.readString();
        social_rank = in.readDouble();
        source_url = in.readString();
        _id = in.readString();
        publisher_url = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(recipe_id);
        dest.writeString(title);
        dest.writeString(publisher);
        dest.writeString(image_url);
        dest.writeDouble(social_rank);
        dest.writeString(source_url);
        dest.writeString(_id);
        dest.writeString(publisher_url);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<RecipesData> CREATOR = new Creator<RecipesData>() {
        @Override
        public RecipesData createFromParcel(Parcel in) {
            return new RecipesData(in);
        }

        @Override
        public RecipesData[] newArray(int size) {
            return new RecipesData[size];
        }
    };

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public double getSocial_rank() {
        return social_rank;
    }

    public void setSocial_rank(double social_rank) {
        this.social_rank = social_rank;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getSource_url() {
        return source_url;
    }

    public void setSource_url(String source_url) {
        this.source_url = source_url;
    }

    public String getRecipe_id() {
        return recipe_id;
    }

    public void setRecipe_id(String recipe_id) {
        this.recipe_id = recipe_id;
    }

    public String getPublisher_url() {
        return publisher_url;
    }

    public void setPublisher_url(String publisher_url) {
        this.publisher_url = publisher_url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "RecipesData{" +
                "recipe_id='" + recipe_id + '\'' +
                ", title='" + title + '\'' +
                ", publisher='" + publisher + '\'' +
                ", image_url='" + image_url + '\'' +
                ", social_rank=" + social_rank +
                ", source_url=" + source_url +
                ", _id=" + _id +
                ", publisher_url=" + publisher_url +
                '}';
    }
}