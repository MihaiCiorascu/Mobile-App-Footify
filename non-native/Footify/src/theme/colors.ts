export const Colors = {
  primary: '#6A4C93',
  secondary: '#8B7BA6',
  tertiary: '#FFD700',
  lightGray: '#F5F5F5',
  gray: '#808080',
  darkGray: '#333333',
  white: '#FFFFFF',
  black: '#000000',

  ratingBlue: '#2196F3',
  ratingGreen: '#4CAF50',
  ratingYellow: '#FFEB3B',
  ratingRed: '#F44336',
};

export const getRatingColor = (rating: number): string => {
  if (rating >= 9.0) return Colors.ratingBlue;
  if (rating >= 8.0) return Colors.ratingGreen;
  if (rating >= 6.0) return Colors.ratingYellow;
  return Colors.ratingRed;
};

