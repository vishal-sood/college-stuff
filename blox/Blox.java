import java.util.*;
import java.util.Scanner;

public class Blox {
	/* the next several lines create constants local to THIS java file
	 *		with the same names/values as those present in the BloxLibrary
	 *		file, to make them easier to refer to (so you can do map[Y][X]
	 *		instead of map[BloxLibrary.Y][BloxLibrary.X], for example).
	 */
	public static final int X = BloxLibrary.X, Y = BloxLibrary.Y;
	public static final char BLOCK = BloxLibrary.BLOCK, HOLE = BloxLibrary.HOLE,
			EMPTY = BloxLibrary.EMPTY;
	
	// constants for user input and map output. Make more as needed!
	public static final char PLAYER = 'P', BORDER = 'X', EASY = 'E',
			MEDIUM = 'M', HARD = 'H', QUIT = 'Q';
	
	// scanner for taking inputs
	public static Scanner input = new Scanner(System.in);
	
	/* Controls the main logic of this program, calling most of the other
	 *		methods directly to accomplish subtasks of the overall program.
	 *
	 * @param  args  Command-line arguments. Unused by this program.
	 */
	public static void main(String[] args) {
		// Just an example of how to call the provided library methods
		displayInstructions();
		char start = getDifficulty();
		int x = 0;
		while(start == 'E' || start == 'M' || start == 'H')
		{
			switch(start){
				case 'E': x = 1;
					  break;
				case 'M': x = 2;
					  break;
				case 'H': x = 3;
					  break;
			}
			char[][] example = BloxLibrary.randomizeMap(x, 10, 10);
			int[] player = BloxLibrary.findAvailablePosition(example);
			playGame(example, player, x);//printMap(example,player);
			start = getDifficulty();
		}
	}
	
	/* Displays instructions on how to play this game.
	 */
	public static void displayInstructions() {
		System.out.println("The player is represented by a 'P'."+
				"\n1.The player can interact with the game by moving in one of four directions: up, down, left, and right." +
				"\n2.When player moves all the blox to the hole, the player win the game, if the blox is not over the hole " +
				"\n  and player walks over the hole, then the player lose the game." +
				"\nHave Fun ^-^!");
		
	}
	
	/* Prints the 2D character map passed in as a parameter, including the player
	 *		whose position is specified by the int array parameter. The player
	 *		position array should be in the same format as that returned by the
	 *		findAvailablePosition method provided in the BloxLibrary file.
	 *
	 * @param  map  The 2D character array map of the current game.
	 * @param  player  An array containing the x and y coordinates of the player.
	 */
	public static void printMap(char[][] map, int[] player) {
		map[player[X]][player[Y]] = 'P';
		for(int i = 0; i < map.length; i++)
		{
			for(int j = 0; j < map[i].length; j++)
			{
				char display = map[i][j];
				System.out.print(display);
			}
			System.out.println();
		}
	}
	
	/* Prompts the user to select a difficulty from the available options.
	 *		Continues to prompt while the selection made by the user does
	 *		not correspond to any of the available options, ensuring that
	 *		a valid difficulty (or quit) is returned.
	 *
	 * @return  The validated difficulty selected by the user.
	 */
	public static char getDifficulty() {
		char enter;
		do{
			System.out.println("Press 'E' for the EASY VERSION, 'M' for the MIDIUM VERSION, and 'H' for the HARD VERSION."+
				"\nIf you want to quit,Press 'Q'." +
				"\nTips: press ENTER after you press the letter");
			enter = input.nextLine().charAt(0);
		}while(enter != 'E' && enter != 'M' && enter != 'H' && enter != 'Q');
		return enter;
	}
	
	/* Prompts the user to select a move from the available options.
	 *		Continues to prompt while the selection made by the user does
	 *		not correspond to any of the available options, ensuring that
	 *		a valid move (or quit) is returned.
	 *
	 * @return  The validated move selected by the user.
	 */
	public static char getMove() {
		char move;
		do{
			System.out.println("Please enter a move - UP (W), DOWN (S), LEFT (A), RIGHT (D), or QUIT (Q): " +
				"\nTips: press ENTER after you press the letter");
			move = input.nextLine().charAt(0);
		}while(move != 'W' && move != 'S' && move != 'A' && move != 'D' && move != 'Q');
		return move;
	}
	
	/* This method indicates if the coordinate specified by the integer parameters
	 *		is a valid location within the 2D character array map specified by the
	 *		remaining parameter.
	 *
	 * @param  map  The 2D character array map of the current game.
	 * @param  x  The horizontal coordinate for the location being tested.
	 * @param  y  The vertical coordinate for the location being tested.
	 * @return  True if the coordinate is within the range of the map,
	 *		false otherwise.
	 */
	public static boolean isOnMap(char[][] map, int x, int y) {
		if(x < map.length && y < map[x].length)
		{
			return true;
		}
		else
		return false;
	}
	
	/* This method moves the player according to the move parameter after
	 * 		determining if the move is valid/possible or not.
	 * 
	 * @param  map  The 2D character array map of the current game.
	 * @param  player  An array containing the x and y coordinates of the player.
	 * @param  move  This parameter indicates the direction in which the player is
	 * 		supposed to move. It can be one of the following: 'W', 'S', 'A' or 'D'.
	 * @return  '0' if the move is blocked, '1' if the move is not blocked and '2'
	 * 		if the move pushes a block into a hole. It returns '-1', if some error
	 * 		occurs.
	 */
	public static int movePlayer(char[][] map, int[] player, char move){
		int[] newPosition = new int[player.length];
		
		switch(move){
		case 'W':
			newPosition[X] = player[X];
			newPosition[Y] = player[Y] - 1;
			break;
		case 'S':
			newPosition[X] = player[X];
			newPosition[Y] = player[Y] + 1;
			break;
		case 'A':
			newPosition[X] = player[X] - 1;
			newPosition[Y] = player[Y];
			break;
		case 'D':
			newPosition[X] = player[X] + 1;
			newPosition[Y] = player[Y];
			break;
		}
		
		if(!isOnMap(map, newPosition[X], newPosition[Y])) // move not possible
			return 0;
		
		if(map[newPosition[X]][newPosition[Y]] == EMPTY || map[newPosition[X]][newPosition[Y]] == HOLE) // move is not blocked
			return 1;
		
		if(map[newPosition[X]][newPosition[Y]] == BLOCK){
			switch(move){
			case 'W':
				if(map[newPosition[X]][newPosition[Y] - 1] == BLOCK) // multiple blocks aligned
					return 0;
				else if (map[newPosition[X]][newPosition[Y] - 1] == EMPTY) // move will push a block into an empty space
					return 1;
				else if (map[newPosition[X]][newPosition[Y] - 1] == HOLE) // move will push a block into a hole
					return 2;
				else if (!isOnMap(map, newPosition[X], newPosition[Y] - 1)) // move will attempt to push the block out of the map
					return 0;
				break;
			case 'S':
				if(map[newPosition[X]][newPosition[Y] + 1] == BLOCK) // multiple blocks aligned
					return 0;
				else if (map[newPosition[X]][newPosition[Y] + 1] == EMPTY) // move will push a block into an empty space
					return 1;
				else if (map[newPosition[X]][newPosition[Y] + 1] == HOLE) // move will push a block into a hole
					return 2;
				else if (!isOnMap(map, newPosition[X], newPosition[Y] + 1)) // move will attempt to push the block out of the map
					return 0;
				break;
			case 'A':
				if(map[newPosition[X] - 1][newPosition[Y]] == BLOCK) // multiple blocks aligned
					return 0;
				else if (map[newPosition[X] - 1][newPosition[Y]] == EMPTY) // move will push a block into an empty space
					return 1;
				else if (map[newPosition[X] - 1][newPosition[Y]] == HOLE) // move will push a block into a hole
					return 2;
				else if (!isOnMap(map, newPosition[X] - 1, newPosition[Y])) // move will attempt to push the block out of the map
					return 0;
				break;
			case 'D':
				if(map[newPosition[X] + 1][newPosition[Y]] == BLOCK) // multiple blocks aligned
					return 0;
				else if (map[newPosition[X] + 1][newPosition[Y]] == EMPTY) // move will push a block into an empty space
					return 1;
				else if (map[newPosition[X] + 1][newPosition[Y]] == HOLE) // move will push a block into a hole
					return 2;
				else if (!isOnMap(map, newPosition[X] + 1, newPosition[Y])) // move will attempt to push the block out of the map
					return 0;
				break;
			}
		}
		
		// the control will not reach here in normal cases
		return -1;		
	}
	
	/* This method contains the core logic of the game. It controls the gameplay
	 * 		and uses other methods (such as movePlayer, printMap, etc) to update
	 * 		the game state.
	 *
	 * @param  map  The 2D character array map of the current game.
	 * @param  player  An array containing the x and y coordinates of the player.
	 * @param  numberOfBlox  The number of blox currently in the game.
	 */
	public static void playGame(char[][] map, int[] player, int numberOfBlox){
		
		while(true){
			printMap(map, player);

			if(numberOfBlox < 1){
				System.out.println("You won!");
				return;
			}

			char move = getMove();
			if(move == 'Q')
				return;
			
			int status = movePlayer(map, player, move);
			switch(status){
			case 0:
				System.out.println("You cannot go that way.");
				break;
			case 1:				
				switch(move){
				case 'A':
					map[player[X]][player[Y]] = EMPTY;
					player[Y] -= 1;
					if (map[player[X]][player[Y]] == BLOCK){
						map[player[X]][player[Y]] = EMPTY;
						map[player[X]][player[Y] - 1] = BLOCK;
					}
					break;
				case 'D':
					map[player[X]][player[Y]] = EMPTY;
					player[Y] += 1;
					if (map[player[X]][player[Y]] == BLOCK){
						map[player[X]][player[Y]] = EMPTY;
						map[player[X]][player[Y] + 1] = BLOCK;
					}
					break;
				case 'W':
					map[player[X]][player[Y]] = EMPTY;
					player[X] -= 1;
					if (map[player[X]][player[Y]] == BLOCK){
						map[player[X]][player[Y]] = EMPTY;
						map[player[X] - 1][player[Y]] = BLOCK;
					}
					break;
				case 'S':
					map[player[X]][player[Y]] = EMPTY;
					player[X] += 1;
					if (map[player[X]][player[Y]] == BLOCK){
						map[player[X]][player[Y]] = EMPTY;
						map[player[X] + 1][player[Y]] = BLOCK;
					}
					break;
				}
				if (map[player[X]][player[Y]] == HOLE){
					printMap(map, player);
					System.out.println("You died!");
					return;
				}
				break;
			case 2:
				map[player[X]][player[Y]] = EMPTY;
				switch(move){
				case 'A':
					player[Y] -= 1;
					map[player[X]][player[Y]] = EMPTY;
					map[player[X]][player[Y] - 1] = EMPTY;
					break;
				case 'D':
					player[Y] += 1;
					map[player[X]][player[Y]] = EMPTY;
					map[player[X]][player[Y] + 1] = EMPTY;
					break;
				case 'W':
					player[X] -= 1;
					map[player[X]][player[Y]] = EMPTY;
					map[player[X] - 1][player[Y]] = EMPTY;
					break;
				case 'S':
					player[X] += 1;
					map[player[X]][player[Y]] = EMPTY;
					map[player[X] + 1][player[Y]] = EMPTY;
					break;
				}
				numberOfBlox--;
				break;
			case -1:
				System.out.println("Some Error has Occurred!! Aborting.."); // game will not reach this state in normal circumstances
				System.exit(1); // exit with non-zero exit code
				break;
			}
		}
		
	}
	
}
