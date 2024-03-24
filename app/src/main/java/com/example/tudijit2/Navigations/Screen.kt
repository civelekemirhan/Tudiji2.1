package com.example.tudijit2.Navigations


const val DETAIL_ARGUMENT_KEY ="schoolName"

sealed class Screen(val route: String) {

    object Splash : Screen(route = "splash_screen")
    object LoginPage : Screen(route = "login_screen")
    object RegisterPage : Screen(route = "register_screen")
    object DetailPage : Screen(route = "detail_screen")
    object DataInputPage: Screen(route = "data_screen")
    object ForgetPass: Screen(route="forget_pass")
    object VerificationPage: Screen(route="verification_page")
    object NewPassPage: Screen(route="newpass_page")
}
