package com.example.mymovieapp.ui.screens.details

import androidx.activity.compose.BackHandler
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.mymovieapp.ui.components.EmptyView
import com.example.mymovieapp.ui.components.ErrorView
import com.example.mymovieapp.ui.components.LoadingView
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun DetailScreen(
    movieId: String,
    viewModel: DetailViewModel = androidx.hilt.navigation.compose.hiltViewModel(),
    onBack: () -> Unit
) {
    val state by viewModel.uiState.collectAsState()
    var isVisible by remember { mutableStateOf(true) }
    var isNavigatingBack by remember { mutableStateOf(false) }

    LaunchedEffect(movieId) {
        viewModel.loadMovie(movieId)
    }

    // Handle Android back button
    BackHandler {
        if (!isNavigatingBack) {
            isNavigatingBack = true
            isVisible = false
        }
    }

    // Animate visibility with fade in/out
    AnimatedVisibility(
        visible = isVisible,
        enter = fadeIn(animationSpec = tween(200)),
        exit = fadeOut(animationSpec = tween(170))
    ) {
        when {
            state.isLoading -> LoadingView()
            state.error != null -> ErrorView(message = state.error ?: "Error") {
                viewModel.loadMovie(movieId)
            }
            state.movie == null -> EmptyView("Movie not found")
            else -> {
                val movie = state.movie!!
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background)
                ) {
                    Box(modifier = Modifier.fillMaxWidth().height(400.dp)) {
                        Image(
                            painter = rememberAsyncImagePainter(
                                movie.posterUrl ?: "file:///android_asset/no_image.png"
                            ),
                            contentDescription = movie.title,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )

                        Box(
                            modifier = Modifier.fillMaxSize().background(
                                Brush.verticalGradient(
                                    colors = listOf(Color.Transparent, Color(0xAA000000)),
                                    startY = 0f,
                                    endY = 400f
                                )
                            )
                        )

                        IconButton(
                            onClick = {
                                if (!isNavigatingBack) {
                                    isNavigatingBack = true
                                    isVisible = false
                                }
                            },
                            modifier = Modifier.align(Alignment.TopStart).padding(12.dp)
                        ) {
                            Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
                        }

                        if (movie.rating != null) {
                            IconButton(
                                onClick = { viewModel.toggleFavorite() },
                                modifier = Modifier.align(Alignment.TopEnd).padding(12.dp)
                            ) {
                                Icon(
                                    imageVector = if (movie.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                    contentDescription = "Favorite",
                                    tint = Color(0xFFFF8C00)
                                )
                            }
                        }

                        Column(modifier = Modifier.align(Alignment.BottomStart).padding(12.dp)) {
                            Text(
                                movie.title,
                                fontSize = 26.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis
                            )

                            val releaseDateText = movie.releaseDateObj?.let {
                                SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(it)
                            } ?: "TBA"

                            if (movie.rating != null) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    repeat(5) { index ->
                                        Icon(
                                            Icons.Default.Star, null,
                                            tint = if (index < ((movie.rating ?: 0.0) / 2).toInt()) Color(0xFFFF8C00) else Color.Gray,
                                            modifier = Modifier.size(14.dp)
                                        )
                                    }
                                    Text(" ${movie.rating}", color = Color.White, fontSize = 12.sp)
                                }
                            }

                            Text(releaseDateText, color = Color.White, fontSize = 12.sp)
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                        Text(
                            "Overview",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = movie.description ?: "No description available",
                            fontSize = 14.sp,
                            lineHeight = 20.sp,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }
                }
            }
        }
    }

    // Trigger navigation after fade-out + optional small loading
    if (isNavigatingBack) {
        LaunchedEffect(isNavigatingBack) {
            delay(150) // fade-out + tiny loading duration
            onBack()
        }
    }
}
