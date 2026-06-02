package com.example.a211368_nelson_project2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.a211368_nelson_project2.screen.DetailScreen
import com.example.a211368_nelson_project2.screen.ExperimentOverview
import com.example.a211368_nelson_project2.screen.HomeScreen
import com.example.a211368_nelson_project2.screen.LabScreen
import com.example.a211368_nelson_project2.screen.ProfileScreen
import com.example.a211368_nelson_project2.screen.SummaryScreen
import com.example.a211368_nelson_project2.viewmodel.LabViewModel
import com.example.a211368_nelson_project2.screen.AssignmentScreen
import com.example.a211368_nelson_project2.ui.theme.A211368_NELSON_PROJECT1Theme
import com.example.a211368_nelson_project2.screen.ClassScreen
import com.example.a211368_nelson_project2.screen.ClassDetail
import com.example.a211368_nelson_project2.screen.LoginScreen
import com.example.a211368_nelson_project2.viewmodel.AppViewModelProvider

class MainActivity : ComponentActivity() {

    private var darkTheme by mutableStateOf(false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {

            A211368_NELSON_PROJECT1Theme(darkTheme = darkTheme) {

                val navController = rememberNavController()
                val viewModel: LabViewModel = viewModel(
                    factory = AppViewModelProvider.Factory
                )

                Scaffold(
                    bottomBar = { BottomNavigationBar(navController) }
                ) { padding ->

                    val startDestination =
                        if (viewModel.userData.name.isNotEmpty())
                            LabScreen.Home.name
                        else
                            LabScreen.Login.name

                    NavHost(
                        navController = navController,
                        startDestination = startDestination,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(padding)
                    ) {

                        composable(LabScreen.Login.name) {
                            LoginScreen(
                                onLogin = { name ->

                                    viewModel.setName(name)

                                    navController.navigate(LabScreen.Home.name) {
                                        popUpTo(LabScreen.Login.name) { inclusive = true }
                                    }
                                }
                            )
                        }

                        // home screen
                        composable(LabScreen.Home.name) {
                            HomeScreen(
                                viewModel = viewModel,
                                onToggleTheme = { darkTheme = !darkTheme },
                                isDarkTheme = darkTheme,
                                onExperimentClick = { title ->
                                    viewModel.updateExperiment(title)
                                    navController.navigate(LabScreen.Detail.name)
                                },
                                onSubmitName = { name -> viewModel.updateUserName(name)
                                }
                            )
                        }

                        // detail screen
                        composable(LabScreen.Detail.name) {
                            DetailScreen(
                                viewModel = viewModel,
                                onBack = { navController.popBackStack() },
                                onNext = {
                                    navController.navigate(LabScreen.ExperimentOverview.name)
                                }
                            )
                        }

                        //experiment notes screen
                        composable(LabScreen.ExperimentOverview.name) {
                            ExperimentOverview(
                                viewModel = viewModel,
                                onBack = { navController.popBackStack() },
                                onNext = {
                                    navController.navigate("summary")
                                }
                            )
                        }

                        composable(LabScreen.Summary.name) {
                            SummaryScreen(
                                viewModel = viewModel,
                                onBack = { navController.popBackStack() }
                            )
                        }

                        //profile screen
                        //profile screen
                        composable(LabScreen.Profile.name) {
                            ProfileScreen(
                                modifier = Modifier.fillMaxSize(),
                                viewModel = viewModel,
                                userName = viewModel.userData.name,
                                isDarkTheme = darkTheme,
                                onToggleTheme = { darkTheme = !darkTheme },
                                onBack = { navController.popBackStack() },

                                onLogout = {
                                    navController.navigate(LabScreen.Login.name) {
                                        popUpTo(0)
                                    }
                                },

                                // ADDED
                                isLoggedIn = viewModel.userData.name.isNotEmpty()
                            )
                        }

                        // class screen
                        composable(LabScreen.Class.name) {
                            ClassScreen(
                                onBack = { navController.popBackStack() },
                                onClassClick = { title, desc, category ->

                                    navController.currentBackStackEntry?.savedStateHandle?.set("title", title)
                                    navController.currentBackStackEntry?.savedStateHandle?.set("desc", desc)
                                    navController.currentBackStackEntry?.savedStateHandle?.set("category", category)

                                    navController.navigate(LabScreen.ClassDetail.name)
                                }
                            )
                        }

                        composable(LabScreen.ClassDetail.name) {
                            val backStackEntry = navController.previousBackStackEntry
                            val title = backStackEntry?.savedStateHandle?.get<String>("title") ?: ""
                            val desc = backStackEntry?.savedStateHandle?.get<String>("desc") ?: ""
                            val iconName = backStackEntry?.savedStateHandle?.get<String>("icon") ?: "FlashOn"
                            val category = backStackEntry?.savedStateHandle?.get<String>("category") ?: ""

                            val icon = when (iconName) {
                                "Eco" -> Icons.Default.Eco
                                "Science" -> Icons.Default.Science
                                else -> Icons.Default.FlashOn
                            }

                            ClassDetail(
                                title = title,
                                description = desc,
                                icon = icon,
                                navController = navController,
                                category = category,
                                onBack = { navController.popBackStack() }
                            )
                        }

                        composable(LabScreen.Assignment.name) {
                            AssignmentScreen(
                                onBack = { navController.popBackStack() }
                            )
                        }


                    }
                }
            }
        }
    }
}


@Composable
fun BottomNavigationBar(navController: NavHostController) {

    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

    NavigationBar {

        NavigationBarItem(
            selected = currentRoute == LabScreen.Home.name,
            onClick = {
                navController.navigate(LabScreen.Home.name) {
                    popUpTo(navController.graph.startDestinationId)
                    launchSingleTop = true
                }
            },
            icon = { Icon(Icons.Default.Home, null) },
            label = { Text("Home") }
        )

        NavigationBarItem(
            selected = currentRoute == LabScreen.Class.name,
            onClick = {
                navController.navigate(LabScreen.Class.name) {
                    launchSingleTop = true
                }
            },
            icon = { Icon(Icons.Default.Person, null) },
            label = { Text("Class") }
        )

        NavigationBarItem(
            selected = currentRoute == LabScreen.Profile.name,
            onClick = {
                navController.navigate(LabScreen.Profile.name) {
                    launchSingleTop = true
                }
            },
            icon = { Icon(Icons.Default.AccountCircle, null) },
            label = { Text("Profile") }
        )
    }
}
