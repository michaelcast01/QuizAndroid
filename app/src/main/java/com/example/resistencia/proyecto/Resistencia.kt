package com.example.proyecto

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Resistencia(modifier: Modifier) {
    val colores = listOf("Negro", "Marrón", "Rojo", "Naranja", "Amarillo", "Verde", "Azul", "Violeta", "Gris", "Blanco")
    val valores = listOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9)
    val multiplicadores = listOf(1, 10, 100, 1000, 10000)
    val multiplicadorColores = listOf("Negro", "Marrón", "Rojo", "Naranja", "Amarillo")
    val tolerancias = listOf("Dorado", "Plateado", "Ninguno")
    val toleranciaTexto = mapOf(
        "Dorado" to "±5%",
        "Plateado" to "±10%",
        "Ninguno" to "±20%"
    )

    var banda1 by remember { mutableStateOf(0) }
    var banda2 by remember { mutableStateOf(0) }
    var banda3 by remember { mutableStateOf(0) }
    var tolerancia by remember { mutableStateOf(tolerancias[0]) }

    val resultado = (valores[banda1] * 10 + valores[banda2]) * multiplicadores[banda3]

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF1F8E9)),
        color = Color(0xFFF1F8E9)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "🔌 Calculadora de Resistencia",
                fontSize = 24.sp,
                color = Color(0xFF2E7D32)
            )

            Selector("🎨 Banda 1", colores, banda1) { banda1 = it }
            Selector("🎨 Banda 2", colores, banda2) { banda2 = it }
            Selector("📏 Multiplicador", multiplicadorColores, banda3) { banda3 = it }
            Selector("⚙️ Tolerancia", tolerancias, tolerancias.indexOf(tolerancia)) {
                tolerancia = tolerancias[it]
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                "Resultado: $resultado Ω ${toleranciaTexto[tolerancia]}",
                fontSize = 20.sp,
                color = Color(0xFF1B5E20)
            )
        }
    }
}

@Composable
fun Selector(label: String, opciones: List<String>, selectedIndex: Int, onSelected: (Int) -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(label, fontSize = 16.sp, color = Color.DarkGray)
        OutlinedButton(
            onClick = { expanded = true },
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = Color(0xFFE8F5E9),
                contentColor = Color.Black
            ),
            modifier = Modifier.width(160.dp)
        ) {
            Text(opciones[selectedIndex], color = Color.Black)
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            opciones.forEachIndexed { index, item ->
                DropdownMenuItem(
                    text = { Text(item) },
                    onClick = {
                        onSelected(index)
                        expanded = false
                    }
                )
            }
        }
    }
}
