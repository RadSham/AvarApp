package my.exam.avarapp.ui.account

import android.util.Patterns
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
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
import androidx.compose.material.OutlinedButton
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
import androidx.lifecycle.viewmodel.compose.viewModel
import my.exam.avarapp.R
import my.exam.avarapp.ShowToast
import my.exam.avarapp.viewmodel.LoginViewModel

/**
 * The login view which will help the user to authenticate themselves and go to the
 * chat screen to show and send messages to others.
 */

@Composable
fun LoginScreen(
    chat: () -> Unit,
    back: () -> Unit,
    loginViewModel: LoginViewModel = viewModel(),
    showToast: ShowToast
) {
    val email: String by loginViewModel.email.observeAsState("")
    val password: String by loginViewModel.password.observeAsState("")
    val loading: Boolean by loginViewModel.loading.observeAsState(initial = false)
    var isErrorEmail by remember { mutableStateOf(false) }
    var isErrorPassword by remember { mutableStateOf(false) }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
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
                title = { Text(stringResource(id = R.string.login)) },
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
                    value = email,
                    onValueChange = {
                        loginViewModel.updateEmail(it)
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
                            IconButton(onClick = { loginViewModel.updateEmail("") }) {
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
                        loginViewModel.updatePassword(it)
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
                            IconButton(onClick = { loginViewModel.updatePassword("") }) {
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
                        loginViewModel.loginUser(chat = chat, showToast)
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
                shape = RoundedCornerShape(20),
            ) {
                Text(
                    text = stringResource(id = R.string.enter)
                )
            }
            OutlinedButton(
                onClick = {
                    if (!emailValidator(email)) isErrorEmail = true
                    else loginViewModel.resetPassword(showToast)
                },
                border = BorderStroke(1.dp, MaterialTheme.colors.secondary),
                colors = ButtonDefaults.buttonColors(
                    contentColor = MaterialTheme.colors.secondary
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, top = 10.dp, end = 20.dp)
                    .height(50.dp),
                shape = RoundedCornerShape(20),
            ) {
                Text(
                    text = "Сбросить пароль"
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
