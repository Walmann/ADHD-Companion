package it.walmann.adhdcompanion.Screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun SettingsScreen(modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxSize()) {
        /*
           - Backup status
          - Last backup
          - configure backup
          - disconnect backup, log out.
          - Setup backup
          -
        */
    }
}

@Preview
@Composable
private fun SettingScreenPreview() {
    SettingsScreen()
}