package com.kovalmax.powermonitoring.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FlashOff
import androidx.compose.material.icons.outlined.FlashOn
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kovalmax.powermonitoring.domain.model.Device
import com.kovalmax.powermonitoring.presentation.model.MainViewModel

sealed class PowerState(val color: Color, val icon: ImageVector) {
    object On : PowerState(
        Color.Green,
        Icons.Outlined.FlashOn,
    )

    object Off : PowerState(
        Color.Red,
        Icons.Outlined.FlashOff
    )
}

@Composable
fun MainScreen(mainViewModel: MainViewModel = hiltViewModel()) {
    val devices: List<Device> by mainViewModel.devices.observeAsState(listOf())

    mainViewModel.updateList()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(15.dp))
        Button(
            onClick = { mainViewModel.updateList() },
            shape = RoundedCornerShape(10.dp)

        ) {
            Text(text = "Refresh", color = Color.White)
        }
    }

    Column {
        Spacer(modifier = Modifier.height(90.dp))
        if (devices.isEmpty()) {
            Row(
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
            ) {
                Text(text = "No devices found", fontSize = 20.sp)
            }
        }
        LazyColumn(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
        ) {
            items(devices) {
                val state = when (it.lastEvent?.powerState) {
                    1 -> PowerState.On
                    else -> PowerState.Off
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colors.primary, shape = RoundedCornerShape(15.dp))
                ) {
                    Text(
                        text = it.deviceName,
                        fontSize = 20.sp,
                        modifier = Modifier.padding(25.dp),
                        color = Color.White
                    )
                    Text(
                        text = it.deviceLocation,
                        fontSize = 20.sp,
                        modifier = Modifier.padding(25.dp),
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.width(35.dp))
                    Icon(
                        imageVector = state.icon,
                        contentDescription = "Energy state icon",
                        tint = state.color,
                        modifier = Modifier.size(35.dp)
                    )
                }
            }
        }
    }
}