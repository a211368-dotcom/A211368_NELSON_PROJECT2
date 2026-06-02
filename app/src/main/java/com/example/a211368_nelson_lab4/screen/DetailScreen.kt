package com.example.a211368_nelson_lab4.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.a211368_nelson_lab4.ui.theme.A211368_NELSON_LAB4Theme
import com.example.a211368_nelson_lab4.viewmodel.LabViewModel

// data model
data class ExperimentDetail(
    val title: String,
    val description: String,
    val objective: String,
    val materials: String,
    val steps: String
)

// data source
fun getExperimentDetail(name: String): ExperimentDetail {
    return when (name) {

        "Acid Reaction" -> ExperimentDetail(
            title = "Acid Reaction",
            description = "Study how acids react with metals.",
            objective = "To observe hydrogen gas formation when acid reacts with metal.",
            materials = "Zinc, Hydrochloric Acid, Test Tube",
            steps = "1. Add zinc into test tube\n2. Pour acid\n3. Observe bubbles"
        )

        "Acid-Base Titration" -> ExperimentDetail(
            title = "Acid-Base Titration",
            description = "Determine concentration using neutralization.",
            objective = "To determine unknown concentration using titration.",
            materials = "Burette, Indicator, Acid, Base",
            steps = "1. Fill burette\n2. Add indicator\n3. Titrate until color change"
        )

        "Electrolysis" -> ExperimentDetail(
            title = "Electrolysis",
            description = "Break compounds using electricity.",
            objective = "To observe decomposition using electrical energy.",
            materials = "Battery, Electrodes, Electrolyte",
            steps = "1. Setup circuit\n2. Insert electrodes\n3. Observe reaction"
        )

        "Pendulum Motion" -> ExperimentDetail(
            title = "Pendulum Motion",
            description = "Investigate oscillation.",
            objective = "To study relationship between length and period.",
            materials = "String, Weight, Stopwatch",
            steps = "1. Setup pendulum\n2. Measure swings\n3. Record time"
        )

        else -> ExperimentDetail(
            title = name,
            description = "No description available",
            objective = "-",
            materials = "-",
            steps = "-"
        )
    }
}

@Composable
fun DetailScreen(
    modifier: Modifier = Modifier,
    viewModel: LabViewModel,
    onBack: () -> Unit = {},
    onNext: () -> Unit = {}
) {

    val experimentName = viewModel.userData.experiment
    val detail = getExperimentDetail(experimentName)


    val gradient = Brush.verticalGradient(
        listOf(
            MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.7f),
            MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.4f),
            MaterialTheme.colorScheme.surface
        )
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(gradient)
            .padding(20.dp)
    ) {

        // header
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(top = 10.dp, bottom = 20.dp)
        ) {

            IconButton(
                onClick = onBack,
                modifier = Modifier
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.8f))
            ) {
                Icon(
                    Icons.Default.ArrowBack,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column {
                Text(
                    text = "Experiment Detail",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    color = MaterialTheme.colorScheme.onBackground
                )

                Text(
                    text = detail.description,
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
                )
            }
        }

        // main card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            ),
            elevation = CardDefaults.cardElevation(6.dp)
        ) {

            Column(modifier = Modifier.padding(20.dp)) {

                Row(verticalAlignment = Alignment.CenterVertically) {

                    Icon(
                        Icons.Default.School,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )

                    Spacer(modifier = Modifier.width(10.dp))

                    Text(
                        text = detail.title,
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // objective
                Text("Objective", fontWeight = FontWeight.Bold)
                Text(
                    detail.objective,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(10.dp))

                // materials
                Text("Materials", fontWeight = FontWeight.Bold)
                Text(
                    detail.materials,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(10.dp))

                // steps
                Text("Steps", fontWeight = FontWeight.Bold)
                Text(
                    detail.steps,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(16.dp))

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = onNext,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text("Continue")
                }
            }
        }

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.5f)
            )
        ) {
            Column(modifier = Modifier.padding(16.dp)) {

                Row(verticalAlignment = Alignment.CenterVertically) {

                    Icon(
                        Icons.Default.Description,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )

                    Spacer(modifier = Modifier.width(10.dp))

                    Text(
                        text = "Tips",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "• Be clear and concise\n• Include observations\n• Write conclusion based on results",
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

