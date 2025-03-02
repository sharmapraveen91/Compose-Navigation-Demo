package com.praveen.picsumapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.praveen.picsumapp.presentation.PicSumDestinations
import com.praveen.picsumapp.presentation.theme.PicSumAppTheme
import com.praveen.picsumapp.presentation.ui.screen.ImageDetailScreen
import com.praveen.picsumapp.presentation.ui.screen.ImageListScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PicSumAppTheme {
                val navController = rememberNavController()
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        NavHost(
                            navController = navController,
                            startDestination = PicSumDestinations.List.route
                        ) {
                            composable(PicSumDestinations.List.route) {
                                ImageListScreen(navController)
                            }

                            composable(
                                route = PicSumDestinations.Detail.routeWithArgs,
                                arguments = PicSumDestinations.Detail.arguments
                            ) {
                                ImageDetailScreen(navController)
                            }
                        }
                    }
                }
            }
        }
    }
}

