import React from 'react';
import {
  View,
  Text,
  StyleSheet,
  TouchableOpacity,
  Image,
} from 'react-native';
import {Player} from '../types/Player';
import {PositionDisplayNames, PositionAbbreviations} from '../types/Player';
import {getRatingColor} from '../theme/colors';
import {Colors} from '../theme/colors';

interface PlayerCardProps {
  player: Player;
  onPress?: (player: Player) => void;
  onDeleteClick?: (player: Player) => void;
}

export const PlayerCard: React.FC<PlayerCardProps> = React.memo(
  ({player, onPress, onDeleteClick}) => {
    const ratingColor = getRatingColor(player.rating);

    return (
      <View style={styles.cardWrapper}>
        <TouchableOpacity
          style={styles.card}
          onPress={() => onPress?.(player)}
          activeOpacity={0.7}>
          <View style={styles.cardContent}>
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

            <View style={styles.playerInfo}>
              <Text style={styles.playerName}>{player.name}</Text>
              <Text style={styles.playerPosition}>
                {PositionDisplayNames[player.position]}
              </Text>
            </View>

            <View style={[styles.ratingBadge, {backgroundColor: ratingColor}]}>
              <Text style={styles.ratingText}>
                {player.rating.toFixed(1)}
              </Text>
            </View>
          </View>
        </TouchableOpacity>

        <TouchableOpacity
          style={styles.deleteButton}
          onPress={() => onDeleteClick?.(player)}
          activeOpacity={0.7}>
          <Text style={styles.deleteButtonText}>−</Text>
        </TouchableOpacity>
      </View>
    );
  },
);

PlayerCard.displayName = 'PlayerCard';

const styles = StyleSheet.create({
  cardWrapper: {
    position: 'relative',
    marginHorizontal: 16,
    marginVertical: 4,
  },
  card: {
    backgroundColor: Colors.lightGray,
    borderRadius: 12,
    elevation: 4,
    shadowColor: '#000',
    shadowOffset: {width: 0, height: 2},
    shadowOpacity: 0.1,
    shadowRadius: 4,
  },
  cardContent: {
    flexDirection: 'row',
    alignItems: 'center',
    padding: 16,
  },
  avatarContainer: {
    marginRight: 16,
  },
  avatar: {
    width: 60,
    height: 60,
    borderRadius: 30,
    backgroundColor: Colors.primary,
    justifyContent: 'center',
    alignItems: 'center',
  },
  avatarText: {
    color: Colors.white,
    fontSize: 18,
    fontWeight: 'bold',
  },
  playerInfo: {
    flex: 1,
  },
  playerName: {
    fontSize: 16,
    fontWeight: 'bold',
    color: Colors.primary,
    marginBottom: 4,
  },
  playerPosition: {
    fontSize: 14,
    color: Colors.gray,
  },
  ratingBadge: {
    width: 50,
    height: 50,
    borderRadius: 25,
    justifyContent: 'center',
    alignItems: 'center',
  },
  ratingText: {
    color: Colors.white,
    fontSize: 14,
    fontWeight: 'bold',
  },
  deleteButton: {
    position: 'absolute',
    top: 10,
    left: 64, // 16 (padding) + 60 (avatar) - 12 (half button size)
    width: 24,
    height: 24,
    borderRadius: 12,
    backgroundColor: Colors.ratingRed,
    justifyContent: 'center',
    alignItems: 'center',
    elevation: 4,
    shadowColor: '#000',
    shadowOffset: {width: 0, height: 2},
    shadowOpacity: 0.2,
    shadowRadius: 4,
  },
  deleteButtonText: {
    color: Colors.white,
    fontSize: 16,
    fontWeight: 'bold',
    lineHeight: 16,
  },
});

