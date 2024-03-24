package com.example.tudijit2.Pages
import android.app.StatusBarManager
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.tudijit2.Navigations.Screen
import com.example.tudijit2.R
import com.example.tudijit2.ui.theme.newBackground
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavHostController) {

    var systemUiController = rememberSystemUiController()
    val darkTheme = isSystemInDarkTheme()
    SideEffect {
        systemUiController.setSystemBarsColor(
            color = newBackground
        )

    }


    var startAnimation by remember { mutableStateOf(false) }
    val alphaAnim = animateFloatAsState(
        targetValue = if(startAnimation) 1f else 0f,
        animationSpec = tween(
            durationMillis = 2000
        )
    )
    LaunchedEffect(key1 = true){
        startAnimation=true
        delay(2000)
        navController.navigate(Screen.LoginPage.route){
            popUpTo(Screen.Splash.route) {
                inclusive = true
            }
        }
    }

    /*
    .fillMaxSize()
            .paint(painterResource(id = R.drawable.sunrise9),
                contentScale = ContentScale.Crop)
            .background(Color.Black.copy(alpha = 0.5f))
     */
    Column(
        modifier = Modifier
            .background(newBackground)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {


        Box(modifier = Modifier.size(200.dp)) {
            Image(
                painter = painterResource(id = R.drawable.tudijit),
                contentDescription = "Logo",
                modifier = Modifier
                    .fillMaxSize()
                    .clip(CircleShape)
                    .alpha(alphaAnim.value)
                    .border(1.dp, Color.Black, CircleShape),

                )
        }

    }


}

