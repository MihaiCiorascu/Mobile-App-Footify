package com.example.footify.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.footify.model.Player
import com.example.footify.model.Position
import com.example.footify.repository.PlayerRepository
import kotlinx.coroutines.launch
import java.util.Date

class PlayerViewModel(application: Application) : AndroidViewModel(application) {
    
    private val repository = PlayerRepository(application)
    
    private val _playersSource: LiveData<List<Player>> = repository.getAllPlayers()
    
    private val _readError = MutableLiveData<String?>()
    val readError: LiveData<String?> = _readError
    
    private val _searchQuery = MutableLiveData<String>("")
    val searchQuery: LiveData<String> = _searchQuery
    
    init {
        repository.readError.observeForever { error ->
            if (error != null) {
                Log.e("PlayerViewModel", "Read operation error: $error")
                _readError.value = error
                _errorMessage.value = "Failed to load players: $error"
            }
        }
    }
    
    val filteredPlayers: LiveData<List<Player>> = MediatorLiveData<List<Player>>().apply {
        fun updateFiltered() {
            val allPlayers = _playersSource.value ?: emptyList()
            val query = _searchQuery.value ?: ""
            val filtered = if (query.isBlank()) {
                allPlayers
            } else {
                filterPlayers(allPlayers, query)
            }
            value = filtered
        }
        
        addSource(_playersSource) { updateFiltered() }
        addSource(_searchQuery) { updateFiltered() }
    }
    
    val players: LiveData<List<Player>> = _playersSource
    
    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage
    
    private var isInitialized = false
    
    init {
        // Data is retrieved in a coroutine (separate thread) via Room and observed via LiveData
        viewModelScope.launch {
            try {
                // Observe players once to check if database is empty
                var observer: androidx.lifecycle.Observer<List<Player>>? = null
                observer = androidx.lifecycle.Observer<List<Player>> { players ->
                    if (players.isEmpty() && !isInitialized) {
                        loadSampleData()
                        isInitialized = true
                        observer?.let { _playersSource.removeObserver(it) }
                    } else if (players.isNotEmpty()) {
                        isInitialized = true
                        observer?.let { _playersSource.removeObserver(it) }
                    }
                }
                observer.let { _playersSource.observeForever(it) }
            } catch (e: Exception) {
                Log.e("PlayerViewModel", "Error in initialization", e)
                _errorMessage.value = "Failed to load players: ${e.message}"
            }
        }
    }
    
    private fun loadSampleData() {
        viewModelScope.launch {
            try {
                val baseTime = Date()

                val samplePlayers = listOf(
                    Player(
                        id = "",
                        name = "Lionel Messi",
                        age = 37,
                        position = Position.CENTER_FORWARD,
                        rating = 8.7,
                        shirtNumber = 10,
                        goals = 851,
                        image = "",
                        createdAt = Date(baseTime.time + 7000),
                        updatedAt = Date(baseTime.time + 7000)
                    ),
                    Player(
                        id = "",
                        name = "Paulo Dybala",
                        age = 30,
                        position = Position.CENTRAL_ATTACKING_MIDFIELDER,
                        rating = 8.1,
                        shirtNumber = 21,
                        goals = 120,
                        image = "",
                        createdAt = Date(baseTime.time + 6000),
                        updatedAt = Date(baseTime.time + 6000)
                    ),
                    Player(
                        id = "",
                        name = "Cristiano Ronaldo",
                        age = 39,
                        position = Position.STRIKER,
                        rating = 7.9,
                        shirtNumber = 7,
                        goals = 900,
                        image = "",
                        createdAt = Date(baseTime.time + 5000),
                        updatedAt = Date(baseTime.time + 5000)
                    ),
                    Player(
                        id = "",
                        name = "Lamine Yamal",
                        age = 17,
                        position = Position.RIGHT_WINGER,
                        rating = 9.4,
                        shirtNumber = 19,
                        goals = 5,
                        image = "",
                        createdAt = Date(baseTime.time + 4000),
                        updatedAt = Date(baseTime.time + 4000)
                    ),
                    Player(
                        id = "",
                        name = "Pedri",
                        age = 21,
                        position = Position.CENTRAL_MIDFIELDER,
                        rating = 9.0,
                        shirtNumber = 8,
                        goals = 15,
                        image = "",
                        createdAt = Date(baseTime.time + 3000),
                        updatedAt = Date(baseTime.time + 3000)
                    ),
                    Player(
                        id = "",
                        name = "Radu Dragusin",
                        age = 22,
                        position = Position.CENTER_BACK,
                        rating = 7.3,
                        shirtNumber = 4,
                        goals = 2,
                        image = "",
                        createdAt = Date(baseTime.time + 2000),
                        updatedAt = Date(baseTime.time + 2000)
                    ),
                    Player(
                        id = "",
                        name = "Florinel Coman",
                        age = 26,
                        position = Position.LEFT_MIDFIELDER,
                        rating = 8.4,
                        shirtNumber = 11,
                        goals = 45,
                        image = "",
                        createdAt = Date(baseTime.time + 1000),
                        updatedAt = Date(baseTime.time + 1000)
                    )
                )
                
                samplePlayers.forEach { player ->
                    repository.insertPlayer(player).fold(
                        onSuccess = { /* Successfully inserted */ },
                        onFailure = { e ->
                            Log.e("PlayerViewModel", "Error inserting sample player: ${player.name}", e)
                        }
                    )
                }
            } catch (e: Exception) {
                Log.e("PlayerViewModel", "Error loading sample data", e)
                _errorMessage.value = "Failed to load sample data: ${e.message}"
            }
        }
    }
    
    fun searchPlayers(query: String) {
        _searchQuery.value = query
    }
    
    private fun filterPlayers(players: List<Player>, query: String): List<Player> {
        return players.filter { player ->
            val nameParts = player.name.trim().split(" ", limit = 2)
            val firstName = nameParts.getOrElse(0) { "" }
            val lastName = nameParts.getOrElse(1) { "" }

            firstName.startsWith(query, ignoreCase = true) ||
            lastName.startsWith(query, ignoreCase = true) ||
            player.name.startsWith(query, ignoreCase = true)
        }
    }
    
    fun getPlayerById(id: String): Player? {
        return players.value?.find { it.id == id }
    }

    fun addPlayer(name: String, age: Int, shirtNumber: Int, position: Position) {
        viewModelScope.launch {
            try {
                val newPlayer = Player(
                    id = "",
                    name = name,
                    age = age,
                    position = position,
                    rating = 5.0,
                    shirtNumber = shirtNumber,
                    goals = 0,
                    image = "",
                    createdAt = Date(),
                    updatedAt = Date()
                )

                repository.insertPlayer(newPlayer).fold(
                    onSuccess = { 
                        Log.d("PlayerViewModel", "Player added successfully: $name")
                    },
                    onFailure = { e ->
                        Log.e("PlayerViewModel", "Error adding player: $name", e)
                        _errorMessage.value = "Failed to add player: ${e.message}"
                    }
                )
            } catch (e: Exception) {
                Log.e("PlayerViewModel", "Error adding player", e)
                _errorMessage.value = "Failed to add player: ${e.message}"
            }
        }
    }

    fun updatePlayer(updatedPlayer: Player) {
        viewModelScope.launch {
            try {
                val playerWithUpdatedTimestamp = updatedPlayer.copy(
                    updatedAt = Date()
                )
                
                repository.updatePlayer(playerWithUpdatedTimestamp).fold(
                    onSuccess = {
                        Log.d("PlayerViewModel", "Player updated successfully: ${updatedPlayer.name}")
                    },
                    onFailure = { e ->
                        Log.e("PlayerViewModel", "Error updating player: ${updatedPlayer.name}", e)
                        _errorMessage.value = "Failed to update player: ${e.message}"
                    }
                )
            } catch (e: Exception) {
                Log.e("PlayerViewModel", "Error updating player", e)
                _errorMessage.value = "Failed to update player: ${e.message}"
            }
        }
    }
    
    fun deletePlayer(playerId: String) {
        viewModelScope.launch {
            try {
                val playerIdLong = try {
                    playerId.toLong()
                } catch (e: NumberFormatException) {
                    Log.e("PlayerViewModel", "Invalid player ID: $playerId", e)
                    _errorMessage.value = "Invalid player ID"
                    return@launch
                }
                
                repository.deletePlayerById(playerIdLong).fold(
                    onSuccess = {
                        Log.d("PlayerViewModel", "Player deleted successfully: $playerId")
                    },
                    onFailure = { e ->
                        Log.e("PlayerViewModel", "Error deleting player: $playerId", e)
                        _errorMessage.value = "Failed to delete player: ${e.message}"
                    }
                )
            } catch (e: Exception) {
                Log.e("PlayerViewModel", "Error deleting player", e)
                _errorMessage.value = "Failed to delete player: ${e.message}"
            }
        }
    }
    
    fun clearError() {
        _errorMessage.value = null
        _readError.value = null
    }
    
    override fun onCleared() {
        super.onCleared()
    }
}
