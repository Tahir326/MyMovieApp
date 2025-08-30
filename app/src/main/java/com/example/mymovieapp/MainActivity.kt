package com.example.mymovieapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.ExperimentalMaterial3Api
import com.example.mymovieapp.ui.theme.MyMovieAppTheme
import dagger.hilt.android.AndroidEntryPoint
import com.example.mymovieapp.ui.navigation.NavGraph
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyMovieAppTheme {
                NavGraph() // your root navigation
            }
        }
    }
}
