package com.example.findguard.repository

interface UserRepo {
    fun login(email: String, password: String, callback: (Boolean, String) -> Unit)
    fun signup(email: String, password: String, callback: (Boolean, String) -> Unit)
    fun forgetPassword(email: String, callback: (Boolean, String) -> Unit)
}