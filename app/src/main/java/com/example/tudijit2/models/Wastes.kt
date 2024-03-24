package com.example.tudijit2.models

import com.google.firebase.Timestamp
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


data class Wastes(
    var schoolID:String?="",
    var dataTitle:String="",
    var paperCount: String="",
    var metalCount: String="",
    var glassCount: String="",
    var plasticCount: String="",
    var month:String="",
    var year:String="",
    var timestamp: Timestamp = Timestamp.now()

    )


