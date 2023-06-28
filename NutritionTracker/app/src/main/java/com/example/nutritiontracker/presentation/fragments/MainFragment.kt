package com.example.nutritiontracker.presentation.fragments

import android.Manifest
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.ComposeView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.nutritiontracker.events.MainEvent
import com.example.nutritiontracker.presentation.composable.MainScreen
import com.example.nutritiontracker.presentation.composable.cammon.CameraView
import com.example.nutritiontracker.viewModel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@AndroidEntryPoint
class MainFragment: Fragment() {

    private val viewModel: MainViewModel by viewModels()

    private lateinit var outputDirectory: File
    private lateinit var cameraExecutor: ExecutorService
    private var shouldShowCamera: MutableState<Boolean> = mutableStateOf(false)

    private val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        if (isGranted) {
            Log.i("TAG", "Permission granted")
            shouldShowCamera.value = true
        } else {
            Log.i("TAG", "Permission denied")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        outputDirectory = getOutputDirectory()
        cameraExecutor = Executors.newSingleThreadExecutor()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            setContent {
                MainScreen(
                    onUrlClicked = { onUrlClicked(url = it) },
                    openCamera = { requestCameraPermission() },
                    sendEmail = { sendEmail() }
                )

                if (shouldShowCamera.value) {
                    CameraView(
                        outputDirectory = outputDirectory,
                        executor = cameraExecutor,
                        onImageCaptured = ::handleImageCapture,
                        onError = { Log.e("TAG", "View error:", it) }
                    )
                }
            }
        }
    }

    private fun onUrlClicked(url :String?){
        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse(url)
        startActivity(i)
    }

    private fun sendEmail(){

        Log.e("EMAIL", viewModel.createPlanDataState.value.email)

        val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:")
            putExtra(Intent.EXTRA_EMAIL, arrayOf(viewModel.createPlanDataState.value.email))
            putExtra(Intent.EXTRA_SUBJECT, "Meal plan")
            putExtra(Intent.EXTRA_TEXT, viewModel.createPlanDataState.value.emailBody())
        }
        Log.e("EMAIL", viewModel.createPlanDataState.value.emailBody().substring(1,10))
        try {
            startActivity(emailIntent)
        } catch (ex: ActivityNotFoundException) {
            Log.e("EMAIL", "EXCEPTION")
        }
//        if (emailIntent.resolveActivity(requireContext().packageManager) != null) {
//            Log.e("EMAIL", "YES")
//            startActivity(emailIntent)
//        } else {
//            Log.e("EMAIL", "NO")
//        }

    }

    private fun requestCameraPermission() {
        when {
            ContextCompat.checkSelfPermission(requireContext(),Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED -> {
                Log.i("TAG", "Permission previously granted")
                shouldShowCamera.value = true
            }
            ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(), Manifest.permission.CAMERA) -> {
                Log.i("TAG", "Show camera permissions dialog")
            }
            else -> requestPermissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }

    private fun handleImageCapture(uri: Uri) {
        shouldShowCamera.value = false
        viewModel.onEvent(MainEvent.SetMealPictureUri(uri.toString()))
    }

    private fun getOutputDirectory(): File {
        val mediaDir = requireActivity().externalMediaDirs.firstOrNull()?.let {
            File(it, "myPictures").apply { mkdirs() }
        }

        return if (mediaDir != null && mediaDir.exists()) mediaDir else  requireActivity().filesDir
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
    }

}