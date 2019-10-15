package com.wa82bj.ads_mvvm_github.ui.auth

import android.app.Application
import android.view.View
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.wa82bj.ads_mvvm_github.data.repository.user.UserRepository
import com.wa82bj.ads_mvvm_github.ui.common.toResult
import com.wa82bj.ads_mvvm_github.util.ext.toLiveData
import com.wa82bj.ads_mvvm_github.util.rx.SchedulerProvider
import com.wa82bj.ads_mvvm_github.util.toast
import javax.inject.Inject

class LoginViewModel  @Inject constructor(
    private val repository: UserRepository,
    private val application: Application,
    private val schedulerProvider: SchedulerProvider
) : ViewModel(), LifecycleObserver {

    var  userName : String ? = null
    var  password : String ? = null
    var  authListener : AuthListener ? = null

    private val page = MutableLiveData<Int>()


    private val _user = Transformations
        .switchMap(page) { page ->
            return@switchMap repository.getUserResponse(userName!! ,password!!)
                .toResult(schedulerProvider)
                .toLiveData()
        }
    val user = _user


    fun onClickLoginBtn (view : View) {

        if (userName.isNullOrEmpty() || password.isNullOrEmpty()){
            application.applicationContext.toast("User Name Or Password not Correct!")
            return
        }

        val loginResponse = repository.getUserResponse(userName!! ,password!!)

        val  res = user.value
        //authListener?.onSuccess(loginResponse).toString()
        application.applicationContext.toast(""+res)


    }

    fun onClickRegisterBtn (view : View) {

    }
}
