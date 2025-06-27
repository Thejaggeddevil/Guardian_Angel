package com.mansi.guardianangel
//
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.text.KeyboardOptions
//import androidx.compose.material3.*
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.text.input.*
//import androidx.compose.ui.text.style.TextAlign
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.material3.*
//import androidx.compose.ui.tooling.preview.Preview
//
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun SignupScreen() {
//    val fullName = remember { mutableStateOf("") }
//    val phoneNumber = remember { mutableStateOf("") }
//    val email = remember { mutableStateOf("") }
//    val language = remember { mutableStateOf("English") }
//    val password = remember { mutableStateOf("") }
//    val confirmPassword = remember { mutableStateOf("") }
//    val passwordVisible = remember { mutableStateOf(false) }
//    val confirmPasswordVisible = remember { mutableStateOf(false) }
//
//
//    val languageOptions = listOf("English", "Hindi")
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(Color(0xFFE8F5E9)) // light green
//            .padding(16.dp),
//        verticalArrangement = Arrangement.Center,
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//
//        Text(
//            text = "Create Account",
//            fontSize = 24.sp,
//            color = Color(0xFF1A1B41),
//            fontWeight = FontWeight.Bold
//        )
//
//        Spacer(modifier = Modifier.height(24.dp))
//
//        // Full Name
//        OutlinedTextField(
//            value = fullName.value,
//            onValueChange = { fullName.value = it },
//            label = { Text("Full Name") },
//            leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) },
//            modifier = Modifier.fillMaxWidth()
//        )
//
//        Spacer(modifier = Modifier.height(12.dp))
//
//        // Phone Number
//        OutlinedTextField(
//            value = phoneNumber.value,
//            onValueChange = { phoneNumber.value = it },
//            label = { Text("Phone Number") },
//            leadingIcon = { Icon(Icons.Default.Phone, contentDescription = null) },
//            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Phone),
//            modifier = Modifier.fillMaxWidth()
//        )
//
//        Spacer(modifier = Modifier.height(12.dp))
//
//        // Email
//        OutlinedTextField(
//            value = email.value,
//            onValueChange = { email.value = it },
//            label = { Text("Email") },
//            leadingIcon = { Icon(Icons.Default.Email, contentDescription = null) },
//            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Email),
//            modifier = Modifier.fillMaxWidth()
//        )
//
//        Spacer(modifier = Modifier.height(12.dp))
//
//        // Preferred Language Dropdown
//        var expanded by remember { mutableStateOf(false) }
//
//        ExposedDropdownMenuBox(
//            expanded = expanded,
//            onExpandedChange = { expanded = !expanded }
//        ) {
//            OutlinedTextField(
//                readOnly = true,
//                value = language.value,
//                onValueChange = {},
//                label = { Text("Preferred Language") },
//                leadingIcon = { Icon(Icons.Default.Language, contentDescription = null) },
//                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
//                modifier = Modifier.fillMaxWidth()
//            )
//
//            ExposedDropdownMenu(
//                expanded = expanded,
//                onDismissRequest = { expanded = false }
//            ) {
//                languageOptions.forEach { selection ->
//                    DropdownMenuItem(
//                        text = { Text(selection) },
//                        onClick = {
//                            language.value = selection
//                            expanded = false
//                        }
//                    )
//                }
//            }
//        }
//
//        Spacer(modifier = Modifier.height(12.dp))
//
//        // Password
//        OutlinedTextField(
//            value = password.value,
//            onValueChange = { password.value = it },
//            label = { Text("Password") },
//            leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
//            trailingIcon = {
//                IconButton(onClick = { passwordVisible.value = !passwordVisible.value }) {
//                    Icon(
//                        imageVector = if (passwordVisible.value) Icons.Default.Visibility else Icons.Default.VisibilityOff,
//                        contentDescription = "Toggle password visibility"
//                    )
//                }
//            },
//            visualTransformation = if (passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
//            modifier = Modifier.fillMaxWidth()
//        )
//
//        Spacer(modifier = Modifier.height(12.dp))
//
//        // Confirm Password
//        OutlinedTextField(
//            value = confirmPassword.value,
//            onValueChange = { confirmPassword.value = it },
//            label = { Text("Confirm Password") },
//            leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
//            trailingIcon = {
//                IconButton(onClick = {
//                    confirmPasswordVisible.value = !confirmPasswordVisible.value
//                }) {
//                    Icon(
//                        imageVector = if (confirmPasswordVisible.value) Icons.Default.Visibility else Icons.Default.VisibilityOff,
//                        contentDescription = "Toggle confirm password visibility"
//                    )
//                }
//            },
//            visualTransformation = if (confirmPasswordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
//            modifier = Modifier.fillMaxWidth()
//        )
//
//        Spacer(modifier = Modifier.height(24.dp))
//
//        // Sign Up Button
//        Button(
//            onClick = {
//                // Perform Sign Up Logic
//            },
//            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFEF5350)), // red
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(50.dp)
//        ) {
//            Text(text = "Sign Up", color = Color.White, fontWeight = FontWeight.Bold)
//        }
//
//        Spacer(modifier = Modifier.height(8.dp))
//
//        Text(
//            text = "By SignUp you are agreeing to our Terms and conditions",
//            fontSize = 12.sp,
//            color = Color.Gray,
//            textAlign = TextAlign.Center,
//            modifier = Modifier.padding(horizontal = 8.dp)
//        )
//    }
//}
//@Preview(showBackground =  true)
//@Composable
//fun SignupScreenPreview() {
//    SignupScreen()
//
//}