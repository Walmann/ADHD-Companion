package it.walmann.adhdcompanion

import android.Manifest
import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.ColorInt
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.core.graphics.blue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import it.walmann.adhdcompanion.Screens.NewReminder
import it.walmann.adhdcompanion.Screens.RemindersScreen
import it.walmann.adhdcompanion.ui.theme.ADHDCompanionTheme


enum class CupcakeScreen(@StringRes val title: Int) {
    Start(title = R.string.app_name),
    NewReminder(title = R.string.NewReminder),
//    Pickup(title = R.string.choose_pickup_date),
//    Summary(title = R.string.order_summary)
}

class MainActivity : ComponentActivity() {
    lateinit var PACKAGE_NAME: String
    override fun onCreate(savedInstanceState: Bundle?) {
        PACKAGE_NAME = applicationContext.packageName
        super.onCreate(savedInstanceState)
        setContent {
            ADHDCompanionTheme {
//                window.navigationBarColor(@ColorInt )
                ADHDCompanionApp(modifier= Modifier, context = this)
            }
        }

    }

    // TODO Create "ask for alarm permission" section



    companion object {
        private const val TAG = "CameraXApp"
        private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
        public val REQUIRED_PERMISSIONS =
            mutableListOf(
                Manifest.permission.CAMERA,
                Manifest.permission.RECORD_AUDIO
            ).apply {
                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
                    add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                }
            }.toTypedArray()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ADHDCompanionApp(
    navController: NavHostController = rememberNavController(),
    modifier: Modifier.Companion,
    context: Context
) {
    NavHost(
        navController = navController,
        startDestination = CupcakeScreen.NewReminder.name,
//        startDestination = CupcakeScreen.Start.name,
        modifier = modifier
            .fillMaxSize()
//            .verticalScroll(rememberScrollState())
//                .padding(innerPadding)
    ) {

        composable(route = CupcakeScreen.Start.name) {
            RemindersScreen(
                modifier = Modifier
                    .fillMaxSize()
//                    .verticalScroll(rememberScrollState())
                    ,
                navController,
                context = context
            )
        }
        composable(route = CupcakeScreen.NewReminder.name) {
            NewReminder(
                context= context,
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            )
        }
    }


}


//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun ADHDCompanionApp(
//    navController: NavHostController = rememberNavController()
//) {
////    Scaffold(
////        topBar = {
////            TopAppBar(
////                title = {
////                    Text(text = stringResource(R.string.ToppAppBarTitle))
////                },
////                navigationIcon = {
////                    Icon(Icons.Filled.Menu, "backIcon")
//////                    IconButton(onClick = { /*TODO*/ }) { // TODO
//////                        Icon(Icons.Filled.Menu, "backIcon")
//////                    }
////                },
////                colors = TopAppBarDefaults.topAppBarColors(
////                    containerColor = MaterialTheme.colorScheme.primary,
////                    titleContentColor = Color.White,
////                ),
////            )
////
////        },
////        floatingActionButton = {
////            FloatingActionButton(
////                onClick = { /*TODO*/
////                    navController.navigate(CupcakeScreen.NewReminder.name)
////                          },
//////                Modifier.background = Color.Red,
////                content = {
////                    Icon(
////                        Icons.Filled.Add,
////                        contentDescription = null,
//////                        tint = Color.White
////                    )
////                }
////            )
////        }
////    )
////    { innerPadding ->
//        NavHost(
//            navController = navController,
//            startDestination = CupcakeScreen.Start.name,
//            modifier = Modifier
//                .fillMaxSize()
//                .verticalScroll(rememberScrollState())
////                .padding(innerPadding)
//        ) {
//            composable(route = CupcakeScreen.Start.name) {
//                RemindersScreen(
//                    modifier = Modifier
//                        .fillMaxSize()
//                        .verticalScroll(rememberScrollState())
//                )
//            }
//            composable(route = CupcakeScreen.NewReminder.name) {
//                NewReminder(
//                    modifier = Modifier
//                        .fillMaxSize()
//                        .verticalScroll(rememberScrollState())
//                )
//            }
//        }
//
//
//    }
//}
