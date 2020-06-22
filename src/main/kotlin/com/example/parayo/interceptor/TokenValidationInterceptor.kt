package com.example.parayo.interceptor

import com.example.parayo.domain.auth.UserContextHolder
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor

@Component
class TokenValidationInterceptor @Autowired constructor(
    private val userContextHolder: UserContextHolder
) : HandlerInterceptor {

    private val logger = LoggerFactory.getLogger(this::class.java)

    companion object {
        private const val Authorization = "Authorization"
        private const val BEARER = "Bearer"
        private const val GRANT_TYPE = "grant_type"
        private const val GRANT_TYPE_REFRESH = "refresh_token"

        // TODO Spring Security 를 사용하여 좀 더 세련된 방법으로 설정
        private val DEFAULT_ALLOWED_API_URLS = listOf(
            "POST" to "/api/v1/signin",
            "POST" to "/api/v1/users"
        )
    }
}
