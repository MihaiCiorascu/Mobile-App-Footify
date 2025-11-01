package com.example.footify.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.footify.model.Player
import com.example.footify.model.Position

@Composable
fun EditPlayerDialog(
    player: Player,
    onDismiss: () -> Unit,
    onSave: (String, Int, Position) -> Unit = { _, _, _ -> }
) {
    var playerName by remember { mutableStateOf(player.name) }
    var shirtNumber by remember { mutableStateOf(player.shirtNumber.toString()) }
    var selectedPosition by remember { mutableStateOf(player.position) }
    var shirtNumberError by remember { mutableStateOf("") }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true
        )
    ) {
        // Dialog content
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(32.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Title
                    Text(
                        text = "Edit:",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF6A4C93),
                        modifier = Modifier.padding(bottom = 24.dp)
                    )

                    // Player Name Field
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp)
                    ) {
                        Text(
                            text = "Player Name",
                            fontSize = 14.sp,
                            color = Color(0xFF6A4C93),
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        OutlinedTextField(
                            value = playerName,
                            onValueChange = { playerName = it },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(8.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color(0xFF8B7BA6),
                                unfocusedBorderColor = Color(0xFF8B7BA6),
                                focusedTextColor = Color(0xFF6A4C93),
                                unfocusedTextColor = Color(0xFF6A4C93)
                            ),
                            textStyle = androidx.compose.ui.text.TextStyle(
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp
                            )
                        )
                    }

                    // Shirt Number Field
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp)
                    ) {
                        Text(
                            text = "Shirt number",
                            fontSize = 14.sp,
                            color = Color(0xFF6A4C93),
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        OutlinedTextField(
                            value = shirtNumber,
                            onValueChange = { 
                                shirtNumber = it
                                // Validate shirt number
                                val number = it.toIntOrNull()
                                shirtNumberError = when {
                                    it.isEmpty() -> ""
                                    number == null -> "Please enter a valid number"
                                    number < 0 -> "Shirt number must be 0 or greater"
                                    number > 99 -> "Shirt number must be 99 or less"
                                    else -> ""
                                }
                            },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(8.dp),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = if (shirtNumberError.isNotEmpty()) Color.Red else Color(0xFF8B7BA6),
                                unfocusedBorderColor = if (shirtNumberError.isNotEmpty()) Color.Red else Color(0xFF8B7BA6),
                                focusedTextColor = Color(0xFF6A4C93),
                                unfocusedTextColor = Color(0xFF6A4C93)
                            ),
                            textStyle = androidx.compose.ui.text.TextStyle(
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp
                            ),
                            isError = shirtNumberError.isNotEmpty()
                        )
                        
                        // Error message
                        if (shirtNumberError.isNotEmpty()) {
                            Text(
                                text = shirtNumberError,
                                color = Color.Red,
                                fontSize = 12.sp,
                                modifier = Modifier.padding(top = 4.dp)
                            )
                        }
                    }

                    // Position Field
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 24.dp)
                    ) {
                        Text(
                            text = "Position",
                            fontSize = 14.sp,
                            color = Color(0xFF6A4C93),
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        
                        var expanded by remember { mutableStateOf(false) }
                        var textFieldSize by remember { mutableStateOf(IntSize.Zero) }
                        val density = LocalDensity.current
                        
                        Box {
                            OutlinedTextField(
                                value = selectedPosition.getAbbreviation(),
                                onValueChange = { },
                                readOnly = true,
                                trailingIcon = {
                                    Icon(
                                        imageVector = Icons.Default.ArrowDropDown,
                                        contentDescription = "Dropdown",
                                        tint = Color(0xFF6A4C93)
                                    )
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .onGloballyPositioned { coordinates ->
                                        textFieldSize = coordinates.size
                                    },
                                shape = RoundedCornerShape(8.dp),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = Color(0xFF8B7BA6),
                                    unfocusedBorderColor = Color(0xFF8B7BA6),
                                    focusedTextColor = Color(0xFF6A4C93),
                                    unfocusedTextColor = Color(0xFF6A4C93)
                                ),
                                textStyle = androidx.compose.ui.text.TextStyle(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp
                                )
                            )
                            
                            // Invisible clickable overlay
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(56.dp)
                                    .clickable { expanded = !expanded }
                            )
                            
                            // Custom dropdown menu
                            if (expanded) {
                                val scrollState = rememberScrollState()
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(top = with(density) { textFieldSize.height.toDp() + 4.dp })
                                ) {
                                    Surface(
                                        color = Color.White,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .heightIn(max = 200.dp),
                                        shape = RoundedCornerShape(8.dp),
                                        shadowElevation = 8.dp
                                    ) {
                                        Column(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .clip(RoundedCornerShape(8.dp))
                                                .verticalScroll(scrollState)
                                        ) {
                                            Position.values().forEachIndexed { index, position ->
                                                Box(
                                                    modifier = Modifier
                                                        .fillMaxWidth()
                                                        .clickable {
                                                            selectedPosition = position
                                                            expanded = false
                                                        }
                                                        .padding(horizontal = 16.dp, vertical = 12.dp)
                                                ) {
                                                    Text(
                                                        text = position.getAbbreviation(),
                                                        fontWeight = FontWeight.Bold,
                                                        color = Color(0xFF6A4C93),
                                                        fontSize = 16.sp
                                                    )
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }

                    // Save Changes Button
                    Button(
                        onClick = {
                            if (shirtNumberError.isEmpty()) {
                                val number = shirtNumber.toIntOrNull() ?: player.shirtNumber
                                onSave(playerName, number, selectedPosition)
                                onDismiss()
                            }
                        },
                        enabled = shirtNumberError.isEmpty(),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp),
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White,
                            contentColor = Color(0xFF6A4C93)
                        ),
                        border = androidx.compose.foundation.BorderStroke(
                            width = 1.dp,
                            color = Color(0xFF8B7BA6)
                        )
                    ) {
                        Text(
                            text = "SAVE CHANGES",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
    }
}
