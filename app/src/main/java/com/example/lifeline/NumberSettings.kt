package com.example.lifeline
import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.ContentValues
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import com.example.lifeline.ui.theme.LifeLineTheme
class NumberSettings : ComponentActivity() {


    // backend: activate if needed
    private var contactnum by mutableStateOf("")
    private var contactname by mutableStateOf("")

    @SuppressLint("Range")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // backend: activate if needed
        // Request the permission to read contacts.
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_CONTACTS), 1)
        setContent {
            Image(
                painter = painterResource(id = R.drawable.nyellow1), // Replace with your image resource
                contentDescription = null, // Provide a description for accessibility
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.fillMaxSize()
            )
            LifeLineTheme {
                // backend: activate if needed
                val db: SQLiteDatabase = openOrCreateDatabase("ContactDB", MODE_PRIVATE, null)
                db.execSQL("CREATE TABLE IF NOT EXISTS contacts(ID INTEGER PRIMARY KEY AUTOINCREMENT, Name TEXT, PhoneNum TEXT);")

                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Top
                ) {
                    Button(
                        colors = ButtonDefaults.outlinedButtonColors(Color.Black),
                        onClick = {
                            // backend: activate if needed
                            val goBack = Intent(this@NumberSettings, MainActivity::class.java)
                            startActivity(goBack)
                            finish()
                        },
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text("X",color=Color.Yellow)
                    }
                }

                Column(

                    modifier = Modifier
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                )
                {
                    Spacer(modifier = Modifier.padding(50.dp))
                    Box(
                        modifier = Modifier.padding(top =30.dp)
                    ){
                        Button(
                            colors = ButtonDefaults.outlinedButtonColors(Color.Black),
                            onClick = {
                                // backend: activate if needed
                                val intent = Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI)
                                startActivityForResult(intent, 1)

                            },
                            modifier = Modifier
                                .width(200.dp)
                        ) {
                            Text("Select a contact",color = Color.White)
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                    // frontend: with conditional execution
                    if (contactnum.isNotEmpty())
                    {
                        Text("Selected Phone Number: $contactnum")
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                    // frontend: with conditional execution
                    if (contactname.isNotEmpty())
                    {
                        Text("Name of Selected Number: $contactname")
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                    val context= LocalContext.current
                    Box(
                        modifier = Modifier.padding(top =30.dp)
                    ) {
                        Button(
                            colors = ButtonDefaults.outlinedButtonColors(Color.Black),
                            onClick = {
                                val values = ContentValues()
                                if (contactname.isNotEmpty() && contactnum.isNotEmpty())
                                {
                                    values.put("Name", contactname)
                                    values.put("PhoneNum", contactnum)
                                    val newRowId = db.insert("contacts", null, values)
                                    when {
                                        newRowId != -1L -> {
                                            Toast.makeText(context,"Saved", Toast.LENGTH_SHORT).show()
                                        }
                                        else -> {
                                            Toast.makeText(context,"failed to save contact!\nplease try later", Toast.LENGTH_SHORT).show()
                                        }
                                    }
                                    contactname = ""
                                    contactnum = ""
                                }
                                else
                                {
                                    Toast.makeText(context,"Please first select a contact", Toast.LENGTH_SHORT).show()
                                }

                            },
                            modifier = Modifier.width(200.dp)
                        ) {
                            Text("Save the contact",color = Color.White)
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                    // backend: activate if needed
                    var databaseRead by remember { mutableStateOf("") }
                    Box(
                        modifier = Modifier.padding(top =30.dp)
                    ) {
                        Button(
                            colors = ButtonDefaults.outlinedButtonColors(Color.Black),
                            onClick = {
                                // backend: activate if needed
                                databaseRead = ""
                                // // Create a cursor to query the database
                                val cursor = db.rawQuery("SELECT * FROM contacts", null)

                                // // Iterate through the cursor and store the data in a string

                                if (cursor.moveToFirst()) {
                                    do {
                                        databaseRead += "${cursor.getString(1)} : ${
                                            cursor.getString(
                                                2
                                            )
                                        }\n"
                                    } while (cursor.moveToNext())
                                }

                                // // Close the cursor
                                cursor.close()
                                showMessage("These people will be contacted", databaseRead)

                            },
                            modifier = Modifier.width(200.dp)
                        ) {
                            Text("See saved contacts", color = Color.White)
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // frontend: conditional behaviour



                }
            }
        }

    }

    // backend: activate if needed
    @SuppressLint("Range")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            val contactUri = data?.data

            //         // Get the selected contacts.
            val cursor = contactUri?.let { contentResolver.query(it, null, null, null, null) }

            //         // Iterate over the contacts.
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    // Get the contact's name.
                    val name = cursor?.let { cursor.getString(it.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)) }

                    val contentResolver = contentResolver
                    val uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI
                    val projection = arrayOf(
                        ContactsContract.CommonDataKinds.Phone.NUMBER,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID
                    )
                    val selection = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " = ?"
                    val selectionArgs = arrayOf(name)
                    val cursor =
                        contentResolver.query(uri, projection, selection, selectionArgs, null)

                    if (cursor != null) {
                        while (cursor.moveToNext()) {
                            val number =
                                cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                            val contactId =
                                cursor.getInt(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID))
                            // Do something with the contact number and contact ID.
                            contactnum = number
                        }
                        cursor.close()
                    }
                    // Do something with the contact's name and phone number.
                    if (name != null) {
                        contactname = name
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