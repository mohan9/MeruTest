package com.mohan.merutest.repo;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.mohan.merutest.entity.RecipesData;
import com.mohan.merutest.entitydao.RecpiesDAO;
import com.mohan.merutest.roomdb.UserRoomDatabase;

import java.util.List;


public class RecipesRepository {

    private RecpiesDAO recpiesDAO;
    private LiveData<List<RecipesData>> listLiveData;

    public RecipesRepository(Application application) {
        UserRoomDatabase db = UserRoomDatabase.getDatabase(application);
        recpiesDAO = db.recpiesDAO();
        listLiveData = recpiesDAO.getRecipes();
    }

    public LiveData<List<RecipesData>> getAllRecipes() {
        return listLiveData;
    }

    public void insert(RecipesData recipesData) {
        new insertAsyncTask(recpiesDAO).execute(recipesData);
    }

    private static class insertAsyncTask extends AsyncTask<RecipesData, Void, Void> {

        private RecpiesDAO mAsyncTaskDao;

        insertAsyncTask(RecpiesDAO dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final RecipesData... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }
}
