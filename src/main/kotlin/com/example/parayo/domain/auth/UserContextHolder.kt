package com.example.parayo.domain.auth

import com.example.parayo.domain.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserContextHolder @Autowired constructor(
    private val userRepository: UserRepository
) {
    private val userHolder = ThreadLocal.withInitial { UserHolder() }

    val id: Long = userHolder.get().id
    val email: String = userHolder.get().email
    val name: String = userHolder.get().name

    fun set(email: String) = userRepository.findByEmail(email)?.let {
        userHolder.set(UserHolder(it.id, it.email, it.name))
    }

    fun clear() = userHolder.remove()
}