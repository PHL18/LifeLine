package com.example.lifeline

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lifeline.ui.theme.LifeLineTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Semicircle()
            //BottomCenteredReducedRoundedRectangle()
            Image(
                painter = painterResource(id = R.drawable.welcomescreen), // Replace with your image resource
                contentDescription = null, // Provide a description for accessibility
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.fillMaxSize()
            )
            LifeLineTheme {
                Column(
                    modifier = Modifier.padding(80.dp)
                ) {

                }

                Text("hello world")
                Text("Hello hello")

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {

                        }
                        Box{

                        }
                        Column (
                            modifier=Modifier
                                .padding(10.dp),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally

                        ){
                            Spacer(modifier = Modifier.height(560.dp))


                            Button(
                                colors = ButtonDefaults.outlinedButtonColors(Color(0xFFFFCC00)),
                                onClick = {
                                    // backend: activate if needed
                                    val intent = Intent(this@MainActivity, SMSsettings::class.java)
                                    startActivity(intent)
                                    finish()
                                },
                                modifier = Modifier
                                    .padding(16.dp)
                                    .fillMaxWidth(0.7f)
                                    .padding(5.dp)

                            ) {
                                Text("SMS settings", color = Color.DarkGray, fontSize = 16.sp,fontWeight = FontWeight.Bold)
                            }
                            Button(
                                colors = ButtonDefaults.outlinedButtonColors(Color(0xFFFFCC00)),
                                onClick = {
                                    // backend: activate if needed
                                    val intent = Intent(this@MainActivity, NumberSettings::class.java)
                                    startActivity(intent)
                                    finish()
                                },
                                modifier = Modifier
                                    .padding(2.dp)
                                    .fillMaxWidth(0.7f)
                                    .padding(6.dp)



                            ) {
                                Text("Number Settings", color = Color.DarkGray, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                            }

                            Spacer(modifier = Modifier.height(32.dp))
                            Spacer(modifier = Modifier.height(32.dp))
                            Spacer(modifier = Modifier.height(32.dp))

                        }

//
                    }
                }
            }
        }
    }
}