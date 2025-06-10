package com.example.myapplication.moviesApp.screens.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.example.myapplication.moviesApp.model.getMovies
import com.example.myapplication.moviesApp.widget.MovieRow
import com.example.myapplication.ui.theme.MAGENTA
import com.example.myapplication.ui.theme.black

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen(navController: NavController, movieId: String?) {
    val movie = getMovies().filter { movie->
        movie.id == movieId
    }
    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MAGENTA,
                    titleContentColor = black,
                ),
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Localized description"
                        )
                    }
                },
                /*navigationIcon = {
                   Button(onClick = {
                       navController.popBackStack()
                   }) { }
                },*/
                title = {
                    Text("Movies")
                },
            )
        }
    ) {
        Surface(modifier = Modifier
            .fillMaxSize()
            .padding(it.calculateTopPadding())) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                MovieRow(movie = movie.first())
                Spacer(modifier = Modifier.height(8.dp))
                HorizontalDivider()
                Text("Movie Images")
                LazyRow {
                    items ( movie[0].images ){image->
                        Card(modifier = Modifier.padding(12.dp).size(240.dp)){
                            AsyncImage(
                                model = image ,
                                contentDescription = ""
                            )
                        }
                    }
                }

                /*Button(onClick = {
                    navController.popBackStack()
                }) {
                    Text("Go Back")
                }*/
            }
        }
    }

}