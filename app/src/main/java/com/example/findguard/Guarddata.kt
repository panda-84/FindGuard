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
   ALL GUARDS DATA — 10 companies × 10 guards = 100 guards
   ============================================================ */

val allGuardsByCompany: Map<String, List<GuardData>> = mapOf(

    /* ── 1. ABC Security Pvt. Ltd. ─────────────────────────── */
    "ABC Security Pvt. Ltd." to listOf(
        GuardData("Ram Bahadur",    6,  1800, "Day",   "Kathmandu", 4.8f, "Armed"),
        GuardData("Sita Shrestha",  4,  1600, "Night", "Lalitpur",  4.6f, "CCTV"),
        GuardData("Bikash Tamang",  5,  1700, "Day",   "Kathmandu", 4.5f, "Patrol"),
        GuardData("Anita Rai",      3,  1400, "Night", "Bhaktapur", 4.3f, "Armed"),
        GuardData("Suresh Thapa",   7,  1900, "Day",   "Kathmandu", 4.7f, "VIP"),
        GuardData("Priya Gurung",   2,  1300, "Night", "Lalitpur",  4.1f, "CCTV"),
        GuardData("Dipesh Lama",    8,  2000, "Day",   "Pokhara",   4.8f, "Armed"),
        GuardData("Kamala Magar",   4,  1550, "Night", "Kathmandu", 4.4f, "Patrol"),
        GuardData("Naresh Bohara",  6,  1750, "Day",   "Bhaktapur", 4.6f, "VIP"),
        GuardData("Sunita Karki",   3,  1450, "Night", "Kathmandu", 4.2f, "CCTV")
    ),

    /* ── 2. Himal Secure Service ────────────────────────────── */
    "Himal Secure Service" to listOf(
        GuardData("Hari Lama",      8,  2200, "Day",   "Bhaktapur", 4.9f, "VIP"),
        GuardData("Gita Khatri",    3,  1400, "Night", "Kathmandu", 4.3f, "Night Patrol"),
        GuardData("Rajan Shrestha", 5,  1800, "Day",   "Pokhara",   4.6f, "Armed"),
        GuardData("Mina Tamang",    4,  1600, "Night", "Lalitpur",  4.4f, "CCTV"),
        GuardData("Prakash Rana",   9,  2400, "Day",   "Kathmandu", 5.0f, "VIP Escort"),
        GuardData("Sabita Thapa",   2,  1350, "Night", "Bhaktapur", 4.0f, "Patrol"),
        GuardData("Binod Gurung",   7,  2100, "Day",   "Pokhara",   4.7f, "Armed"),
        GuardData("Puja Rai",       3,  1500, "Night", "Kathmandu", 4.2f, "CCTV"),
        GuardData("Kamal Basnet",   6,  1900, "Day",   "Lalitpur",  4.5f, "Night Patrol"),
        GuardData("Rekha Magar",    5,  1700, "Night", "Bhaktapur", 4.3f, "Armed")
    ),

    /* ── 3. Everest Protection Group ────────────────────────── */
    "Everest Protection Group" to listOf(
        GuardData("Nabin Rai",      7,  2100, "Day",   "Pokhara",   4.7f, "Armed"),
        GuardData("Sunita Gurung",  5,  1700, "Night", "Pokhara",   4.5f, "CCTV"),
        GuardData("Anil Karki",     6,  1850, "Day",   "Kathmandu", 4.6f, "VIP"),
        GuardData("Kopila Thapa",   4,  1600, "Night", "Lalitpur",  4.3f, "Patrol"),
        GuardData("Roshan Lama",    8,  2200, "Day",   "Bhaktapur", 4.8f, "Armed"),
        GuardData("Nirmala Rana",   3,  1400, "Night", "Pokhara",   4.1f, "CCTV"),
        GuardData("Suman Basnet",   7,  2000, "Day",   "Kathmandu", 4.7f, "VIP Escort"),
        GuardData("Rina Shrestha",  2,  1300, "Night", "Lalitpur",  4.0f, "Patrol"),
        GuardData("Ajay Tamang",    9,  2500, "Day",   "Pokhara",   5.0f, "Armed"),
        GuardData("Sarita Magar",   4,  1550, "Night", "Bhaktapur", 4.2f, "Night Patrol")
    ),

    /* ── 4. Nepal Guard Force ───────────────────────────────── */
    "Nepal Guard Force" to listOf(
        GuardData("Bikash Thapa",   9,  2500, "Day",   "Kathmandu", 5.0f, "VIP Escort"),
        GuardData("Prabha Gurung",  5,  1750, "Night", "Pokhara",   4.4f, "Armed"),
        GuardData("Sanjay Rai",     6,  1900, "Day",   "Bhaktapur", 4.6f, "CCTV"),
        GuardData("Annapurna KC",   4,  1600, "Night", "Kathmandu", 4.3f, "Patrol"),
        GuardData("Deepak Lama",    7,  2100, "Day",   "Lalitpur",  4.7f, "Armed"),
        GuardData("Mandira Karki",  3,  1450, "Night", "Pokhara",   4.1f, "VIP"),
        GuardData("Santosh Bohara", 8,  2300, "Day",   "Kathmandu", 4.9f, "VIP Escort"),
        GuardData("Laxmi Tamang",   2,  1350, "Night", "Bhaktapur", 4.0f, "CCTV"),
        GuardData("Raju Shrestha",  6,  1850, "Day",   "Pokhara",   4.5f, "Armed"),
        GuardData("Kabita Magar",   4,  1600, "Night", "Kathmandu", 4.2f, "Patrol")
    ),

    /* ── 5. Kathmandu Shield Security ──────────────────────── */
    "Kathmandu Shield Security" to listOf(
        GuardData("Arjun Basnet",   7,  2000, "Day",   "Kathmandu", 4.8f, "Armed"),
        GuardData("Sushila Thapa",  5,  1700, "Night", "Lalitpur",  4.5f, "CCTV"),
        GuardData("Manoj Gurung",   6,  1850, "Day",   "Bhaktapur", 4.6f, "VIP"),
        GuardData("Champa Rai",     3,  1400, "Night", "Kathmandu", 4.2f, "Night Patrol"),
        GuardData("Tilak Lama",     8,  2200, "Day",   "Pokhara",   4.9f, "Armed"),
        GuardData("Durga Karki",    4,  1550, "Night", "Lalitpur",  4.3f, "Patrol"),
        GuardData("Rabindra Rana",  9,  2500, "Day",   "Kathmandu", 5.0f, "VIP Escort"),
        GuardData("Parbati Magar",  2,  1300, "Night", "Bhaktapur", 4.0f, "CCTV"),
        GuardData("Gopal Tamang",   6,  1800, "Day",   "Pokhara",   4.6f, "Armed"),
        GuardData("Saraswati KC",   4,  1600, "Night", "Kathmandu", 4.4f, "VIP")
    ),

    /* ── 6. Pokhara Guard Services ──────────────────────────── */
    "Pokhara Guard Services" to listOf(
        GuardData("Amrit Shrestha", 6,  1900, "Day",   "Pokhara",   4.7f, "Armed"),
        GuardData("Bimala Gurung",  4,  1600, "Night", "Pokhara",   4.4f, "CCTV"),
        GuardData("Saroj Thapa",    7,  2100, "Day",   "Pokhara",   4.8f, "VIP Escort"),
        GuardData("Chandrika Rai",  3,  1400, "Night", "Pokhara",   4.1f, "Patrol"),
        GuardData("Dinesh Lama",    8,  2300, "Day",   "Pokhara",   4.9f, "Armed"),
        GuardData("Elina Magar",    2,  1350, "Night", "Pokhara",   4.0f, "Night Patrol"),
        GuardData("Furba Tamang",   6,  1800, "Day",   "Pokhara",   4.6f, "CCTV"),
        GuardData("Ganga Basnet",   5,  1750, "Night", "Pokhara",   4.3f, "Armed"),
        GuardData("Hari Karki",     9,  2500, "Day",   "Pokhara",   5.0f, "VIP"),
        GuardData("Indira Bohara",  4,  1550, "Night", "Pokhara",   4.2f, "Patrol")
    ),

    /* ── 7. Lalitpur Security Solutions ─────────────────────── */
    "Lalitpur Security Solutions" to listOf(
        GuardData("Jagat Shrestha", 5,  1750, "Day",   "Lalitpur",  4.5f, "Armed"),
        GuardData("Kamana Thapa",   3,  1450, "Night", "Lalitpur",  4.2f, "CCTV"),
        GuardData("Lokendra Rai",   7,  2000, "Day",   "Lalitpur",  4.7f, "VIP"),
        GuardData("Meera Gurung",   4,  1600, "Night", "Lalitpur",  4.3f, "Patrol"),
        GuardData("Narayan Lama",   8,  2200, "Day",   "Lalitpur",  4.8f, "Armed"),
        GuardData("Omkar Magar",    2,  1300, "Night", "Lalitpur",  4.0f, "Night Patrol"),
        GuardData("Pushpa Karki",   6,  1850, "Day",   "Lalitpur",  4.6f, "VIP Escort"),
        GuardData("Ritu Tamang",    3,  1400, "Night", "Lalitpur",  4.1f, "CCTV"),
        GuardData("Shiva Basnet",   9,  2400, "Day",   "Lalitpur",  4.9f, "Armed"),
        GuardData("Tara Rana",      5,  1700, "Night", "Lalitpur",  4.4f, "Patrol")
    ),

    /* ── 8. Bhaktapur Vigilance Corp ───────────────────────── */
    "Bhaktapur Vigilance Corp" to listOf(
        GuardData("Umesh Shrestha", 6,  1800, "Day",   "Bhaktapur", 4.6f, "Armed"),
        GuardData("Vandana Thapa",  4,  1600, "Night", "Bhaktapur", 4.3f, "CCTV"),
        GuardData("Wangchuk Lama",  8,  2200, "Day",   "Bhaktapur", 4.9f, "VIP"),
        GuardData("Xina Gurung",    3,  1400, "Night", "Bhaktapur", 4.1f, "Patrol"),
        GuardData("Yam Bahadur",    7,  2100, "Day",   "Bhaktapur", 4.8f, "Armed"),
        GuardData("Zara Magar",     2,  1350, "Night", "Bhaktapur", 4.0f, "Night Patrol"),
        GuardData("Aakash Rai",     5,  1750, "Day",   "Bhaktapur", 4.5f, "CCTV"),
        GuardData("Babita Karki",   4,  1600, "Night", "Bhaktapur", 4.2f, "VIP Escort"),
        GuardData("Chandan Basnet", 9,  2500, "Day",   "Bhaktapur", 5.0f, "Armed"),
        GuardData("Devi Tamang",    6,  1850, "Night", "Bhaktapur", 4.4f, "Patrol")
    ),

    /* ── 9. Himalayan Elite Guards ──────────────────────────── */
    "Himalayan Elite Guards" to listOf(
        GuardData("Eshan Shrestha", 7,  2100, "Day",   "Kathmandu", 4.7f, "VIP Escort"),
        GuardData("Fulmaya Thapa",  3,  1450, "Night", "Pokhara",   4.2f, "Armed"),
        GuardData("Ganesh Gurung",  8,  2300, "Day",   "Bhaktapur", 4.9f, "VIP"),
        GuardData("Hira Lama",      4,  1600, "Night", "Lalitpur",  4.3f, "CCTV"),
        GuardData("Ishwor Rai",     6,  1900, "Day",   "Kathmandu", 4.6f, "Armed"),
        GuardData("Juna Magar",     2,  1300, "Night", "Pokhara",   4.0f, "Patrol"),
        GuardData("Kiran Karki",    9,  2500, "Day",   "Bhaktapur", 5.0f, "VIP Escort"),
        GuardData("Lalita Basnet",  5,  1750, "Night", "Lalitpur",  4.4f, "Night Patrol"),
        GuardData("Mohan Tamang",   7,  2000, "Day",   "Kathmandu", 4.7f, "Armed"),
        GuardData("Nita Bohara",    4,  1600, "Night", "Pokhara",   4.2f, "CCTV")
    ),

    /* ── 10. Terai Protection Agency ────────────────────────── */
    "Terai Protection Agency" to listOf(
        GuardData("Om Shrestha",    6,  1800, "Day",   "Birgunj",   4.5f, "Armed"),
        GuardData("Poonam Thapa",   4,  1600, "Night", "Birgunj",   4.3f, "CCTV"),
        GuardData("Qadir Ansari",   5,  1700, "Day",   "Birgunj",   4.4f, "Patrol"),
        GuardData("Rita Yadav",     3,  1400, "Night", "Birgunj",   4.1f, "Night Patrol"),
        GuardData("Suraj Mahato",   8,  2200, "Day",   "Birgunj",   4.8f, "Armed"),
        GuardData("Tina Sah",       2,  1350, "Night", "Birgunj",   4.0f, "VIP"),
        GuardData("Umang Jha",      7,  2100, "Day",   "Birgunj",   4.7f, "VIP Escort"),
        GuardData("Vijaya Kushwaha",4,  1600, "Night", "Birgunj",   4.2f, "CCTV"),
        GuardData("Wasim Khan",     9,  2500, "Day",   "Birgunj",   5.0f, "Armed"),
        GuardData("Yamuna Rai",     5,  1750, "Night", "Birgunj",   4.3f, "Patrol")
    )
)

val allGuardsFlat: List<GuardData> = allGuardsByCompany.values.flatten()