package com.krp.whoknows.Auth.WelcomeScreen.presentation

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.krp.whoknows.R
import com.krp.whoknows.roomdb.JWTViewModel
import com.krp.whoknows.ui.theme.ordColor
import com.krp.whoknows.ui.theme.splashScreenColor
import kotlinx.coroutines.flow.StateFlow
import org.koin.java.KoinJavaComponent.inject

/**
 * Created by KUSHAL RAJ PAREEK on 27,January,2025
 */

@SuppressLint("ResourceAsColor", "StateFlowValueCalledInComposition")
@Composable
fun WelcomeScreen(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(splashScreenColor)
    ) {
        Canvas(modifier = Modifier.size(1400.dp), onDraw = {
            translate(left = 1130f, top = -900f) {
                drawCircle(color = ordColor, radius = 1200f)
            }
        })

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 25.dp),
        ) {
            Row(
                modifier = Modifier
                    .padding(start = 8.dp, top = 30.dp)
            ) {
                Text(
                    text = "Who",
                    color = Color.Black,
                    fontWeight = FontWeight.Normal,
                    fontFamily = FontFamily(Font(R.font.pompiere)),
                    fontSize = 70.sp,
                    modifier = Modifier.padding(start = 0.dp)
                )
                Spacer(modifier = Modifier.width(2.dp))
                Text(
                    text = "Knows",
                    color = Color.Black,
                    fontWeight = FontWeight.Normal,
                    fontFamily = FontFamily(Font(R.font.pompiere)),
                    fontSize = 70.sp,
                    modifier = Modifier.padding(start = 0.dp, top = 60.dp)
                )
            }
            Button(
                modifier = modifier
                    .padding(top = 50.dp)
                    .align(Alignment.CenterHorizontally),
                colors = ButtonDefaults.buttonColors(
                    containerColor = ordColor
                ), onClick = { onClick() },
                shape = RoundedCornerShape(10.dp)
            ) {
                Text(
                    text = "Welcome",
                    fontSize = 25.sp,
                    color = Color.Black
                )
            }
        }
    }
}