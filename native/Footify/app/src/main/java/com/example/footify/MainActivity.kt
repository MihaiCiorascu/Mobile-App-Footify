package com.example.footify

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.footify.model.Player
import com.example.footify.ui.components.EditPlayerDialog
import com.example.footify.ui.screens.PlayerListScreen
import com.example.footify.ui.screens.PlayerStatsScreen
import com.example.footify.ui.theme.FootifyTheme
import com.example.footify.viewmodel.PlayerViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        
        try {
            Log.d("Footify", "MainActivity onCreate started")
            setContent {
                FootifyTheme {
                    val viewModel: PlayerViewModel = viewModel()
                    var selectedPlayer by remember { mutableStateOf<Player?>(null) }
                    var showEditDialog by remember { mutableStateOf(false) }
                    
                    if (selectedPlayer != null) {
                        PlayerStatsScreen(
                            player = selectedPlayer!!,
                            onBackClick = { selectedPlayer = null },
                            onEditClick = { 
                                showEditDialog = true
                            }
                        )

                        if (showEditDialog) {
                            EditPlayerDialog(
                                player = selectedPlayer!!,
                                onDismiss = { showEditDialog = false },
                                onSave = { name, shirtNumber, position ->
                                    val updatedPlayer = selectedPlayer!!.copy(
                                        name = name,
                                        shirtNumber = shirtNumber,
                                        position = position
                                    )

                                    viewModel.updatePlayer(updatedPlayer)
                                    
                                    selectedPlayer = updatedPlayer
                                }
                            )
                        }
                    } else {
                        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                            PlayerListScreen(
                                viewModel = viewModel,
                                modifier = Modifier.padding(innerPadding),
                                onPlayerClick = { player ->
                                    selectedPlayer = player
                                }
                            )
                        }
                    }
                }
            }
            Log.d("Footify", "MainActivity onCreate completed successfully")
        } catch (e: Exception) {
            Log.e("Footify", "Error in MainActivity onCreate", e)
            throw e
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PlayerListScreenPreview() {
    FootifyTheme {
        val viewModel: PlayerViewModel = viewModel()
        PlayerListScreen(viewModel = viewModel)
    }
}