import React from 'react';
import {
  View,
  Text,
  StyleSheet,
  Modal,
  TouchableOpacity,
  TouchableWithoutFeedback,
} from 'react-native';
import {Player} from '../types/Player';
import {Colors} from '../theme/colors';

interface DeleteConfirmationDialogProps {
  player: Player | null;
  visible: boolean;
  onDismiss: () => void;
  onConfirm: () => void;
}

export const DeleteConfirmationDialog: React.FC<DeleteConfirmationDialogProps> = ({
  player,
  visible,
  onDismiss,
  onConfirm,
}) => {
  if (!player || !visible) {
    return null;
  }

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
              <View style={styles.dialogContent}>
                <Text style={styles.dialogTitle}>
                  ARE YOU SURE YOU WANT TO DELETE?
                </Text>

                <View style={styles.buttonRow}>
                  <TouchableOpacity
                    style={[styles.button, styles.noButton]}
                    onPress={onDismiss}>
                    <Text style={styles.noButtonText}>NO</Text>
                  </TouchableOpacity>

                  <TouchableOpacity
                    style={[styles.button, styles.yesButton]}
                    onPress={onConfirm}>
                    <Text style={styles.yesButtonText}>YES</Text>
                  </TouchableOpacity>
                </View>
              </View>
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
    fontSize: 18,
    fontWeight: 'bold',
    color: Colors.primary,
    textAlign: 'center',
    marginBottom: 24,
  },
  buttonRow: {
    flexDirection: 'row',
    justifyContent: 'space-evenly',
    gap: 8,
  },
  button: {
    flex: 1,
    borderRadius: 8,
    padding: 12,
    alignItems: 'center',
    justifyContent: 'center',
  },
  noButton: {
    backgroundColor: Colors.primary,
    marginRight: 8,
  },
  noButtonText: {
    color: Colors.white,
    fontWeight: 'bold',
    fontSize: 16,
  },
  yesButton: {
    backgroundColor: Colors.white,
    borderWidth: 2,
    borderColor: Colors.primary,
    marginLeft: 8,
  },
  yesButtonText: {
    color: Colors.primary,
    fontWeight: 'bold',
    fontSize: 16,
  },
});

