package it.walmann.adhdcompanion

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
}


class MainActivity : ComponentActivity() {
    private lateinit var PACKAGE_NAME: String


    override fun onCreate(savedInstanceState: Bundle?) {
        PACKAGE_NAME = applicationContext.packageName
        super.onCreate(savedInstanceState)
        setContent {
            ADHDCompanionTheme {
//                window.navigationBarColor(@ColorInt )
                ADHDCompanionApp(modifier = Modifier, context = this)
            }
        }

    }

    // TODO Create "ask for alarm permission" section

}

@Composable
fun ADHDCompanionApp(
    navController: NavHostController = rememberNavController(),
    modifier: Modifier,
    context: Context
) {
    // TODO NEXT Camera permission request is not working on Pixel 7
    NavHost(
        navController = navController,
//        startDestination = CupcakeScreen.NewReminder.name,
        startDestination = CupcakeScreen.Start.name,
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
                context = context,
                navController = navController,
                modifier = Modifier
                    .fillMaxSize()
//                    .verticalScroll(rememberScrollState())
            )
        }
    }


}

