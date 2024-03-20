package it.walmann.adhdcompanion

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import it.walmann.adhdcompanion.ui.theme.ADHDCompanionTheme
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ADHDCompanionTheme {

                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    Scaffolding()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Scaffolding() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(R.string.ToppAppBarTitle))
                },
                navigationIcon = {
                    IconButton(onClick = {}) { // TODO
                        Icon(Icons.Filled.Menu, "backIcon")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = Color.White,
                ),
            )

        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /*TODO*/ },
//                Modifier.background = Color.Red,
                content = {
                    Icon(
                        Icons.Filled.Add,
                        contentDescription = null,
//                        tint = Color.White
                    )
                }
            )
        },
         content = {
            Column(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize()
                    .background(Color(0xff8d6e63)),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                    ReminderCard(ReminderTime = "10:45", ReminderDate = "03.02.2024", ReminderText = "Lorem Ipsum is simply dummy ", modifier = Modifier)
                    ReminderCard(ReminderTime = "10:45", ReminderDate = "03.02.2024", ReminderText = "Lorem Ipsum is simply dummy ", modifier = Modifier)
                    ReminderCard(ReminderTime = "10:45", ReminderDate = "03.02.2024", ReminderText = "Lorem Ipsum is simply dummy ", modifier = Modifier)
                    ReminderCard(ReminderTime = "10:45", ReminderDate = "03.02.2024", ReminderText = "Lorem Ipsum is simply dummy ", modifier = Modifier)
                    ReminderCard(ReminderTime = "10:45", ReminderDate = "03.02.2024", ReminderText = "Lorem Ipsum is simply dummy ", modifier = Modifier)
                    ReminderCard(ReminderTime = "10:45", ReminderDate = "03.02.2024", ReminderText = "Lorem Ipsum is simply dummy ", modifier = Modifier)
                }

            }
        }
    )


}


@Composable
fun ReminderCard(
    ReminderTime: String,
    ReminderDate: String,
    ReminderText: String,
    modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .fillMaxWidth(0.9f)
            .height(275.dp)
            .padding(vertical = 10.dp)
        ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = modifier
//                .background(Color.Blue)
//                .weight(1f)
                .fillMaxSize()
        ) {
            Row(
//                modifier = modifier
//                .background(Color.Black)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = modifier
                        .width(200.dp)
                        .height(200.dp)


                ) {
                    Text(
                        text = ReminderTime,
                        fontSize = 50.sp,
                    )
                    Text(
                        text = ReminderDate,
                        fontSize = 20.sp,
                    )

                }
                Image(
                    modifier = modifier
                        .height(200.dp)
                        .padding(10.dp),
                    contentScale = ContentScale.Fit,
                    painter = painterResource(id = R.drawable.placeholder_remindercard_image),
                    contentDescription = null
                )
            }
            Text(
                text = ReminderText,
                fontSize = 20.sp,
                modifier = modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 10.dp
                    )
            )
        }
    }
}
