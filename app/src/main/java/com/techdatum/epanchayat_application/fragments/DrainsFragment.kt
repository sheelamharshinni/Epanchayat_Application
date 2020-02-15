package com.techdatum.epanchayat_application.fragments


import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.annotation.RequiresApi

import com.techdatum.epanchayat_application.R
import kotlinx.android.synthetic.main.fragment_drains.view.*
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import android.util.Base64
import java.time.format.DateTimeFormatter
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class DrainsFragment : Fragment() {
    var VIewview: View? = null
    private val CAMERA_REQUEST = 1888
    private val CAMERA_REQUEST1 = 188
    private val MY_CAMERA_PERMISSION_CODE = 100
    var TIme_road1: String? = null
    var TIme_road2: String? = null
    var Location: String? = null
    var Location2: String? = null
    var Image1: String? = null
    var Image2: String? = null
    var selectedImage: Uri? = null

    companion object {
        fun newInstance() = DrainsFragment()
    }


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        VIewview = inflater.inflate(R.layout.fragment_drains, container, false);
        onClikcs()

        return VIewview!!;
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun onClikcs() {


        VIewview!!.btn_submit.setOnClickListener {
            stringConvertion()
            stringNotNull()
            if (!Location.equals("")) {
                if (Image1 != null) {
                    if (!Location2.equals("")) {
                        if (Image2 != null) {
                            Toast.makeText(
                                context!!,
                                "Succes",
                                Toast.LENGTH_SHORT
                            ).show()

                        } else {
                            Toast.makeText(
                                context!!,
                                "Image2 Cannot be Empty",
                                Toast.LENGTH_SHORT
                            ).show()

                        }
                    } else {
                        Toast.makeText(
                            context!!,
                            "Location2 Cannot be Empty",
                            Toast.LENGTH_SHORT
                        ).show()

                    }
                } else {
                    Toast.makeText(
                        context!!,
                        "Image1 Cannot be Empty",
                        Toast.LENGTH_SHORT
                    ).show()

                }
            } else {
                Toast.makeText(
                    context!!,
                    "Location1 Cannot be Empty",
                    Toast.LENGTH_SHORT
                ).show()

            }

        }
        VIewview!!.iv_pickimage_roadnumber1.setOnClickListener {

            println("Current Date and Time is: $TIme_road1")
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val current = LocalDateTime.now()
                val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy. hh:mm:ss a")

                TIme_road1 = current.format(formatter)
                Log.d("answer", TIme_road1)
            } else {
                val date = Date()
                val formatter = SimpleDateFormat("dd mm yyyy hh:mm:ss a")

                TIme_road1 = formatter.format(date)
                Log.d("answer", TIme_road1)
            }

            if (context!!.checkSelfPermission(Manifest.permission.CAMERA) !== PackageManager.PERMISSION_GRANTED) {
                requestPermissions(
                    arrayOf<String>(Manifest.permission.CAMERA),
                    MY_CAMERA_PERMISSION_CODE
                )
            } else {
                val cameraIntent = Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(cameraIntent, CAMERA_REQUEST)
            }
        }


        VIewview!!.iv_pickimage_roadnumber2.setOnClickListener {

            println("Current Date and Time is: $TIme_road2")
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val current = LocalDateTime.now()
                val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy. hh:mm:ss a")

                TIme_road2 = current.format(formatter)
                Log.d("answer", TIme_road2)
            } else {
                val date = Date()
                val formatter = SimpleDateFormat("dd mm yyyy hh:mm:ss a")

                TIme_road2 = formatter.format(date)
                Log.d("answer", TIme_road2)
            }

            if (context!!.checkSelfPermission(Manifest.permission.CAMERA) !== PackageManager.PERMISSION_GRANTED) {
                requestPermissions(
                    arrayOf<String>(Manifest.permission.CAMERA),
                    MY_CAMERA_PERMISSION_CODE
                )
            } else {
                val cameraIntent = Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(cameraIntent, CAMERA_REQUEST1)
            }
        }

    }

    override fun onRequestPermissionsResult(requestCode: Int, @NonNull permissions: Array<String>, @NonNull grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == MY_CAMERA_PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(context, "camera permission granted", Toast.LENGTH_LONG).show()
                val cameraIntent = Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(cameraIntent, CAMERA_REQUEST)
            } else {
                Toast.makeText(context, "camera permission denied", Toast.LENGTH_LONG).show()
            }
        }
    }

    fun stringConvertion() {
        Location = VIewview!!.tv_location1.text.toString()

        Location2 = VIewview!!.tv_location2.text.toString()

    }

    fun stringNotNull() {

        if (Location != null) {

        } else {
            Location = ""
        }
        if (Location2 != null) {

        } else {
            Location2 = ""
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            val photo1 = data!!.getExtras()!!.get("data") as Bitmap
            VIewview!!.iv_pickimage_roadnumber1.setImageBitmap(photo1)
            VIewview!!.tv_pickimage_time1.setText(TIme_road1)
            val outputStream = ByteArrayOutputStream()
            photo1!!.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
            val byteArray = outputStream.toByteArray()
            Image1 = Base64.encodeToString(byteArray, Base64.DEFAULT)
            //Log.e("STRING!PATH",Image1)
        }
        if (requestCode == CAMERA_REQUEST1 && resultCode == Activity.RESULT_OK) {


            val photo = data!!.getExtras()!!.get("data") as Bitmap
            try {


                VIewview!!.iv_pickimage_roadnumber2.setImageBitmap(photo)
                VIewview!!.tv_pickimage_time2.setText(TIme_road2)

            } catch (e: IOException) {
                e.printStackTrace();

            }
        }
    }

}

