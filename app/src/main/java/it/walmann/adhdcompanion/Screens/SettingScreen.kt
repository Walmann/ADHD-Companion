package it.walmann.adhdcompanion.Screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import it.walmann.adhdcompanion.CommonUI.MyTopAppBar

@Composable
fun SettingsScreen(modifier: Modifier = Modifier) {
    Scaffold(
        topBar = { MyTopAppBar() }
    )
    { innerPadding ->

/* Setup->
     Column
        settingsEntry -> headline, description
            content for changing that setting.
*/

        Column(modifier = modifier
            .padding(innerPadding)
            .fillMaxSize()) {
            SettingEntry(header = "Quick reminder time", description = "Value description! ") {
                Text(text = "Hello from content!!") // TODO NEXT Create settings screen.
            }


        }
    }
}


@Composable
private fun SettingEntry(
    modifier: Modifier = Modifier.fillMaxWidth(),
    header: String,
    description: String,
    content: @Composable () -> Unit
) {
    Row(){
        Column(modifier = modifier.fillMaxSize()) {
    // Headline
            Text(text = header)
            Text(text = description)
        }
        // Description
    content()
    }
}


@Preview
@Composable
private fun SettingsScreenPreview() {
    SettingsScreen()
}