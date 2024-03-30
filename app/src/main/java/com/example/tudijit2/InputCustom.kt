package com.example.tudijit2

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.tudijit2.Navigations.Screen
import com.example.tudijit2.models.Wastes
import com.example.tudijit2.ui.theme.newBackground
import com.google.android.gms.common.internal.Constants
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputCustom(schoolName: String, schoolID: String,navController: NavHostController) {
    val context = LocalContext.current
    var user = Firebase.auth.currentUser?.uid
    var scope= rememberCoroutineScope()
    var paperCount by remember { mutableStateOf("") }
    var metalCount by remember { mutableStateOf("") }
    var plasticCount by remember { mutableStateOf("") }
    var glassCount by remember { mutableStateOf("") }
    val maxLength = 15
    var isThere:Boolean by remember {
        mutableStateOf(false)
    }

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


    FirebaseApp.initializeApp(context)
    val firestore = FirebaseFirestore.getInstance()
    val wastesCollection = firestore.collection("WASTES")
    val months = remember { mutableStateListOf<String>() }
    val years = remember { mutableStateListOf<String>() }
    var fetchedData = remember { mutableStateListOf<String>() }
    monthAndYearDataInformation(
        context = context,
        scope = scope,
        user = user,
        fetchedData=fetchedData,
        wastesCollection = wastesCollection
    )
    Card(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth(0.85f),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 5.dp
        ), colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        shape = RoundedCornerShape(5.dp)
    ) {
        Row(
            Modifier
                .padding(15.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                "VERİ GİRİŞİ",
                color = newBackground,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
        Row(
            modifier = Modifier
                .padding(bottom = 15.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                schoolName,
                color = Color.Gray,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold
            )


        }
        Divider(Modifier.padding(start = 10.dp, end = 10.dp, bottom = 10.dp))
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth(0.90f)
                    .padding(10.dp),
                value = paperCount,
                onValueChange = {
                    if (it.length <= maxLength) paperCount = it
                },
                label = {
                    Text(
                        text = "Kağıt atık veri girişi",
                        color = Color.Black
                    )
                },
                singleLine = true,
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = null
                    )
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Black,
                    unfocusedBorderColor = Color.Black,
                    focusedLabelColor = Color.White,
                    unfocusedLabelColor = Color.White,
                    focusedLeadingIconColor = Color.Black,
                    unfocusedLeadingIconColor = Color.Black,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    focusedTrailingIconColor = Color.Black,
                    unfocusedTrailingIconColor = Color.Black,
                    cursorColor = Color.Black,
                )

            )
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth(0.90f)
                    .padding(10.dp),
                value = metalCount,
                onValueChange = {
                    if (it.length <= maxLength) metalCount = it
                },
                label = {
                    Text(
                        text = "Metal atık veri girişi",
                        color = Color.Black
                    )
                },
                singleLine = true,
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = null
                    )
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Black,
                    unfocusedBorderColor = Color.Black,
                    focusedLabelColor = Color.White,
                    unfocusedLabelColor = Color.White,
                    focusedLeadingIconColor = Color.Black,
                    unfocusedLeadingIconColor = Color.Black,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    focusedTrailingIconColor = Color.Black,
                    unfocusedTrailingIconColor = Color.Black,
                    cursorColor = Color.Black,
                )

            )
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth(0.90f)
                    .padding(10.dp),
                value = glassCount,
                onValueChange = {
                    if (it.length <= maxLength) glassCount = it
                },
                label = {
                    Text(
                        text = "Cam atık veri girişi",
                        color = Color.Black
                    )
                },
                singleLine = true,
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = null
                    )
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Black,
                    unfocusedBorderColor = Color.Black,
                    focusedLabelColor = Color.White,
                    unfocusedLabelColor = Color.White,
                    focusedLeadingIconColor = Color.Black,
                    unfocusedLeadingIconColor = Color.Black,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    focusedTrailingIconColor = Color.Black,
                    unfocusedTrailingIconColor = Color.Black,
                    cursorColor = Color.Black,
                )

            )


            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth(0.90f)
                    .padding(10.dp),
                value = plasticCount,
                onValueChange = {
                    if (it.length <= maxLength) plasticCount = it
                },
                label = {
                    Text(
                        text = "Plastik atık veri girişi",
                        color = Color.Black
                    )
                },
                singleLine = true,
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = null
                    )
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Black,
                    unfocusedBorderColor = Color.Black,
                    focusedLabelColor = Color.White,
                    unfocusedLabelColor = Color.White,
                    focusedLeadingIconColor = Color.Black,
                    unfocusedLeadingIconColor = Color.Black,
                    focusedTextColor = Color.Black,
                    unfocusedTextColor = Color.Black,
                    focusedTrailingIconColor = Color.Black,
                    unfocusedTrailingIconColor = Color.Black,
                    cursorColor = Color.Black,
                )

            )

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


        }
        Divider(Modifier.padding(10.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Button(onClick = {
                if (metalCount.equals("") || paperCount.equals("") || glassCount.equals(
                        ""
                    ) || plasticCount.equals("") || month.equals("") || year.equals("")
                ) {
                    Toast.makeText(context, "Boş alan bırakmayınız", Toast.LENGTH_SHORT)
                        .show()
                } else {

                    var selectedDate=month+year
                    for (a:String in  fetchedData) {
                        if (a == selectedDate) {

                            isThere = !isThere
                            break
                        }
                    }

                    if(isThere){
                        Toast.makeText(context,"Bu tarih için daha önce veri girişi yapılmış",Toast.LENGTH_SHORT).show()
                        isThere=!isThere
                    }else{
                        addDataToFirebase(
                            user,
                            schoolName,
                            paperCount,
                            metalCount,
                            glassCount,
                            plasticCount,
                            month,
                            year,
                            context
                        )
                        paperCount=""
                        metalCount=""
                        glassCount=""
                        plasticCount=""
                        month=""
                        year=""

                        navController.popBackStack()
                    }

                }

            }) {
                Text(text = "Tamamla", color = Color.White)

            }

        }


    }
}

fun addDataToFirebase(
    schoolID: String?,
    schoolName: String,
    paperCount: String,
    metalCount: String,
    glassCount: String,
    plasticCount: String,
    month:String,
    year:String,
    context: Context
) {
    // on below line creating an instance of firebase firestore.
    val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    //creating a collection reference for our Firebase Firestore database.
    val dbCourses: CollectionReference = db.collection("WASTES")
    //adding our data to our courses object class.
    val wastes =
        Wastes(schoolID, schoolName.uppercase(), paperCount, metalCount, glassCount, plasticCount,month,year)

    //below method is use to add data to Firebase Firestore.
    dbCourses.add(wastes).addOnSuccessListener {
        // after the data addition is successful
        // we are displaying a success toast message.
        Toast.makeText(
            context,
            "Veri girişi başarılı",
            Toast.LENGTH_SHORT
        ).show()

    }.addOnFailureListener { e ->
        // this method is called when the data addition process is failed.
        // displaying a toast message when data addition is failed.
        Toast.makeText(context, "Veri girişi başarısız \n$e", Toast.LENGTH_SHORT).show()
    }
}
@Composable
fun monthAndYearDataInformation(context: Context, scope:CoroutineScope, user:String?, fetchedData:SnapshotStateList<String>, wastesCollection: CollectionReference){

    LaunchedEffect(key1 = Unit) {
        scope.launch {
            wastesCollection.get()
                .addOnSuccessListener { result ->

                    for (document in result) {

                        val waste = document.toObject(Wastes::class.java)

                        if(user==waste.schoolID){
                            fetchedData.add(waste.month+waste.year)
                            Log.d("DATE","month=${waste.month} and year=${waste.year} \n ")
                        }

                    }

                }
                .addOnFailureListener { exception ->
                    // Handle error
                }
        }
    }

}