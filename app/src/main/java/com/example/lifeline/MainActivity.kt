package com.example.lifeline

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lifeline.ui.theme.LifeLineTheme
import androidx.compose.ui.unit.Dp
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val db: SQLiteDatabase = openOrCreateDatabase("ContactDB", MODE_PRIVATE, null)
        db.execSQL("CREATE TABLE IF NOT EXISTS sms(SMS VARCHAR(160));")
        db.execSQL("CREATE TABLE IF NOT EXISTS contacts(ID INTEGER PRIMARY KEY AUTOINCREMENT, Name TEXT, PhoneNum TEXT);")
        setContent {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color= Color.Black
                ) {
                    LifeLineTheme {
                        Image(
                            painter = painterResource(id = R.drawable.android_large_6_vector2),
                            contentDescription = null,
                            modifier = Modifier
                                .scale(0.17f)
                                .offset(y = (-905).dp) // Adjust the offset value as needed to move it upward
                        )
                        Image(
                            painter = painterResource(id = R.drawable.android_large_6_ellipse_32),
                            contentDescription = null,
                            modifier = Modifier
                                .scale(0.5f)
                                .offset(y = (-340).dp) // Adjust the offset value as needed to move it upward
                        )
                        Column(
                            modifier = Modifier
                                .padding(60.dp,top=90.dp),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.Start
                        ) {
                            Text(text = "Lifeline", color = Color.White, fontSize = 40.sp)
                        }
                        Column (
                            modifier = Modifier.padding(140.dp,top=265.dp),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.Start
                        ){
                            Text(text = "Try Messages", color = Color.White, fontSize = 21.sp)
                        }
                        Column (
                            modifier = Modifier.padding(140.dp,top=427.dp),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.Start
                        ){
                            Text(text = "Message manager", color = Color.White, fontSize = 21.sp)
                        }
                        Column (
                            modifier = Modifier.padding(140.dp,top=590.dp),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.Start
                        ){
                            Text(text = "Contacts manager", color = Color.White, fontSize = 21.sp)
                        }
                        Column(
                            modifier = Modifier.padding(60.dp)
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
                                            .padding(50.dp),
                                        verticalArrangement = Arrangement.Center,
                                        horizontalAlignment = Alignment.CenterHorizontally

                                    ){
                                        Spacer(modifier = Modifier.height(150.dp))
                                        Spacer(modifier = Modifier.height(300.dp))
                                        FigmaButton(
                                            buttonAsset = painterResource(id = R.drawable.android_large_6_rectangle_12),
                                            buttonText = " ",
                                            onClick = {
                                                val intent = Intent(this@MainActivity, trycallsms::class.java)
                                                startActivity(intent)
                                                finish()
                                            },
                                            vectorAsset = painterResource(id = R.drawable.android_large_6_vector4), // Replace with your vector drawable
                                            vectorSize = 40.dp
                                        )

                                        Spacer(modifier = Modifier.height(16.dp))

                                        FigmaButton(
                                            buttonAsset = painterResource(id = R.drawable.android_large_6_rectangle_13),
                                            buttonText = " ",
                                            onClick = {
                                                // backend: activate if needed
                                                val intent = Intent(this@MainActivity, SMSsettings::class.java)
                                                startActivity(intent)
                                                finish()
                                            },
                                            vectorAsset = painterResource(id = R.drawable.android_large_6_vector3), // Replace with your vector drawable
                                            vectorSize = 40.dp


                                        )
                                        Spacer(modifier = Modifier.height(16.dp))

                                        FigmaButton(
                                            buttonAsset= painterResource(id = R.drawable.android_large_6_rectangle_14),
                                            buttonText = " ",
                                            onClick = {
                                                // backend: activate if needed
                                                val intent = Intent(this@MainActivity, NumberSettings::class.java)
                                                startActivity(intent)
                                                finish()
                                            },
                                            vectorAsset = painterResource(id = R.drawable.group_8_vector), // Replace with your vector drawable
                                            vectorSize = 40.dp



                                        )

                                        Spacer(modifier = Modifier.height(32.dp))



                                    }

                                }

//
                            }
                        }
                    }
                }
        }
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
                .size(64.dp)
                .then(modifier)
                .background(Color.Black)
        ) {
            Image(
                painter = buttonAsset,
                contentDescription = null,
                modifier = Modifier.fillMaxSize()
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
