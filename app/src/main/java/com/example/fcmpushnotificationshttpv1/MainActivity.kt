package com.example.fcmpushnotificationshttpv1

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.fcmpushnotificationshttpv1.ui.theme.FcmPushNotificationsHttpV1Theme

class MainActivity : ComponentActivity() {

    private val viewModel: ChatViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestNotificationPermission()

        enableEdgeToEdge()
        setContent {
            FcmPushNotificationsHttpV1Theme {
                MainScreen(viewModel = viewModel)
            }
        }
    }
    private fun requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val hasPermission = ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED

            if (!hasPermission) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                    0
                )
            }
        }
    }
}

@Composable
fun MainScreen(viewModel: ChatViewModel) {
    val state = viewModel.state

    if (state.isEnteringToken) {
        EnterTokenDialog(
            token = state.remoteToken,
            onTokenChange = viewModel::onRemoteTokenChange,
            onSubmit = viewModel::onSubmitRemoteToken
        )
    } else {
        ChatScreen(
            messageText = state.messageText,
            onMessageSend = { viewModel.sendMessage(isBroadcast = true) },
            onMessageBroadcast = { viewModel.sendMessage(isBroadcast = true) },
            onMessageChange = viewModel::onMessageChange,
        )
    }
}

