package com.example.findguard

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Business
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Security
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.findguard.ui.theme.FindGuardTheme

class DashboardActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            FindGuardTheme {
                MainScreen()
            }
        }
    }
}

/* ---------- MAIN SCREEN ---------- */

@Composable
fun MainScreen() {
    var selectedIndex by remember { mutableIntStateOf(0) }

    Scaffold(
        bottomBar = {
            NavigationBar(containerColor = MaterialTheme.colorScheme.secondary) {
                NavigationBarItem(
                    selected = selectedIndex == 0,
                    onClick = { selectedIndex = 0 },
                    icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                    label = { Text("Home") }
                )

                NavigationBarItem(
                    selected = selectedIndex == 1,
                    onClick = { selectedIndex = 1 },
                    icon = { Icon(Icons.Default.Business, contentDescription = "Company") },
                    label = { Text("Company") }
                )

                NavigationBarItem(
                    selected = selectedIndex == 2,
                    onClick = { selectedIndex = 2 },
                    icon = { Icon(Icons.Default.Security, contentDescription = "Guards") },
                    label = { Text("Guards") }
                )
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            when (selectedIndex) {
                0 -> HomeScreen()
                1 -> CompanyScreen()
                2 -> GuardsScreen()
            }
        }
    }
}

/* ---------- HOME / INTRO ---------- */

@Composable
fun HomeScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Welcome to FindGuard 🛡️",
            style = MaterialTheme.typography.headlineLarge,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = "Your trusted security partner",
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center
        )
    }
}

/* ---------- COMPANY SCREEN ---------- */

@Composable
fun CompanyScreen() {
    val context = LocalContext.current

    // Sample data
    val companies = listOf("ABC Security Pvt. Ltd.", "XYZ Security Co.")
    val guardsByCompany = mapOf(
        "ABC Security Pvt. Ltd." to listOf(
            GuardData("Ram Bahadur", 5, 20, "Armed, First Aid, CCTV"),
            GuardData("Sita Shrestha", 3, 18, "First Aid, CCTV")
        ),
        "XYZ Security Co." to listOf(
            GuardData("Hari Lama", 4, 22, "Armed, CCTV"),
            GuardData("Gita Khatri", 2, 15, "CCTV, Night Shift")
        )
    )

    var expandedCompany by remember { mutableStateOf<String?>(null) }
    var selectedGuard by remember { mutableStateOf<GuardData?>(null) }
    var showDialog by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(
            text = "Security Companies",
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(16.dp))

        companies.forEach { company ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .padding(vertical = 4.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(6.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp)
                ) {
                    Text(
                        text = company,
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                expandedCompany =
                                    if (expandedCompany == company) null else company
                            }
                    )

                    if (expandedCompany == company) {
                        Spacer(modifier = Modifier.height(8.dp))
                        // Show guards in grid
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(3),
                            modifier = Modifier.height(150.dp),
                            content = {
                                items(guardsByCompany[company] ?: emptyList()) { guard ->
                                    Card(
                                        modifier = Modifier
                                            .padding(4.dp)
                                            .fillMaxSize()
                                            .clickable {
                                                selectedGuard = guard
                                                showDialog = true
                                            },
                                        colors = CardDefaults.cardColors(
                                            containerColor = MaterialTheme.colorScheme.primaryContainer
                                        ),
                                        elevation = CardDefaults.cardElevation(4.dp)
                                    ) {
                                        Box(
                                            contentAlignment = Alignment.Center,
                                            modifier = Modifier.fillMaxSize().padding(8.dp)
                                        ) {
                                            Text(
                                                text = guard.name,
                                                style = MaterialTheme.typography.bodyMedium,
                                                textAlign = TextAlign.Center
                                            )
                                        }
                                    }
                                }
                            }
                        )
                    }
                }
            }
        }
    }

    // Guard booking dialog
    selectedGuard?.let { guard ->
        if (showDialog) {
            BookingDialog(guard) { showDialog = false }
        }
    }
}

/* ---------- GUARDS SCREEN ---------- */

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun GuardsScreen() {
    val context = LocalContext.current

    val guards = listOf(
        GuardData("Ram Bahadur", 5, 20, "Armed, First Aid, CCTV"),
        GuardData("Sita Shrestha", 3, 18, "First Aid, CCTV"),
        GuardData("Hari Lama", 4, 22, "Armed, CCTV"),
        GuardData("Gita Khatri", 2, 15, "CCTV, Night Shift")
    )

    var selectedGuard by remember { mutableStateOf<GuardData?>(null) }
    var showDialog by remember { mutableStateOf(false) }

    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        content = {
            items(guards) { guard ->
                Card(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxSize()
                        .clickable {
                            selectedGuard = guard
                            showDialog = true
                        },
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(6.dp)
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .fillMaxSize()
                            .height(150.dp)
                            .width(100.dp)
                            .padding(16.dp)
                    ) {
                        Text(
                            text = guard.name,
                            style = MaterialTheme.typography.bodyMedium,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    )

    selectedGuard?.let { guard ->
        if (showDialog) {
            BookingDialog(guard) { showDialog = false }
        }
    }
}

/* ---------- BOOKING DIALOG ---------- */

@Composable
fun BookingDialog(guard: GuardData, onDismiss: () -> Unit) {
    val context = LocalContext.current

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Book ${guard.name}") },
        text = {
            Column {
                Text("Experience: ${guard.experience} years")
                Spacer(modifier = Modifier.height(4.dp))
                Text("Features: ${guard.features}")
                Spacer(modifier = Modifier.height(4.dp))
                Text("Price per hour: \$${guard.pricePerHour}")
                Spacer(modifier = Modifier.height(8.dp))
                Text("Click Confirm to book this guard.", style = MaterialTheme.typography.bodySmall)
            }
        },
        confirmButton = {
            TextButton(onClick = {
                onDismiss()
                Toast.makeText(context, "${guard.name} booked successfully!", Toast.LENGTH_SHORT).show()
            }) { Text("Confirm") }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancel") }
        }
    )
}

/* ---------- DATA CLASS ---------- */

data class GuardData(
    val name: String,
    val experience: Int,
    val pricePerHour: Int,
    val features: String
)

/* ---------- PREVIEW ---------- */

@Preview(showBackground = true)
@Composable
fun DashboardPreview() {
    FindGuardTheme {
        MainScreen()
    }
}