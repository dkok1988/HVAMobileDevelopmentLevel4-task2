package com.example.MadLevel4Task2.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.MadLevel4Task2.R
import com.example.MadLevel4Task2.model.Game
import kotlinx.android.synthetic.main.layout_game_item.view.*

class GameAdapter(private val gameHistory: List<Game>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(
            R.layout.layout_game_item,
            parent,
            false
        )
        return GameViewHolder(view)
    }

    override fun getItemCount(): Int = gameHistory.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as GameViewHolder).bind(gameHistory[position])
    }

    class GameViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(game: Game) {
            itemView.tvResult.text = Game.RESULT_STRINGS[game.result]
            itemView.tvDate.text = game.date.toString()
            itemView.ivComputer.setImageResource(Game.RESULT_ICONS[game.computer])
            itemView.ivPlayer.setImageResource(Game.RESULT_ICONS[game.player])
        }
    }
}