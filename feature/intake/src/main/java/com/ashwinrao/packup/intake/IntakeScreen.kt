package com.ashwinrao.packup.intake

import android.annotation.SuppressLint
import android.net.Uri
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign

@Composable
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
fun IntakeScreen(
    modifier: Modifier = Modifier,
    itemImageUri: Uri?,
    onExit: () -> Unit,
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),

        ) {
        // todo: add real stuff
        Text(
            text = itemImageUri?.path ?: "...",
            textAlign = TextAlign.Center
        )
    }
}