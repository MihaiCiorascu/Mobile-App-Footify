This is a new [**React Native**](https://reactnative.dev) project, bootstrapped using [`@react-native-community/cli`](https://github.com/react-native-community/cli).

## Project Context

This React Native project is a non-native implementation of the **Footify** mobile application. The native version (located in `../native/Footify/`) is built with Kotlin and Jetpack Compose.

### Footify App Overview

Footify is a football/soccer player management application with full CRUD (Create, Read, Update, Delete) functionality:

**Key Features:**
- **Player List Screen**: Display all players with search functionality
- **Player Details Screen**: View individual player statistics (goals, age, position)
- **Add Player**: Create new players with validation (name, age, shirt number, position)
- **Edit Player**: Update player information (name, shirt number, position)
- **Delete Player**: Remove players with confirmation dialog
- **Search**: Filter players by name (first name, last name, or full name)

**Player Model:**
- ID, Name, Age, Position (enum with 13 positions), Rating, Shirt Number, Goals, Images, Timestamps

**Position Types:**
- Goalkeeper (GK), Right Back (RB), Left Back (LB), Center Back (CB), Central Defensive Midfielder (CDM), Right Midfielder (RM), Left Midfielder (LM), Central Midfielder (CM), Central Attacking Midfielder (CAM), Right Winger (RW), Left Winger (LW), Center Forward (CF), Striker (ST)

**UI Features:**
- Rating color coding (Blue ≥9.0, Green ≥8.0, Yellow ≥6.0, Red <6.0)
- Material Design 3 theme with purple/gold color scheme
- Player cards with avatars
- Form validation for age (14-50) and shirt number (0-99)

This React Native version will replicate the same functionality using React Native components and state management.

# Getting Started

> **Note**: Make sure you have completed the [Set Up Your Environment](https://reactnative.dev/docs/set-up-your-environment) guide before proceeding.

## Step 1: Start Metro

First, you will need to run **Metro**, the JavaScript build tool for React Native.

To start the Metro dev server, run the following command from the root of your React Native project:

```sh
# Using npm
npm start

# OR using Yarn
yarn start
```

## Step 2: Build and run your app

With Metro running, open a new terminal window/pane from the root of your React Native project, and use the following command to build and run your Android app:

### Android

```sh
# Using npm
npm run android

# OR using Yarn
yarn android
```

If everything is set up correctly, you should see your new app running in the Android Emulator or your connected device.

> **Note**: This project is configured for Android only. The iOS directory and related dependencies have been removed.

This is one way to run your app — you can also build it directly from Android Studio.

## Step 3: Modify your app

Now that you have successfully run the app, let's make changes!

Open `App.tsx` in your text editor of choice and make some changes. When you save, your app will automatically update and reflect these changes — this is powered by [Fast Refresh](https://reactnative.dev/docs/fast-refresh).

When you want to forcefully reload, for example to reset the state of your app, you can perform a full reload:

- **Android**: Press the <kbd>R</kbd> key twice or select **"Reload"** from the **Dev Menu**, accessed via <kbd>Ctrl</kbd> + <kbd>M</kbd> (Windows/Linux) or <kbd>Cmd ⌘</kbd> + <kbd>M</kbd> (macOS).

## Congratulations! :tada:

You've successfully run and modified your React Native App. :partying_face:

### Now what?

- If you want to add this new React Native code to an existing application, check out the [Integration guide](https://reactnative.dev/docs/integration-with-existing-apps).
- If you're curious to learn more about React Native, check out the [docs](https://reactnative.dev/docs/getting-started).

# Troubleshooting

If you're having issues getting the above steps to work, see the [Troubleshooting](https://reactnative.dev/docs/troubleshooting) page.

# Learn More

To learn more about React Native, take a look at the following resources:

- [React Native Website](https://reactnative.dev) - learn more about React Native.
- [Getting Started](https://reactnative.dev/docs/environment-setup) - an **overview** of React Native and how setup your environment.
- [Learn the Basics](https://reactnative.dev/docs/getting-started) - a **guided tour** of the React Native **basics**.
- [Blog](https://reactnative.dev/blog) - read the latest official React Native **Blog** posts.
- [`@facebook/react-native`](https://github.com/facebook/react-native) - the Open Source; GitHub **repository** for React Native.
