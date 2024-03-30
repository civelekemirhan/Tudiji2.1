@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.tudijit2.Pages

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.animation.core.tween

import androidx.compose.foundation.background

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items

import androidx.compose.foundation.rememberScrollState

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button

import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults

import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text


import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier


import androidx.compose.ui.graphics.Color


import androidx.compose.ui.platform.LocalContext


import androidx.compose.ui.text.font.FontWeight

import androidx.compose.ui.tooling.preview.Preview

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.tudijit2.InputCustom
import com.example.tudijit2.Navigations.DETAIL_ARGUMENT_KEY
import com.example.tudijit2.Navigations.Screen

import com.example.tudijit2.StringConsts
import com.example.tudijit2.dataCustomView

import com.example.tudijit2.models.Schools

import com.example.tudijit2.models.Wastes

import com.example.tudijit2.rememberImeState


import com.example.tudijit2.ui.theme.newBackground
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.firebase.FirebaseApp

import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


@Composable
fun DataInputPage(
    navController: NavHostController
) {//


    val imeState = rememberImeState()
    val scrollState = rememberScrollState()
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var schoolname by remember {
        mutableStateOf("")
    }
    var user = Firebase.auth.currentUser?.uid
    var dataType by remember {
        mutableStateOf("")
    }
    var dataTitleText by remember { mutableStateOf("") }

    LaunchedEffect(key1 = imeState.value) {
        if (imeState.value) {
            scrollState.animateScrollTo(scrollState.maxValue, tween(300))
        }
    }

    Column(modifier = Modifier.fillMaxSize(1f)) {
        TopAppBar(
            navigationIcon = {
                IconButton(onClick = {
                   navController.popBackStack()
                }) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Localized description",
                        tint = Color.White
                    )
                }
            },
            title = {
                Text(
                    text = StringConsts.headerDataPage
                )
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = newBackground,
                titleContentColor = Color.White,


                ),
        )


        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .verticalScroll(scrollState)
                .padding(top = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,


            ) {
            var systemUiController = rememberSystemUiController()

            SideEffect {
                systemUiController.setSystemBarsColor(
                    color = newBackground
                )
            }



            FirebaseApp.initializeApp(context)
            val firestore = FirebaseFirestore.getInstance()
            val schoolCollection = firestore.collection("SCHOOLS")
            val schools = remember { mutableStateListOf<Schools>() }

            LaunchedEffect(key1 = Unit) {
                scope.launch {
                    schoolCollection.get()
                        .addOnSuccessListener { result ->

                            for (document in result) {

                                val school = document.toObject(Schools::class.java)
                                if (user==school.schoolID){
                                    schools.add(school)
                                }

                            }

                        }
                        .addOnFailureListener { exception ->
                            // Handle error
                        }
                }
            }

            for (a in schools){
                    schoolname=a.schoolName
                }
            InputCustom(schoolName = schoolname, schoolID = "",navController)

        }


    }


}


@Composable
@Preview(showBackground = true, showSystemUi = true)
fun PreDataInputPage() {

    DataInputPage(navController = rememberNavController())
}

