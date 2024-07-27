package my.exam.avarapp.ui.chat

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
import androidx.lifecycle.viewmodel.compose.viewModel
import my.exam.avarapp.R
import my.exam.avarapp.ShowToast
import my.exam.avarapp.viewmodel.ChatViewModel

@Composable
fun ChatScreen(
    authOptions: () -> Unit,
    account: () -> Unit,
    paddingValues: PaddingValues,
    showToast: ShowToast,
    chatViewModel: ChatViewModel = viewModel()
) {

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
}

