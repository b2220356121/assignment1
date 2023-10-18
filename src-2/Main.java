
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.*;
            public class Main {

            public static String[] readFile(String path){
                try{
                    int i=0;
                    // Get the number of lines in the file
                    int length= Files.readAllLines(Paths.get(path)).size();
                    String[] results=new String[length];
                    // Read all the lines from the file and add them to the result array
                    for (String line: Files.readAllLines(Paths.get(path))){
                        results[i++]=line;
                    }
                    return results;
                    // If there is an error while reading the file, print the stack trace and return null
                } catch (IOException e){
                    e.printStackTrace();
                    return null;
                }

            }
            public static void main(String[] args) {
                try{
                    boolean hole=false;
                    // Create a FileWriter object to write output to a file named "output.txt"
                    FileWriter myWriter = new FileWriter("output.txt");
                    // Get the file path from the command-line argument
                    String map= args[0];
                    String[] lines=readFile (map);
                    // Convert the game board to a 2D String array
                    String board =(Arrays.toString(lines));
                    String boardNoSpace=board.replaceAll("\\s+" ,"");
                    boardNoSpace = boardNoSpace.substring(1, boardNoSpace.length() - 1);
                    String[] boardArray = boardNoSpace.split(",");
                    int row=boardArray.length;
                    int column= boardArray[0].length();
                    String[][] board2d = new String[row][column];
                    for (int k=0;k<row;k++){
                        String rowValue=boardArray[k];
                        for (int j=0;j<column;j++){
                            board2d[k][j]=String.valueOf(rowValue.charAt(j));
                        }
                    }
                    // Create a new ArrayList to store the movements
                    ArrayList<String> moveList = new ArrayList<String>();
                    // Read the movements from a file and store them in the ArrayList
                    String movement=args[1];
                    String[] movements=readFile (movement);
                    String move=(Arrays.toString(movements)).replaceAll("\\s+" ,"");
                    move=move.substring(1, move.length() - 1);
                    for (int i = 0; i < move.length(); i++) {
                        moveList.add(String.valueOf(move.charAt(i)));
                    }
                    // Set the initial position of the ball to (0, 0)
                    int ballRow=0;
                    int ballCol=0;
                    // Write the current state of the game board to the output file
                    myWriter.write("Game board:"+"\n");
                    for (int t = 0; t < row; t++) {
                        for (int m = 0; m < column; m++) {
                            myWriter.write(board2d[t][m] + " ");
                            if (board2d[t][m].equals("*")){
                                // Update the position of the ball if it is found on the game board
                                ballRow+=t;
                                ballCol+=m;
                            }
                        }
                        myWriter.write("\n");
                    }
                    myWriter.write("\n");
                    // Write the movements to the output file
                    myWriter.write("Your movement is:"+"\n");

                    for (int f=0;f<moveList.size();f++){
                        myWriter.write(moveList.get(f)+" ");
                    }
                    myWriter.write("\n");

                    myWriter.write("\n");
                    // Set the initial score to 0
                    int score=0;
                    // Loop through each element in the moveList
                    for (int n=0;n<moveList.size();n++){
                        // Check if the current move is "L" (for "left")
                        if (moveList.get(n).equals("L")){
                            // If the ball is at the leftmost column, move it to the rightmost column
                            if (ballCol==0){
                                ballCol+=column;
                            }
                            // Check if the element to the left of the ball is a wall
                            if (board2d[ballRow][ballCol-1].equals("W")){
                                // If the ball is not at the rightmost column, move the rightmost element to the leftmost column
                                if (ballCol!=column){
                                    // Replace the leftmost element with the rightmost element
                                    if(ballCol==column-1){
                                        board2d[ballRow][column-1]=board2d[ballRow][0];
                                        board2d[ballRow][0]="*";
                                        // Determine what type of element the rightmost element is and update score and element type accordingly
                                        if (board2d[ballRow][column-1].equals("R")){
                                            board2d[ballRow][column-1]="X";
                                            score+=10;
                                        }
                                        else if (board2d[ballRow][column-1].equals("Y")){
                                            board2d[ballRow][column-1]="X";
                                            score+=5;
                                        }
                                        else if (board2d[ballRow][column-1].equals("B")){
                                            board2d[ballRow][column-1]="X";
                                            score-=5;
                                        }
                                        else if (board2d[ballRow][column-1].equals("H")){
                                            board2d[ballRow][column-1]=" ";
                                            board2d[ballRow][0]="H";
                                            hole=true;
                                            // Exit the loop if the ball falls into a hole
                                            break;
                                        }
                                        // Reset the ball's column to the leftmost column
                                        ballCol=0;


                                    // Check if the current move is not a leftward move
                                    }else{
                                        // Move the ball one space to the right
                                        board2d[ballRow][ballCol]=board2d[ballRow][ballCol+1];
                                        board2d[ballRow][ballCol+1]="*";
                                        // Determine what type of element the ball has moved onto and update score and element type accordingly
                                        if (board2d[ballRow][ballCol].equals("R")){
                                            board2d[ballRow][ballCol]="X";
                                            score+=10;
                                        }
                                        else if (board2d[ballRow][ballCol].equals("Y")){
                                            board2d[ballRow][ballCol]="X";
                                            score+=5;
                                        }
                                        else if (board2d[ballRow][ballCol].equals("B")){
                                            board2d[ballRow][ballCol]="X";
                                            score-=5;
                                        }
                                        else if (board2d[ballRow][ballCol].equals("H")){
                                            // If the ball has fallen into a hole, replace the hole with the ball and update the "hole" flag
                                            board2d[ballRow][ballCol]=" ";
                                            board2d[ballRow][ballCol+1]="H";
                                            hole=true;
                                            // Exit the loop if the ball falls into a hole
                                            break;
                                        }
                                        ballCol=ballCol+1;
                                    }


                                }else{
                                    // move the ball to the leftmost column
                                    board2d[ballRow][0]=board2d[ballRow][1];
                                    board2d[ballRow][1]="*";
                                    // check the content of the new cell and update score accordingly
                                    if (board2d[ballRow][0].equals("R")){
                                        board2d[ballRow][0]="X";
                                        score+=10;
                                    }
                                    else if (board2d[ballRow][0].equals("Y")){
                                        board2d[ballRow][0]="X";
                                        score+=5;
                                    }
                                    else if (board2d[ballRow][0].equals("B")){
                                        board2d[ballRow][0]="X";
                                        score-=5;
                                    }
                                    else if (board2d[ballRow][0].equals("H")){
                                        // move the ball into the hole and break the loop
                                        board2d[ballRow][0]=" ";
                                        board2d[ballRow][1]="H";
                                        hole=true;
                                        break;
                                    }
                                    // update the column index of the ball
                                    ballCol=1;


                                }
                            }else{
                                // If the new position to the left of the ball is a hole
                                if (board2d[ballRow][ballCol-1].equals("H")){
                                    board2d[ballRow][ballCol]=" ";
                                    hole=true;
                                    break;
                                }
                                // If the new position to the left of the ball is a red block
                                else if (board2d[ballRow][ballCol-1].equals("R")){
                                    board2d[ballRow][ballCol]="X";
                                    board2d[ballRow][ballCol-1]="*";
                                    score+=10;
                                }
                                // If the new position to the left of the ball is a blue block
                                else if (board2d[ballRow][ballCol-1].equals("B")){
                                    board2d[ballRow][ballCol]="X";
                                    board2d[ballRow][ballCol-1]="*";
                                    score-=5;
                                }
                                // If the new position to the left of the ball is a yellow block
                                else if (board2d[ballRow][ballCol-1].equals("Y")){
                                    board2d[ballRow][ballCol]="X";
                                    board2d[ballRow][ballCol-1]="*";
                                    score+=5;
                                    // If the new position to the left of the ball is empty
                                }else{
                                    board2d[ballRow][ballCol]=board2d[ballRow][ballCol-1];
                                    board2d[ballRow][ballCol-1]="*";

                                }
                                ballCol--;
                            }
                        }
                        if (moveList.get(n).equals("R")){
                            if (ballCol==column-1){
                                ballCol=-1;
                            }
                            // Check if ball will hit a wall or move normally
                            if (board2d[ballRow][ballCol+1].equals("W")){
                                // If ball hits a wall, move it to the other side
                                if (ballCol!=-1){
                                    if (ballCol==0){
                                        board2d[ballRow][0]=board2d[ballRow][column-1];
                                        board2d[ballRow][column-1]="*";
                                        // Check if there's a special tile the ball is hitting
                                        if (board2d[ballRow][0].equals("R")){
                                            board2d[ballRow][0]="X";
                                            score+=10;
                                        }
                                        else if (board2d[ballRow][0].equals("Y")){
                                            board2d[ballRow][0]="X";
                                            score+=5;
                                        }
                                        else if (board2d[ballRow][0].equals("B")){
                                            board2d[ballRow][0]="X";
                                            score-=5;
                                        }
                                        else if (board2d[ballRow][0].equals("H")){
                                            board2d[ballRow][0]=" ";
                                            board2d[ballRow][column-1]="H";
                                            hole=true;
                                            break;
                                        }
                                        // Set ball coordinates after movement is completed
                                        ballCol=column-1;
                                    }else{
                                        // Update the current position of the ball and the cell it moves to
                                        board2d[ballRow][ballCol]=board2d[ballRow][ballCol-1];
                                        board2d[ballRow][ballCol-1]="*";
                                        // Check if the cell the ball moves to contains a special cell type and update the score accordingly
                                        if (board2d[ballRow][ballCol].equals("R")){
                                            board2d[ballRow][ballCol]="X";
                                            score+=10;
                                        }
                                        else if (board2d[ballRow][ballCol].equals("Y")){
                                            board2d[ballRow][ballCol]="X";
                                            score+=5;
                                        }
                                        else if (board2d[ballRow][ballCol].equals("B")){
                                            board2d[ballRow][ballCol]="X";
                                            score-=5;
                                        }
                                        else if (board2d[ballRow][ballCol].equals("H")){
                                            // If the cell contains a hole, set the hole flag and break out of the loop
                                            board2d[ballRow][ballCol]=" ";
                                            board2d[ballRow][ballCol-1]="H";
                                            hole=true;
                                            break;
                                        }
                                        // Decrement the column index of the ball
                                        ballCol=ballCol-1;

                                    }

                                }else{
                                    // Move the ball to the left edge of the board when it reaches the right edge
                                    board2d[ballRow][column-1]=board2d[ballRow][column-2];
                                    board2d[ballRow][column-2]="*";
                                    // Check the content of the cell where the ball will move
                                    if (board2d[ballRow][column-1].equals("R")){
                                        board2d[ballRow][column-1]="X";
                                        score+=10;
                                    }
                                    else if (board2d[ballRow][column-1].equals("Y")){
                                        board2d[ballRow][column-1]="X";
                                        score+=5;
                                    }
                                    else if (board2d[ballRow][column-1].equals("B")){
                                        board2d[ballRow][column-1]="X";
                                        score-=5;
                                    }
                                    else if (board2d[ballRow][column-1].equals("H")){
                                        board2d[ballRow][column-1]=" ";
                                        board2d[ballRow][column-2]="*";
                                        hole=true;
                                        break;
                                    }
                                    // Update the position of the ball to the left cell after moving it.
                                    ballCol=column-2;
                                }
                            }else{
                                // Check if the ball has landed in a hole
                                if (board2d[ballRow][ballCol+1].equals("H")){
                                    board2d[ballRow][ballCol]=" ";
                                    hole=true;
                                    break;
                                }
                                // Check the content of the cell where the ball will move
                                else if (board2d[ballRow][ballCol+1].equals("R")){
                                    board2d[ballRow][ballCol]="X";
                                    board2d[ballRow][ballCol+1]="*";
                                    score+=10;
                                }
                                else if (board2d[ballRow][ballCol+1].equals("B")){
                                    board2d[ballRow][ballCol]="X";
                                    board2d[ballRow][ballCol+1]="*";
                                    score-=5;
                                }
                                else if (board2d[ballRow][ballCol+1].equals("Y")){
                                    board2d[ballRow][ballCol]="X";
                                    board2d[ballRow][ballCol+1]="*";
                                    score+=5;
                                }else{
                                    board2d[ballRow][ballCol]=board2d[ballRow][ballCol+1];
                                    board2d[ballRow][ballCol+1]="*";

                                }
                                ballCol++;// Increment the column index of the ball's position
                            }
                        }
                        // Check if the move is "Up"
                        if (moveList.get(n).equals("U")){
                            // If the ball is at the top row, wrap around to the bottom row
                            if (ballRow==0){
                                ballRow+=row;
                            }
                            // Check if the ball hits a wall block
                            if (board2d[ballRow-1][ballCol].equals("W")){
                                if (ballRow!=row){
                                    if(ballRow==row-1){
                                        // Move the ball to the bottom row
                                        board2d[row-1][ballCol]=board2d[0][ballCol];
                                        board2d[0][ballCol]="*";
                                        // Check if the ball hits a block of a specific color
                                        if (board2d[row-1][ballCol].equals("R")){
                                            board2d[row-1][ballCol]="X";
                                            score+=10;
                                        }
                                        else if (board2d[row-1][ballCol].equals("Y")){
                                            board2d[row-1][ballCol]="X";
                                            score+=5;
                                        }
                                        else if (board2d[row-1][ballCol].equals("B")){
                                            board2d[row-1][ballCol]="X";
                                            score-=5;
                                        }
                                        else if (board2d[row-1][ballCol].equals("H")){
                                            board2d[row-1][ballCol]=" ";
                                            board2d[0][ballCol]="H";
                                            hole=true;
                                            break;
                                        }
                                        ballRow=0;// Set the row index of the ball's position to 0

                                    }else{
                                        // Move the ball down one row

                                        board2d[ballRow][ballCol]=board2d[ballRow+1][ballCol];
                                        board2d[ballRow+1][ballCol]="*";
                                        // Check if the ball hits a block of a specific color
                                        if (board2d[ballRow][ballCol].equals("R")){
                                            board2d[ballRow][ballCol]="X";
                                            score+=10;
                                        }
                                        else if (board2d[ballRow][ballCol].equals("Y")){
                                            board2d[ballRow][ballCol]="X";
                                            score+=5;
                                        }
                                        else if (board2d[ballRow][ballCol].equals("B")){
                                            board2d[ballRow][ballCol]="X";
                                            score-=5;
                                        }
                                        // Check if the ball falls into a hole
                                        else if (board2d[ballRow][ballCol].equals("H")){
                                            board2d[ballRow][ballCol]=" ";
                                            board2d[ballRow+1][ballCol]="H";
                                            hole=true;
                                            break;
                                        }
                                        ballRow=ballRow+1;// Increase the row index of the ball's position by 1
                                    }
                                // If the ball can't move up, it falls down to the first row
                                }else{
                                    board2d[0][ballCol]=board2d[1][ballCol];
                                    board2d[1][ballCol]="*";
                                    ballRow=ballRow+1;
                                    // Check the color of the cell the ball falls onto and update the score accordingly

                                    if (board2d[0][ballCol].equals("R")){
                                        board2d[0][ballCol]="X";
                                        score+=10;
                                    }
                                    else if (board2d[0][ballCol].equals("Y")){
                                        board2d[0][ballCol]="X";
                                        score+=5;
                                    }
                                    else if (board2d[0][ballCol].equals("B")){
                                        board2d[0][ballCol]="X";
                                        score-=5;
                                    }
                                    else if (board2d[0][ballCol].equals("H")){
                                        board2d[0][ballCol]=" ";
                                        board2d[1][ballCol]="H";
                                        hole=true;
                                        break;
                                    }
                                    ballRow=1;
                                }
                            }else{
                                // Check what's above the ball
                                if (board2d[ballRow-1][ballCol].equals("H")){
                                    board2d[ballRow][ballCol]=" ";
                                    hole=true;
                                    break;
                                }

                                else if (board2d[ballRow-1][ballCol].equals("R")){
                                    board2d[ballRow][ballCol]="X";
                                    board2d[ballRow-1][ballCol]="*";
                                    score+=10;// Update score for hitting red block
                                }
                                else if (board2d[ballRow-1][ballCol].equals("B")){
                                    board2d[ballRow][ballCol]="X";
                                    board2d[ballRow-1][ballCol]="*";
                                    score-=5;// Update score for hitting black block
                                }
                                else if (board2d[ballRow-1][ballCol].equals("Y")){
                                    board2d[ballRow][ballCol]="X";
                                    board2d[ballRow-1][ballCol]="*";
                                    score+=5; // Update score for hitting yellow block
                                }else{
                                    board2d[ballRow][ballCol]=board2d[ballRow-1][ballCol];
                                    board2d[ballRow-1][ballCol]="*";

                                }
                                ballRow--; // Move ball up one row
                            }
                        }
                        if (moveList.get(n).equals("D")){
                            // reset row to top if move is "D" and ball is at bottom
                            if (ballRow==row-1){
                                ballRow=-1;
                            }
                            if (board2d[ballRow+1][ballCol].equals("W")){
                                if (ballRow!=-1){
                                    if (ballRow==0){
                                        // Move ball to the top row, update score based on obstacles, and check for holes
                                        board2d[0][ballCol]=board2d[row-1][ballCol];
                                        board2d[row-1][ballCol]="*";
                                        // Check if the ball hits a block of a specific color
                                        if (board2d[0][ballCol].equals("R")){
                                            board2d[0][ballCol]="X";
                                            score+=10;
                                        }
                                        else if (board2d[0][ballCol].equals("Y")){
                                            board2d[0][ballCol]="X";
                                            score+=5;
                                        }
                                        else if (board2d[0][ballCol].equals("B")){
                                            board2d[0][ballCol]="X";
                                            score-=5;
                                        }
                                        // Check if the ball falls into a hole
                                        else if (board2d[0][ballCol].equals("H")){
                                            board2d[0][ballCol]=" ";
                                            board2d[row-1][ballCol]="H";
                                            hole=true;
                                            break;
                                        }
                                        ballRow=row-1;

                                    }else{
                                        // update the board2d accordingly
                                        board2d[ballRow][ballCol]=board2d[ballRow-1][ballCol];
                                        board2d[ballRow-1][ballCol]="*";
                                        // check if the new position has a special tile and update the score accordingly

                                        if (board2d[0][ballCol].equals("R")){
                                            board2d[0][ballCol]="X";
                                            score+=10;
                                        }
                                        else if (board2d[ballRow][ballCol].equals("Y")){
                                            board2d[ballRow][ballCol]="X";
                                            score+=5;
                                        }
                                        else if (board2d[ballRow][ballCol].equals("B")){
                                            board2d[ballRow][ballCol]="X";
                                            score-=5;
                                        }
                                        else if (board2d[ballRow][ballCol].equals("H")){
                                            // if the new position is a hole, move the ball to the hole and end the loop
                                            board2d[ballRow][ballCol]=" ";
                                            board2d[ballRow-1][ballCol]="H";
                                            hole=true;
                                            break;
                                        }
                                        ballRow=ballRow-1;

                                    }


                                }else{
                                    // Move ball up one row
                                    board2d[row-1][ballCol]=board2d[row-2][ballCol];
                                    board2d[row-2][ballCol]="*";
                                    // Check the item on the new ball position and update score accordingly

                                    if (board2d[row-1][ballCol].equals("R")){
                                        board2d[row-1][ballCol]="X";
                                        score+=10;
                                    }
                                    else if (board2d[row-1][ballCol].equals("Y")){
                                        board2d[row-1][ballCol]="X";
                                        score+=5;
                                    }
                                    else if (board2d[row-1][ballCol].equals("B")){
                                        board2d[row-1][ballCol]="X";
                                        score-=5;
                                    }
                                    else if (board2d[row-1][ballCol].equals("H")){
                                        board2d[row-1][ballCol]=" ";
                                        board2d[row-2][ballCol]="H";
                                        hole=true;
                                        break;
                                    }
                                    ballRow=row-2;// Update ballRow to the new position

                                }
                            }else{
                                // Check if there's a hole in the cell below the ball
                                if (board2d[ballRow+1][ballCol].equals("H")){
                                    board2d[ballRow][ballCol]=" ";
                                    hole=true;
                                    break;
                                }
                                // Check if the cell below the ball contains a red block
                                if (board2d[ballRow+1][ballCol].equals("R")){
                                    board2d[ballRow][ballCol]="X";
                                    board2d[ballRow+1][ballCol]="*";
                                    score+=10;
                                }
                                // Check if the cell below the ball contains a blue block
                                if (board2d[ballRow+1][ballCol].equals("B")){
                                    board2d[ballRow][ballCol]="X";
                                    board2d[ballRow+1][ballCol]="*";
                                    score-=5;
                                }
                                // Check if the cell below the ball contains a yellow block
                                if (board2d[ballRow+1][ballCol].equals("Y")){
                                    board2d[ballRow][ballCol]="X";
                                    board2d[ballRow+1][ballCol]="*";
                                    score+=5;
                                }else{
                                    // If the cell below the ball contains a normal block, move the ball down
                                    board2d[ballRow][ballCol]=board2d[ballRow+1][ballCol];
                                    board2d[ballRow+1][ballCol]="*";
                                }
                                ballRow++;

                            }
                        }


                    }
                    // Write the game board and score to the output file
                    myWriter.write("Your output is:"+" \n");
                    for (int t = 0; t < row; t++) {
                        for (int m = 0; m < column; m++) {
                            myWriter.write(board2d[t][m] + " ");
                        }
                        myWriter.write(" \n");
                    }
                    myWriter.write(" \n");
                    // Check if the game is over and print the appropriate message
                    if (hole==true){
                        myWriter.write("Game Over!"+"\n");
                    }
                    myWriter.write("Score: "+score+"\n");
                    // Close the output file
                    myWriter.close();

                }
                // Handle any possible errors during file writing
                catch (IOException e) {
                    e.printStackTrace();
            }
                }}






