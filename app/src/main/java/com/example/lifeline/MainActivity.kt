package com.example.lifeline

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import com.example.lifeline.ui.theme.LifeLineTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            // Semicircle()
            //BottomCenteredReducedRoundedRectangle()
            Image(
                painter = painterResource(id = R.drawable.yellow3), // Replace with your image resource
                contentDescription = null, // Provide a description for accessibility
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.fillMaxSize()
            )
            LifeLineTheme {
                Column(
                    modifier = Modifier.padding(80.dp)
                ) {

                }


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
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Start

                        ) {
                            Column (
                                modifier=Modifier
                                    .padding(10.dp),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally

                            ){
                                Spacer(modifier = Modifier.height(560.dp))
                                Spacer(modifier = Modifier.height(70.dp))




                                Button(
                                    colors = ButtonDefaults.outlinedButtonColors(Color.Black),
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
                                    Text("Message Manager", color = Color.White, fontSize = 16.sp,fontWeight = FontWeight.Bold)
                                }
                                Button(
                                    colors = ButtonDefaults.outlinedButtonColors(Color.Black),
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
                                    Text("Number Settings", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                                }

                                Spacer(modifier = Modifier.height(32.dp))


                            }

                        }

//
                    }
                }
            }
        }
    }


//   @Composable
//   fun BottomCenteredReducedRoundedRectangle() {
//        Box(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(16.dp),
//            contentAlignment = Alignment.BottomCenter // Align the content (rectangle) to the bottom center
//        ) {
//            Box(
//                modifier = Modifier
//                    .size(500.dp, 600.dp)
//                    .background(
//                        color = Color.LightGray,
//                        shape = RoundedCornerShape(16.dp)
//                    )
//            ) {
//                // Content inside the rounded rectangle, if needed
//            }
//        }
//    }
//@Composable
//fun Semicircle() {
//    Box(
//        modifier = Modifier
//            .fillMaxWidth()
//            .aspectRatio(1f / 2f) // Adjust the aspect ratio to control the size of the semicircle
//    ) {
//        Box(
//            modifier = Modifier
//                .fillMaxSize()
//                .background(color = Color.LightGray) // Background color of the full rectangle
//        )
//        Box(
//            modifier = Modifier
//                .fillMaxSize()
//                .clip(shape = CircleShape)
//                .background(color = Color.Blue) // Background color of the circle
//        )
//    }
//}

}
