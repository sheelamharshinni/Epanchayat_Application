package com.techdatum.epanchayat_application.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

/**
 * Created by Sonal Naidu on 23-03-2018.
 */

public class SmsReceiver extends BroadcastReceiver {

    private static SmsListener mListener;

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle data = intent.getExtras();

        Object[] pdus = (Object[]) data.get("pdus");

        for (int i = 0; i < pdus.length; i++) {
            SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) pdus[i]);

            String sender = smsMessage.getDisplayOriginatingAddress();
            //Check the sender to filter messages which we require to read

            if (sender.contains("VK-TSPRRE")) {

                String messageBody = smsMessage.getMessageBody();
                String Messagebody = messageBody.replace("Your E-Panchayat OTP", "");
                String Messagebody2 = Messagebody.replace(".Please don't share this OTP.", "");

                Log.e("message Recieved", Messagebody.replace(".Please don't share this OTP.", ""));


                //Pass the message text to interface
                mListener.messageReceived(Messagebody2);
            }
        }
    }

    public static void bindListener(SmsListener listener) {
        mListener = listener;
    }
}
