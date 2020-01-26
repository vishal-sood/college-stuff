/*
 ============================================================================
 Name        : MineSweeper.c
 Author      : Vishal Sood
 Version     :
 Copyright   : Your copyright notice
 Description : Basic implementation of the MineSweeper game in C, Ansi-style
 ============================================================================
 */

#include <stdio.h>
#include <stdlib.h>
#include <time.h>

/*
 * Defines an enumeration to assign numbers to different levels of difficulty
 */
typedef enum difficulty {
    Easy, Medium, Hard
} difficulty;

/*
 * Defines an enumeration to represent different types of cells
 */
typedef enum cell_type {
    Empty, Mine=9
} cell_type;

/*
 * Defines an enumeration to represent different states of cells
 */
typedef enum cell_state {
    Closed, Open
} cell_state;

/*
 * Defines a structure to hold the coordinates for a move/position on the board
 */
typedef struct move {
	int x;
	int y;
}move;

// game constants and variables
#define BOARD_SIZE		10 // board size
int numberOfMines[] = {10,16,25}; // integer array representing the initial numbers of mine for various levels of difficulty

int board[BOARD_SIZE][BOARD_SIZE]; // 2-D integer array representing the board to store the type/info of cells/tiles on the board
int boardState[BOARD_SIZE][BOARD_SIZE]; // 2-D integer array representing the board to store the current state of the board
int currentMines; // the number of mines in the current game
int numberOfClosedEmptyTiles; // to hold the number of closed empty tiles; once this becomes zero (0), the player wins the game
int isGameOver; // flag to determine whether the game is over or not

int computerPlayer; // flag to indicate whether user wants to play, or the computer shall play

/*
 * This method prints the welcome message and prompts the user if he/she wants to know about the game or read the instructions.
 */
void welcome()
{
	char choice;

	printf("Welcome to MINESWEEPER in C\n");

	printf("Enter 'a' for knowing About the game\n");
	printf("Enter 'i' for instructions\n");
	printf("Enter any other key to Enter the game\n");
	scanf("%c",&choice);
	fflush(stdin);

	if(choice == 'a')
	{
		printf("MineSweeper is a deceptively simple test of memory and reasoning—and one\n");
		printf("of the most popular games of all time. The goal is to find the empty squares\n");
		printf("and avoid the mines.\n");
		printf("Good Luck!!\n\n");

	}
	else if(choice == 'i'){
		printf("Objective of the Game: To find all the empty squares without clicking on any\n");
		printf("of the mines.\n");
		printf("Instructions:\n");
		printf("1. You can enter the coordinates (x,y) of any unopened cell to open it.\n");
		printf("2. The cell may be either empty or may contain a mine.\n");
		printf("3. Opening a cell with a mine will result in the end of the game.\n");
		printf("4. Opening an empty cell will reveal how many of its adjacent tiles\n");
		printf("   contain mines.\n");

	}

}

/*
 * This method sets the board to the initial state for starting the game. It randomly places a number (decided on the basis of level of difficulty)
 * of mines in the board.
 *
 * input parameters-
 * d  represents the difficulty according to which the board has to be initialized
 */
void start(difficulty d){
	int i, j, done, pos, c;
	currentMines = numberOfMines[d];
	c = currentMines;
	isGameOver = 0;
	numberOfClosedEmptyTiles = (BOARD_SIZE * BOARD_SIZE) - currentMines;
	srand(time(NULL));
	while(c>0){
		done = 0;
		do{
			pos = rand() % (BOARD_SIZE * BOARD_SIZE);
			if(board[pos/10][pos%10] != Mine){
				board[pos/10][pos%10] = Mine;
				done = 1;
			}
		}while(done != 1);
		c--;
	}

	for(i=0; i<BOARD_SIZE; i++){
		for(j=0; j<BOARD_SIZE; j++){
			if(board[i][j] != Mine){
				board[i][j] = Empty;
			}
			boardState[i][j] = Closed;
		}
	}
}

/*
 * This method checks whether the position represented by the input parameter lies on the board or not
 *
 * input parameters-
 * m  a move type structure representing the coordinates/position
 *
 * returns-
 * '1' if the position is on board, '0' otherwise
 */
int isOnBoard(move m){
	if(m.x>=0 && m.x<BOARD_SIZE && m.y>=0 && m.y<BOARD_SIZE)
		return 1;
	else
		return 0;
}

/*
 * This method checks whether the position represented by the input parameters lies on the board or not
 *
 * input parameters-
 * x  x-coordinate of the position
 * y  y-coordinate of the position
 *
 * returns-
 * '1' if the position is on board, '0' otherwise
 */
int isOnBoardXY(int x, int y){
	if(x>=0 && x<BOARD_SIZE && y>=0 && y<BOARD_SIZE)
		return 1;
	else
		return 0;
}

/*
 * This method prints the current state of the board to the console and prints status messages, if any
 */
void printBoard(){
	int i, j;
	printf("\n");
	printf("Number of Mines: %d\n", currentMines);
	printf("\n");
	printf(".-.-.-.-.-.-.-.-.-.-.");
	printf("\n");
	for(i=0; i<BOARD_SIZE; i++){
		printf("|");
		for(j=0; j<BOARD_SIZE; j++){
			if(isGameOver || boardState[i][j] == Open){ 	// if game is over or the current cell is opened
				if(board[i][j] == Mine){ 					// show the mines
					printf("*");
				}
				else{
					if(boardState[i][j] == Closed){ // if the cell is closed
						printf(" ");				// print nothing
					}
					else{
						printf("%d", board[i][j]);	// otherwise, print the cell's content
					}
				}
			}
			else{
				printf(" ");	// if the cell is closed print nothing
			}
			printf("|");
		}
		printf("\n");
	}
	printf(".-.-.-.-.-.-.-.-.-.-.");
	printf("\n");
}

/*
 * This method prints the status messages indicating the current status of the game.
 *
 * input parameters-
 * status  '1' representing the player has won the game, '0' indicating loss
 */
void printStatusMessage(int status){
	if(status == 1){
		printf("\n");
		printf("Congratulations!! You have completed the game successfully..\n");
		printf("\n");
	}
	else{
		printf("\n");
		printf("You clicked on a MINE!! Game Over..\n");
		printf("\n");
	}
}

/*
 * This method prompts the user to enter the coordinates of the cell to be opened in the next move.
 *
 * returns-
 * a move type structure representing the coordinates/position entered by the user
 */
move getNextMove(){
	move nextMove;
	do{
		printf("\n");
		printf("Enter the coordinates (x, y) for the next move:\n");
		printf("(Range: 1-10)\n");
		if(computerPlayer == 0){
			scanf("%d", &nextMove.x);
			scanf("%d", &nextMove.y);
			fflush(stdin);
			nextMove.x--;
			nextMove.y--;
		}
		else{
			srand(time(NULL));
			nextMove.x = rand()%10;
			nextMove.y = rand()%10;
		}
	}while((!isOnBoard(nextMove)) && boardState[nextMove.x][nextMove.y] != Closed);

	return nextMove;
}

/*
 * This method makes the next move based on the parameter passed, and updates the board and other game variables accordingly
 *
 * input parameters-
 * nextMove a move type structure representing the coordinates/position entered by the user
 */
void makeMove(move nextMove){
	int i;

	int adjacentMines = 0;
	int dirx[] = {-1,0,1,-1,1,-1,0,1}; // directions along x-axis
	int diry[] = {-1,-1,-1,0,0,1,1,1}; // directions along y-axis
	//these dirx and diry arrays can be used to check the celss adjacent to the current cell in this particular order-
	//top-left, up, top-right, left, right, bottom-left, down, bottom-right

	if(boardState[nextMove.x][nextMove.y] == Open) // if the cell is already open, return without doing anything
		return;

	boardState[nextMove.x][nextMove.y] = Open;
	if(board[nextMove.x][nextMove.y] == Mine){
		isGameOver = 1;
		printBoard();
		printStatusMessage(0);
		return;
	}
	numberOfClosedEmptyTiles--;

	//checking for adjacent mines
	for(i=0; i<8; i++){
		if(isOnBoardXY(nextMove.x + dirx[i], nextMove.y + diry[i]) && board[nextMove.x + dirx[i]][nextMove.y + diry[i]] == Mine)
			adjacentMines++;
	}

	if(adjacentMines != 0){
		board[nextMove.x][nextMove.y] = adjacentMines;
	}
	else{ // if all adjacent squares are empty, then recursively call nextMove for all the adjacent squares
		for(i=0; i<8; i++){
			move newMove;
			newMove.x = nextMove.x + dirx[i];
			newMove.y = nextMove.y + diry[i];
			makeMove(newMove);
		}
	}

	if(numberOfClosedEmptyTiles == 0){
		isGameOver = 1;
		printBoard();
		printStatusMessage(1);
	}

}

/*
 * This sets the playing control to computer
 */
void setComputer(){
	computerPlayer = 1;
}

int main(void) {

	int dif;
	char comp;

	welcome(); // print the welcome message

	// prompt user to enter level of difficulty
	do{
		printf("\n");
		printf("Choose the level of difficulty:\n");
		printf("1. Easy\n");
		printf("2. Medium\n");
		printf("3. Hard\n");
		printf("Enter your choice (1-3):\t");
		scanf("%d", &dif);
		fflush(stdin);

		// initialize the board according to the level of difficulty selected
		switch(dif){
		case 1:
			start(Easy);
			break;
		case 2:
			start(Medium);
			break;
		case 3:
			start(Hard);
			break;
		default:
			printf("\nInvalid Choice Entered!! Try Again..\n");
			break;
		}
	}while(dif != 1 && dif != 2 && dif != 3);

	printf("Do you want the computer to play? (Y/N)");
	scanf("%c", &comp);
	fflush(stdin);
	if(comp == 'y' || comp == 'Y')
		setComputer();

	// the game loop
	while(!isGameOver){
		printBoard(); // print current state of board
		makeMove(getNextMove()); // make move by asking next move
	}
	return EXIT_SUCCESS;
}
