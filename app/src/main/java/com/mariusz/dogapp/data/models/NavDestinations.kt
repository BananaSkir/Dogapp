package com.mariusz.dogapp.data.models

import kotlinx.serialization.Serializable

@Serializable
object DogList

@Serializable
data class DogScreen(val name: String)

@Serializable
object Settings

@Serializable
object Profile
