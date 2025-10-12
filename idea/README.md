# ⚽ Footyfy

## 📖 Short Description

Footyfy helps a manager maintain a list of football players. You can add players, view all players, edit their details, and remove players who left the team.
The app also displays simple charts, such as the number of defenders or strikers and the overall season rating for each player.
A rating grading system rewards players with points based on their ratings and minutes played.
It works even without an internet connection and syncs automatically once back online.



## 🧩 Entity: Player

| **Field**                   | **Description**                                                                                                                                                                                                       |
| --------------------------- | --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| **id**                      | Unique identifier                                                                                                                                                                                                     |
| **name**                    | Full name                                                                                                                                                                                                             |
| **age**                     | Age in years                                                                                                                                                                                                          |
| **position**                | Goalkeeper, Right Back, Left Back, Center Back, Central Defensive Midfielder, Right Midfielder, Left Midfielder, Central Midfielder, Central Attacking Midfielder, Right Winger, Left Winger, Center Forward, Striker |
| **nationality**             | Country                                                                                                                                                                                                               |
| **rating**                  | Number from 0.0 to 10.0                                                                                                                                                                                               |
| **shirt number**           | Number from 0 to 99                                                                                                                                                                                                   |
| **goals**                   | Total goals (default 0)                                                                                                                                                                                               |
| **image / image1 / image2** | URLs for profile picture, preview, and player card                                                                                                                                                                    |
| **createdAt / updatedAt**   | Automatic timestamps                                                                                                                                                                                                  |



## ⚙️ CRUD Operations

### **Create**

* Form with: name, age, position, nationality, rating (default 5.0), jersey number, goals (default 0), image (default anonymous).
* Client validates inputs; server re-validates.
* On success: new player card appears and charts update.

### **Read**

* List view with search and filtering by name.
* Stats panel displays player data and rating history.
* Charts show distributions by position, rating, and age.

### **Update**

* Edit screen allows changing name, nationality, position and shirt number.
* Real-time updates are broadcast to all connected clients.

### **Delete**

* Confirmation required.
* Player and related data are removed, charts update automatically.



## 💾 Persistence Details

### **Local Database**

* **Create:** Players added offline are stored in the local database and synced when online.
* **Read:** Player lists and profiles are cached for offline access.
* **Update:** Edits made offline (e.g., name, position, rating) are queued for synchronization.
* **Delete:** Players removed offline are flagged locally and fully deleted from the server when reconnected.

### **Server Database**

* **Create:** Newly added players are uploaded and stored in the central database.
* **Update:** Modifications are synced to the server and overwrite outdated data.
* **Delete:** Deleted players are removed from the server database.



## 📶 Offline Behavior

* **Create (offline):** Player is saved locally and synced via POST when online.
* **Read (offline):** Cached player list and data remain available; a banner shows “Server is unavailable.”
* **Update (offline):** Edits are saved locally and synced in order when online.
* **Delete (offline):** Player is marked for deletion and hidden; deletion request is sent once reconnected.

## 📸 Screenshots

🏠 Main Page
<br>
  <img src="https://github.com/user-attachments/assets/2072c0c7-02ac-4129-9ee1-1595210f2c5c" width="300"/>
<br><br>
📋 List View
<br>
  <img src="https://github.com/user-attachments/assets/3093dccc-6926-4739-83ca-20e508affc91" width="300"/>
  <img src="https://github.com/user-attachments/assets/45fa7510-3383-40b2-9369-3e57fec1a19e" width="300"/>
<br><br>
🗑️ Delete Player
<br>
  <img src="https://github.com/user-attachments/assets/9d1691a0-2969-4818-a10a-3558c974bcaa" width="300"/>
<br><br>
  
✏️ Edit Player
<br>
  <img src="https://github.com/user-attachments/assets/9b6ee07a-9407-4f6e-94bf-ef1433f9303f" width="300"/>
  <img src="https://github.com/user-attachments/assets/a08e3979-fa0c-4144-9c0c-d9d1e50fddaf" width="300"/>

