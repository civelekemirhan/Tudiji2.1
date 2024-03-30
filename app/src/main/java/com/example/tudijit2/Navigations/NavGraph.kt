package com.example.tudijit2.Navigations

import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.tudijit2.Pages.DetailPage
import com.example.tudijit2.Pages.DataInputPage
import com.example.tudijit2.Pages.ForgetPass
import com.example.tudijit2.Pages.LoginPage
import com.example.tudijit2.Pages.RegisterPage
import com.example.tudijit2.Pages.SplashScreen
import com.example.tudijit2.Pages.VerificationPage
import com.example.tudijit2.dialog.DialogViewModel
import com.example.tudijit2.models.Wastes
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel

@Composable
fun SetupNavGraph(navController: NavHostController) {


    NavHost(navController = navController, startDestination = Screen.Splash.route) {
        composable(route = Screen.Splash.route) {
            SplashScreen(navController = navController)
        }
        composable(route = Screen.LoginPage.route) {
            LoginPage(navController)
        }
        composable(route = Screen.RegisterPage.route) {
            RegisterPage(navController)
        }
        composable(route = Screen.DetailPage.route) {
            DetailPage(navController)
        }
        composable(route = Screen.DataInputPage.route) {
            DataInputPage(navController)
        }
        composable(route = Screen.ForgetPass.route) {
            ForgetPass(navController)
        }
        composable(route = Screen.VerificationPage.route) {
            VerificationPage(navController)
        }
    }


}