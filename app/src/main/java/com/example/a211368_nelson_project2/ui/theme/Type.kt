package com.example.a211368_nelson_project2.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import com.example.a211368_nelson_project2.R

// Fonts
val Poppins = FontFamily(
    Font(R.font.poppins_regular),
    Font(R.font.poppins_bold)
)

val Inter = FontFamily(
    Font(R.font.inter_regular),
    Font(R.font.inter_medium)
)

val AbrilFatFace = FontFamily(
    Font(R.font.abril_fatface_regular)
)

val Montserrat = FontFamily(
    Font(R.font.montserrat_regular),
    Font(R.font.montserrat_bold)
)



// Typography System
val Typography = Typography(
    displayLarge = TextStyle(
        fontFamily = AbrilFatFace,
        fontSize = 32.sp
    ),

    displayMedium = TextStyle(
        fontFamily = Montserrat,
        fontSize = 24.sp
    ),

    titleLarge = TextStyle(
        fontFamily = Poppins,
        fontSize = 20.sp
    ),

    titleMedium = TextStyle(
        fontFamily = Poppins,
        fontSize = 14.sp
    ),

    bodyLarge = TextStyle(
        fontFamily = Inter,
        fontSize = 16.sp
    ),

    bodyMedium = TextStyle(
        fontFamily = Inter,
        fontSize = 14.sp
    ),

    labelSmall = TextStyle(
        fontFamily = Inter,
        fontSize = 12.sp
    )
)