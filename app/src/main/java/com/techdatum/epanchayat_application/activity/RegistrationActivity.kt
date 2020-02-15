package com.techdatum.epanchayat_application.activity

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.provider.MediaStore
import android.telephony.TelephonyManager
import android.text.Editable
import android.text.TextWatcher
import android.util.Base64
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.stfalcon.smsverifycatcher.SmsVerifyCatcher
import com.techdatum.epanchayat_application.PopupWindowSpinnerAdapter
import com.techdatum.epanchayat_application.R
import com.techdatum.epanchayat_application.broadcast.SmsListener
import com.techdatum.epanchayat_application.broadcast.SmsReceiver
import com.techdatum.epanchayat_application.datamodelclasses.RoleMasterDataModelClass
import com.techdatum.epanchayat_application.datamodelclasses.UserDetailsDataModelClass
import com.techdatum.epanchayat_application.webservices.WebServiceClass
import kotlinx.android.synthetic.main.activity_registration.*
import kotlinx.android.synthetic.main.dialog_verification.*
import okhttp3.MediaType
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern

class RegistrationActivity : AppCompatActivity() {
    private val GALLERY = 1
    private val CAMERA = 2
    var profileLink = ""
    var RoleId: String? = null
    var districtId: String? = null
    var divisionid: String? = null
    var MandalId: String? = null
    var PanchayatId: String? = null
    var TitleId: String? = null
    var Name: String? = null
    var SurName: String? = null
    var GenderId: String? = null
    var DateofBirth: String? = null
    var EmailId: String? = null
    var IMEINumber: String? = null
    var ImagePath: String? = null
    var RolesListDataModel: RoleMasterDataModelClass? = null
    var UserDatadetails: UserDetailsDataModelClass? = null
    var Gender = arrayOf("Male", "Female", "Transgender")
    var Title = arrayOf("Mr.", "Mrs.")
    private var smsVerifyCatcher: SmsVerifyCatcher? = null
    var telephonymanager: TelephonyManager? = null
    var REQUEST_READ_PHONE_STATE = 110
    var password: String? = null
    var confirmpassword: String? = null
    var Username: String? = null
    var Email: String? = null
    var MobileNumber: String? = null
    var Surname: String? = null
    var LastName: String? = null
    var dob: String? = null
    var OTP: String? = null
    var otpAuto: String? = null
    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
        //requestMultiplePermissions()
//        gender()
        getMasterRoles()

        try {
            telephonymanager = getSystemService(TELEPHONY_SERVICE) as TelephonyManager
            IMEINumber = telephonymanager!!.getDeviceId()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        profile_image.setOnClickListener {
            selectImage()
        }
        im_dateOfBirth.setOnClickListener {
            clickDataPickerOFDOB()
        }
        lay_skip.setOnClickListener {
            registrationreportdialog()
        }
        btn_submit.setOnClickListener { registrationUser() }
    }

    private fun parseCode(message: String): String? {
        val p = Pattern.compile("\\b\\d{4}\\b")
        val m = p.matcher(message)
        var code: String? = ""
        while (m.find()) {
            code = m.group(0)
        }
        return code
    }


    fun getMasterRoles() {
        val reqobject = JSONObject()
        reqobject.put("ActionName", "Role")
        reqobject.put("Id", "")
        Log.e("RequestBody", "Request Body:- ${reqobject}")
        val mediaType = MediaType.parse("application/json");
        val requestBody = RequestBody.create(mediaType, reqobject.toString())
        val callback = WebServiceClass.callWebServices().postRolesMaster(requestBody)
        callback.enqueue(object : Callback<RoleMasterDataModelClass> {
            override fun onFailure(
                call: Call<RoleMasterDataModelClass>,
                t: Throwable
            ) {
                t.printStackTrace()
            }

            override fun onResponse(
                call: Call<RoleMasterDataModelClass>,
                response: Response<RoleMasterDataModelClass>
            ) {
                Log.e("URL", "URL:- ${response.raw().request().url()}")
                Log.e("RequestBody", "Request Body:- ${response.body()}")
                RolesListDataModel = response.body()
                try {
                    val data = RolesListDataModel!!.Data
                    if (data.isNotEmpty() && data.size != 0) {
                        val popupMenu = ListPopupWindow(this@RegistrationActivity!!)
                        popupMenu.anchorView = lay_role
                        val adapter = PopupWindowSpinnerAdapter(this@RegistrationActivity!!, data)
                        popupMenu.setAdapter(adapter)
                        popupMenu.setOnItemClickListener { parent, view, position, id ->
                            txt_rolename.text = data[position].Data
                            RoleId = data[position].Id
                            if (RoleId == 3.toString()) {
                                getMasterDistricts()
                                lay_text_divisiton.visibility = View.GONE
                                lay_et_division.visibility = View.GONE
                                lay_text_mandal.visibility = View.GONE
                                lay_et_mandal.visibility = View.GONE
                                lay_text_Gps.visibility = View.GONE
                                lay_et_gps.visibility = View.GONE
                                et_email.setText("")
                                txt_district.text = ""
                                et_mobilenumber.setText("")
                                txt_title.text = ""
                                et_surname.setText("")
                                et_lastname.setText("")
                                txt_gender.text = ""
                                txt_mandal.text = ""
                                txt_dateOfBirth.text = ""
//                                ImagePath = ""
//                                profile_image.setImageDrawable(getResources().getDrawable(R.drawable.ic_photo_camera_black_24dp));


                            } else if (RoleId == 4.toString()) {
                                getMasterDistricts()
                                lay_text_divisiton.visibility = View.VISIBLE
                                lay_et_division.visibility = View.VISIBLE
                                lay_text_mandal.visibility = View.GONE
                                lay_et_mandal.visibility = View.GONE
                                lay_text_Gps.visibility = View.GONE
                                lay_et_gps.visibility = View.GONE
                                et_email.setText("")
                                et_mobilenumber.setText("")
                                txt_title.text = ""
                                txt_district.text = ""
                                et_surname.setText("")
                                et_lastname.setText("")
                                txt_mandal.text = ""
                                txt_division.text = ""
                                txt_gender.text = ""
                                txt_dateOfBirth.text = ""
//                                ImagePath = ""
//                                profile_image.setImageDrawable(getResources().getDrawable(R.drawable.ic_photo_camera_black_24dp));
                            } else if (RoleId == 5.toString()) {
                                lay_text_divisiton.visibility = View.VISIBLE
                                lay_et_division.visibility = View.VISIBLE
                                lay_text_mandal.visibility = View.VISIBLE
                                lay_et_mandal.visibility = View.VISIBLE
                                lay_text_Gps.visibility = View.GONE
                                lay_et_gps.visibility = View.GONE
                                getMasterDistricts()
                                getMasterDivisional()
                                et_email.setText("")
                                et_mobilenumber.setText("")
                                txt_title.text = ""
                                txt_district.text = ""
                                txt_division.text = ""
                                txt_mandal.text = ""
                                et_surname.setText("")
                                et_lastname.setText("")
                                txt_gender.text = ""
                                txt_dateOfBirth.text = ""


                            } else if (RoleId == 6.toString()) {
                                lay_text_divisiton.visibility = View.VISIBLE
                                lay_et_division.visibility = View.VISIBLE
                                lay_text_mandal.visibility = View.VISIBLE
                                lay_et_mandal.visibility = View.VISIBLE
                                lay_text_Gps.visibility = View.VISIBLE
                                lay_et_gps.visibility = View.VISIBLE
                                getMasterDistricts()
                                getMasterDivisional()
                                getMasterMandal()
                                et_email.setText("")
                                et_mobilenumber.setText("")
                                txt_title.text = ""
                                txt_district.text = ""
                                txt_division.text = ""
                                txt_mandal.text = ""
                                et_surname.setText("")
                                et_lastname.setText("")
                                txt_gender.text = ""
                                txt_dateOfBirth.text = ""

                            }
                            popupMenu.dismiss();
                        }
                        lay_role.setOnClickListener {
                            popupMenu.show()
                        }
                    } else {
                        Toast.makeText(
                            this@RegistrationActivity,
                            "No Roles Data Found",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

        })
    }

    fun getMasterDistricts() {
        val reqobject = JSONObject()
        reqobject.put("ActionName", "District")
        reqobject.put("Id", "")
        Log.e("RequestBody", "Request Body:- ${reqobject}")
        val mediaType = MediaType.parse("application/json");
        val requestBody = RequestBody.create(mediaType, reqobject.toString())
        val callback = WebServiceClass.callWebServices().postRolesMaster(requestBody)
        callback.enqueue(object : Callback<RoleMasterDataModelClass> {
            override fun onFailure(
                call: Call<RoleMasterDataModelClass>,
                t: Throwable
            ) {
                t.printStackTrace()
            }

            override fun onResponse(
                call: Call<RoleMasterDataModelClass>,
                response: Response<RoleMasterDataModelClass>
            ) {
                Log.e("URL", "URL:- ${response.raw().request().url()}")
                Log.e("RequestBody", "Request Body:- ${response.body()}")
                RolesListDataModel = response.body()
                try {
                    val data = RolesListDataModel!!.Data
                    if (data.isNotEmpty() && data.size != 0) {
                        val popupMenu = ListPopupWindow(this@RegistrationActivity!!)
                        popupMenu.anchorView = lay_district
                        val adapter = PopupWindowSpinnerAdapter(this@RegistrationActivity!!, data)
                        popupMenu.setAdapter(adapter)
                        popupMenu.setOnItemClickListener { parent, view, position, id ->
                            txt_district.text = data[position].Data
                            districtId = data[position].Id
                            if (districtId != 0.toString()) {
                                getUserdistrictDetails()
                                getMasterDivisional()
                            }
                            popupMenu.dismiss();
                        }
                        lay_district.setOnClickListener {
                            popupMenu.show()
                        }
                    } else {
                        Toast.makeText(
                            this@RegistrationActivity,
                            "No District Data Found",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

        })
    }

    fun getMasterDivisional() {
        val reqobject = JSONObject()
        reqobject.put("ActionName", "Division")
        reqobject.put("Id", districtId)
        Log.e("RequestBody", "Request Body:- ${reqobject}")
        val mediaType = MediaType.parse("application/json");
        val requestBody = RequestBody.create(mediaType, reqobject.toString())
        val callback = WebServiceClass.callWebServices().postRolesMaster(requestBody)
        callback.enqueue(object : Callback<RoleMasterDataModelClass> {
            override fun onFailure(
                call: Call<RoleMasterDataModelClass>,
                t: Throwable
            ) {
                t.printStackTrace()
            }

            override fun onResponse(
                call: Call<RoleMasterDataModelClass>,
                response: Response<RoleMasterDataModelClass>
            ) {
                Log.e("URL", "URL:- ${response.raw().request().url()}")
                Log.e("RequestBody", "Request Body:- ${response.body()}")
                RolesListDataModel = response.body()
                try {
                    val data = RolesListDataModel!!.Data
                    if (data.isNotEmpty() && data.size != 0) {
                        val popupMenu = ListPopupWindow(this@RegistrationActivity!!)
                        popupMenu.anchorView = lay_et_division
                        val adapter = PopupWindowSpinnerAdapter(this@RegistrationActivity!!, data)
                        popupMenu.setAdapter(adapter)
                        popupMenu.setOnItemClickListener { parent, view, position, id ->
                            txt_division.text = data[position].Data
                            divisionid = data[position].Id
                            if (divisionid != 0.toString()) {
                                getUserdivisionDetails()
                                getMasterMandal()

                            }
                            popupMenu.dismiss();
                        }
                        lay_et_division.setOnClickListener {
                            popupMenu.show()
                        }
                    } else {
                        Toast.makeText(
                            this@RegistrationActivity,
                            "No District Data Found",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

        })
    }

    fun getMasterMandal() {
        val reqobject = JSONObject()
        reqobject.put("ActionName", "Mandal")
        reqobject.put("Id", divisionid)
        Log.e("RequestBody", "Request Body:- ${reqobject}")
        val mediaType = MediaType.parse("application/json");
        val requestBody = RequestBody.create(mediaType, reqobject.toString())
        val callback = WebServiceClass.callWebServices().postRolesMaster(requestBody)
        callback.enqueue(object : Callback<RoleMasterDataModelClass> {
            override fun onFailure(
                call: Call<RoleMasterDataModelClass>,
                t: Throwable
            ) {
                t.printStackTrace()
            }

            override fun onResponse(
                call: Call<RoleMasterDataModelClass>,
                response: Response<RoleMasterDataModelClass>
            ) {
                Log.e("URL", "URL:- ${response.raw().request().url()}")
                Log.e("RequestBody", "Request Body:- ${response.body()}")
                RolesListDataModel = response.body()
                try {
                    val data = RolesListDataModel!!.Data
                    if (data.isNotEmpty() && data.size != 0) {
                        val popupMenu = ListPopupWindow(this@RegistrationActivity!!)
                        popupMenu.anchorView = lay_et_mandal
                        val adapter = PopupWindowSpinnerAdapter(this@RegistrationActivity!!, data)
                        popupMenu.setAdapter(adapter)
                        popupMenu.setOnItemClickListener { parent, view, position, id ->
                            txt_mandal.text = data[position].Data
                            MandalId = data[position].Id
                            if (MandalId != 0.toString()) {
                                getUserMandalDetails()
                                getMasterPanchayatSecratory()
                            }
                            popupMenu.dismiss();
                        }
                        lay_et_mandal.setOnClickListener {
                            popupMenu.show()
                        }
                    } else {
                        Toast.makeText(
                            this@RegistrationActivity,
                            "No District Data Found",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

        })
    }

    fun getMasterPanchayatSecratory() {
        val reqobject = JSONObject()
        reqobject.put("ActionName", "Panchayat")
        reqobject.put("Id", MandalId)
        Log.e("RequestBody", "Request Body:- ${reqobject}")
        val mediaType = MediaType.parse("application/json");
        val requestBody = RequestBody.create(mediaType, reqobject.toString())
        val callback = WebServiceClass.callWebServices().postRolesMaster(requestBody)
        callback.enqueue(object : Callback<RoleMasterDataModelClass> {
            override fun onFailure(
                call: Call<RoleMasterDataModelClass>,
                t: Throwable
            ) {
                t.printStackTrace()
            }

            override fun onResponse(
                call: Call<RoleMasterDataModelClass>,
                response: Response<RoleMasterDataModelClass>
            ) {
                Log.e("URL", "URL:- ${response.raw().request().url()}")
                Log.e("RequestBody", "Request Body:- ${response.body()}")
                RolesListDataModel = response.body()
                try {
                    val data = RolesListDataModel!!.Data
                    if (data.isNotEmpty() && data.size != 0) {
                        val popupMenu = ListPopupWindow(this@RegistrationActivity!!)
                        popupMenu.anchorView = lay_et_gps
                        val adapter = PopupWindowSpinnerAdapter(this@RegistrationActivity!!, data)
                        popupMenu.setAdapter(adapter)
                        popupMenu.setOnItemClickListener { parent, view, position, id ->
                            txt_grampanchayat.text = data[position].Data
                            PanchayatId = data[position].Id
                            if (PanchayatId != 0.toString()) {
                                getUserPanchayatDetails()
                            }
                            popupMenu.dismiss();
                        }
                        lay_et_gps.setOnClickListener {
                            popupMenu.show()
                        }
                    } else {
                        Toast.makeText(
                            this@RegistrationActivity,
                            "No District Data Found",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

        })
    }

    fun gender() {
        val popupMenu = ListPopupWindow(this@RegistrationActivity!!)
        popupMenu.anchorView = lay_gender
        popupMenu.setAdapter(ArrayAdapter(this@RegistrationActivity, R.layout.list_item, Gender))
        popupMenu.setOnItemClickListener { parent, view, position, id ->
            txt_gender.text = Gender[position]
            if (Gender[position].equals("Male")) {
                GenderId = "1"
            } else if (Gender[position].equals("Female")) {
                GenderId = "2"
            } else if (Gender[position].equals("Transgender")) {
                GenderId = "3"
            }


            Log.e("genderposition", "$txt_gender")
            popupMenu.dismiss();
        }
        lay_gender.setOnClickListener {
            popupMenu.show()
        }
    }

    fun titleselection() {
        val popupMenu = ListPopupWindow(this@RegistrationActivity!!)
        popupMenu.anchorView = rl_title
        popupMenu.setAdapter(ArrayAdapter(this@RegistrationActivity, R.layout.list_item, Title))
        popupMenu.setOnItemClickListener { parent, view, position, id ->
            txt_title.text = Title[position]

            if (Title[position].equals("Mr.")) {
                TitleId = "1"
            } else if (Title[position].equals("Mrs.")) {
                TitleId = "2"
            }

            Log.e("genderposition", "$txt_title")
            popupMenu.dismiss();
        }
        rl_title.setOnClickListener {
            popupMenu.show()
        }
    }

    fun getUserdistrictDetails() {
        val reqobject = JSONObject()
        reqobject.put("DistrictId", districtId)
        reqobject.put("DivisionId", "")
        reqobject.put("MandalId", "")
        reqobject.put("PanchayatId", "")
        reqobject.put("RoleId", RoleId)
        Log.e("RequestBody", "Request Body:- ${reqobject}")
        val mediaType = MediaType.parse("application/json");
        val requestBody = RequestBody.create(mediaType, reqobject.toString())
        val callback = WebServiceClass.callWebServices().postUserDetails(requestBody)
        callback.enqueue(object : Callback<UserDetailsDataModelClass> {
            override fun onFailure(
                call: Call<UserDetailsDataModelClass>,
                t: Throwable
            ) {
                t.printStackTrace()
            }

            override fun onResponse(
                call: Call<UserDetailsDataModelClass>,
                response: Response<UserDetailsDataModelClass>
            ) {
                Log.e("URL", "URL:- ${response.raw().request().url()}")
                Log.e("RequestBody", "Request Body:- ${response.body()}")
                try {
                    UserDatadetails = response.body()
                    val Code = UserDatadetails!!.Code.toInt()
                    if (Code == 1) {
                        et_email.setText(UserDatadetails!!.EmailId)
                        et_mobilenumber.setText(UserDatadetails!!.MobileNumber)
                        txt_title.text = UserDatadetails!!.TitleName
                        et_surname.setText(UserDatadetails!!.Name)
                        et_lastname.setText(UserDatadetails!!.SurName)
                        txt_dateOfBirth.text = UserDatadetails!!.DateofBirth
                        if (UserDatadetails!!.Gender == 0.toString()) {
                            gender()
                        } else if (UserDatadetails!!.Gender == 1.toString()) {
                            txt_gender.text = "Male"
                        } else if (UserDatadetails!!.Gender == 2.toString()) {
                            txt_gender.text = "Female"
                        } else if (UserDatadetails!!.Gender == 3.toString()) {
                            txt_gender.text = "Transgender"
                        }


                    } else {
                        et_email.setText("")
                        et_mobilenumber.setText("")
                        txt_title.text = ""
                        et_surname.setText("")
                        et_lastname.setText("")
                        txt_gender.text = ""
                        txt_dateOfBirth.text = ""
                        gender()
                        titleselection()
                    }

                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

        })
    }

    fun getUserdivisionDetails() {
        val reqobject = JSONObject()
        reqobject.put("DistrictId", districtId)
        reqobject.put("DivisionId", divisionid)
        reqobject.put("MandalId", "")
        reqobject.put("PanchayatId", "")
        reqobject.put("RoleId", RoleId)
        Log.e("RequestBody", "Request Body:- ${reqobject}")
        val mediaType = MediaType.parse("application/json");
        val requestBody = RequestBody.create(mediaType, reqobject.toString())
        val callback = WebServiceClass.callWebServices().postUserDetails(requestBody)
        callback.enqueue(object : Callback<UserDetailsDataModelClass> {
            override fun onFailure(
                call: Call<UserDetailsDataModelClass>,
                t: Throwable
            ) {
                t.printStackTrace()
            }

            override fun onResponse(
                call: Call<UserDetailsDataModelClass>,
                response: Response<UserDetailsDataModelClass>
            ) {
                Log.e("URL", "URL:- ${response.raw().request().url()}")
                Log.e("RequestBody", "Request Body:- ${response.body()}")
                try {
                    UserDatadetails = response.body()
                    val Code = UserDatadetails!!.Code.toInt()
                    if (Code == 1) {
                        et_email.setText(UserDatadetails!!.EmailId)
                        et_mobilenumber.setText(UserDatadetails!!.MobileNumber)
                        txt_title.text = UserDatadetails!!.TitleName
                        et_surname.setText(UserDatadetails!!.Name)
                        et_lastname.setText(UserDatadetails!!.SurName)
                        txt_dateOfBirth.text = UserDatadetails!!.DateofBirth
                        if (UserDatadetails!!.Gender == 0.toString()) {
                            gender()
                        } else if (UserDatadetails!!.Gender == 1.toString()) {
                            txt_gender.text = "Male"
                        } else if (UserDatadetails!!.Gender == 2.toString()) {
                            txt_gender.text = "Female"
                        } else if (UserDatadetails!!.Gender == 3.toString()) {
                            txt_gender.text = "Transgender"
                        }

                    } else {
                        et_email.setText("")
                        et_mobilenumber.setText("")
                        txt_title.text = ""
                        et_surname.setText("")
                        et_lastname.setText("")
                        txt_gender.text = ""
                        txt_dateOfBirth.text = ""
                        gender()
                        titleselection()
                    }

                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

        })
    }

    fun getUserMandalDetails() {
        val reqobject = JSONObject()
        reqobject.put("DistrictId", districtId)
        reqobject.put("DivisionId", divisionid)
        reqobject.put("MandalId", MandalId)
        reqobject.put("PanchayatId", "")
        reqobject.put("RoleId", RoleId)
        Log.e("RequestBody", "Request Body:- ${reqobject}")
        val mediaType = MediaType.parse("application/json");
        val requestBody = RequestBody.create(mediaType, reqobject.toString())
        val callback = WebServiceClass.callWebServices().postUserDetails(requestBody)
        callback.enqueue(object : Callback<UserDetailsDataModelClass> {
            override fun onFailure(
                call: Call<UserDetailsDataModelClass>,
                t: Throwable
            ) {
                t.printStackTrace()
            }

            override fun onResponse(
                call: Call<UserDetailsDataModelClass>,
                response: Response<UserDetailsDataModelClass>
            ) {
                Log.e("URL", "URL:- ${response.raw().request().url()}")
                Log.e("RequestBody", "Request Body:- ${response.body()}")
                try {
                    UserDatadetails = response.body()
                    val Code = UserDatadetails!!.Code.toInt()
                    if (Code == 1) {
                        et_email.setText(UserDatadetails!!.EmailId)
                        et_mobilenumber.setText(UserDatadetails!!.MobileNumber)
                        txt_title.text = UserDatadetails!!.TitleName
                        et_surname.setText(UserDatadetails!!.Name)
                        et_lastname.setText(UserDatadetails!!.SurName)
                        txt_dateOfBirth.text = UserDatadetails!!.DateofBirth
                        if (UserDatadetails!!.Gender == 0.toString()) {
                            gender()
                        } else if (UserDatadetails!!.Gender == 1.toString()) {
                            txt_gender.text = "Male"
                        } else if (UserDatadetails!!.Gender == 2.toString()) {
                            txt_gender.text = "Female"
                        } else if (UserDatadetails!!.Gender == 3.toString()) {
                            txt_gender.text = "Transgender"
                        }

                    } else {
                        et_email.setText("")
                        et_mobilenumber.setText("")
                        txt_title.text = ""
                        et_surname.setText("")
                        et_lastname.setText("")
                        txt_gender.text = ""
                        txt_dateOfBirth.text = ""
                        gender()
                        titleselection()
                    }

                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

        })
    }

    fun getUserPanchayatDetails() {
        val reqobject = JSONObject()
        reqobject.put("DistrictId", districtId)
        reqobject.put("DivisionId", divisionid)
        reqobject.put("MandalId", MandalId)
        reqobject.put("PanchayatId", PanchayatId)
        reqobject.put("RoleId", RoleId)
        Log.e("RequestBody", "Request Body:- ${reqobject}")
        val mediaType = MediaType.parse("application/json");
        val requestBody = RequestBody.create(mediaType, reqobject.toString())
        val callback = WebServiceClass.callWebServices().postUserDetails(requestBody)
        callback.enqueue(object : Callback<UserDetailsDataModelClass> {
            override fun onFailure(
                call: Call<UserDetailsDataModelClass>,
                t: Throwable
            ) {
                t.printStackTrace()
            }

            override fun onResponse(
                call: Call<UserDetailsDataModelClass>,
                response: Response<UserDetailsDataModelClass>
            ) {
                Log.e("URL", "URL:- ${response.raw().request().url()}")
                Log.e("RequestBody", "Request Body:- ${response.body()}")
                try {
                    UserDatadetails = response.body()
                    val Code = UserDatadetails!!.Code.toInt()
                    if (Code == 1) {
                        et_email.setText(UserDatadetails!!.EmailId)
                        et_mobilenumber.setText(UserDatadetails!!.MobileNumber)
                        txt_title.text = UserDatadetails!!.TitleName
                        et_surname.setText(UserDatadetails!!.Name)
                        et_lastname.setText(UserDatadetails!!.SurName)
                        txt_dateOfBirth.text = UserDatadetails!!.DateofBirth
                        if (UserDatadetails!!.Gender == 1.toString()) {
                            txt_gender.text = "Male"
                        } else if (UserDatadetails!!.Gender == 2.toString()) {
                            txt_gender.text = "Female"
                        } else if (UserDatadetails!!.Gender == 3.toString()) {
                            txt_gender.text = "Transgender"
                        }

                    } else {
                        if (Code == -1) {
                            et_email.setText("")
                            et_mobilenumber.setText("")
                            txt_title.text = ""
                            et_surname.setText("")
                            et_lastname.setText("")
                            txt_gender.text = ""
                            txt_dateOfBirth.text = ""
                            gender()
                            titleselection()
                        }

                    }

                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

        })
    }


    private fun selectImage() {
        val options = arrayOf<CharSequence>("Take Photo", "Choose from Gallery", "Cancel")

        val builder = android.app.AlertDialog.Builder(this@RegistrationActivity)
        builder.setTitle("Choose your profile picture")

        builder.setItems(options) { dialog, item ->
            if (options[item] == "Take Photo") {
                val takePicture = Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(takePicture, 0)

            } else if (options[item] == "Choose from Gallery") {
                val pickPhoto = Intent(
                    Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                )
                startActivityForResult(pickPhoto, 1)

            } else if (options[item] == "Cancel") {
                dialog.dismiss()
            }
        }
        builder.show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != Activity.RESULT_CANCELED) {
            when (requestCode) {
                0 -> if (resultCode == Activity.RESULT_OK && data != null) {
                    val selectedImage = data.extras!!.get("data") as Bitmap?
                    val outputStream = ByteArrayOutputStream()
                    selectedImage!!.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
                    val byteArray = outputStream.toByteArray()
                    ImagePath = Base64.encodeToString(byteArray, Base64.DEFAULT)
                    Log.e("PhotoImagePath", "" + ImagePath)
                    profile_image!!.setImageBitmap(selectedImage)
                }
                1 -> if (resultCode == Activity.RESULT_OK && data != null) {
                    val selectedImage = data.data
                    val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
                    if (selectedImage != null) {
                        val cursor = applicationContext!!.contentResolver.query(
                            selectedImage,
                            filePathColumn, null, null, null
                        )
                        if (cursor != null) {
                            cursor.moveToFirst()

                            val columnIndex = cursor.getColumnIndex(filePathColumn[0])
                            val picturePath = cursor.getString(columnIndex)
                            try {
                                val bitmap = MediaStore.Images.Media.getBitmap(
                                    applicationContext!!.contentResolver,
                                    selectedImage
                                )
                                val outputStream = ByteArrayOutputStream()
                                bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
                                val byteArray = outputStream.toByteArray()
                                ImagePath = Base64.encodeToString(byteArray, Base64.DEFAULT)
                                Log.e("GalleryImagePath", "text" + ImagePath)
                                val f = File(picturePath)
                                val imageName = f.getName()
                                profile_image!!.setImageBitmap(bitmap)
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }

                            cursor.close()
                        }
                    }

                }
            }
        }
    }

    fun registrationUser() {
        password = et_password.text.toString()
        confirmpassword = et_confirmpassword.text.toString()
        Username = et_username.text.toString()
        Email = et_email.text.toString()
        MobileNumber = et_mobilenumber.text.toString()
        Surname = et_surname.text.toString()
        LastName = et_lastname.text.toString()
        dob = txt_dateOfBirth.text.toString()


        if (ImagePath == null) {
            Toast.makeText(this@RegistrationActivity, "Please Select Image", Toast.LENGTH_SHORT)
                .show()
        } else if (txt_rolename.text.isEmpty()) {
            Toast.makeText(this@RegistrationActivity, "Please Select Role", Toast.LENGTH_SHORT)
                .show()
        } else if (RoleId == 3.toString()) {
            if (txt_district.text.isEmpty()) {
                Toast.makeText(
                    this@RegistrationActivity,
                    "Please Select District",
                    Toast.LENGTH_SHORT
                ).show()
            } else if (Email!!.isEmpty()) {
                et_email.error = "Please Enter Email"
            } else if (MobileNumber!!.isEmpty()) {
                et_mobilenumber.error = "Please Enter Mobile Number"
            } else if (txt_title.text!!.isEmpty()) {
                Toast.makeText(this@RegistrationActivity, "Please Select Title", Toast.LENGTH_SHORT)
                    .show()
            } else if (Surname!!.isEmpty()) {
                et_surname.error = "Please Enter FirstName"
            } else if (LastName!!.isEmpty()) {
                et_lastname.error = "Please Enter LastName"
            } else if (txt_gender!!.text.isEmpty()) {
                Toast.makeText(
                    this@RegistrationActivity,
                    "Please Select Gender",
                    Toast.LENGTH_SHORT
                )
                    .show()
            } else if (dob!!.isEmpty()) {
                txt_dateOfBirth.error = "Please Enter DateofBirth"
            } else if (Username!!.isEmpty()) {
                et_username.error = "Please Enter UserName"
            } else if (!isValidPassword((password!!))) {
                et_password.error =
                    "Password must contain mix of upper and lower case letters as well as digits and one special character(8-15)"
            } else if (!password.equals(confirmpassword)) {
                et_confirmpassword.error = "Passwords Don't Match !"
            } else {
                senddatatoserver()
            }
        } else if (RoleId == 4.toString()) {
            if (txt_district.text.isEmpty()) {
                Toast.makeText(
                    this@RegistrationActivity,
                    "Please Select District",
                    Toast.LENGTH_SHORT
                ).show()
            } else if (txt_division.text.isEmpty()) {
                Toast.makeText(
                    this@RegistrationActivity,
                    "Please Select Division",
                    Toast.LENGTH_SHORT
                ).show()
            } else if (Email!!.isEmpty()) {
                et_email.error = "Please Enter Email"
            } else if (MobileNumber!!.isEmpty()) {
                et_mobilenumber.error = "Please Enter Mobile Number"
            } else if (txt_title.text!!.isEmpty()) {
                Toast.makeText(this@RegistrationActivity, "Please Select Title", Toast.LENGTH_SHORT)
                    .show()
            } else if (Surname!!.isEmpty()) {
                et_surname.error = "Please Enter FirstName"
            } else if (LastName!!.isEmpty()) {
                et_lastname.error = "Please Enter LastName"
            } else if (txt_gender!!.text.isEmpty()) {
                Toast.makeText(
                    this@RegistrationActivity,
                    "Please Select Gender",
                    Toast.LENGTH_SHORT
                )
                    .show()
            } else if (dob!!.isEmpty()) {
                txt_dateOfBirth.error = "Please Enter DateofBirth"
            } else if (Username!!.isEmpty()) {
                et_username.error = "Please Enter UserName"
            } else if (!isValidPassword((password!!))) {
                et_password.error =
                    "Password must contain mix of upper and lower case letters as well as digits and one special character(8-15)"
            } else if (!password.equals(confirmpassword)) {
                et_confirmpassword.error = "Passwords Don't Match !"
            } else {
                senddatatoserver()
            }
        } else if (RoleId == 5.toString()) {
            if (txt_district.text.isEmpty()) {
                Toast.makeText(
                    this@RegistrationActivity,
                    "Please Select District",
                    Toast.LENGTH_SHORT
                ).show()
            } else if (txt_division.text.isEmpty()) {
                Toast.makeText(
                    this@RegistrationActivity,
                    "Please Select Division",
                    Toast.LENGTH_SHORT
                ).show()
            } else if (txt_mandal.text.isEmpty()) {
                Toast.makeText(
                    this@RegistrationActivity,
                    "Please Select Mandal",
                    Toast.LENGTH_SHORT
                ).show()
            } else if (Email!!.isEmpty()) {
                et_email.error = "Please Enter Email"
            } else if (MobileNumber!!.isEmpty()) {
                et_mobilenumber.error = "Please Enter Mobile Number"
            } else if (txt_title.text!!.isEmpty()) {
                Toast.makeText(this@RegistrationActivity, "Please Select Title", Toast.LENGTH_SHORT)
                    .show()
            } else if (Surname!!.isEmpty()) {
                et_surname.error = "Please Enter FirstName"
            } else if (LastName!!.isEmpty()) {
                et_lastname.error = "Please Enter LastName"
            } else if (txt_gender!!.text.isEmpty()) {
                Toast.makeText(
                    this@RegistrationActivity,
                    "Please Select Gender",
                    Toast.LENGTH_SHORT
                )
                    .show()
            } else if (dob!!.isEmpty()) {
                txt_dateOfBirth.error = "Please Enter DateofBirth"
            } else if (Username!!.isEmpty()) {
                et_username.error = "Please Enter UserName"
            } else if (!isValidPassword((password!!))) {
                et_password.error =
                    "Password must contain mix of upper and lower case letters as well as digits and one special character(8-15)"
            } else if (!password.equals(confirmpassword)) {
                et_confirmpassword.error = "Passwords Don't Match !"
            } else {
                senddatatoserver()
            }

        } else if (RoleId == 6.toString()) {
            if (txt_district.text.isEmpty()) {
                Toast.makeText(
                    this@RegistrationActivity,
                    "Please Select District",
                    Toast.LENGTH_SHORT
                ).show()
            } else if (txt_division.text.isEmpty()) {
                Toast.makeText(
                    this@RegistrationActivity,
                    "Please Select Division",
                    Toast.LENGTH_SHORT
                ).show()
            } else if (txt_mandal.text.isEmpty()) {
                Toast.makeText(
                    this@RegistrationActivity,
                    "Please Select Mandal",
                    Toast.LENGTH_SHORT
                ).show()
            } else if (txt_grampanchayat.text.isEmpty()) {
                Toast.makeText(
                    this@RegistrationActivity,
                    "Please Select GramPanchayat",
                    Toast.LENGTH_SHORT
                ).show()
            } else if (Email!!.isEmpty()) {
                et_email.error = "Please Enter Email"
            } else if (MobileNumber!!.isEmpty()) {
                et_mobilenumber.error = "Please Enter Mobile Number"
            } else if (txt_title.text!!.isEmpty()) {
                Toast.makeText(this@RegistrationActivity, "Please Select Title", Toast.LENGTH_SHORT)
                    .show()
            } else if (Surname!!.isEmpty()) {
                et_surname.error = "Please Enter FirstName"
            } else if (LastName!!.isEmpty()) {
                et_lastname.error = "Please Enter LastName"
            } else if (txt_gender!!.text.isEmpty()) {
                Toast.makeText(
                    this@RegistrationActivity,
                    "Please Select Gender",
                    Toast.LENGTH_SHORT
                )
                    .show()
            } else if (dob!!.isEmpty()) {
                txt_dateOfBirth.error = "Please Enter DateofBirth"
            } else if (Username!!.isEmpty()) {
                et_username.error = "Please Enter UserName"
            } else if (!isValidPassword((password!!))) {
                et_password.error =
                    "Password must contain mix of upper and lower case letters as well as digits and one special character(8-15)"
            } else if (!password.equals(confirmpassword)) {
                et_confirmpassword.error = "Passwords Don't Match !"
            } else {
                senddatatoserver()
            }
        }


    }

    fun senddatatoserver() {

        if (MandalId != null) {
        } else {
            MandalId = ""
        }
        if (PanchayatId != null) {

        } else {
            PanchayatId = ""
        }
        if (divisionid != null) {

        } else {
            divisionid = ""
        }


        val reqObject = JSONObject()
        if (UserDatadetails!!.Gender != null) {
            reqObject.put("TitleName", UserDatadetails!!.TitleName)
        } else {
            reqObject.put("TitleName", TitleId)
        }

        reqObject.put("Name", Surname)
        reqObject.put("SurName", LastName)
        reqObject.put("UserName", Username)
        reqObject.put("Password", password)
        if (UserDatadetails!!.Gender != null) {
            reqObject.put("Gender", UserDatadetails!!.Gender)
        } else {
            reqObject.put("Gender", GenderId)
        }

        reqObject.put("DateofBirth", txt_dateOfBirth.text.toString())
        reqObject.put("MobileNumber", MobileNumber)
        reqObject.put("IMEINumber", IMEINumber)
        reqObject.put("EmailId", Email)
        reqObject.put("RoleId", RoleId)
        reqObject.put("DistrictId", districtId)
        reqObject.put("DivisionId", divisionid)
        reqObject.put("MandalId", MandalId)
        reqObject.put("PanchayatId", PanchayatId)
        reqObject.put("ProfilePicName", ImagePath)
        reqObject.put("Createdby", "")
        Log.e("RequestBody", "Request Body:- ${reqObject}")
        val mediaType = MediaType.parse("application/json");
        val requestBody = RequestBody.create(mediaType, reqObject.toString())
        val callBcak = WebServiceClass.callWebServices().postRegistrationDetails(requestBody)
        callBcak.enqueue(object : Callback<String> {
            override fun onFailure(call: Call<String>, t: Throwable) {
                t.printStackTrace()
                Toast.makeText(
                    this@RegistrationActivity,
                    "Unable to Connect Server",
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onResponse(call: Call<String>, response: Response<String>) {
                try {
                    Log.e("URL", "URL:- ${response.raw().request().url()}")
                    Log.e("RequestBody", "Request Body:- ${response.body()}")
                    val jsonObject = JSONObject(response.body().toString())
                    val Code = jsonObject.getString("Code").toInt()
                    val Message = jsonObject.getString("Message")
                    if (Code == 1) {
                        otpdialog(et_mobilenumber.text.toString())
                        et_email.setText("")
                        et_mobilenumber.setText("")
                        txt_rolename.text = ""
                        txt_title.text = ""
                        txt_district.text = ""
                        txt_division.text = ""
                        txt_mandal.text = ""
                        et_surname.setText("")
                        et_lastname.setText("")
                        txt_gender.text = ""
                        txt_dateOfBirth.text = ""
                        Toast.makeText(this@RegistrationActivity, Message, Toast.LENGTH_SHORT)
                            .show()
                    }

                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        })
    }

    fun otpdialog(MobileNumber: String) {
        val dialogs = Dialog(this@RegistrationActivity)
        dialogs.setContentView(R.layout.dialog_verification)
        dialogs.window!!.setLayout(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        dialogs.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val lay_rg_yes = dialogs.findViewById(R.id.lay_rg_yes) as RelativeLayout
        val lay_reportno_rg = dialogs.findViewById(R.id.lay_reportno_rg) as RelativeLayout

        val et_otp = dialogs.findViewById(R.id.et_otp) as EditText

        et_otp.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
                if (s.length == 6) {
                    if (et_otp.getText() != null) {
                        otpAuto = et_otp.getText().toString()
                    }
                }
            }
        })
        lay_rg_yes.setOnClickListener {
            if (et_otp.text.toString().isEmpty()) {
                et_otp.error = "Please Enter Mobile OTP"
            } else if (et_otp.getText().trim().toString() == OTP) {

                val reqObject = JSONObject()
                reqObject.put("MobileNo", MobileNumber)
                reqObject.put("IsVerifiedOTP", "1")
                Log.e("RequestBody", "Request Body:- ${reqObject}")
                val mediaType = MediaType.parse("application/json");
                val requestBody = RequestBody.create(mediaType, reqObject.toString())
                val callBcak = WebServiceClass.callWebServices().SaveOTPVerification(requestBody)
                callBcak.enqueue(object : Callback<String> {
                    override fun onFailure(call: Call<String>, t: Throwable) {
                        t.printStackTrace()
                        Toast.makeText(
                            applicationContext,
                            "Unable to Connect Server ",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }

                    override fun onResponse(call: Call<String>, response: Response<String>) {
                        try {
                            Log.e("URL", "URL:- ${response.raw().request().url()}")
                            Log.e("RequestBody", "Request Body:- ${response.body()}")
                            val jsonObject = JSONObject(response.body().toString())
                            val Code = jsonObject.getString("Code").toInt()
                            val Message = jsonObject.getString("Message")
                            if (Code == 1) {
                                registrationinfodialog()
                                dialogs.dismiss()
                                Toast.makeText(
                                    this@RegistrationActivity,
                                    Message,
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                            } else if (Code.equals(-1)) {
                                Toast.makeText(
                                    this@RegistrationActivity,
                                    Message,
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                            }

                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                })
                //registrationinfodialog()
                dialogs.dismiss()
            }

        }
        val call: Call<String> =
            WebServiceClass.callWebServicesverficationotp().sentOTP("8520972368")
        call.enqueue(object : Callback<String> {
            override fun onResponse(
                call: Call<String>?,
                response: Response<String>?
            ) {
                try {
                    Log.e(
                        "Response",
                        "Code ${response!!.code()}, Data ${response.body()}, \n URL:- ${response.raw().request().url()}"
                    )

                    if (response.code() == 200) {
                        val responseData = response.body()
                        Log.e("response", "$responseData")
                        val jsonObject = JSONObject(response.body().toString())
                        val responseMsg = jsonObject.getString("responseMsg")
                        Toast.makeText(
                            this@RegistrationActivity,
                            "${responseMsg}",
                            Toast.LENGTH_LONG
                        )
                            .show()
                        response.body()
                        OTP = jsonObject.getString("otp")
                        SmsReceiver.bindListener(object : SmsListener {
                            override fun messageReceived(id: String) {
                                otpAuto = id
                                et_otp.setText(otpAuto)


                            }
                        })

                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            override fun onFailure(call: Call<String>?, t: Throwable?) {
                t!!.printStackTrace()
                Toast.makeText(
                    this@RegistrationActivity,
                    "Unable to Connect Server",
                    Toast.LENGTH_SHORT
                ).show()
                Log.e("ERROR", t.toString())
            }
        })
        lay_reportno_rg.setOnClickListener {
            val call: Call<String> =
                WebServiceClass.callWebServicesverficationotp().sentOTP("8520972368")
            call.enqueue(object : Callback<String> {
                override fun onResponse(
                    call: Call<String>?,
                    response: Response<String>?
                ) {
                    try {
                        Log.e(
                            "Response",
                            "Code ${response!!.code()}, Data ${response.body()}, \n URL:- ${response.raw().request().url()}"
                        )

                        if (response.code() == 200) {
                            val responseData = response.body()
                            Log.e("response", "$responseData")
                            val jsonObject = JSONObject(response.body().toString())
                            val responseMsg = jsonObject.getString("responseMsg")
                            Toast.makeText(
                                this@RegistrationActivity,
                                "${responseMsg}",
                                Toast.LENGTH_LONG
                            )
                                .show()
                            response.body()
                            OTP = jsonObject.getString("otp")
                            SmsReceiver.bindListener(object : SmsListener {
                                override fun messageReceived(id: String) {
                                    otpAuto = id
                                    et_otp.setText(otpAuto)


                                }
                            })

                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }

                override fun onFailure(call: Call<String>?, t: Throwable?) {
                    t!!.printStackTrace()
                    Toast.makeText(
                        this@RegistrationActivity,
                        "Unable to Connect Server",
                        Toast.LENGTH_SHORT
                    ).show()
                    Log.e("ERROR", t.toString())
                }
            })
        }
        val iv_dismis = dialogs.findViewById(R.id.iv_dismis) as ImageView
        iv_dismis.setOnClickListener {
            dialogs.dismiss()
        }
        dialogs.show()
    }


    private fun getOTP(id: String) {

    }

    fun registrationinfodialog() {
        val dialogs = Dialog(this@RegistrationActivity)
        dialogs.setContentView(R.layout.dialog_registrationinfo)
        dialogs.window!!.setLayout(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        dialogs.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val btn_ok = dialogs.findViewById(R.id.btn_ok) as Button
        btn_ok.setOnClickListener {
            val intent = Intent(this@RegistrationActivity, LoginActivity::class.java)
            startActivity(intent)
            dialogs.dismiss()
        }

        dialogs.show()
    }

    private fun requestMultiplePermissions() {
        Dexter.withActivity(this)
            .withPermissions(
                Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.READ_PHONE_STATE
            )
            .withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport) {
                    if (report.areAllPermissionsGranted()) {
                        Toast.makeText(
                            applicationContext,
                            "All permissions are granted by user!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    if (report.isAnyPermissionPermanentlyDenied) {
                    }
                }

                override fun onPermissionRationaleShouldBeShown(
                    permissions: List<com.karumi.dexter.listener.PermissionRequest>,
                    token: PermissionToken
                ) {
                    token.continuePermissionRequest()
                }
            }).withErrorListener {
                Toast.makeText(
                    applicationContext,
                    "Some Error! ",
                    Toast.LENGTH_SHORT
                ).show()
            }
            .onSameThread()
            .check()
    }

    fun isValidPassword(password: String): Boolean {
        val matcher = Pattern.compile("((?=.*[a-z])(?=.*\\d)(?=.*[A-Z])(?=.*[@#$%!]).{4,20})")
            .matcher(password)
        return matcher.matches()
    }

    fun clickDataPickerOFDOB() {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        val dpd = DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                val dateDOB = "$dayOfMonth-${month + 1}-$year"
                val inputPattern = "dd-MM-yyyy";
                val outputPattern = "yyyy-MM-dd";
                val inputFormat = SimpleDateFormat(inputPattern);
                val outputFormat = SimpleDateFormat(outputPattern);
                try {
                    val date = inputFormat.parse(dateDOB);
                    val str = outputFormat.format(date);
                    txt_dateOfBirth.setText(str)
                } catch (e: Exception) {
                    e.printStackTrace();
                }
            },
            year,
            month,
            day
        )
        dpd.show()
    }


    fun registrationreportdialog() {
        val dialogs = Dialog(this@RegistrationActivity)
        dialogs.setContentView(R.layout.dialog_registrationalert)
        dialogs.window!!.setLayout(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        dialogs.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val lay_reportno_rg = dialogs.findViewById(R.id.lay_reportno_rg) as RelativeLayout
        val lay_rg_yes = dialogs.findViewById(R.id.lay_rg_yes) as RelativeLayout
        lay_rg_yes.setOnClickListener {
            //            SaveSharedPreference.getSessionDataInstance().clearAll()
            val intent = Intent(this@RegistrationActivity, LoginActivity::class.java)
            startActivity(intent)
            dialogs.dismiss()
        }
        lay_reportno_rg.setOnClickListener { dialogs.dismiss() }
        dialogs.show()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

    }

}
