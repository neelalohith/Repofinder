package com.example.repofinder.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    placeholderText: String = "Search repositories",
    onSearch: (String) -> Unit
) {
    var textState by remember { mutableStateOf(TextFieldValue("")) }

    OutlinedTextField(
        value = textState,
        onValueChange = {
            textState = it
            onSearch(it.text) // Emit search query whenever input changes
        },
        label = { Text(placeholderText) },
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(onSearch = { onSearch(textState.text) })
    )
}