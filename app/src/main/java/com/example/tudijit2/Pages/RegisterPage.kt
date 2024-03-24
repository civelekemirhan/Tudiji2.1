@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.tudijit2.Pages

import android.content.Context
import android.view.WindowManager
import androidx.hilt.navigation.compose.hiltViewModel
import android.widget.Toast
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.tudijit2.Data.AuthRepository
import com.example.tudijit2.InOutModels.SignUpViewModel
import com.example.tudijit2.Navigations.Screen
import com.example.tudijit2.R
import com.example.tudijit2.StringConsts
import com.example.tudijit2.models.Schools
import com.example.tudijit2.rememberImeState
import com.example.tudijit2.ui.theme.myButtonColor
import com.example.tudijit2.ui.theme.newBackground
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch

@Composable
fun RegisterPage(
    navController: NavController,
    viewModel: SignUpViewModel = hiltViewModel()
) {
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
    var passagain by remember {
        mutableStateOf("")
    }
    var schoolname by remember{
        mutableStateOf("")
    }
    var isTrue by remember { mutableStateOf(false) }
    var isTrueAgain by remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val state = viewModel.signUpState.collectAsState(initial = null)

    Column(
        modifier = Modifier
            .background(newBackground)
            .fillMaxSize()
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.CenterHorizontally,
     
    ) {
        Spacer(modifier = Modifier.size(35.dp))

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

        Text(StringConsts.registerHeader, color = Color.White, fontWeight = FontWeight.SemiBold, fontSize = 32.sp)
        Spacer(modifier = Modifier.height(35.dp))

        val icon = if (!isTrue) {
            painterResource(id = R.drawable.visibilityoff)
        } else {
            painterResource(id = R.drawable.visibilitytrue)
        }
        val iconagain = if (!isTrueAgain) {
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
            label = { Text(text = StringConsts.txtFieldMail) },
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

        Spacer(modifier = Modifier.height(20.dp))

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
        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(0.85f),
            value = passagain,
            onValueChange = {
                passagain = it
            },
            label = { Text(text = StringConsts.txtFieldPassAgain) },
            leadingIcon = {
                Icon(imageVector = Icons.Default.Lock, contentDescription = "Şifre")
            },
            trailingIcon = {
                IconButton(onClick = {
                    isTrueAgain = !isTrueAgain
                }) {
                    Icon(painter = iconagain, contentDescription = "Visibility")
                }
            },
            visualTransformation = if (!isTrueAgain) PasswordVisualTransformation()
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
        Spacer(modifier = Modifier.height(20.dp))
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(0.85f),
            value = schoolname,
            onValueChange = {
                schoolname = it
            },
            label = { Text(text = StringConsts.schoolNameField) },
            leadingIcon = {
                Icon(imageVector = Icons.Default.Home, contentDescription = "Şifre")
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
        Spacer(modifier = Modifier.height(35.dp))
        Row(modifier=Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center){
            Button(
                onClick = {

                    scope.launch {
                        if (mail.equals("")|| pass.equals("")|| passagain.equals("")|| schoolname.equals("")) {
                            Toast.makeText(context, "Boş Alan bırakmayınız", Toast.LENGTH_SHORT).show()

                        } else {
                            if (pass == passagain) {

                                viewModel.registerUser(mail, pass)
                            } else {
                                Toast.makeText(
                                    context,
                                    "Şifreler Eşleşmiyor kontrol ediniz...",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }

                    }
                }, colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent
                ), contentPadding = PaddingValues(), modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .height(50.dp),
                shape = RoundedCornerShape(20)
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .background(myButtonColor)
                        .fillMaxSize()
                        .padding(vertical = 8.dp, horizontal = 16.dp)
                ) {
                    Text(text = StringConsts.registerBtn, fontSize = 16.sp, color = Color.White)
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
                    Toast.makeText(context, "Başarıyla Kayıt Oldunuz...", Toast.LENGTH_SHORT).show()
                    addSchoolNameToFirebase(schoolname,context,Firebase.auth?.currentUser?.uid.toString())
                    if(state.value?.isRegister==true){

                            navController.navigate(Screen.DetailPage.route) {
                                popUpTo(Screen.RegisterPage.route) {
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
                    Toast.makeText(context, "Bir şeyler ters gitti...", Toast.LENGTH_SHORT).show()
                }

            }
        }

    }
}

fun addSchoolNameToFirebase(
    schoolName:String,
    context: Context,
    userID:String
) {
    // on below line creating an instance of firebase firestore.
    val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    //creating a collection reference for our Firebase Firestore database.
    val dbCourses: CollectionReference = db.collection("SCHOOLS")
    //adding our data to our courses object class.
    val schools =
        Schools(schoolName.uppercase(), userID )

    //below method is use to add data to Firebase Firestore.
    dbCourses.add(schools).addOnSuccessListener {

    }.addOnFailureListener { e ->

    }

}