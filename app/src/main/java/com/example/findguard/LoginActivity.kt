package com.example.findguard

import android.app.Activity
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Mail
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.findguard.repository.UserRepoImpl
import com.example.findguard.viewmodel.UserViewModel
import com.example.findguard.viewmodel.UserViewModelFactory

/* ---------- NEON THEME COLORS ---------- */

private val DarkBg = Color(0xFF0B0F1A)
private val NeonBlue = Color(0xFF00E5FF)
private val NeonGreen = Color(0xFF1DE9B6)

/* ---------- ACTIVITY ---------- */

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val repo = UserRepoImpl()
            val userViewModel: UserViewModel =
                viewModel(factory = UserViewModelFactory(repo))
            LoginBody(userViewModel)
        }
    }
}

/* ---------- UI ---------- */

@Composable
fun LoginBody(userViewModel: UserViewModel) {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val context = LocalContext.current
    val activity = context as? Activity

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

            Text(
                text = "FindGuard",
                fontSize = 30.sp,
                fontWeight = FontWeight.ExtraBold,
                color = NeonBlue
            )

            Text(
                text = "Secure Login",
                color = Color.LightGray,
                fontSize = 14.sp
            )

            Spacer(Modifier.height(30.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Email", color = Color.LightGray) },
                leadingIcon = {
                    Icon(Icons.Default.Mail, null, tint = NeonBlue)
                },
                shape = RoundedCornerShape(14.dp),
                textStyle = TextStyle(color = Color.White),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = NeonBlue,
                    unfocusedBorderColor = Color.Gray,
                    focusedLabelColor = NeonBlue,
                    cursorColor = NeonBlue
                )
            )

            Spacer(Modifier.height(14.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Password", color = Color.LightGray) },
                leadingIcon = {
                    Icon(Icons.Default.Lock, null, tint = NeonGreen)
                },
                shape = RoundedCornerShape(14.dp),
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                textStyle = TextStyle(color = Color.White),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = NeonGreen,
                    unfocusedBorderColor = Color.Gray,
                    focusedLabelColor = NeonGreen,
                    cursorColor = NeonGreen
                )
            )

            Spacer(Modifier.height(24.dp))

            Button(
                onClick = {
                    if (email.isEmpty() || password.isEmpty()) {
                        Toast.makeText(context, "Fill all fields", Toast.LENGTH_SHORT).show()
                    } else {
                        userViewModel.login(email, password) { success, msg ->
                            if (success) {
                                Toast.makeText(context, "Login Successful", Toast.LENGTH_SHORT).show()
                                context.startActivity(
                                    Intent(context, DashboardActivity::class.java)
                                )
                                activity?.finish()
                            } else {
                                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = NeonBlue)
            ) {
                Text("LOGIN", fontWeight = FontWeight.Bold, color = Color.Black)
            }

            Spacer(Modifier.height(18.dp))

            Text(
                text = "Forgot Password?",
                color = NeonGreen,
                modifier = Modifier.clickable {
                    context.startActivity(
                        Intent(context, ForgetPassword::class.java)
                    )
                }
            )

            Spacer(Modifier.height(12.dp))

            Text(
                text = "Don't have an account? Sign Up",
                color = Color.LightGray,
                modifier = Modifier.clickable {
                    context.startActivity(
                        Intent(context, SignupActivity::class.java)
                    )
                }
            )
        }
    }
}

/* ---------- PREVIEW ---------- */

@Preview(showBackground = true)
@Composable
fun LoginPreview() {
    val repo = UserRepoImpl()
    val vm = UserViewModel(repo)
    LoginBody(vm)
}