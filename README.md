# Tic Tac Toe

<!-- TOC depthFrom:1 depthTo:6 withLinks:1 updateOnSave:1 orderedList:0 -->
### Contents
- [How to run](#how-to-run)
- [Endpoints](#endpoints)
    - [1. /player](#1-player)
      - [1.1. GET /player](#11-get-player)
      - [1.2. POST /player](#12-post-player)
      - [1.3. PUT /player](#13-put-player)
      - [1.4. DELETE /player](#14-delete-player)
    - [2. /game](#2-game)
      - [2.1. GET /game](#21-get-game)
      - [2.2. POST /game](#22-post-game)
      - [2.3. DELETE /game](#23-delete-game)
    - [3. /gameplay](#3-gameplay)
      - [3.1. GET /gameplay](#31-get-gameplay)
      - [3.2. GET /gameplay/{gameCode}](#32-get-gameplaygamecode)
      - [3.3. GET /gameplay/singleplayer](#33-get-gameplaysingleplayer)
- [TODOs](#todos)

<!-- /TOC -->

### About
A Tic Tac Toe game can have a grid of X3, X4 or X5 (more can be added with minimal change).

Players are assigned "X" or "0" for each game and each player plays on his turn.

***Note***: At the moment, the actual gameplay is on terminal. Will be moved to REST APIs soon.

# How to run

***Note***: Server runs on port 8050

```bash
$ ./mvnw spring-boot:run
```

# Endpoints

## 1. /player

### 1.1. GET /player
**Param**: *username* (Username of the player)

Returns the player

####  Sample response

```json
{
  "id": 1,
  "code": "a72660a0-0123-4125-b10f-6bbefc2c4276",
  "version": 0,
  "name": "Steve Mann",
  "description": null,
  "created": "2021-09-05T20:37:27.719+00:00",
  "updated": "2021-09-05T20:37:27.719+00:00",
  "enabled": true,
  "createdBy": null,
  "updatedBy": null,
  "username": "stevemann"
}
```

### 1.2. POST /player

Saves a new player. Returns the saved player. 

Throws 409 (CONFLICT) if player with username already exists

####  Sample request body

Username should be unique.

```json
{
  "name": "Steve Mann",
  "username": "stevemann"
}
```

####  Sample response
```json
{
  "id": 1,
  "code": "a72660a0-0123-4125-b10f-6bbefc2c4276",
  "version": 0,
  "name": "Steve Mann",
  "description": null,
  "created": "2021-09-05T20:37:27.719+00:00",
  "updated": "2021-09-05T20:37:27.719+00:00",
  "enabled": true,
  "createdBy": null,
  "updatedBy": null,
  "username": "stevemann"
}
```

#### Sample error response
```json
{
  "timestamp": "2021-09-05T21:02:04.475+00:00",
  "status": 409,
  "error": "Conflict",
  "message": "Username already exists.",
  "path": "/player"
}
```

### 1.3. PUT /player

Updates a player based on username. Returns the saved player.

Throws 404 (NOT_FOUND) if player with username doesn't exist.

#### Sample request body

```json
{
  "username": "stevemann",
  "name": "Steve Mann"
}
```

#### Sample response
```json
{
  "id": 1,
  "code": "a72660a0-0123-4125-b10f-6bbefc2c4276",
  "version": 1,
  "name": "Steve Mann",
  "description": null,
  "created": "2021-09-05T20:37:27.719+00:00",
  "updated": "2021-09-05T20:37:27.719+00:00",
  "enabled": true,
  "createdBy": null,
  "updatedBy": null,
  "username": "stevemann"
}
```

#### Sample error response
```json
{
  "timestamp": "2021-09-05T21:02:04.475+00:00",
  "status": 404,
  "error": "Not Found",
  "message": "Player not found.",
  "path": "/player"
}
```

### 1.4. DELETE /player
**Param**: *username* (Username of the player)

Deletes the player (disables it in database). Returns the disabled player.

#### Sample response

```json
{
  "id": 1,
  "code": "a72660a0-0123-4125-b10f-6bbefc2c4276",
  "version": 1,
  "name": "Steve Mann",
  "description": null,
  "created": "2021-09-05T20:37:27.719+00:00",
  "updated": "2021-09-05T20:37:27.719+00:00",
  "enabled": false,
  "createdBy": null,
  "updatedBy": null,
  "username": "stevemann"
}
```

## 2. /game

### 2.1. GET /game
**Param**: *gameCode*

Returns the game

#### Sample response

```json
{
  "code": "157b118f-c310-4555-a0f3-1fe2a3ecd7ed",
  "id": 1,
  "version": 0,
  "name": null,
  "description": null,
  "created": "2021-09-05T20:37:41.676+00:00",
  "updated": "2021-09-05T20:37:58.966+00:00",
  "enabled": true,
  "createdBy": null,
  "updatedBy": null,
  "firstPlayer": {
    "id": 1,
    "code": "e7dc0b31-a33e-466b-97a9-b856583177db",
    "version": 0,
    "name": "Steve Mann",
    "description": null,
    "created": "2021-09-05T20:37:24.173+00:00",
    "updated": "2021-09-05T20:37:24.173+00:00",
    "enabled": true,
    "createdBy": null,
    "updatedBy": null,
    "username": "stevemann"
  },
  "secondPlayer": {
    "id": 2,
    "code": "a72660a0-0123-4125-b10f-6bbefc2c4276",
    "version": 0,
    "name": "Test Player",
    "description": null,
    "created": "2021-09-05T20:37:27.719+00:00",
    "updated": "2021-09-05T20:37:27.719+00:00",
    "enabled": true,
    "createdBy": null,
    "updatedBy": null,
    "username": "testplayer"
  },
  "status": "IN_PROGRESS",
  "gridType": "X3",
  "moves": null
}
```

### 2.2. POST /game

Creates a new game. Returns the saved game.

Throws 404 (NOT_FOUND) if any player not found.

#### Sample request body

Username should be unique.

```json
{
  "firstPlayerUsername": "stevemann",
  "secondPlayerUsername": "testplayer",
  "gridType": "X3"
}
```

#### Sample response
```json
{
  "code": "157b118f-c310-4555-a0f3-1fe2a3ecd7ed",
  "id": 1,
  "version": 0,
  "name": null,
  "description": null,
  "created": "2021-09-05T20:37:41.676+00:00",
  "updated": "2021-09-05T20:37:58.966+00:00",
  "enabled": true,
  "createdBy": null,
  "updatedBy": null,
  "firstPlayer": {
    "id": 1,
    "code": "e7dc0b31-a33e-466b-97a9-b856583177db",
    "version": 0,
    "name": "Steve Mann",
    "description": null,
    "created": "2021-09-05T20:37:24.173+00:00",
    "updated": "2021-09-05T20:37:24.173+00:00",
    "enabled": true,
    "createdBy": null,
    "updatedBy": null,
    "username": "stevemann"
  },
  "secondPlayer": {
    "id": 2,
    "code": "a72660a0-0123-4125-b10f-6bbefc2c4276",
    "version": 0,
    "name": "Test Player",
    "description": null,
    "created": "2021-09-05T20:37:27.719+00:00",
    "updated": "2021-09-05T20:37:27.719+00:00",
    "enabled": true,
    "createdBy": null,
    "updatedBy": null,
    "username": "testplayer"
  },
  "status": "IN_PROGRESS",
  "gridType": "X3",
  "moves": null
}
```

#### Sample error response
```json
{
  "timestamp": "2021-09-05T21:22:31.612+00:00",
  "status": 404,
  "error": "Not Found",
  "message": "Player not found.",
  "path": "/game"
}
```

### 2.3. DELETE /game
**Param**: *gameCode*

Deletes the game (disables it in database). Returns the disabled game.

#### Sample response

```json
{
  "code": "157b118f-c310-4555-a0f3-1fe2a3ecd7ed",
  "id": 1,
  "version": 1,
  "name": null,
  "description": null,
  "created": "2021-09-05T20:37:41.676+00:00",
  "updated": "2021-09-05T20:37:58.966+00:00",
  "enabled": false,
  "createdBy": null,
  "updatedBy": null,
  "firstPlayer": {
    "id": 1,
    "code": "e7dc0b31-a33e-466b-97a9-b856583177db",
    "version": 0,
    "name": "Steve Mann",
    "description": null,
    "created": "2021-09-05T20:37:24.173+00:00",
    "updated": "2021-09-05T20:37:24.173+00:00",
    "enabled": true,
    "createdBy": null,
    "updatedBy": null,
    "username": "stevemann"
  },
  "secondPlayer": {
    "id": 2,
    "code": "a72660a0-0123-4125-b10f-6bbefc2c4276",
    "version": 0,
    "name": "Test Player",
    "description": null,
    "created": "2021-09-05T20:37:27.719+00:00",
    "updated": "2021-09-05T20:37:27.719+00:00",
    "enabled": true,
    "createdBy": null,
    "updatedBy": null,
    "username": "testplayer"
  },
  "status": "IN_PROGRESS",
  "gridType": "X3",
  "moves": null
}
```

## 3. /gameplay

### 3.1. GET /gameplay
**Param**: *firstUsername* (optional) <br>
Uses the player as first player. Throws 404-Player not found if player not found. 
Note: Creates a random player if not passed.

**Param**: *secondUsername* (optional) <br>
Uses the player as second player. Throws 404-Player not found if player not found. 
Note: Creates a random player if not passed.

**Param**: *gridType* (optional) <br>
Grid Type for the game. Value can be X3, X4, X5 as of now

The game runs on the console. Moving the game to REST is in TODO. As soon as the API is hit, go to the console to see a positinal grid with possible positional inputs for the game.

#### Sample positional grid for 3X3 grid type
```
POSITIONS TO BE ENTERED ARE: 
 + - + - + - +
 | 1 | 2 | 3 |
 + - + - + - +
 | 4 | 5 | 6 |
 + - + - + - +
 | 7 | 8 | 9 |
 + - + - + - +
```

Positional numbers to be used as inputs for each turn. Prints current board after each turn.

#### Sample current board output
```
CURRENT BOARD:
+ - + - + - +
| X |   |   |
+ - + - + - +
| O | X |   |
+ - + - + - +
|   |   | O |
+ - + - + - +
```

### 3.2. GET /gameplay/{gameCode}
**Path Param**: *gameCode* <br>
The code of the game you want to resume.

Prints the positional grid, current board and continues the game.

### 3.3. GET /gameplay/singleplayer
**Param**: *playerUsername* (optional) <br>
Uses the player as first player. Throws 404-Player not found if player not found.
Note: Creates a random player if not passed.

**Param**: *gridType* (optional) <br>
Grid Type for the game. Value can be X3, X4, X5 as of now

Second Player will be computer. Gameplay similar to [3.1. GET /gameplay](#31-get-gameplay)

# TODOs

- ~~Complete the documentation~~
- ~~Write tests~~
- Better error handling
- ~~Create automated player~~ (Using [Minimax Algorithm](https://en.wikipedia.org/wiki/Minimax) for automated player)
- Move gameplay to REST APIs
- Visualise the saved data as statistics
- Probably create a front end for this

# Notes

1. Test for GameplayService weren't written because currently the game is on console which needs to be moved to REST.
2. We don't check/update game status automatically after each move which should be done and is a task for next version.
