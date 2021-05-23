package com.unava.dia.trellolightmvi.ui.fragments.board

sealed class BoardIntent {
    data class GetCurrentBoard(val boardId: Long) : BoardIntent()
    data class GetTasks(val boardId: Long) : BoardIntent()
    data class AddNewBoard(val name: String) : BoardIntent()
    data class SaveBoard(val boardId: Long, val name: String) : BoardIntent()
    data class DeleteBoard(val boardId: Long) : BoardIntent()
}