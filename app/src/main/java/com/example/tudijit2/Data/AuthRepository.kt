package com.example.tudijit2.Data

import android.content.res.Resources
import com.example.tudijit2.util.Resource
import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.flow.Flow


interface AuthRepository {
    fun loginUser(email:String,passWord:String) : Flow<Resource<AuthResult>>
    fun registerUser(email:String,passWord:String) : Flow<Resource<AuthResult>>
}