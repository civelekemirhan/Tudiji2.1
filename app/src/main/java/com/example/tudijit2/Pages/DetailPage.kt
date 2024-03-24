@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package com.example.tudijit2.Pages

import android.annotation.SuppressLint

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items

import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ExitToApp

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults

import androidx.compose.runtime.*

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

import androidx.compose.ui.unit.dp

import androidx.navigation.NavHostController
import com.example.tudijit2.Bars.BarGraph
import com.example.tudijit2.Bars.BarType
import com.example.tudijit2.Bars.TabItem
import com.example.tudijit2.Navigations.DETAIL_ARGUMENT_KEY


import com.example.tudijit2.Navigations.Screen
import com.example.tudijit2.dataCustomView
import com.example.tudijit2.models.Schools
import com.example.tudijit2.models.Wastes
import com.example.tudijit2.ui.theme.newBackground
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch


private const val headerDetailPage: String = "Geri Dönüşüm Verileri"

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun DetailPage(navController: NavHostController) {



    var systemUiController = rememberSystemUiController()

    var context = LocalContext.current
    SideEffect {
        systemUiController.setSystemBarsColor(
            color = newBackground
        )
    }


    Column(
        modifier = Modifier
            .fillMaxSize(1f)
    ) {

        val scope = rememberCoroutineScope()






        FirebaseApp.initializeApp(context)
        val firestore = FirebaseFirestore.getInstance()
        val wastesCollection = firestore.collection("WASTES")
        val wastes = remember { mutableStateListOf<Wastes>() }

        LaunchedEffect(key1 = Unit) {
            scope.launch {
                wastesCollection.get()
                    .addOnSuccessListener { result ->

                        for (document in result) {

                            val waste = document.toObject(Wastes::class.java)

                            wastes.add(waste)


                        }

                    }
                    .addOnFailureListener { exception ->
                        // Handle error
                    }
            }
        }



        TopAppBar(
            title = {
                Text(
                    text = headerDetailPage,
                    modifier = Modifier.fillMaxWidth()
                )

            },
            actions = {
                IconButton(
                    onClick = { navController.navigate(Screen.DataInputPage.route)
                       },
                    colors = IconButtonDefaults.iconButtonColors(
                        contentColor = Color.White
                    )
                ) {
                    Icon(imageVector = Icons.Filled.Add, contentDescription = "Add")
                }

                IconButton(
                    onClick = {  }, colors = IconButtonDefaults.iconButtonColors(
                        contentColor = Color.White
                    )
                ) {
                    Icon(imageVector = Icons.Filled.ExitToApp, contentDescription = "Exit")
                }

            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = newBackground,
                titleContentColor = Color.White,
            )
        )

        /* Githudan Akacağın kodları buraya koy*/
        Column(
            modifier = Modifier
                .background(Color.White)
                .fillMaxSize()
        ) {

            val tabItems = listOf(
                TabItem(
                    title = "Veri Grafiği",
                ),
                TabItem(
                    title = "Veri Girişi Geçmişi"
                )
            )
            val pagerState = rememberPagerState {
                tabItems.size
            }
            var selectedTabIndex by remember {
                mutableStateOf(0)
            }

            
            LaunchedEffect(selectedTabIndex) {
                pagerState.animateScrollToPage(selectedTabIndex)
            }
            LaunchedEffect(pagerState.currentPage, pagerState.isScrollInProgress) {
                if (!pagerState.isScrollInProgress) {
                    selectedTabIndex = pagerState.currentPage
                }


            }

            TabRow(//Daha fazla item olursa eğer burayı scorrabletabrow yapmayı unutma
                selectedTabIndex = selectedTabIndex,
                containerColor = Color.Transparent
            ) {
                tabItems.forEachIndexed { index, item ->
                    Tab(
                        selected = index == selectedTabIndex,
                        onClick = {
                            selectedTabIndex = index
                        },
                        text = { Text(text = item.title, color = Color.Black) }

                    )

                }

            }


            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
            ) { index ->

                if (index == 0) {//tabItems[index].title=="Genel"
                    Box(
                        modifier = Modifier
                            .background(Color.Transparent)
                            .fillMaxSize()
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            LazyColumn {
                                items(wastes) { waste ->
                                    Column(
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        dataCustomView(
                                            schoolName =  waste.dataTitle,
                                          dataPaperCount =   waste.paperCount,
                                          dataMetalCount =   waste.metalCount,
                                          dataGlassCount =   waste.glassCount,
                                          dataPlasticCount =   waste.plasticCount,
                                            month = waste.month,
                                            year = waste.year

                                        )

                                    }


                                }

                            }
                        }

                    }
                } else if (index == 1) {
                    Box(
                        modifier = Modifier
                            .background(Color.Transparent)
                            .fillMaxSize()
                    ) {

                        LazyColumn {
                            items(wastes) { waste ->

                                if (waste.schoolID == Firebase.auth.currentUser?.uid) {
                                    Column(
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        dataCustomView(
                                            schoolName =  waste.dataTitle,
                                            dataPaperCount =   waste.paperCount,
                                            dataMetalCount =   waste.metalCount,
                                            dataGlassCount =   waste.glassCount,
                                            dataPlasticCount =   waste.plasticCount,
                                            month = waste.month,
                                            year = waste.year
                                        )


                                    }

                                }
                            }

                        }



                    }
                }


            }


        }


    }
}

@Composable
fun chartShape(barColor: Color) {

    Column(
        modifier = Modifier
            .padding(horizontal = 30.dp)
            .fillMaxSize()
            .padding(top = 20.dp)

    ) {

        val dataList = mutableListOf(30, 60, 120, 50, 70, 45, 5, 10, 15, 20, 25, 30)
        val floatValue = mutableListOf<Float>()
        val datesList =
            mutableListOf("O", "Ş", "M", "N", "M", "H", "T", "A", "E", "E", "K", "A")

        dataList.forEachIndexed { index, value ->

            floatValue.add(index = index, element = value.toFloat() / dataList.max().toFloat())

        }

        BarGraph(
            graphBarData = floatValue,
            xAxisScaleData = datesList,
            barData_ = dataList,
            height = 300.dp,
            roundType = BarType.TOP_CURVED,
            barWidth = 20.dp,
            barColor = barColor,
            barArrangement = Arrangement.SpaceEvenly
        )
    }

}











