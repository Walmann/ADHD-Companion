package it.walmann.adhdcompanion.Components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

import java.util.Calendar

private val buttonRoundness = 30.dp


@Composable
fun myButtonDefault(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    text: String,
    textStyle: TextStyle = MaterialTheme.typography.bodyMedium
) {
    Button(
        onClick = onClick, shape = RoundedCornerShape(30.dp), modifier = modifier.fillMaxWidth()
    ) {

        Text(
            text = text, maxLines = 1,
//            overflow = TextOverflow.Clip,
            style = textStyle
        )
    }
}

@Composable
fun MyButtonCombinedTop(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    text: String,
    textStyle: TextStyle = MaterialTheme.typography.bodyMedium
) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(topStart = buttonRoundness, topEnd = buttonRoundness),
        modifier = modifier
//            .weight(10f)
            .fillMaxWidth()
    ) {
        Text(
            text = text,
            maxLines = 1,
            style = textStyle
        )
    }
}

@Composable
fun MyButtonCombinedBottom(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    text: String,
    textStyle: TextStyle = MaterialTheme.typography.bodyMedium
) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(
            bottomStart = buttonRoundness, bottomEnd = buttonRoundness
        ), modifier = modifier
//            .weight(10f)
            .fillMaxWidth()

    ) {
        Text(
            text = text,
            maxLines = 1,
            style = textStyle

        )
    }
}

@Preview
@Composable
private fun MyButtonPreview() {
    Column {


        MyButtonCombinedTop(
            onClick = {},
            text = "21:59",
            textStyle = MaterialTheme.typography.displayLarge,
//        modifier = Modifier
//            .weight(10f)
//            .fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(1.dp))
        MyButtonCombinedBottom(
            onClick = {},
//        modifier = Modifier
//            .weight(10f)
//            .fillMaxWidth(),
            textStyle = MaterialTheme.typography.displayMedium,
            text = "16.04.2024"
        )
        Spacer(modifier = Modifier.height(30.dp))
        myButtonDefault(
            onClick = {},
            text = "⏱\uFE0F Remind me in 10 minutes",
            textStyle = MaterialTheme.typography.headlineSmall
        )
    }
}