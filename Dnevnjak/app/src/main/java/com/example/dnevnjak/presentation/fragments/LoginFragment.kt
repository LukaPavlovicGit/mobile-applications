package com.example.dnevnjak.presentation.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.dnevnjak.R
import com.example.dnevnjak.presentation.composable.LoginPage
import com.example.dnevnjak.presentation.viewModels.LoginVIewModel
import com.example.dnevnjak.utilities.Constants
import com.example.dnevnjak.utilities.Constants.Companion.EMAIL_KEY
import com.example.dnevnjak.utilities.Constants.Companion.SHARED_PREFERENCES_PATH
import com.example.dnevnjak.utilities.Constants.Companion.USERNAME_KEY
import java.io.InputStream
import java.nio.charset.Charset

class LoginFragment: Fragment() {

    private val viewModel: LoginVIewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return ComposeView(requireContext()).apply {
            setContent {
                LoginPage(viewModel = viewModel, onClick = { checkCredentials() })
            }
        }
    }

    private fun checkCredentials(){

        val ins: InputStream = resources.openRawResource(R.raw.password)
        val storedPassword = ins.readBytes().toString(charset = Charset.defaultCharset())

        if(viewModel.password.value != storedPassword){
            Toast.makeText(activity, "Invalid username or password", Toast.LENGTH_SHORT).show()
            return
        }

        Toast.makeText(activity, "Success!", Toast.LENGTH_SHORT).show()

        val sharedPreferences = requireActivity().getSharedPreferences(SHARED_PREFERENCES_PATH, Context.MODE_PRIVATE)
        sharedPreferences.edit().apply {
            putString(EMAIL_KEY,  viewModel.email.value)
            putString(USERNAME_KEY, viewModel.username.value)
            apply()
        }

        val transaction = activity?.supportFragmentManager?.beginTransaction()
        transaction?.replace(R.id.activity_main_fragment_container, MainFragment(),Constants.MAIN_FRAGMENT_TAG)
        transaction?.commit()
    }
}

