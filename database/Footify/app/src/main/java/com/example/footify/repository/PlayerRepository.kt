package com.example.footify.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import com.example.footify.data.AppDatabase
import com.example.footify.data.PlayerEntity
import com.example.footify.data.PlayerMapper
import com.example.footify.model.Player
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PlayerRepository(context: Context) {
    private val database = AppDatabase.getDatabase(context)
    private val playerDao = database.playerDao()
    
    // Error LiveData for read operation failures
    private val _readError = MutableLiveData<String?>()
    val readError: LiveData<String?> = _readError

    // Get all players as LiveData - retrieved only once and reused
    // Wrapped with error handling to catch database query failures
    fun getAllPlayers(): LiveData<List<Player>> {
        val result = MediatorLiveData<List<Player>>()
        
        try {
            // Get the LiveData from Room
            val roomLiveData = playerDao.getAllPlayers()
            
            // Map entities to domain models with error handling
            val mappedLiveData = roomLiveData.map { entities ->
                try {
                    PlayerMapper.toDomainList(entities)
                } catch (e: Exception) {
                    Log.e("PlayerRepository", "Error mapping entities to domain models", e)
                    _readError.postValue("Failed to process player data: ${e.message}")
                    emptyList()
                }
            }
            
            // Observe the mapped LiveData with error handling
            result.addSource(mappedLiveData) { players ->
                try {
                    result.value = players
                    // Clear error on successful read
                    _readError.postValue(null)
                } catch (e: Exception) {
                    Log.e("PlayerRepository", "Error retrieving players from database", e)
                    _readError.postValue("Failed to retrieve players: ${e.message}")
                    result.value = emptyList()
                }
            }
        } catch (e: Exception) {
            // Catch errors when creating the query (database corruption, etc.)
            Log.e("PlayerRepository", "Error creating player query", e)
            _readError.postValue("Failed to access database: ${e.message}")
            result.value = emptyList()
        }
        
        return result
    }

    // Get player by ID in a coroutine
    suspend fun getPlayerById(playerId: Long): Player? {
        return try {
            withContext(Dispatchers.IO) {
                val entity = playerDao.getPlayerById(playerId)
                entity?.let { PlayerMapper.toDomain(it) }
            }
        } catch (e: Exception) {
            Log.e("PlayerRepository", "Error getting player by ID: $playerId", e)
            throw e
        }
    }

    // Insert player in a coroutine
    suspend fun insertPlayer(player: Player): Result<Long> {
        return try {
            withContext(Dispatchers.IO) {
                val entity = PlayerMapper.toEntity(player)
                val id = playerDao.insertPlayer(entity)
                Result.success(id)
            }
        } catch (e: Exception) {
            Log.e("PlayerRepository", "Error inserting player: ${player.name}", e)
            Result.failure(e)
        }
    }

    // Update player in a coroutine
    suspend fun updatePlayer(player: Player): Result<Unit> {
        return try {
            withContext(Dispatchers.IO) {
                val entity = PlayerMapper.toEntity(player)
                playerDao.updatePlayer(entity)
                Result.success(Unit)
            }
        } catch (e: Exception) {
            Log.e("PlayerRepository", "Error updating player: ${player.name}", e)
            Result.failure(e)
        }
    }

    // Delete player by ID in a coroutine
    suspend fun deletePlayerById(playerId: Long): Result<Unit> {
        return try {
            withContext(Dispatchers.IO) {
                playerDao.deletePlayerById(playerId)
                Result.success(Unit)
            }
        } catch (e: Exception) {
            Log.e("PlayerRepository", "Error deleting player: $playerId", e)
            Result.failure(e)
        }
    }
}

