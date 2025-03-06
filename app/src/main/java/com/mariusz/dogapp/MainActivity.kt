package com.mariusz.dogapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
    private val dogBreeds = listOf("Labrador", "Bulldog", "Poodle", "Beagle", "German Shepherd", "Chihuahua", "Golden Retriever", "Boxer", "Dachshund")
    private var searchQuery by mutableStateOf("")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DogAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(
                        modifier = Modifier.fillMaxSize().padding(innerPadding).padding(16.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            OutlinedTextField(
                                value = name,
                                onValueChange = { name = it },
                                placeholder = { Text("Wyszukaj lub dodaj pieska 🐕") },
                                shape = RoundedCornerShape(16.dp),
                                modifier = Modifier.weight(1f)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            IconButton(onClick = { searchQuery = name }) {
                                Icon(Icons.Default.Search, contentDescription = "Szukaj")
                            }
                            IconButton(onClick = {
                                if (name.isNotEmpty() && name !in dogs) {
                                    dogs.add(name)
                                    likedDogs.value[name] = false
                                    dogBreedsMap[name] = dogBreeds[Random.nextInt(dogBreeds.size)]
                                    name = ""
                                }
                            }) {
                                Icon(Icons.Default.Add, contentDescription = "Dodaj")
                            }
                        }

                        Text(
                            text = "🐕: ${dogs.size}    💜: ${likedDogs.value.count { it.value }}",
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )

                        LazyColumn(modifier = Modifier.fillMaxWidth()) {
                            val filteredDogs = if (searchQuery.isNotEmpty()) {
                                dogs.filter { it.contains(searchQuery, ignoreCase = true) }
                            } else dogs

                            items(filteredDogs) { dogName ->
                                Card(
                                    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                                    shape = RoundedCornerShape(12.dp),
                                    colors = CardDefaults.cardColors(containerColor = Color(0xFFF0E6FF))
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier.padding(16.dp)
                                    ) {
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Column(modifier = Modifier.weight(1f)) {
                                            Text(text = dogName, fontWeight = FontWeight.SemiBold)
                                            Text(text = "Rasa: ${dogBreedsMap[dogName]}", color = Color.Gray)
                                        }
                                        IconButton(onClick = {
                                            likedDogs.value = likedDogs.value.toMutableMap().apply {
                                                this[dogName] = !(this[dogName] ?: false)
                                            }
                                        }) {
                                            Icon(
                                                imageVector = if (likedDogs.value[dogName] == true) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                                contentDescription = "Like"
                                            )
                                        }
                                        IconButton(onClick = {
                                            dogs.remove(dogName)
                                            likedDogs.value = likedDogs.value.toMutableMap().apply { remove(dogName) }
                                        }) {
                                            Icon(Icons.Default.Delete, contentDescription = "Usuń")
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
}
