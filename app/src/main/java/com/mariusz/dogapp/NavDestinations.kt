package com.mariusz.dogapp

import kotlinx.serialization.Serializable

@Serializable
object DogList

@Serializable
data class DogScreen(val name: String)

@Serializable
object Settings

@Serializable
object Profile
