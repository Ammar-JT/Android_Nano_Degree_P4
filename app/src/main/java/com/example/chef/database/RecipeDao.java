package com.example.chef.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface RecipeDao {
    @Query("SELECT * FROM favorite")
    LiveData<List<Favorite>> loadAllFavorites();

    @Insert
    void insertFavorite(Favorite favorite);

    @Delete
    void deleteFavorite(Favorite favorite);

    @Query("SELECT * FROM favorite WHERE id = :id")
    LiveData<Favorite> loadFavoriteById(int id);

    @Query("SELECT * FROM favorite ORDER BY id ASC LIMIT 1")
    Favorite loadFirstFavorite();


    @Query("Delete FROM favorite")
    void deleteAll();
}
