package com.example.footify.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.livedata.observeAsState
import com.example.footify.model.Player
import com.example.footify.ui.components.AddPlayerDialog
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
    val errorMessage by viewModel.errorMessage.observeAsState(null)
    
    var showAddDialog by remember { mutableStateOf(false) }
    var playerToDelete by remember { mutableStateOf<Player?>(null) }
    
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            Column {
                Text(
                    text = "Hello!",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = "Welcome Back!",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            }
            
            IconButton(
                onClick = { showAddDialog = true },
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .background(
                        color = MaterialTheme.colorScheme.primary,
                        shape = RoundedCornerShape(8.dp)
                    )
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add player",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))


        OutlinedTextField(
            value = searchQuery,
            onValueChange = { query -> viewModel.searchPlayers(query) },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Search for a player") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                    tint = MaterialTheme.colorScheme.primary
                )
            },
            singleLine = true,
            shape = RoundedCornerShape(8.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.secondary,
                unfocusedBorderColor = MaterialTheme.colorScheme.secondary,
                focusedTextColor = MaterialTheme.colorScheme.primary,
                unfocusedTextColor = MaterialTheme.colorScheme.primary
            )
        )
        
        Spacer(modifier = Modifier.height(24.dp))


        Text(
            text = "Recent Player Performances",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
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


    if (showAddDialog) {
        AddPlayerDialog(
            onDismiss = { showAddDialog = false },
            onAdd = { name, age, shirtNumber, position ->
                viewModel.addPlayer(name, age, shirtNumber, position)
            }
        )
    }

    DeleteConfirmationDialog(
        player = playerToDelete,
        onDismiss = { playerToDelete = null },
        onConfirm = {
            playerToDelete?.id?.let { playerId ->
                viewModel.deletePlayer(playerId)
            }
            playerToDelete = null
        }
    )

    errorMessage?.let { error ->
        AlertDialog(
            onDismissRequest = { viewModel.clearError() },
            title = { Text("Error") },
            text = { Text(error) },
            confirmButton = {
                TextButton(onClick = { viewModel.clearError() }) {
                    Text("OK")
                }
            }
        )
    }
}
