package net.MobileApp.Ingredo

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.google.firebase.ml.vision.text.FirebaseVisionText
import com.spark.submitbutton.SubmitButton
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView


class text_main : AppCompatActivity() {

    private val GALLERY_REQUEST_CODE=1234;
    private val TAG : String = "AppDebug"
    lateinit var imageView: ImageView
    lateinit var editText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_text_main)

        imageView = findViewById(R.id.imageView)
        editText = findViewById(R.id.editText)

    }
    fun selectImage(v: View) {
        val intent = Intent()
        intent.type = "image/*"
        val mimeTypes = arrayOf("image/jpeg","image/png", "image/jpg");
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(intent,GALLERY_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {

            GALLERY_REQUEST_CODE -> {
                if (resultCode == Activity.RESULT_OK) {
                    data?.data?.let { uri ->
                        launchImageCrop(uri)
                    }
                } else {
                    Log.e(TAG, "Image selection error")
                }
            }

            CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE -> {
                val result = CropImage.getActivityResult(data)
                if (resultCode == Activity.RESULT_OK) {
                    result.uri?.let {
                        setImage(it)

                    }
                }
            }
        }
    }
    private fun launchImageCrop(uri: Uri){
        CropImage.activity(uri)
                .setGuidelines(CropImageView.Guidelines.ON)
                .setAspectRatio(1920,1080)
                .setCropShape(CropImageView.CropShape.RECTANGLE)
                .start(this)
    }
    private fun setImage(uri: Uri) {
        Glide.with(this)
                .load(uri)
                .into(imageView)
    }
    fun startRecognizing(v: View) {
        if (imageView.drawable != null) {
            editText.setText("")
            v.isEnabled = false
            val bitmap = (imageView.drawable as BitmapDrawable).bitmap
            val image = FirebaseVisionImage.fromBitmap(bitmap)
            val detector = FirebaseVision.getInstance().onDeviceTextRecognizer

            detector.processImage(image)
                    .addOnSuccessListener { firebaseVisionText ->
                        v.isEnabled = true
                        processResultText(firebaseVisionText)
                    }
                    .addOnFailureListener {
                        v.isEnabled = true
                        editText.setText("Failed")
                    }
        } else {
            Toast.makeText(this, "Select an Image First", Toast.LENGTH_LONG).show()
        }
    }
    private fun processResultText(resultText: FirebaseVisionText) {
        if (resultText.textBlocks.size == 0) {
            editText.setText("No Text Found")
            return
        }
        for (block in resultText.textBlocks) {
            val blockText = block.text
            editText.append(blockText + "\n")
            val button: SubmitButton = findViewById(R.id.button) as SubmitButton

            button.setOnClickListener(){

                val intent= Intent(this@text_main,text_listing::class.java)
                intent.putExtra("Text", editText.text.toString())
                Toast.makeText(applicationContext,"this is toast message", Toast.LENGTH_SHORT).show()

                startActivity(intent)
            }
        }
    }
}
