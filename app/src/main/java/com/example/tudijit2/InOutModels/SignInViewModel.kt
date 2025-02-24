package com.example.tudijit2.InOutModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tudijit2.Data.AuthRepository
import com.example.tudijit2.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

import javax.inject.Inject


@HiltViewModel
class SignInViewModel @Inject constructor(
    private val repository:AuthRepository,
    private val application: Application
) : AndroidViewModel(application = application) {

    private var _signInState= Channel<SignInState>()
    val signInState=_signInState.receiveAsFlow()


    fun loginUser(email:String,password:String)=viewModelScope.launch {
        repository.loginUser(email,password).collectLatest{result->
            when(result){
                is  Resource.Success ->{
                    _signInState.send(SignInState(isSuccess = "Giriş Başarılı", isLogin = true))
                }
                is Resource.Loading->{
                    _signInState.send(SignInState(isLoading = true))
                }
                is Resource.Error->{
                    _signInState.send(SignInState(isSuccess =result.message))

                }
            }
        }

    }

}