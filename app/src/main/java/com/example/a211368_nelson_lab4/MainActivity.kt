package com.example.a211368_nelson_lab4

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.a211368_nelson_lab4.data.UserData
import com.example.a211368_nelson_lab4.screen.ClassScreen
import com.example.a211368_nelson_lab4.screen.DetailScreen
import com.example.a211368_nelson_lab4.screen.HomeScreen
import com.example.a211368_nelson_lab4.screen.ProfileScreen
import com.example.a211368_nelson_lab4.screen.ExperimentOverview
import com.example.a211368_nelson_lab4.screen.SummaryScreen
import com.example.a211368_nelson_project2.screen.LabScreen
import com.example.a211368_nelson_project2.viewmodel.LabViewModel

class MainActivity : ComponentActivity() {

    private var darkTheme by mutableStateOf(false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {

            A211368_NELSON_LAB4Theme(darkTheme = darkTheme) {

                val navController = rememberNavController()
                val viewModel: LabViewModel = viewModel()

                Scaffold(
                    bottomBar = { BottomNavigationBar(navController) }
                ) { padding ->

                    NavHost(
                        navController = navController,
                        startDestination = LabScreen.Home.name,
                        modifier = Modifier.padding(padding)
                    ) {

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

                        composable("summary") {
                            SummaryScreen(
                                viewModel = viewModel,
                                onBack = { navController.popBackStack() }
                            )
                        }

                        //profile screen
                        composable(LabScreen.Profile.name) {
                            ProfileScreen(
                                userName = viewModel.userData.name,
                                isDarkTheme = darkTheme,
                                onToggleTheme = { darkTheme = !darkTheme },
                                onBack = { navController.popBackStack() }
                            )
                            }

                        // class screen
                        composable(LabScreen.Class.name) {
                            ClassScreen(
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

