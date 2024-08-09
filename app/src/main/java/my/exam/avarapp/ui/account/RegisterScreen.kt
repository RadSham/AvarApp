package my.exam.avarapp.ui.account

import android.util.Patterns
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import my.exam.avarapp.R
import my.exam.avarapp.ShowToast
import my.exam.avarapp.viewmodel.RegisterViewModel

/**
 * The Register view which will be helpful for the user to register themselves into
 * our database and go to the chat screen to see and send messages.
 */

@Composable
fun RegisterScreen(
    chat: () -> Unit,
    back: () -> Unit,
    showToast: ShowToast
) {
    val registerViewModel: RegisterViewModel = hiltViewModel()
    val username: String by registerViewModel.username.observeAsState("")
    val email: String by registerViewModel.email.observeAsState("")
    val password: String by registerViewModel.password.observeAsState("")
    val loading: Boolean by registerViewModel.loading.observeAsState(initial = false)
    var isErrorUsername by remember { mutableStateOf(false) }
    var isErrorEmail by remember { mutableStateOf(false) }
    var isErrorPassword by remember { mutableStateOf(false) }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize(),
    ) {
        if (loading) {
            CircularProgressIndicator()
        }
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            TopAppBar(
                title = { Text(stringResource(id = R.string.registration)) },
                navigationIcon = {
                    IconButton(onClick = back) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back button")
                    }
                },
                backgroundColor = MaterialTheme.colors.primary,
                contentColor = MaterialTheme.colors.secondary,
                elevation = 10.dp
            )
            val customTextSelectionColors = TextSelectionColors(
                handleColor = MaterialTheme.colors.secondary,
                backgroundColor = MaterialTheme.colors.secondary.copy(alpha = 0.4f)
            )
            CompositionLocalProvider(LocalTextSelectionColors provides customTextSelectionColors) {
                OutlinedTextField(
                    modifier = Modifier
                        .padding(horizontal = 20.dp, vertical = 5.dp)
                        .fillMaxWidth(),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        cursorColor = MaterialTheme.colors.secondary,
                        textColor = Color.Black,
                        focusedBorderColor = MaterialTheme.colors.primaryVariant,
                        unfocusedBorderColor = MaterialTheme.colors.secondaryVariant
                    ),
                    value = username,
                    onValueChange = {
                        registerViewModel.updateUsername(it)
                        isErrorUsername = false
                    },
                    label = {
                        Text(
                            stringResource(id = R.string.username),
                            color = MaterialTheme.colors.primaryVariant
                        )
                    },
                    trailingIcon = {
                        if (username.isNotEmpty()) {
                            IconButton(onClick = { registerViewModel.updateUsername("") }) {
                                Icon(
                                    imageVector = Icons.Outlined.Close,
                                    contentDescription = "ic_close",
                                    tint = MaterialTheme.colors.secondary
                                )
                            }
                        }
                    },
                    maxLines = 1,
                    singleLine = true,
                    visualTransformation = VisualTransformation.None,
                    isError = isErrorUsername,
                )
                if (isErrorUsername) {
                    Text(
                        text = stringResource(id = R.string.invalid_username),
                        color = MaterialTheme.colors.error,
                        style = MaterialTheme.typography.caption,
                        modifier = Modifier
                            .align(Alignment.Start)
                            .padding(start = 20.dp, end = 20.dp)
                    )
                }
            }
            CompositionLocalProvider(LocalTextSelectionColors provides customTextSelectionColors) {
                OutlinedTextField(
                    modifier = Modifier
                        .padding(horizontal = 20.dp, vertical = 5.dp)
                        .fillMaxWidth(),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        cursorColor = MaterialTheme.colors.secondary,
                        textColor = Color.Black,
                        focusedBorderColor = MaterialTheme.colors.primaryVariant,
                        unfocusedBorderColor = MaterialTheme.colors.secondaryVariant
                    ),
                    value = email,
                    onValueChange = {
                        registerViewModel.updateEmail(it)
                        isErrorEmail = false
                    },
                    label = {
                        Text(
                            stringResource(id = R.string.email),
                            color = MaterialTheme.colors.primaryVariant
                        )
                    },
                    trailingIcon = {
                        if (email.isNotEmpty()) {
                            IconButton(onClick = { registerViewModel.updateEmail("") }) {
                                Icon(
                                    imageVector = Icons.Outlined.Close,
                                    contentDescription = "ic_close",
                                    tint = MaterialTheme.colors.secondary
                                )
                            }
                        }
                    },
                    maxLines = 1,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email
                    ),
                    singleLine = true,
                    visualTransformation = VisualTransformation.None,
                    isError = isErrorEmail,
                )
                if (isErrorEmail) {
                    Text(
                        text = stringResource(id = R.string.invalid_email),
                        color = MaterialTheme.colors.error,
                        style = MaterialTheme.typography.caption,
                        modifier = Modifier
                            .align(Alignment.Start)
                            .padding(start = 20.dp, end = 20.dp)
                    )
                }
            }
            CompositionLocalProvider(LocalTextSelectionColors provides customTextSelectionColors) {
                OutlinedTextField(
                    modifier = Modifier
                        .padding(horizontal = 20.dp, vertical = 5.dp)
                        .fillMaxWidth(),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        cursorColor = MaterialTheme.colors.secondary,
                        textColor = Color.Black,
                        focusedBorderColor = MaterialTheme.colors.primaryVariant,
                        unfocusedBorderColor = MaterialTheme.colors.secondaryVariant
                    ),
                    value = password,
                    onValueChange = {
                        registerViewModel.updatePassword(it)
                        isErrorPassword = false
                    },
                    label = {
                        Text(
                            stringResource(id = R.string.password),
                            color = MaterialTheme.colors.primaryVariant
                        )
                    },
                    trailingIcon = {
                        if (password.isNotEmpty()) {
                            IconButton(onClick = { registerViewModel.updatePassword("") }) {
                                Icon(
                                    imageVector = Icons.Outlined.Close,
                                    contentDescription = "ic_close",
                                    tint = MaterialTheme.colors.secondary
                                )
                            }
                        }
                    },
                    maxLines = 1,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password
                    ),
                    singleLine = true,
                    visualTransformation = PasswordVisualTransformation(),
                    isError = isErrorPassword,
                )
                if (isErrorPassword) {
                    Text(
                        text = stringResource(id = R.string.invalid_password),
                        color = MaterialTheme.colors.error,
                        style = MaterialTheme.typography.caption,
                        modifier = Modifier
                            .align(Alignment.Start)
                            .padding(start = 20.dp, end = 20.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            fun checkDetails(): Boolean {
                var isAllFilledInCorrectly = true
                if (username.isEmpty()) {
                    isErrorUsername = true
                    isAllFilledInCorrectly = false
                }
                if (!emailValidator(email)) {
                    isErrorEmail = true
                    isAllFilledInCorrectly = false
                }
                if (password.length < 6) {
                    isErrorPassword = true
                    isAllFilledInCorrectly = false
                }
                return isAllFilledInCorrectly
            }
            Button(
                onClick = {
                    if (checkDetails()) {
                        registerViewModel.registerUser(chat, showToast)
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = MaterialTheme.colors.secondary,
                    contentColor = Color.White
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp)
                    .height(50.dp),
                shape = RoundedCornerShape(20)
            ) {
                Text(
                    text = stringResource(id = R.string.registration)
                )
            }
        }
    }
}

private fun emailValidator(stringEmail: String): Boolean {
    if (!stringEmail.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(stringEmail).matches()) {
        return true
    } else {
        return false
    }
}