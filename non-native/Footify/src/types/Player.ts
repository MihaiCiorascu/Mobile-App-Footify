export enum Position {
  GOALKEEPER = 'GOALKEEPER',
  RIGHT_BACK = 'RIGHT_BACK',
  LEFT_BACK = 'LEFT_BACK',
  CENTER_BACK = 'CENTER_BACK',
  CENTRAL_DEFENSIVE_MIDFIELDER = 'CENTRAL_DEFENSIVE_MIDFIELDER',
  RIGHT_MIDFIELDER = 'RIGHT_MIDFIELDER',
  LEFT_MIDFIELDER = 'LEFT_MIDFIELDER',
  CENTRAL_MIDFIELDER = 'CENTRAL_MIDFIELDER',
  CENTRAL_ATTACKING_MIDFIELDER = 'CENTRAL_ATTACKING_MIDFIELDER',
  RIGHT_WINGER = 'RIGHT_WINGER',
  LEFT_WINGER = 'LEFT_WINGER',
  CENTER_FORWARD = 'CENTER_FORWARD',
  STRIKER = 'STRIKER',
}

export interface Player {
  id: string;
  name: string;
  age: number;
  position: Position;
  rating: number;
  shirtNumber: number;
  goals: number;
  image: string;
  image1: string;
  image2: string;
  createdAt: Date;
  updatedAt: Date;
}

export const PositionDisplayNames: Record<Position, string> = {
  [Position.GOALKEEPER]: 'Goalkeeper',
  [Position.RIGHT_BACK]: 'Right Back',
  [Position.LEFT_BACK]: 'Left Back',
  [Position.CENTER_BACK]: 'Center Back',
  [Position.CENTRAL_DEFENSIVE_MIDFIELDER]: 'Central Defensive Midfielder',
  [Position.RIGHT_MIDFIELDER]: 'Right Midfielder',
  [Position.LEFT_MIDFIELDER]: 'Left Midfielder',
  [Position.CENTRAL_MIDFIELDER]: 'Central Midfielder',
  [Position.CENTRAL_ATTACKING_MIDFIELDER]: 'Central Attacking Midfielder',
  [Position.RIGHT_WINGER]: 'Right Winger',
  [Position.LEFT_WINGER]: 'Left Winger',
  [Position.CENTER_FORWARD]: 'Center Forward',
  [Position.STRIKER]: 'Striker',
};

export const PositionAbbreviations: Record<Position, string> = {
  [Position.GOALKEEPER]: 'GK',
  [Position.RIGHT_BACK]: 'RB',
  [Position.LEFT_BACK]: 'LB',
  [Position.CENTER_BACK]: 'CB',
  [Position.CENTRAL_DEFENSIVE_MIDFIELDER]: 'CDM',
  [Position.RIGHT_MIDFIELDER]: 'RM',
  [Position.LEFT_MIDFIELDER]: 'LM',
  [Position.CENTRAL_MIDFIELDER]: 'CM',
  [Position.CENTRAL_ATTACKING_MIDFIELDER]: 'CAM',
  [Position.RIGHT_WINGER]: 'RW',
  [Position.LEFT_WINGER]: 'LW',
  [Position.CENTER_FORWARD]: 'CF',
  [Position.STRIKER]: 'ST',
};

