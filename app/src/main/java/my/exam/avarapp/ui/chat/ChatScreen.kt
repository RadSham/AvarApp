package my.exam.avarapp.ui.chat

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.ui.unit.dp

@Composable
fun ChatScreen(
    authOptions: () -> Unit,
    back: () -> Unit,
    padding: PaddingValues
) {
    Box(modifier = Modifier.padding(padding)) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(text = "ГОДЕКIАН")
                    },
                    /*navigationIcon = {
                        IconButton(onClick = back) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, "backIcon")
                        }
                    },*/
                    actions = {
                        IconButton(onClick = authOptions) {
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
                Chat()
            }
        }
    }
}

