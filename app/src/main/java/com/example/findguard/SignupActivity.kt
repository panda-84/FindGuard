package com.example.findguard

import android.os.Bundle
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Color.Companion.Black
import com.example.findguard.ui.theme.Purple80
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.Color.Companion.Blue
import android.widget.Toast
import androidx.compose.material3.Button
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.LocalContext
import androidx.compose.foundation.background
class SignupActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
           SignUpBody()
            }
    }
}
@Composable
fun SignUpBody() {
    var fullName by remember { mutableStateOf("")}
    var email by remember { mutableStateOf("")}
    var password by remember { mutableStateOf("")}
    val context = LocalContext.current
    var checkbox by remember { mutableStateOf(false) }

    Scaffold { padding ->
        Column (modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray)
            .padding(padding),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Spacer(modifier = Modifier.height(100.dp)

            )
            Image(
                painter = painterResource(R.drawable.logo),
                contentDescription = null,
                modifier = Modifier.size(150.dp)
            )
            Text(
                "Sign Up",
                modifier = Modifier.fillMaxWidth(),
                style = TextStyle(
                    textAlign = TextAlign.Center,
                    fontSize = 25.sp,
                    color = Black,
                    fontWeight = FontWeight.Bold
                )

            )
            OutlinedTextField(

                value = fullName,
                onValueChange = { fullName = it },
                placeholder = { Text("") },
                label = { Text("Name") },
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Gray,
                    focusedContainerColor = White,
                    focusedIndicatorColor = Gray,

                    unfocusedIndicatorColor = Color.Transparent
                ),
                shape = RoundedCornerShape(30.dp),
                modifier = Modifier
                    .width(300.dp)
                    .padding(vertical = 5.dp)


            )
            OutlinedTextField(

                value = email,
                onValueChange = { email = it },
                placeholder = { Text("abc@gmail.com") },
                label = { Text("Email") },
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Gray,
                    focusedContainerColor = White,
                    focusedIndicatorColor = Gray,

                    unfocusedIndicatorColor = Color.Transparent
                ),
                shape = RoundedCornerShape(30.dp),
                modifier = Modifier
                    .width(300.dp)
                    .padding(vertical = 5.dp)


            )

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                placeholder = { Text("") },
                label = { Text("Password") },
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Gray,
                    focusedContainerColor = White,
                    focusedIndicatorColor = Gray,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                shape = RoundedCornerShape(30.dp),
               
                modifier = Modifier
                    .width(300.dp)
                    .padding(vertical = 5.dp)
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(top = 10.dp)
            ) {
                Checkbox(
                    checked = checkbox,
                    onCheckedChange = { checkbox = it },
                    colors = CheckboxDefaults.colors(
                        checkmarkColor = White,
                        checkedColor = Color.Green
                    )
                )
                Spacer(modifier = Modifier.width(2.dp))
                Text("I agree to Terms & Conditions")
            }
            Button(onClick = {
                if (!checkbox) {
                    Toast.makeText(
                        context,
                        "Please accept terms & conditions",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(context, "Sign up successful", Toast.LENGTH_SHORT).show()
//                    userViewModel.register(email, password) { success, message, userId ->
//                        if (success) {
//                            val model = UserModel(
//                                id = userId,
//                                firstName = "",
//                                lastName = "",
//                                email = email,
//                                gender = "",
//                                dob = selectedDate
//                            )
//                            userViewModel.addUserToDatabase(userId, model) { success, message ->
//                                if (success) {
//                                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
//                                    activity?.finish()
//                                } else {
//                                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
//
//                                }
//                            }
//                        } else {
//                            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
//                        }
//                    }
                }
            }) {
                Text(text = "Sign Up")
            }
        }
    }

}


@Preview
@Composable
fun SignUpPreview() {
    SignUpBody()
}

