package com.example.findguard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.findguard.ui.theme.NeonBlue
import com.example.findguard.ui.theme.NeonGreen

/* ============================================================
   COMPANY SCREEN
   ============================================================ */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompanyScreen() {

    val companies = remember { allGuardsByCompany.keys.toList() }

    var selectedCompany by remember { mutableStateOf(companies.first()) }
    var profileGuard by remember { mutableStateOf<GuardData?>(null) }
    var receiptGuard by remember { mutableStateOf<GuardData?>(null) }

    val bookedNames = BookingState.bookedGuards.map { it.name }.toSet()

    val availableGuards = remember(selectedCompany, bookedNames) {
        allGuardsByCompany[selectedCompany]
            ?.filter { it.name !in bookedNames }
            ?: emptyList()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = "Select Security Agency",
            color = NeonGreen,
            fontWeight = FontWeight.Bold
        )

        Spacer(Modifier.height(10.dp))

        CompanyDropdown(
            companies = companies,
            selectedCompany = selectedCompany,
            onCompanySelected = { selectedCompany = it }
        )

        Spacer(Modifier.height(16.dp))

        if (availableGuards.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    "All guards from this agency are booked.",
                    color = Color.Gray,
                    textAlign = TextAlign.Center
                )
            }
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(availableGuards, key = { it.name }) { guard ->
                    GuardCard(
                        guard = guard,
                        onClick = { profileGuard = guard }
                    )
                }
            }
        }
    }

    /* ---------------- PROFILE POPUP ---------------- */

    profileGuard?.let { guard ->
        GuardProfileDialog(
            guard = guard,
            onDismiss = { profileGuard = null },
            onBook = {
                profileGuard = null
                receiptGuard = guard
            }
        )
    }

    /* ---------------- RECEIPT ---------------- */

    receiptGuard?.let { guard ->
        ReceiptDialog(
            guard = guard,
            onDismiss = {
                BookingState.bookedGuards.add(guard)
                receiptGuard = null
            }
        )
    }
}

/* ============================================================
   COMPANY DROPDOWN  (100% WORKING MATERIAL 3)
   ============================================================ */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompanyDropdown(
    companies: List<String>,
    selectedCompany: String,
    onCompanySelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = Modifier.fillMaxWidth()
    ) {

        OutlinedTextField(
            value = selectedCompany,
            onValueChange = {},
            readOnly = true,
            label = { Text("Agency Name") },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded)
            },
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                focusedBorderColor = NeonBlue,
                unfocusedBorderColor = NeonBlue.copy(alpha = 0.5f)
            )
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            companies.forEach { company ->
                DropdownMenuItem(
                    text = { Text(company) },
                    onClick = {
                        onCompanySelected(company)
                        expanded = false
                    }
                )
            }
        }
    }
}