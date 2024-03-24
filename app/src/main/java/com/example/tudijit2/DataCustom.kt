package com.example.tudijit2

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tudijit2.ui.theme.newBackground
import java.sql.Timestamp
import java.time.Year


@Composable
fun dataCustomView(
    schoolName: String = "Hatırlatıcı Başlığı",
    dataGlassCount: String = "15",
    dataPaperCount:String="15",
    dataMetalCount:String="15",
    dataPlasticCount:String="15",
    month:String="",
    year: String="",
    dataTime: com.google.firebase.Timestamp = com.google.firebase.Timestamp.now()
) {

    Card(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 5.dp
        ), colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        modifier = Modifier
            .fillMaxWidth(0.95f)
            .fillMaxHeight(0.2f)
            .padding(10.dp),
        shape = RoundedCornerShape(5.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(15.dp)
                .fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row() {
                Icon(
                    painter = painterResource(id = R.drawable.pin),
                    contentDescription = null,
                    tint = Color.DarkGray,
                    modifier = Modifier.padding(3.dp)
                )
                Text(
                    text = " $schoolName",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = newBackground,

                )
            }
        }
        Row(modifier = Modifier.padding(start = 15.dp, bottom = 15.dp)) {

            Text(
                text = "Dönüştürülen Cam miktarı : ",
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black
            )
            Text(
                text = "$dataGlassCount kg",
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black
            )

        }
        Row(modifier = Modifier.padding(start = 15.dp, bottom = 15.dp)) {

            Text(
                text = "Dönüştürülen Kağıt miktarı : ",
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black
            )
            Text(
                text = "$dataPaperCount kg",
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black
            )

        }
        Row(modifier = Modifier.padding(start = 15.dp, bottom = 15.dp)) {

            Text(
                text = "Dönüştürülen Plastik miktarı : ",
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black
            )
            Text(
                text = "$dataPlasticCount kg",
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black
            )

        }
        Row(modifier = Modifier.padding(start = 15.dp, bottom = 15.dp)) {

            Text(
                text = "Dönüştürülen Metal miktarı : ",
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black
            )
            Text(
                text = "$dataMetalCount kg",
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black
            )

        }

        Row(modifier = Modifier.padding(start = 15.dp, bottom = 15.dp)){
            Row(modifier = Modifier.padding(3.dp)) {
                Text(
                    text = "Tarih:",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = newBackground
                )
                Text(
                    text = "$month $year",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.DarkGray
                )

            }

        }

    }


}


@Composable
@Preview(showBackground = true, showSystemUi = true)
fun PreviewDataCustomPreview() {
    dataCustomView()

}





