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
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
                    modifier = Modifier.fillMaxSize()
                        .background(color = Color.Black),
                    verticalArrangement = Arrangement.Top
                ) {
                    Button(
                        colors = ButtonDefaults.outlinedButtonColors(Color.White),
                        onClick = {
                            // backend: activate if needed
                            val goBack = Intent(this@trycallsms, MainActivity::class.java)
                            startActivity(goBack)
                            finish()
                        },
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text("X",color= Color.Black)
                    }
                }
                Column (
                    modifier = Modifier.padding(165.dp,top=279.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.Start
                ){
                    Text(text = "Family", color = Color.White, fontSize = 21.sp)
                }
                Column (
                    modifier = Modifier.padding(165.dp,top=367.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.Start
                ){
                    Text(text = "Police", color = Color.White, fontSize = 21.sp)
                }
                Column (
                    modifier = Modifier.padding(165.dp,top=460.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.Start
                ){
                    Text(text = "Ambulance", color = Color.White, fontSize = 21.sp)
                }

                Column(
                    modifier= Modifier
                        .padding(60.dp,top=250.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                   FigmaButton(
                       buttonAsset= painterResource(id = R.drawable.android_large_6_rectangle_14),
                       buttonText = " ",
                       onClick = {

                           if (checkCallPermissions()) {
                               // placeCall("+917057603762")
                                sendSMStoAll()

                           }


                   },
                       vectorAsset = painterResource(id = R.drawable._icon_family_vector), // Replace with your vector drawable
                       vectorSize = 40.dp
                   )
                    Spacer(modifier = Modifier.padding(7.dp))
                    FigmaButton(
                        buttonAsset= painterResource(id = R.drawable.android_large_6_rectangle_12),
                        buttonText = " ",
                        onClick = {

                        if (checkCallPermissions()) {
                            //placeCall("+919673093300")
                            sendSMStoAll()
                        }
                    },
                        vectorAsset = painterResource(id = R.drawable.mdi_police_badge_outline_vector), // Replace with your vector drawable
                        vectorSize = 40.dp
                    )
                    Spacer(modifier = Modifier.padding(7.dp))
                    FigmaButton(
                        buttonAsset = painterResource(id = R.drawable.android_large_6_rectangle_13),
                        buttonText = " ",
                        onClick = {

                            if (checkCallPermissions()) {
                                // placeCall("+919673083300")
                                sendSMStoAll()
                            }
                        }
                    )
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
    @Composable
    fun FigmaButton(
        buttonAsset: Painter,
        buttonText: String,
        onClick: () -> Unit,
        vectorAsset: Painter? = null,
        vectorSize: Dp? = null, // Add an optional parameter for the vector size
        modifier: Modifier = Modifier
    ) {
        Box(
            modifier = Modifier
                .clickable { onClick() }
                .size(80.dp)
                .then(modifier)

        ) {
            Image(
                painter = buttonAsset,
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
            )

            vectorAsset?.let {
                val vectorModifier = vectorSize?.let { Modifier.size(it) } ?: Modifier
                Image(
                    painter = it,
                    contentDescription = null,
                    modifier = vectorModifier.then(Modifier.align(Alignment.Center))
                )
            }

            Text(
                text = buttonText,
                color = Color.White,
                modifier = Modifier.align(Alignment.Center)
            )
        }
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

