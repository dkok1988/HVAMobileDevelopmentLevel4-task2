package com.example.MadLevel4Task2.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.example.MadLevel4Task2.R
import com.example.MadLevel4Task2.database.GameRepository
import com.example.MadLevel4Task2.model.Game
import kotlinx.android.synthetic.main.activity_game.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

const val GAME_HISTORY_CODE = 62

class GameActivity : AppCompatActivity() {

    private val mainScope = CoroutineScope(Dispatchers.Main)
    private lateinit var gameRepository: GameRepository


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        gameRepository = GameRepository(this)
        initViews()
    }

    private fun initViews() {
        supportActionBar?.title = getString(R.string.app_name)
        updateStats()
        btnRock.setOnClickListener { rockPaperScissorsClicked(0) }
        btnPaper.setOnClickListener { rockPaperScissorsClicked(1) }
        btnScissors.setOnClickListener { rockPaperScissorsClicked(2) }
    }

    private fun rockPaperScissorsClicked(type: Int) {
        val playerChoice = type
        val computerChoice = getComputerChoice()
        resultCheck(playerChoice, computerChoice)
    }

    private fun getComputerChoice(): Int {
        return (0..2).random()
    }

    private fun resultCheck(playerChoice: Int, computerChoice: Int) {
        if (playerChoice in 0..2 && computerChoice in 0..2) {
            val result = getResult(playerChoice, computerChoice)
            tvResult.text = Game.RESULT_STRINGS[result]
            ivPlayer.setImageDrawable(getDrawable(Game.RESULT_ICONS[playerChoice]))
            ivComputer.setImageDrawable(getDrawable(Game.RESULT_ICONS[computerChoice]))
            tvResult.visibility = View.VISIBLE
            AddToHistory(playerChoice, computerChoice, result)
        }
    }

    private fun getResult(playerChoice: Int, computerChoice: Int): Int {
        if (playerChoice == computerChoice) {
            return 1
        } else if ((playerChoice == 0 && computerChoice == 2) || (playerChoice == 1
                    && computerChoice == 0) || (playerChoice == 2 && computerChoice == 1)
        ) {
            return 2
        } else if ((playerChoice == 0 && computerChoice == 1) || (playerChoice == 1
                    && computerChoice == 2) || (playerChoice == 2 && computerChoice == 0)
        ) {
            return 0
        }
        else return 9
    }

    private fun AddToHistory(playerChoice: Int, computerChoice: Int, result: Int) {
        mainScope.launch {
            val game = Game(
                Calendar.getInstance().time,
                playerChoice,
                computerChoice,
                result
            )

            withContext(Dispatchers.IO) {
                gameRepository.inserGame(game)
            }
        }
        updateStats()
    }

    private fun updateStats() {
        mainScope.launch {
            val winCount = withContext(Dispatchers.IO) {
                gameRepository.getWinCount()
            }
            val drawCount = withContext(Dispatchers.IO) {
                gameRepository.getDrawCount()
        }
            val lossCount = withContext(Dispatchers.IO) {
                gameRepository.getLossCount()
            }
            tvStats.text = getString(R.string.game_stats, winCount, drawCount, lossCount)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_game, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_show_game_history -> {
                val gameHistoryActivityIntent =
                    Intent(this, GameHistoryActivity::class.java)
                startActivityForResult(gameHistoryActivityIntent, GAME_HISTORY_CODE)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == GAME_HISTORY_CODE) {
            updateStats()
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}
