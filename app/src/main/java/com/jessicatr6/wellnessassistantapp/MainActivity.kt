package com.jessicatr6.wellnessassistantapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jessicatr6.wellnessassistantapp.ui.theme.WellnessAssistantAppTheme

// --- Routes ---
sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Dashboard : Screen("dashboard")
    object AddEntry : Screen("add_entry")
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WellnessAssistantAppTheme {
                AppNav()
            }
        }
    }
}

@Composable
fun AppNav() {
    val nav = rememberNavController()

    NavHost(navController = nav, startDestination = Screen.Login.route) {

        composable(Screen.Login.route) {
            LoginScreen(
                onLoginSuccess = {
                    // Navigate to Dashboard and remove Login from back stack
                    nav.navigate(Screen.Dashboard.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                },
                onForgotPassword = { /* TODO */ },
                onCreateAccount = { /* TODO */ }
            )
        }

        composable(Screen.Dashboard.route) {
            DashboardScreen(
                onAddEntry = { nav.navigate(Screen.AddEntry.route) }
            )
        }

        composable(Screen.AddEntry.route) {
            AddEntryScreen(
                onBack = { nav.popBackStack() }
            )
        }
    }
}

// --- Login Screen ---
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    onForgotPassword: () -> Unit,
    onCreateAccount: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var showPassword by remember { mutableStateOf(false) }
    var error by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(false) }

    fun validate(): Boolean {
        if (!email.contains("@") || !email.contains(".")) {
            error = "Please enter a valid email."
            return false
        }
        if (password.length < 6) {
            error = "Password must be at least 6 characters."
            return false
        }
        error = null
        return true
    }

    fun submit() {
        if (!validate()) return
        isLoading = true
        // TODO: replace with real auth (ViewModel/coroutine)
        isLoading = false
        onLoginSuccess()
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Sign In") })
        }
    ) { inner ->
        Column(
            modifier = Modifier
                .padding(inner)
                .padding(20.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                leadingIcon = { Icon(Icons.Filled.Email, contentDescription = null) },
                singleLine = true,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                leadingIcon = { Icon(Icons.Filled.Lock, contentDescription = null) },
                trailingIcon = {
                    IconButton(onClick = { showPassword = !showPassword }) {
                        Icon(
                            imageVector = if (showPassword) Icons.Filled.VisibilityOff else Icons.Filled.Visibility,
                            contentDescription = if (showPassword) "Hide password" else "Show password"
                        )
                    }
                },
                visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                modifier = Modifier.fillMaxWidth()
            )

            if (error != null) {
                Text(text = error!!, color = MaterialTheme.colorScheme.error)
            }

            Button(
                onClick = { if (!isLoading) submit() },
                enabled = !isLoading,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
            ) {
                if (isLoading) {
                    CircularProgressIndicator(modifier = Modifier.size(22.dp), strokeWidth = 2.dp)
                } else {
                    Text("Sign In")
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Forgot password?",
                    modifier = Modifier.clickable { onForgotPassword() },
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    "Create account",
                    modifier = Modifier.clickable { onCreateAccount() },
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

// --- Your existing screens (kept minimal) ---
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(onAddEntry: () -> Unit) {
    Scaffold(
        topBar = { TopAppBar(title = { Text("Wellness Dashboard") }) },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddEntry) { Text("+") }
        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding).padding(16.dp)) {
            Text("Dashboard content…")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEntryScreen(onBack: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add Wellness Entry") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding).padding(16.dp)) {
            Text("This is the Add Entry screen")
            Button(onClick = onBack, modifier = Modifier.padding(top = 16.dp)) {
                Text("Save and Go Back")
            }
        }
    }
}
