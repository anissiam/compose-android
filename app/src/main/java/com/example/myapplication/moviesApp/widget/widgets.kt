package com.example.myapplication.moviesApp.widget

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.KeyboardDoubleArrowDown
import androidx.compose.material.icons.filled.KeyboardDoubleArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import coil3.compose.AsyncImagePainter.Companion.DefaultTransform
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.myapplication.moviesApp.model.Movie
import com.example.myapplication.moviesApp.model.getMovies
import com.example.myapplication.ui.theme.white

@Preview
@Composable
fun MovieRow(movie: Movie  = getMovies()[0], onItemClick:(String) -> Unit = {}) {
    var expanded by remember { mutableStateOf(false) }
    Card (modifier = Modifier
        .padding(4.dp)
        .fillMaxWidth()
        //.height(130.dp)
        .clickable {
            onItemClick(movie.id)
        },
        colors =  CardDefaults.cardColors(
            containerColor = white,

            ),
        elevation = CardDefaults.cardElevation(6.dp),
        shape = RoundedCornerShape(corner= CornerSize(12.dp),)
    ){
        Row (verticalAlignment =Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start){
            Surface (modifier = Modifier
                .padding(12.dp)
                .size(100.dp),
                shape = RectangleShape,
                color = Color.White,
                tonalElevation = 4.dp
            ){
                //Icon(imageVector = Icons.Default.AccountBox , "")
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(movie.images[0])
                        .crossfade(true)
                        .build(),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.clip(CircleShape),
                    contentDescription = null,
                )
            }
            Column(modifier = Modifier.padding(4.dp)) {
                Text(text = "${movie.title}" , style = MaterialTheme.typography.titleMedium)
                Text(text = "Director : ${movie.director}" , style = MaterialTheme.typography.titleSmall)
                Text(text = "Released ${movie.year}" , style = MaterialTheme.typography.titleSmall)
                AnimatedVisibility(visible = expanded) {
                    Column() {
                        Text(buildAnnotatedString {
                            withStyle (style = SpanStyle(color = Color.DarkGray, fontSize = 10.sp)){
                                append("Plot:")
                            }
                            withStyle (style = SpanStyle(color = Color.DarkGray, fontSize = 10.sp , fontWeight = FontWeight.Light)){
                                append(movie.plot)
                            }
                        } , modifier = Modifier.padding(6.dp))
                        HorizontalDivider(modifier = Modifier.padding(3.dp))
                        Text(text = "Director: ${movie.director}" , style = MaterialTheme.typography.bodyMedium)
                        Text(text = "Actors: ${movie.actors}" , style = MaterialTheme.typography.bodyMedium)
                        Text(text = "Rating: ${movie.rating}" , style = MaterialTheme.typography.bodyMedium)
                    }
                }

                Icon(imageVector = if(expanded)Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                    contentDescription = "" ,
                    modifier = Modifier
                        .size(25.dp)
                        .clickable {
                            expanded = !expanded
                        },
                    tint = Color.DarkGray
                )
            }

        }



    }
}