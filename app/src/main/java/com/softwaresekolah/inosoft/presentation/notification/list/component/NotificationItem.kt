package com.softwaresekolah.inosoft.presentation.notification.list.component

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Mail
import androidx.compose.material.icons.filled.MarkEmailRead
import androidx.compose.material.icons.filled.Markunread
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.softwaresekolah.inosoft.data.notification.models.Notification
import com.softwaresekolah.inosoft.data.notification.responses.NotificationListResponse

@Composable
fun NotificationItem(selected: Boolean, item: NotificationListResponse) {
   ListItem(
       colors = if(selected) ListItemDefaults.colors(containerColor = Color.LightGray) else ListItemDefaults.colors(),
       leadingContent = {
           Crossfade(targetState = selected) { selected ->
               if(selected){
                   Icon(
                       imageVector = Icons.Default.Check,
                       contentDescription = "Checked",
                       tint = Color.White,
                       modifier = Modifier
                           .background(color = MaterialTheme.colorScheme.primary, shape = CircleShape)
                           .padding(8.dp)
                   )
               }else{
                   BadgedBox(badge = {
//                       if (!item.notif_read_status){ Badge { Text(" ")}}
                   }) {
                       Icon(
                           imageVector = if (!item.notif_read_status) Icons.Default.Markunread else Icons.Default.MarkEmailRead,
                           contentDescription = item.notif_title,
                           tint = Color.White,
                           modifier = Modifier
                               .background(color = MaterialTheme.colorScheme.primary, shape = CircleShape)
                               .padding(8.dp)
                       )
                   }
               }
           }
        },
       headlineContent = { Text(
           text = item.notif_title,
           style = MaterialTheme.typography.titleMedium
       ) },
       supportingContent = { Text(
           text = item.notif_created,
           style = MaterialTheme.typography.bodyMedium
       ) },
//       trailingContent = { Text(text = item.notif_created, style = MaterialTheme.typography.bodySmall) },
   )
}