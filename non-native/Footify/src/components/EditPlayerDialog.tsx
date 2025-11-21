import React, {useState} from 'react';
import {
  View,
  Text,
  StyleSheet,
  Modal,
  TouchableOpacity,
  TextInput,
  ScrollView,
  TouchableWithoutFeedback,
} from 'react-native';
import {Player, Position, PositionAbbreviations} from '../types/Player';
import {Colors} from '../theme/colors';

interface EditPlayerDialogProps {
  player: Player;
  visible: boolean;
  onDismiss: () => void;
  onSave: (name: string, shirtNumber: number, position: Position) => void;
}

export const EditPlayerDialog: React.FC<EditPlayerDialogProps> = ({
  player,
  visible,
  onDismiss,
  onSave,
}) => {
  const [playerName, setPlayerName] = useState(player.name);
  const [shirtNumber, setShirtNumber] = useState(player.shirtNumber.toString());
  const [selectedPosition, setSelectedPosition] = useState(player.position);
  const [shirtNumberError, setShirtNumberError] = useState('');
  const [positionDropdownOpen, setPositionDropdownOpen] = useState(false);

  const handleShirtNumberChange = (value: string) => {
    setShirtNumber(value);
    const number = parseInt(value, 10);
    if (value === '') {
      setShirtNumberError('');
    } else if (isNaN(number)) {
      setShirtNumberError('Please enter a valid number');
    } else if (number < 0) {
      setShirtNumberError('Shirt number must be 0 or greater');
    } else if (number > 99) {
      setShirtNumberError('Shirt number must be 99 or less');
    } else {
      setShirtNumberError('');
    }
  };

  const handleSave = () => {
    if (shirtNumberError === '' && playerName.trim() !== '' && shirtNumber !== '') {
      const number = parseInt(shirtNumber, 10) || player.shirtNumber;
      onSave(playerName.trim(), number, selectedPosition);
      onDismiss();
    }
  };

  return (
    <Modal
      visible={visible}
      transparent={true}
      animationType="fade"
      onRequestClose={onDismiss}>
      <TouchableWithoutFeedback onPress={onDismiss}>
        <View style={styles.modalOverlay}>
          <TouchableWithoutFeedback onPress={() => {}}>
            <View style={styles.dialogContainer}>
              <ScrollView
                contentContainerStyle={styles.dialogContent}
                showsVerticalScrollIndicator={false}>
                <Text style={styles.dialogTitle}>Edit:</Text>

                <View style={styles.fieldContainer}>
                  <Text style={styles.fieldLabel}>Player Name</Text>
                  <TextInput
                    style={styles.textInput}
                    value={playerName}
                    onChangeText={setPlayerName}
                    placeholder="Enter player name"
                    placeholderTextColor={Colors.gray}
                  />
                </View>

                <View style={styles.fieldContainer}>
                  <Text style={styles.fieldLabel}>Shirt number</Text>
                  <TextInput
                    style={[
                      styles.textInput,
                      shirtNumberError ? styles.textInputError : null,
                    ]}
                    value={shirtNumber}
                    onChangeText={handleShirtNumberChange}
                    keyboardType="numeric"
                    placeholder="Enter shirt number"
                    placeholderTextColor={Colors.gray}
                  />
                  {shirtNumberError ? (
                    <Text style={styles.errorText}>{shirtNumberError}</Text>
                  ) : null}
                </View>

                <View style={styles.fieldContainer}>
                  <Text style={styles.fieldLabel}>Position</Text>
                  <TouchableOpacity
                    style={styles.positionSelector}
                    onPress={() => setPositionDropdownOpen(!positionDropdownOpen)}>
                    <Text style={styles.positionSelectorText}>
                      {PositionAbbreviations[selectedPosition]}
                    </Text>
                    <Text style={styles.dropdownArrow}>▼</Text>
                  </TouchableOpacity>

                  {positionDropdownOpen && (
                    <View style={styles.positionDropdown}>
                      <ScrollView
                        style={styles.positionList}
                        nestedScrollEnabled={true}>
                        {Object.values(Position).map(position => (
                          <TouchableOpacity
                            key={position}
                            style={styles.positionOption}
                            onPress={() => {
                              setSelectedPosition(position);
                              setPositionDropdownOpen(false);
                            }}>
                            <Text style={styles.positionOptionText}>
                              {PositionAbbreviations[position]}
                            </Text>
                          </TouchableOpacity>
                        ))}
                      </ScrollView>
                    </View>
                  )}
                </View>

                <TouchableOpacity
                  style={[
                    styles.saveButton,
                    shirtNumberError || playerName.trim() === '' || shirtNumber === ''
                      ? styles.saveButtonDisabled
                      : null,
                  ]}
                  onPress={handleSave}
                  disabled={
                    shirtNumberError !== '' ||
                    playerName.trim() === '' ||
                    shirtNumber === ''
                  }>
                  <Text style={styles.saveButtonText}>SAVE CHANGES</Text>
                </TouchableOpacity>
              </ScrollView>
            </View>
          </TouchableWithoutFeedback>
        </View>
      </TouchableWithoutFeedback>
    </Modal>
  );
};

const styles = StyleSheet.create({
  modalOverlay: {
    flex: 1,
    backgroundColor: 'rgba(0, 0, 0, 0.5)',
    justifyContent: 'center',
    alignItems: 'center',
  },
  dialogContainer: {
    backgroundColor: Colors.white,
    borderRadius: 16,
    width: '90%',
    maxHeight: '80%',
    elevation: 8,
    shadowColor: '#000',
    shadowOffset: {width: 0, height: 4},
    shadowOpacity: 0.3,
    shadowRadius: 8,
  },
  dialogContent: {
    padding: 24,
  },
  dialogTitle: {
    fontSize: 20,
    fontWeight: 'bold',
    color: Colors.primary,
    marginBottom: 24,
  },
  fieldContainer: {
    marginBottom: 16,
  },
  fieldLabel: {
    fontSize: 14,
    color: Colors.primary,
    marginBottom: 8,
  },
  textInput: {
    borderWidth: 1,
    borderColor: Colors.secondary,
    borderRadius: 8,
    padding: 12,
    fontSize: 16,
    fontWeight: 'bold',
    color: Colors.primary,
  },
  textInputError: {
    borderColor: Colors.ratingRed,
  },
  errorText: {
    color: Colors.ratingRed,
    fontSize: 12,
    marginTop: 4,
  },
  positionSelector: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
    borderWidth: 1,
    borderColor: Colors.secondary,
    borderRadius: 8,
    padding: 12,
  },
  positionSelectorText: {
    fontSize: 16,
    fontWeight: 'bold',
    color: Colors.primary,
  },
  dropdownArrow: {
    fontSize: 12,
    color: Colors.primary,
  },
  positionDropdown: {
    marginTop: 4,
    borderWidth: 1,
    borderColor: Colors.secondary,
    borderRadius: 8,
    backgroundColor: Colors.white,
    maxHeight: 200,
    elevation: 4,
    shadowColor: '#000',
    shadowOffset: {width: 0, height: 2},
    shadowOpacity: 0.1,
    shadowRadius: 4,
  },
  positionList: {
    maxHeight: 200,
  },
  positionOption: {
    paddingHorizontal: 16,
    paddingVertical: 12,
    borderBottomWidth: 1,
    borderBottomColor: Colors.lightGray,
  },
  positionOptionText: {
    fontSize: 16,
    fontWeight: 'bold',
    color: Colors.primary,
  },
  saveButton: {
    marginTop: 8,
    backgroundColor: Colors.white,
    borderWidth: 1,
    borderColor: Colors.secondary,
    borderRadius: 8,
    padding: 12,
    alignItems: 'center',
    height: 48,
    justifyContent: 'center',
  },
  saveButtonDisabled: {
    opacity: 0.5,
  },
  saveButtonText: {
    fontSize: 16,
    fontWeight: 'bold',
    color: Colors.primary,
  },
});

