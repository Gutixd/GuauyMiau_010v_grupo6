package com.example.guaumiau

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.guaumiau.ui.theme.GuauMiauTheme
import com.example.guaumiau.ui.theme.auth.LoginScreen
import com.example.guaumiau.ui.theme.auth.RegisterScreen
import com.example.guaumiau.ui.theme.pets.PetsScreen
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { AppRoot() }
    }
}

@Composable
fun AppRoot() {
    // Usa tu tema (el que viene por defecto en ui.theme/Theme.kt)
    GuauMiauTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            val nav = rememberNavController()
            NavHost(navController = nav, startDestination = "login") {
                composable("login") {
                    LoginScreen(
                        onGoRegister = { nav.navigate("register") },
                        onLoggedIn = { userId -> nav.navigate("pets/$userId") { popUpTo("login") { inclusive = true } } }
                    )
                }
                composable("register") {
                    RegisterScreen(
                        onBack = { nav.popBackStack() },
                        onRegistered = { nav.popBackStack() } // tras registro vuelve a login
                    )
                }
                composable("pets/{userId}") { backStackEntry ->
                    val userId = backStackEntry.arguments?.getString("userId")?.toLongOrNull() ?: 0L
                    PetsScreen(
                        userId = userId,
                        onLogout = { nav.navigate("login") { popUpTo(0) } }
                    )
                }
            }
        }
    }
}
