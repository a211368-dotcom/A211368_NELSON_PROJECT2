package com.example.a211368_nelson_lab4.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.Nature
import androidx.compose.material.icons.filled.Science
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.a211368_nelson_lab4.BottomNavigationBar
import com.example.a211368_nelson_lab4.ui.theme.A211368_NELSON_LAB4Theme
import com.example.a211368_nelson_lab4.viewmodel.LabViewModel

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: LabViewModel,
    onToggleTheme: () -> Unit,
    isDarkTheme: Boolean,
    onExperimentClick: (String) -> Unit,
    onSubmitName: (String) -> Unit
) {

    val chemistry = listOf(
        "Acid Reaction" to "Study how acids react with metals producing hydrogen gas.",
        "Acid-Base Titration" to "Determine concentration using neutralization process.",
        "Electrolysis" to "Use electricity to break down chemical compounds."
    )

    val physics = listOf(
        "Pendulum Motion" to "Investigate oscillation period vs length.",
        "Gravity Test" to "Measure gravitational acceleration using free fall."
    )

    val biology = listOf(
        "Plant Growth" to "Observe sunlight effect on plant growth.",
        "Cell Observation" to "Use microscope to study cell structure."
    )

    var name by remember { mutableStateOf("") }
    var inputName by remember { mutableStateOf(viewModel.userData.name) }
    val isSubmitted = viewModel.isSubmitted

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
            .padding(horizontal = 20.dp)
    ) {

        // header lab quest
        item {

            val gradient = Brush.verticalGradient(
                listOf(
                    MaterialTheme.colorScheme.primaryContainer,
                    MaterialTheme.colorScheme.secondaryContainer
                )
            )

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp, bottom = 20.dp),
                shape = RoundedCornerShape(28.dp),
                elevation = CardDefaults.cardElevation(0.dp)
            ) {

                Box(
                    modifier = Modifier
                        .background(gradient)
                        .padding(24.dp)
                ) {

                    Column {

                        //icon theme
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End
                        ) {
                            IconButton(onClick = onToggleTheme) {
                                Icon(
                                    imageVector = if (isDarkTheme)
                                        Icons.Default.DarkMode
                                    else Icons.Default.LightMode,
                                    contentDescription = null,
                                            tint = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }

                        Text(
                            text = if (isSubmitted) "Welcome, $inputName" else "LabQuest",
                            style = MaterialTheme.typography.headlineMedium.copy(
                                fontWeight = FontWeight.Bold
                            )
                        )

                        Text(
                            text = "Science Experiment Class",
                            style = MaterialTheme.typography.labelMedium
                        )
                    }
                }
            }
        }

        // name input text field
        item {
            AnimatedVisibility(visible = !isSubmitted) {

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.tertiaryContainer
                    )
                ) {

                    Column(modifier = Modifier.padding(16.dp)) {

                        Text(
                            text = "Enter Your Name",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold
                            )
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        OutlinedTextField(
                            value = inputName,
                            onValueChange = {
                                inputName = it },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(16.dp),
                            singleLine = true,
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Button(
                            onClick = {
                                if (inputName.isNotBlank()) {
                                    viewModel.submitName(inputName)
                                    onSubmitName(inputName)
                                }
                            },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(14.dp)
                        ) {
                            Text("Continue")
                        }
                    }
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(20.dp))
        }

        // category header
        item {
            Text(
                text = "Experiment Categories",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.ExtraBold
                ),
                modifier = Modifier.padding(bottom = 12.dp)
            )
        }

        // chemistry section
        item {
            SimpleCategoryCard(
                title = "Chemistry",
                color = MaterialTheme.colorScheme.primaryContainer,
                items = chemistry,
                onClick = onExperimentClick
            )
        }

        item { Spacer(modifier = Modifier.height(12.dp)) }

        // physics
        item {
            SimpleCategoryCard(
                title = "Physics",
                color = MaterialTheme.colorScheme.secondaryContainer,
                items = physics,
                onClick = onExperimentClick
            )
        }

        item { Spacer(modifier = Modifier.height(12.dp)) }

        // biology
        item {
            SimpleCategoryCard(
                title = "Biology",
                color = MaterialTheme.colorScheme.tertiaryContainer,
                items = biology,
                onClick = onExperimentClick
            )
        }

        item { Spacer(modifier = Modifier.height(100.dp)) }
    }
}

@Composable
fun SimpleCategoryCard(
    title: String,
    color: Color,
    items: List<Pair<String, String>>,
    onClick: (String) -> Unit
) {

    var expanded by remember { mutableStateOf(false) }

    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = color),
        modifier = Modifier.fillMaxWidth()
    ) {

        Column(modifier = Modifier.padding(16.dp)) {

            Row(verticalAlignment = Alignment.CenterVertically) {

                Text(
                    text = title,
                    modifier = Modifier.weight(1f),
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                )

                IconButton(onClick = { expanded = !expanded }) {
                    Icon(
                        imageVector = if (expanded)
                            Icons.Default.ExpandLess
                        else Icons.Default.ExpandMore,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
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

                    items.forEach { (name, desc) ->

                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 8.dp),
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.7f)
                            )
                        ) {

                            Column(modifier = Modifier
                                .padding(12.dp)
                                .clickable { onClick(name) }
                            ) {

                                Text(
                                    text = name,
                                    fontWeight = FontWeight.Bold
                                )

                                Text(
                                    text = desc,
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

