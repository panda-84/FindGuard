package com.example.findguard

import androidx.compose.runtime.mutableStateListOf

/* ============================================================
   DATA CLASS
   ============================================================ */

data class GuardData(
    val name: String,
    val experience: Int,
    val pricePerHour: Int,
    val shift: String,
    val location: String,
    val rating: Float,
    val features: String
) {
    val pricePerDay: Int get() = pricePerHour * 8
}

/* ============================================================
   GLOBAL BOOKING STATE
   ============================================================ */

object BookingState {
    val bookedGuards = mutableStateListOf<GuardData>()
}

/* ============================================================
   ALL GUARDS DATA — single source of truth
   ============================================================ */

val allGuardsByCompany: Map<String, List<GuardData>> = mapOf(
    "ABC Security Pvt. Ltd." to listOf(
        GuardData("Ram Bahadur",   6, 1800, "Day",   "Kathmandu", 4.8f, "Armed"),
        GuardData("Sita Shrestha", 4, 1600, "Night", "Lalitpur",  4.6f, "CCTV")
    ),
    "Himal Secure Service" to listOf(
        GuardData("Hari Lama",   8, 2200, "Day",   "Bhaktapur", 4.9f, "VIP"),
        GuardData("Gita Khatri", 3, 1400, "Night", "Kathmandu", 4.3f, "Night Patrol")
    ),
    "Everest Protection Group" to listOf(
        GuardData("Nabin Rai",     7, 2100, "Day",   "Pokhara", 4.7f, "Armed"),
        GuardData("Sunita Gurung", 5, 1700, "Night", "Pokhara", 4.5f, "CCTV")
    ),
    "Nepal Guard Force" to listOf(
        GuardData("Bikash Thapa", 9, 2500, "Day", "Kathmandu", 5.0f, "VIP Escort")
    )
)

val allGuardsFlat: List<GuardData> = allGuardsByCompany.values.flatten()