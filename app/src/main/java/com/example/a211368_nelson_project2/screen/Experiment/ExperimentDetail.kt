package com.example.a211368_nelson_project2.screen.Experiment

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.example.a211368_nelson_project2.R
import com.example.a211368_nelson_project2.viewmodel.LabViewModel

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
    var capturedImage by remember { mutableStateOf<Bitmap?>(null) }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap ->
        capturedImage = bitmap
    }

    val context = LocalContext.current

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            cameraLauncher.launch(null)
        }
    }

    val progress = if (stepsList.isNotEmpty()) {
        checkedSteps.count { it } / stepsList.size.toFloat()
    } else 0f

    val bgGradient = Brush.verticalGradient(
        listOf(
            MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.28f),
            MaterialTheme.colorScheme.surfaceContainerLowest,
            MaterialTheme.colorScheme.surfaceContainerLowest
        )
    )

    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(bgGradient)
            .verticalScroll(scrollState)
            .padding(20.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(
                onClick = onBack,
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    contentColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Icon(Icons.Default.ArrowBack, contentDescription = null)
            }

            Spacer(Modifier.width(12.dp))

            Column {
                Text(
                    text = "Experiment Detail",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.ExtraBold
                    ),
                    color = MaterialTheme.colorScheme.onBackground
                )

                Text(
                    text = detail.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        Spacer(Modifier.height(18.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(30.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primary
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(modifier = Modifier.padding(22.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.16f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            Icons.Default.School,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }

                    Spacer(Modifier.width(14.dp))

                    Column {
                        Text(
                            text = detail.title,
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.ExtraBold
                            ),
                            color = MaterialTheme.colorScheme.onPrimary
                        )

                        Text(
                            text = "Complete all steps to continue",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.85f)
                        )
                    }
                }

                Spacer(Modifier.height(18.dp))

                LinearProgressIndicator(
                    progress = progress,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp)
                        .clip(RoundedCornerShape(50)),
                    color = MaterialTheme.colorScheme.secondary,
                    trackColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.18f)
                )

                Spacer(Modifier.height(8.dp))

                Text(
                    text = "${checkedSteps.count { it }} of ${stepsList.size} steps completed",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.9f)
                )
            }
        }

        Spacer(Modifier.height(16.dp))

        Card(
            shape = RoundedCornerShape(28.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
            border = BorderStroke(
                1.dp,
                MaterialTheme.colorScheme.outlineVariant
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(Modifier.padding(18.dp)) {
                Section("Objective", detail.objective)
                Section("Materials", detail.materials)

                Spacer(Modifier.height(12.dp))

                Text(
                    text = "Steps",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(Modifier.height(8.dp))

                stepsList.forEachIndexed { i, step ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp),
                        shape = RoundedCornerShape(18.dp),
                        colors = CardDefaults.cardColors(
                            containerColor =
                                if (checkedSteps[i])
                                    MaterialTheme.colorScheme.primaryContainer
                                else
                                    MaterialTheme.colorScheme.surfaceContainerLow
                        ),
                        border = BorderStroke(
                            1.dp,
                            if (checkedSteps[i])
                                MaterialTheme.colorScheme.primary.copy(alpha = 0.35f)
                            else
                                MaterialTheme.colorScheme.outlineVariant
                        )
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(8.dp)
                        ) {
                            Checkbox(
                                checked = checkedSteps[i],
                                onCheckedChange = { checkedSteps[i] = it },
                                colors = CheckboxDefaults.colors(
                                    checkedColor = MaterialTheme.colorScheme.primary
                                )
                            )

                            Text(
                                text = step,
                                style = MaterialTheme.typography.bodyMedium,
                                color =
                                    if (checkedSteps[i])
                                        MaterialTheme.colorScheme.onPrimaryContainer
                                    else
                                        MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }

                Spacer(Modifier.height(10.dp))

                Card(
                    shape = RoundedCornerShape(22.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceContainerLow
                    ),
                    border = BorderStroke(
                        1.dp,
                        MaterialTheme.colorScheme.outlineVariant
                    )
                ) {
                    Column(Modifier.padding(14.dp)) {
                        Text(
                            text = "Observation",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold
                            ),
                            color = MaterialTheme.colorScheme.onSurface
                        )

                        Spacer(Modifier.height(10.dp))

                        OutlinedTextField(
                            value = observation,
                            onValueChange = { observation = it },
                            modifier = Modifier.fillMaxWidth(),
                            placeholder = { Text("Write observation...") },
                            shape = RoundedCornerShape(18.dp),
                            minLines = 3
                        )
                    }
                }

                Spacer(Modifier.height(14.dp))

                Button(
                    onClick = {
                        val permissionCheckResult =
                            ContextCompat.checkSelfPermission(
                                context,
                                Manifest.permission.CAMERA
                            )

                        if (permissionCheckResult == PackageManager.PERMISSION_GRANTED) {
                            cameraLauncher.launch(null)
                        } else {
                            permissionLauncher.launch(Manifest.permission.CAMERA)
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(50),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondary,
                        contentColor = MaterialTheme.colorScheme.onSecondary
                    )
                ) {
                    Icon(Icons.Default.CameraAlt, contentDescription = null)
                    Spacer(Modifier.width(8.dp))
                    Text("Capture Experiment Evidence")
                }

                capturedImage?.let { bitmap ->
                    Spacer(Modifier.height(14.dp))

                    Card(
                        shape = RoundedCornerShape(22.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceContainerLow
                        ),
                        border = BorderStroke(
                            1.dp,
                            MaterialTheme.colorScheme.outlineVariant
                        ),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.padding(14.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                "Captured Evidence",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold
                                ),
                                color = MaterialTheme.colorScheme.primary
                            )

                            Spacer(Modifier.height(10.dp))

                            Image(
                                bitmap = bitmap.asImageBitmap(),
                                contentDescription = "Captured experiment evidence",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(220.dp)
                                    .clip(RoundedCornerShape(18.dp))
                            )
                        }
                    }
                }

                Spacer(Modifier.height(12.dp))

                OutlinedButton(
                    onClick = { showResult = true },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(50)
                ) {
                    Text("Show Result")
                }

                AnimatedVisibility(
                    visible = showResult,
                    enter = fadeIn() + expandVertically(),
                    exit = fadeOut() + shrinkVertically()
                ) {
                    Column {
                        Spacer(Modifier.height(14.dp))

                        Card(
                            shape = RoundedCornerShape(22.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.surfaceContainerLow
                            ),
                            border = BorderStroke(
                                1.dp,
                                MaterialTheme.colorScheme.outlineVariant
                            ),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(
                                modifier = Modifier.padding(14.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    "Expected Outcome",
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontWeight = FontWeight.Bold
                                    ),
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
                                        .clip(RoundedCornerShape(18.dp))
                                )
                            }
                        }
                    }
                }

                Spacer(Modifier.height(14.dp))

                Button(
                    onClick = onNext,
                    enabled = checkedSteps.all { it },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(50)
                ) {
                    Text("Continue")
                }
            }
        }

        Spacer(Modifier.height(16.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(28.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.tertiaryContainer
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 3.dp
            ),
            border = BorderStroke(
                1.dp,
                MaterialTheme.colorScheme.outlineVariant
            )
        ) {
            Column(Modifier.padding(18.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Default.Description,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onTertiaryContainer
                    )

                    Spacer(Modifier.width(10.dp))

                    Text(
                        text = "Tips",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        color = MaterialTheme.colorScheme.onTertiaryContainer
                    )
                }

                Spacer(Modifier.height(8.dp))

                Text(
                    "• Be clear\n• Record observation\n• Make conclusion",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onTertiaryContainer.copy(alpha = 0.85f)
                )
            }
        }

        Spacer(Modifier.height(24.dp))
    }
}

@Composable
fun Section(title: String, content: String) {
    Column(Modifier.padding(vertical = 8.dp)) {
        Text(
            text = title,
            style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = FontWeight.Bold
            ),
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(Modifier.height(3.dp))

        Text(
            text = content,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}