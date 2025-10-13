# ⚽ Footyfy

## Short Description

Footyfy helps a manager maintain a list of football players. You can add players, view all players, edit their details, and remove players who left the team.
The app also displays simple charts, such as the number of defenders or strikers and the overall season rating for each player.
A rating grading system rewards players with points based on their ratings and minutes played.
It works even without an internet connection and syncs automatically once back online.



## Entity: Player

| **Field**                   | **Description**                                                                                                                                                                                                       |
| --------------------------- | --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| **id**                      | Unique identifier                                                                                                                                                                                                     |
| **name**                    | Full name                                                                                                                                                                                                             |
| **age**                     | Age in years                                                                                                                                                                                                          |
| **position**                | Goalkeeper, Right Back, Left Back, Center Back, Central Defensive Midfielder, Right Midfielder, Left Midfielder, Central Midfielder, Central Attacking Midfielder, Right Winger, Left Winger, Center Forward, Striker |
| **rating**                  | Number from 0.0 to 10.0                                                                                                                                                                                               |
| **shirt number**           | Number from 0 to 99                                                                                                                                                                                                   |
| **goals**                   | Total goals (default 0)                                                                                                                                                                                               |
| **image / image1 / image2** | URLs for profile picture, preview, and player card                                                                                                                                                                    |
| **createdAt / updatedAt**   | Automatic timestamps                                                                                                                                                                                                  |



## CRUD Operations

### **Create**

* Form with: name, age, position, shirt number, rating (default 5.0), goals (default 0), image (default anonymous).
* Client validates inputs; server re-validates.
* On success: new player card appears and charts update.

### **Read**

* List view with search and filtering by name.
* Stats panel displays player data and rating history.
* Charts show distributions by position, rating, and age.

### **Update**

* Edit screen allows changing name, position and shirt number.
* Real-time updates are broadcast to all connected clients.

### **Delete**

* Confirmation required.
* Player and related data are removed, charts update automatically.



## Persistence Details

### **Local Database**

* **Create:** Players added offline are stored in the local database and synced when online.
* **Read:** Player lists and profiles are cached for offline access.
* **Update:** Edits made offline (e.g., name, position, rating) are queued for synchronization.
* **Delete:** Players removed offline are flagged locally and fully deleted from the server when reconnected.

### **Server Database**

* **Create:** Newly added players are uploaded and stored in the central database.
* **Update:** Modifications are synced to the server and overwrite outdated data.
* **Delete:** Deleted players are removed from the server database.



## Offline Behavior

* **Create (offline):** Player is saved locally and synced via POST when online.
* **Read (offline):** Cached player list and data remain available; a banner shows “Server is unavailable.”
* **Update (offline):** Edits are saved locally and synced in order when online.
* **Delete (offline):** Player is marked for deletion and hidden; deletion request is sent once reconnected.

## Screenshots

### Main Page
<br>
  <img src="https://github.com/user-attachments/assets/2072c0c7-02ac-4129-9ee1-1595210f2c5c" width="300"/>
<br><br>

### List View
<br>
  <img src="https://github.com/user-attachments/assets/da3e617c-975a-4148-bd77-42e4f6bdf65d" width="300"/>
  <img src="https://github.com/user-attachments/assets/3b7c9467-f945-4f3b-96fc-f29a9b961fe1" width="300"/>



### Delete Player
<br>
  <img src="https://github.com/user-attachments/assets/9d1691a0-2969-4818-a10a-3558c974bcaa" width="300"/>
<br><br>
  
### Edit Player
<br>
  <img src="https://github.com/user-attachments/assets/a3e48624-eb9e-4279-a5b9-fbf273593975" width="300"/>
  <img src="https://github.com/user-attachments/assets/08bae1c7-3855-4c3c-83d0-8222b9daa030" width="300"/>

