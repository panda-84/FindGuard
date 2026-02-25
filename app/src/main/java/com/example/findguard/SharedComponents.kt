package com.example.findguard

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.findguard.ui.theme.NeonBlue
import com.example.findguard.ui.theme.NeonGreen

/* ============================================================
   GUARD CARD
   ============================================================ */

@Composable
fun GuardCard(guard: GuardData, onClick: () -> Unit) {
    Card(
        modifier  = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        colors    = CardDefaults.cardColors(containerColor = Color(0xFF10172A)),
        border    = BorderStroke(1.dp, NeonBlue),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(
            modifier            = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier         = Modifier
                    .size(56.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF0D1321)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Default.Person,
                    contentDescription = null,
                    tint     = NeonGreen,
                    modifier = Modifier.size(36.dp)
                )
            }
            Spacer(Modifier.height(8.dp))
            Text(
                guard.name,
                fontWeight = FontWeight.Bold,
                color      = Color.White,
                textAlign  = TextAlign.Center,
                fontSize   = 13.sp
            )
            Spacer(Modifier.height(2.dp))
            Text("⭐ ${guard.rating}", color = Color.Yellow, fontSize = 12.sp)
            Spacer(Modifier.height(4.dp))
            Text("Rs.${guard.pricePerHour}/hr", color = NeonBlue,  fontWeight = FontWeight.SemiBold, fontSize = 12.sp)
            Text("Rs.${guard.pricePerDay}/day",  color = NeonGreen, fontWeight = FontWeight.Bold,     fontSize = 11.sp)
            Spacer(Modifier.height(6.dp))
            Text("Tap to view profile →", color = Color.Gray, fontSize = 10.sp)
        }
    }
}

/* ============================================================
   GUARD PROFILE DIALOG
   ============================================================ */

@Composable
fun GuardProfileDialog(
    guard    : GuardData,
    onDismiss: () -> Unit,
    onBook   : () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor   = Color(0xFF1A1C1E),
        title = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier            = Modifier.fillMaxWidth()
            ) {
                Box(
                    modifier         = Modifier
                        .size(72.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF10172A)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Default.Person,
                        contentDescription = null,
                        tint     = NeonGreen,
                        modifier = Modifier.size(52.dp)
                    )
                }
                Spacer(Modifier.height(8.dp))
                Text(guard.name, color = Color.White, fontWeight = FontWeight.ExtraBold, fontSize = 20.sp)
                Text("⭐ ${guard.rating}  •  ${guard.features}", color = Color.Yellow, fontSize = 13.sp)
            }
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
            ) {
                HorizontalDivider(color = Color.DarkGray)
                Spacer(Modifier.height(12.dp))
                SharedDetailRow("🛡️  Service",   guard.features)
                SharedDetailRow("🕐  Shift",      guard.shift)
                SharedDetailRow("📍  Location",   guard.location)
                SharedDetailRow("📅  Experience", "${guard.experience} years")
                Spacer(Modifier.height(14.dp))
                HorizontalDivider(color = Color.DarkGray)
                Spacer(Modifier.height(12.dp))
                Text("PRICING", color = NeonGreen, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                Spacer(Modifier.height(8.dp))
                Row(
                    modifier              = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    SharedPriceBox(
                        label    = "Per Hour",
                        amount   = "Rs. ${guard.pricePerHour}",
                        modifier = Modifier.weight(1f)
                    )
                    SharedPriceBox(
                        label    = "Per Day (8 hrs)",
                        amount   = "Rs. ${guard.pricePerDay}",
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        },
        confirmButton = {
            Button(
                onClick  = onBook,
                modifier = Modifier.fillMaxWidth(),
                colors   = ButtonDefaults.buttonColors(containerColor = NeonGreen)
            ) {
                Icon(
                    Icons.Default.CheckCircle,
                    contentDescription = null,
                    tint     = Color.Black,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(Modifier.width(6.dp))
                Text("Book This Guard", color = Color.Black, fontWeight = FontWeight.Bold)
            }
        },
        dismissButton = {
            TextButton(
                onClick  = onDismiss,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Cancel", color = Color.Gray)
            }
        }
    )
}

/* ============================================================
   RECEIPT DIALOG
   ============================================================ */

@Composable
fun ReceiptDialog(guard: GuardData, onDismiss: () -> Unit) {
    val orderId = remember { (10000..99999).random() }

    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor   = Color.White,
        title = {
            Text(
                "OFFICIAL RECEIPT",
                modifier   = Modifier.fillMaxWidth(),
                textAlign  = TextAlign.Center,
                color      = Color.Black,
                fontWeight = FontWeight.Black,
                fontSize   = 18.sp
            )
        },
        text = {
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    "─────────────────────────",
                    color     = Color.Gray,
                    modifier  = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
                Text("Order ID: FG-$orderId", color = Color.DarkGray, fontSize = 12.sp)
                Spacer(Modifier.height(10.dp))
                SharedReceiptLine("Guard Name",  guard.name)
                SharedReceiptLine("Service",     guard.features)
                SharedReceiptLine("Shift",       guard.shift)
                SharedReceiptLine("Location",    guard.location)
                SharedReceiptLine("Experience",  "${guard.experience} yrs")
                SharedReceiptLine("Rate/Hour",   "Rs. ${guard.pricePerHour}")
                Spacer(Modifier.height(4.dp))
                Text(
                    "─────────────────────────",
                    color     = Color.Gray,
                    modifier  = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
                Row(
                    modifier              = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("TOTAL (1 Day / 8 hrs)", fontWeight = FontWeight.Black, color = Color.Black)
                    Text("Rs. ${guard.pricePerDay}", fontWeight = FontWeight.Black, color = Color.Black)
                }
                Spacer(Modifier.height(14.dp))
                Text(
                    "STATUS: PAID ✅",
                    color      = Color(0xFF2E7D32),
                    fontWeight = FontWeight.ExtraBold,
                    textAlign  = TextAlign.Center,
                    modifier   = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick  = onDismiss,
                modifier = Modifier.fillMaxWidth(),
                colors   = ButtonDefaults.buttonColors(containerColor = Color(0xFF1976D2))
            ) {
                Text("Close Receipt", color = Color.White, fontWeight = FontWeight.Bold)
            }
        }
    )
}

/* ============================================================
   SMALL SHARED HELPERS
   ============================================================ */

@Composable
fun SharedDetailRow(label: String, value: String) {
    Row(
        modifier              = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, color = Color.Gray,  fontSize = 13.sp)
        Text(value, color = Color.White, fontSize = 13.sp, fontWeight = FontWeight.Medium)
    }
}

@Composable
fun SharedReceiptLine(label: String, value: String) {
    Row(
        modifier              = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, color = Color.DarkGray, fontSize = 13.sp)
        Text(value, color = Color.Black,    fontSize = 13.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun SharedPriceBox(label: String, amount: String, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        colors   = CardDefaults.cardColors(containerColor = Color(0xFF0D1321)),
        border   = BorderStroke(1.dp, NeonBlue),
        shape    = RoundedCornerShape(10.dp)
    ) {
        Column(
            modifier            = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(label,  color = Color.Gray,  fontSize = 11.sp)
            Spacer(Modifier.height(4.dp))
            Text(amount, color = NeonBlue,    fontSize = 14.sp, fontWeight = FontWeight.Bold)
        }
    }
}