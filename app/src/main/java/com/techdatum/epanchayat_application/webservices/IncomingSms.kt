package com.techdatum.epanchayat_application.webservices

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.SmsManager
import android.telephony.SmsMessage
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import java.util.regex.Pattern


class IncomingSms : BroadcastReceiver() {
    val sms = SmsManager.getDefault()
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == "android.provider.Telephony.SMS_RECEIVED") {
            val bundle = intent.extras
            var msgs: Array<SmsMessage?>? = null
            var msg_from: String?
            if (bundle != null) {
                try {
                    val pdus =
                        bundle["pdus"] as Array<Any>?
                    msgs = arrayOfNulls(pdus!!.size)
                    for (i in msgs.indices) {
                        msgs[i] =
                            SmsMessage.createFromPdu(pdus[i] as ByteArray)
                        msg_from = msgs[i]!!.getOriginatingAddress()
                        val msgBody = msgs[i]!!.getMessageBody()
                        val generalOtpPattern =
                            Pattern.compile(GENERAL_OTP_TEMPLATE)
                        val generalOtpMatcher =
                            generalOtpPattern.matcher(msgs[i]!!.getMessageBody().toString())
                        if (generalOtpMatcher.find()) {
                            val otp = generalOtpMatcher.group(1)
                            val myIntent = Intent("otp")
                            myIntent.putExtra("message", otp)
                            LocalBroadcastManager.getInstance(context).sendBroadcast(myIntent)
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    companion object {
        var GENERAL_OTP_TEMPLATE = "Dear User, (.*)."
    }
}