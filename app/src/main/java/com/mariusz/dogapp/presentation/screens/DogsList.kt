package com.mariusz.dogapp.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.mariusz.dogapp.R
import com.mariusz.dogapp.DogScreen
import com.mariusz.dogapp.Profile
import com.mariusz.dogapp.Settings
import com.mariusz.dogapp.data.models.Dog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun DogsTypeList(
    items: List<Dog>,
    onSave: (name: String) -> Unit,
    onFav: (id: Int) -> Unit,
    onTrash: (id: Int) -> Unit,
    onSetClick: () -> Unit,
    onProfClick: () -> Unit,
    onRowClick: () -> Unit,
    modifier: Modifier = Modifier
){
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text("Doggos")
                },
                navigationIcon = {
                    IconButton(onClick = onSetClick) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = null
                        )
                    }
                },
                actions = {
                    IconButton(onClick = onProfClick) {
                        Icon(
                            imageVector = Icons.Default.AccountCircle,
                            contentDescription = null
                        )
                    }
                }
            )
        }
    )
    { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            var dog by remember { mutableStateOf("pies") }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = dog,
                    onValueChange = { dog = it }
                )
                Spacer(modifier = Modifier.width(8.dp))
                //IconButton(onClick = { onSearchChange(name) }) {
                //    Icon(Icons.Default.Search, contentDescription = "Szukaj")             //PRZYCISK WYSZUKIWANIA
                //}
                IconButton(onClick = { onSave(dog) }) {
                    Icon(Icons.Default.Add, contentDescription = "Dodaj")
                }
            }

//            Text(
//                text = "🐕: ${dogs.size}    💜: ${likedDogs.value.count { it.value }}",         //LICZNIKI
//                fontWeight = FontWeight.Bold,
//                modifier = Modifier.padding(vertical = 8.dp)
//            )

//            LazyColumn(modifier = Modifier.fillMaxWidth()) {
//                val filteredDogs = if (searchQuery.isNotEmpty()) {                                //FILTR
//                    dogs.filter { it.contains(searchQuery, ignoreCase = true) }
//                } else dogs

            items.forEach{
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .clickable {onRowClick()},
                    //shape = RoundedCornerShape(12.dp),
                    //colors = CardDefaults.cardColors(containerColor = Color(0xFFF0E6FF))
                ) {

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .padding(8.dp)
                    ) {
                        //val placeholderImage: Painter = painterResource(id = R.drawable.placeholder_dog)
                        //Image(
                        //painter = placeholderImage,
                        //contentDescription = null,                                                //PLACEHOLDER IMG
                        //modifier = Modifier
                        //.size(50.dp)
                        //.clip(RoundedCornerShape(4.dp))
                        //)
                        Spacer(modifier = Modifier.width(8.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(it.name, fontWeight = FontWeight.SemiBold)
                            Text(it.breed, color = Color.Gray)
                        }
                        IconButton(onClick = { onFav(it.id) }
                        ) {
                            Icon(
                                imageVector = if (it.isFav) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                contentDescription = "Like"
                            )
                        }
                        IconButton(onClick = { onTrash(it.id) }
                        ) {
                            Icon(Icons.Default.Delete, contentDescription = "Usuń")
                        }
                    }
                }
            }
        }
    }
}