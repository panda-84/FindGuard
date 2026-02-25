package com.example.findguard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.findguard.ui.theme.NeonBlue
import com.example.findguard.ui.theme.NeonGreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompanyScreen() {
    val companies = remember { allGuardsByCompany.keys.toList() }

    var expanded        by remember { mutableStateOf(false) }
    var selectedCompany by remember { mutableStateOf(companies.first()) }
    var profileGuard    by remember { mutableStateOf<GuardData?>(null) }
    var receiptGuard    by remember { mutableStateOf<GuardData?>(null) }

    val bookedNames     = BookingState.bookedGuards.map { it.name }.toSet()
    val availableGuards = (allGuardsByCompany[selectedCompany] ?: emptyList())
        .filter { it.name !in bookedNames }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Select Security Agency", color = NeonGreen, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(10.dp))

        ExposedDropdownMenuBox(
            expanded         = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            OutlinedTextField(
                value         = selectedCompany,
                onValueChange = {},
                readOnly      = true,
                label         = { Text("Agency Name", color = Color.Gray) },
                trailingIcon  = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
                colors        = OutlinedTextFieldDefaults.colors(
                    focusedTextColor     = Color.White,
                    unfocusedTextColor   = Color.White,
                    focusedBorderColor   = NeonBlue,
                    unfocusedBorderColor = NeonBlue.copy(alpha = 0.5f)
                ),
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth()
            )
            ExposedDropdownMenu(
                expanded         = expanded,
                onDismissRequest = { expanded = false },
                modifier         = Modifier.background(Color(0xFF1A1C1E))
            ) {
                companies.forEach { company ->
                    DropdownMenuItem(
                        text    = { Text(company, color = Color.White) },
                        onClick = {
                            selectedCompany = company
                            expanded        = false
                        }
                    )
                }
            }
        }

        Spacer(Modifier.height(16.dp))

        if (availableGuards.isEmpty()) {
            Box(
                modifier         = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    "All guards from this agency are booked.",
                    color     = Color.Gray,
                    textAlign = TextAlign.Center
                )
            }
        } else {
            LazyVerticalGrid(
                columns               = GridCells.Fixed(2),
                modifier              = Modifier.fillMaxSize(),
                verticalArrangement   = Arrangement.spacedBy(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(availableGuards, key = { it.name }) { guard ->
                    GuardCard(guard = guard, onClick = { profileGuard = guard })
                }
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