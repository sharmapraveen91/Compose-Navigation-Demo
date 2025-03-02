# PicSum App - Jetpack Compose Navigation Demo

## 🚀 Introduction

PicSum App is a Jetpack Compose-based application demonstrating best practices in Compose Navigation. It includes navigation patterns for a large-scale app, covering argument passing, back stack handling, deep linking, error handling, and performance optimizations.

## 🎯 Features

- Jetpack Compose Navigation with argument passing

- Lazy List & Detail Navigation

- Back Stack Handling & Navigation Patterns

- Error Handling & Auth Flow Navigation

- Best Practices & Performance Optimization

---

## 📌 Navigation Setup

### Define Navigation Destinations
```kotlin
sealed interface PicSumDestinations {
    val route: String

    data object List : PicSumDestinations {
        override val route = "list"
    }

    data object Detail : PicSumDestinations {
        override val route = "detail"
        const val imageArg = "image"
        val routeWithArgs = "$route/{$imageArg}"
        val arguments = listOf(navArgument(imageArg) { type = NavType.StringType })

        fun createRoute(picSumImage: PicsumImage): String {
            val json = moshi.adapter(PicsumImage::class.java).toJson(picSumImage)
            val encodedJson = Uri.encode(json)
            return "$route/$encodedJson"
        }
    }
}
```

### 📌 Implement Navigation
```kotlin
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
                            ) { backStackEntry ->
                                val json = backStackEntry.arguments?.getString(PicSumDestinations.Detail.imageArg)
                                val picSumImage = PicSumDestinations.decodeImageJson(json ?: "")
                                if (picSumImage != null) {
                                    ImageDetailScreen(navController)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
```
### 📌 Navigating with Data Passing

Image List Screen → Detail Screen
```kotlin
@Composable
fun ImageListScreen(
    navController: NavHostController,
    viewModel: PicSumViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    when (val state = uiState) {
        is UiState.Success -> {
            LazyColumn {
                items(state.data) { image ->
                    ImageCard(image) {
                        navController.navigate(PicSumDestinations.Detail.createRoute(image))
                    }
                }
            }
        }
    }
}
```
### Detail Screen Handling
```kotlin
@Composable
fun ImageDetailScreen(navController: NavHostController) {
    val json = navController.previousBackStackEntry?.arguments?.getString(PicSumDestinations.Detail.imageArg)
    val picSumImage = PicSumDestinations.decodeImageJson(json ?: "")
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Image Detail", fontSize = 20.sp) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            picSumImage?.let {
                AsyncImage(
                    model = it.download_url,
                    contentDescription = "Image Detail",
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}
```
----
## Some Use-case to handle in navigation
### 📌 Handling API Errors (E.g., 401 Unauthorized Navigation to Login Screen)
```kotlin
viewModelScope.launch {
    apiCall()
        .catch { exception ->
            if (exception is HttpException && exception.code() == 401) {
                navController.navigate("login") {
                    popUpTo(PicSumDestinations.List.route) { inclusive = true }
                }
            }
        }
}
```

### 📌 Handling Complex Back Stack Scenarios

Navigating from D → A while clearing B & C
```kotlin
navController.navigate("A") {
    popUpTo("B") { inclusive = true }
}
```

### Retaining State on Back Press
```kotlin
navController.currentBackStackEntry?.savedStateHandle?.set("image", image)
```

----
## 📌 Best Practices ✅

- ✔ Use sealed interface for defining navigation destinations
- ✔ Encode/decode JSON objects when passing complex data
- ✔ Use savedStateHandle to retain UI state across navigation
- ✔ Implement Deep Linking for external navigation
- ✔ Handle authentication flows seamlessly

## ❌ Common Mistakes ⚠️

- ❌ Passing large objects directly in navigation arguments (use JSON encoding)
- ❌ Forgetting to clear back stack when navigating to login on 401 errors
- ❌ Using hardcoded routes instead of sealed interface

----
🏆 Conclusion

Read more about compose-navigation in this blog- https://medium.com/@sharmapraveen91/mastering-jetpack-compose-navigation-for-large-scale-apps-051d9974df0e

This PicSum App demonstrates Jetpack Compose Navigation for real-world apps, covering advanced use cases like argument passing, back stack handling, authentication flows, and deep linking. Understanding these patterns will make you stand out in interviews and help build scalable apps. 🚀

🔗 Star & Fork the Repository! 🤩