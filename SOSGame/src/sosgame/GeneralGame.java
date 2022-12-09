package sosgame;

import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Button;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.RowConstraints;
import javafx.scene.shape.Line;
import javafx.scene.paint.Color;


public class GeneralGame extends SimpleGame{

    private int size;
    private GridPane boardGrid;
    private Label turnField;
    private RadioButton redS;
    private RadioButton blueS;
    private RadioButton redComp;
    private RadioButton blueComp;
    private AnchorPane gridAnchor;

    private Button[][] board;
    private String player;
    private int bLines;
    private int rLines;
    private int moves;

    public GeneralGame(int s, RadioButton rS, RadioButton bS, Label tF, GridPane bG, AnchorPane gA, RadioButton rC, RadioButton bC){
        
        System.out.println("Inputted size is "+s);
        size = s;
        player = "Red";
        boardGrid = bG;
        turnField = tF;
        redS = rS;
        blueS = bS;
        gridAnchor = gA;
        redComp = rC;
        blueComp = bC;
        bLines = 0;
        rLines = 0;
        moves = 0;

    }

    @Override
    public void boardGen() {
        System.out.println("Beginning board generation for GENERAL Game");
        board = new Button[size][size];
        turnField.setText("Red");
        player = "Red";
        moves = 0;
        createRecording();
        for (int col = 0; col < size; col++){
//Set dimensions of grid cells.
            ColumnConstraints c = new ColumnConstraints();
            c.setPercentWidth(1/size);
            boardGrid.getColumnConstraints().add(c);
            for (int row = 0; row < size; row++){
                RowConstraints r = new RowConstraints();
                r.setPercentHeight(1/size);
                boardGrid.getRowConstraints().add(r);
//Fill grid cells with buttons.
                Button btn = new Button(" ");
                btn.setPrefHeight(50);
                btn.setPrefWidth(50);
                boardGrid.add(btn, col, row);
                board[row][col] = btn;
                setConditions(btn,row,col);
            }
        }   
    System.out.println("Board generation for GENERAL Game complete.");
    }

    @Override
    public void setConditions(Button btn, int row, int col) {
        //Set onClick methods for grid buttons.               
        btn.setOnAction(e -> {
            if (btn.getText() == " ") {
                if (player == "Red") {
                    if (redS.isSelected()) {
                        btn.setText("S");
                    } else {
                        btn.setText("O");
                    }
                } else if (player == "Blue"){
                    if (blueS.isSelected()) {
                        btn.setText("S");
                    } else {
                        btn.setText("O");
                    }
                } else {
                    return;
                }
                sosCheck(btn,row,col);
            }
        });
    }
   
   @Override
   public void sosCheck(Button btn, int row, int col) {
    if(btn.getText() == "S") {
        sValidate(row, col, false);
    } else if (btn.getText() == "O") {
        oValidate(row, col,true);
    } else {
        if (! sValidate(row, col,true)) {
            oValidate(row, col,true);
        }
    }
    moves++;
    recordMove(player, row, col);
    nextTurn();  
}

    @Override
    public void nextTurn() {
        System.out.println("Starting move " + (moves+1) + " out of " + (size*size));
        if (moves >= size * size) {
            System.out.println("Final move made.");
            winCheck();
        }
        if (player != "None") {
            player = (player == "Red"? "Blue" : "Red");
            turnField.setText(player);
        }
        if (player == "Red") {
            System.out.println("Red's turn.");
            if (redComp.isSelected()) {
                computerMove();
            }
        } else {
            if (player == "Blue") {
                System.out.println("Blue's turn.");
                if (blueComp.isSelected()) {
                    computerMove();
                }
            }
        }
    }

    @Override    
    public void winCheck() {
        if (rLines > bLines) {
            turnField.setText("Red Wins!");
            player = "None";
        } else if (bLines > rLines) {
            turnField.setText(" Blue Wins!");
            player = "None";
        } else if (moves >= size * size) {
            turnField.setText("No one wins!");
            player = "None";
        }
        return;
    }

    @Override    
    public void drawLine(Button start, Button end, String color) {
        Line line = new Line();
        double posAdjust =  start.getWidth() / 2;
        line.setStartX(start.getLayoutX() + posAdjust);
        line.setStartY(start.getLayoutY() + posAdjust);
        line.setEndX(end.getLayoutX() + posAdjust);
        line.setEndY(end.getLayoutY() + posAdjust);

        if (color == "Red") {
            line.setStroke(Color.RED);
            rLines++;
            player = "Red";
        } else {
            line.setStroke(Color.BLUE);
            bLines++;
            player = "Blue";
        }
        gridAnchor.getChildren().add(line);
        return;
    }

    @Override
    public boolean sValidate(int row, int col, boolean draw) {
        //.out.println("Validating S placement.");
        for (int r = -1; r <= 1; r++) {
            for ( int c = -1; c <= 1; c++) {
    //Checking to make sure an SOS won't fall out of bounds.
                if(r < 0) {
                    if (row + r + r < 0) {
                        continue;
                    }
                } else {
                    if (row + r + r >= size) {
                        continue;
                    }
                }
                if (c < 0) {
                    if (col + c + c < 0) {
                        continue;
                    }
                } else {
                    if (col + c + c >= size) {
                        continue;
                    }
                }
    //Checking if there is an SO / OS pattern adjacent to the cell in question.
                if (board[row+r][col+c].getText() == "O") {
                    if (board[row+r+r][col+c+c].getText() == "S") {
                        System.out.println("SOS Found.");
                        if (draw) {
                            board[row][col].setText("S");
                            drawLine(board[row+r+r][col+c+c], board[row][col], player);
                            player = (player == "Red"? "Blue" : "Red");
                        }
                        return true;
                    }
                }
            }
        }
    //Returns false if no SO / OS pattern is found.
        return false;
    }

    @Override
    public boolean oValidate(int row, int col, boolean draw) {
        //System.out.println("Validating O placement.");
        for (int r = -1; r <= 1; r++) {
            for ( int c = -1; c <= 1; c++) {
    //Checking to make sure an SOS won't fall out of bounds.
                if(r < 0) {
                    if (row + r < 0 || row - r >= size) {
                        continue;
                    }
                } else {
                    if (row + r >= size || row - r < 0) {
                        continue;
                    }
                }
                if (c < 0) {
                    if (col + c < 0 || col - c >= size) {
                        continue;
                    }
                } else {
                    if (col + c >= size || col - c < 0) {
                        continue;
                    }
                }
    //Checking if there is an S_S pattern adjacent to the cell in question.
                if (board[row+r][col+c].getText() == "S") {
                    if (board[row-r][col-c].getText() == "S") {
                        System.out.println("SOS Found.");
                        if (draw) {
                            board[row][col].setText("O");
                            drawLine(board[row-r][col-c], board[row+r][col+c], player);
                            player = (player == "Red"? "Blue" : "Red");
                        }
                        return true;
                    }
                }
            }
        }    
    //Returns false if no S_S pattern is found.
        return false;
    }

    @Override
    public void computerMove() {
        System.out.println(player + " computer moving.");
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if(board[i][j].getText() == " ") {
                    if (oValidate(i,j,false)) {
                        System.out.println("Computer placing O");
                        sosCheck(board[i][j],i,j);
                        return;
                    }
                    else if (sValidate(i,j,false)) {
                        System.out.println("Computer placing S");
                        sosCheck(board[i][j],i,j);
                        return;
                    }
                }
            }
        }

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (board[j][i].getText() == " ") {
                    board[j][i].setText("S");
                    System.out.println("Computer placing S");
                    moves++;
                    recordMove(player,i,j);
                    nextTurn();
                    return;
                }
            }
        }
    }
}