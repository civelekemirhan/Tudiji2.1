@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.tudijit2.Pages
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.tudijit2.Navigations.Screen
import com.example.tudijit2.R
import com.example.tudijit2.ui.theme.myButtonColor
import com.example.tudijit2.ui.theme.newBackground
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun VerificationPage(navController:NavHostController){
    var systemUiController = rememberSystemUiController()
    val darkTheme = isSystemInDarkTheme()
    SideEffect {
        systemUiController.setSystemBarsColor(
            color = newBackground

        )
    }
    Column(
        modifier = Modifier
            .background(newBackground).fillMaxSize()
        , horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center

        ) {

        Spacer(modifier = Modifier.height(50.dp))
        Box(modifier = Modifier.size(150.dp)) {
            Image(
                painter = painterResource(id = R.drawable.tudijit),
                contentDescription = "Dijital Donusum Toplulugu",
                modifier = Modifier
                    .size(150.dp)
                    .clip(CircleShape)
                    .border(1.dp, Color.Black, CircleShape)

            )
        }
        Spacer(modifier = Modifier.height(35.dp))

        Text("ŞİFREMİ UNUTTUM", color = Color.White, fontWeight = FontWeight.SemiBold, fontSize = 32.sp)
        Spacer(modifier = Modifier.height(35.dp))
        var mail by remember {
            mutableStateOf("")
        }


        OutlinedTextField(
            modifier = Modifier.width(350.dp),
            value = mail,
            onValueChange = {
                mail = it
            },
            label = { Text(text = "Gelen Kodu Giriniz") },
            leadingIcon = {
                Icon(imageVector = Icons.Default.Lock, contentDescription = "Verification Code")
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.Black,
                unfocusedBorderColor = Color.Black,
                focusedLabelColor = Color.White,
                unfocusedLabelColor = Color.White,
                focusedLeadingIconColor = Color.Black,
                focusedTextColor = Color.White,
                cursorColor = Color.White,
            ),
            singleLine = true

        )

        Spacer(modifier = Modifier.height(35.dp))

        Button(
            onClick = { navController.navigate(Screen.DetailPage.route){
                popUpTo(Screen.VerificationPage.route) {
                    inclusive = true
                }
            }},
            colors = ButtonDefaults.buttonColors(
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
                Text(text = "İLERLE", fontSize = 16.sp, color = Color.White)
            }


        }
    }

}


