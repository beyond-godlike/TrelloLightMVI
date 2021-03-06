package com.unava.dia.trellolightmvi.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey

@Entity( tableName = "tasks",
    foreignKeys = [ForeignKey(
        entity = Board::class,
        parentColumns = ["id"],
        childColumns = ["boardId"],
        onDelete = CASCADE
    )]
)

data class Task(var title: String, var description: String, var boardId: Long) {
    @PrimaryKey(autoGenerate = true)
    var id: Long? = null
}