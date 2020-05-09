package com.mohan.merutest.entitydao;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.mohan.merutest.entity.RecipesData;

import java.util.List;

@Dao
public abstract class RecpiesDAO {

    @Query("SELECT * from recipes")
    public abstract LiveData<List<RecipesData>> getRecipes();

    @Insert
    public abstract void insert(RecipesData recipesData);

    @Query("DELETE FROM recipes")
    public abstract void deleteAll();
}
