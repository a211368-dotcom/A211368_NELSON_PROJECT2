package com.example.a211368_nelson_project2.screen

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.a211368_nelson_project2.viewmodel.LabViewModel

private val ClassroomBlue = Color(0xFF1967D2)
private val ClassroomGreen = Color(0xFF34A853)
private val ClassroomYellow = Color(0xFFFBBC04)
private val ClassroomRed = Color(0xFFEA4335)
private val ClassroomBg = Color(0xFFF8FAFD)
private val ClassroomCard = Color(0xFFFFFFFF)
private val ClassroomText = Color(0xFF1F1F1F)
private val ClassroomMuted = Color(0xFF5F6368)

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    viewModel: LabViewModel,
    userName: String,
    isDarkTheme: Boolean,
    onToggleTheme: () -> Unit,
    onBack: () -> Unit,
    onLogout: () -> Unit,
    isLoggedIn: Boolean = false
) {
    var selectedImageUri by rememberSaveable { mutableStateOf<Uri?>(null) }
    var email by rememberSaveable { mutableStateOf(viewModel.userData.email) }
    var age by rememberSaveable { mutableStateOf(viewModel.userData.age) }
    var studentClass by rememberSaveable { mutableStateOf(viewModel.userData.className) }

    var isEditMode by rememberSaveable { mutableStateOf(false) }
    var showSavedMessage by rememberSaveable { mutableStateOf(false) }

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        selectedImageUri = uri
        uri?.let {
            viewModel.updateProfileImage(it.toString())
        }
    }

    LaunchedEffect(
        viewModel.userData.email,
        viewModel.userData.age,
        viewModel.userData.className
    ) {
        email = viewModel.userData.email
        age = viewModel.userData.age
        studentClass = viewModel.userData.className
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(ClassroomBg)
            .verticalScroll(rememberScrollState())
    ) {
        ProfileHeader(
            userName = userName,
            studentClass = studentClass,
            selectedImageUri = selectedImageUri,
            savedImage = viewModel.profileImage,
            isLoggedIn = isLoggedIn,
            onBack = onBack,
            onPickImage = { imagePickerLauncher.launch("image/*") }
        )

        Column(
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 18.dp)
        ) {
            ProfileInfoCard(
                email = email,
                age = age,
                studentClass = studentClass,
                isEditMode = isEditMode,
                showSavedMessage = showSavedMessage,
                onEmailChange = {
                    email = it
                    showSavedMessage = false
                },
                onAgeChange = {
                    age = it
                    showSavedMessage = false
                },
                onClassChange = {
                    studentClass = it
                    showSavedMessage = false
                },
                onEditClick = {
                    isEditMode = true
                    showSavedMessage = false
                },
                onCancelClick = {
                    email = viewModel.userData.email
                    age = viewModel.userData.age
                    studentClass = viewModel.userData.className
                    isEditMode = false
                    showSavedMessage = false
                },
                onSaveClick = {
                    viewModel.updateProfile(
                        email = email,
                        age = age,
                        className = studentClass
                    )
                    isEditMode = false
                    showSavedMessage = true
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            PreferenceCard(
                isDarkTheme = isDarkTheme,
                onToggleTheme = onToggleTheme
            )

            Spacer(modifier = Modifier.height(16.dp))

            LogoutCard(onLogout = onLogout)

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
private fun ProfileHeader(
    userName: String,
    studentClass: String,
    selectedImageUri: Uri?,
    savedImage: String,
    isLoggedIn: Boolean,
    onBack: () -> Unit,
    onPickImage: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(310.dp)
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        ClassroomBlue,
                        Color(0xFF4285F4)
                    )
                )
            )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 42.dp, start = 18.dp, end = 18.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = onBack,
                modifier = Modifier
                    .size(44.dp)
                    .background(Color.White.copy(alpha = 0.18f), CircleShape)
            ) {
                Icon(
                    Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.White
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column {
                Text(
                    text = "Profile",
                    color = Color.White,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Google Classroom style student account",
                    color = Color.White.copy(alpha = 0.85f),
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }

        Card(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(horizontal = 20.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(28.dp),
            colors = CardDefaults.cardColors(containerColor = ClassroomCard),
            elevation = CardDefaults.cardElevation(10.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(contentAlignment = Alignment.BottomEnd) {
                    val imageToShow =
                        selectedImageUri ?: savedImage.takeIf { it.isNotBlank() }?.let { Uri.parse(it) }

                    if (imageToShow != null) {
                        AsyncImage(
                            model = imageToShow,
                            contentDescription = "Profile Image",
                            modifier = Modifier
                                .size(112.dp)
                                .clip(CircleShape)
                                .border(4.dp, ClassroomBlue.copy(alpha = 0.18f), CircleShape),
                            contentScale = ContentScale.Crop
                        )
                    } else {
                        Box(
                            modifier = Modifier
                                .size(112.dp)
                                .clip(CircleShape)
                                .background(Color(0xFFE8F0FE))
                                .border(4.dp, Color.White, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                Icons.Default.Person,
                                contentDescription = null,
                                modifier = Modifier.size(56.dp),
                                tint = ClassroomBlue
                            )
                        }
                    }

                    if (isLoggedIn) {
                        IconButton(
                            onClick = onPickImage,
                            modifier = Modifier
                                .size(42.dp)
                                .background(ClassroomGreen, CircleShape)
                                .border(3.dp, Color.White, CircleShape)
                        ) {
                            Icon(
                                Icons.Default.CameraAlt,
                                contentDescription = "Upload Profile Picture",
                                tint = Color.White
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(14.dp))

                Text(
                    text = userName.ifBlank { "Student" },
                    color = ClassroomText,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(4.dp))

                Surface(
                    shape = RoundedCornerShape(50),
                    color = Color(0xFFE8F0FE)
                ) {
                    Text(
                        text = studentClass.ifBlank { "No class assigned" },
                        color = ClassroomBlue,
                        style = MaterialTheme.typography.labelLarge,
                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun ProfileInfoCard(
    email: String,
    age: String,
    studentClass: String,
    isEditMode: Boolean,
    showSavedMessage: Boolean,
    onEmailChange: (String) -> Unit,
    onAgeChange: (String) -> Unit,
    onClassChange: (String) -> Unit,
    onEditClick: () -> Unit,
    onCancelClick: () -> Unit,
    onSaveClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(26.dp),
        colors = CardDefaults.cardColors(containerColor = ClassroomCard),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .clip(RoundedCornerShape(14.dp))
                        .background(Color(0xFFE8F0FE)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Default.Badge,
                        contentDescription = null,
                        tint = ClassroomBlue
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Personal Information",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = ClassroomText
                    )
                    Text(
                        text = if (isEditMode) "Editing mode enabled" else "Registered student details",
                        style = MaterialTheme.typography.bodySmall,
                        color = ClassroomMuted
                    )
                }

                if (!isEditMode) {
                    TextButton(onClick = onEditClick) {
                        Icon(Icons.Default.Edit, contentDescription = null)
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Edit")
                    }
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            ProfileTextField(
                value = email,
                onValueChange = onEmailChange,
                label = "Email",
                icon = Icons.Default.Email,
                enabled = isEditMode
            )

            Spacer(modifier = Modifier.height(12.dp))

            ProfileTextField(
                value = age,
                onValueChange = onAgeChange,
                label = "Age",
                icon = Icons.Default.Badge,
                enabled = isEditMode
            )

            Spacer(modifier = Modifier.height(12.dp))

            ProfileTextField(
                value = studentClass,
                onValueChange = onClassChange,
                label = "Class",
                icon = Icons.Default.School,
                enabled = isEditMode
            )

            if (isEditMode) {
                Spacer(modifier = Modifier.height(16.dp))

                Row(modifier = Modifier.fillMaxWidth()) {
                    OutlinedButton(
                        onClick = onCancelClick,
                        modifier = Modifier
                            .weight(1f)
                            .height(50.dp),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Text("Cancel")
                    }

                    Spacer(modifier = Modifier.width(10.dp))

                    Button(
                        onClick = onSaveClick,
                        modifier = Modifier
                            .weight(1f)
                            .height(50.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = ClassroomBlue
                        )
                    ) {
                        Icon(Icons.Default.Save, contentDescription = null)
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Save")
                    }
                }
            }

            if (showSavedMessage) {
                Spacer(modifier = Modifier.height(12.dp))

                Surface(
                    color = Color(0xFFE6F4EA),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Default.CheckCircle,
                            contentDescription = null,
                            tint = ClassroomGreen
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            "Profile updated successfully",
                            color = ClassroomGreen,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun PreferenceCard(
    isDarkTheme: Boolean,
    onToggleTheme: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(26.dp),
        colors = CardDefaults.cardColors(containerColor = ClassroomCard),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(Color(0xFFFFF8E1)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = if (isDarkTheme) Icons.Default.DarkMode else Icons.Default.WbSunny,
                    contentDescription = null,
                    tint = ClassroomYellow
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = if (isDarkTheme) "Dark Theme" else "Light Theme",
                    fontWeight = FontWeight.Bold,
                    color = ClassroomText
                )
                Text(
                    text = "Switch app appearance",
                    style = MaterialTheme.typography.bodySmall,
                    color = ClassroomMuted
                )
            }

            Switch(
                checked = isDarkTheme,
                onCheckedChange = { onToggleTheme() }
            )
        }
    }
}

@Composable
private fun LogoutCard(
    onLogout: () -> Unit
) {
    OutlinedButton(
        onClick = onLogout,
        modifier = Modifier
            .fillMaxWidth()
            .height(54.dp),
        shape = RoundedCornerShape(18.dp),
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = ClassroomRed
        )
    ) {
        Icon(Icons.Default.Logout, contentDescription = null)
        Spacer(modifier = Modifier.width(8.dp))
        Text("Logout", fontWeight = FontWeight.Bold)
    }
}

@Composable
private fun ProfileTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    enabled: Boolean
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        enabled = enabled,
        label = { Text(label) },
        leadingIcon = { Icon(icon, contentDescription = null) },
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        singleLine = true,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = ClassroomBlue,
            focusedLabelColor = ClassroomBlue,
            cursorColor = ClassroomBlue,
            disabledBorderColor = Color(0xFFE0E3EB),
            disabledTextColor = ClassroomText,
            disabledLabelColor = ClassroomMuted,
            disabledLeadingIconColor = ClassroomMuted
        )
    )
}