package com.example.a211368_nelson_project1.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun ClassDetail(
    title: String,
    description: String,
    icon: ImageVector,
    category: String,
    navController: NavController,
    teacher: String = "Assignee Teacher",
    time: String = "Scheduled Time",
    onBack: () -> Unit = {}
) {

    val (assignments, announcements, materials) = when (category) {

        "Chemistry" -> Triple(
            listOf("Acid Report", "Lab Safety Quiz"),
            listOf("Bring lab coat", "Test next week"),
            listOf("Periodic Table PDF", "Lab Manual")
        )

        "Physics" -> Triple(
            listOf("Pendulum Report", "Force Calculation"),
            listOf("Experiment tomorrow", "Homework uploaded"),
            listOf("Motion Slides", "Formula Sheet")
        )

        "Biology" -> Triple(
            listOf("Plant Growth Journal", "Microscope Sketch"),
            listOf("Field trip soon", "Submit report Friday"),
            listOf("Cell Diagram", "Biology Notes")
        )

        else -> Triple(emptyList(), emptyList(), emptyList())
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(20.dp)
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {

            IconButton(
                onClick = onBack,
                modifier = Modifier
                    .size(42.dp)
                    .background(
                        MaterialTheme.colorScheme.surfaceVariant,
                        CircleShape
                    )
            ) {
                Icon(
                    Icons.Default.ArrowBack,
                    contentDescription = null
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                text = "Class Details",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold
                )
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(28.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer
            ),
            elevation = CardDefaults.cardElevation(2.dp)
        ) {

            Column(modifier = Modifier.padding(18.dp)) {

                Row(verticalAlignment = Alignment.CenterVertically) {

                    Icon(
                        icon,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onPrimaryContainer,
                        modifier = Modifier.size(30.dp)
                    )

                    Spacer(modifier = Modifier.width(10.dp))

                    Text(
                        title,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                InfoRow(Icons.Default.Person, teacher)
                Spacer(modifier = Modifier.height(6.dp))
                InfoRow(Icons.Default.CalendarToday, time)
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        SectionTitle("About Class")

        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                description,
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.bodyMedium
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        SectionTitle("Class Activities")

        ExpandableCard(
            title = "Assignments",
            items = assignments,
            containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.9f),
            accentColor = MaterialTheme.colorScheme.primary,
            onClick = {
                navController.navigate(LabScreen.Assignment.name)
            }
        )

        ExpandableCard(
            title = "Announcements",
            items = announcements,
            containerColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.9f),
            accentColor = MaterialTheme.colorScheme.secondary
        )

        ExpandableCard(
            title = "Materials",
            items = materials,
            containerColor = MaterialTheme.colorScheme.tertiaryContainer.copy(alpha = 0.9f),
            accentColor = MaterialTheme.colorScheme.tertiary
        )

        ExpandableCard(
            title = "Chat with Teacher",
            items = listOf("Message $teacher"),
            containerColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.9f),
            accentColor = MaterialTheme.colorScheme.secondary,
            onClick = {
                navController.navigate("chat_screen")
            }
        )
    }
}

@Composable
fun InfoRow(icon: ImageVector, text: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {

        Icon(
            icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onPrimaryContainer,
            modifier = Modifier.size(18.dp)
        )

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )
    }
}

@Composable
fun SectionTitle(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleMedium.copy(
            fontWeight = FontWeight.SemiBold
        ),
        modifier = Modifier.padding(bottom = 10.dp)
    )
}

@Composable
fun ExpandableCard(
    title: String,
    items: List<String>,
    containerColor: Color = MaterialTheme.colorScheme.primary.copy(alpha = 0.15f),
    accentColor: Color = MaterialTheme.colorScheme.primary,
    onClick: () -> Unit = {}
) {

    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 10.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = containerColor.copy(alpha = 1f)
        ),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {

        Column(
            modifier = Modifier
                .padding(14.dp)
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { expanded = !expanded },
                verticalAlignment = Alignment.CenterVertically
            ) {

                Box(
                    modifier = Modifier
                        .size(width = 6.dp, height = 22.dp)
                        .background(accentColor, RoundedCornerShape(50))
                )

                Spacer(modifier = Modifier.width(10.dp))

                Text(
                    text = title,
                    modifier = Modifier.weight(1f),
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontWeight = FontWeight.SemiBold
                    )
                )

                Icon(
                    imageVector = if (expanded)
                        Icons.Default.ExpandLess
                    else
                        Icons.Default.ExpandMore,
                    contentDescription = null,
                    tint = accentColor
                )
            }

            AnimatedVisibility(visible = expanded) {

                Column(modifier = Modifier.padding(top = 10.dp)) {

                    items.forEach { item ->

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onClick() }
                                .padding(vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Box(
                                modifier = Modifier
                                    .size(6.dp)
                                    .background(accentColor, CircleShape)
                            )

                            Spacer(modifier = Modifier.width(10.dp))

                            Text(
                                text = item,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }

                    if (title == "Assignments") {

                        Spacer(modifier = Modifier.height(12.dp))

                        Button(
                            onClick = onClick,
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(14.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = accentColor
                            )
                        ) {

                            Text(
                                text = "Calculate Assignment",
                                style = MaterialTheme.typography.labelLarge,
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                    }

                    if (title == "Chat with Teacher") {

                        Spacer(modifier = Modifier.height(12.dp))

                        Button(
                            onClick = onClick,
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(14.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = accentColor
                            )
                        ) {

                            Text(
                                text = "Open Chat",
                                style = MaterialTheme.typography.labelLarge,
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                    }
                }
            }
        }
    }
}