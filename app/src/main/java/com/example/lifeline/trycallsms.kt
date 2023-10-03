package com.example.lifeline

import android.Manifest
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.database.sqlite.SQLiteDatabase
import android.net.Uri
import android.os.Bundle
import android.telephony.SmsManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.lifeline.ui.theme.LifeLineTheme

class trycallsms : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LifeLineTheme {
                checkCallPermissions()

                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Top
                ) {
                    Button(
                        colors = ButtonDefaults.outlinedButtonColors(Color.Black),
                        onClick = {
                            // backend: activate if needed
                            val goBack = Intent(this@trycallsms, MainActivity::class.java)
                            startActivity(goBack)
                            finish()
                        },
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text("X",color= Color.Yellow)
                    }
                }

                Column(
                    modifier= Modifier
                        .padding(10.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                   Button(
                       onClick = {

                           if (checkCallPermissions()) {
                               // placeCall("+917057603762")
                                sendSMStoAll()

                           }


                   }) {
                       Text("Family")
                   }
                    Button(onClick = {

                        if (checkCallPermissions()) {
                            //placeCall("+919673093300")
                            sendSMStoAll()
                        }
                    }) {
                        Text("Police")
                    }
                    Button(
                        onClick = {

                            if (checkCallPermissions()) {
                                // placeCall("+919673083300")
                                sendSMStoAll()
                            }
                        }
                    ) {
                        Text("Ambulance")
                    }
                }
            }
        }
    }

    private fun showMessage(title: String, message: String) {
        val builder = AlertDialog.Builder(this)
        builder.setCancelable(true)
        builder.setTitle(title)
        builder.setMessage(message)
        builder.show()
    }

    private fun sendSMStoAll()
    {
        val db: SQLiteDatabase = openOrCreateDatabase("ContactDB", MODE_PRIVATE, null)
        var message = ""

        val cursor1 = db.rawQuery("SELECT SMS FROM sms WHERE rowid = 1;", null)
        message = if (cursor1.moveToFirst()) {
            cursor1.getString(0).ifEmpty {
                "Hello! please help! your contact is in emergency!"
            }
        } else {
            "Hello! please help! your contact is in emergency!"
        }
       cursor1.close()
        val cursor = db.rawQuery("SELECT * FROM contacts", null)

        // // Iterate through the cursor and store the data in a string

        if (cursor.moveToFirst()) {
            do {
                val contact = "${cursor.getString(2)}"
                val newmessage = "Hello ${cursor.getString(1)}\n" + message
                sendSMS(contact, newmessage)
            } while (cursor.moveToNext())

        }

        // // Close the cursor
        cursor.close()
   }

    private fun sendSMS(phoneNumber: String, message: String) {
        val smsManager = SmsManager.getDefault()
        smsManager.sendTextMessage(phoneNumber, null, message, null, null)
    }

    private fun placeCall(phoneNumber: String) {
        val callIntent = Intent(Intent.ACTION_CALL)
        callIntent.data = Uri.parse("tel:$phoneNumber")

        try {
            startActivity(callIntent)
        } catch (e: SecurityException) {
            showMessage("error", "couldn't place call")
        }
    }

    private fun checkCallPermissions(): Boolean {
        val permissions = arrayOf(
            Manifest.permission.SEND_SMS,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.CALL_PHONE
        )

        for (permission in permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, permissions, 1)
                return false
            }
        }
        return true
    }

}
