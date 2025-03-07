package com.mariusz.dogapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.mariusz.dogapp.data.models.DogList
import com.mariusz.dogapp.data.models.DogScreen
import com.mariusz.dogapp.data.models.Profile
import com.mariusz.dogapp.data.models.Settings
import com.mariusz.dogapp.presentation.screens.DogDetails
import com.mariusz.dogapp.presentation.screens.DogsList
import com.mariusz.dogapp.presentation.screens.Profile
import com.mariusz.dogapp.presentation.screens.Settings
import com.mariusz.dogapp.ui.theme.DogAppTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DogAppTheme {

                val navController = rememberNavController()
                var name by remember {mutableStateOf("")}
                var dogs = remember {mutableStateListOf<String>()}
                val likedDogs = remember { mutableStateOf(mutableMapOf<String, Boolean>()) }
                val dogBreedsMap = remember {mutableStateMapOf<String, String>()}
                val dogBreeds = listOf("Labrador", "Bulldog", "Poodle", "Beagle", "German Shepherd", "Chihuahua", "Golden Retriever", "Boxer", "Dachshund")
                var searchQuery by remember {mutableStateOf("")}

                NavHost(navController = navController, startDestination = DogList){
                    composable<DogList> {
                        DogsList(
                            name = name,
                            dogs = dogs,
                            likedDogs = likedDogs,
                            dogBreedsMap = dogBreedsMap,
                            dogBreeds = dogBreeds,
                            searchQuery = searchQuery,
                            navController = navController,
                            onNameChange = { name = it },
                            onAddDog = { newDog ->
                                if (newDog.isNotEmpty() && newDog !in dogs) {
                                    dogs.add(newDog)
                                    likedDogs.value = likedDogs.value.toMutableMap().apply { put(newDog, false) }
                                    dogBreedsMap[newDog] = dogBreeds.random()
                                }
                            },
                            onDeleteDog = { dogToRemove ->
                                dogs.remove(dogToRemove)
                                likedDogs.value = likedDogs.value.toMutableMap().apply { remove(dogToRemove) }
                                dogBreedsMap.remove(dogToRemove)
                            },
                            onSearchChange = { searchQuery = it }
                        )
                    }

                    composable<DogScreen> {
                        val args = it.toRoute<DogScreen>()

                        DogDetails(
                            navController = navController,
                            name = args.name,
                            dogBreedsMap = dogBreedsMap,
                            onDeleteDog = { dogToRemove ->
                                dogs.remove(dogToRemove)
                                likedDogs.value = likedDogs.value.toMutableMap().apply { remove(dogToRemove) }
                                dogBreedsMap.remove(dogToRemove)
                            }
                        )
                    }

                    composable<Settings> {
                        val args = it.toRoute<Settings>()
                        Settings(
                            navController = navController
                        )
                    }

                    composable<Profile> {
                        val args = it.toRoute<Profile>()
                        Profile(
                            navController = navController
                        )
                    }
                }
            }
        }
    }
}
