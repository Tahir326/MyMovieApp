package com.example.mymovieapp.ui.navigation

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.example.mymovieapp.ui.components.AppTopBar
import com.example.mymovieapp.ui.components.BottomNavigationBar
import com.example.mymovieapp.ui.components.LoadingView
import com.example.mymovieapp.ui.screens.details.DetailScreen
import com.example.mymovieapp.ui.screens.favorites.FavoritesScreen
import com.example.mymovieapp.ui.screens.home.HomeScreen
import com.example.mymovieapp.ui.screens.home.HomeViewModel
import com.example.mymovieapp.ui.screens.search.SearchScreen
import com.example.mymovieapp.ui.screens.search.SearchViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

sealed class MainScreen(val route: String) {
    object Home : MainScreen("home")
    object Favorites : MainScreen("favorites")
    object Search : MainScreen("search")
    object Settings : MainScreen("settings")
    object Details : MainScreen("details/{movieId}")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavGraph() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    // Main screens that show the bottom nav
    val mainScreens = listOf(
        MainScreen.Home.route,
        MainScreen.Favorites.route,
        MainScreen.Search.route,
        MainScreen.Settings.route
    )

    Scaffold(
        topBar = {
            if (currentRoute in mainScreens) {
                // Show top bar for all main screens including Search
                AppTopBar(title = currentRoute?.replaceFirstChar { it.uppercase() } ?: "")
            }
        },
        bottomBar = {
            if (currentRoute in mainScreens) {
                BottomNavigationBar(
                    currentRoute = currentRoute ?: MainScreen.Home.route,
                    onHomeClicked = { navController.navigate(MainScreen.Home.route) },
                    onFavoritesClicked = { navController.navigate(MainScreen.Favorites.route) },
                    onSearchClicked = { navController.navigate(MainScreen.Search.route) },
                    onSettingsClicked = { navController.navigate(MainScreen.Settings.route) }
                )
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = MainScreen.Home.route,
            modifier = Modifier.padding(paddingValues)
        ) {
            // Home Screen
            composable(MainScreen.Home.route) {
                val homeViewModel: HomeViewModel = hiltViewModel()
                HomeScreen(
                    viewModel = homeViewModel,
                    onOpenDetails = { movieId -> navController.navigate("details/$movieId") }
                )
            }

            // Favorites Screen
            composable(MainScreen.Favorites.route) {
                FavoritesScreen(
                    onOpenDetails = { movieId -> navController.navigate("details/$movieId") }
                )
            }

            // Settings Screen
            composable(MainScreen.Settings.route) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "Settings Screen")
                }
            }

            // Details Screen
            composable(
                MainScreen.Details.route,
                arguments = listOf(navArgument("movieId") { type = NavType.StringType })
            ) { backStackEntry ->
                val movieId = backStackEntry.arguments?.getString("movieId") ?: ""
                DetailScreen(
                    movieId = movieId,
                    onBack = { navController.popBackStack() }
                )
            }

            // Search Screen
            composable(MainScreen.Search.route) {
                var isLoading by remember { mutableStateOf(true) }
                val scope = rememberCoroutineScope()

                LaunchedEffect(Unit) {
                    scope.launch {
                        delay(1500) // initial loading for 1.5s
                        isLoading = false
                    }
                }

                if (isLoading) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        LoadingView()
                    }
                } else {
                    val searchViewModel: SearchViewModel = hiltViewModel()
                    SearchScreen(
                        viewModel = searchViewModel,
                        onOpenDetails = { movieId -> navController.navigate("details/$movieId") }
                    )
                }
            }
        }
    }
}
