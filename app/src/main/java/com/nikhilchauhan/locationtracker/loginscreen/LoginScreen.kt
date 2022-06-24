package com.nikhilchauhan.locationtracker.loginscreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(loginViewModel: LoginViewModel = hiltViewModel()) {
  val scope = rememberCoroutineScope()
  Column(
    modifier = Modifier.fillMaxSize().padding(16.dp),
    verticalArrangement = Arrangement.Center
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
    ) {
      HeadingText(headingText = "Name")
      TextInputField(label = "Name", text = "", onTextChanged = {})
    }
    Spacer(modifier = Modifier.fillMaxSize(0.1F))
    Column(
      modifier = Modifier
        .fillMaxWidth()
    ) {
      HeadingText(headingText = "Mobile Number")
      TextInputField(label = "Mobile Number", text = "", onTextChanged = {})
    }
    Spacer(modifier = Modifier.fillMaxSize(0.1F))
    LoginButton(
      text = "Login", {
      scope.launch(Dispatchers.IO) {
        loginViewModel.login()
      }
    }, modifier = Modifier
      .fillMaxWidth()
      .align(Alignment.CenterHorizontally)
    )
  }
}

@Composable
fun LoginButton(
  text: String,
  onLoginButtonClick: () -> Unit,
  modifier: Modifier
) {
  Button(onClick = {
    onLoginButtonClick()
  }) {
    Text(
      text = text, style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold),
      modifier = modifier, textAlign = TextAlign.Center
    )
  }
}

@Composable
fun HeadingText(headingText: String) {
  Text(text = headingText)
}

@Composable
fun TextInputField(
  label: String,
  text: String,
  onTextChanged: (String) -> Unit
) {
  TextField(
    value = "", onValueChange = {
    onTextChanged(it)
  },
    modifier = Modifier.fillMaxWidth(), label = {
    Text(text = label)
  }
  )
}