package com.example.lifeline

import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.lifeline.ui.theme.LifeLineTheme

class SMSsettings : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Image(
                painter = painterResource(id = R.drawable.smsyellow), // Replace with your image resource
                contentDescription = null, // Provide a description for accessibility
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.fillMaxSize()
            )
            LifeLineTheme {
                // backend: activate if needed
                val db: SQLiteDatabase = openOrCreateDatabase("ContactDB", MODE_PRIVATE, null)
                db.execSQL("CREATE TABLE IF NOT EXISTS sms(SMS VARCHAR(160));")
                var message by remember { mutableStateOf("") }

                val cursor1 = db.rawQuery("SELECT SMS FROM sms WHERE rowid = 1;", null)
                message = if (cursor1.moveToFirst()) {
                    cursor1.getString(0).ifEmpty {
                        ""
                    }
                } else {
                    ""
                }
                var existingMessage by remember { mutableStateOf(message) }
                cursor1.close()

                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Top
                ) {
                    Button(
                        colors = ButtonDefaults.outlinedButtonColors(Color.Black),
                        onClick = {

                            // backend: activate if needed
                            val goBack = Intent(this@SMSsettings, MainActivity::class.java)
                            startActivity(goBack)
                            finish()
                        },
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(text = "X", color=Color.White)
                    }
                }

                Column (
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    // Input Text Box
                    Spacer(modifier = Modifier.height(280.dp))
                    TextField(

                        value = existingMessage,
                        onValueChange = { existingMessage = it },
                        // conditional behaviour in frontend
                        label = { Text(if (existingMessage.isNotEmpty()) "Change the SMS" else "Enter an SMS") },
                        modifier = Modifier
                            .padding(16.dp),

                        )

                    Column{
                        val context= LocalContext.current
                        Button(
                            colors = ButtonDefaults.outlinedButtonColors(Color(0xFFFFCC00)),
                            onClick = {
                                Toast.makeText(context,"saved",Toast.LENGTH_SHORT).show()
                                // backend: activate if needed
                                val cursor = db.rawQuery("SELECT SMS FROM sms WHERE rowid = 1;", null)
                                if (cursor.moveToFirst())
                                {
                                    db.execSQL("UPDATE sms SET SMS = ? WHERE rowid = 1;", arrayOf(existingMessage))
                                }
                                else
                                {
                                    db.execSQL("INSERT INTO sms (SMS) VALUES (?)", arrayOf(existingMessage))
                                }
                                cursor.close()
                            },
                            modifier = Modifier
                                .padding(16.dp)
                                .height(48.dp)
                                .width(200.dp)


                        ) {
                            Text("Save", color = Color.DarkGray)
                        }

                        Button(
                            colors = ButtonDefaults.outlinedButtonColors(Color(0xFFFFCC00)),
                            onClick = {
                                // backend: activate if needed
                                val cursor2 = db.rawQuery("SELECT SMS FROM sms WHERE rowid = 1;", null)
                                var shownMessage = if (cursor2.moveToFirst()) {
                                    cursor2.getString(0).ifEmpty {
                                        ""
                                    }
                                } else {
                                    ""
                                }
                                shownMessage = "Hello <alias Name>\n$shownMessage\n<your location>"
                                showMessage("message sent to <mobile number>", shownMessage)
                            },
                            modifier = Modifier
                                .padding(16.dp)
                                .height(48.dp)
                                .width(200.dp)

                        ) {
                            Text("View SMS",color = Color.DarkGray)
                        }

                        Button(
                            colors = ButtonDefaults.outlinedButtonColors(Color(0xFFFFCC00)),
                            onClick = {
                                // backend: activate if needed
                            },
                            modifier = Modifier
                                .padding(16.dp)
                                .height(48.dp)
                                .width(200.dp)

                        ){
                            Text("Send", color = Color.DarkGray)
                        }
                    }

                }
            }
        }
    }

    // semibackend: not needed to be de-activated
    private fun showMessage(title: String, message: String) {
        val builder = AlertDialog.Builder(this)
        builder.setCancelable(true)
        builder.setTitle(title)
        builder.setMessage(message)
        builder.show()
    }
}
