import React from 'react';
import {
  View,
  Text,
  StyleSheet,
  ScrollView,
  TouchableOpacity,
} from 'react-native';
import {Player} from '../types/Player';
import {PositionAbbreviations} from '../types/Player';
import {Colors} from '../theme/colors';

interface PlayerStatsScreenProps {
  player: Player;
  onBackClick: () => void;
  onEditClick: () => void;
}

export const PlayerStatsScreen: React.FC<PlayerStatsScreenProps> = ({
  player,
  onBackClick,
  onEditClick,
}) => {
  const nameParts = player.name.trim().split(' ', 2);
  const firstName = nameParts[0] || '';
  const lastName = nameParts[1] || '';

  return (
    <View style={styles.container}>
      {/* Top Bar with Back Button */}
      <View style={styles.topBar}>
        <TouchableOpacity onPress={onBackClick} style={styles.backButton}>
          <Text style={styles.backButtonText}>←</Text>
        </TouchableOpacity>
      </View>

      {/* Header Section with Purple Background */}
      <View style={styles.headerSection}>
        <View style={styles.headerContent}>
          <View style={styles.playerInfo}>
            <Text style={styles.firstName}>{firstName}</Text>
            {lastName && <Text style={styles.lastName}>{lastName}</Text>}
            <Text style={styles.shirtNumber}>#{player.shirtNumber}</Text>
          </View>

          <View style={styles.avatarContainer}>
            <View style={styles.avatar}>
              <Text style={styles.avatarText}>
                {player.name
                  .split(' ')
                  .map(n => n[0])
                  .join('')
                  .toUpperCase()}
              </Text>
            </View>
          </View>
        </View>

        <TouchableOpacity onPress={onEditClick} style={styles.editButton}>
          <Text style={styles.editButtonText}>✎</Text>
        </TouchableOpacity>
      </View>

      {/* Stats Section */}
      <ScrollView style={styles.statsSection} contentContainerStyle={styles.statsContent}>
        <View style={styles.statsRow}>
          <StatCard value={player.goals.toString()} label="Goals" />
          <StatCard value={player.age.toString()} label="Age" />
          <StatCard
            value={PositionAbbreviations[player.position]}
            label="Position"
          />
        </View>
      </ScrollView>
    </View>
  );
};

interface StatCardProps {
  value: string;
  label: string;
}

const StatCard: React.FC<StatCardProps> = ({value, label}) => {
  return (
    <View style={styles.statCard}>
      <Text style={styles.statValue}>{value}</Text>
      <Text style={styles.statLabel}>{label}</Text>
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: Colors.white,
  },
  topBar: {
    backgroundColor: Colors.primary,
    paddingTop: 16,
    paddingBottom: 8,
    paddingHorizontal: 16,
  },
  backButton: {
    width: 40,
    height: 40,
    justifyContent: 'center',
    alignItems: 'center',
  },
  backButtonText: {
    color: Colors.white,
    fontSize: 24,
    fontWeight: 'bold',
  },
  headerSection: {
    backgroundColor: Colors.primary,
    borderBottomLeftRadius: 24,
    borderBottomRightRadius: 24,
    padding: 24,
    position: 'relative',
  },
  headerContent: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
  },
  playerInfo: {
    flex: 1,
  },
  firstName: {
    fontSize: 28,
    fontWeight: 'bold',
    color: Colors.white,
  },
  lastName: {
    fontSize: 28,
    fontWeight: 'bold',
    color: Colors.white,
  },
  shirtNumber: {
    fontSize: 24,
    fontWeight: 'bold',
    color: Colors.tertiary,
    marginTop: 4,
  },
  avatarContainer: {
    marginLeft: 16,
  },
  avatar: {
    width: 120,
    height: 120,
    borderRadius: 16,
    backgroundColor: Colors.white,
    justifyContent: 'center',
    alignItems: 'center',
  },
  avatarText: {
    fontSize: 36,
    fontWeight: 'bold',
    color: Colors.primary,
  },
  editButton: {
    position: 'absolute',
    top: 24,
    right: 24,
    width: 40,
    height: 40,
    borderRadius: 8,
    backgroundColor: Colors.secondary,
    justifyContent: 'center',
    alignItems: 'center',
  },
  editButtonText: {
    fontSize: 20,
    color: Colors.tertiary,
    fontWeight: 'bold',
  },
  statsSection: {
    flex: 1,
  },
  statsContent: {
    padding: 16,
  },
  statsRow: {
    flexDirection: 'row',
    justifyContent: 'space-evenly',
    gap: 8,
  },
  statCard: {
    flex: 1,
    backgroundColor: Colors.lightGray,
    borderRadius: 12,
    padding: 16,
    alignItems: 'center',
    elevation: 4,
    shadowColor: '#000',
    shadowOffset: {width: 0, height: 2},
    shadowOpacity: 0.1,
    shadowRadius: 4,
  },
  statValue: {
    fontSize: 24,
    fontWeight: 'bold',
    color: Colors.darkGray,
    marginBottom: 4,
  },
  statLabel: {
    fontSize: 14,
    color: Colors.gray,
  },
});

