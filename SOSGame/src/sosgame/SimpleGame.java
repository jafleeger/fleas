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


public class SimpleGame{
    private int size;
    private GridPane boardGrid;
    private Label turnField;
    private RadioButton redS;
    private RadioButton blueS;
    private AnchorPane gridAnchor;

    private Button[][] board;
    private String player;
    private int moves;

    public SimpleGame(int s, RadioButton rS, RadioButton bS, Label tF, GridPane bG, AnchorPane gA){

        System.out.println("Inputted size is "+s);
        size = s;
        player = "Red";
        boardGrid = bG;
        turnField = tF;
        redS = rS;
        blueS = bS;
        gridAnchor = gA;
        moves = 0;

    }

    public SimpleGame() {
        size = 5;
    }

    public void boardGen() {
        System.out.println("Beginning board generation for SIMPLE Game");
        board = new Button[size][size];

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
    System.out.println("Board generation for SIMPLE Game complete.");
    }

    public void setConditions(Button btn, int row, int col) {
         //Set onClick methods for grid buttons.               
         btn.setOnAction(e -> {
            if (btn.getText().equals(" ")) {
                if (player == "Red") {
                    if (redS.isSelected()) {
                        btn.setText("S");
                    } else {
                        btn.setText("O");
                    }
                    player = "Blue";
                    moves++;
                } else if (player == "Blue"){
                    if (blueS.isSelected()) {
                        btn.setText("S");
                    } else {
                        btn.setText("O");
                    }
                    player = "Red";
                    moves++;
                } else {
                    return;
                }
                turnField.setText(player);
                sosCheck(btn,row,col);
                if(moves >= size * size) {
                    winCheck();
                }
            }
        });
    }

    public void sosCheck(Button btn, int row, int col) {
        if(btn.getText() == "S") {
            for (int c = -1; c <= 1; c++) {
                for (int r = -1; r <= 1; r++) {
                    if ( row + r + r >= 0 && row + r + r < size) {
                        if (col + c + c >=0 && col + c + c < size) {
                            if (board[row+r][col+c].getText() != btn.getText() && board[row+r][col+c].getText() != " ") {
                                if (board[row+r+r][col+c+c].getText() == btn.getText()) {
                                    String lastPlayer = (player == "Red"? "Blue" : "Red");
                                    drawLine(btn, board[row+r+r][col+c+c],lastPlayer);
                                }
                            }
                        }
                    }
                }
            }
        } else {
            for (int c = -1; c < 1; c++) {
                for (int r = -1; r < 1; r++) {
                    if ( row + r >= 0 && row + r < size) {
                        if (col + c >=0 && col + c < size) {
                            if (board[row+r][col+c].getText() != btn.getText() && board[row+r][col+c].getText() != " "
                                && board[row-r][col-c].getText() != btn.getText() && board[row-r][col-c].getText() != " ") {
                                String lastPlayer = player == "Red"? "Blue" : "Red";
                                drawLine(board[row-r][col-c], board[row+r][col+c],lastPlayer);
                            }
                        }
                    }
                }
            }
        }
    }

    public void winCheck() {
        if (moves >= size * size) {
            turnField.setText("No One Wins!");
            player = "none";
        } else if (player == "Blue") {
            turnField.setText(" Red Wins!");
            player = "none";
        } else {
            turnField.setText("Blue Wins!");
            player = "none";
        }
        return;
    }

    public void drawLine(Button start, Button end, String color) {
        Line line = new Line();
        double posAdjust =  start.getWidth() / 2;
        line.setStartX(start.getLayoutX() + posAdjust);
        line.setStartY(start.getLayoutY() + posAdjust);
        line.setEndX(end.getLayoutX() + posAdjust);
        line.setEndY(end.getLayoutY() + posAdjust);

        if (color == "Red") {
            line.setStroke(Color.RED);
        } else {
            line.setStroke(Color.BLUE);
        }
        gridAnchor.getChildren().add(line);
        winCheck();
        return;
    }
}