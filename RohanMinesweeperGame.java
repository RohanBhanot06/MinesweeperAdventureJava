package unit1Game;

/* Name: Rohan Bhanot 
 * Starting Date: Thursday, February 15, 2024
 * Ending Date: Tuesday, February 20, 2024
 * Teacher: Elena Kapustina
 * Course Code: ICS4U1-02
 * Program Name: RohanMinesweeperGame
 * Description: Creating the 2D array "field" that is 6 rows and 6 columns to play the game of Minesweeper. Also implementing the methods "countNumberOfMines", "printField", "printFieldWithCoordinates", and "generalRules" to simulate a game where the user enters coordinates, finding mines and hearts, and takes part in a trip trying to reach the last row to win the game.
 */

import java.util.Scanner;

public class RohanMinesweeperGame {
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		int[][] field = new int[6][6]; // Variable to store the field/map the game is played on. (6x6 filled with zeros)
		int lives = 2; // Variable to store the lives available.
		int positionX = 0; // Variable to store x coordinate position.
		int positionY = 0; // Variable to store y coordinate position.
		int tries = 10; // Variable to store the amount of tries available during checking of the field.
		// Calls the general rules method to display the game rules and displays a welcome message within the method.
		generalRules();
		// Initializing the field.
		// Putting 8 randomly generated mines. (represented by the number '6')
		for (int i = 0; i < 8; i++) {
			int x, y;
			do {
				x = (int) (Math.random() * 6);
				y = (int) (Math.random() * 6);
			} while (field[x][y] != 0);
			field[x][y] = 6; // Indicates that a mine has been inputed on to the field.
		}
		// Putting 3 randomly generated hearts. (represented by the number '7')
		for (int i = 0; i < 3; i++) {
			int x, y;
			do {
				x = (int) (Math.random() * 6);
				y = (int) (Math.random() * 6);
			} while (field[x][y] != 0);
			field[x][y] = 7; // Indicates that a heart has been inputed on to the field.
		}
		// Place the player at position (0,0). (represented by the number '5')
		field[positionX][positionY] = 5;
		// Print the initial field with hidden mines and hearts for the player.
		printFieldWithCoordinates(field);
		System.out.println(" ");
		// Print the field with mines and hearts for teacher validation.
		printField(field);
		// Checking the field.
		while (tries > 0) {
			int x, y; // Variables to store the x and y coordinates of the user.
			System.out.println(" ");
			do {
				// Prompts the user to enter the x coordinate for their checking of a square on the field.
				System.out.print("Enter x coordinate to check (1-6): ");
				x = input.nextInt() - 1; // Adjusting input to 0-based indexing.
				// Prompts the user to enter the y coordinate for their checking of a square on the field.
				System.out.print("Enter y coordinate to check (1-6): ");
				y = input.nextInt() - 1; // Adjusting input to 0-based indexing.
				// Checks if the coordinate inputed is valid.
				if (x < 0 || x > 5 || y < 0 || y > 5) {
					System.out.println("Invalid coordinates. Please enter values between 1 and 6.");
				}
				// Checks for how many mines are around the user inputed coordinates, if the user has found a heart, or if it is not occupied by anything.
			} while (x < 0 || x > 5 || y < 0 || y > 5);
			if (field[x][y] == 6) {
				System.out.println("There are " + countNumberOfMines(field, x, y) + " mines around (" + (x + 1) + "," + (y + 1) + ").");
				field[x][y] = -1; // Mark as checked.
			} else if (field[x][y] == 7) {
				System.out.println("You found a heart!");
				field[x][y] = -2; // Mark as checked.
			} else {
				System.out.println("Nice! You found a safe place.");
				field[x][y] = 0; // Mark as an empty space.
			}
			System.out.println("-------------------------------------------------");
			// Displays the player's map.
			System.out.println(" ");
			printFieldWithCoordinates(field);
			/// Displays the teacher's validating map.
			System.out.println(" ");
			printField(field);
			/// Reduces the tries count by one and repeats the whole process.
			tries--;
		}
		// Displays a message that the trip is about to begin.
		System.out.println("-------------------------------------------------");
		System.out.println(" ");
		System.out.println("----------------------------");
		System.out.println("NOW YOU WILL BEGIN THE TRIP!");
		System.out.println("----------------------------");
		// Start the trip process.
		while (positionY < 5 && lives > 0) {
			String direction;
			do {
				// Displays the players life count before beginning each part of the trip.
				System.out.println(" ");
				System.out.println("---------------------");
				System.out.println("CURRENT LIFE COUNT: " + lives);
				System.out.println("---------------------");
				// Prompts the user to enter 'w', 'a', 's', or 'd' for the player's movement.
				System.out.println(" ");
				System.out.print("Enter a Direction (w/a/s/d): ");
				direction = input.next();
				// Checks if the direction inputed is valid, and requests for a new input if not.
				if (!direction.matches("[wasd]")) {
					System.out.println("Invalid Direction. Please Enter w, a, s, or d.");
				}
				// Repeat until a valid direction is entered.
			} while (!direction.matches("[wasd]"));
			int newPositionX = positionX; // Variable to store the new x coordinate position.
			int newPositionY = positionY; // Variable to store the new y coordinate position.
			switch (direction) {
			case "w":
				newPositionY--; // Move up.
				break;
			case "a":
				newPositionX--; // Move left.
				break;
			case "s":
				newPositionY++; // Move down.
				break;
			case "d":
				newPositionX++; // Move right.
				break;
			}
			// Checks if the new positions are valid, and requests for a new input if not.
			if (newPositionX < 0 || newPositionX > 5 || newPositionY < 0 || newPositionY > 5) {
				System.out.println("Error: Going out of bounds. Please try again.");
				continue;
			}
			// Checks if the new position contains a mine and reduces the live count if it is hit.
			if (field[newPositionY][newPositionX] == -1) {
				lives--;
				System.out.println("You hit a mine! Lives left: " + lives);
				// Checks if the live count is zero and displays the game over message if valid.
				if (lives == 0) {
					// Displays the player's map.
					System.out.println("-------------------------------------------------");
					printFieldWithCoordinates(field);
					System.out.println(" ");
					System.out.println("----------");
					System.out.println("GAME OVER!");
					System.out.println("----------");
					return;
				}
				// Checks if there's only one life left.
				if (lives == 1) {
					double price = 5.00; // Variable to store the price for an extra life.
					// Randomly generating a balance between the numbers 3 and 10.
					double balance = Math.random() * 8 + 3; // Variable to store the balance available to the user.
					// Rounding balance to 2 decimal places.
					balance = Math.round(balance * 100.0) / 100.0;
					// Prints the user's current balance after randomly generating it.
					System.out.println("Current Balance: $" + balance);
					// Checks if the users randomly generated balance is greater or equal to the price of one heart.
					if (balance >= price) {
						// Prompts the user to enter Y ('Yes') or N ('No') in purchasing an extra life.
						System.out.println("Would you like to purchase an extra life for $" + price + "? (Y/N)");
						String response = input.next();
						// Checks if the users input is yes or no and if they don't have the available funds to purchase an extra life.
						if (response.equalsIgnoreCase("Y")) {
							balance -= price; // Deducting the price from the balance.
							lives++; // Adding lives to the user's life count.
							System.out.println("You purchased an extra life for $" + price + ". Lives: " + lives);
						} else {
							System.out.println("You chose not to purchase an extra life.");
						}
					} else {
						System.out.println("Insufficient funds to purchase an extra life.");
					}
				}
				// Check if the new position contains a heart and increases the live count if it is hit.
			} else if (field[newPositionY][newPositionX] == -2) {
				lives++;
				System.out.println("You found a heart! Lives: " + lives);
			}
			System.out.println("-------------------------------------------------");
			field[positionY][positionX] = field[positionY][positionX] > 0 ? field[positionY][positionX] : 0; // Reset player's position.
			positionX = newPositionX; // Updates the X coordinate with new position.
			positionY = newPositionY; // Updates the Y coordinate with new position.
			field[positionY][positionX] = 5; // Set player's new position on the field.
			// Displays the player's map.
			printFieldWithCoordinates(field);
		}
		// Checks if the live count is greater than zero and displays the winning message if the player reaches the last row.
		if (positionY == 5 && lives > 0) {
			System.out.println(" ");
			System.out.println("--------");
			System.out.println("YOU WIN!");
			System.out.println("--------");
		}
	}

	/*
	 * Purpose of Method: Calculates how many mines are surrounding a square. 
	 * PRE: int[][] field, int x, int y 
	 * POST: int count
	 */
	public static int countNumberOfMines(int[][] field, int x, int y) {
		int count = 0; // Variable to store the mine count.
		for (int i = Math.max(0, x - 1); i <= Math.min(5, x + 1); i++) {
			for (int j = Math.max(0, y - 1); j <= Math.min(5, y + 1); j++) {
				// Checks if it is a mine on the field.
				if (field[i][j] == 6) {
					count++; // Adding increments of count if mine is found.
				}
			}
		}
		return count; // Return the total count of surrounding mines.
	}

	/*
	 * Purpose: Prints the field for the teachers validation (with shown mines and hearts throughout). 
	 * PRE: int[][] field 
	 * POST: None
	 */
	public static void printField(int[][] field) {
		System.out.println("MAP WITH MINES AND HEARTS (FOR TEACHER VALIDATION):");
		System.out.println(" ");
		// Prints the map layout for the y coordinates.
		System.out.println("    1 2 3 4 5 6");
		System.out.println("    ------------");
		for (int i = 0; i < field.length; i++) {
			// Prints the map layout for the x coordinates.
			System.out.print((i + 1) + " | ");
			for (int j = 0; j < field[i].length; j++) {
				// Checks if the maps coordinates inputed result in a mine or heart.
				if (field[i][j] == 6 || field[i][j] == 7) {
					System.out.print(field[i][j] + " ");
					// Checks if the maps coordinates inputed result in an empty space.
				} else {
					System.out.print("0 ");
				}
			}
			// Prints the map layout for the x coordinates.
			System.out.println("| " + (i + 1));
		}
		// Prints the map layout for the y coordinates.
		System.out.println("    ------------");
		System.out.println("    1 2 3 4 5 6");
	}

	/*
	 * Purpose: Prints the field for the player (with hidden mines and hearts at the beginning). 
	 * PRE: int[][] field 
	 * POST: None
	 */
	public static void printFieldWithCoordinates(int[][] field) {
		System.out.println("MAP FOR THE PLAYER:");
		System.out.println(" ");
		// Prints the map layout for the y coordinates.
		System.out.println("    1 2 3 4 5 6");
		System.out.println("    ------------");
		for (int i = 0; i < field.length; i++) {
			// Prints the map layout for the x coordinates.
			System.out.print((i + 1) + " | ");
			for (int j = 0; j < field[i].length; j++) {
				// Initial coordinate for the players position.
				if (field[i][j] == 5) {
					System.out.print(field[i][j] + " ");
					// Checks if the maps coordinates inputed result in a mine or heart.
				} else if (field[i][j] == 6 || field[i][j] == 7) {
					System.out.print("0 ");
					// Checks if the maps coordinates inputed result in a mine.
				} else if (field[i][j] == -1) {
					System.out.print("M "); // Display "M" for mines
					// Checks if the maps coordinates inputed result in a heart.
				} else if (field[i][j] == -2) {
					System.out.print("H "); // Display "H" for hearts
					// Checks if the maps coordinates inputed result in an empty space.
				} else {
					System.out.print(field[i][j] + " ");
				}
			}
			// Prints the map layout for the x coordinates.
			System.out.println("| " + (i + 1));
		}
		// Prints the map layout for the y coordinates.
		System.out.println("    ------------");
		System.out.println("    1 2 3 4 5 6");
	}

	/*
	 * Purpose of Method: Prints the general rules of the Minesweeper game. 
	 * PRE: None 
	 * POST: None
	 */
	public static void generalRules() {
		// Prints all the general rules to the Minesweeper game
		System.out.println("-------------------------------------------------");
		System.out.println("                MINESWEEPER GAME!");
		System.out.println("-------------------------------------------------");
		System.out.println("-------------------------------------------------");
		System.out.println("DISCLAIMER: GAME IS BEST PLAYED USING FULL-SCREEN");
		System.out.println("-------------------------------------------------");
		System.out.println("-------------------------------------------------");
		System.out.println("Welcome to the Minesweeper Game! By: Rohan Bhanot");
		System.out.println("-------------------------------------------------");
		System.out.println(" ");
		System.out.println("--------------");
		System.out.println("GENERAL RULES:");
		System.out.println("--------------");
		System.out.println(" ");
		System.out.println("GAME OBJECTIVE: Reach the last row without stepping on a mine. You have 2 lives.");
		System.out.println(" ");
		System.out.println("GAME FIELD:");
		System.out.println("  - Field Size: 6x6");
		System.out.println("  - Mines: 8 (represented by '6')");
		System.out.println("  - Hearts: 3 (extra life) (represented by '7')");
		System.out.println(" ");
		System.out.println("CHECKING THE FIELD:");
		System.out.println("  - You have 10 tries to check the field.");
		System.out.println("  - Choose a square to check and get a number representing nearby mines.");
		System.out.println("  - Diagonals do not count.");
		System.out.println(" ");
		System.out.println("STARTING THE TRIP:");
		System.out.println("  - After checking the field, start the trip.");
		System.out.println("  - Choose directions (w/a/s/d) to reach the last row.");
		System.out.println("  - Stepping on a mine without extra life ends the game.");
		System.out.println("  - Finding a heart grants an extra life.");
		System.out.println(" ");
		System.out.println("ADDITIONAL FEATURE:");
		System.out.println("  - If you have one life left, you can pay from your generated balance to gain an extra life.");
		System.out.println(" ");
		System.out.println("SYMBOL REPRESENTATION:");
		System.out.println("  - '0' indicates an empty space on the map.");
		System.out.println("  - '6' indicates a mine.");
		System.out.println("  - '7' indicates a heart.");
		System.out.println("  - '5' indicates the player's position.");
		System.out.println("  - 'M' indicates a mine that has been found on the player's map.");
		System.out.println("  - 'H' indicates a heart that has been found on the player's map.");
		System.out.println(" ");
		System.out.println("WINNING THE GAME: Reach the last row without losing all lives.");
		System.out.println(" ");
		System.out.println("-------------------------------------------------------------------------");
		System.out.println(" DISCLAIMER: The mines and hearts cover a total of 11 spaces on the map.");
		System.out.println("Eight (1 Space) Mines + Three (1 Space) Hearts = 11 Total Variable Spaces");
		System.out.println("-------------------------------------------------------------------------");
		System.out.println(" ");

	}

}