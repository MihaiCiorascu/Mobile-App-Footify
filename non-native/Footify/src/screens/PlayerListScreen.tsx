import React, {useState} from 'react';
import {
  View,
  Text,
  StyleSheet,
  FlatList,
  TouchableOpacity,
} from 'react-native';
import {usePlayerContext} from '../context/PlayerContext';
import {PlayerCard} from '../components/PlayerCard';
import {DeleteConfirmationDialog} from '../components/DeleteConfirmationDialog';
import {AddPlayerDialog} from '../components/AddPlayerDialog';
import {Player} from '../types/Player';
import {Colors} from '../theme/colors';

interface PlayerListScreenProps {
  onPlayerPress?: (player: Player) => void;
}

export const PlayerListScreen: React.FC<PlayerListScreenProps> = ({
  onPlayerPress,
}) => {
  const {players, deletePlayer, addPlayer} = usePlayerContext();
  const [playerToDelete, setPlayerToDelete] = useState<Player | null>(null);
  const [showAddDialog, setShowAddDialog] = useState(false);

  const handleDeleteClick = React.useCallback((player: Player) => {
    setPlayerToDelete(player);
  }, []);

  const renderPlayerCard = React.useCallback(
    ({item}: {item: Player}) => (
      <PlayerCard
        player={item}
        onPress={onPlayerPress}
        onDeleteClick={handleDeleteClick}
      />
    ),
    [onPlayerPress, handleDeleteClick],
  );

  const keyExtractor = React.useCallback((item: Player) => item.id, []);

  return (
    <View style={styles.container}>
      <View style={styles.header}>
        <View>
          <Text style={styles.greeting}>Hello!</Text>
          <Text style={styles.welcome}>Welcome Back!</Text>
        </View>
        <TouchableOpacity
          style={styles.addButton}
          onPress={() => setShowAddDialog(true)}>
          <Text style={styles.addButtonText}>+</Text>
        </TouchableOpacity>
      </View>

      <View style={styles.content}>
        <Text style={styles.sectionTitle}>Recent Player Performances</Text>

        {players.length === 0 ? (
          <View style={styles.emptyContainer}>
            <Text style={styles.emptyText}>No players found</Text>
          </View>
        ) : (
          <FlatList
            data={players}
            renderItem={renderPlayerCard}
            keyExtractor={keyExtractor}
            contentContainerStyle={styles.listContent}
            showsVerticalScrollIndicator={false}
            removeClippedSubviews={true}
            maxToRenderPerBatch={10}
            windowSize={10}
            initialNumToRender={10}
          />
        )}
      </View>

      <DeleteConfirmationDialog
        player={playerToDelete}
        visible={playerToDelete !== null}
        onDismiss={() => setPlayerToDelete(null)}
        onConfirm={() => {
          if (playerToDelete) {
            deletePlayer(playerToDelete.id);
            setPlayerToDelete(null);
          }
        }}
      />

      <AddPlayerDialog
        visible={showAddDialog}
        onDismiss={() => setShowAddDialog(false)}
        onAdd={(name, age, shirtNumber, position) => {
          addPlayer(name, age, shirtNumber, position);
        }}
      />
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: Colors.white,
  },
  header: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
    paddingHorizontal: 16,
    paddingTop: 16,
    paddingBottom: 8,
  },
  greeting: {
    fontSize: 16,
    color: Colors.primary,
  },
  welcome: {
    fontSize: 24,
    fontWeight: 'bold',
    color: Colors.primary,
    marginTop: 4,
  },
  content: {
    flex: 1,
    paddingTop: 16,
  },
  sectionTitle: {
    fontSize: 20,
    fontWeight: 'bold',
    color: Colors.primary,
    paddingHorizontal: 16,
    marginBottom: 16,
  },
  listContent: {
    paddingBottom: 16,
  },
  emptyContainer: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
  },
  emptyText: {
    fontSize: 16,
    color: Colors.gray,
  },
  addButton: {
    width: 40,
    height: 40,
    borderRadius: 8,
    backgroundColor: Colors.primary,
    justifyContent: 'center',
    alignItems: 'center',
    elevation: 2,
    shadowColor: '#000',
    shadowOffset: {width: 0, height: 2},
    shadowOpacity: 0.1,
    shadowRadius: 4,
  },
  addButtonText: {
    fontSize: 24,
    fontWeight: 'bold',
    color: Colors.white,
    lineHeight: 28,
  },
});

