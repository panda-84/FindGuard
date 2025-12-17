package com.example.findguard

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.findguard.ui.theme.FindGuardTheme
import com.example.findguard.ui.theme.Purple80

class ForgetPassword : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ForgetPasswordPreview()

        }
    }
}

@Composable
fun ForgetPasswordBody() {
    var email by remember { mutableStateOf("")}
//    val userViewModel = remember { UserViewModel(UserRepoImpl()) }
    val context = LocalContext.current

    Scaffold { padding ->
        Column (modifier = Modifier
            .fillMaxSize()
            .padding(padding),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Spacer(modifier = Modifier.height(110.dp)

            )
            Image(
                painter = painterResource(R.drawable.logo),
                contentDescription = null,
                modifier = Modifier.size(150.dp)
            )
            Text(
                "Forget Password",
                modifier = Modifier.fillMaxWidth(),
                style = TextStyle(
                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    color = Black,
                    fontWeight = FontWeight.Bold
                )

            )
            OutlinedTextField(

                value = email,
                onValueChange = { email = it },
                placeholder = { Text("abc@gmail.com") },
                label = { Text("Email") },
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Gray,
                    focusedContainerColor = Purple80,
                    focusedIndicatorColor = Blue,

                    unfocusedIndicatorColor = Color.Transparent
                ),
                shape = RoundedCornerShape(30.dp),
                modifier = Modifier
                    .width(300.dp)
                    .padding(vertical = 5.dp)

            )
            Button (onClick = {
                Toast.makeText(context, "Reset Link Sent", Toast.LENGTH_SHORT).show()
            })  {
                Text(text = "Send Reset Link")
//                onClick = {
//                    userViewModel.forgetPassword(email) { success, message ->
//                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
//                    }
                }
//            ) {
//                Text("Send Reset Link")
//            }
        }
    }

}


@Preview
@Composable
fun ForgetPasswordPreview() {
    ForgetPasswordBody()
}


