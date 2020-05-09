package com.mohan.merutest.network;

import com.mohan.merutest.entity.RecipesResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GetDataRetro {

    @GET("api/search")
    Call<RecipesResponse> getAllRecipes(@Query("q") String query);
}
