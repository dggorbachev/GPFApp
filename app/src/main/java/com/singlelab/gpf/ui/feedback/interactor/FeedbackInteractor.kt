package com.singlelab.gpf.ui.feedback.interactor

import com.singlelab.net.model.person.FeedbackRequest

interface FeedbackInteractor {
    suspend fun addFeedback(request: FeedbackRequest)
}