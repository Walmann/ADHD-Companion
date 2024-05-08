package it.walmann.adhdcompanion.Components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp

private val buttonRoundness = 30.dp


@Composable
fun myButtonDefault(modifier: Modifier = Modifier, onClick: () -> Unit, text: String, textStyle: TextStyle = MaterialTheme.typography.bodyMedium) {
    Button(
        onClick = onClick, shape = RoundedCornerShape(30.dp), modifier = modifier
    ) {

        Text(
            text = text, maxLines = 1,
//            overflow = TextOverflow.Clip,
            style = textStyle
        )
    }
}

@Composable
fun MyButtonCombinedTop(modifier: Modifier = Modifier, onClick: () -> Unit, text: String, textStyle: TextStyle = MaterialTheme.typography.bodyMedium) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(topStart = buttonRoundness, topEnd = buttonRoundness),
        modifier = modifier
//            .weight(10f)
//            .fillMaxWidth()
    ) {
        Text(
            text = text,
            maxLines = 1,
            style = textStyle
        )
    }
}
@Composable
fun MyButtonCombinedBottom(modifier: Modifier = Modifier, onClick: () -> Unit, text: String, textStyle: TextStyle = MaterialTheme.typography.bodyMedium) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(
            bottomStart = buttonRoundness, bottomEnd = buttonRoundness
        ), modifier = modifier
//            .weight(10f)
//            .fillMaxWidth()

    ) {
        Text(
            text = text,
            maxLines = 1,
            style = textStyle

        )
    }
}