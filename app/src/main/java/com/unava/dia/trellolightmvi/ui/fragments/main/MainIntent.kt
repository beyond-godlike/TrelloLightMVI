package com.unava.dia.trellolightmvi.ui.fragments.main

sealed class MainIntent {
    object GetBoards : MainIntent()
}