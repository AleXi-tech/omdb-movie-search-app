package com.furkankocak.omdbmoviesearchapp.screen.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
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
        modifier = Modifier.fillMaxWidth().padding(8.dp)
    ) {
        OutlinedButton(  //POPUP ÇIKIŞ BUTONU
            onClick = onExitButtonClick, //POPUP pencere kapatma
            shape = CircleShape,
            colors = ButtonDefaults.buttonColors(
                backgroundColor = MaterialTheme.colors.primary,
                contentColor = Color.White
            ),
            modifier = Modifier.size(48.dp),
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
                backgroundColor = Color(255,184,28),
                contentColor = Color.White
            ),
            modifier = Modifier.size(48.dp),
            contentPadding = PaddingValues(8.dp)
        ) {
            Icon(
                Icons.Default.Star,
                contentDescription = "Close"
            )
        }

    }
}