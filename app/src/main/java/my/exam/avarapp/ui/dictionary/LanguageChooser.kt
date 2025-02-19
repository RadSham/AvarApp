package my.exam.avarapp.ui.dictionary

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.ExposedDropdownMenuDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun LanguageChooser(
    selectedLanguageFirst: MutableState<String>,
    selectedLanguageSecond: MutableState<String>,
    languagesList: List<String>
) {
    val languagesStateListSecond = remember {
        languagesList.toMutableStateList()
    }

    Row(Modifier.fillMaxWidth()) {
        val expanded = remember { mutableStateOf(false) }
        ExposedDropdownMenuBox(
            modifier = Modifier.weight(1f),
            expanded = expanded.value,
            onExpandedChange = { expanded.value = !expanded.value },
        ) {
            TextField(
//                modifier = Modifier.fillMaxWidth(),
                textStyle = TextStyle.Default.copy(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colors.secondary
                ),
                readOnly = true,
                value = selectedLanguageFirst.value,
                onValueChange = { },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded.value) },
                colors = ExposedDropdownMenuDefaults.textFieldColors(
                    backgroundColor = MaterialTheme.colors.primary,
                    focusedIndicatorColor = Transparent,
                    unfocusedIndicatorColor = Transparent,
                    trailingIconColor = MaterialTheme.colors.secondary,
                    focusedTrailingIconColor = MaterialTheme.colors.secondary
                )
            )
            ExposedDropdownMenu(
                expanded = expanded.value,
                onDismissRequest = { expanded.value = false },
            ) {
                languagesList.forEachIndexed { index, s ->
                    DropdownMenuItem(onClick = {
                        selectedLanguageFirst.value = languagesList[index]
                        languagesStateListSecond.clear()
                        languagesStateListSecond.addAll(languagesList)
                        languagesStateListSecond.remove(selectedLanguageFirst.value)
                        if (selectedLanguageSecond.value.equals(selectedLanguageFirst.value))
                            selectedLanguageSecond.value = languagesStateListSecond.first()
                        expanded.value = false
                    }) {
                        Text(text = s)
                    }
                }
            }
        }

        val expanded2 = remember { mutableStateOf(false) }
        ExposedDropdownMenuBox(
            modifier = Modifier.weight(1f),
            expanded = expanded2.value,
            onExpandedChange = { expanded2.value = !expanded2.value },
        ) {
            TextField(
                modifier = Modifier.fillMaxWidth(),
                textStyle = TextStyle.Default.copy(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colors.secondary
                ),
                readOnly = true,
                value = selectedLanguageSecond.value,
                onValueChange = { },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded2.value) },
                colors = ExposedDropdownMenuDefaults.textFieldColors(
                    backgroundColor = MaterialTheme.colors.primary,
                    focusedIndicatorColor = Transparent,
                    unfocusedIndicatorColor = Transparent,
                    trailingIconColor = MaterialTheme.colors.secondary,
                    focusedTrailingIconColor = MaterialTheme.colors.secondary
                )
            )
            ExposedDropdownMenu(
                expanded = expanded2.value,
                onDismissRequest = { expanded2.value = false },
            ) {
                languagesStateListSecond.forEachIndexed { index, s ->
                    DropdownMenuItem(onClick = {
                        selectedLanguageSecond.value = languagesStateListSecond[index]
                        expanded2.value = false
                    }) {
                        Text(text = s)
                    }
                }
            }
        }
    }
}