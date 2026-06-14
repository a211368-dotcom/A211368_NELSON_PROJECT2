package com.example.a211368_nelson_project2.screen.Class

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Eco
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material.icons.filled.Science
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItemDefaults.contentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
import androidx.navigation.compose.rememberNavController
import com.example.a211368_nelson_project2.BottomNavigationBar
import com.example.a211368_nelson_project2.ui.theme.A211368_NELSON_PROJECT1Theme
import com.example.a211368_nelson_project2.viewmodel.LabViewModel

@Composable
fun ClassScreen(
    modifier: Modifier = Modifier,
    onBack: () -> Unit = {},
    onClassClick: (String, String, String) -> Unit = { _, _, _ -> }
) {
    // Data kelas dengan ikon yang berbeza untuk setiap subjek
    val classes = listOf(
        ClassItem("Physics 4 Zamrud", "Teacher Aisyah, 9 a.m., Tuesday.", Icons.Default.FlashOn),
        ClassItem("Biology 5 Utarid", "Sir Zakir, 2 p.m., Thursday.", Icons.Default.Eco),
        ClassItem("Chemistry 4 Zamrud", "Madam Mariam, 10 a.m., Friday.", Icons.Default.Science)
    )

    var classCode by remember { mutableStateOf("") }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
            .padding(horizontal = 20.dp)
    ) {
        // HEADER
        item {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.background(
                    MaterialTheme.colorScheme.surface,
                    CircleShape
                )
            ) {
                IconButton(
                    onClick = onBack,
                    modifier = Modifier.
                    background(Color.White, CircleShape)
                ) {
                    Icon(Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.Black)
                }
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "My Classes",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                )
            }
        }

        // join class card
        item {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 30.dp),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f)
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(
                        text = "Join Class",
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.SemiBold)
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(
                        value = classCode,
                        onValueChange = { classCode = it },
                        placeholder = { Text("Enter Class Code",
                            color = MaterialTheme.colorScheme.onSurfaceVariant) },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                            focusedContainerColor = MaterialTheme.colorScheme.surface,
                            unfocusedBorderColor = Color.Transparent,
                            focusedBorderColor = MaterialTheme.colorScheme.primary
                        )
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = { /* Join logic */ },
                        modifier = Modifier.fillMaxWidth().height(50.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF4F6D91) // Biru premium
                        )
                    ) {
                        Text(
                            "Join Class",
                            fontWeight = FontWeight.Bold,
                        color = contentColor
                        )
                    }
                }
            }
        }

        // section title
        item {
            Text(
                text = "Your Science Class",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.ExtraBold,
                    color = MaterialTheme.colorScheme.onSurface
                ),
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }

        // class list
        itemsIndexed(classes) { index, item ->
            val cardColor = when (index % 3) {
                0 -> MaterialTheme.colorScheme.primaryContainer
                1 -> MaterialTheme.colorScheme.secondaryContainer
                else -> MaterialTheme.colorScheme.tertiaryContainer
            }
            EnhancedClassCard(
                title = item.title,
                description = item.desc,
                icon = item.icon,
                bgColor = cardColor,
                onClick = {
                    val iconName = when (item.icon) {
                        Icons.Default.Eco -> "Eco"
                        Icons.Default.Science -> "Science"
                        else -> "FlashOn"
                    }

                    val category = when {
                        item.title.contains("Physics") -> "Physics"
                        item.title.contains("Biology") -> "Biology"
                        item.title.contains("Chemistry") -> "Chemistry"
                        else -> "Unknown"
                    }

                    onClassClick(item.title, item.desc, category)
                }
            )
            Spacer(modifier = Modifier.height(12.dp))
        }

        item { Spacer(modifier = Modifier.height(100.dp)) }
    }
}

@Composable
fun EnhancedClassCard(
    title: String,
    description: String,
    icon: ImageVector,
    bgColor: Color,
    onClick: () -> Unit
)
{
    var expanded by remember { mutableStateOf(false) }

    val contentColor = when (bgColor) {
        MaterialTheme.colorScheme.primaryContainer ->
            MaterialTheme.colorScheme.onPrimaryContainer

        MaterialTheme.colorScheme.secondaryContainer ->
            MaterialTheme.colorScheme.onSecondaryContainer

        else ->
            MaterialTheme.colorScheme.onTertiaryContainer
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
        .clickable { onClick() },
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = bgColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {

                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = contentColor,
                    modifier = Modifier.size(24.dp)
                )

                Spacer(modifier = Modifier.width(12.dp))

                Text(
                    text = title,
                    modifier = Modifier.weight(1f),
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    color = contentColor
                )

                IconButton(
                    onClick = { expanded = !expanded },
                    modifier = Modifier.size(32.dp).clip(CircleShape)
                    .background(MaterialTheme.colorScheme.surface)
                ) {
                    Icon(
                        imageVector = if (expanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurface
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
                    Divider(color = MaterialTheme.colorScheme.outlineVariant)
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = description,
                        style = MaterialTheme.typography.bodyMedium,
                        color = contentColor
                    )
                }
            }
        }
    }
}

data class ClassItem(val title: String, val desc: String, val icon: ImageVector)

@Preview(showBackground = true)
@Composable
fun ClassScreenPreview() {
    A211368_NELSON_PROJECT1Theme {
        Scaffold(
            floatingActionButton = {
                FloatingActionButton(onClick = {}) { Text("+", fontSize = 24.sp) }
            },
            bottomBar = { BottomNavigationBar(navController = rememberNavController()) }
        ) { innerPadding ->
            ClassScreen (
                modifier = Modifier.padding(innerPadding)
               )
        }
    }
}

@Composable
fun SummaryScreen(
    viewModel: LabViewModel,
    onBack: () -> Unit = {}
) {

    val pastelBg = Brush.Companion.verticalGradient(
        colors = listOf(
            MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f),
            MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.35f),
            MaterialTheme.colorScheme.tertiaryContainer.copy(alpha = 0.25f),
            MaterialTheme.colorScheme.surface
        )
    )

    val note = viewModel.userData.note
    val updated = viewModel.lastUpdated
    val journalEntries by viewModel.journalEntries.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(pastelBg)
            .padding(20.dp)
    ) {

        // HEADER
        Row {

            IconButton(
                onClick = onBack,
                modifier = Modifier
                    .clip(RoundedCornerShape(14.dp))
                    .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.9f))
            ) {
                Icon(Icons.Default.ArrowBack, contentDescription = null)
            }

            Spacer(modifier = Modifier.width(10.dp))

            Column {
                Text(
                    "Summary Report",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    "Your experiment reflection",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        Spacer(modifier = Modifier.height(18.dp))

        // MAIN NOTE CARD
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(26.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            ),
            elevation = CardDefaults.cardElevation(8.dp)
        ) {

            Column(modifier = Modifier.padding(20.dp)) {

                Row {
                    Icon(
                        Icons.Default.EditNote,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )

                    Spacer(modifier = Modifier.width(10.dp))

                    Text(
                        "Latest Note",
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = note.ifEmpty { "No notes yet." },
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(14.dp))

                Divider()

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    "Last Updated",
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )

                Text(
                    text = updated.ifEmpty { "Not updated yet" },
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // HISTORY CARD
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.5f)
            )
        ) {

            Column(modifier = Modifier.padding(16.dp)) {

                Text(
                    "History Notes",
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.height(10.dp))

                if (journalEntries.isEmpty()) {
                    Text(
                        "No history yet",
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                } else {
                    Column {
                        journalEntries.forEachIndexed { index, entry ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 8.dp),
                                shape = RoundedCornerShape(14.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = MaterialTheme.colorScheme.surface
                                )
                            ) {
                                Column(modifier = Modifier.padding(12.dp)) {
                                    Text(
                                        text = "${index + 1}. ${entry.experimentName}",
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(text = entry.note)
                                    Text(
                                        text = entry.date,
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                }
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(14.dp))

                // TIP CARD
                Card(
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.tertiaryContainer.copy(alpha = 0.6f)
                    )
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {

                        Text("Tip", fontWeight = FontWeight.Bold)

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            "Good reflection = explain what you learned, not just what happened.",
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}