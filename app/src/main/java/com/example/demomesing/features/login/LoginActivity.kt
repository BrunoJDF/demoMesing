package com.example.demomesing.features.login

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.Window
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.demomesing.R
import com.example.demomesing.base.BaseActivity
import com.example.demomesing.data.session.ShPreference
import com.example.demomesing.di.Injection
import com.example.demomesing.features.home.HomeActivity
import com.example.demomesing.features.home.ui.add.AddActivity
import com.example.demomesing.model.ResponseData
import com.example.demomesing.model.User
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity(), View.OnClickListener {

    override fun getLayout(): Int {
        return R.layout.activity_login
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_ingresar -> {
                signInService()
            }
            R.id.btn_add_usu -> {
                registerUser()
            }
        }
    }

    private fun registerUser() {
        val intent = Intent(this@LoginActivity, AddActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initApp()
        btn_ingresar.setOnClickListener(this)
        btn_add_usu.setOnClickListener(this)
    }

    private fun initApp() {
        progressBar = progress_bar
        viewModel = ViewModelProviders.of(
            this,
            LoginViewModelFactory(
                Injection.getLogin(),
                ShPreference(
                    getSharedPreferences(
                        ShPreference.PREFERENCE_NAME,
                        Context.MODE_PRIVATE
                    ), this
                )
            )
        ).get(LoginViewModel::class.java)
        viewModel.responseBody.observe(this, response)
        viewModel.message.observe(this, message)
        //viewModel.error.observe(this, error)
    }
    private val error = Observer<String> {
        toast(it)
    }
    private val message = Observer<ResponseData> {
        loader()
        toast(it.status+" "+it.message)
    }

    private val response = Observer<User> {
        sendHome()
    }

    private fun sendHome() {
        loader()
        val intent = Intent(this@LoginActivity, HomeActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }


    private fun fieldsEmpty(): Boolean {
        var validate = true

        if (et_user.text.isNullOrEmpty()) {
            et_user.error = "Requerido"
            validate = false
            toast("Ingrese usuario")
        } else {
            et_user.error = null
        }

        if (et_password.text.isNullOrEmpty()) {
            et_password.error = "Requerido"
            validate = false
            toast("Ingrese contraseña")
        } else {
            et_password.error = null
        }
        return validate
    }


    private fun signInService() {
        showProgressBar()
        if (!fieldsEmpty()) {
            loader()
            return
        }
        Log.i("Info", "SignInService")
        viewModel.signInService(et_user.text.toString(), et_password.text.toString())
    }



    private fun loader() {
        Handler().postDelayed({ hideProgressBarr() }, 500)
    }

    private fun transition(context: Context) {
        val pd = Dialog(context)
        pd.requestWindowFeature(Window.FEATURE_NO_TITLE)
        pd.setContentView(R.layout.activity_login)
        pd.progress_bar.visibility = View.VISIBLE
        pd.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        pd.setCancelable(true)
        pd.setCanceledOnTouchOutside(true)
        pd.show()
    }

}
