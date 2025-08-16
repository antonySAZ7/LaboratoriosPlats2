//Antony David saz 24710
// Laboratorio 5


package com.example.laboratorio5



import androidx.compose.foundation.layout.statusBarsPadding
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.widget.Space
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Autorenew
import androidx.compose.material.icons.filled.Directions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale

//para esta funcion tuve que pedirle ayuda a chatGPT para que abriera el mapa en la dirrecion el restaurante OOKI
private fun openNavigationToLatLng(context: Context, lat: Double, lng: Double, mode: Char = 'd') {
    val uri = Uri.parse("google.navigation?q=$lat,$lng&mode=$mode")
    val intent = Intent(Intent.ACTION_VIEW, uri).apply {
        setPackage("com.google.android.apps.maps")
    }
    try {
        context.startActivity(intent)
    } catch (_: Exception) {
        context.startActivity(Intent(Intent.ACTION_VIEW, uri))
    }
}


private fun abrirPlayStore(context: Context, packageName: String){
    val uri = Uri.parse("https://play.google.com/store/apps/details?id=$packageName")
    context.startActivity(Intent(Intent.ACTION_VIEW, uri))
}

private fun abrirmap(context: Context, x: Double, y:Double, z:String){
    val query = Uri.encode("$x,$y ($z)")
    val gmmUri = Uri.parse("geo:$x,$y?q=$query")
    context.startActivity(Intent(Intent.ACTION_VIEW, gmmUri))
}

private fun mostrarToast(context: Context, mensaje: String){
    Toast.makeText(context, mensaje, Toast.LENGTH_LONG).show()
}


@Composable
fun actBanner(onDescargar: () -> Unit){
    Surface(
        color = MaterialTheme.colorScheme.surfaceVariant,
        shape = MaterialTheme.shapes.medium
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primaryContainer),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.Autorenew, contentDescription = null)
                }


                Spacer(Modifier.width(12.dp))
                Text(
                    "Actualizacion disponible",
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            TextButton(onClick = onDescargar) { Text("Descargar") }
        }
    }
}

@Composable
fun cartaRestaurante( nombre: String,
                      direccion: String,
                      horario: String,
                      onIniciar: () -> Unit,
                      onDetalles: () -> Unit,
                      onDirections: () -> Unit){

    Card(shape = MaterialTheme.shapes.large, modifier = Modifier.fillMaxWidth( )){
        Box(Modifier.fillMaxWidth()){
            Column(Modifier.padding(20.dp)){
                Text(nombre, style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.ExtraBold)
                Spacer(Modifier.height(8.dp))
                Text(direccion, style = MaterialTheme.typography.bodyLarge)
                Spacer(Modifier.height(8.dp))
                Text(horario, style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Spacer(Modifier.height(16.dp))
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ){
                    Button(onClick = onIniciar){Text("Iniciar")}
                    TextButton(onClick = onDetalles) { Text("Detalles") }
                }
            }
            IconButton(
                onClick = onDirections,
                modifier = Modifier.align(Alignment.TopEnd).padding(8.dp)
            ){
                Icon(Icons.Filled.Directions, contentDescription = "Direcciones ")
            }


        }

    }

}

@Composable
fun MainScreen(
    onDescargar: () -> Unit,
    onIniciar: () -> Unit,
    onDirections: () -> Unit,
    onDetalles: () -> Unit
){
    val localeEs = Locale("es", "GT")


    val cumpleDia = 28
    val cumpleMes = 11
    val cumple = LocalDate.of(LocalDate.now().year, cumpleMes, cumpleDia)
    val textoDia = cumple
        .format(DateTimeFormatter.ofPattern("EEEE", localeEs))
        .replaceFirstChar { if (it.isLowerCase()) it.titlecase(localeEs) else it.toString() }
    val textoFecha = cumple.format(DateTimeFormatter.ofPattern("d 'de' MMMM", localeEs))

    val fechaCumple = LocalDate.of(LocalDate.now().year, cumpleMes, cumpleDia)
    val diaSemana = fechaCumple.dayOfWeek.getDisplayName(TextStyle.FULL, localeEs)
        .replaceFirstChar { if (it.isLowerCase()) it.titlecase(localeEs) else it.toString() }
    val fechaLarga = fechaCumple.format(DateTimeFormatter.ofPattern("d 'de' MMMM", localeEs))

    Column(Modifier.fillMaxSize().statusBarsPadding().padding(horizontal = 16.dp)) {
        Spacer(Modifier.height(12.dp))
        actBanner(onDescargar)

        Spacer(Modifier.height(24.dp))
        Text(diaSemana, style = MaterialTheme.typography.displaySmall, fontWeight = FontWeight.ExtraBold)
        Spacer(Modifier.height(8.dp))
        Text(fechaLarga, style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)

        Spacer(Modifier.height(24.dp))
        cartaRestaurante(
            nombre = "Han Guk Gwan",
            direccion = "Calzada Mateo Flores, Cdad. de Guatemala",
            horario = "11:00AM 9:00PM",
            onIniciar = onIniciar,
            onDetalles = onDetalles,
            onDirections = onDirections
        )
    }
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                val ctx = LocalContext.current
                Surface(Modifier.fillMaxSize()) {
                    MainScreen(
                        onDescargar = { abrirPlayStore(ctx, "com.whatsapp") },
                        onIniciar   = { mostrarToast(ctx, "Antony David saz") },
                        onDirections = { abrirmap(ctx, 14.632097272709881, -90.56071059694231, "Han Guk Gwan") },
                        onDetalles  = {
                            mostrarToast(
                                ctx,
                                "Tipo de comida: Coreana\n¿Qué tan caro es? normal"

                            )
                        }
                    )
                }
            }
        }
    }
}



@Composable
fun AppTheme(content: @Composable () -> Unit) {
    val scheme =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val ctx = LocalContext.current
            if (isSystemInDarkTheme()) dynamicDarkColorScheme(ctx) else dynamicLightColorScheme(ctx)
        } else {
            if (isSystemInDarkTheme()) darkColorScheme() else lightColorScheme()
        }

    MaterialTheme(colorScheme = scheme, typography = Typography(), content = content)
}


