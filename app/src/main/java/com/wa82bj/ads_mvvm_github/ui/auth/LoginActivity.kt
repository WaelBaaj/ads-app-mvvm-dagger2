package com.wa82bj.ads_mvvm_github.ui.auth

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.wa82bj.ads_mvvm_github.R
import com.wa82bj.ads_mvvm_github.databinding.ActivityLoginBinding
import com.wa82bj.ads_mvvm_github.util.toast
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class LoginActivity : DaggerAppCompatActivity() {


    private val TAG = LoginActivity::class.java.name
    private lateinit var binding : ActivityLoginBinding

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: LoginViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(LoginViewModel::class.java)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        }
        binding.loginView = viewModel

        viewModel.user.observe(this, Observer {it.let {
            binding.txt.text = it.toString()
        }

        })
    }


}
