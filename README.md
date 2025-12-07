# Minesweeper Adventure (Java Console Game)

A 6×6 Minesweeper-style console game written in Java.  
First, you scan the field to find mines and hearts; then you go on a “trip” using w/a/s/d movement, trying to reach the last row without losing all your lives.

---

## Overview

This project simulates a simplified Minesweeper-inspired adventure game:

- The game plays on a **6×6 grid**.
- The field contains:
  - **8 mines** (danger)
  - **3 hearts** (extra lives)
- You start with **2 lives** at position **(1, 1)** (top-left corner).
- The game has **two phases**:
  1. **Checking phase** – you probe coordinates to learn where mines and hearts are.
  2. **Trip phase** – you move across the grid (w/a/s/d) trying to reach the last row.

Along the way, you can gain or lose lives and even be offered a chance to **buy an extra life** with a randomly generated balance.

---

## Features

- **Randomized board**
  - Generates a 6×6 `int[][] field`.
  - Places **8 mines** (value `6`) and **3 hearts** (value `7`) at random, non-overlapping positions.
  - Marks the starting player position at `(0, 0)` with value `5`.

- **Checking phase (10 tries)**
  - You get **10 attempts** to inspect cells before the trip begins.
  - For each try, you input **x and y coordinates (1–6)**.
  - Depending on what’s at that location:
    - **Mine (`6`)** → prints how many mines surround that square, marks it as discovered (`-1`).
    - **Heart (`7`)** → prints that you found a heart, marks it as discovered (`-2`).
    - **Empty** → prints a safe message.
  - Two maps are printed each time:
    - **Player map** (with mines/hearts hidden at the start, using symbols).
    - **Teacher validation map** (shows where mines and hearts really are).

- **Trip phase (movement with lives)**
  - After checking, the “trip” begins.
  - You move using:
    - `w` = up
    - `a` = left
    - `s` = down
    - `d` = right
  - You **cannot move out of bounds** (error message if you try).
  - Stepping on:
    - Discovered mine (`-1`) → you **lose a life**.
    - Discovered heart (`-2`) → you **gain a life**.
  - Your current position is marked with value `5` and shown on the player map.

- **Lives and extra life shop**
  - You start with **2 lives**.
  - If you hit a mine and drop to **1 life**, you may:
    - Get a random **balance between $3 and $10**.
    - If balance ≥ $5, you’re offered to **buy an extra life for $5**.
    - If you accept, lives increase and the price is deducted.
  - If lives reach **0**, the game prints the final map and **“GAME OVER”**.

- **Win condition**
  - The trip continues while:
    - You still have **at least 1 life**, and
    - Your row index (`y`) is less than 5.
  - If you reach the **last row** (`y == 5`) with **lives > 0**, you **win**.

---

## Symbols on the Maps

The game uses an `int[][] field` and prints it in two ways.

### Teacher validation map (`printField`)

- `6` – Mine
- `7` – Heart
- `0` – all other (non-mine/heart) cells

This map is meant to show the true locations for validation/debugging.

### Player map (`printFieldWithCoordinates`)

- `5` – Player’s current position
- `0` – Empty / unknown safe space
- `6` / `7` initially hidden as `0`
- `M` – Discovered mine during checking phase (`-1`)
- `H` – Discovered heart during checking phase (`-2`)

Coordinates are printed with a labeled border (1–6 on both axes) to make it easier to choose positions.

---

## Project Structure

All functionality is contained in a single class:

### `RohanMinesweeperGame.java`

Key methods:

- `public static void main(String[] args)`
  - Sets up the grid, lives, player position, and number of tries.
  - Places mines and hearts at random.
  - Runs:
    1. `generalRules()` – prints a detailed rules explanation.
    2. Checking phase (10 coordinate checks).
    3. Trip phase (movement loop using w/a/s/d).
  - Handles win/lose and prints final messages.

- `generalRules()`
  - Prints the full rules of the game:
    - Objective, field size, number of mines and hearts.
    - How checking works.
    - How movement works.
    - Life system and extra-life purchase feature.
    - Explanation of all symbols (`0`, `5`, `6`, `7`, `M`, `H`).

- `countNumberOfMines(int[][] field, int x, int y)`
  - Counts how many mines are in the neighboring squares around a given coordinate.
  - Used during the checking phase to give feedback like:
    - `"There are X mines around (x,y)."`

- `printField(int[][] field)`
  - Prints the **teacher validation map**:
    - Shows actual mines (`6`) and hearts (`7`) on the 6×6 grid with coordinates.

- `printFieldWithCoordinates(int[][] field)`
  - Prints the **player-visible map**:
    - Shows player position, discovered mines as `M`, hearts as `H`, and other spaces.

---

## How to Run

1. **Install Java**

   Make sure you have the Java JDK (e.g., Java 8 or later) installed.

2. **Compile the program**

   ```bash
   javac RohanMinesweeperGame.java
