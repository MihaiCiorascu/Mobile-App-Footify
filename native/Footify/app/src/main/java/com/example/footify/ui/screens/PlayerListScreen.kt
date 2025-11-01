package com.example.footify.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.livedata.observeAsState
import com.example.footify.model.Player
import com.example.footify.ui.components.DeleteConfirmationDialog
import com.example.footify.ui.components.PlayerCard
import com.example.footify.viewmodel.PlayerViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayerListScreen(
    viewModel: PlayerViewModel,
    modifier: Modifier = Modifier,
    onPlayerClick: (Player) -> Unit = {}
) {
    val players by viewModel.filteredPlayers.observeAsState(emptyList())
    val searchQuery by viewModel.searchQuery.observeAsState("")
    
    // State for delete confirmation dialog
    var playerToDelete by remember { mutableStateOf<Player?>(null) }
    
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Hello!",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = "Welcome Back!",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )
        
        Spacer(modifier = Modifier.height(16.dp))


        OutlinedTextField(
            value = searchQuery,
            onValueChange = { query -> viewModel.searchPlayers(query) },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Search for a player") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search"
                )
            },
            singleLine = true,
            shape = MaterialTheme.shapes.medium
        )
        
        Spacer(modifier = Modifier.height(24.dp))


        Text(
            text = "Recent Player Performances",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
        
        Spacer(modifier = Modifier.height(16.dp))


        if (players.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = if (searchQuery.isBlank()) "No players found" else "No players match your search",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(players) { player ->
                    PlayerCard(
                        player = player,
                        onPlayerClick = onPlayerClick,
                        onDeleteClick = { player ->
                            playerToDelete = player
                        }
                    )
                }
            }
        }
    }


    DeleteConfirmationDialog(
        player = playerToDelete,
        onDismiss = { playerToDelete = null },
        onConfirm = {
            // TODO: Implement DELETE
            println("Deleting player: ${playerToDelete?.name}")
            playerToDelete = null
        }
    )
}
