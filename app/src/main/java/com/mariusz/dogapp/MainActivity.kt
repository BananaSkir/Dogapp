package com.mariusz.dogapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.mariusz.dogapp.ui.theme.DogAppTheme
import kotlin.random.Random

class MainActivity : ComponentActivity() {

    private var name by mutableStateOf("")
    private var dogs by mutableStateOf(mutableListOf<String>())
    private var likedDogs = mutableStateOf(mutableMapOf<String, Boolean>())
    private var dogBreedsMap = mutableStateMapOf<String, String>()
    private val dogBreeds = listOf(
        "Labrador", "Bulldog", "Poodle", "Beagle", "German Shepherd", "Chihuahua", "Golden Retriever", "Boxer", "Dachshund"
    )
    private var searchQuery by mutableStateOf("")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DogAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ){
                        Row(
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            modifier = Modifier
                                .fillMaxWidth()
                        ){
                            OutlinedTextField(
                                value = name,
                                onValueChange = { name = it },
                                placeholder = { Text("Wyszukaj lub dodaj psa") }
                            )
                            IconButton(
                                onClick = {
                                    searchQuery = name
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Search,
                                    contentDescription = "Szukaj"
                                )
                            }
                            OutlinedButton(onClick = {
                                if (name.isNotEmpty()) {
                                    if(name in dogs){
                                        return@OutlinedButton
                                    }
                                    dogs.add(name)
                                    likedDogs.value[name] = false
                                    val dogBreed = dogBreeds[Random.nextInt(dogBreeds.size)]
                                    dogBreedsMap[name] = dogBreed
                                    name = ""
                                }
                            }){
                                Icon(
                                    imageVector = Icons.Default.Add,
                                    contentDescription = "Dodaj"
                                )
                            }
                        }

                        Row(
                            modifier = Modifier.padding(top = 16.dp)
                        ) {
                            Text(
                                text = "🐕: ${dogs.size}      💜: ${likedDogs.value.filter { it.value }.size}",
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        LazyColumn (
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 16.dp)
                        ){
                            val filteredDogs = if (searchQuery.isNotEmpty()) {
                                dogs.filter { it.contains(searchQuery, ignoreCase = true) }
                            } else {
                                dogs
                            }
                            items(filteredDogs){ dogName ->
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 16.dp)
                                ){

                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(
                                            text = dogName,
                                            fontWeight = FontWeight.SemiBold,
                                        )
                                        Text(
                                            text = "Rasa: ${dogBreedsMap[dogName]}",
                                            style = MaterialTheme.typography.bodySmall,
                                            color = Color.Gray
                                        )
                                    }

                                    IconButton(onClick = {
                                        likedDogs.value = likedDogs.value.toMutableMap().apply {
                                            this[dogName] = !(this[dogName] ?: false)
                                        }
                                    }) {
                                        Text(
                                            text = if (likedDogs.value[dogName] == true) "💜" else "🤍"
                                        )
                                    }

                                    IconButton(onClick = {
                                        dogs.remove(dogName)
                                        likedDogs.value = likedDogs.value.toMutableMap().apply {
                                            this.remove(dogName)  // Usuwamy psa z likedDogs po usunięciu z dogs
                                        }
                                    }) {
                                        Icon(
                                            imageVector = Icons.Default.Delete,
                                            contentDescription = null
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
