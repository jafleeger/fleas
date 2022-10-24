package sosgame;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Button;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.RowConstraints;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;

public class Controller {

    @FXML
    private AnchorPane anchor;
    @FXML
    private GridPane boardGrid;
    @FXML
    private Label turnField;
    @FXML
    private RadioButton redS;
    @FXML
    private RadioButton blueS;
    @FXML
    private MenuItem menuNewGame;
    @FXML
    private TextField sizeField;

    private Button[][] board;
    private String player = "red";

    public void initialize() {
        System.out.println("Initializing!");
        menuNewGame.setOnAction(e -> {
            newGame(Integer.parseInt(sizeField.getText()));
        });
        newGame(5);
    }

    public void newGame(int inSize) {
        int size = inSize;
        if (size <= 2) {
            size = 3;
        }
        System.out.println("Creating new game!");
        board = new Button[size][size];
        boardGrid.getChildren().clear();
        boardGrid.getColumnConstraints().clear();
        boardGrid.getRowConstraints().clear();
        
        System.out.println("Creating board!");
        for (int col = 0; col < size; col++){
            ColumnConstraints c = new ColumnConstraints();
            c.setPercentWidth(1/size);
            boardGrid.getColumnConstraints().add(c);
            for (int row = 0; row < size; row++){
                RowConstraints r = new RowConstraints();
                r.setPercentHeight(1/size);
                boardGrid.getRowConstraints().add(r);

                Button btn = new Button(" ");
                btn.setPrefHeight(50);
                btn.setPrefWidth(50);
                boardGrid.add(btn, col, row);
                board[row][col] = btn;
                
                btn.setOnAction(e -> {
                    if (btn.getText().equals(" ")) {
                        if (player == "red") {
                            if (redS.isSelected()) {
                                btn.setText("S");
                            } else {
                                btn.setText("O");
                            }
                            player = "blue";
                        } else {
                            if (blueS.isSelected()) {
                                btn.setText("S");
                            } else {
                                btn.setText("O");
                            }
                            player = "red";
                        }
                        turnField.setText(player);
                    }
                });
            }
        }
        System.out.println("Board complete!");
    }
}