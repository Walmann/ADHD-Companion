package it.walmann.adhdcompanion.CommonUI

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import it.walmann.adhdcompanion.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopAppBar() {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = Color.White
        ),
        title = {
            Text(text = stringResource(R.string.ToppAppBarTitle))
        },
        navigationIcon = {
            Icon(Icons.Filled.Menu, "backIcon")
//                    IconButton(onClick = { /*TODO Create side menu for Settings etc.*/ }) {
//                        Icon(Icons.Filled.Menu, "backIcon")
//                    }
        },
    )
}

@Composable
fun MyBottomAppBar() {
    BottomAppBar(
        containerColor = Color.Black
    ) {

    }
}