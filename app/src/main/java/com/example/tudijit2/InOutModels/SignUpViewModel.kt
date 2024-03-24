package com.example.tudijit2.InOutModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tudijit2.Data.AuthRepository
import com.example.tudijit2.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val repository: AuthRepository,
    private val application: Application
) : AndroidViewModel(application = application) {



    private var _signUpState = Channel<SignUpState>()
    val signUpState=_signUpState.receiveAsFlow()

    fun registerUser(email:String,password:String)=viewModelScope.launch {
        repository.registerUser(email,password).collectLatest{result->
            when(result){
                is  Resource.Success ->{
                    _signUpState.send ( SignUpState(isSuccess = "Kayıt Başarılı!", isRegister = true) )
                }
                is Resource.Loading->{
                    _signUpState.send (SignUpState(isLoading = true) )
                }
                is Resource.Error->{
                    _signUpState.send ( SignUpState(isError = result.message) )
                }
            }
        }

    }

}