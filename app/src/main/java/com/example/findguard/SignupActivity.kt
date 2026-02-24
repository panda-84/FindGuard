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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.findguard.model.UserModel
import com.example.findguard.repository.UserRepoImpl
import com.example.findguard.ui.theme.DarkBg
import com.example.findguard.ui.theme.NeonBlue
import com.example.findguard.ui.theme.NeonGray
import com.example.findguard.ui.theme.NeonGreen
import com.example.findguard.viewmodel.UserViewModel
import com.example.findguard.viewmodel.UserViewModelFactory

/* ---------- ACTIVITY ---------- */
class SignupActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SignUpBody(
                onSuccess = {
                    startActivity(Intent(this, LoginActivity::class.java))
                    finish()
                }
            )
        }
    }
}

/* ---------- UI ---------- */
@Composable
fun SignUpBody(onSuccess: () -> Unit = {}) {
    var fullName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var checkbox by remember { mutableStateOf(false) }
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
                painter = painterResource(id = R.drawable.logo),
                contentDescription = null,
                modifier = Modifier.size(140.dp)
            )

            Spacer(Modifier.height(16.dp))

            Text(
                text = "Sign Up",
                fontSize = 30.sp,
                fontWeight = FontWeight.ExtraBold,
                color = NeonBlue
            )

            Text(
                text = "Create your account",
                color = NeonGray,
                fontSize = 14.sp
            )

            Spacer(Modifier.height(30.dp))

            // Full Name
            OutlinedTextField(
                value = fullName,
                onValueChange = { fullName = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Full Name", color = NeonGray) },
                placeholder = { Text("", color = NeonGray) },
                shape = RoundedCornerShape(14.dp),
                textStyle = TextStyle(color = Color.White),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = NeonBlue,
                    unfocusedBorderColor = NeonGray,
                    focusedLabelColor = NeonBlue,
                    cursorColor = NeonBlue
                )
            )

            Spacer(Modifier.height(14.dp))

            // Email
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Email", color = NeonGray) },
                placeholder = { Text("abc@gmail.com", color = NeonGray) },
                shape = RoundedCornerShape(14.dp),
                textStyle = TextStyle(color = Color.White),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = NeonBlue,
                    unfocusedBorderColor = NeonGray,
                    focusedLabelColor = NeonBlue,
                    cursorColor = NeonBlue
                )
            )

            Spacer(Modifier.height(14.dp))

            // Password with show/hide
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Password", color = NeonGray) },
                placeholder = { Text("", color = NeonGray) },
                shape = RoundedCornerShape(14.dp),
                textStyle = TextStyle(color = Color.White),
                singleLine = true,
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(
                            imageVector = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                            contentDescription = null,
                            tint = NeonGreen
                        )
                    }
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = NeonGreen,
                    unfocusedBorderColor = NeonGray,
                    focusedLabelColor = NeonGreen,
                    cursorColor = NeonGreen
                )
            )

            Spacer(Modifier.height(14.dp))

            // Terms checkbox
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = checkbox,
                    onCheckedChange = { checkbox = it },
                    colors = CheckboxDefaults.colors(
                        checkmarkColor = Color.Black,
                        checkedColor = NeonGreen
                    )
                )
                Spacer(Modifier.width(6.dp))
                Text("I agree to Terms & Conditions", color = NeonGray)
            }

            Spacer(Modifier.height(24.dp))

            // Sign Up button
            Button(
                onClick = {
                    if (!checkbox) {
                        Toast.makeText(context, "Please accept terms & conditions", Toast.LENGTH_SHORT).show()
                    } else if (fullName.isEmpty() || email.isEmpty() || password.isEmpty()) {
                        Toast.makeText(context, "Please fill all fields", Toast.LENGTH_SHORT).show()
                    } else {
                        userViewModel.signup(email, password) { success, message, userId ->
                            if (success) {
                                val userModel = UserModel(
                                    id = userId,
                                    fullName = fullName,
                                    email = email,
                                    password = password
                                )
                                userViewModel.addUserToDatabase(userId, userModel) { dbSuccess, dbMessage ->
                                    if (dbSuccess) {
                                        Toast.makeText(context, "Registration Success", Toast.LENGTH_SHORT).show()
                                        onSuccess()
                                    } else {
                                        Toast.makeText(context, dbMessage, Toast.LENGTH_SHORT).show()
                                    }
                                }
                            } else {
                                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
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
                Text("SIGN UP", fontWeight = FontWeight.Bold, color = Color.Black)
            }

            Spacer(Modifier.height(16.dp))

            Text(
                text = "Do have an account? Login",
                color = NeonGray,
                modifier = Modifier.clickable {
                    context.startActivity(Intent(context, LoginActivity::class.java))
                }
            )
        }
    }
}

/* ---------- PREVIEW ---------- */
@Preview(showBackground = true)
@Composable
fun SignUpPreview() {
    SignUpBody()
}
