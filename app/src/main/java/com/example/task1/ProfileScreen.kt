package com.example.task1

import android.app.DatePickerDialog
import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import java.util.*

@Composable
fun ProfileScreen(navController: NavController) {
    var selectedDate by remember { mutableStateOf("Select Date") }
    val context = LocalContext.current
    var showLogoutDialog by remember { mutableStateOf(false) }

    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    val datePickerDialog = DatePickerDialog(
        context,
        { _, selectedYear: Int, selectedMonth: Int, selectedDay: Int ->
            selectedDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
        }, year, month, day
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(24.dp))

        Image(
            painter = painterResource(id = R.drawable.ic_profile_picture),
            contentDescription = "Profile Picture",
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
                .background(Color.LightGray)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Ella Lee", fontSize = 26.sp, color = MaterialTheme.colorScheme.onBackground)
        Text(text = "ellalee@gmail.com", fontSize = 18.sp, color = Color.Gray)

        Spacer(modifier = Modifier.height(24.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                ProfileInfoItem("Username", "EllaLee123", Icons.Default.Person)
                Divider(thickness = 1.dp, color = Color.Gray.copy(alpha = 0.4f))
                ProfileInfoItem("Email", "ellalee@gmail.com", Icons.Default.Email)
                Divider(thickness = 1.dp, color = Color.Gray.copy(alpha = 0.4f))
                ProfileInfoItem(
                    "Date of Birth", selectedDate, Icons.Default.CalendarMonth,
                    onClick = { datePickerDialog.show() }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 🔹 Настройки
        Button(
            onClick = { navController.navigate("settings") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(text = "Settings", fontSize = 18.sp)
        }

        Spacer(modifier = Modifier.height(8.dp))
        // 🔹 Выход (с подтверждением)
        OutlinedButton(
            onClick = { showLogoutDialog = true },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Red),
            shape = RoundedCornerShape(12.dp)
        ) {
            Icon(imageVector = Icons.Default.Logout, contentDescription = "Logout", tint = Color.Red)
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "Log Out", fontSize = 18.sp, color = Color.Red)
        }
    }

    // 🔥 Диалог подтверждения выхода
    if (showLogoutDialog) {
        LogoutDialog(
            onDismiss = { showLogoutDialog = false },
            onConfirm = { showLogoutDialog = false } // TODO: Добавить логику выхода
        )
    }
}

// 📌 Информация в профиле с иконками
@Composable
fun ProfileInfoItem(title: String, value: String, icon: ImageVector, onClick: (() -> Unit)? = null) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable(enabled = onClick != null) { onClick?.invoke() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(imageVector = icon, contentDescription = title, tint = MaterialTheme.colorScheme.primary)
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            Text(text = title, fontSize = 14.sp, color = Color.Gray)
            Text(text = value, fontSize = 18.sp, color = MaterialTheme.colorScheme.onBackground)
        }
    }
}

// 📌 Диалог подтверждения выхода
@Composable
fun LogoutDialog(onDismiss: () -> Unit, onConfirm: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "Log Out", fontSize = 22.sp) },
        text = { Text("Are you sure you want to log out?", fontSize = 18.sp) },
        confirmButton = {
            Button(onClick = onConfirm) {
                Text("Yes", fontSize = 18.sp)
            }
        },
        dismissButton = {
            OutlinedButton(onClick = onDismiss) {
                Text("Cancel", fontSize = 18.sp)
            }
        }
    )
}