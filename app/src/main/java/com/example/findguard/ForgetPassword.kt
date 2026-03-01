package com.example.findguard

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.findguard.repository.UserRepoImpl
import com.example.findguard.ui.theme.DarkBg
import com.example.findguard.ui.theme.NeonBlue
import com.example.findguard.ui.theme.NeonGray
import com.example.findguard.ui.theme.NeonGreen
import com.example.findguard.viewmodel.UserViewModel
import com.example.findguard.viewmodel.UserViewModelFactory

class ForgetPassword : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent { ForgetPasswordBody() }
    }
}

@Composable
fun ForgetPasswordBody() {
    var email by remember { mutableStateOf("") }
    val context = LocalContext.current
    val userRepo = UserRepoImpl()
    val userViewModel: UserViewModel = viewModel(factory = UserViewModelFactory(userRepo))

    Scaffold(containerColor = DarkBg) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(DarkBg)
                .padding(padding)
                .padding(horizontal = 28.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(R.drawable.logo),
                contentDescription = null,
                modifier = Modifier.size(140.dp)
            )
            Spacer(Modifier.height(16.dp))
            Text("FindGuard", fontSize = 30.sp, fontWeight = FontWeight.ExtraBold, color = NeonBlue)
            Text("Reset your password", color = NeonGray, fontSize = 14.sp)
            Spacer(Modifier.height(30.dp))

            // Email Input
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                modifier = Modifier.fillMaxWidth().testTag("email"), // ← testTag
                label = { Text("Email", color = NeonGray) },
                placeholder = { Text("abc@gmail.com", color = NeonGray) },
                shape = RoundedCornerShape(14.dp),
                textStyle = TextStyle(color = Color.White),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = NeonBlue,
                    unfocusedBorderColor = NeonGray,
                    focusedLabelColor = NeonBlue,
                    cursorColor = NeonBlue
                )
            )
            Spacer(Modifier.height(24.dp))

            Button(
                onClick = {
                    if (email.isEmpty()) {
                        Toast.makeText(context, "Please enter your email", Toast.LENGTH_SHORT).show()
                    } else {
                        userViewModel.forgetPassword(email) { success, message ->
                            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp)
                    .testTag("sendResetButton"), // ← testTag
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = NeonGreen)
            ) {
                Text("SEND RESET LINK", fontWeight = FontWeight.Bold, color = Color.Black)
            }
            Spacer(Modifier.height(18.dp))

            Text(
                text = "Back to Login",
                color = NeonBlue,
                modifier = Modifier
                    .testTag("backToLogin") // ← testTag
                    .clickable {
                        context.startActivity(Intent(context, LoginActivity::class.java))
                    }
            )
        }
    }
}