import React, {useState, useEffect} from 'react';
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
import {Position, PositionAbbreviations} from '../types/Player';
import {Colors} from '../theme/colors';

interface AddPlayerDialogProps {
  visible: boolean;
  onDismiss: () => void;
  onAdd: (name: string, age: number, shirtNumber: number, position: Position) => void;
}

export const AddPlayerDialog: React.FC<AddPlayerDialogProps> = ({
  visible,
  onDismiss,
  onAdd,
}) => {
  const [playerName, setPlayerName] = useState('');
  const [age, setAge] = useState('');
  const [shirtNumber, setShirtNumber] = useState('');
  const [selectedPosition, setSelectedPosition] = useState(Position.CENTER_FORWARD);
  const [ageError, setAgeError] = useState('');
  const [shirtNumberError, setShirtNumberError] = useState('');
  const [positionDropdownOpen, setPositionDropdownOpen] = useState(false);

  // Reset form when dialog is closed
  useEffect(() => {
    if (!visible) {
      setPlayerName('');
      setAge('');
      setShirtNumber('');
      setSelectedPosition(Position.CENTER_FORWARD);
      setAgeError('');
      setShirtNumberError('');
      setPositionDropdownOpen(false);
    }
  }, [visible]);

  const handleAgeChange = (value: string) => {
    setAge(value);
    const number = parseInt(value, 10);
    if (value === '') {
      setAgeError('');
    } else if (isNaN(number)) {
      setAgeError('Please enter a valid number');
    } else if (number < 14) {
      setAgeError('Age must be 14 or greater');
    } else if (number > 50) {
      setAgeError('Age must be 50 or less');
    } else {
      setAgeError('');
    }
  };

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

  const handleAdd = () => {
    if (
      ageError === '' &&
      shirtNumberError === '' &&
      playerName.trim() !== '' &&
      age !== '' &&
      shirtNumber !== ''
    ) {
      const ageNum = parseInt(age, 10) || 0;
      const shirtNum = parseInt(shirtNumber, 10) || 0;
      onAdd(playerName.trim(), ageNum, shirtNum, selectedPosition);
      onDismiss();
    }
  };

  const isFormValid =
    ageError === '' &&
    shirtNumberError === '' &&
    playerName.trim() !== '' &&
    age !== '' &&
    shirtNumber !== '';

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
                <Text style={styles.dialogTitle}>Add Player</Text>

                {/* Player Name Field */}
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

                {/* Age Field */}
                <View style={styles.fieldContainer}>
                  <Text style={styles.fieldLabel}>Age</Text>
                  <TextInput
                    style={[
                      styles.textInput,
                      ageError ? styles.textInputError : null,
                    ]}
                    value={age}
                    onChangeText={handleAgeChange}
                    keyboardType="numeric"
                    placeholder="Enter age"
                    placeholderTextColor={Colors.gray}
                  />
                  {ageError ? (
                    <Text style={styles.errorText}>{ageError}</Text>
                  ) : null}
                </View>

                {/* Position Field */}
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

                {/* Shirt Number Field */}
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

                {/* Buttons */}
                <View style={styles.buttonRow}>
                  <TouchableOpacity
                    style={[styles.button, styles.cancelButton]}
                    onPress={onDismiss}>
                    <Text style={styles.cancelButtonText}>CANCEL</Text>
                  </TouchableOpacity>

                  <TouchableOpacity
                    style={[
                      styles.button,
                      styles.addButton,
                      !isFormValid ? styles.addButtonDisabled : null,
                    ]}
                    onPress={handleAdd}
                    disabled={!isFormValid}>
                    <Text style={styles.addButtonText}>ADD</Text>
                  </TouchableOpacity>
                </View>
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
    textAlign: 'center',
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
  buttonRow: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    marginTop: 8,
    gap: 8,
  },
  button: {
    flex: 1,
    borderRadius: 8,
    padding: 12,
    alignItems: 'center',
    justifyContent: 'center',
    height: 48,
  },
  cancelButton: {
    backgroundColor: Colors.white,
    borderWidth: 1,
    borderColor: Colors.secondary,
  },
  cancelButtonText: {
    fontSize: 14,
    fontWeight: 'bold',
    color: Colors.primary,
  },
  addButton: {
    backgroundColor: Colors.primary,
  },
  addButtonDisabled: {
    opacity: 0.5,
  },
  addButtonText: {
    fontSize: 14,
    fontWeight: 'bold',
    color: Colors.white,
  },
});

