package com.example.a211368_nelson_project2.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.material3.ButtonDefaults
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

    var inputName by remember { mutableStateOf(viewModel.userData.name) }
    val isSubmitted = viewModel.isSubmitted

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surfaceContainerLowest)
            .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Spacer(modifier = Modifier.height(12.dp))

            val gradient = Brush.verticalGradient(
                colors = listOf(
                    MaterialTheme.colorScheme.primary,
                    MaterialTheme.colorScheme.primary.copy(alpha = 0.88f)
                )
            )

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(32.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Box(
                    modifier = Modifier
                        .background(gradient)
                        .padding(24.dp)
                ) {
                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End
                        ) {
                            IconButton(onClick = onToggleTheme) {
                                Icon(
                                    imageVector = if (isDarkTheme) Icons.Default.DarkMode else Icons.Default.LightMode,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.onPrimary
                                )
                            }
                        }

                        Text(
                            text = if (isSubmitted) "Welcome, $inputName" else "Let’s Explore",
                            style = MaterialTheme.typography.displayMedium.copy(
                                fontWeight = FontWeight.ExtraBold
                            ),
                            color = MaterialTheme.colorScheme.onPrimary
                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = if (isSubmitted) "Ready for today’s experiment?" else "Science Experiment Class",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.88f)
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        Button(
                            onClick = { },
                            shape = RoundedCornerShape(50),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.secondary,
                                contentColor = MaterialTheme.colorScheme.onSecondary
                            )
                        ) {
                            Text(
                                text = "View Experiments",
                                style = MaterialTheme.typography.labelLarge.copy(
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        }
                    }
                }
            }
        }


        item {
            Text(
                text = "Experiment Categories",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.ExtraBold
                ),
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(top = 4.dp)
            )
        }

        item {
            SimpleCategoryCard(
                title = "Chemistry",
                color = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                items = chemistry,
                onClick = onExperimentClick
            )
        }

        item {
            SimpleCategoryCard(
                title = "Physics",
                color = MaterialTheme.colorScheme.surfaceContainerHigh,
                contentColor = MaterialTheme.colorScheme.onSurface,
                items = physics,
                onClick = onExperimentClick
            )
        }

        item {
            SimpleCategoryCard(
                title = "Biology",
                color = MaterialTheme.colorScheme.tertiaryContainer,
                contentColor = MaterialTheme.colorScheme.onTertiaryContainer,
                items = biology,
                onClick = onExperimentClick
            )
        }

        item {
            Spacer(modifier = Modifier.height(100.dp))
        }
    }
}

@Composable
fun InfoText(title: String, desc: String) {
    Column(modifier = Modifier.padding(bottom = 10.dp)) {
        Text(
            text = title,
            style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = FontWeight.Bold
            ),
            color = MaterialTheme.colorScheme.primary
        )

        Text(
            text = desc,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
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
        shape = RoundedCornerShape(28.dp),
        colors = CardDefaults.cardColors(containerColor = color),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(18.dp)) {
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
                        imageVector = if (expanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
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
                    Spacer(modifier = Modifier.height(12.dp))

                    items.forEach { (name, desc) ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 10.dp)
                                .clickable { onClick(name) },
                            shape = RoundedCornerShape(22.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.surface
                            ),
                            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                            border = BorderStroke(
                                1.dp,
                                MaterialTheme.colorScheme.outlineVariant
                            )
                        ) {
                            Column(modifier = Modifier.padding(14.dp)) {
                                Text(
                                    text = name,
                                    style = MaterialTheme.typography.bodyLarge.copy(
                                        fontWeight = FontWeight.Bold
                                    ),
                                    color = MaterialTheme.colorScheme.onSurface
                                )

                                Spacer(modifier = Modifier.height(4.dp))

                                Text(
                                    text = desc,
                                    style = MaterialTheme.typography.bodySmall,
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