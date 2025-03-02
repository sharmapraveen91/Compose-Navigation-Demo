package com.praveen.picsumapp.presentation.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.praveen.picsumapp.presentation.PicSumDestinations
import com.praveen.picsumapp.presentation.state.UiState
import com.praveen.picsumapp.presentation.ui.screen.ui.composable.ImageCard
import com.praveen.picsumapp.presentation.viewmodel.PicSumViewModel
import com.valentinilk.shimmer.shimmer


/**
 * Created by Praveen.Sharma on 01/03/25 - 08:19..
 *
 ***/
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImageListScreen(
    navController: NavHostController,
    viewModel: PicSumViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Picsum Images", fontSize = 20.sp) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = Color.White
                )
            )
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            when (val state = uiState) {
                is UiState.Loading -> LoadingShimmerEffect()
                is UiState.Success -> {
                    LazyColumn(
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(state.data) { image ->
                            ImageCard(image) {
                                navController.currentBackStackEntry
                                    ?.savedStateHandle
                                    ?.set("image", image) // ✅ Fixed navigation
                                navController.navigate(PicSumDestinations.Detail.route)
                            }
                        }
                    }
                }
                is UiState.Error -> {
                    Text(
                        text = "Failed to load images: ${state.message}",
                        color = Color.Red,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }
    }
}

@Composable
fun LoadingShimmerEffect() {
    Column(Modifier.padding(16.dp)) {
        repeat(5) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .padding(vertical = 8.dp)
                    .background(Color.Gray.copy(alpha = 0.3f))
                    .shimmer()
            )
        }
    }
}

@Preview
@Composable
fun PreviewImageList(){
    val navController = rememberNavController()
    ImageListScreen(navController)
}

