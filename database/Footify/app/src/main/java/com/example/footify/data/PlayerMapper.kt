package com.example.footify.data

import com.example.footify.model.Player

object PlayerMapper {
    fun toEntity(player: Player): PlayerEntity {
        val id = if (player.id.isNotEmpty()) {
            try {
                player.id.toLong()
            } catch (_: NumberFormatException) {
                0L
            }
        } else {
            0L
        }
        
        return PlayerEntity(
            id = id,
            name = player.name,
            age = player.age,
            position = player.position,
            rating = player.rating,
            shirtNumber = player.shirtNumber,
            goals = player.goals,
            image = player.image,
            image1 = player.image1,
            image2 = player.image2,
            createdAt = player.createdAt,
            updatedAt = player.updatedAt
        )
    }

    fun toDomain(entity: PlayerEntity): Player {
        return Player(
            id = entity.id.toString(),
            name = entity.name,
            age = entity.age,
            position = entity.position,
            rating = entity.rating,
            shirtNumber = entity.shirtNumber,
            goals = entity.goals,
            image = entity.image,
            image1 = entity.image1,
            image2 = entity.image2,
            createdAt = entity.createdAt,
            updatedAt = entity.updatedAt
        )
    }

    fun toDomainList(entities: List<PlayerEntity>): List<Player> {
        return entities.map { toDomain(it) }
    }
}

