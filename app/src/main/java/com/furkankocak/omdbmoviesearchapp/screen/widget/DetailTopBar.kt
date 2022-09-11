package com.furkankocak.omdbmoviesearchapp.screen.widget

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun DetailTopBar(
    onExitButtonClick: () -> Unit
){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth().padding(16.dp)
    ) {
        OutlinedButton(  //POPUP ÇIKIŞ BUTONU
            onClick = onExitButtonClick, //POPUP pencere kapatma
            shape = CircleShape,
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.Black.copy(alpha = 0.5f),
                contentColor = Color.White
            ),
            modifier = Modifier
                .size(36.dp)
                .border(BorderStroke(1.dp, Color.White.copy(alpha = 0.6f)), CircleShape),
            contentPadding = PaddingValues(8.dp),
            elevation = ButtonDefaults.elevation(8.dp)
        ) {
            Icon(
                Icons.Default.ArrowBack,
                contentDescription = "Close"
            )
        }
        Spacer(Modifier.weight(1f))
        OutlinedButton(  //POPUP ÇIKIŞ BUTONU
            onClick = onExitButtonClick, //POPUP pencere kapatma
            shape = CircleShape,
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.Black.copy(alpha = 0.5f),
                contentColor = Color.White
            ),
            modifier = Modifier
                .size(36.dp)
                .border(BorderStroke(1.dp, Color.White.copy(alpha = 0.6f)), CircleShape),
            contentPadding = PaddingValues(8.dp)
        ) {
            Icon(
                Icons.Default.Star,
                contentDescription = "Close"
            )
        }

    }
}