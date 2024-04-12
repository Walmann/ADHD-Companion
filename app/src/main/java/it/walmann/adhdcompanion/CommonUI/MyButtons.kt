package it.walmann.adhdcompanion.CommonUI

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun MyButtonsAccept(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    text: String = "ButtonText"
) {
    Button(
        modifier = modifier,
        onClick = onClick
    ) {
        Text(
            text = text
        )
    }
}