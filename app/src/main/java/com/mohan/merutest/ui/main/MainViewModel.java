package com.mohan.merutest.ui.main;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.mohan.merutest.entity.RecipesData;
import com.mohan.merutest.repo.RecipesRepository;

import java.util.List;

public class MainViewModel extends AndroidViewModel {
    // TODO: Implement the ViewModel

    private RecipesRepository mRepository;
    private LiveData<List<RecipesData>> listLiveData;
    private Application application;

    public MainViewModel(@NonNull Application application) {
        super(application);
        this.application = application;
    }

    public void init() {
        mRepository = new RecipesRepository(application);
        listLiveData = mRepository.getAllRecipes();
    }

    public LiveData<List<RecipesData>> getListLiveData() {
        return listLiveData;
    }

    void insert(RecipesData recipesData) {
        mRepository.insert(recipesData);
    }

}
