package it.walmann.adhdcompanion.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import it.walmann.adhdcompanion.CommonUI.MyTopAppBar
import it.walmann.adhdcompanion.ui.theme.ADHDCompanionTheme

@Composable
fun SettingsScreen(modifier: Modifier = Modifier) {
    ADHDCompanionTheme {
        Scaffold(
            topBar = { MyTopAppBar() }
        )
        { innerPadding ->

/* Setup->
     Column
        settingsEntry -> headline, description
            content for changing that setting.
*/

            Column(
                modifier = modifier
                    .padding(innerPadding)
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                repeat(50) {
                    SettingEntry(
                        header = "Quick reminder time",
                        description = "Set amount of time for the quick reminder button",
                        modifier = modifier
                            .fillMaxWidth()
                    ) {
                        Text(text = "Hello from content!!") // TODO Create settings screen.
                    }
                }

            }
        }
    }
}

@Composable
private fun SettingEntry(
    modifier: Modifier = Modifier,
    header: String,
    description: String,
    content: @Composable () -> Unit
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Row(
            modifier = modifier
                .padding(horizontal = 10.dp, vertical = 15.dp)
                .fillMaxWidth()
        ) {
            Column(modifier = modifier.weight(8f)) {
                // Headline
                Text(text = header, style = MaterialTheme.typography.titleLarge)
                Text(text = description, style = MaterialTheme.typography.labelMedium)
            }
            // Description
            //        Box(modifier = modifier.weight(2f)) {
            content()
            //        }
        }
//        Divider(
//            color = MaterialTheme.colorScheme.onBackground,
////            color = Color.Black,
//            modifier = Modifier
//                .height(1.dp)
////                .background(brush = Brush.horizontalGradient(colors = listOf(Color.Transparent, Color.Black, Color.Transparent)))
//                .fillMaxWidth()
//        )
    }
}


//@Preview
@PreviewLightDark
@Composable
fun SettingsScreenPreview() {
    SettingsScreen()
}