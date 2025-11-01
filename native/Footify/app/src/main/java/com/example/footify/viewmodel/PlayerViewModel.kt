package com.example.footify.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.footify.model.Player
import com.example.footify.model.Position
import kotlinx.coroutines.launch
import java.util.Date

class PlayerViewModel : ViewModel() {
    
    private val _players = MutableLiveData<List<Player>>()
    val players: LiveData<List<Player>> = _players
    
    private val _searchQuery = MutableLiveData<String>()
    val searchQuery: LiveData<String> = _searchQuery
    
    private val _filteredPlayers = MutableLiveData<List<Player>>()
    val filteredPlayers: LiveData<List<Player>> = _filteredPlayers
    
    init {
        loadSampleData()
    }
    
    private fun loadSampleData() {
        val samplePlayers = listOf(
            Player(
                id = "1",
                name = "Lionel Messi",
                age = 37,
                position = Position.CENTER_FORWARD,
                rating = 8.7,
                shirtNumber = 10,
                goals = 851,
                image = "",
                createdAt = Date(),
                updatedAt = Date()
            ),
            Player(
                id = "2",
                name = "Paulo Dybala",
                age = 30,
                position = Position.CENTRAL_ATTACKING_MIDFIELDER,
                rating = 8.1,
                shirtNumber = 21,
                goals = 120,
                image = "",
                createdAt = Date(),
                updatedAt = Date()
            ),
            Player(
                id = "3",
                name = "Cristiano Ronaldo",
                age = 39,
                position = Position.STRIKER,
                rating = 7.9,
                shirtNumber = 7,
                goals = 900,
                image = "",
                createdAt = Date(),
                updatedAt = Date()
            ),
            Player(
                id = "4",
                name = "Lamine Yamal",
                age = 17,
                position = Position.RIGHT_WINGER,
                rating = 9.4,
                shirtNumber = 19,
                goals = 5,
                image = "",
                createdAt = Date(),
                updatedAt = Date()
            ),
            Player(
                id = "5",
                name = "Pedri",
                age = 21,
                position = Position.CENTRAL_MIDFIELDER,
                rating = 9.0,
                shirtNumber = 8,
                goals = 15,
                image = "",
                createdAt = Date(),
                updatedAt = Date()
            ),
            Player(
                id = "6",
                name = "Radu Dragusin",
                age = 22,
                position = Position.CENTER_BACK,
                rating = 7.3,
                shirtNumber = 4,
                goals = 2,
                image = "",
                createdAt = Date(),
                updatedAt = Date()
            ),
            Player(
                id = "7",
                name = "Florinel Coman",
                age = 26,
                position = Position.LEFT_MIDFIELDER,
                rating = 8.4,
                shirtNumber = 11,
                goals = 45,
                image = "",
                createdAt = Date(),
                updatedAt = Date()
            )
        )
        
        _players.value = samplePlayers
        _filteredPlayers.value = samplePlayers
    }
    
    fun searchPlayers(query: String) {
        _searchQuery.value = query
        viewModelScope.launch {
            val allPlayers = _players.value ?: emptyList()
            val filtered = if (query.isBlank()) {
                allPlayers
            } else {
                allPlayers.filter { player ->
                    val nameParts = player.name.trim().split(" ", limit = 2)
                    val firstName = nameParts.getOrElse(0) { "" }
                    val lastName = nameParts.getOrElse(1) { "" }

                    firstName.startsWith(query, ignoreCase = true) ||
                    lastName.startsWith(query, ignoreCase = true) ||
                    player.name.startsWith(query, ignoreCase = true)
                }
            }
            _filteredPlayers.value = filtered
        }
    }
    
    fun getPlayerById(id: String): Player? {
        return _players.value?.find { it.id == id }
    }

    fun addPlayer(name: String, age: Int, shirtNumber: Int, position: Position) {
        viewModelScope.launch {
            val currentPlayers = _players.value?.toMutableList() ?: mutableListOf()

            val newId = (currentPlayers.size + 1).toString()

            val newPlayer = Player(
                id = newId,
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

            currentPlayers.add(newPlayer)
            _players.value = currentPlayers

            val currentQuery = _searchQuery.value ?: ""
            searchPlayers(currentQuery)
        }
    }

    fun updatePlayer(updatedPlayer: Player) {
        viewModelScope.launch {
            val currentPlayers = _players.value?.toMutableList() ?: mutableListOf()
            val index = currentPlayers.indexOfFirst { it.id == updatedPlayer.id }
            
            if (index != -1) {
                val playerWithUpdatedTimestamp = updatedPlayer.copy(
                    updatedAt = Date()
                )
                currentPlayers[index] = playerWithUpdatedTimestamp
                _players.value = currentPlayers
                
                val currentQuery = _searchQuery.value ?: ""
                searchPlayers(currentQuery)
            }
        }
    }
    
    fun deletePlayer(playerId: String) {
        viewModelScope.launch {
            val currentPlayers = _players.value?.toMutableList() ?: mutableListOf()
            val filteredList = currentPlayers.filter { it.id != playerId }
            _players.value = filteredList

            val currentQuery = _searchQuery.value ?: ""
            searchPlayers(currentQuery)
        }
    }
}
