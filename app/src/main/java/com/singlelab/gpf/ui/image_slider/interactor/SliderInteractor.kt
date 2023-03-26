package com.singlelab.gpf.ui.image_slider.interactor

interface SliderInteractor {
    suspend fun deleteImage(eventUid: String, imageUid: String)
}