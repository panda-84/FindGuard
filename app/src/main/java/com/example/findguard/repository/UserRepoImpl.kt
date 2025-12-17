package com.example.findguard.repository

class UserRepoImpl : UserRepo  {
    override fun login(
        email: String,
        password: String,
        callback: (Boolean, String) -> Unit
    ) {
        TODO("Not yet implemented")
    }

    override fun signup(
        email: String,
        password: String,
        callback: (Boolean, String) -> Unit
    ) {
        TODO("Not yet implemented")
    }

    override fun forgetPassword(
        email: String,
        callback: (Boolean, String) -> Unit
    ) {
        TODO("Not yet implemented")
    }

}