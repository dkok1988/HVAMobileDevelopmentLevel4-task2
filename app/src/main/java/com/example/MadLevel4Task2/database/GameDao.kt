package com.example.MadLevel4Task2.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.MadLevel4Task2.model.Game

@Dao
interface GameDao {
    @Query("SELECT * FROM gameTable")
    suspend fun getAllGames() : List<Game>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGame(game: Game)

    @Query("DELETE FROM gameTable")
    suspend fun deleteAllGames()

    @Query("SELECT COUNT(gameResult) FROM gameTable WHERE gameResult=2")
    suspend fun getWinCount() : Int

    @Query("SELECT COUNT(gameResult) FROM gameTable WHERE gameResult=1")
    suspend fun getDrawCount() : Int

    @Query("SELECT COUNT(gameResult) FROM gameTable WHERE gameResult=0")
    suspend fun getLossCount() : Int
}