package com.example.findguard.model

data class UserModel(
    val id: String = "",
    val fullName: String = "",
    val email: String = "",
    val password: String = "",

) {
    fun toMap(): Map<String, Any> {
        return mapOf(
            "id" to id,
            "fullName" to fullName,
            "email" to email,
            "password" to password)
    }
}