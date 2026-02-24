package com.example.findguard

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.findguard.ui.theme.DarkBg
import com.example.findguard.ui.theme.FindGuardTheme
import com.example.findguard.ui.theme.NeonBlue
import com.example.findguard.ui.theme.NeonGreen

/* ---------------- ACTIVITY ---------------- */

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

/* ---------------- MAIN SCREEN ---------------- */

@Composable
fun MainScreen() {
    var selectedIndex by remember { mutableIntStateOf(0) }

    Scaffold(
        containerColor = DarkBg,
        bottomBar = {
            NavigationBar(containerColor = Color.Black) {
                listOf(
                    Icons.Default.Home to "Home",
                    Icons.Default.Business to "Company",
                    Icons.Default.Security to "Guards"
                ).forEachIndexed { index, pair ->
                    NavigationBarItem(
                        selected = selectedIndex == index,
                        onClick = { selectedIndex = index },
                        icon = { Icon(pair.first, null, tint = NeonBlue) },
                        label = { Text(pair.second, color = NeonBlue) }
                    )
                }
            }
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(DarkBg)
        ) {
            when (selectedIndex) {
                0 -> HomeScreen()
                1 -> CompanyScreen()
                2 -> GuardsScreen()
            }
        }
    }
}

/* ---------------- HOME SCREEN ---------------- */

@Composable
fun HomeScreen() {
    val context = LocalContext.current
    val prefs = context.getSharedPreferences("auth", Context.MODE_PRIVATE)

    Box(modifier = Modifier.fillMaxSize().padding(20.dp)) {

        // 🔓 WORKING LOGOUT
        IconButton(
            modifier = Modifier.align(Alignment.TopEnd),
            onClick = {
                prefs.edit().clear().apply()

                context.startActivity(
                    Intent(context, LoginActivity::class.java)
                )
                (context as ComponentActivity).finish()
            }
        ) {
            Icon(Icons.Default.Logout, null, tint = NeonGreen)
        }

        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "FindGuard 🛡️",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.ExtraBold,
                color = NeonBlue
            )
            Spacer(Modifier.height(12.dp))
            Text(
                "Elite Security Services Across Nepal",
                color = Color.LightGray,
                textAlign = TextAlign.Center
            )
        }
    }
}

/* ---------------- COMPANY SCREEN ---------------- */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompanyScreen() {

    val companies = listOf(
        "ABC Security Pvt. Ltd.",
        "Himal Secure Service",
        "Everest Protection Group",
        "Nepal Guard Force"
    )

    val guardsByCompany = mapOf(
        "ABC Security Pvt. Ltd." to listOf(
            GuardData("Ram Bahadur", 6, 1800, "Day", "Kathmandu", 4.8f, "Armed"),
            GuardData("Sita Shrestha", 4, 1600, "Night", "Lalitpur", 4.6f, "CCTV")
        ),
        "Himal Secure Service" to listOf(
            GuardData("Hari Lama", 8, 2200, "Day", "Bhaktapur", 4.9f, "VIP"),
            GuardData("Gita Khatri", 3, 1400, "Night", "Kathmandu", 4.3f, "Night Patrol")
        ),
        "Everest Protection Group" to listOf(
            GuardData("Nabin Rai", 7, 2100, "Day", "Pokhara", 4.7f, "Armed"),
            GuardData("Sunita Gurung", 5, 1700, "Night", "Pokhara", 4.5f, "CCTV")
        ),
        "Nepal Guard Force" to listOf(
            GuardData("Bikash Thapa", 9, 2500, "Day", "Kathmandu", 5.0f, "VIP Escort")
        )
    )

    var expanded by remember { mutableStateOf(false) }
    var selectedCompany by remember { mutableStateOf(companies.first()) }
    var selectedGuard by remember { mutableStateOf<GuardData?>(null) }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {

        Text("Select Company", color = NeonGreen, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(10.dp))

        ExposedDropdownMenuBox(expanded, { expanded = !expanded }) {
            OutlinedTextField(
                value = selectedCompany,
                onValueChange = {},
                readOnly = true,
                label = { Text("Security Company") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
                modifier = Modifier.menuAnchor().fillMaxWidth()
            )

            ExposedDropdownMenu(expanded, { expanded = false }) {
                companies.forEach { company ->
                    DropdownMenuItem(
                        text = { Text(company) },
                        onClick = {
                            selectedCompany = company
                            expanded = false
                        }
                    )
                }
            }
        }

        Spacer(Modifier.height(16.dp))

        LazyVerticalGrid(columns = GridCells.Fixed(3)) {
            items(guardsByCompany[selectedCompany] ?: emptyList()) { guard ->
                GuardCard(guard) { selectedGuard = guard }
            }
        }
    }

    selectedGuard?.let {
        BookingDialog(it) { selectedGuard = null }
    }
}

/* ---------------- GUARDS SCREEN ---------------- */

@Composable
fun GuardsScreen() {
    val guards = listOf(
        GuardData("Ram Bahadur", 6, 1800, "Day", "Kathmandu", 4.8f, "Armed"),
        GuardData("Hari Lama", 8, 2200, "Day", "Bhaktapur", 4.9f, "VIP"),
        GuardData("Sunita Gurung", 5, 1700, "Night", "Pokhara", 4.5f, "CCTV"),
        GuardData("Bikash Thapa", 9, 2500, "Day", "Kathmandu", 5.0f, "VIP Escort"),
        GuardData("Nabin Rai", 7, 2100, "Day", "Pokhara", 4.7f, "Armed")
    )

    var selectedGuard by remember { mutableStateOf<GuardData?>(null) }

    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = Modifier.fillMaxSize().padding(16.dp)
    ) {
        items(guards) {
            GuardCard(it) { selectedGuard = it }
        }
    }

    selectedGuard?.let {
        BookingDialog(it) { selectedGuard = null }
    }
}

/* ---------------- GUARD CARD ---------------- */

@Composable
fun GuardCard(guard: GuardData, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = Color(0xFF10172A)),
        border = BorderStroke(1.dp, NeonBlue),
        elevation = CardDefaults.cardElevation(10.dp)
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(Icons.Default.Person, null, tint = NeonGreen, modifier = Modifier.size(36.dp))
            Text(guard.name, fontWeight = FontWeight.Bold, color = Color.White)
            Text("⭐ ${guard.rating}", color = Color.LightGray)
            Text("Rs. ${guard.pricePerHour}/hr", color = NeonBlue)
        }
    }
}

/* ---------------- BOOKING DIALOG ---------------- */

@Composable
fun BookingDialog(guard: GuardData, onDismiss: () -> Unit) {
    val context = LocalContext.current

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Confirm Booking") },
        text = {
            Column {
                Text("Name: ${guard.name}")
                Text("Experience: ${guard.experience} years")
                Text("Shift: ${guard.shift}")
                Text("Location: ${guard.location}")
                Text("Features: ${guard.features}")
                Spacer(Modifier.height(8.dp))
                Text("Rs. ${guard.pricePerHour} / hour", fontWeight = FontWeight.Bold)
            }
        },
        confirmButton = {
            TextButton(onClick = {
                Toast.makeText(context, "Guard Booked Successfully", Toast.LENGTH_SHORT).show()
                onDismiss()
            }) { Text("Confirm") }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancel") }
        }
    )
}

/* ---------------- DATA CLASS ---------------- */

data class GuardData(
    val name: String,
    val experience: Int,
    val pricePerHour: Int,
    val shift: String,
    val location: String,
    val rating: Float,
    val features: String
)

/* ---------------- PREVIEW ---------------- */

@Preview(showBackground = true)
@Composable
fun PreviewDashboard() {
    FindGuardTheme { MainScreen() }
}
