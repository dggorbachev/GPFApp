package com.singlelab.gpf.model.target

enum class TargetType(val title: String) {
    EVENT("event"), CHAT("chat");

    companion object {
        fun findByTitle(title: String): TargetType? {
            return values().find { it.title == title }
        }
    }
}