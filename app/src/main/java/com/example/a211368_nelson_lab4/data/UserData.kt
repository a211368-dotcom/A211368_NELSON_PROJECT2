package com.example.a211368_nelson_lab4.data

data class UserData(
    val name: String = "",
    val experiment: String = "",
    val note: String = "",
)

data class Experiment(
    val title: String,
    val description: String,
    val aim: String,
    val procedure: String,
    val result: String,

)