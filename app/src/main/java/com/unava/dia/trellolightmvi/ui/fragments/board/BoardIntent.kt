package com.unava.dia.trellolightmvi.ui.fragments.board

sealed class BoardIntent {
    data class GetCurrentBoard(val boardId: Int) : BoardIntent()
    data class GetTasks(val boardId: Int) : BoardIntent()
    data class AddNewBoard(val name: String) : BoardIntent()
    data class SaveBoard(val boardId: Int, val name: String) : BoardIntent()
    data class DeleteBoard(val boardId: Int) : BoardIntent()
}