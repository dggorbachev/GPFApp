package com.singlelab.gpf.new_features.firebase

data class UserFirebase(
    val age: Long,
    val city: String,
    val description: String,
    val friends: List<String>,
    val icon: String,
    val id: String,
    val login: String,
    val name: String
)