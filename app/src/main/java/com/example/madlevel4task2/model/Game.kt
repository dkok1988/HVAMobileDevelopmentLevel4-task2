package com.example.madlevel4task2.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.madlevel4task2.R
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
@Entity(tableName = "gameTable")
data class Game (

    @PrimaryKey
    @ColumnInfo(name = "gameDate")
    var date: Date,
    @ColumnInfo(name = "gamePlayer")
    var player: Int,
    @ColumnInfo(name = "gameComputer")
    var computer: Int,
    @ColumnInfo(name = "gameResult")
    var result: Int

) : Parcelable {
    companion object{
        val RESULT_ICONS = arrayOf(
            R.drawable.rock,
            R.drawable.paper,
            R.drawable.scissors
        )

        val RESULT_STRINGS = arrayOf(
            "Computer wins!",
            "Draw",
            "You win!"
        )
    }
}