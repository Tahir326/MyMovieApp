package com.example.mymovieapp.ui.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.mymovieapp.data.model.Movie
import com.example.mymovieapp.ui.components.EmptyView
import com.example.mymovieapp.ui.components.ErrorView
import com.example.mymovieapp.ui.components.LoadingView
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import androidx.compose.animation.core.animateFloatAsState
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = androidx.hilt.navigation.compose.hiltViewModel(),
    onOpenDetails: (String) -> Unit
) {
    val state by viewModel.uiState.collectAsState()
    val listState = rememberLazyListState()

    Box(modifier = Modifier.fillMaxSize()) {
        when {
            state.isLoading -> LoadingView()
            state.error != null -> ErrorView(
                message = state.error ?: "Unknown error",
                onRetry = { viewModel.loadMovies() }
            )

            state.trending.isEmpty() &&
                    state.popular.isEmpty() &&
                    state.upcoming.isEmpty() &&
                    state.carousel6.isEmpty() -> EmptyView("No movies available")
            else -> LazyColumn(
                modifier = Modifier.fillMaxSize(),
                state = listState,
                contentPadding = PaddingValues(vertical = 12.dp)
            ) {
                if (state.carousel6.isNotEmpty()) {
                    item {
                        MovieCarouselPager(
                            movies = state.carousel6,
                            onClick = { movieId -> onOpenDetails(movieId) }
                        )
                    }
                }
                if (state.trending.isNotEmpty()) {
                    item {
                        MovieSection(
                            title = "Trending",
                            movies = state.trending,
                            onClick = { movieId -> onOpenDetails(movieId) }
                        )
                    }
                }
                if (state.upcoming.isNotEmpty()) {
                    item {
                        UpcomingCarousel(
                            movies = state.upcoming,
                            onClick = { movieId -> onOpenDetails(movieId) }
                        )
                    }
                }
                if (state.popular.isNotEmpty()) {
                    item {
                        MovieSection(
                            title = "Popular",
                            movies = state.popular,
                            onClick = { movieId -> onOpenDetails(movieId) }
                        )
                    }
                }
            }
        }
    }
}

// ---------------- Movie Carousel ----------------
@Composable
fun MovieCarouselPager(movies: List<Movie>, onClick: (String) -> Unit) {
    if (movies.isEmpty()) return

    val pagerState = rememberPagerState()

    LaunchedEffect(pagerState) {
        while (true) {
            delay(5000)
            val nextPage = (pagerState.currentPage + 1) % movies.size
            pagerState.animateScrollToPage(nextPage)
        }
    }

    Column(modifier = Modifier.fillMaxWidth()) {
        HorizontalPager(
            count = movies.size,
            state = pagerState,
            contentPadding = PaddingValues(horizontal = 11.dp),
            modifier = Modifier.fillMaxWidth(),
            itemSpacing = 8.dp
        ) { page ->
            val movie = movies[page]
            val scale by animateFloatAsState(
                targetValue = if (pagerState.currentPage == page) 1f else 0.92f,
                label = "scaleAnim"
            )

            Box(
                modifier = Modifier.graphicsLayer { scaleX = scale; scaleY = scale }
            ) {
                MovieCardLarge(
                    movie = movie,
                    onClick = { onClick(movie.id) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(0.95f)
                        .clip(RoundedCornerShape(12.dp))
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        HorizontalPagerIndicator(
            pagerState = pagerState,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            activeColor = Color(0xFFFF8C00),
            inactiveColor = Color.Gray,
            indicatorWidth = 6.dp,
            spacing = 5.dp
        )
    }
}

// ---------------- Movie Sections ----------------
@Composable
fun MovieSection(title: String, movies: List<Movie>, onClick: (String) -> Unit) {
    Column(modifier = Modifier.padding(vertical = 12.dp)) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp,
            modifier = Modifier.padding(horizontal = 12.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(horizontal = 12.dp)
        ) {
            items(movies) { movie ->
                MovieCardSmall(
                    movie = movie,
                    onClick = { onClick(movie.id) }
                )
            }
        }
    }
}

// ---------------- Upcoming Carousel ----------------
@Composable
fun UpcomingCarousel(movies: List<Movie>, onClick: (String) -> Unit) {
    Column(modifier = Modifier.padding(vertical = 12.dp)) {
        Text(
            text = "Upcoming",
            style = MaterialTheme.typography.titleLarge,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(horizontal = 12.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .height(280.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(horizontal = 12.dp)
        ) {
            items(movies) { movie ->
                UpcomingCardLarge(
                    movie = movie,
                    onClick = { onClick(movie.id) }
                )
            }
        }
    }
}

// ---------------- Movie Cards ----------------
@Composable
fun MovieCardLarge(movie: Movie, onClick: () -> Unit, modifier: Modifier = Modifier) {
    val fullDate = movie.releaseDateObj?.let {
        SimpleDateFormat("dd MMMM yyyy", Locale.getDefault()).format(it)
    } ?: "TBA"

    Box(modifier = modifier.clickable { onClick() }) {
        Image(
            painter = rememberAsyncImagePainter(movie.posterUrl ?: "file:///android_asset/no_image.png"),
            contentDescription = movie.title,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Brush.verticalGradient(listOf(Color.Transparent, Color(0xAA000000))))
        )
        Column(modifier = Modifier.align(Alignment.BottomStart).padding(8.dp)) {
            Text(
                text = movie.title,
                color = Color.White,
                fontSize = 28.sp,
                fontWeight = FontWeight.SemiBold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            if (movie.rating != null) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    repeat(5) { index ->
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = null,
                            tint = if (index < ((movie.rating ?: 0.0) / 2).toInt()) Color(0xFFFF8C00) else Color.Gray,
                            modifier = Modifier.size(12.dp)
                        )
                    }
                    Text(" ${movie.rating}", color = Color.White, fontSize = 12.sp)
                }
            }
            Text(fullDate, color = Color.White, fontSize = 10.sp)
        }
    }
}

@Composable
fun MovieCardSmall(movie: Movie, onClick: () -> Unit) {
    val fullDate = movie.releaseDateObj?.let {
        SimpleDateFormat("dd MMMM yyyy", Locale.getDefault()).format(it)
    } ?: "TBA"

    Column(modifier = Modifier.width(120.dp).clickable { onClick() }) {
        Box {
            Image(
                painter = rememberAsyncImagePainter(movie.posterUrl ?: "file:///android_asset/no_image.png"),
                contentDescription = movie.title,
                modifier = Modifier
                    .height(180.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
            Box(
                modifier = Modifier.fillMaxSize().background(
                    Brush.verticalGradient(
                        listOf(Color.Transparent, Color(0xAA000000)),
                        startY = 0f,
                        endY = 180f
                    )
                )
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = movie.title,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            fontWeight = FontWeight.SemiBold,
            fontSize = 14.sp,
            color = Color.White
        )
        Text(fullDate, fontSize = 10.sp, color = Color.White)
    }
}

@Composable
fun UpcomingCardLarge(movie: Movie, onClick: () -> Unit) {
    val fullDate = movie.releaseDateObj?.let {
        SimpleDateFormat("dd MMMM yyyy", Locale.getDefault()).format(it)
    } ?: "TBA"

    Box(
        modifier = Modifier
            .width(250.dp)
            .height(400.dp)
            .clip(RoundedCornerShape(12.dp))
            .clickable { onClick() }
    ) {
        Image(
            painter = rememberAsyncImagePainter(movie.posterUrl ?: "file:///android_asset/no_image.png"),
            contentDescription = movie.title,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
        Box(
            modifier = Modifier.fillMaxSize().background(
                Brush.verticalGradient(listOf(Color.Transparent, Color(0xAA000000)), startY = 0f, endY = 400f)
            )
        )
        Column(modifier = Modifier.align(Alignment.BottomStart).padding(8.dp)) {
            Text(
                text = movie.title,
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(fullDate, color = Color.White, fontSize = 12.sp)
        }
    }
}
