package com.example.parayo.domain

import com.example.parayo.domain.jpa.BaseEntity
import javax.persistence.Entity

@Entity(name = "user")
class User(
    val email: String = "",
    val password: String = "",
    val name: String = ""
) : BaseEntity()