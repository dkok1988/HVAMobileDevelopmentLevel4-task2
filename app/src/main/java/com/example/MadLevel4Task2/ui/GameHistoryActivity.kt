package com.example.MadLevel4Task2.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.LinearLayout
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.MadLevel4Task2.R
import com.example.MadLevel4Task2.database.GameRepository
import com.example.MadLevel4Task2.model.Game
import kotlinx.android.synthetic.main.activity_game_history.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class GameHistoryActivity : AppCompatActivity() {

    private val gameHistory = arrayListOf<Game>()
    private val gameAdapter = GameAdapter(gameHistory)
    private val mainScope = CoroutineScope(Dispatchers.Main)
    private lateinit var gameRepository: GameRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_history)
        gameRepository = GameRepository(this)
        initViews()
    }

    private fun initViews() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.title_game_history)
        rvGameHistory.addItemDecoration(DividerItemDecoration(this, LinearLayout.VERTICAL))
        rvGameHistory.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL, false
        )
        rvGameHistory.adapter = gameAdapter
        getGameHistory()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_game_history, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item?.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            R.id.action_delete_game_history -> {
                deleteGameHistory()
                getGameHistory()
                true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    private fun deleteGameHistory() {
        mainScope.launch {
            withContext(Dispatchers.IO) {
                gameRepository.deleteAllGames()
            }
        }
    }

    private fun getGameHistory() {
        mainScope.launch {
            val gameHistory = withContext(Dispatchers.IO) {
                gameRepository.getAllGames()
            }
            this@GameHistoryActivity.gameHistory.clear()
            this@GameHistoryActivity.gameHistory.addAll(gameHistory)
            this@GameHistoryActivity.gameAdapter.notifyDataSetChanged()
        }
    }
}
