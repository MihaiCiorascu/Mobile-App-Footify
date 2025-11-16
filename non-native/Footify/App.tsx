/**
 * Footify - Football Player Management App
 * React Native Implementation
 *
 * @format
 */

import React, {useState} from 'react';
import {StatusBar, useColorScheme, SafeAreaView} from 'react-native';
import {PlayerProvider, usePlayerContext} from './src/context/PlayerContext';
import {PlayerListScreen} from './src/screens/PlayerListScreen';
import {PlayerStatsScreen} from './src/screens/PlayerStatsScreen';
import {EditPlayerDialog} from './src/components/EditPlayerDialog';
import {Player} from './src/types/Player';

function AppContent() {
  const [selectedPlayer, setSelectedPlayer] = useState<Player | null>(null);
  const [showEditDialog, setShowEditDialog] = useState(false);
  const {updatePlayer} = usePlayerContext();

  if (selectedPlayer) {
    return (
      <>
        <PlayerStatsScreen
          player={selectedPlayer}
          onBackClick={() => setSelectedPlayer(null)}
          onEditClick={() => setShowEditDialog(true)}
        />
        <EditPlayerDialog
          player={selectedPlayer}
          visible={showEditDialog}
          onDismiss={() => setShowEditDialog(false)}
          onSave={(name, shirtNumber, position) => {
            const updatedPlayer: Player = {
              ...selectedPlayer,
              name,
              shirtNumber,
              position,
              updatedAt: new Date(),
            };
            updatePlayer(updatedPlayer);
            setSelectedPlayer(updatedPlayer);
            setShowEditDialog(false);
          }}
        />
      </>
    );
  }

  return (
    <PlayerListScreen
      onPlayerPress={player => setSelectedPlayer(player)}
    />
  );
}

function App() {
  const isDarkMode = useColorScheme() === 'dark';

  return (
    <SafeAreaView style={{flex: 1}}>
      <StatusBar
        barStyle={isDarkMode ? 'light-content' : 'dark-content'}
        backgroundColor="#6A4C93"
      />
      <PlayerProvider>
        <AppContent />
      </PlayerProvider>
    </SafeAreaView>
  );
}

export default App;
