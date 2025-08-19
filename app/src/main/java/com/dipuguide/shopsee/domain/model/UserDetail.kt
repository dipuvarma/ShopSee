package com.dipuguide.shopsee.domain.model

data class UserDetail(
    val userId: String? = null,
    val name: String = "",
    val email: String = "",
    val phoneNumber: String? = null,
    val profileImage: String? = null,
    val createAt: Long? = null,
    val addresses: List<Address> = emptyList(),
)

data class Address(
    val id: String = "",
    val name: String = "",
    val phone: String = "",
    val addressLine1: String = "",
    val addressLine2: String = "",
    val city: String = "",
    val state: String = "",
    val pincode: String = "",
)






