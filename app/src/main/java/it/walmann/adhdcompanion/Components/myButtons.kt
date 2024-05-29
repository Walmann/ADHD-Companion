package it.walmann.adhdcompanion.Components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewFontScale
import androidx.compose.ui.unit.dp

private val buttonRoundness = 30.dp


@Composable
fun MyButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    text: String = "",
    textStyle: TextStyle = MaterialTheme.typography.bodyMedium,
    textModifier: Modifier = Modifier,
    content: @Composable () -> Unit = {}
) {
    TextButton(
        onClick = onClick,
        shape = RoundedCornerShape(30.dp),
        colors = ButtonDefaults.textButtonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        ),
        modifier = modifier
    ) {
        if (text == "") {
            content()
        } else {
            Text(
                text = text, maxLines = 1,
                modifier = textModifier,
                style = textStyle
            )
        }
    }
}

@Composable
fun MyButtonCombinedTop(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    text: String,
    textStyle: TextStyle = MaterialTheme.typography.bodyMedium
) {
    TextButton(
        onClick = onClick,
        shape = RoundedCornerShape(topStart = buttonRoundness, topEnd = buttonRoundness),
        colors = ButtonDefaults.textButtonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        ),
        modifier = modifier
            .fillMaxWidth()
    ) {
        Text(
            text = text,
            maxLines = 1,
            style = textStyle,
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
    TextButton(
        onClick = onClick,
        colors = ButtonDefaults.textButtonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        ),
        shape = RoundedCornerShape(
            bottomStart = buttonRoundness, bottomEnd = buttonRoundness
        ),
        modifier = modifier
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
fun MyButtonCombinedHorizontal(
    modifier: Modifier = Modifier,
    divider_placement: Float = 8f,
    divider_modifier: Modifier = Modifier,
    textStyle: TextStyle = MaterialTheme.typography.bodyMedium,
    left_onClick: () -> Unit,
    left_modifier: Modifier = modifier,
    left_text: String,
    left_textStyle: TextStyle = TextStyle().merge(textStyle),
    right_onClick: () -> Unit,
    right_modifier: Modifier = modifier,
    right_text: String,
    right_textStyle: TextStyle = TextStyle().merge(textStyle)
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        MyButtonCombinedLeft(
            modifier = modifier
                .then(left_modifier)
                .weight(divider_placement, fill = false),
            onClick = left_onClick,
            text = left_text,
            textStyle = left_textStyle
        )
        Spacer(modifier = divider_modifier.width(1.dp))
        MyButtonCombinedRight(
            modifier = modifier
                .then(right_modifier)
                .weight(10f - divider_placement, fill = false),
            onClick = right_onClick,
            text = right_text,
            textStyle = right_textStyle
        )
    }
}

@Composable
fun MyButtonCombinedLeft(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    text: String,
    textStyle: TextStyle = MaterialTheme.typography.bodyMedium
) {
    TextButton(
        onClick = onClick,
        shape = RoundedCornerShape(topStart = buttonRoundness, bottomStart = buttonRoundness),
        colors = ButtonDefaults.textButtonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        ),
        modifier = modifier
            .fillMaxWidth()
    ) {
        Text(
            text = text,
            style = textStyle,
        )
    }
}

@Composable
fun MyButtonCombinedRight(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    text: String,
    textStyle: TextStyle = MaterialTheme.typography.bodyMedium
) {
    TextButton(
        onClick = onClick,
        colors = ButtonDefaults.textButtonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        ),
        shape = RoundedCornerShape(
            topEnd = buttonRoundness, bottomEnd = buttonRoundness
        ),
        modifier = modifier
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
@PreviewFontScale
@Composable
private fun MyButtonPreview() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        MyButtonCombinedTop(
            onClick = {},
            text = "21:59",
            textStyle = MaterialTheme.typography.displayLarge,
        )
        Spacer(modifier = Modifier.height(1.dp))
        MyButtonCombinedBottom(
            onClick = {},
            textStyle = MaterialTheme.typography.displayMedium,
            text = "16.04.2024"
        )
        Spacer(modifier = Modifier.height(30.dp))
        MyButtonCombinedHorizontal(
            modifier = Modifier,
            left_onClick = {},
            left_text = "Remind me in 10 minutes",
            right_onClick = {},
            right_text = "⚙️"
        )
    }
}