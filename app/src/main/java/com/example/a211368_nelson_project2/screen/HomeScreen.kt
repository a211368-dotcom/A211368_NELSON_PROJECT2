package com.example.a211368_nelson_project2.screen

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
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.a211368_nelson_project2.viewmodel.LabViewModel

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
                            ),
                                    color = MaterialTheme.colorScheme.onPrimaryContainer
                        )

                        Text(
                            text = "Science Experiment Class",
                            style = MaterialTheme.typography.labelMedium,
                                    color = MaterialTheme.colorScheme.onPrimaryContainer
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
                        containerColor = MaterialTheme.colorScheme.surface
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

        item {
            SDGCard()
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
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
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
                contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
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
                contentColor = MaterialTheme.colorScheme.onTertiaryContainer,
                items = biology,
                onClick = onExperimentClick
            )
        }

        item { Spacer(modifier = Modifier.height(100.dp)) }
    }
}

@Composable
fun SDGCard() {

    var expanded by remember { mutableStateOf(false) }

    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFFFD6E8) // pastel pink
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {

        Column(modifier = Modifier.padding(16.dp)) {

            // HEADER
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { expanded = !expanded },
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = "SDG 4: Quality Education",
                    modifier = Modifier.weight(1f),
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    color = Color(0xFF4A2C3A)
                )

                Icon(
                    imageVector = if (expanded)
                        Icons.Default.ExpandLess
                    else Icons.Default.ExpandMore,
                    contentDescription = null,
                    tint = Color(0xFF4A2C3A)
                )
            }

            Text(
                text = "Interactive science learning platform for better understanding of experiments.",
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xFF4A2C3A).copy(alpha = 0.85f),
                modifier = Modifier.padding(top = 6.dp)
            )

            AnimatedVisibility(
                visible = expanded,
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically()
            ) {

                Column(modifier = Modifier.padding(top = 10.dp)) {

                    Text(
                        text = "Problem:",
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF4A2C3A)
                    )

                    Text(
                        text = "Students struggle to understand science experiments due to lack of interactive learning.",
                        color = Color(0xFF4A2C3A)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Impact:",
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF4A2C3A)
                    )

                    Text(
                        text = "Low engagement leads to weak understanding of scientific concepts.",
                        color = Color(0xFF4A2C3A)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Solution:",
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF4A2C3A)
                    )

                    Text(
                        text = "LabQuest provides interactive experiments and teacher interaction for better learning.",
                        color = Color(0xFF4A2C3A)
                    )
                }
            }
        }
    }
}

@Composable
fun SimpleCategoryCard(
    title: String,
    color: Color,
    contentColor: Color,
    items: List<Pair<String, String>>,
    onClick: (String) -> Unit
) {

    var expanded by rememberSaveable { mutableStateOf(false) }

    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = color),
        modifier = Modifier.fillMaxWidth()
    ) {

        Column(modifier = Modifier.padding(16.dp)) {

            Row(verticalAlignment = Alignment.CenterVertically) {

                Text(
                    text = title,
                    color = contentColor,
                    modifier = Modifier.weight(1f),
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold
                    )
                )

                IconButton(onClick = { expanded = !expanded }) {
                    Icon(
                        imageVector = if (expanded)
                            Icons.Default.ExpandLess
                        else Icons.Default.ExpandMore,
                        contentDescription = null,
                        tint = contentColor
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
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurface
                                )

                                Text(
                                    text = desc,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

