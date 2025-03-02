package com.praveen.picsumapp.presentation.intent

sealed class ImageListIntent {
    object LoadImages : ImageListIntent()
}