package com.example.tudijit2

import android.os.Bundle
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.tudijit2.InOutModels.SignInViewModel
import com.example.tudijit2.Navigations.Screen
import com.example.tudijit2.Navigations.SetupNavGraph
import com.example.tudijit2.datastore.UserStore
import com.example.tudijit2.models.Wastes
import com.example.tudijit2.ui.theme.MyGreen
import com.example.tudijit2.ui.theme.Tudijit2Theme
import com.example.tudijit2.ui.theme.newBackground
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    lateinit var navController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        setContent {
            Tudijit2Theme {

                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = newBackground
                ) {
                    val imeState = rememberImeState()
                    val scrollState = rememberScrollState()

                    LaunchedEffect(key1 = imeState.value) {
                        if (imeState.value){
                            scrollState.animateScrollTo(scrollState.maxValue, tween(300))
                        }
                    }
                    Column(
                        modifier = Modifier
                            .fillMaxSize()

                    ) {


                        navController = rememberNavController()
                        SetupNavGraph(navController = navController)
                    }
                }
            }
        }
    }

}



