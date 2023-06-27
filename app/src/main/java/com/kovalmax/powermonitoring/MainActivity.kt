package com.kovalmax.powermonitoring

import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Scaffold
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.auth.api.identity.Identity
import com.kovalmax.powermonitoring.intent.signin.GoogleAuthClient
import com.kovalmax.powermonitoring.presentation.Screen
import com.kovalmax.powermonitoring.presentation.model.SignInViewModel
import com.kovalmax.powermonitoring.presentation.screen.AddDeviceScreen
import com.kovalmax.powermonitoring.presentation.screen.MainScreen
import com.kovalmax.powermonitoring.presentation.screen.ProfileScreen
import com.kovalmax.powermonitoring.presentation.screen.SignInScreen
import com.kovalmax.powermonitoring.presentation.theme.PowerMonitoringTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val authClient by lazy {
        GoogleAuthClient(
            context = applicationContext,
            oneTapClient = Identity.getSignInClient(applicationContext)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PowerMonitoringTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colorScheme.background) {
                    val nav = rememberNavController()
                    val navBackStackEntry by nav.currentBackStackEntryAsState()

                    var showBottomBar by rememberSaveable { mutableStateOf(true) }
                    showBottomBar = when (navBackStackEntry?.destination?.route) {
                        Screen.SignIn.route -> false // on this screen bottom bar should be hidden
                        else -> true // in all other cases show bottom bar
                    }

                    Scaffold(
                        bottomBar = {
                            if (showBottomBar) {
                                BottomBar(
                                    navController = nav,
                                    navBackStackEntry = navBackStackEntry
                                )
                            }
                        }
                    ) {
                        Navigation(nav = nav, Modifier.padding(it))
                    }
                }
            }
        }
    }

    @Composable
    fun BottomBar(navController: NavHostController, navBackStackEntry: NavBackStackEntry?) {
        val screens = listOf(
            Screen.Main,
            Screen.AddDevice,
            Screen.Profile,
        )
        val currentDestination = navBackStackEntry?.destination?.route

        BottomNavigation(
            contentColor = Color.Green
        ) {
            screens.forEach { screen ->
                AddItem(
                    screen = screen,
                    currentDestination = currentDestination,
                    navController = navController
                )
            }
        }
    }

    @Composable
    fun RowScope.AddItem(
        screen: Screen,
        currentDestination: String?,
        navController: NavHostController
    ) {
        BottomNavigationItem(
            label = {
                Text(text = screen.title, fontSize = 12.sp, color = Color.White)
            },
            icon = {
                Icon(
                    imageVector = screen.icon,
                    contentDescription = "Navigation Icon",
                    tint = Color.White.copy(0.8f)
                )
            },
            selected = currentDestination == screen.route,
            unselectedContentColor = LocalContentColor.current.copy(0.1f),
            alwaysShowLabel = false,
            onClick = {
                if (currentDestination != screen.route) {
                    navController.navigate(screen.route) {
                        popUpTo(Screen.Main.route)
                        launchSingleTop = true

                    }
                }
            }
        )
    }

    @Composable
    fun Navigation(nav: NavHostController, modifier: Modifier) {
        NavHost(
            navController = nav,
            startDestination = Screen.SignIn.route,
            modifier = modifier
        ) {

            composable(route = Screen.Main.route) {
                LaunchedEffect(key1 = Unit) {
                    if (authClient.getSignedInUser() == null) {
                        nav.navigate(Screen.SignIn.route)
                    }
                }

                MainScreen()
            }

            composable(route = Screen.AddDevice.route) {
                LaunchedEffect(key1 = Unit) {
                    if (authClient.getSignedInUser() == null) {
                        nav.navigate(Screen.SignIn.route)
                    }
                }
                AddDeviceScreen(nav = nav, authClient.getSignedInUser()!!)
            }

            composable(route = Screen.DeviceDetails.route) {
                LaunchedEffect(key1 = Unit) {
                    if (authClient.getSignedInUser() == null) {
                        nav.navigate(Screen.SignIn.route)
                    }
                }

            }

            composable(route = Screen.Profile.route) {
                LaunchedEffect(key1 = Unit) {
                    if (authClient.getSignedInUser() == null) {
                        nav.navigate(Screen.SignIn.route)
                    }
                }
                ProfileScreen(
                    userData = authClient.getSignedInUser(),
                    onSignOut = {
                        lifecycleScope.launch {
                            authClient.signOut()
                            Toast.makeText(
                                applicationContext,
                                "Signed out",
                                Toast.LENGTH_LONG
                            ).show()

                            nav.navigate(Screen.SignIn.route)
                        }
                    })
            }

            composable(route = Screen.SignIn.route) {
                val signInViewModel: SignInViewModel = hiltViewModel()
                val state by signInViewModel.state.collectAsStateWithLifecycle()

                LaunchedEffect(key1 = Unit) {
                    if (authClient.getSignedInUser() != null) {
                        nav.navigate(Screen.Main.route)
                    }
                }

                val launcher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.StartIntentSenderForResult(),
                    onResult = { result ->
                        if (result.resultCode == RESULT_OK) {
                            lifecycleScope.launch {
                                val signInResult = authClient.signInWithIntent(
                                    intent = result.data ?: return@launch
                                )
                                signInViewModel.onSignInResult(signInResult)
                            }
                        }
                    }
                )

                LaunchedEffect(key1 = state.isSignInSuccessful) {
                    if (state.isSignInSuccessful) {
                        Toast.makeText(
                            applicationContext,
                            "Sign in successful",
                            Toast.LENGTH_LONG
                        ).show()

                        nav.navigate(Screen.Main.route)
                        signInViewModel.resetState()
                    }
                }

                SignInScreen(
                    state = state,
                    onSignInClick = {
                        lifecycleScope.launch {
                            val signInIntentSender = authClient.signIn()
                            launcher.launch(
                                IntentSenderRequest.Builder(
                                    signInIntentSender ?: return@launch
                                ).build()
                            )
                        }
                    }
                )
            }
        }
    }
}
