package com.kovalmax.powermonitoring.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material3.Checkbox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.kovalmax.powermonitoring.intent.signin.UserData
import com.kovalmax.powermonitoring.presentation.model.ProfileViewModel

@Composable
fun ProfileScreen(
    userData: UserData?,
    onSignOut: () -> Unit,
    model: ProfileViewModel = hiltViewModel()
) {
    val checkedState by model.state.collectAsStateWithLifecycle()
    val uriHandler = LocalUriHandler.current

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (userData?.photoUrl != null) {
            AsyncImage(
                model = userData.photoUrl,
                contentDescription = "Profile picture",
                modifier = Modifier
                    .size(125.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(16.dp))
        }

        if (userData?.displayName != null) {
            Text(
                text = userData.displayName,
                textAlign = TextAlign.Center,
                fontSize = 36.sp,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(16.dp))
        }


        Row {
            Checkbox(
                checked = checkedState,
                onCheckedChange = {
                    model.onTgChanged(it, userData?.id)
                },
                modifier = Modifier
                    .padding(10.dp)
                    .size(10.dp),
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                "Send notifications to telegram?",
                textAlign = TextAlign.Justify,
                fontSize = 20.sp,
            )
        }
        Spacer(modifier = Modifier.height(25.dp))
        Button(onClick = {
            uriHandler.openUri(
                "https://t.me/MaxPersonalAssistantBot?start=" + userData?.id
            )
        }) {
            Text(text = "Setup telegram", color = Color.White)
        }
        Spacer(modifier = Modifier.height(25.dp))
        Button(onClick = onSignOut) {
            Text(text = "Sign out", color = Color.White)
        }
    }
}