package com.techdatum.epanchayat_application.activity

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.CountDownTimer
import android.telephony.TelephonyManager
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.techdatum.epanchayat.textUi.CustomTextView
import com.techdatum.epanchayat_application.MainActivity
import com.techdatum.epanchayat_application.R
import com.techdatum.epanchayat_application.broadcast.SmsListener
import com.techdatum.epanchayat_application.broadcast.SmsReceiver
import com.techdatum.epanchayat_application.webservices.SaveSharedPreference
import com.techdatum.epanchayat_application.webservices.WebServiceClass
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.et_password
import kotlinx.android.synthetic.main.dialog_forgotpassword.*
import kotlinx.android.synthetic.main.dialog_loginerror.*
import kotlinx.android.synthetic.main.dialog_verification.*
import okhttp3.MediaType
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.regex.Pattern


class LoginActivity : AppCompatActivity() {
    var telephonymanager: TelephonyManager? = null
    var IMEINumber: String? = null
    private var loginPrefsEditor: SharedPreferences.Editor? = null
    private var saveLogin: Boolean? = null
    var loginPreferences: SharedPreferences? = null
    var OTP: String? = null
    var otpAuto: String? = null
    var counter: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        bt_login.setOnClickListener {

            if (et_email.text.isEmpty()) {
                et_email.setError("Please Enter Mobile Number or User name")
            } else if (et_password.text.isEmpty()) {
                et_email.setError("Password")

            } else {
                if (savecredentials.isChecked) {
                    loginPrefsEditor!!.putBoolean("saveLogin", true)
                    loginPrefsEditor!!.putString("username", et_email.text.toString())
                    loginPrefsEditor!!.putString("Password", et_password.text.toString())
                    loginPrefsEditor!!.commit()
                } else {
                    loginPrefsEditor!!.clear()
                    loginPrefsEditor!!.commit()
                }
                sendtoserver()
            }
//            val intent = Intent(this@LoginActivity, MainActivity::class.java)
//            startActivity(intent)
        }
        bt_register.setOnClickListener {
            val intent = Intent(this@LoginActivity, RegistrationActivity::class.java)
            startActivity(intent)
        }
        loginPreferences = getSharedPreferences("loginPrefs", Context.MODE_PRIVATE)
        loginPrefsEditor = loginPreferences!!.edit()
        saveLogin = loginPreferences!!.getBoolean("saveLogin", false)
        if (saveLogin == true) {
            et_email.setText(loginPreferences!!.getString("username", ""))
            et_password.setText(loginPreferences!!.getString("Password", ""))
            savecredentials.isChecked = true
        }
        tv_forgotpassword.setOnClickListener {
            forgotpassworddialog()
        }
    }


    @SuppressLint("MissingPermission")
    fun sendtoserver() {

        try {
            telephonymanager = getSystemService(TELEPHONY_SERVICE) as TelephonyManager
            IMEINumber = telephonymanager!!.getDeviceId()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        val reqObject = JSONObject()
        reqObject.put("UserId", et_email!!.text.toString())
        reqObject.put("Password", et_password.text.toString())
        reqObject.put("IMEI", IMEINumber)
        Log.e("RequestBody", "Request Body:- ${reqObject}")
        val mediaType = MediaType.parse("application/json");
        val requestBody = RequestBody.create(mediaType, reqObject.toString())
        val callBcak = WebServiceClass.callWebServices().postLoginDetails(requestBody)
        callBcak.enqueue(object : Callback<String> {
            override fun onFailure(call: Call<String>, t: Throwable) {
                t.printStackTrace()
            }

            override fun onResponse(call: Call<String>, response: Response<String>) {
                try {
                    Log.e("URL", "URL:- ${response.raw().request().url()}")
                    Log.e("RequestBody", "Request Body:- ${response.body()}")
                    val jsonObject = JSONObject(response.body().toString())
                    val Code = jsonObject.getString("Code").toInt()
                    val Message = jsonObject.getString("Message")
                    if (Code == 1) {

                        val AppUserId: String = jsonObject.getString("AppUserId")
                        val TitleName: String = jsonObject.getString("TitleName")
                        val Name: String = jsonObject.getString("Name")
                        val SurName: String = jsonObject.getString("SurName")
                        val UserName: String = jsonObject.getString("UserName")
                        val Password: String = jsonObject.getString("Password")
                        val Gender: String = jsonObject.getString("Gender")
                        val DateofBirth: String = jsonObject.getString("DateofBirth")
                        val MobileNumber: String = jsonObject.getString("MobileNumber")
                        val IMEINumber: String = jsonObject.getString("IMEINumber")
                        val EmailId: String = jsonObject.getString("EmailId")
                        val RoleId: String = jsonObject.getString("RoleId")
                        val Role: String = jsonObject.getString("Role")
                        val DistrictId: String = jsonObject.getString("DistrictId")
                        val District: String = jsonObject.getString("District")
                        val DivisionId: String = jsonObject.getString("DivisionId")
                        val Division: String = jsonObject.getString("Division")
                        val MandalId: String = jsonObject.getString("MandalId")
                        val Mandal: String = jsonObject.getString("Mandal")
                        val PanchayatId: String = jsonObject.getString("PanchayatId")
                        val Panchayat: String = jsonObject.getString("Panchayat")
                        val NicOfficeId: String = jsonObject.getString("NicOfficeId")
                        val IsApproved: String = jsonObject.getString("IsApproved")
                        val ProfilePicName: String = jsonObject.getString("ProfilePicName")
                        val IsVerifiedOTP: String = jsonObject.getString("IsVerifiedOTP")
                        val user = SaveSharedPreference()
                        user.saveAppUserId(AppUserId);
                        user.saveTitleName(TitleName);
                        user.saveName(Name);
                        user.saveClientSurName(SurName);
                        user.saveUserName(UserName)
                        user.savePassword(Password);
                        user.saveGender(Gender)
                        user.saveDateofBirth(DateofBirth);
                        user.saveMobileNumber(MobileNumber);
                        user.saveIMEINumber(IMEINumber)
                        user.saveEmailId(EmailId);
                        user.saveRoleId(RoleId);
                        user.saveRole(Role)
                        user.saveDistrictId(DistrictId);
                        user.saveDistrict(District);
                        user.saveDivisionId(DivisionId);
                        user.saveDivision(Division);
                        user.saveMandal(Mandal);
                        user.savePanchayatId(PanchayatId);
                        user.savePanchayat(Panchayat);
                        user.saveNicOfficeId(NicOfficeId);
                        user.saveIsApproved(IsApproved);
                        user.saveProfilePicName(ProfilePicName);
//                        Toast.makeText(this@LoginActivity, Message, Toast.LENGTH_SHORT)
//                            .show()

                        if (IsVerifiedOTP.equals("Yes")) {
                            if (IsApproved.equals("Yes")) {
                                Toast.makeText(
                                    this@LoginActivity,
                                    "Login Succes",
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                                val inttent = Intent(this@LoginActivity, MainActivity.javaClass)
                                startActivity(inttent)
                            } else {
                                loginverificationdialog(EmailId);
                            }

                        } else {
                            otpdialog(MobileNumber)
                        }


                    } else if (Code.equals(-1)) {
                        Toast.makeText(this@LoginActivity, Message, Toast.LENGTH_SHORT).show()
                    }

                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        })
    }

    fun otpdialog(MobileNumber: String) {
        val dialogs = Dialog(this@LoginActivity)
        dialogs.setContentView(R.layout.dialog_verification)
        dialogs.window!!.setLayout(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        dialogs.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val lay_rg_yes = dialogs.findViewById(R.id.lay_rg_yes) as RelativeLayout
        val lay_reportno_rg = dialogs.findViewById(R.id.lay_reportno_rg) as RelativeLayout

        val et_otp = dialogs.findViewById(R.id.et_otp) as EditText
        val iv_dismis = dialogs.findViewById(R.id.iv_dismis) as ImageView
        iv_dismis.setOnClickListener {
            dialogs.dismiss()
        }
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
                                Toast.makeText(this@LoginActivity, Message, Toast.LENGTH_SHORT)
                                    .show()
                            } else if (Code.equals(-1)) {
                                Toast.makeText(this@LoginActivity, Message, Toast.LENGTH_SHORT)
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
                            this@LoginActivity,
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
                    this@LoginActivity,
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
                                this@LoginActivity,
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
                        this@LoginActivity,
                        "Unable to Connect Server",
                        Toast.LENGTH_SHORT
                    ).show()
                    Log.e("ERROR", t.toString())
                }
            })
        }




        dialogs.show()
    }

    fun registrationinfodialog() {
        val dialogs = Dialog(this@LoginActivity)
        dialogs.setContentView(R.layout.dialog_registrationinfo)
        dialogs.window!!.setLayout(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        dialogs.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val btn_ok = dialogs.findViewById(R.id.btn_ok) as Button
        btn_ok.setOnClickListener {
            val intent = Intent(this@LoginActivity, LoginActivity::class.java)
            startActivity(intent)
            dialogs.dismiss()
        }

        dialogs.show()
    }

    fun forgotpassworddialog() {

        val dialogs = Dialog(this@LoginActivity)
        dialogs.setContentView(R.layout.dialog_forgotpassword)
        dialogs.window!!.setLayout(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        dialogs.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val lay_sendotp_rg = dialogs.findViewById(R.id.lay_sendotp_rg) as RelativeLayout
        val et_otp_forgotpassword = dialogs.findViewById(R.id.et_otp_forgotpassword) as EditText
        val lay_rg_yes = dialogs.findViewById(R.id.lay_rg_yes) as RelativeLayout
        val ll_otpverified = dialogs.findViewById(R.id.ll_otpverified) as LinearLayout
        val ll_password = dialogs.findViewById(R.id.ll_password) as LinearLayout
        val lay_changepassword = dialogs.findViewById(R.id.lay_changepassword) as RelativeLayout
        val et_password = dialogs.findViewById(R.id.et_password) as EditText
        val et_mobilenumber = dialogs.findViewById(R.id.et_mobilenumber) as EditText
        val et_confirmpassword = dialogs.findViewById(R.id.et_confirmpassword_foegor) as EditText
        val lay_reportno_rg = dialogs.findViewById(R.id.lay_reportno_rg) as RelativeLayout
        val tv_countdowntimer=dialogs.findViewById(R.id.tv_countdowntimer) as CustomTextView
        dialogs.et_otp_forgotpassword.addTextChangedListener(object : TextWatcher {
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
                    if (et_otp_forgotpassword.getText() != null) {
                        otpAuto = et_otp_forgotpassword.getText().toString()
                    }
                }
            }
        })
        object : CountDownTimer(50000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                tv_countdowntimer.setText(counter.toString())
                counter++
            }

            override fun onFinish() {
                tv_countdowntimer.setText("Finished")
            }
        }.start()

        lay_rg_yes.setOnClickListener {
            if (et_otp_forgotpassword.text.toString().isEmpty()) {
                et_otp_forgotpassword.error = "Please Enter Mobile OTP"
            } else {
                if (et_otp_forgotpassword.text.toString().trim().equals(OTP)) {
                    Log.d("MOBILEOTP", et_otp_forgotpassword.text.toString())
                    ll_otpverified.visibility = View.GONE
                    ll_password.visibility = View.VISIBLE
                    et_otp_forgotpassword.isEnabled = false
                    Toast.makeText(this@LoginActivity, "welcome", Toast.LENGTH_SHORT).show()
                }
            }


        }
        lay_changepassword.setOnClickListener {
            if (et_password.text.toString().isEmpty()) {
                et_password.error = "Please Enter Password"

            } else if (!isValidPassword((et_password.text.toString()!!))) {
                et_password.error =
                    "Password must contain mix of upper and lower case letters as well as digits and one special character(8-15)"
            } else if (et_confirmpassword.text.toString().isEmpty()) {
                et_confirmpassword.error = "Please Enter Confirm Password"
            } else if (!et_password.text.toString().equals(et_confirmpassword.text.toString())) {
                et_confirmpassword.error = "Passwords Don't Match !"
            } else {

                val reqObject = JSONObject()
                reqObject.put("MobileNo", et_mobilenumber!!.text.toString())
                reqObject.put("Password", et_password!!.text.toString())
                Log.e("RequestBody", "Request Body:- ${reqObject}")
                val mediaType = MediaType.parse("application/json");
                val requestBody = RequestBody.create(mediaType, reqObject.toString())
                val callBcak = WebServiceClass.callWebServices().ForgotPassword(requestBody)
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
                                val intent = Intent(this@LoginActivity, LoginActivity::class.java)
                                startActivity(intent)
                                dialogs.dismiss()
                                Toast.makeText(this@LoginActivity, Message, Toast.LENGTH_SHORT)
                                    .show()
                            } else if (Code.equals(-1)) {
                                Toast.makeText(this@LoginActivity, Message, Toast.LENGTH_SHORT)
                                    .show()
                            }

                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                })
            }

        }
        lay_sendotp_rg.setOnClickListener {
            if (et_mobilenumber.text.isEmpty()) {
                et_mobilenumber.setError("Please Enter Mobile Number")
            } else {


                val reqObject = JSONObject()
                reqObject.put("MobileNo", et_mobilenumber!!.text.toString())

                Log.e("RequestBody", "Request Body:- ${reqObject}")
                val mediaType = MediaType.parse("application/json");
                val requestBody = RequestBody.create(mediaType, reqObject.toString())
                val callBcak = WebServiceClass.callWebServices().CheckMobileNo(requestBody)
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
                                lay_sendotp_rg.visibility = View.GONE
                                dialogs.ll_mobileotp.visibility = View.VISIBLE
                                et_mobilenumber.isEnabled = false

                                val call: Call<String> =
                                    WebServiceClass.callWebServicesverficationotp()
                                        .sentOTP("8520972368")
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
                                                val jsonObject =
                                                    JSONObject(response.body().toString())
                                                val responseMsg =
                                                    jsonObject.getString("responseMsg")
                                                Toast.makeText(
                                                    this@LoginActivity,
                                                    "${responseMsg}",
                                                    Toast.LENGTH_LONG
                                                )
                                                    .show()
                                                response.body()
                                                OTP = jsonObject.getString("otp")
                                                SmsReceiver.bindListener(object : SmsListener {
                                                    override fun messageReceived(id: String) {
                                                        otpAuto = id
                                                        dialogs.et_otp_forgotpassword.setText(
                                                            otpAuto
                                                        )


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
                                            this@LoginActivity,
                                            "Unable to Connect Server",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        Log.e("ERROR", t.toString())
                                    }
                                })

                            } else if (Code.equals(-1)) {
                                Toast.makeText(this@LoginActivity, Message, Toast.LENGTH_SHORT)
                                    .show()
                            }

                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                })
            }


        }
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
                                this@LoginActivity,
                                "${responseMsg}",
                                Toast.LENGTH_LONG
                            )
                                .show()
                            response.body()
                            OTP = jsonObject.getString("otp")
                            SmsReceiver.bindListener(object : SmsListener {
                                override fun messageReceived(id: String) {
                                    otpAuto = id
                                    dialogs.et_otp_forgotpassword.setText(otpAuto)


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
                        this@LoginActivity,
                        "Unable to Connect Server",
                        Toast.LENGTH_SHORT
                    ).show()
                    Log.e("ERROR", t.toString())
                }
            })
        }
        dialogs.show()
    }

    fun isValidPassword(password: String): Boolean {
        val matcher = Pattern.compile("((?=.*[a-z])(?=.*\\d)(?=.*[A-Z])(?=.*[@#$%!]).{4,20})")
            .matcher(password)
        return matcher.matches()
    }

    fun loginverificationdialog(EmailID: String) {
        val dialogs = Dialog(this@LoginActivity)
        dialogs.setContentView(R.layout.dialog_loginerror)
        dialogs.window!!.setLayout(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        dialogs.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val bt_okay = dialogs.findViewById(R.id.bt_okay) as Button
        dialogs.userid.setText(EmailID)

        bt_okay.setOnClickListener {
            //            SaveSharedPreference.getSessionDataInstance().clearAll()

            dialogs.dismiss()
        }
        dialogs.show()
    }
}
