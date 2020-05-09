package com.mohan.merutest.roomdb;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import com.mohan.merutest.Constant;
import com.mohan.merutest.entity.RecipesData;
import com.mohan.merutest.entity.RecipesResponse;
import com.mohan.merutest.entitydao.RecpiesDAO;
import com.mohan.merutest.network.GetDataRetro;
import com.mohan.merutest.network.RetrofitClientInstance;

import retrofit2.Call;
import retrofit2.Response;

@Database(entities = {RecipesData.class}, version = 1, exportSchema = false)
public abstract class UserRoomDatabase extends RoomDatabase {

    public abstract RecpiesDAO recpiesDAO();

    private static volatile UserRoomDatabase INSTANCE;

    public static UserRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (UserRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            UserRoomDatabase.class, "recipes_database")
                            .fallbackToDestructiveMigration()
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }


    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            // If you want to keep the data through app restarts,
            // comment out the following line.
            callApi(INSTANCE);
        }
    };

    private static void callApi(UserRoomDatabase INSTANCE) {
        final RecpiesDAO recpiesDAO = INSTANCE.recpiesDAO();


        /*Create handle for the RetrofitInstance interface*/
        GetDataRetro service = RetrofitClientInstance.getRetrofitInstance().create(GetDataRetro.class);

        Call<RecipesResponse> call = service.getAllRecipes(Constant.q);
        call.enqueue(new retrofit2.Callback<RecipesResponse>() {
            @Override
            public void onResponse(@NonNull Call<RecipesResponse> call, @NonNull final Response<RecipesResponse> response) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        recpiesDAO.deleteAll();

                        for (int i = 0; i < response.body().getArrayList().size(); i++) {
                            recpiesDAO.insert(response.body().getArrayList().get(i));
                        }
                    }
                }).start();
            }

            @Override
            public void onFailure(@NonNull Call<RecipesResponse> call, @NonNull Throwable t) {
                Log.e("mohan", t.getMessage());
            }
        });
    }

    @NonNull
    @Override
    protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration config) {
        return null;
    }
}