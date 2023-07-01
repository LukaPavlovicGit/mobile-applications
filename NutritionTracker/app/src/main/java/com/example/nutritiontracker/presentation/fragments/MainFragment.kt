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
import com.example.nutritiontracker.events.EventBusEvent
import com.example.nutritiontracker.presentation.composable.MainScreen
import com.example.nutritiontracker.presentation.composable.cammon.CameraView
import com.example.nutritiontracker.viewModel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
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
                MainScreen()
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

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }


    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
    }

    private fun cameraRequest(){
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
        EventBus.getDefault().postSticky(EventBusEvent.MealImageUrl(uri.toString()))
        shouldShowCamera.value = false
    }

    private fun getOutputDirectory(): File {
        val mediaDir = requireActivity().externalMediaDirs.firstOrNull()?.let {
            File(it, "myPictures").apply { mkdirs() }
        }

        return if (mediaDir != null && mediaDir.exists()) mediaDir else requireActivity().filesDir
    }

    private fun openUrl(url: String){
        if(url.isEmpty()){
            Toast.makeText(requireContext(), "Not available", Toast.LENGTH_SHORT).show()
            return
        }
        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse(url)
        startActivity(i)
    }

    private fun sendEmail(receiver: String, subject: String, body: String){

        val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:")
            putExtra(Intent.EXTRA_EMAIL, arrayOf(receiver))
            putExtra(Intent.EXTRA_SUBJECT, subject)
            putExtra(Intent.EXTRA_TEXT, body)
        }
        try {
            startActivity(emailIntent)
            Toast.makeText(requireContext(), "Email sent", Toast.LENGTH_SHORT).show()
        } catch (ex: ActivityNotFoundException) {
            Toast.makeText(requireContext(), "Email in not sent", Toast.LENGTH_SHORT).show()
        }

    }


    @Subscribe(sticky = true)
    fun handleEvent(event: EventBusEvent) {
        when(event){
            EventBusEvent.CameraRequest -> cameraRequest()
            is EventBusEvent.MessageToast -> Toast.makeText(requireContext(), event.message, Toast.LENGTH_SHORT).show()
            is EventBusEvent.SendEmail -> sendEmail(event.receiver, event.subject, event.body)
            is EventBusEvent.OpenUrl -> openUrl(event.url)
            else -> {  }
        }
        // prevent event from re-delivering, like when leaving and coming back to app
        EventBus.getDefault().removeStickyEvent(event);
    }



}