@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.tudijit2.dialog

import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.tudijit2.rememberImeState
import com.example.tudijit2.ui.theme.myBlue
import com.example.tudijit2.ui.theme.newBackground

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CustomDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    dataType: String,
    textTitle: String,
    textContent: String
) {



    var textTitle_ = textTitle
    var textContent_ = textContent
    Dialog(
        onDismissRequest = {
            onDismiss()
        },
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        )


    ) {
        Card(

            shape = RoundedCornerShape(5.dp),
            modifier = Modifier
                .fillMaxWidth(0.85f)
                .border(width = 1.dp, color = Color.Transparent, shape = RoundedCornerShape(5.dp))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                   ,
                verticalArrangement = Arrangement.spacedBy(25.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 15.dp, end = 15.dp, top = 15.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    Text(
                        "VERİ GİRİŞİ",
                        color = newBackground,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 30.sp
                    )

                    Row() {
                        Text(
                            "Tip:",
                            color = myBlue,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 30.sp
                        )
                        Text("${dataType}", fontWeight = FontWeight.SemiBold, fontSize = 30.sp)

                    }


                }

                Divider(Modifier.padding(start = 15.dp, end = 15.dp))



                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(0.90f),
                        value = textTitle_,
                        onValueChange = {
                            textTitle_ = it
                        },
                        label = { Text(text = "Veri Girişi Başlığı", color = Color.Black) },
                        leadingIcon = {
                            Icon(imageVector = Icons.Default.Edit, contentDescription = null)
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
                        modifier = Modifier.fillMaxWidth(0.90f),
                        value = textContent_,
                        onValueChange = {
                            textContent_ = it
                        },
                        label = { Text(text = "$dataType veri girişi", color = Color.Black) },
                        leadingIcon = {
                            Icon(imageVector = Icons.Default.Edit, contentDescription = null)
                        },
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
                            cursorColor = Color.Black,
                        )

                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 15.dp, end = 15.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {

                        var expanded by remember { mutableStateOf(false) }
                        val suggestions = listOf("Kotlin", "Java", "Dart", "Python")
                        var selectedText by remember { mutableStateOf("") }

                        var textfieldSize by remember { mutableStateOf(Size.Zero) }

                        val icon = if (expanded)
                            Icons.Filled.KeyboardArrowUp
                        else
                            Icons.Filled.KeyboardArrowDown


                        Column() {
                            OutlinedTextField(
                                value = selectedText,
                                onValueChange = { selectedText = it },
                                modifier = Modifier
                                    .width(150.dp)
                                    .onGloballyPositioned { coordinates ->
                                        //This value is used to assign to the DropDown the same width
                                        textfieldSize = coordinates.size.toSize()
                                    },
                                label = { Text("AY") },
                                trailingIcon = {
                                    Icon(icon, "contentDescription",
                                        Modifier.clickable { expanded = !expanded })
                                }
                            )
                            DropdownMenu(
                                expanded = expanded,
                                onDismissRequest = { expanded = false },
                                modifier = Modifier
                                    .width(with(LocalDensity.current) { textfieldSize.width.toDp() })
                            ) {
                                suggestions.forEach { label ->
                                    DropdownMenuItem(text = { Text(text = label) }, onClick = {
                                        selectedText = label
                                        expanded = false
                                    })
                                }
                            }
                        }

                        var expandedTwo by remember { mutableStateOf(false) }
                        val suggestionsTwo = listOf("Kotlin", "Java", "Dart", "Python")
                        var selectedTextTwo by remember { mutableStateOf("") }

                        var textfieldSizeTwo by remember { mutableStateOf(Size.Zero) }

                        val iconTwo = if (expandedTwo)
                            Icons.Filled.KeyboardArrowUp
                        else
                            Icons.Filled.KeyboardArrowDown


                        Column() {
                            OutlinedTextField(
                                value = selectedTextTwo,
                                onValueChange = { selectedTextTwo = it },
                                modifier = Modifier
                                    .width(150.dp)
                                    .onGloballyPositioned { coordinates ->
                                        //This value is used to assign to the DropDown the same width
                                        textfieldSizeTwo = coordinates.size.toSize()
                                    },
                                label = { Text("YIL") },
                                trailingIcon = {
                                    Icon(iconTwo, "contentDescription",
                                        Modifier.clickable { expandedTwo = !expandedTwo })
                                }
                            )
                            DropdownMenu(
                                expanded = expandedTwo,
                                onDismissRequest = { expandedTwo = false },
                                modifier = Modifier
                                    .width(with(LocalDensity.current) { textfieldSizeTwo.width.toDp() })
                            ) {
                                suggestionsTwo.forEach { label ->
                                    DropdownMenuItem(text = { Text(text = label) }, onClick = {
                                        selectedTextTwo = label
                                        expandedTwo = false
                                    })
                                }
                            }
                        }


                    }


                }
                Divider(Modifier.padding(start = 15.dp, end = 15.dp, top = 15.dp))

            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(15.dp),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextButton(onClick = { onDismiss() }) {

                    Text(
                        text = "İPTAL ET",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.Black
                    )

                }
                Spacer(modifier = Modifier.width(10.dp))
                TextButton(onClick = { onConfirm() }) {

                    Text(
                        text = "TAMAMLA",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = myBlue
                    )

                }
            }
        }


    }
}

