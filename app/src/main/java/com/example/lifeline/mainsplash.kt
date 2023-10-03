package com.example.lifeline

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

class Splash : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val db: SQLiteDatabase = openOrCreateDatabase("ContactDB", MODE_PRIVATE, null)
        val tableExists = doesTableExist(db, "sms")
        if (tableExists) {
            val mainact = Intent(this@Splash, MainActivity::class.java)
            startActivity(mainact)
            finish()
        }

        setContent {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = Color.White,
                contentColor = Color.Black
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center, // Center vertically
                    horizontalAlignment = Alignment.CenterHorizontally // Center horizontally
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.newyellow), // Replace with your image resource
                        contentDescription = null, // Provide a description for accessibility
                        contentScale = ContentScale.FillWidth,
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f) // Take up available space and fill the screen
                    )

                    // Add space between image and button

                    Button(
                        colors = ButtonDefaults.outlinedButtonColors(Color.Black),
                        onClick = {
                            val goBack = Intent(this@Splash, MainActivity::class.java)
                            startActivity(goBack)
                            finish()
                        },
                        modifier = Modifier
                            .width(350.dp)
                            .padding(16.dp) // Add padding for spacing
                    ) {
                        Text(text = "Get started", color = Color.White)
                    }
                }
            }
        }
    }

    fun doesTableExist(database: SQLiteDatabase, tableName: String): Boolean {
        val query = "SELECT name FROM sqlite_master WHERE type='table' AND name='$tableName'"
        val cursor = database.rawQuery(query, null)
        val tableExists = cursor.moveToFirst()
        cursor.close()
        return tableExists
    }
}
