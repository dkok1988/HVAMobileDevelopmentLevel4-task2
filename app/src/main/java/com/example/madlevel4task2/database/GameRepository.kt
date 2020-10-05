package com.example.madlevel4task2.database

import android.content.Context
import com.example.madlevel4task2.model.Game

class GameRepository(context: Context) {
    private val gameDao: GameDao

    init {
        val database = GameHistoryRoomDatabase.getDatabase(context)
        gameDao = database!!.gameDao()
    }

    suspend fun getAllGames(): List<Game> = gameDao.getAllGames()

    suspend fun inserGame(game: Game) = gameDao.insertGame(game)

    suspend fun  deleteAllGames() = gameDao.deleteAllGames()

    suspend fun getWinCount() = gameDao.getWinCount()

    suspend fun getDrawCount() = gameDao.getDrawCount()

    suspend fun getLossCount() = gameDao.getLossCount()
}