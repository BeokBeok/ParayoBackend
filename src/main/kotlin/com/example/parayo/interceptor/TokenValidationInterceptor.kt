package com.example.parayo.interceptor

import com.example.parayo.domain.auth.JWTUtil
import com.example.parayo.domain.auth.UserContextHolder
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor
import org.springframework.web.servlet.ModelAndView
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class TokenValidationInterceptor @Autowired constructor(
    private val userContextHolder: UserContextHolder
) : HandlerInterceptor {

    private val logger = LoggerFactory.getLogger(this::class.java)

    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        val authHeader = request.getHeader(AUTHORIZATION)

        if (authHeader.isNullOrBlank()) {
            val methodToServletPath = request.method to request.servletPath
            if (!DEFAULT_ALLOWED_API_URLS.contains(methodToServletPath)) {
                response.sendError(401)
                return false
            }
            return true
        } else {
            val grantType = request.getParameter("grant_type")
            val token = extractToken(authHeader)
            return handleToken(grantType, token, response)
        }
    }

    override fun postHandle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any,
        modelAndView: ModelAndView?
    ) = userContextHolder.clear()

    private fun handleToken(grantType: String, token: String, response: HttpServletResponse) = try {
        val jwt = when (grantType) {
            GRANT_TYPE_REFRESH -> JWTUtil.verifyRefresh(token)
            else -> JWTUtil.verify(token)
        }
        val email = JWTUtil.extractEmail(jwt)
        userContextHolder.set(email)
        true
    } catch (e: Exception) {
        logger.error("토큰 검증 실패. token = $token", e)
        response.sendError(401)
        false
    }

    private fun extractToken(token: String) =
        token.replace(BEARER, "").trim()

    companion object {
        private const val AUTHORIZATION = "Authorization"
        private const val BEARER = "Bearer"
        private const val GRANT_TYPE_REFRESH = "refresh_token"

        // TODO Spring Security 를 사용하여 좀 더 세련된 방법으로 설정
        private val DEFAULT_ALLOWED_API_URLS = listOf(
            "POST" to "/api/v1/signin",
            "POST" to "/api/v1/users"
        )
    }
}
