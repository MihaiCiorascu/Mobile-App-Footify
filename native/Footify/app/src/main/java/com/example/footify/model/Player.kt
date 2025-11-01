package com.example.footify.model

import java.util.Date

data class Player(
    val id: String = "",
    val name: String = "",
    val age: Int = 0,
    val position: Position = Position.CENTER_FORWARD,
    val rating: Double = 5.0,
    val shirtNumber: Int = 0,
    val goals: Int = 0,
    val image: String = "",
    val image1: String = "",
    val image2: String = "",
    val createdAt: Date = Date(),
    val updatedAt: Date = Date()
)

enum class Position(val displayName: String) {
    GOALKEEPER("Goalkeeper"),
    RIGHT_BACK("Right Back"),
    LEFT_BACK("Left Back"),
    CENTER_BACK("Center Back"),
    CENTRAL_DEFENSIVE_MIDFIELDER("Central Defensive Midfielder"),
    RIGHT_MIDFIELDER("Right Midfielder"),
    LEFT_MIDFIELDER("Left Midfielder"),
    CENTRAL_MIDFIELDER("Central Midfielder"),
    CENTRAL_ATTACKING_MIDFIELDER("Central Attacking Midfielder"),
    RIGHT_WINGER("Right Winger"),
    LEFT_WINGER("Left Winger"),
    CENTER_FORWARD("Center Forward"),
    STRIKER("Striker");
    
    fun getAbbreviation(): String {
        return when (this) {
            GOALKEEPER -> "GK"
            RIGHT_BACK -> "RB"
            LEFT_BACK -> "LB"
            CENTER_BACK -> "CB"
            CENTRAL_DEFENSIVE_MIDFIELDER -> "CDM"
            RIGHT_MIDFIELDER -> "RM"
            LEFT_MIDFIELDER -> "LM"
            CENTRAL_MIDFIELDER -> "CM"
            CENTRAL_ATTACKING_MIDFIELDER -> "CAM"
            RIGHT_WINGER -> "RW"
            LEFT_WINGER -> "LW"
            CENTER_FORWARD -> "CF"
            STRIKER -> "ST"
        }
    }
}
