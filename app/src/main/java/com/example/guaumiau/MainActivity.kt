package com.example.guaumiau

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
//import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.guaumiau.ui.theme.GuauMiauTheme
import com.example.guaumiau.ui.theme.auth.LoginScreen
import com.example.guaumiau.ui.theme.auth.RegisterScreen
import com.example.guaumiau.ui.theme.pets.PetsScreen
import com.example.guaumiau.ui.theme.perfil.ProfileViewModel
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
    GuauMiauTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            val nav = rememberNavController()

            NavHost(
                navController = nav,
                startDestination = "welcome"
            ) {
                // 🟣 Pantalla de bienvenida
                composable("welcome") {
                    WelcomeScreen(
                        onGoLogin = {
                            nav.navigate("login") {
                                popUpTo("welcome") { inclusive = true }
                            }
                        },
                        onGoRegister = {
                            nav.navigate("register")
                        }
                    )
                }

                // 🟣 Login
                composable("login") {
                    LoginScreen(
                        onGoRegister = { nav.navigate("register") },
                        onLoggedIn = { userId ->
                            nav.navigate("pets/$userId") {
                                popUpTo("login") { inclusive = true }
                            }
                        }
                    )
                }

                // 🟣 Registro
                composable("register") {
                    RegisterScreen(
                        onBack = { nav.popBackStack() },
                        onRegistered = { nav.popBackStack() }
                    )
                }

                //lista d emascotas
                composable("pets/{userId}") { backStackEntry ->
                    val userId = backStackEntry.arguments
                        ?.getString("userId")
                        ?.toLongOrNull() ?: 0L

                    PetsScreen(
                        userId = userId,
                        onLogout = {
                            nav.navigate("login") { popUpTo(0) }
                        },
                        onGoProfile = { uid ->
                            nav.navigate("profile/$uid")
                        },
                        onGoSettings = {
                            nav.navigate("settings")
                        },
                        onGoAbout = {
                            nav.navigate("about")
                        }
                    )
                }

                // 🟣 Perfil de usuario (con Room)
                composable("profile/{userId}") { backStackEntry ->
                    val userId = backStackEntry.arguments
                        ?.getString("userId")
                        ?.toLongOrNull() ?: 0L

                    ProfileScreen(
                        userId = userId,
                        onBack = { nav.popBackStack() },
                        onLogout = {
                            nav.navigate("login") { popUpTo(0) }
                        }
                    )
                }

                // 🟣 Configuración
                composable("settings") {
                    SettingsScreen(
                        onBack = { nav.popBackStack() }
                    )
                }

                // 🟣 Acerca de la app
                composable("about") {
                    AboutAppScreen(
                        onBack = { nav.popBackStack() }
                    )
                }
            }
        }
    }
}

@Composable
fun WelcomeScreen(
    onGoLogin: () -> Unit,
    onGoRegister: () -> Unit
) {
    Scaffold { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(24.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                Text(
                    text = "GuauMiau",
                    style = MaterialTheme.typography.headlineLarge
                )
                Text(
                    text = "App para gestionar usuarios y mascotas, desarrollada como proyecto académico con Jetpack Compose, Room y Hilt.",
                    style = MaterialTheme.typography.bodyLarge
                )
            }

            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Button(
                    onClick = onGoLogin,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Ya tengo cuenta")
                }
                OutlinedButton(
                    onClick = onGoRegister,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Quiero registrarme")
                }
                Text(
                    text = "Esta pantalla de bienvenida te ayuda a mostrar el flujo completo de la app en la presentación.",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    userId: Long,
    onBack: () -> Unit,
    onLogout: () -> Unit,
    vm: ProfileViewModel = hiltViewModel()
) {
    val userState by vm.user.collectAsState()

    LaunchedEffect(userId) {
        vm.loadUser(userId)
    }

    Scaffold(
        topBar = {
            SmallTopAppBar(
                title = { Text("Perfil de usuario") },
                navigationIcon = {
                    TextButton(onClick = onBack) { Text("Atrás") }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text("Información del usuario", style = MaterialTheme.typography.titleMedium)

            Text("Nombre completo: ${userState?.fullName ?: "—"}")
            Text("Email: ${userState?.email ?: "—"}")
            Text("Teléfono: ${userState?.phone ?: "No registrado"}")

            Spacer(modifier = Modifier.height(16.dp))

            Text("Seguridad", style = MaterialTheme.typography.titleMedium)

            OutlinedButton(
                onClick = { /* futura pantalla de cambio de contraseña */ },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Cambiar contraseña (demo)")
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = onLogout,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Cerrar sesión")
            }
        }
    }
}

@Composable
fun SmallTopAppBar(title: @Composable () -> Unit, navigationIcon: @Composable () -> Unit) {
    TODO("Not yet implemented")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onBack: () -> Unit
) {
    var darkModeEnabled by remember { mutableStateOf(false) }
    var notificationsEnabled by remember { mutableStateOf(true) }

    Scaffold(
        topBar = {
            SmallTopAppBar(
                title = { Text("Configuración") },
                navigationIcon = {
                    TextButton(onClick = onBack) { Text("Atrás") }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text("Preferencias", style = MaterialTheme.typography.titleMedium)

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Modo oscuro (demo)")
                Switch(
                    checked = darkModeEnabled,
                    onCheckedChange = { darkModeEnabled = it }
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Notificaciones (demo)")
                Switch(
                    checked = notificationsEnabled,
                    onCheckedChange = { notificationsEnabled = it }
                )
            }

            Text(
                text = "Estas opciones son de demostración. Sirven para mostrar más UI y controles interactivos en la entrega.",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutAppScreen(
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            SmallTopAppBar(
                title = { Text("Acerca de GuauMiau") },
                navigationIcon = {
                    TextButton(onClick = onBack) { Text("Atrás") }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text("GuauMiau", style = MaterialTheme.typography.headlineSmall)
            Text(
                text = "Aplicación desarrollada como proyecto académico.\n\n" +
                        "- Gestión de usuarios y mascotas con Room.\n" +
                        "- Arquitectura con repositorios, ViewModel y Hilt.\n" +
                        "- Interfaz con Jetpack Compose.\n\n" +
                        "Incluye pantallas de bienvenida, login, registro, listado de mascotas, perfil, ajustes y esta pantalla de información.",
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = "Esta pantalla es ideal para explicar el proyecto al profesor o en la rúbrica.",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}
