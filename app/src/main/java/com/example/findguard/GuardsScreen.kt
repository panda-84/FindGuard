package com.example.findguard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.findguard.ui.theme.NeonGreen

@Composable
fun GuardsScreen() {
    var profileGuard by remember { mutableStateOf<GuardData?>(null) }
    var receiptGuard by remember { mutableStateOf<GuardData?>(null) }

    val bookedNames     = BookingState.bookedGuards.map { it.name }.toSet()
    val availableGuards = allGuardsFlat.filter { it.name !in bookedNames }

    if (availableGuards.isEmpty()) {
        Box(
            modifier         = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                "All guards have been booked! 🎉",
                color      = NeonGreen,
                fontWeight = FontWeight.Bold,
                textAlign  = TextAlign.Center
            )
        }
    } else {
        LazyVerticalGrid(
            columns               = GridCells.Fixed(2),
            modifier              = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement   = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(availableGuards, key = { it.name }) { guard ->
                GuardCard(guard = guard, onClick = { profileGuard = guard })
            }
        }
    }

    profileGuard?.let { guard ->
        GuardProfileDialog(
            guard     = guard,
            onDismiss = { profileGuard = null },
            onBook    = {
                profileGuard = null
                receiptGuard = guard
            }
        )
    }

    receiptGuard?.let { guard ->
        ReceiptDialog(
            guard     = guard,
            onDismiss = {
                BookingState.bookedGuards.add(guard)
                receiptGuard = null
            }
        )
    }
}