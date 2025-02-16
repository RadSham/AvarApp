package my.exam.avarapp.ui.chat

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LifecycleResumeEffect
import androidx.lifecycle.compose.LifecycleStartEffect
import my.exam.avarapp.R
import my.exam.avarapp.ShowToast
import my.exam.avarapp.viewmodel.ChatViewModel

@Composable
fun ChatScreen(
    authOptions: () -> Unit,
    account: () -> Unit,
    paddingValues: PaddingValues,
    showToast: ShowToast,
    dictionary: () -> Unit,
) {
    //handle back button to avoid navigation to duplicate screen
    BackHandler {
        dictionary()
    }
    val chatViewModel: ChatViewModel = hiltViewModel()
    Box(modifier = Modifier.padding(paddingValues)) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = stringResource(id = R.string.godekhan),
                            textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth()
                        )
                    },
                    actions = {
                        IconButton(
                            onClick = if (chatViewModel.checkUserExists()) account else authOptions
                        ) {
                            Icon(
                                imageVector = Icons.Filled.AccountCircle,
                                contentDescription = "Account"
                            )
                        }
                    },
                    backgroundColor = MaterialTheme.colors.primary,
                    contentColor = MaterialTheme.colors.secondary,
                    elevation = 10.dp
                )
            }) { paddingValues ->
            Box(modifier = Modifier.padding(paddingValues)) {
                Chat(chatViewModel, showToast)
            }
        }
    }
    LifecycleStartEffect(Unit) {
        Log.d("GoogleIO", "OnStart: Effect")
        onStopOrDispose {
            Log.d("GoogleIO", "OnStopOrDispose: Effect")
        }
    }
    LifecycleResumeEffect(Unit) {
        Log.d("GoogleIO", "OnResume: Effect")
        chatViewModel.connectToChat()
        onPauseOrDispose {
            Log.d("GoogleIO", "OnPauseOrDispose: Effect")
        }
    }
}

