package com.wa82bj.ads_mvvm_github.ui.auth

import com.wa82bj.ads_mvvm_github.data.api.response.user.UserResponse

interface AuthListener {

    fun onStarted ()

    fun onSuccess(userResponse: UserResponse)

}