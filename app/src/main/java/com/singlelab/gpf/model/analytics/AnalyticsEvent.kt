package com.singlelab.gpf.model.analytics

enum class AnalyticsEvent(val title: String) {
    CREATE_EVENT("create_event"),
    OPEN_CHAT("open_chat"),
    SEND_MESSAGE("send_message"),
    PROMO_CLICK("promo_click")
}