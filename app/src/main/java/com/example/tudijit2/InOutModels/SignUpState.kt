package com.example.tudijit2.InOutModels

data class SignUpState(
    val isLoading: Boolean = false,
    val isSuccess: String? = "",
    val isError: String? = "",
    val isRegister:Boolean=false

)