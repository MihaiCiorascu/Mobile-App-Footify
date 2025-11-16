import React, {createContext, useContext, useState, useCallback, ReactNode} from 'react';
import {Player, Position} from '../types/Player';

interface PlayerContextType {
  players: Player[];
  getPlayerById: (id: string) => Player | undefined;
  updatePlayer: (updatedPlayer: Player) => void;
  deletePlayer: (playerId: string) => void;
  addPlayer: (name: string, age: number, shirtNumber: number, position: Position) => void;
}

const PlayerContext = createContext<PlayerContextType | undefined>(undefined);

export const usePlayerContext = () => {
  const context = useContext(PlayerContext);
  if (!context) {
    throw new Error('usePlayerContext must be used within a PlayerProvider');
  }
  return context;
};

interface PlayerProviderProps {
  children: ReactNode;
}

export const PlayerProvider: React.FC<PlayerProviderProps> = ({children}) => {
  const [players, setPlayers] = useState<Player[]>(() => {
    const now = new Date();
    return [
      {
        id: '1',
        name: 'Lionel Messi',
        age: 37,
        position: Position.CENTER_FORWARD,
        rating: 8.7,
        shirtNumber: 10,
        goals: 851,
        image: '',
        image1: '',
        image2: '',
        createdAt: now,
        updatedAt: now,
      },
      {
        id: '2',
        name: 'Paulo Dybala',
        age: 30,
        position: Position.CENTRAL_ATTACKING_MIDFIELDER,
        rating: 8.1,
        shirtNumber: 21,
        goals: 120,
        image: '',
        image1: '',
        image2: '',
        createdAt: now,
        updatedAt: now,
      },
      {
        id: '3',
        name: 'Cristiano Ronaldo',
        age: 39,
        position: Position.STRIKER,
        rating: 7.9,
        shirtNumber: 7,
        goals: 900,
        image: '',
        image1: '',
        image2: '',
        createdAt: now,
        updatedAt: now,
      },
      {
        id: '4',
        name: 'Lamine Yamal',
        age: 17,
        position: Position.RIGHT_WINGER,
        rating: 9.4,
        shirtNumber: 19,
        goals: 5,
        image: '',
        image1: '',
        image2: '',
        createdAt: now,
        updatedAt: now,
      },
      {
        id: '5',
        name: 'Pedri',
        age: 21,
        position: Position.CENTRAL_MIDFIELDER,
        rating: 9.0,
        shirtNumber: 8,
        goals: 15,
        image: '',
        image1: '',
        image2: '',
        createdAt: now,
        updatedAt: now,
      },
      {
        id: '6',
        name: 'Radu Dragusin',
        age: 22,
        position: Position.CENTER_BACK,
        rating: 7.3,
        shirtNumber: 4,
        goals: 2,
        image: '',
        image1: '',
        image2: '',
        createdAt: now,
        updatedAt: now,
      },
      {
        id: '7',
        name: 'Florinel Coman',
        age: 26,
        position: Position.LEFT_MIDFIELDER,
        rating: 8.4,
        shirtNumber: 11,
        goals: 45,
        image: '',
        image1: '',
        image2: '',
        createdAt: now,
        updatedAt: now,
      },
    ];
  });

  const getPlayerById = useCallback((id: string): Player | undefined => {
    return players.find(player => player.id === id);
  }, [players]);

  const updatePlayer = useCallback((updatedPlayer: Player) => {
    setPlayers(prevPlayers => {
      const updatedPlayers = prevPlayers.map(player =>
        player.id === updatedPlayer.id
          ? {...updatedPlayer, updatedAt: new Date()}
          : player
      );
      return updatedPlayers;
    });
  }, []);

  const deletePlayer = useCallback((playerId: string) => {
    setPlayers(prevPlayers => prevPlayers.filter(player => player.id !== playerId));
  }, []);

  const addPlayer = useCallback((name: string, age: number, shirtNumber: number, position: Position) => {
    setPlayers(prevPlayers => {
      const newId = (prevPlayers.length + 1).toString();
      const now = new Date();
      const newPlayer: Player = {
        id: newId,
        name,
        age,
        position,
        rating: 5.0,
        shirtNumber,
        goals: 0,
        image: '',
        image1: '',
        image2: '',
        createdAt: now,
        updatedAt: now,
      };
      return [...prevPlayers, newPlayer];
    });
  }, []);

  const value: PlayerContextType = {
    players,
    getPlayerById,
    updatePlayer,
    deletePlayer,
    addPlayer,
  };

  return (
    <PlayerContext.Provider value={value}>
      {children}
    </PlayerContext.Provider>
  );
};

