package com.example.tudijit2.InOutModels

data class SignInState(
    val isLoading: Boolean = false,
    val isSuccess: String? = "",
    val isError: String? = "",
    val isLogin:Boolean=false
)