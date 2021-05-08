package com.unava.dia.trellolightmvi.ui.main

import com.unava.dia.trellolightmvi.mviBase.MviIntent

sealed class MainIntent : MviIntent {
    object InitialIntent : MainIntent()
    //data class ClickIntent(val article: BoardsItem): MainIntent()
}