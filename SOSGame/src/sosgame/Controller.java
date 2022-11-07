package sosgame;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;

public class Controller {

    @FXML
    private AnchorPane anchor;
    @FXML
    private AnchorPane gridAnchor;
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
    @FXML
    private RadioButton simpleButton;

    private SimpleGame sGame;
    private GeneralGame gGame;

    private int size;

    public void initialize() {
        System.out.println("Initializing!");
        menuNewGame.setOnAction(e -> {
            System.out.println("Starting new game.");
            clearGrid();
            validateSize();
            if (simpleButton.isSelected()) {
            sGame = new SimpleGame(size, redS, blueS, turnField, boardGrid, gridAnchor);
            sGame.boardGen();
            } else {
            gGame = new GeneralGame(size, redS, blueS, turnField, boardGrid, gridAnchor);
            gGame.boardGen();
            }
        });
        clearGrid();
        validateSize();
        sGame = new SimpleGame(5,redS, blueS, turnField, boardGrid, gridAnchor);
        sGame.boardGen();
    }

    public void validateSize() {
        String newSize = sizeField.getText();
        if (!(newSize.isEmpty())) {
            try {
                int toInt = Integer.parseInt(newSize);
                if (toInt > 2) {
                    size = toInt;
                } else {
                    System.out.println("Invalid size input. Given size too small.");
                    size = 5;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid size input. String cannot convert to integer.");
                size = 5;
            }
        } else {
            System.out.println("Invalid size input. Field empty.");
            size = 5;
        }
    }

    public void clearGrid(){
        GridPane tempPane = boardGrid;
        gridAnchor.getChildren().clear();
        gridAnchor.getChildren().add(tempPane);
        boardGrid.getChildren().clear();
        boardGrid.getColumnConstraints().clear();
        boardGrid.getRowConstraints().clear();
    }
}