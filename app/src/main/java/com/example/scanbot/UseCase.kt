package com.example.scanbot

import android.app.Activity
import com.example.scanbot.usecases.*
import kotlin.reflect.KClass

enum class UseCase {
    SINGLE_PAGE,
    MULTIPLE_PAGE,
    FINDER,
    GALLERY,
}


sealed class ViewType(val type: Int) {
    class Header(val title: String) : ViewType(0)
    class Option(val useCase: UseCase, val title: String) : ViewType(1)
    class Support : ViewType(2)
}