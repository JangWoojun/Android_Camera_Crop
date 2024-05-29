package com.woojun.androidcameracrop

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.woojun.androidcameracrop.databinding.ActivityMainBinding
import com.yalantis.ucrop.UCrop
import java.io.File





class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding


    private var pickMedia = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        val destinationFileName = "SAMPLE_CROPPED_IMAGE_NAME.png"

        val option = UCrop.Options().apply {
            val pinkColor = ContextCompat.getColor(this@MainActivity, R.color.pink)
            val whiteColor = ContextCompat.getColor(this@MainActivity, R.color.white)

            setToolbarColor(pinkColor)
            setStatusBarColor(pinkColor)
            setToolbarWidgetColor(pinkColor)

            setToolbarWidgetColor(whiteColor)
        }

        UCrop.of(uri!!, Uri.fromFile(File(cacheDir, destinationFileName)))
            .withAspectRatio(1f, 1f)
            .withMaxResultSize(400, 400)
            .withOptions(option)
            .start(this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == UCrop.REQUEST_CROP) {
            val resultUri = UCrop.getOutput(data!!)
            Toast.makeText(this, resultUri.toString(), Toast.LENGTH_SHORT).show()
        } else if (resultCode == UCrop.RESULT_ERROR) {
            val cropError = UCrop.getError(data!!)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }



}