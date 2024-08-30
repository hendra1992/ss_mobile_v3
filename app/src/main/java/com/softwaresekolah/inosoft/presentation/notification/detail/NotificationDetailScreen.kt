package com.softwaresekolah.inosoft.presentation.notification.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.softwaresekolah.inosoft.data.notification.Notification
import com.softwaresekolah.inosoft.presentation.notification.detail.component.NotificationDetailTopBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationDetailScreen(
    item: Notification,
    navigateUp: () -> Unit
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    Scaffold (
        topBar = { NotificationDetailTopBar(title = "Notification Detail", scrollBehavior = scrollBehavior, navigateUp = navigateUp) }
    ){padding ->
        Surface(
            Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            Column {
                Text( modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                    text = item.title,
                    style = MaterialTheme.typography.headlineMedium,
                )
                Card (
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 8.dp),
                    colors =  CardDefaults.cardColors(
                        containerColor = Color.White,
                    ),
                    elevation = CardDefaults.elevatedCardElevation(
                        defaultElevation = 3.dp
                    ),
                    shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp)
                            .padding(16.dp)
                    ) {
                        Text(
                            modifier = Modifier.fillMaxWidth()
                                .padding(horizontal = 16.dp)
                                .padding(bottom = 24.dp),
                            text = item.date+", 12 desember | 2024",
                            textAlign = TextAlign.End,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = item.body,
                            style = MaterialTheme.typography.bodyLarge,
                            textAlign = TextAlign.Justify,
                            color = Color.Black
                        )
                    }
                }
            }

        }
    }
}