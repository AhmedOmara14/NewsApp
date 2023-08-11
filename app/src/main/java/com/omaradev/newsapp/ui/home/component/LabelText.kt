package com.omaradev.newsapp.ui.home.component

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.omaradev.newsapp.R

@Composable
fun LabelText(label: String, modifier: Modifier) {
    Text(
        modifier = modifier,
        textAlign = TextAlign.Center,
        text = label,
        style = TextStyle(
            color = Color.Black,
            fontSize = 20.sp,
            fontFamily = FontFamily(Font(R.font.cairobold))
        )
    )
}