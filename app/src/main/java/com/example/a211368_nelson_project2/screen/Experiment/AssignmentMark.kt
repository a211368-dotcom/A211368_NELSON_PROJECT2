package com.example.a211368_nelson_project2.screen.Experiment

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun AssignmentScreen(
    onBack: () -> Unit = {}
) {


    var mark1 by rememberSaveable { mutableStateOf("") }
    var mark2 by rememberSaveable { mutableStateOf("") }
    var mark3 by rememberSaveable { mutableStateOf("") }
    var result by rememberSaveable { mutableStateOf("") }

    val pastelBg = Brush.verticalGradient(
        colors = listOf(
            MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.35f),
            MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.25f),
            MaterialTheme.colorScheme.tertiaryContainer.copy(alpha = 0.25f)
        )
    )

    val isInputValid =
        mark1.isNotBlank() &&
                mark2.isNotBlank() &&
                mark3.isNotBlank()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(pastelBg)
            .padding(20.dp)
    ) {

        // HEADER
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {

            IconButton(
                onClick = onBack,
                modifier = Modifier
                    .background(
                        MaterialTheme.colorScheme.surface.copy(alpha = 0.8f),
                        RoundedCornerShape(14.dp)
                    )
            ) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
            }

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                text = "Assignment Calculator",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.ExtraBold,
                color = MaterialTheme.colorScheme.onBackground
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // CARD INPUT
        Card(
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.95f)
            ),
            elevation = CardDefaults.cardElevation(6.dp),
            modifier = Modifier.fillMaxWidth()
        ) {

            Column(
                modifier = Modifier.padding(18.dp)
            ) {

                Text(
                    text = "Enter Your Marks",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.height(12.dp))

                MarkField("Assignment 1", mark1) { mark1 = it }
                MarkField("Assignment 2", mark2) { mark2 = it }
                MarkField("Assignment 3", mark3) { mark3 = it }

                Spacer(modifier = Modifier.height(14.dp))

                Button(
                    onClick = {
                        val m1 = mark1.toFloatOrNull() ?: 0f
                        val m2 = mark2.toFloatOrNull() ?: 0f
                        val m3 = mark3.toFloatOrNull() ?: 0f

                        val avg = (m1 + m2 + m3) / 3

                        val grade = when {
                            avg >= 80 -> "A"
                            avg >= 65 -> "B"
                            avg >= 50 -> "C"
                            else -> "D"
                        }

                        result = "Average: %.2f\nGrade: $grade".format(avg)
                    },
                    enabled = isInputValid,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Text(
                        "Calculate",
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // RESULT CARD (CLEAN + POP STYLE)
        AnimatedVisibility(visible = result.isNotEmpty()) {

            Card(
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                ),
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(6.dp)
            ) {

                Column(modifier = Modifier.padding(18.dp)) {

                    Text(
                        text = "Result",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = result,
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }
        }
    }
}

@Composable
fun MarkField(
    label: String,
    value: String,
    onChange: (String) -> Unit
) {

    OutlinedTextField(
        value = value,
        onValueChange = onChange,
        label = { Text(label) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        shape = RoundedCornerShape(16.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.surface,
            unfocusedContainerColor = MaterialTheme.colorScheme.surface,
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.4f)
        )
    )
}