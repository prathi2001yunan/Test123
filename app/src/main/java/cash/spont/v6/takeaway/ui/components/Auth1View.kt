package cash.spont.v6.takeaway.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cash.spont.v6.tvtakeaway.R.drawable
import cash.spont.v6.takeaway.ui.theme.appTheme
import cash.spont.v6.takeaway.ui.theme.navigationBarPadding
import cash.spont.v6.takeaway.ui.theme.statusBarPadding


import cash.spont.v6.tvtakeaway.R

@Composable
fun Auth1View(onClick: () -> Unit) {
    var height = 0.0f
    var width = 0.0f
    var buttonHeight = 0.0f
    var buttonWidth = 0.0f


    val configuration = LocalConfiguration.current
    when (configuration.orientation) {
        Configuration.ORIENTATION_LANDSCAPE -> {
            height = 1f
            width = 1f
            buttonHeight = 0.14f
            buttonWidth = 0.35f
        }

        else -> {
            height = 1f
            width = 1f
            buttonHeight = 0.11f
            buttonWidth = 0.45f
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(appTheme {})
            .padding(
                top = MaterialTheme.statusBarPadding(),
                bottom = MaterialTheme.navigationBarPadding()
            ),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier = Modifier
                .fillMaxHeight(height)
                .fillMaxWidth(width),
            shape = RoundedCornerShape(15.dp, 15.dp, 15.dp, 15.dp),
            colors = if (isSystemInDarkTheme()) {
                CardDefaults.cardColors(Color(0xFF212127))
            } else {
                CardDefaults.cardColors(Color(0x63BBBBBD))
            }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(top = 30.dp),
                    Arrangement.SpaceBetween,
                    Alignment.CenterVertically
                ) {
                    Auth1ViewLogo()
                }
                Spacer(modifier = Modifier.fillMaxHeight(0.1f))
                Auth1ViewDescription()
                Spacer(modifier = Modifier.fillMaxHeight(0.1f))
                Auth1ViewImage(buttonWidth, buttonHeight, onClick)
            }
        }
    }
}

@Composable
private fun Auth1ViewImage(buttonWidth: Float, buttonHeight: Float, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.authstep1),
            contentDescription = "",
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .fillMaxSize()
        )
        Column(
            Modifier.fillMaxSize(),
            Arrangement.Bottom,
            Alignment.CenterHorizontally
        ) {
            Auth1ViewButton(buttonWidth, buttonHeight, onClick = onClick)
            Spacer(modifier = Modifier.fillMaxHeight(0.3f))
        }
    }
}

@Composable
private fun Auth1ViewLogo() {
    val icon = if (isSystemInDarkTheme()) {
        drawable.spont_white
    } else {
        drawable.spont_dark
    }
    Icon(
        painter = painterResource(id = icon),
        contentDescription = "",
        modifier = Modifier
            .fillMaxWidth()
            .height(
                if (isSystemInDarkTheme()) 28.dp else
                    40.dp
            )
            .padding(horizontal = 100.dp)
    )
}

@Composable
private fun Auth1ViewDescription() {
    Text(
        "Welkom,lets activate your new device by scanning your QR code",
        fontSize = 25.sp,
        textAlign = TextAlign.Center,
        lineHeight = 25.sp,
        fontWeight = FontWeight.ExtraBold,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 100.dp)
    )
}

@Composable
fun Auth1ViewButton(buttonWidth: Float, buttonHeight: Float, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth(buttonWidth)
            .fillMaxHeight(buttonHeight),
        colors = ButtonDefaults.buttonColors(Color(0xFF4353FF))
    ) {
        Text(
            text = "Scan and Activate", color = Color.White, fontSize = 22.sp,
            modifier = Modifier.fillMaxSize(),
            textAlign = TextAlign.Center
        )
    }
}
