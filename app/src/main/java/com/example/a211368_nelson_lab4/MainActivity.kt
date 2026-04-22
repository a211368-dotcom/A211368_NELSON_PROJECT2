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
import com.example.a211368_nelson_lab4.ui.theme.A211368_NELSON_LAB4Theme
import com.example.a211368_nelson_lab4.screen.ClassScreen
import com.example.a211368_nelson_lab4.screen.DetailScreen
import com.example.a211368_nelson_lab4.screen.HomeScreen
import com.example.a211368_nelson_lab4.screen.LabScreen
import com.example.a211368_nelson_lab4.screen.ProfileScreen
import com.example.a211368_nelson_lab4.viewmodel.LabViewModel
import com.example.a211368_nelson_lab4.screen.ExperimentOverview

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

                        // 🌸 HOME
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

                        // 📄 DETAIL
                        composable(LabScreen.Detail.name) {
                            DetailScreen(
                                viewModel = viewModel,
                                onBack = { navController.popBackStack() },
                                onNext = {
                                    navController.navigate(LabScreen.ExperimentOverview.name)
                                }
                            )
                        }

                        composable(LabScreen.ExperimentOverview.name) {
                            ExperimentOverview(
                                viewModel = viewModel,
                                onBack = { navController.popBackStack() },
                            )
                        }

                        // 👤 PROFILE (FIXED)
                        composable(LabScreen.Profile.name) {
                            ProfileScreen(
                                userName = viewModel.userData.name,
                                isDarkTheme = darkTheme,
                                onToggleTheme = { darkTheme = !darkTheme },
                                onBack = { navController.popBackStack() }
                            )
                            }

                        // 📘 CLASS
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
fun CategorySection(
    title: String,
    items: Map<String, String>,
    icon: ImageVector,
    onExperimentClick: (String) -> Unit) {

    Column {

 Row( //title with icon
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(vertical = 10.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = title,
                style = MaterialTheme.typography.displayMedium,
                color = MaterialTheme.colorScheme.onBackground
            )
        }

        items.forEach { (item, description) ->
            var expanded by remember { mutableStateOf(false) }

            Card(  //experiment in each category
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp)
                    .clickable {
                        onExperimentClick(item)
                    }
                    .shadow(4.dp, RoundedCornerShape(20.dp)),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                ),
                border = BorderStroke(
                    1.dp,
                    MaterialTheme.colorScheme.outline.copy(alpha = 0.2f)
                )
            ) {

                Column {

                    // top strip
    Box(
      modifier = Modifier
     .fillMaxWidth()
     .height(4.dp)
     .background(MaterialTheme.colorScheme.primary)
                    )

        Column(modifier = Modifier.padding(16.dp)) {

        Row(verticalAlignment = Alignment.CenterVertically) {

         Spacer(modifier = Modifier.width(10.dp))

         Text(
        text = item,
        style = MaterialTheme.typography.titleMedium,
        color = MaterialTheme.colorScheme.onSurface,
        modifier = Modifier.weight(1f)
                            )

         IconButton(onClick = { expanded = !expanded }) {
           Icon(
            imageVector = if (expanded)
            Icons.Default.ExpandLess
              else
            Icons.Default.ExpandMore,
            contentDescription = null
     )
    }
 }

 AnimatedVisibility(
  visible = expanded,
   enter = fadeIn() + expandVertically(),
   exit = fadeOut() + shrinkVertically()
   ) {

     Column {
     Spacer(modifier = Modifier.height(10.dp))

      Divider(
      color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f)
   )

    Spacer(modifier = Modifier.height(10.dp))

   Text(
   text = description,
   style = MaterialTheme.typography.bodyLarge,
   color = MaterialTheme.colorScheme.onSurfaceVariant
)
}
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