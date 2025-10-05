package com.jessicatr6.wellnessassistantapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jessicatr6.wellnessassistantapp.ui.theme.WellnessAssistantAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WellnessAssistantAppTheme {
                DashboardScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Wellness Dashboard") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFB3E5FC),
                    titleContentColor = Color.DarkGray
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /* TODO: Add entry or navigation */ },
                containerColor = Color(0xFF4FC3F7)
            ) {
                Text("+") // Simple text instead of icon
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color(0xFFE1F5FE), Color(0xFFB3E5FC))
                    )
                )
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                StatCard(
                    title = "Steps Walked",
                    value = "8,542",
                    backgroundColor = Color(0xFFFFF9C4)
                )
                StatCard(
                    title = "Calories Burned",
                    value = "562 kcal",
                    backgroundColor = Color(0xFFFFCDD2)
                )
                StatCard(
                    title = "Mood",
                    value = "Happy 😊",
                    backgroundColor = Color(0xFFC8E6C9)
                )
                StatCard(
                    title = "Sleep Score",
                    value = "7h 45m",
                    backgroundColor = Color(0xFFD1C4E9)
                )
            }
        }
    }
}

@Composable
fun StatCard(title: String, value: String, backgroundColor: Color) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = title,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                color = Color.DarkGray
            )
            Text(
                text = value,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DashboardPreview() {
    WellnessAssistantAppTheme {
        DashboardScreen()
    }
}
