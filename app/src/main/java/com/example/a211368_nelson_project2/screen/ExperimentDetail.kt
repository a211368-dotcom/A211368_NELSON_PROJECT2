package com.example.a211368_nelson_project2.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.a211368_nelson_project2.viewmodel.LabViewModel
import com.example.a211368_nelson_project2.R

// data model
data class ExperimentDetail(
    val title: String,
    val description: String,
    val objective: String,
    val materials: String,
    val steps: String
)

fun getExperimentDetail(name: String): ExperimentDetail {
    return when (name) {

        "Acid Reaction" -> ExperimentDetail(
            "Acid Reaction",
            "Study how acids react with metals.",
            "To observe hydrogen gas formation when acid reacts with metal.",
            "Zinc, Hydrochloric Acid, Test Tube",
            "1. Add zinc into test tube\n2. Pour acid\n3. Observe bubbles"
        )

        "Acid-Base Titration" -> ExperimentDetail(
            "Acid-Base Titration",
            "Determine concentration using neutralization.",
            "To determine unknown concentration using titration.",
            "Burette, Indicator, Acid, Base",
            "1. Fill burette\n2. Add indicator\n3. Titrate until color change"
        )

        "Electrolysis" -> ExperimentDetail(
            "Electrolysis",
            "Break compounds using electricity.",
            "To observe decomposition using electrical energy.",
            "Battery, Electrodes, Electrolyte",
            "1. Setup circuit\n2. Insert electrodes\n3. Observe reaction"
        )

        "Pendulum Motion" -> ExperimentDetail(
            "Pendulum Motion",
            "Investigate oscillation.",
            "To study relationship between length and period.",
            "String, Weight, Stopwatch",
            "1. Setup pendulum\n2. Measure swings\n3. Record time"
        )

        else -> ExperimentDetail(name, "No description", "-", "-", "-")
    }
}

fun getResultImage(name: String): Int {
    return when (name) {
        "Acid Reaction" -> R.drawable.acid_result
        "Acid-Base Titration" -> R.drawable.titration_result
        "Electrolysis" -> R.drawable.electrolysis_result
        "Pendulum Motion" -> R.drawable.pendulum_result
        else -> R.drawable.placeholder
    }
}

@Composable
fun DetailScreen(
    modifier: Modifier = Modifier,
    viewModel: LabViewModel,
    onBack: () -> Unit = {},
    onNext: () -> Unit = {}
) {

    val detail = getExperimentDetail(viewModel.userData.experiment)

    val stepsList = detail.steps.split("\n")

    val checkedSteps = remember(detail) {
        mutableStateListOf<Boolean>().apply {
            repeat(stepsList.size) { add(false) }
        }
    }

    var observation by rememberSaveable { mutableStateOf("") }
    var showResult by rememberSaveable { mutableStateOf(false) }

    val progress = if (stepsList.isNotEmpty()) {
        checkedSteps.count { it } / stepsList.size.toFloat()
    } else 0f

    val bgGradient = Brush.verticalGradient(
        listOf(
            MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.35f),
            MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.25f),
            MaterialTheme.colorScheme.surface
        )
    )

    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .background(bgGradient)
            .padding(20.dp)
    ) {

        // HEADER
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onBack) {
                Icon(Icons.Default.ArrowBack, contentDescription = null)
            }

            Spacer(Modifier.width(10.dp))

            Column {
                Text("Experiment Detail", fontWeight = FontWeight.Bold)
                Text(detail.description)
            }
        }

        Spacer(Modifier.height(12.dp))

        LinearProgressIndicator(
            progress = progress,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(12.dp))

        Card(
            shape = RoundedCornerShape(24.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(Modifier.padding(16.dp)) {

                Row(verticalAlignment = Alignment.CenterVertically) {

                    Box(
                        modifier = Modifier
                            .size(42.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.primaryContainer),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.School, null)
                    }

                    Spacer(Modifier.width(10.dp))

                    Text(detail.title, fontWeight = FontWeight.Bold)
                }

                Spacer(Modifier.height(12.dp))

                Section("Objective", detail.objective)
                Section("Materials", detail.materials)

                Spacer(Modifier.height(10.dp))

                Text("Steps", fontWeight = FontWeight.Bold)

                stepsList.forEachIndexed { i, step ->

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(6.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(
                                if (checkedSteps[i])
                                    MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f)
                                else Color.Transparent
                            )
                    ) {
                        Checkbox(
                            checked = checkedSteps[i],
                            onCheckedChange = { checkedSteps[i] = it }
                        )

                        Text(step)
                    }
                }

                Spacer(Modifier.height(12.dp))

                Card {
                    Column(Modifier.padding(12.dp)) {
                        Text("Observation", fontWeight = FontWeight.Bold)

                        OutlinedTextField(
                            value = observation,
                            onValueChange = { observation = it },
                            modifier = Modifier.fillMaxWidth(),
                            placeholder = { Text("Write observation...") }
                        )
                    }
                }

                Spacer(Modifier.height(12.dp))

                Button(
                    onClick = { showResult = true },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Show Result")
                }

                if (showResult) {

                    Spacer(Modifier.height(12.dp))

                    Card(
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.padding(12.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {

                            Text(
                                "Expected Outcome",
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )

                            Spacer(Modifier.height(10.dp))

                            Image(
                                painter = painterResource(id = getResultImage(detail.title)),
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(220.dp)
                            )
                        }
                    }
                }

                Spacer(Modifier.height(12.dp))

                Button(
                    onClick = onNext,
                    enabled = checkedSteps.all { it },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Continue")
                }
            }
        }

        Spacer(Modifier.height(12.dp))

        Card {
            Column(Modifier.padding(12.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Description, null)
                    Spacer(Modifier.width(8.dp))
                    Text("Tips", fontWeight = FontWeight.Bold)
                }
                Spacer(Modifier.height(6.dp))
                Text("• Be clear\n• Record observation\n• Make conclusion")
            }
        }
    }
}

@Composable
fun Section(title: String, content: String) {
    Column(Modifier.padding(vertical = 6.dp)) {
        Text(title, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
        Text(content)
    }
}