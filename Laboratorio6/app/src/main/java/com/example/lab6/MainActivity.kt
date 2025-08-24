//Laboratorio 6
//antony saz 24710


package com.example.lab6


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme(colorScheme = lightColorScheme()) {
                Surface(Modifier.fillMaxSize()) {
                    CounterScreen(nombre = "Antony David saz ")
                }
            }
        }
    }
}

@Composable
fun CounterScreen(nombre: String) {

    var value by rememberSaveable { mutableIntStateOf(0) }
    var incs by rememberSaveable { mutableIntStateOf(0) }
    var decs by rememberSaveable { mutableIntStateOf(0) }
    var maxVal by rememberSaveable { mutableIntStateOf(0) }
    var minVal by rememberSaveable { mutableIntStateOf(0) }
    var changes by rememberSaveable { mutableIntStateOf(0) }

    val history = rememberSaveable(saver = listSaver(
        save = { it.toList() }, restore = { it.toMutableStateList() }
    )) { mutableStateListOf<String>() }

    fun pushHistory(newValue: Int, isInc: Boolean) {
        history.add(if (isInc) "I:$newValue" else "D:$newValue")
    }

    fun onIncrement() {
        value += 1
        incs += 1
        changes += 1
        if (changes == 1) {
            maxVal = value
            minVal = value
        } else {
            if (value > maxVal) maxVal = value
            if (value < minVal) minVal = value
        }
        pushHistory(value, true)
    }

    fun onDecrement() {
        value -= 1
        decs += 1
        changes += 1
        if (changes == 1) {
            maxVal = value
            minVal = value
        } else {
            if (value > maxVal) maxVal = value
            if (value < minVal) minVal = value
        }
        pushHistory(value, false)
    }

    fun onReset() {
        value = 0; incs = 0; decs = 0; maxVal = 0; minVal = 0; changes = 0
        history.clear()
    }


    Column(
        Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.safeDrawing)
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        Text(
            text = nombre,
            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.SemiBold)
        )


        Card(Modifier.fillMaxWidth(), shape = RoundedCornerShape(24.dp)) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                CircleIconButton(icon = Icons.Default.Clear, contentDesc = "Decrementar") { onDecrement() }
                Text(
                    text = value.toString(),
                    fontSize = 72.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1f)
                )
                CircleIconButton(icon = Icons.Default.Add, contentDesc = "Incrementar") { onIncrement() }
            }
        }

        Card(shape = RoundedCornerShape(20.dp)) {
            Column(Modifier.fillMaxWidth().padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                StatRow("Total incrementos:", incs)
                StatRow("Total decrementos:", decs)
                StatRow("Valor máximo:", maxVal)
                StatRow("Valor mínimo:", minVal)
                StatRow("Total cambios:", changes)
            }
        }


        Text("Historial:", style = MaterialTheme.typography.titleMedium)
        HistoryGrid(history)

        Spacer(Modifier.weight(1f))


        Button(
            onClick = { onReset() },
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
                .height(48.dp),
            shape = RoundedCornerShape(16.dp)
        ) { Text("Reiniciar") }
    }
}

@Composable
private fun CircleIconButton(icon: androidx.compose.ui.graphics.vector.ImageVector, contentDesc: String, onClick: () -> Unit) {
    FilledTonalButton(
        onClick = onClick,
        modifier = Modifier.size(56.dp).clip(CircleShape),
        shape = CircleShape,
        contentPadding = PaddingValues(0.dp)
    ) { Icon(icon, contentDescription = contentDesc) }
}

@Composable
private fun StatRow(label: String, value: Int) {
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
        Text(label, style = MaterialTheme.typography.bodyLarge)
        Text(value.toString(), style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold)
    }
}

@Composable
private fun HistoryGrid(history: List<String>) {

    LazyVerticalGrid(
        columns = GridCells.Fixed(5),
        modifier = Modifier.fillMaxWidth().heightIn(min = 60.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        userScrollEnabled = true
    ) {
        items(history) { item ->
            val parts = item.split(":")
            val isInc = parts.firstOrNull() == "I"
            val num = parts.getOrNull(1) ?: "?"
            val bg = if (isInc) Color(0xFF2E7D32) else Color(0xFFC62828)
            Box(
                modifier = Modifier
                    .height(36.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(bg)
                    .padding(horizontal = 10.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(text = num, color = Color.White, fontWeight = FontWeight.SemiBold)
            }
        }
    }
}
