package com.example.myapplication

import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapplication.databinding.ActivityMainBinding
import com.google.firebase.firestore.auth.User
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanIntentResult
import com.journeyapps.barcodescanner.ScanOptions
import com.journeyapps.barcodescanner.camera.CameraSettings
import java.text.SimpleDateFormat
import java.time.LocalTime
import java.util.Date
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ServerValue

class MainActivity : AppCompatActivity() {
    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()){
            isGranted: Boolean ->
            if (isGranted){
                showCamera()
            }else{

            }
        }
    private val scanLauncher =
        registerForActivityResult(ScanContract()){
            result: ScanIntentResult ->
            run {
                if (result.contents == null) {
                    Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT).show()
                } else {
                    setResult(result.contents)
                }
            }
        }
    private lateinit var binding: ActivityMainBinding

    private fun setResult(string: String){
        binding.textResult.text = string
    }

    private fun showCamera() {
        val options = ScanOptions()
        val camerasetting = CameraSettings()
        options.setDesiredBarcodeFormats(ScanOptions.QR_CODE)
        options.setPrompt("Scan QR Code")
        options.setCameraId(0)
        options.setBeepEnabled(false)
        options.setBarcodeImageEnabled(true)
        options.setOrientationLocked(false)
        camerasetting.isAutoFocusEnabled = true

        scanLauncher.launch(options)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initBinding()
        initViews()
        status()
        val db = Firebase.firestore
        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
        val currentDate = sdf.format(Date())
        val userInfo = binding.textResult.text.toString()
        val infoparse = userInfo.split("/")
        val userId = infoparse[0]
        val userName = infoparse[1]


        binding.SentButton.setOnClickListener {
            db.collection("Users").add(
            com.example.myapplication.`object`.User(
            id = userId,
            Status = userName,
            Waktu = currentDate.toString(),
            )
            )
        }
        }

    private fun initViews() {
        binding.fab.setOnClickListener{
            checkPermissionCamera(this)
        }
    }

    private fun checkPermissionCamera(context: Context) {
        if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){
            showCamera()
        }else if (shouldShowRequestPermissionRationale(android.Manifest.permission.CAMERA)){
            Toast.makeText(context, "Camera Permission Required", Toast.LENGTH_SHORT).show()
        }else{
            requestPermissionLauncher.launch(android.Manifest.permission.CAMERA)
        }
    }

    private fun initBinding() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
    private fun status(){
        binding.status.text = "Masuk"
        binding.switcher.setOnCheckedChangeListener{_,ischecked ->
            if (ischecked){
                binding.status.text = "Pulang"
                Toast.makeText(this, "Switch is On", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this, "Switch is Off", Toast.LENGTH_SHORT).show()
                binding.status.text = "Masuk"
            }
        }

    }
}

