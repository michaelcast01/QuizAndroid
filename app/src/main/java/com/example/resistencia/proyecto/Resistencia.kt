package com.example.proyecto

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Resistencia(modifier: Modifier = Modifier) {
    // Definición de las bandas de color y sus equivalencias
    val colores = listOf("Negro", "Marrón", "Rojo", "Naranja", "Amarillo", "Verde", "Azul", "Violeta", "Gris", "Blanco")
    val valores = listOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9)
    val multiplicadores = listOf(1, 10, 100, 1000, 10000)
    val multiplicadorColores = listOf("Negro", "Marrón", "Rojo", "Naranja", "Amarillo")
    val tolerancias = listOf("Dorado", "Plateado", "Ninguno")

    // Tabla de tolerancia
    val toleranciaTexto = mapOf(
        "Dorado" to "±5%",
        "Plateado" to "±10%",
        "Ninguno" to "±20%"
    )

    // Capturar la selección del usuario
    var banda1 by remember { mutableStateOf(0) }
    var banda2 by remember { mutableStateOf(0) }
    var banda3 by remember { mutableStateOf(0) }
    var tolerancia by remember { mutableStateOf(tolerancias[0]) }

    // Cálculo
    val resultado = (valores[banda1] * 10 + valores[banda2]) * multiplicadores[banda3]

    Surface(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF1F8E9)), // Fondo
        color = Color(0xFFF1F8E9)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp, Alignment.Top),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                "🔌 Calculadora de Resistencia",
                fontSize = 24.sp,
                color = Color(0xFF2E7D32),
                fontWeight = FontWeight.Bold
            )

            // Selectores de bandas de colores
            StyledSelector("🎨 Banda 1", colores, banda1) { banda1 = it }
            StyledSelector("🎨 Banda 2", colores, banda2) { banda2 = it }
            StyledSelector("📏 Multiplicador", multiplicadorColores, banda3) { banda3 = it }

            // Selector para la tolerancia
            StyledSelector("⚙️ Tolerancia", tolerancias, tolerancias.indexOf(tolerancia)) {
                tolerancia = tolerancias[it]
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Mostrar resultado
            Text(
                "Resultado: $resultado Ω ${toleranciaTexto[tolerancia]}",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1B5E20)
            )
        }
    }
}

// Construir un dropdown estilizado
@Composable
fun StyledSelector(
    label: String,
    opciones: List<String>,
    selectedIndex: Int,
    onSelected: (Int) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Column(horizontalAlignment = Alignment.Start) {
        // Etiqueta estilizada para cada banda/tolerancia
        Text(
            label,
            fontSize = 18.sp,
            color = Color(0xFF33691E),
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(start = 6.dp, bottom = 6.dp)
        )

        // Botón visual
        OutlinedButton(
            onClick = { expanded = true },
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = Color(0xFFFFFDE7),
                contentColor = Color.Black
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        ) {
            Text(
                text = opciones[selectedIndex],
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }

        // Menú desplegable
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White, shape = RoundedCornerShape(12.dp))
                .padding(horizontal = 4.dp)
        ) {
            opciones.forEachIndexed { index, item ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = item,
                            fontWeight = if (index == selectedIndex) FontWeight.Bold else FontWeight.Normal,
                            color = if (index == selectedIndex) Color(0xFF2E7D32) else Color.Black
                        )
                    },
                    onClick = {
                        onSelected(index)
                        expanded = false
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            if (index == selectedIndex) Color(0xFFF1F8E9) else Color.Transparent,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(vertical = 6.dp)
                )
            }
        }
    }
}
