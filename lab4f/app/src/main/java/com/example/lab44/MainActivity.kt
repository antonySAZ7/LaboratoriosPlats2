package com.example.lab44

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.draw.alpha

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { CoverScreen() }
    }
}

@Composable
fun CoverScreen() {
    Surface(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
                .border(
                    BorderStroke(4.dp, Color(0xFF2E7D32)),
                    shape = RoundedCornerShape(0.dp)
                )
                .padding(24.dp)
        ) {
            // Marca de agua (logo UVG)
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo UVG",
                modifier = Modifier
                    .fillMaxSize()
                    .alpha(0.08f) // transparencia
                    .align(Alignment.Center)
            )

            // Contenido encima del logo
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement =  Arrangement.Center
            ) {
                Text(
                    text = "Universidad del Valle\nde Guatemala",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )

                Spacer(Modifier.height(16.dp))

                Text(
                    text = "Programación de plataformas\nmóviles, Sección 30",
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center
                )

                Spacer(Modifier.height(24.dp))

                LabeledBlock(
                    label = "INTEGRANTES",
                    lines = listOf(
                        "Antony Saz",
                        "Lionel messi",
                        "Lamine yamal"
                    )
                )

                Spacer(Modifier.height(16.dp))

                LabeledBlock(
                    label = "CATEDRÁTICO",
                    lines = listOf("Juan Carlos Durini")
                )

                Spacer(Modifier.height(48.dp))

                Text(
                    text = "Antony David saz",
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "24710",
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
private fun LabeledBlock(label: String, lines: List<String>) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = label,
            modifier = Modifier.weight(0.45f),
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
        )
        Column(modifier = Modifier.weight(0.55f)) {
            lines.forEach { line ->
                Text(text = line, fontSize = 14.sp)
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 360)
@Composable
private fun PreviewCover() {
    MaterialTheme { CoverScreen() }
}