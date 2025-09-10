package com.sourashis.meesho.domain.models

data class UserAddress(
     val firstName : String = "",
     val lastName : String = "",
     val address : String = "",
     val city : String = "",
     val sate : String = "",
     val pinCode : String = "",
     val country : String = "",
     val phoneNumber : String = "",
)
