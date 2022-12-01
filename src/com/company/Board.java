package com.company;

import java.util.Scanner;

public class Board
{
    //DECLARE PRIVATE VARIABLES
    private final int  ROW = 8;
    private final int  COL = 8;
    private String[][] board = new String[ROW][COL];
    private int row;
    private int col;
    private int bScore;
    private int wScore;
    private int turnsPassed;
    private boolean isBlack;
    private String empty = "〘◉〙";

    public Board() //CONSTRUCTOR
    {
        //SETS DEFAULT VALUES OF THE ARRAY (BOARD) AND INSTANCE VARIABLES
        for(int i = 0; i < board.length; i++)
            for(int j = 0; j < board[0].length; j++)
                board[i][j] = empty;
       board[3][3] =  "〘Ⓦ〙";
       board[3][4] =  "〘Ⓑ〙";
       board[4][4] =  "〘Ⓦ〙";
       board[4][3] =  "〘Ⓑ〙";
       bScore = 0;
       wScore = 0;
       turnsPassed = 0;
       isBlack = true;
    }

    public void printBoard() //GETS THE SCORE AND PRINTS THE BOARD
    {
        //RESETS THE SCORE FIRST
        bScore = 0;
        wScore = 0;
        //PRINT THE BOARD
        System.out.print("       ");
        for(int k = 0; k < board[0].length; k++)
            System.out.print(k + "            ");
        System.out.println();
        for(int i = 0; i < board.length; i++)
        {
            System.out.print(i);
            for (int j = 0; j < board[0].length; j++)
            {
                //IF LINE TO FIND SCORE
                if(board[i][j].equals("〘Ⓑ〙"))
                    bScore++;
                else if(board[i][j].equals("〘Ⓦ〙"))
                    wScore++;
                //PRINT BOARD VALUES
                System.out.print(board[i][j] + "  ");
            }
            System.out.println("\n");
        }
        //PRINT SCORES
        System.out.println("Current Scores:");
        System.out.print("Black: " + bScore + "\t\tWhite: " + wScore + "\n\n");
    }

    public void takeInput() //TAKES INPUT, VALIDATES, AND CALLS printBoard()
    {
        //SOME VARIABLES TO MANAGE WHILE LOOPS
        boolean isMoveNotPossible = true;
        boolean outOfBounds;

        //RUNS UNTIL THE MOVE IS POSSIBLE
        while(isMoveNotPossible)
        {
            //RUNS UNTIL USER ENTERS VALID NUMBERS
            outOfBounds = true;
            while(outOfBounds)
            {
                //ASK INPUT
                Scanner in = new Scanner(System.in);
                if(isBlack)
                {
                    System.out.println("Player 1〘Ⓑ〙 please enter a number corresponding to your desired row number: ");
                    row = in.nextInt();
                    System.out.println("Now please enter a number corresponding to your desired column number: ");
                    col = in.nextInt();
                }
                else
                {
                    System.out.println("Player 2 〘Ⓦ〙 please enter a number corresponding to your desired row number: ");
                    row = in.nextInt();
                    System.out.println("Now please enter a number corresponding to your desired column number: ");
                    col = in.nextInt();
                }
                //CHECK TO SEE IF INPUT IS VALID
                if(!(row < 8 && row >= 0 && col < 8 && col >= 0))
                    System.out.println("Invalid inputs. Please try again.");
                else
                {
                    outOfBounds = false;
                    turnsPassed++;
                }
            }

           //CHECKS TO SEE IF THE MOVE IS VALID (FOLLOWS THE RULES OF THE GAME)
            if(isValidMove())
            {
                //IF PASSED HERE, CHANGES THE VALUE IN THE ARRAY TO THE CURRENT PLAYER'S TOKEN
                if (isBlack)
                    board[this.row][this.col] = "〘Ⓑ〙";
                else
                    board[this.row][this.col] = "〘Ⓦ〙";
                //CHANGES CURRENT PLAYER
                isBlack = !isBlack ;
                //PRINTS BOARD
                printBoard();
                isMoveNotPossible = false;
            }
            else
            {
                //IF PASSED HERE, MOVE IS INVALID AND WILL ASK THE USER TO ENTER AGAIN
                System.out.println("Invalid move. Please try again.");
                isMoveNotPossible = true;
            }
        }

    }

    public boolean isValidMove() //CHECKS TO SEE IF MOVE IS LEGAL
    {
        //SOME VARIABLES TO HELP
        boolean doesPass = false;
        int count;
        String currentVal;
        String otherVal;

        //SETS currentVal AND otherVal SO I DON'T HAVE TO CREATE 16 CHECKS (8 FOR EACH PLAYER)
        if (isBlack)
        {
            currentVal = "〘Ⓑ〙";
            otherVal = "〘Ⓦ〙";
        }
        else
        {
            currentVal = "〘Ⓦ〙";
            otherVal = "〘Ⓑ〙";
        }
        //CHECK IF SPOT IS EMPTY
        if (!board[row][col].equals("〘◉〙"))
            return false;
        if ((col + 1 < COL - 1)) //CHECK VALUES TO THE RIGHT
        {
            count = 0;
            //CHECKS IF VALUE 1 TO THE RIGHT IS VALID
            if (board[row][col + 1].equals(otherVal))
                //LOOP TO RUN UNTIL END OF ROW OR UNTIL THE OPPOSITE VALUE IS FOUND OR IF INVALID
                for (int i = col + 1; i < COL; i++)
                {
                    //ADDS TO count IF THERE IS A VALID TOKEN
                    if (board[row][i].equals(otherVal))
                        count++;
                    //IF NOT, CHECKS FOR OPPOSITE TOKEN
                    else if (board[row][i].equals(currentVal))
                    {
                        //SETS TOKENS TO CURRENT TOKEN FOR count NUMBER OF TOKENS
                        for(int j = col + 1; j <= col + count; j++)
                            board[row][j] = currentVal;
                        doesPass = true;
                        break;
                    }
                    //BREAKS IF THERE IS AN EMPTY SPACE, PROMPTING THE USER TO ENTER AGAIN
                    else
                        break;
                }
        }
        if ((row + 1 < ROW - 1)) //CHECK VALUES DOWN
        {
            count = 0;
            //CHECKS IF VALUE 1 DOWN IS VALID
            if (board[row + 1][col].equals(otherVal))
                //LOOP TO RUN UNTIL END OF ROW OR UNTIL THE OPPOSITE VALUE IS FOUND OR IF INVALID
                for (int i = row + 1; i < ROW; i++)
                {
                    if (board[i][col].equals(otherVal))
                        //ADDS TO count IF THERE IS A VALID TOKEN
                        count++;
                    //IF NOT, CHECKS FOR OPPOSITE TOKEN
                    else if (board[i][col].equals(currentVal))
                    {
                        //SETS TOKENS TO CURRENT TOKEN FOR count NUMBER OF TOKENS
                        for(int j = row + 1; j <= row + count; j++)
                            board[j][col] = currentVal;
                        doesPass = true;
                        break;
                    }
                    //BREAKS IF THERE IS AN EMPTY SPACE, PROMPTING THE USER TO ENTER AGAIN
                    else
                        break;
                }
        }
        if ((col - 1 > 0)) //CHECK VALUES TO THE LEFT
        {
            count = 0;
            //CHECKS IF VALUE 1 LEFT IS VALID
            if (board[row][col - 1].equals(otherVal))
                //LOOP TO RUN UNTIL END OF ROW OR UNTIL THE OPPOSITE VALUE IS FOUND OR IF INVALID
                for (int i = col - 1; i >= 0; i--)
                {
                    if (board[row][i].equals(otherVal))
                        //ADDS TO count IF THERE IS A VALID TOKEN
                        count++;
                    //IF NOT, CHECKS FOR OPPOSITE TOKEN
                    else if (board[row][i].equals(currentVal))
                    {
                        for(int j = col - 1; j >= col - count; j--)
                            board[row][j] = currentVal;
                        doesPass = true;
                        break;
                    }
                    //BREAKS IF THERE IS AN EMPTY SPACE, PROMPTING THE USER TO ENTER AGAIN
                    else
                        break;
                }
        }
        if ((row - 1 > 0)) //CHECK VALUES UP
        {
            count = 0;
            //CHECKS IF VALUE 1 UP IS VALID
            if (board[row - 1][col].equals(otherVal))
                //LOOP TO RUN UNTIL END OF ROW OR UNTIL THE OPPOSITE VALUE IS FOUND OR IF INVALID
                for (int i = row - 1; i >= 0; i--)
                {
                    //ADDS TO count IF THERE IS A VALID TOKEN
                    if (board[i][col].equals(otherVal))
                        count++;
                    //IF NOT, CHECKS FOR OPPOSITE TOKEN
                    else if (board[i][col].equals(currentVal))
                    {
                        for(int j = row - 1; j >= row - count; j--)
                            board[j][col] = currentVal;
                        doesPass = true;
                        break;
                    }
                    //BREAKS IF THERE IS AN EMPTY SPACE, PROMPTING THE USER TO ENTER AGAIN
                    else
                        break;
                }
        }
        if((row - 1 > 0) && (col + 1 < COL - 1))//CHECK TOP RIGHT
        {
            count = 0;
            //CHECKS IF VALUE 1 UP RIGHT IS VALID
            if (board[row - 1][col + 1].equals(otherVal))
            {
                //WHILE LOOP TO RUN UNTIL REACHES ONE OF THE BOUNDARIES (ROW OR COL)
                int i = 1;
                while(col + i < COL && row - i >= 0)
                {
                    //ADDS TO COUNT IF TOKEN IS VALID
                    if (board[row - i][col + i].equals(otherVal))
                        count++;
                    //IF NOT, CHECKS FOR OPPOSITE TOKEN
                    else if (board[row - i][col + i].equals(currentVal))
                    {
                        //UPDATES THE VALUES TO THE CURRENT TOKEN
                        for (int j = 1; j <= count; j++)
                            board[row - j][col + j] = currentVal;
                        doesPass = true;
                        break;
                    }
                    //BREAKS IF THERE IS AN EMPTY SPACE, PROMPTING THE USER TO ENTER AGAIN
                    else
                        break;
                    i++;
                }
            }
        }
        if((row + 1 < ROW - 1) && (col + 1 < COL - 1))//CHECK BOTTOM RIGHT
        {
            count = 0;
            //CHECKS IF VALUE 1 DOWN RIGHT IS VALID
            if (board[row + 1][col + 1].equals(otherVal))
            {
                int i = 1;
                //WHILE LOOP TO RUN UNTIL REACHES ONE OF THE BOUNDARIES (ROW OR COL)
                while(col + i < COL - 1 && row + i < ROW - 1)
                {
                    //ADDS TO COUNT IF TOKEN IS VALID
                    if (board[row + i][col + i].equals(otherVal))
                        count++;
                    //IF NOT, CHECKS FOR OPPOSITE TOKEN
                    else if (board[row + i][col + i].equals(currentVal))
                    {
                        //UPDATES THE VALUES TO THE CURRENT TOKEN
                        for(int j = 1; j <= count; j++)
                            board[row + j][col + j] = currentVal;
                        doesPass = true;
                        break;
                    }
                    //BREAKS IF THERE IS AN EMPTY SPACE, PROMPTING THE USER TO ENTER AGAIN
                    else
                        break;
                    i++;
                }
            }
        }
        if((row + 1 < ROW - 1) && (col - 1 > 0))//CHECK BOTTOM LEFT
        {
            count = 0;
            //CHECKS IF VALUE 1 DOWN LEFT IS VALID
            if (board[row + 1][col - 1].equals(otherVal))
            {
                int i = 1;
                //WHILE LOOP TO RUN UNTIL REACHES ONE OF THE BOUNDARIES (ROW OR COL)
                while(col - i >= 0 && row + i < ROW - 1)
                {
                    //ADDS TO COUNT IF TOKEN IS VALID
                    if (board[row + i][col - i].equals(otherVal))
                        count++;
                    //IF NOT, CHECKS FOR OPPOSITE TOKEN
                    else if (board[row + i][col - i].equals(currentVal))
                    {
                        //UPDATES THE VALUES TO THE CURRENT TOKEN
                        for (int j = 1; j <= count; j++)
                            board[row + j][col - j] = currentVal;
                        doesPass = true;
                        break;
                    }
                    //BREAKS IF THERE IS AN EMPTY SPACE, PROMPTING THE USER TO ENTER AGAIN
                    else
                        break;
                    i++;
                }
            }
        }
        if((row - 1 > 0) && (col - 1 > 0))//CHECK TOP LEFT
        {
            count = 0;
            //CHECKS IF VALUE 1 DOWN LEFT IS VALID
            if (board[row - 1][col - 1].equals(otherVal))
            {
                int i = 1;
                //WHILE LOOP TO RUN UNTIL REACHES ONE OF THE BOUNDARIES (ROW OR COL)
                while(col - i >= 0 && row - i >= 0)
                {
                    //ADDS TO COUNT IF TOKEN IS VALID
                    if (board[row - i][col - i].equals(otherVal))
                        count++;
                    //IF NOT, CHECKS FOR OPPOSITE TOKEN
                    else if (board[row - i][col - i].equals(currentVal))
                    {
                        //UPDATES THE VALUES TO THE CURRENT TOKEN
                        for(int j = 1; j <= count; j++)
                            board[row - j][col - j] = currentVal;
                        doesPass = true;
                        break;
                    }
                    //BREAKS IF THERE IS AN EMPTY SPACE, PROMPTING THE USER TO ENTER AGAIN
                    else
                        break;
                    i++;
                }
            }
        }

        //RETURNS TRUE IF ONE OF THE ABOVE CASES ARE VALID, OTHERWISE RETURNS FALSE
        return doesPass;
    }

    //FUNCTION TO START THE GAME
    public void playGame()
    {
        //PRINTS THE BOARD FIRST
        printBoard();
        //RUNS UNTIL THE WHOLE BOARD IS FILLED
        while(turnsPassed < 64)
        {
            takeInput();
        }
        //DECLARES THE WINNER
        if(bScore > wScore)
            System.out.println("Player 1〘Ⓑ〙won the game!");
        else if(wScore > bScore)
            System.out.println("Player 2〘Ⓦ〙won the game!");
        else
            System.out.println("The game was a tie!");
    }
}