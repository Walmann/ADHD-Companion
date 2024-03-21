package it.walmann.adhdcompanion.CommonUI

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import it.walmann.adhdcompanion.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyScaffolding(
    floatingActionButton: @Composable () -> Unit,
    function: @Composable () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(R.string.ToppAppBarTitle))
                },
                navigationIcon = {
                    Icon(Icons.Filled.Menu, "backIcon")
//                    IconButton(onClick = { /*TODO*/ }) { // TODO
//                        Icon(Icons.Filled.Menu, "backIcon")
//                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = Color.White,
                ),
            )
        floatingActionButton()
        },
    ) {}
}


//        floatingActionButton = {
//            FloatingActionButton(
//                onClick = { /*TODO*/
//                    navController.navigate(CupcakeScreen.NewReminder.name)
//                },
////                Modifier.background = Color.Red,
//                content = {
//                    Icon(
//                        Icons.Filled.Add,
//                        contentDescription = null,
////                        tint = Color.White
//                    )
//                }
//            )
