@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package com.example.tudijit2.Pages

import android.annotation.SuppressLint
import android.widget.Toast

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items

import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults

import androidx.compose.runtime.*

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

import androidx.navigation.NavHostController
import com.example.tudijit2.Bars.BarGraph
import com.example.tudijit2.Bars.BarType
import com.example.tudijit2.Bars.TabItem
import com.example.tudijit2.Navigations.DETAIL_ARGUMENT_KEY


import com.example.tudijit2.Navigations.Screen
import com.example.tudijit2.R
import com.example.tudijit2.dataCustomView
import com.example.tudijit2.models.Schools
import com.example.tudijit2.models.Wastes
import com.example.tudijit2.ui.theme.newBackground
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch


private const val headerDetailPage: String = "Geri Dönüşüm Verileri"

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun DetailPage(navController: NavHostController) {

    var auth: FirebaseAuth = FirebaseAuth.getInstance();
    var user = auth.currentUser;

    var systemUiController = rememberSystemUiController()

    var context = LocalContext.current
    SideEffect {
        systemUiController.setSystemBarsColor(
            color = newBackground
        )
    }
    var openDialog by remember { mutableStateOf(false) }

    var expanded by remember { mutableStateOf(false) }
    var suggestions = ArrayList<String>()
    suggestions.add("Ocak")
    suggestions.add("Şubat")
    suggestions.add("Mart")
    suggestions.add("Nisan")
    suggestions.add("Mayıs")
    suggestions.add("Haziran")
    suggestions.add("Temmuz")
    suggestions.add("Ağustos")
    suggestions.add("Eylül")
    suggestions.add("Ekim")
    suggestions.add("Kasım")
    suggestions.add("Aralık")
    var month by remember { mutableStateOf("") }

    var textfieldSize by remember { mutableStateOf(Size.Zero) }

    val icon = if (expanded)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.KeyboardArrowDown

    var expandedTwo by remember { mutableStateOf(false) }
    var suggestionsTwo = ArrayList<String>()


    for (i in 2024..2034){
        suggestionsTwo.add(i.toString())
    }





    var year by remember { mutableStateOf("") }

    var textfieldSizeTwo by remember { mutableStateOf(Size.Zero) }

    val iconTwo = if (expandedTwo)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.KeyboardArrowDown

    var choosedSchool by remember { mutableStateOf("") }
    var schoolsName=remember{ mutableStateListOf<String>() }
    var expandedThree by remember { mutableStateOf(false) }
    var textfieldSizeThree by remember { mutableStateOf(Size.Zero) }
    var isFilter by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize(1f)
    ) {

        val scope = rememberCoroutineScope()






        FirebaseApp.initializeApp(context)
        val firestore = FirebaseFirestore.getInstance()
        val wastesCollection = firestore.collection("WASTES")
        val schoolCollection = firestore.collection("SCHOOLS")
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
            scope.launch {
                schoolCollection.get()
                    .addOnSuccessListener { result ->

                        for (document in result) {

                            val school = document.toObject(Schools::class.java)

                            schoolsName.add(school.schoolName)



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
                    onClick = {

                        openDialog = !openDialog


                    }, colors = IconButtonDefaults.iconButtonColors(
                        contentColor = Color.White
                    )
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.vector_filter),
                        contentDescription = "Exit"
                    )
                }

                IconButton(
                    onClick = {
                        navController.navigate(Screen.DataInputPage.route)
                    },
                    colors = IconButtonDefaults.iconButtonColors(
                        contentColor = Color.White
                    )
                ) {
                    Icon(imageVector = Icons.Filled.Add, contentDescription = "Add")
                }

                IconButton(
                    onClick = {

                        auth.signOut();
                        Toast.makeText(
                            context,
                            "Hesaptan başarıyla çıkış yapıldı",
                            Toast.LENGTH_SHORT
                        ).show()
                        navController.navigate(Screen.LoginPage.route) {
                            popUpTo(Screen.DetailPage.route) {
                                inclusive = true
                            }
                        }


                    }, colors = IconButtonDefaults.iconButtonColors(
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
                    title = "Tüm Okullar",
                ),
                TabItem(
                    title = "Kendi Okulum"
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
                                        if(isFilter){

                                            if(choosedSchool != "" && year != "" && month!=""){
                                                if (waste.dataTitle==choosedSchool && waste.month==month && waste.year==year){
                                                    dataCustomView(
                                                        schoolName = waste.dataTitle,
                                                        dataPaperCount = waste.paperCount,
                                                        dataMetalCount = waste.metalCount,
                                                        dataGlassCount = waste.glassCount,
                                                        dataPlasticCount = waste.plasticCount,
                                                        month = waste.month,
                                                        year = waste.year

                                                    )
                                                }
                                            }else if(year !="" && month !="" && choosedSchool==""){
                                                if (waste.month==month && waste.year==year) {
                                                    dataCustomView(
                                                        schoolName = waste.dataTitle,
                                                        dataPaperCount = waste.paperCount,
                                                        dataMetalCount = waste.metalCount,
                                                        dataGlassCount = waste.glassCount,
                                                        dataPlasticCount = waste.plasticCount,
                                                        month = waste.month,
                                                        year = waste.year

                                                    )
                                                }
                                            }else if(year !="" && month =="" && choosedSchool!=""){
                                                if (waste.dataTitle==choosedSchool && waste.year==year) {
                                                    dataCustomView(
                                                        schoolName = waste.dataTitle,
                                                        dataPaperCount = waste.paperCount,
                                                        dataMetalCount = waste.metalCount,
                                                        dataGlassCount = waste.glassCount,
                                                        dataPlasticCount = waste.plasticCount,
                                                        month = waste.month,
                                                        year = waste.year

                                                    )
                                                }
                                            }else if(year =="" && month !="" && choosedSchool!=""){
                                                if (waste.dataTitle==choosedSchool && waste.month==month) {
                                                    dataCustomView(
                                                        schoolName = waste.dataTitle,
                                                        dataPaperCount = waste.paperCount,
                                                        dataMetalCount = waste.metalCount,
                                                        dataGlassCount = waste.glassCount,
                                                        dataPlasticCount = waste.plasticCount,
                                                        month = waste.month,
                                                        year = waste.year

                                                    )
                                                }
                                            }else if(year =="" && month !="" && choosedSchool==""){

                                                if (waste.month==month ) {
                                                    dataCustomView(
                                                        schoolName = waste.dataTitle,
                                                        dataPaperCount = waste.paperCount,
                                                        dataMetalCount = waste.metalCount,
                                                        dataGlassCount = waste.glassCount,
                                                        dataPlasticCount = waste.plasticCount,
                                                        month = waste.month,
                                                        year = waste.year

                                                    )
                                                }

                                            }else if(year !="" && month =="" && choosedSchool==""){

                                                if (waste.year==year ) {
                                                    dataCustomView(
                                                        schoolName = waste.dataTitle,
                                                        dataPaperCount = waste.paperCount,
                                                        dataMetalCount = waste.metalCount,
                                                        dataGlassCount = waste.glassCount,
                                                        dataPlasticCount = waste.plasticCount,
                                                        month = waste.month,
                                                        year = waste.year

                                                    )
                                                }


                                            }else{
                                                if (waste.dataTitle==choosedSchool ) {
                                                    dataCustomView(
                                                        schoolName = waste.dataTitle,
                                                        dataPaperCount = waste.paperCount,
                                                        dataMetalCount = waste.metalCount,
                                                        dataGlassCount = waste.glassCount,
                                                        dataPlasticCount = waste.plasticCount,
                                                        month = waste.month,
                                                        year = waste.year

                                                    )
                                                }
                                            }


                                        }else{
                                            dataCustomView(
                                                schoolName = waste.dataTitle,
                                                dataPaperCount = waste.paperCount,
                                                dataMetalCount = waste.metalCount,
                                                dataGlassCount = waste.glassCount,
                                                dataPlasticCount = waste.plasticCount,
                                                month = waste.month,
                                                year = waste.year

                                            )
                                        }


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
                                            schoolName = waste.dataTitle,
                                            dataPaperCount = waste.paperCount,
                                            dataMetalCount = waste.metalCount,
                                            dataGlassCount = waste.glassCount,
                                            dataPlasticCount = waste.plasticCount,
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

    if (openDialog) {

        Dialog(
            onDismissRequest = {
                openDialog = !openDialog
            },
            properties = DialogProperties(
                usePlatformDefaultWidth = false
            )


        ) {

            Card(

                shape = RoundedCornerShape(5.dp),
                modifier = Modifier
                    .fillMaxWidth(0.85f)
                    .border(
                        width = 1.dp,
                        color = Color.Transparent,
                        shape = RoundedCornerShape(5.dp)
                    ),
            ) {

                Column(
                    modifier=Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Row (modifier= Modifier
                        .fillMaxWidth(0.90f)
                        .padding(10.dp)){

                         Text(text = "Tarihe göre listele :")



                    }
                    Row(modifier= Modifier
                        .fillMaxWidth(0.90f)
                        .padding(10.dp), horizontalArrangement = Arrangement.SpaceEvenly){
                        Column(modifier = Modifier
                            .fillMaxWidth(0.45f)){
                            OutlinedTextField(
                                value = month,
                                readOnly = true,
                                onValueChange = { month = it },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .onGloballyPositioned { coordinates ->
                                        //This value is used to assign to the DropDown the same width
                                        textfieldSize = coordinates.size.toSize()
                                    },
                                label = { Text("AY") },
                                trailingIcon = {
                                    Icon(icon, "contentDescription",
                                        Modifier.clickable { expanded = !expanded })
                                },
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = Color.Black,
                                    unfocusedBorderColor = Color.Black,
                                    focusedLeadingIconColor = Color.Black,
                                    unfocusedLeadingIconColor = Color.Black,
                                    focusedTextColor = Color.Black,
                                    unfocusedTextColor = Color.Black,
                                    focusedTrailingIconColor = Color.Black,
                                    unfocusedTrailingIconColor = Color.Black,
                                    cursorColor = Color.Black,
                                )
                            )
                            DropdownMenu(
                                expanded = expanded,
                                onDismissRequest = { expanded = false },
                                modifier = Modifier
                                    .width(with(LocalDensity.current) { textfieldSize.width.toDp() })
                                    .height(200.dp)
                            ) {
                                suggestions.forEach { label ->
                                    DropdownMenuItem(text = { Text(text = label) }, onClick = {
                                        month = label
                                        expanded = false
                                    })
                                }
                            }
                        }
                        Column(modifier = Modifier
                            .fillMaxWidth(0.90f)){
                            OutlinedTextField(
                                value = year,
                                readOnly = true,
                                onValueChange = { year = it },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .onGloballyPositioned { coordinates ->
                                        //This value is used to assign to the DropDown the same width
                                        textfieldSizeTwo = coordinates.size.toSize()
                                    },
                                label = { Text("YIL") },
                                trailingIcon = {
                                    Icon(iconTwo, "contentDescription",
                                        Modifier.clickable { expandedTwo = !expandedTwo })
                                },
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = Color.Black,
                                    unfocusedBorderColor = Color.Black,

                                    focusedLeadingIconColor = Color.Black,
                                    unfocusedLeadingIconColor = Color.Black,
                                    focusedTextColor = Color.Black,
                                    unfocusedTextColor = Color.Black,
                                    focusedTrailingIconColor = Color.Black,
                                    unfocusedTrailingIconColor = Color.Black,
                                    cursorColor = Color.Black,
                                )
                            )
                            DropdownMenu(
                                expanded = expandedTwo,
                                onDismissRequest = { expandedTwo = false },
                                modifier = Modifier
                                    .width(with(LocalDensity.current) { textfieldSizeTwo.width.toDp() })
                                    .height(200.dp)
                            ) {
                                suggestionsTwo.forEach { label ->
                                    DropdownMenuItem(text = { Text(text = label) }, onClick = {
                                        year = label
                                        expandedTwo = false
                                    })
                                }
                            }
                        }




                    }
                    Row (modifier= Modifier
                        .fillMaxWidth(0.90f)
                        .padding(10.dp)){

                        Text(text = "Okul ismine göre listele :")



                    }
                    Row(modifier= Modifier
                        .fillMaxWidth(0.90f)
                        .padding(10.dp), horizontalArrangement = Arrangement.SpaceEvenly){
                        Column(modifier = Modifier
                            .fillMaxWidth(0.90f)){
                            OutlinedTextField(
                                value = choosedSchool,
                                readOnly = true,
                                onValueChange = { choosedSchool = it },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .onGloballyPositioned { coordinates ->
                                        //This value is used to assign to the DropDown the same width
                                        textfieldSizeThree = coordinates.size.toSize()
                                    },
                                label = { Text("SEÇ") },
                                trailingIcon = {
                                    Icon(iconTwo, "contentDescription",
                                        Modifier.clickable { expandedThree = !expandedThree })
                                },
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = Color.Black,
                                    unfocusedBorderColor = Color.Black,

                                    focusedLeadingIconColor = Color.Black,
                                    unfocusedLeadingIconColor = Color.Black,
                                    focusedTextColor = Color.Black,
                                    unfocusedTextColor = Color.Black,
                                    focusedTrailingIconColor = Color.Black,
                                    unfocusedTrailingIconColor = Color.Black,
                                    cursorColor = Color.Black,
                                )
                            )
                            DropdownMenu(
                                expanded = expandedThree,
                                onDismissRequest = { expandedThree = false },
                                modifier = Modifier
                                    .width(with(LocalDensity.current) { textfieldSizeThree.width.toDp() })
                                    .height(200.dp)
                            ) {
                                schoolsName.forEach { label ->
                                    DropdownMenuItem(text = { Text(text = label) }, onClick = {
                                        choosedSchool = label
                                        expandedThree = false
                                    })
                                }
                            }
                        }




                    }
                    Divider(modifier=Modifier.fillMaxWidth(0.90f))

                    Row(modifier= Modifier
                        .fillMaxWidth(0.90f)
                        .padding(10.dp), horizontalArrangement = Arrangement.End){

                        if(isFilter){

                            TextButton(onClick = {
                                isFilter=false
                                openDialog=!openDialog
                                month=""
                                year=""
                                choosedSchool=""
                            }) {
                                Text(text = "Filtreyi İptal Et",color=Color.Red)
                            }
                            TextButton(onClick = {
                                isFilter = true
                                openDialog=!openDialog



                            }) {
                                Text(text = "Filtrele")
                            }

                        }else{

                            TextButton(onClick = {

                                openDialog=!openDialog
                                month=""
                                year=""
                                choosedSchool=""
                            }) {
                                Text(text = "İptal Et")
                            }
                            TextButton(onClick = {

                                if(month=="" && year==""&&choosedSchool==""){
                                    openDialog=!openDialog

                                }else{
                                    isFilter = true
                                    openDialog=!openDialog
                                }




                            }) {
                                Text(text = "Filtrele")
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











