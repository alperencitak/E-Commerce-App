package com.alperencitak.e_commerce_app.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.alperencitak.e_commerce_app.R
import com.alperencitak.e_commerce_app.model.LoginRequest
import com.alperencitak.e_commerce_app.model.RegisterRequest
import com.alperencitak.e_commerce_app.ui.theme.Blue
import com.alperencitak.e_commerce_app.ui.theme.DarkGreen
import com.alperencitak.e_commerce_app.ui.theme.Gray
import com.alperencitak.e_commerce_app.ui.theme.LightBlue
import com.alperencitak.e_commerce_app.ui.theme.LightCream
import com.alperencitak.e_commerce_app.ui.theme.LightGray
import com.alperencitak.e_commerce_app.ui.theme.WhiteModern
import com.alperencitak.e_commerce_app.utils.isValidEmail
import com.alperencitak.e_commerce_app.viewmodel.AuthViewModel


@Composable
fun LoginPage(navHostController: NavHostController) {

    val authViewModel: AuthViewModel = hiltViewModel()
    val loginResponse = authViewModel.loginResponse.collectAsState()
    val registerResponse = authViewModel.registerResponse.collectAsState()
    val currentUserId = authViewModel.currentUserId.collectAsState()
    val loading = authViewModel.loading.collectAsState()
    val font = FontFamily(
        Font(
            R.font.inter_regular
        )
    )
    authViewModel.getCurrentUserId()
    LaunchedEffect(currentUserId.value) {
        currentUserId.value?.let {
            if(it.toInt() != 0){
                navHostController.navigate("main")
            }
        }
    }

    var page by remember { mutableIntStateOf(1) }
    var step by remember { mutableIntStateOf(1) }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var surname by remember { mutableStateOf("") }

    Image(
        modifier = Modifier.fillMaxSize(),
        painter = painterResource(R.drawable.login_page_wp),
        contentDescription = "Background",
        contentScale = ContentScale.FillBounds
    )

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        ElevatedCard(
            colors = CardDefaults.cardColors(
                containerColor = WhiteModern,
            ),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.elevatedCardElevation(defaultElevation = 10.dp)
        ) {
            Column(
                modifier = Modifier.padding(horizontal = 48.dp, vertical = 96.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                if(page == 1){
                    Spacer(modifier = Modifier.height(64.dp))
                    StepSignIn{ localEmail, localPassword ->
                        val loginRequest = LoginRequest(
                            email = localEmail,
                            password = localPassword
                        )
                        authViewModel.login(loginRequest)
                        authViewModel.getCurrentUserId()
                    }
                }else{
                    StepIndicator(step)
                    Spacer(modifier = Modifier.height(64.dp))
                    when(step){
                        1 -> StepOne(email, phone) { newEmail, newPhone ->
                            email = newEmail
                            phone = newPhone
                            step = 2
                        }
                        2 -> StepTwo(name, surname){ newName, newSurname, isNext ->
                            if(isNext){
                                name = newName
                                surname = newSurname
                                step = 3
                            }else{
                                step--
                            }
                        }
                        3 -> StepThree(password){ localPassword, isNext ->
                            if(isNext){
                                password = localPassword
                                val registerRequest = RegisterRequest(
                                    email = email,
                                    phone = phone,
                                    first_name = name,
                                    last_name = surname,
                                    password = password
                                )
                                authViewModel.register(registerRequest)

                                if(registerResponse.value != null){
                                    navHostController.navigate("main")
                                }
                            }else{
                                step--
                            }
                        }
                    }
                }
                HorizontalDivider(
                    color = Gray,
                    thickness = 1.dp,
                    modifier = Modifier
                        .width(200.dp)
                        .padding(horizontal = 16.dp, vertical = 24.dp),
                )
                Text(
                    text = if(page == 2) "Already have an account? Sign in." else "Don't have an account? Sign up!",
                    fontSize = 16.sp,
                    color = Blue,
                    fontFamily = font,
                    modifier = Modifier.clickable { page = 3 - page }
                )
            }
        }
    }
}

@Composable
fun StepSignIn(onLogin: (String, String) -> Unit) {
    var localEmail by remember { mutableStateOf("") }
    var localPassword by remember { mutableStateOf("") }
    var passwordError by remember { mutableStateOf(false) }
    var emailError by remember { mutableStateOf(false) }
    val font = FontFamily(
        Font(
            R.font.inter_regular
        )
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = localEmail,
            onValueChange = {
                if(it.length < 56){
                    localEmail = it
                    emailError = false
                }
            },
            label = { Text("Email", fontFamily = font) },
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedContainerColor = Color.Transparent,
                focusedContainerColor = Color.Transparent,
                unfocusedBorderColor = if (emailError) Color.Red else Blue,
                focusedBorderColor = if (emailError) Color.Red else Blue
            ),
            singleLine = true,
            modifier = Modifier.width(280.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = localPassword,
            onValueChange = {
                if(it.length < 56){
                    localPassword = it
                    passwordError = false
                }
            },
            label = { Text("Password", fontFamily = font) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedContainerColor = Color.Transparent,
                focusedContainerColor = Color.Transparent,
                unfocusedBorderColor = if (passwordError) Color.Red else Blue,
                focusedBorderColor = if (passwordError) Color.Red else Blue
            ),
            singleLine = true,
            modifier = Modifier.width(280.dp)
        )
        Spacer(modifier = Modifier.height(32.dp))
        Button(
            onClick = {
                if(isValidEmail(localEmail)){
                    if(localPassword.length < 7){
                        passwordError = true
                    }else{
                        onLogin(localEmail, localPassword)
                    }
                }else{
                    emailError = true
                }
            },
            colors = ButtonDefaults.elevatedButtonColors(
                containerColor = LightBlue,
            )
        ) {
            Text(text = "SIGN IN", color = Blue, fontFamily = font)
        }
    }
}


@Composable
fun StepIndicator(step: Int) {
    Row(
        horizontalArrangement = Arrangement.Center
    ) {
        for (i in 1..3) {
            Image(
                painter = painterResource(
                    if (i < step) R.drawable.check
                    else when(i){
                        1 -> R.drawable.circle_1
                        2 -> R.drawable.circle_2
                        3 -> R.drawable.circle_3
                        else -> R.drawable.warning
                    }
                ),
                contentDescription = "Step Icon",
                modifier = Modifier.size(48.dp)
            )
            if (i < 3) {
                HorizontalDivider(
                    modifier = Modifier
                        .width(48.dp)
                        .align(Alignment.CenterVertically)
                        .padding(horizontal = 16.dp),
                    thickness = 2.dp,
                    color = if (i < step) DarkGreen else Color.Gray
                )
            }
        }
    }
}


@Composable
fun StepOne(email: String, phone: String, onNext: (String, String) -> Unit) {
    var localEmail by remember { mutableStateOf(email) }
    var localPhone by remember { mutableStateOf(phone) }
    var emailError by remember { mutableStateOf(false) }
    var phoneError by remember { mutableStateOf(false) }
    val font = FontFamily(
        Font(
            R.font.inter_regular
        )
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = localEmail,
            onValueChange = {
                if(it.length < 56){
                    localEmail = it
                    emailError = false
                }
                            },
            label = { Text("Email", fontFamily = font) },
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedContainerColor = Color.Transparent,
                focusedContainerColor = Color.Transparent,
                unfocusedBorderColor = if (emailError) Color.Red else Blue,
                focusedBorderColor = if (emailError) Color.Red else Blue
            ),
            singleLine = true,
            modifier = Modifier.width(280.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = localPhone,
            onValueChange = {
                if(it.length < 56){
                    localPhone = it
                    phoneError = false
                }
                            },
            label = { Text("Phone") },
            leadingIcon = {
                Text(text = "  +90 |", fontFamily = font)
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedContainerColor = Color.Transparent,
                focusedContainerColor = Color.Transparent,
                unfocusedBorderColor = if (phoneError) Color.Red else Blue,
                focusedBorderColor = if (phoneError) Color.Red else Blue
            ),
            singleLine = true,
            modifier = Modifier.width(280.dp)
        )
        Spacer(modifier = Modifier.height(32.dp))
        Button(
            onClick = {
                if(isValidEmail(localEmail)){
                    if(localPhone.length < 10){
                        phoneError = true
                    }else{
                        onNext(localEmail, localPhone)
                    }
                }else{
                    emailError = true
                }
            },
            colors = ButtonDefaults.elevatedButtonColors(
                containerColor = LightBlue,
            )
        ) {
            Text(text = "NEXT", color = Blue, fontFamily = font)
        }
    }
}

@Composable
fun StepTwo(name: String, surname: String, onNext: (String, String, Boolean) -> Unit) {
    var localName by remember { mutableStateOf(name) }
    var localSurname by remember { mutableStateOf(surname) }
    var nameError by remember { mutableStateOf(false) }
    var surnameError by remember { mutableStateOf(false) }
    val font = FontFamily(
        Font(
            R.font.inter_regular
        )
    )
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = localName,
            onValueChange = {
                if(it.length < 32){
                    localName = it
                    nameError = false
                }
                            },
            label = { Text("Name", fontFamily = font) },
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedContainerColor = Color.Transparent,
                focusedContainerColor = Color.Transparent,
                unfocusedBorderColor = if (nameError) Color.Red else Blue,
                focusedBorderColor = if (nameError) Color.Red else Blue
            ),
            singleLine = true,
            modifier = Modifier.width(280.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = localSurname,
            onValueChange = {
                if(it.length < 32){
                    localSurname = it
                    surnameError = false
                }
                            },
            label = { Text("Surname", fontFamily = font) },
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedContainerColor = Color.Transparent,
                focusedContainerColor = Color.Transparent,
                unfocusedBorderColor = if (surnameError) Color.Red else Blue,
                focusedBorderColor = if (surnameError) Color.Red else Blue
            ),
            singleLine = true,
            modifier = Modifier.width(280.dp)
        )
        Spacer(modifier = Modifier.height(32.dp))
        Row {
            Button(
                onClick = {
                    onNext(localName, localSurname, false)
                },
                colors = ButtonDefaults.elevatedButtonColors(
                    containerColor = LightBlue,
                )
            ) {
                Text(text = "BACK", color = Blue, fontFamily = font)
            }
            Spacer(modifier = Modifier.width(16.dp))
            Button(
                onClick = {
                    if(localName.length < 3){
                        nameError = true
                    }else{
                        if (localSurname.length < 3){
                            surnameError = false
                        }else{
                            onNext(localName, localSurname, true)
                        }
                    }
                },
                colors = ButtonDefaults.elevatedButtonColors(
                    containerColor = LightBlue,
                )
            ) {
                Text(text = "NEXT", color = Blue, fontFamily = font)
            }
        }
    }
}

@Composable
fun StepThree(password: String, onNext: (String, Boolean) -> Unit) {
    var localPassword by remember { mutableStateOf(password) }
    var localRepeatPassword by remember { mutableStateOf("") }
    var passwordError by remember { mutableStateOf(false) }
    val font = FontFamily(
        Font(
            R.font.inter_regular
        )
    )
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = localPassword,
            onValueChange = {
                if(it.length < 32){
                    localPassword = it
                    passwordError = false
                }
                            },
            label = { Text("Password", fontFamily = font) },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedContainerColor = Color.Transparent,
                focusedContainerColor = Color.Transparent,
                unfocusedBorderColor = if (passwordError) Color.Red else Blue,
                focusedBorderColor = if (passwordError) Color.Red else Blue
            ),
            singleLine = true,
            modifier = Modifier.width(280.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = localRepeatPassword,
            onValueChange = {
                if(it.length < 32){
                    localRepeatPassword = it
                    passwordError = false
                }
                            },
            label = { Text("Repeat Password", fontFamily = font) },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedContainerColor = Color.Transparent,
                focusedContainerColor = Color.Transparent,
                unfocusedBorderColor = if (passwordError) Color.Red else Blue,
                focusedBorderColor = if (passwordError) Color.Red else Blue
            ),
            singleLine = true,
            modifier = Modifier.width(280.dp)
        )
        Spacer(modifier = Modifier.height(32.dp))
        Row {
            Button(
                onClick = {
                    onNext(localPassword, false)
                },
                colors = ButtonDefaults.elevatedButtonColors(
                    containerColor = LightBlue,
                )
            ) {
                Text("BACK", color = Blue, fontFamily = font)
            }
            Spacer(modifier = Modifier.width(16.dp))
            Button(
                onClick = {
                    if(localPassword.length > 6){
                        if(localPassword == localRepeatPassword){
                            onNext(localPassword, true)
                        }else{
                            passwordError = true
                        }
                    }else{
                        passwordError = true
                    }
                },
                colors = ButtonDefaults.elevatedButtonColors(
                    containerColor = LightBlue,
                )
            ) {
                Text(text = "FINISH", color = Blue, fontFamily = font)
            }
        }
    }
}