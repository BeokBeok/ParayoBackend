package com.example.parayo.controller

import com.example.parayo.common.ApiResponse
import com.example.parayo.domain.auth.*
import com.example.parayo.interceptor.TokenValidationInterceptor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1")
class AuthController @Autowired constructor(
    private val signUpService: SignUpService,
    private val signInService: SignInService,
    private val userContextHolder: UserContextHolder
) {

    @PostMapping("/users")
    fun signUp(@RequestBody signUpRequest: SignUpRequest) =
        ApiResponse.ok(signUpService.signUp(signUpRequest))

    @PostMapping("/signin")
    fun signIn(@RequestBody signInRequest: SignInRequest) =
        ApiResponse.ok(signInService.signIn(signInRequest))

    @PostMapping("/refresh_token")
    fun refreshToken(@RequestParam("grant_type") grantType: String): ApiResponse {
        if (grantType != TokenValidationInterceptor.GRANT_TYPE_REFRESH) {
            throw IllegalArgumentException("Not grant_type")
        }

        return ApiResponse.ok(JWTUtil.createToken(userContextHolder.email))
    }
}