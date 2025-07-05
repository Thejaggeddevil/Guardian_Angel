package com.mansi.guardianangel

import android.util.Patterns
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(navController: NavController, viewModel: AppViewModel) {
    val context = LocalContext.current
    val auth = FirebaseAuth.getInstance()
    val scope = rememberCoroutineScope()

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    // Preloaded strings
    val loginTitle = stringResource(R.string.login_title)
    val emailLabel = stringResource(R.string.email)
    val passwordLabel = stringResource(R.string.password)
    val loginText = stringResource(R.string.login)
    val signupText = stringResource(R.string.dont_have_account)
    val forgotPassText = stringResource(R.string.terms_and_conditions)
    val invalidEmailMsg = stringResource(R.string.invalid_email)
    val passwordLengthError = stringResource(R.string.passwords_do_not_match)
    val loginFailed = stringResource(R.string.login_failed)

    // Auto login
    LaunchedEffect(Unit) {
        auth.currentUser?.let {
            navController.navigate("home") {
                popUpTo("login") { inclusive = true }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE8F5E9))
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "🔐 $loginTitle",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1A1B41)
        )

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text(emailLabel) },
            leadingIcon = { Icon(Icons.Default.Email, contentDescription = null) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text(passwordLabel) },
            leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
            trailingIcon = {
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(
                        imageVector = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                        contentDescription = "Toggle password visibility"
                    )
                }
            },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                when {
                    !Patterns.EMAIL_ADDRESS.matcher(email).matches() ->
                        errorMessage = "⚠️ $invalidEmailMsg"

                    password.length < 6 ->
                        errorMessage = "⚠️ $passwordLengthError"

                    else -> {
                        isLoading = true
                        errorMessage = null
                        scope.launch {
                            auth.signInWithEmailAndPassword(email, password)
                                .addOnCompleteListener { task ->
                                    isLoading = false
                                    if (task.isSuccessful) {
                                        val uid = auth.currentUser?.uid
                                        if (uid != null) {
                                            val firestore = FirebaseFirestore.getInstance()
                                            firestore.collection("users").document(uid).get()
                                                .addOnSuccessListener { doc ->
                                                    val name = doc.getString("name")
                                                    if (name.isNullOrBlank()) {
                                                        // TODO: Prompt user for name input. For now, set as 'User'.
                                                        firestore.collection("users").document(uid)
                                                            .update("name", "User")
                                                        viewModel.setUsername("User", context)
                                                    } else {
                                                        viewModel.setUsername(name, context)
                                                    }
                                                }
                                        }
                                        Toast.makeText(context, "✅ Login Successful", Toast.LENGTH_SHORT).show()
                                        navController.navigate("home") {
                                            popUpTo("login") { inclusive = true }
                                        }
                                    } else {
                                        errorMessage = task.exception?.localizedMessage ?: "❌ $loginFailed"
                                    }
                                }
                        }
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFEF5350))
        ) {
            Text(loginText, color = Color.White, fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(8.dp))

        TextButton(onClick = {
            navController.navigate("signup") {
                popUpTo("login") { inclusive = false }
            }
        }) {
            Text(signupText, color = Color(0xFF1A1B41))
        }

        Text(
            text = "Forgot Password?",
            color = Color.Blue,
            modifier = Modifier
                .padding(top = 6.dp)
                .clickable {
                    if (email.isBlank() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                        Toast.makeText(context, "Enter a valid email to reset password.", Toast.LENGTH_SHORT).show()
                    } else {
                        auth.sendPasswordResetEmail(email)
                            .addOnSuccessListener {
                                Toast.makeText(context, "📩 Reset link sent to your email.", Toast.LENGTH_SHORT).show()
                            }
                            .addOnFailureListener {
                                Toast.makeText(context, "❌ Failed to send reset link.", Toast.LENGTH_SHORT).show()
                            }
                    }
                }
        )

        AnimatedVisibility(visible = errorMessage != null, enter = fadeIn(), exit = fadeOut()) {
            errorMessage?.let {
                Text(it, color = Color.Red, modifier = Modifier.padding(top = 12.dp))
            }
        }

        if (isLoading) {
            Spacer(modifier = Modifier.height(16.dp))
            CircularProgressIndicator()
        }
    }
}
