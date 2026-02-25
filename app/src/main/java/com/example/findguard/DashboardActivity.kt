package com.example.findguard

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.compose.material.icons.filled.Phone
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Business
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.findguard.ui.theme.DarkBg
import com.example.findguard.ui.theme.FindGuardTheme
import com.example.findguard.ui.theme.NeonBlue
import com.example.findguard.ui.theme.NeonGreen

/* ============================================================
   ACTIVITY
   ============================================================ */

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

/* ============================================================
   MAIN SCREEN
   ============================================================ */

@Composable
fun MainScreen() {
    var selectedIndex by remember { mutableIntStateOf(0) }
    var showProfile   by remember { mutableStateOf(false) }

    Scaffold(
        containerColor = DarkBg,
        bottomBar = {
            NavigationBar(containerColor = Color.Black) {
                listOf(
                    Icons.Default.Home     to "Home",
                    Icons.Default.Business to "Company",
                    Icons.Default.Security to "Guards"
                ).forEachIndexed { index, (icon, label) ->
                    NavigationBarItem(
                        selected = selectedIndex == index,
                        onClick  = { selectedIndex = index },
                        icon     = {
                            Icon(
                                icon,
                                contentDescription = null,
                                tint = if (selectedIndex == index) NeonGreen else NeonBlue
                            )
                        },
                        label = { Text(label, color = NeonBlue) }
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
                0 -> HomeScreen(onProfileClick = { showProfile = true })
                1 -> CompanyScreen()
                2 -> GuardsScreen()
            }
        }
    }

    if (showProfile) {
        ProfileDialog(onDismiss = { showProfile = false })
    }
}

/* ============================================================
   HOME SCREEN
   ============================================================ */

@Composable
fun HomeScreen(onProfileClick: () -> Unit) {
    val context = LocalContext.current
    val prefs   = context.getSharedPreferences("auth", Context.MODE_PRIVATE)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        // Profile avatar — top left
        Box(
            modifier = Modifier
                .align(Alignment.TopStart)
                .size(44.dp)
                .clip(CircleShape)
                .background(NeonBlue)
                .clickable { onProfileClick() },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                Icons.Default.Person,
                contentDescription = "Profile",
                tint     = Color.Black,
                modifier = Modifier.size(26.dp)
            )
        }

        // Logout — top right
        IconButton(
            modifier = Modifier.align(Alignment.TopEnd),
            onClick  = {
                prefs.edit().clear().apply()
                context.startActivity(Intent(context, LoginActivity::class.java))
                (context as ComponentActivity).finish()
            }
        ) {
            Icon(Icons.Default.Logout, contentDescription = "Logout", tint = NeonGreen)
        }

        // Centre content
        Column(
            modifier            = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "FindGuard 🛡️",
                style      = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.ExtraBold,
                color      = NeonBlue
            )
            Spacer(Modifier.height(12.dp))
            Text(
                "Elite Security Services Across Nepal",
                color     = Color.LightGray,
                textAlign = TextAlign.Center
            )
            Spacer(Modifier.height(24.dp))

            val count = BookingState.bookedGuards.size
            if (count > 0) {
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF10172A)),
                    border = BorderStroke(1.dp, NeonGreen),
                    shape  = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        "✅  $count Guard${if (count > 1) "s" else ""} Booked",
                        color      = NeonGreen,
                        fontWeight = FontWeight.Bold,
                        modifier   = Modifier.padding(horizontal = 20.dp, vertical = 10.dp)
                    )
                }
            }
        }
    }
}

/* ============================================================
   PROFILE DIALOG
   ============================================================ */

@Composable
fun ProfileDialog(onDismiss: () -> Unit) {
    val booked = BookingState.bookedGuards

    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor   = Color(0xFF1A1C1E),
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(52.dp)
                        .clip(CircleShape)
                        .background(NeonBlue),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Default.Person,
                        contentDescription = null,
                        tint     = Color.Black,
                        modifier = Modifier.size(30.dp)
                    )
                }
                Spacer(Modifier.width(12.dp))
                Column {
                    Text("My Profile", color = NeonBlue, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    Text("Customer",   color = Color.Gray, fontSize = 12.sp)
                }
            }
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
            ) {
                HorizontalDivider(color = Color.DarkGray)
                Spacer(Modifier.height(8.dp))

                Text("CUSTOMER DETAILS", color = NeonGreen, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                Spacer(Modifier.height(6.dp))
                ProfileInfoRow(Icons.Default.Person,     "Name",  "John Doe")
                ProfileInfoRow(Icons.Default.Phone,      "Phone", "+977-9800000000")
                ProfileInfoRow(Icons.Default.LocationOn, "City",  "Kathmandu, Nepal")

                Spacer(Modifier.height(14.dp))
                HorizontalDivider(color = Color.DarkGray)
                Spacer(Modifier.height(8.dp))

                Text("BOOKED GUARDS (${booked.size})", color = NeonGreen, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                Spacer(Modifier.height(6.dp))

                if (booked.isEmpty()) {
                    Text("No guards booked yet.", color = Color.Gray, fontSize = 13.sp)
                } else {
                    Column {
                        booked.forEach { guard ->
                            Spacer(Modifier.height(8.dp))
                            Card(
                                colors = CardDefaults.cardColors(containerColor = Color(0xFF0D1321)),
                                border = BorderStroke(1.dp, NeonBlue),
                                shape  = RoundedCornerShape(8.dp)
                            ) {
                                Row(
                                    modifier          = Modifier
                                        .fillMaxWidth()
                                        .padding(10.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        Icons.Default.Shield,
                                        contentDescription = null,
                                        tint     = NeonGreen,
                                        modifier = Modifier.size(20.dp)
                                    )
                                    Spacer(Modifier.width(8.dp))
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(guard.name, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 13.sp)
                                        Text("${guard.shift} • ${guard.location}", color = Color.Gray, fontSize = 11.sp)
                                    }
                                    Column(horizontalAlignment = Alignment.End) {
                                        Text("Rs.${guard.pricePerHour}/hr", color = NeonBlue,  fontSize = 11.sp)
                                        Text("Rs.${guard.pricePerDay}/day", color = NeonGreen, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick  = onDismiss,
                modifier = Modifier.fillMaxWidth(),
                colors   = ButtonDefaults.buttonColors(containerColor = NeonBlue)
            ) { Text("Close", color = Color.Black, fontWeight = FontWeight.Bold) }
        }
    )
}

@Composable
fun ProfileInfoRow(icon: ImageVector, label: String, value: String) {
    Row(
        modifier          = Modifier
            .fillMaxWidth()
            .padding(vertical = 3.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = null, tint = NeonBlue, modifier = Modifier.size(16.dp))
        Spacer(Modifier.width(8.dp))
        Text("$label: ", color = Color.Gray,  fontSize = 13.sp)
        Text(value,      color = Color.White, fontSize = 13.sp, fontWeight = FontWeight.Medium)
    }
}

/* ============================================================
   PREVIEW
   ============================================================ */

@Preview(showBackground = true)
@Composable
fun PreviewDashboard() {
    FindGuardTheme { MainScreen() }
}