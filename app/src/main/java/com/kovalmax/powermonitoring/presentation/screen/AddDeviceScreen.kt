package com.kovalmax.powermonitoring.presentation.screen

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.kovalmax.powermonitoring.intent.signin.UserData
import com.kovalmax.powermonitoring.presentation.Screen
import com.kovalmax.powermonitoring.presentation.model.AddDeviceViewModel

@Composable
fun AddDeviceScreen(
    nav: NavController,
    usr: UserData,
    viewModel: AddDeviceViewModel = hiltViewModel()
) {
    val ctx = LocalContext.current
    val deviceId by viewModel.deviceId.observeAsState("")
    val deviceName by viewModel.deviceName.observeAsState("")
    val deviceLocation by viewModel.deviceLocation.observeAsState("")


    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 55.dp)
    ) {
        Spacer(modifier = Modifier.height(160.dp))
        DeviceField(
            valueOfField = deviceId,
            label = "Device number",
            keyboardType = KeyboardType.Number,
            onChange = { viewModel.onDeviceIdChange(it) },
        )
        Spacer(modifier = Modifier.height(25.dp))
        DeviceField(
            valueOfField = deviceName,
            label = "Device name",
            keyboardType = KeyboardType.Text,
            onChange = { viewModel.onDeviceNameChange(it) },
        )
        Spacer(modifier = Modifier.height(25.dp))
        DeviceField(
            valueOfField = deviceLocation,
            label = "Device location",
            keyboardType = KeyboardType.Text,
            onChange = { viewModel.onDeviceLocationChange(it) },
        )
        Spacer(modifier = Modifier.height(50.dp))
        AddButton(
            onAdd = {
                try {
                    viewModel.onNewDevice(deviceId, deviceName, deviceLocation, usr.id)
                    nav.navigate(Screen.Main.route)
                } catch (e: Exception) {
                    Toast.makeText(
                        ctx,
                        "Invalid data, please check and try again",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        )
    }
}

@Composable
fun DeviceField(
    valueOfField: String,
    label: String,
    keyboardType: KeyboardType,
    onChange: (String) -> Unit
) {
    val focusManager = LocalFocusManager.current
    OutlinedTextField(
        value = valueOfField,
        onValueChange = onChange,
        label = { Text(label) },
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Done,
            keyboardType = keyboardType
        ),
        keyboardActions = KeyboardActions(
            onDone = { focusManager.clearFocus() }
        )
    )
}

@Composable
fun AddButton(onAdd: () -> Unit) {
    Button(
        onClick = onAdd,
        Modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primaryVariant)
    ) {
        Text(text = "SAVE", color = Color.White)
    }
}