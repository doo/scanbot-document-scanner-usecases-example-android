package com.example.scanbot

interface SaveListener {
    fun savePdf()
    fun saveTiff()
    fun saveJpeg() {}
    fun savePng() {}

}
