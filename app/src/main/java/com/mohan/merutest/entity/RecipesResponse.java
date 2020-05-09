package com.mohan.merutest.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RecipesResponse {

    @SerializedName("count")
    @Expose()
    private int count;

    @SerializedName("recipes")
    @Expose()
    private List<RecipesData> arrayList;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<RecipesData> getArrayList() {
        return arrayList;
    }

    public void setArrayList(List<RecipesData> arrayList) {
        this.arrayList = arrayList;
    }
}
