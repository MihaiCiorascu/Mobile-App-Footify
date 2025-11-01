package com.example.footify.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.footify.model.Player

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayerStatsScreen(
    player: Player,
    onBackClick: () -> Unit = {},
    onEditClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        TopAppBar(
            title = { },
            navigationIcon = {
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color(0xFF6A4C93)
            )
        )
        
        // Header background
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = Color(0xFF6A4C93),
                    shape = RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp)
                )
                .padding(24.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Player Info (Left)
                Column {
                    // Split name into first and last name
                    val nameParts = player.name.trim().split(" ", limit = 2)
                    val firstName = nameParts.getOrElse(0) { "" }
                    val lastName = nameParts.getOrElse(1) { "" }
                    
                    // First Name
                    Text(
                        text = firstName,
                        color = Color.White,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold
                    )
                    // Last Name (only if it exists)
                    if (lastName.isNotEmpty()) {
                        Text(
                            text = lastName,
                            color = Color.White,
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Text(
                        text = "#${player.shirtNumber}",
                        color = Color(0xFFFFD700),
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
                
                // Player Image (Right)
                Box(
                    modifier = Modifier
                        .size(120.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color.White)
                ) {
                    if (player.image.isNotEmpty()) {
                        AsyncImage(
                            model = player.image,
                            contentDescription = player.name,
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(RoundedCornerShape(16.dp)),
                            contentScale = ContentScale.Crop
                        )
                    } else {
                        // Default Icon image when player has no image
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Player placeholder",
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(24.dp),
                            tint = Color.Gray
                        )
                    }
                }
            }
            
            // Edit Button
            IconButton(
                onClick = onEditClick,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .background(
                        color = Color(0xFF8B7BA6),
                        shape = RoundedCornerShape(8.dp)
                    )
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit player",
                    tint = Color(0xFFFFD700),
                    modifier = Modifier.size(20.dp)
                )
            }
        }
        
        // Stats section
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            StatCard(
                value = player.goals.toString(),
                label = "Goals",
                modifier = Modifier.weight(1f)
            )
            
            Spacer(modifier = Modifier.width(8.dp))

            StatCard(
                value = player.age.toString(),
                label = "Age",
                modifier = Modifier.weight(1f)
            )
            
            Spacer(modifier = Modifier.width(8.dp))

            StatCard(
                value = player.position.getAbbreviation(),
                label = "Position",
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun StatCard(
    value: String,
    label: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = value,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Text(
                text = label,
                fontSize = 14.sp,
                color = Color.Gray
            )
        }
    }
}

