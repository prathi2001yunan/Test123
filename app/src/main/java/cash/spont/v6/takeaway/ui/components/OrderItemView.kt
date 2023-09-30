package cash.spont.v6.takeaway.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cash.spont.v6.takeaway.ui.theme.navigationBarPadding
import cash.spont.v6.takeaway.ui.theme.statusBarPadding
import cash.spont.v6.tvtakeaway.R


@Composable
fun OrderItemView(
    alertDialog: () -> Boolean,
    onStopDialog: () -> Unit,
    onShowDialog: () -> Unit,
    onNavigate: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(
                if (isSystemInDarkTheme()) Color.Black
                else Color.White
            )
            .padding(
                top = MaterialTheme.statusBarPadding(),
                bottom = MaterialTheme.navigationBarPadding()
            ),
    ) {
        OrderPreparingTable(alertDialog, onStopDialog, onShowDialog, onNavigate)
        Column(Modifier.fillMaxHeight(), Arrangement.Center, Alignment.CenterHorizontally) {
            Divider(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(3.dp),
                color = if (isSystemInDarkTheme()) Color(0xFF3C3C3C)
                else Color(0xB09E9EA0)
            )
        }
        OrderPleaseCollectTable(alertDialog, onStopDialog, onShowDialog, onNavigate)
    }
}

@Composable
private fun OrderPreparingTable(
    alertDialog: () -> Boolean,
    onStopDialog: () -> Unit,
    onShowDialog: () -> Unit,
    onNavigate: () -> Unit
) {
    OrderItemCheck(
        text = "Preparing...",
        0.5f,
        alertDialog,
        onStopDialog,
        onShowDialog,
        onNavigate
    )
}

@Composable
private fun OrderPleaseCollectTable(
    alertDialog: () -> Boolean,
    onStopDialog: () -> Unit, onShowDialog: () -> Unit, onNavigate: () -> Unit
) {
    OrderItemCheck(
        text = "Please Collect",
        1f,
        alertDialog,
        onStopDialog,
        onShowDialog,
        onNavigate
    )
}

@Composable
private fun OrderItemList() {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .verticalScroll(rememberScrollState()),
    ) {
        val listData = mutableListOf<Int>()
        for (i in 0..20) {
            listData.add(i)
        }
        listData.forEach { i ->
            Row(
                Modifier
                    .fillMaxWidth()
                    .height(70.dp)
                    .background(
                        if (i % 2 == 0) {
                            if (isSystemInDarkTheme()) Color(0x8FBBBBBD)
                            else Color(0x63BBBBBD)
                        } else Color.Transparent
                    ),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "123",
                    modifier = Modifier
                        .height(40.dp)
                        .padding(horizontal = 50.dp),
                    fontSize = 25.sp,
                    color = if (isSystemInDarkTheme()) Color.White
                    else Color.Black,
                )
            }
        }
    }
}

@Composable
private fun OrderItemCheck(
    text: String,
    maxWidth: Float,
    alertDialog: () -> Boolean,
    onStopDialog: () -> Unit,
    onShowDialog: () -> Unit,
    onNavigate: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth(maxWidth),
        Arrangement.Top,
        Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.fillMaxHeight(0.01f))
        Row(
            Modifier
                .fillMaxWidth()
                .padding(22.dp),
            Arrangement.SpaceBetween,
            Alignment.CenterVertically
        ) {
            Text(
                text = text, fontSize = 30.sp, fontWeight = FontWeight.ExtraBold,
                color = if (isSystemInDarkTheme()) Color.White
                else Color.Black,
                modifier = Modifier.padding(start = 30.dp)
            )
            if (text == "Please Collect") {
                Column {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_menu_24),
                        contentDescription = "",
                        tint = if (isSystemInDarkTheme()) Color.White else Color.Black,
                        modifier = Modifier
                            .size(40.dp)
                            .clickable { onShowDialog() }
                    )
                    if (alertDialog()) {
                        AlertDialog(
                            onStopDialog = onStopDialog,
                            onNavigate = onNavigate)
                    }
                }
            } else {
                Spacer(modifier = Modifier.size(40.dp))
            }
        }
        Spacer(modifier = Modifier.fillMaxHeight(0.02f))
        Column(
            Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            OrderItemList()
        }
    }
}

@Composable
fun AlertDialog(onStopDialog: () -> Unit, onNavigate: () -> Unit) {
    androidx.compose.material3.AlertDialog(onDismissRequest = { },
        modifier = Modifier.fillMaxWidth(1f),
        title = {
            Text(
                text = "Confirmation!!",
                fontWeight = FontWeight.ExtraBold,
                fontSize = 35.sp
            )
        },
        text = { Text(text = "Confirm to logout ???", fontSize = 25.sp) },
        confirmButton = {
            TextButton(onClick = {
                onStopDialog()
                onNavigate()
            }) {
                Text("Confirm", fontSize = 20.sp)
            }
        },
        dismissButton = {
            Box {
                TextButton(onClick = onStopDialog) {
                    Text("Dismiss", fontSize = 20.sp)
                }
            }
        })
}


