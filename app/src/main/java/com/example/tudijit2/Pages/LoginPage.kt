@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.tudijit2.Pages


import android.content.Context
import android.widget.Toast
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text

import androidx.compose.material3.TextButton

import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip

import androidx.compose.ui.graphics.Color

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

import com.example.tudijit2.InOutModels.SignInViewModel

import com.example.tudijit2.Navigations.Screen
import com.example.tudijit2.R
import com.example.tudijit2.StringConsts
import com.example.tudijit2.datastore.UserStore
import com.example.tudijit2.models.Schools
import com.example.tudijit2.models.Wastes
import com.example.tudijit2.rememberImeState
import com.example.tudijit2.ui.theme.MyWhite

import com.example.tudijit2.ui.theme.myButtonColor
import com.example.tudijit2.ui.theme.newBackground
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch


@Composable

fun LoginPage(navController: NavHostController, viewModel: SignInViewModel = hiltViewModel()) {


    val imeState = rememberImeState()
    val scrollState = rememberScrollState()

    LaunchedEffect(key1 = imeState.value) {
        if (imeState.value){
            scrollState.animateScrollTo(scrollState.maxValue, tween(300))
        }
    }
    var systemUiController = rememberSystemUiController()
    val darkTheme = isSystemInDarkTheme()
    SideEffect {
        systemUiController.setSystemBarsColor(
            color = newBackground
        )

    }


    var mail by remember {
        mutableStateOf("")
    }
    var pass by remember {
        mutableStateOf("")
    }
    var isTrue by remember { mutableStateOf(false) }
    var isCheck by remember {
        mutableStateOf(false)
    }

    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val state = viewModel.signInState.collectAsState(initial = null)

    val dataStore=UserStore(context)
    val savedMail = dataStore.getEmail.collectAsState(initial = "")
    val savedPass= dataStore.getPass.collectAsState(initial = "")




    Column(
        modifier = Modifier
            .background(newBackground)
            .fillMaxSize()
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center

    ) {



        Box(modifier = Modifier
            .fillMaxHeight(0.4f)
            .fillMaxWidth(0.4f)) {
            Image(
                painter = painterResource(id = R.drawable.tudijit),
                contentDescription = "Dijital Donusum Toplulugu",
                modifier = Modifier
                    .fillMaxHeight(1f)
                    .fillMaxWidth(1f)
                    .clip(CircleShape)
                    .border(1.dp, Color.Black, CircleShape)

            )
        }
        Spacer(modifier = Modifier.height(35.dp))

        Text(
            "GİRİŞ YAP",
            color = Color.White,
            fontWeight = FontWeight.SemiBold,
            fontSize = 32.sp
        )
        Spacer(modifier = Modifier.height(35.dp))

        val icon = if (!isTrue) {
            painterResource(id = R.drawable.visibilityoff)
        } else {
            painterResource(id = R.drawable.visibilitytrue)
        }

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(0.85f),
            value = mail,
            onValueChange = {
                mail = it
            },
            label = { Text(text = "Mail adresinizi giriniz") },
            leadingIcon = {
                Icon(imageVector = Icons.Default.Email, contentDescription = "Mail")
            },
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.Black,
                unfocusedBorderColor = Color.Black,
                focusedLabelColor = Color.White,
                unfocusedLabelColor = Color.White,
                focusedLeadingIconColor = Color.Black,
                unfocusedLeadingIconColor = Color.Black,
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                focusedTrailingIconColor = Color.Black,
                unfocusedTrailingIconColor = Color.Black,
                cursorColor = Color.White,
            )

        )

        Spacer(modifier = Modifier.padding(10.dp))


        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(0.85f),
            value = pass,
            onValueChange = {
                pass = it
            },
            label = { Text(text = StringConsts.txtFieldPass) },
            leadingIcon = {
                Icon(imageVector = Icons.Default.Lock, contentDescription = "Şifre")
            },
            trailingIcon = {
                IconButton(onClick = {
                    isTrue = !isTrue
                }) {
                    Icon(painter = icon, contentDescription = "Visibility")
                }
            },
            visualTransformation = if (!isTrue) PasswordVisualTransformation()
            else VisualTransformation.None,
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.Black,
                unfocusedBorderColor = Color.Black,
                focusedLabelColor = Color.White,
                unfocusedLabelColor = Color.White,
                focusedLeadingIconColor = Color.Black,
                unfocusedLeadingIconColor = Color.Black,
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                focusedTrailingIconColor = Color.Black,
                unfocusedTrailingIconColor = Color.Black,
                cursorColor = Color.White,
            )
        )
        Spacer(modifier = Modifier.padding(10.dp))

        Button(
            onClick = {
                scope.launch {

                    if (mail == "" || pass == "") {

                        Toast.makeText(context, "Boş Alan bırakmayınız", Toast.LENGTH_SHORT).show()

                    } else {


                        if(isCheck == true){

                            dataStore.saveUser(mail,pass)
                            viewModel.loginUser(mail, pass)


                        }else{

                            viewModel.loginUser(mail, pass)
                        }


                    }
                }


            }, colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent
            ), contentPadding = PaddingValues(), modifier = Modifier
                .width(300.dp)
                .height(45.dp),
            shape = RoundedCornerShape(20)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .background(myButtonColor)
                    .fillMaxSize()
                    .padding(vertical = 8.dp, horizontal = 16.dp)
            ) {
                Text(text = StringConsts.logInBtn, fontSize = 16.sp, color = Color.White)
            }


        }
        Spacer(modifier = Modifier.padding(15.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .background(Color.Black)
                    .width(125.dp)
                    .height(2.dp)
            )
            Spacer(modifier = Modifier.width(20.dp))
            Text(
                StringConsts.orText,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White
            )
            Spacer(modifier = Modifier.width(20.dp))
            Box(
                modifier = Modifier
                    .background(Color.Black)
                    .width(125.dp)
                    .height(2.dp)
            )

        }

        Spacer(modifier = Modifier.padding(15.dp))

        Row (modifier=Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center){
            Button(
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .height(50.dp),
                onClick = { navController.navigate(Screen.RegisterPage.route) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent
                ), contentPadding = PaddingValues(),
                shape = RoundedCornerShape(20)
            ) {

                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .background(MyWhite)
                        .fillMaxSize()
                        .padding(vertical = 8.dp, horizontal = 16.dp)
                ) {
                    Text(text = StringConsts.registerBtn, fontSize = 14.sp, color = Color.Black)
                }


            }
        }


    }
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
    ) {
        if (state.value?.isLoading == true) {

            CircularProgressIndicator()
        }

    }

    LaunchedEffect(key1 = state.value?.isSuccess) {
        scope.launch {
            if (state.value?.isSuccess?.isNotEmpty() == true) {
                val success = state.value?.isSuccess
                Toast.makeText(context, "${success}", Toast.LENGTH_SHORT).show()
                if (state.value?.isLogin == true) {

                    //dataStore İşlemleri
                    navController.navigate(Screen.DetailPage.route) {
                        popUpTo(Screen.LoginPage.route) {
                            inclusive = true
                        }
                    }
                }
            }

        }
    }
    LaunchedEffect(key1 = state.value?.isError) {
        scope.launch {
            if (state.value?.isError?.isNotEmpty() == true) {
                val error = state.value?.isError
                Toast.makeText(context, "Bir şeyler ters gitti", Toast.LENGTH_SHORT).show()
            }

        }
    }


}

@Preview(showBackground = true)
@Composable
fun LoginPreview() {

    LoginPage(navController = rememberNavController())

}





