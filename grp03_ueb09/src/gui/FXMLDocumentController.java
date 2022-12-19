package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import logic.Minesweeper;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * The FXMLDocumentController implements Initializable. Add the appropriate code to initialize(..) so that can
 * immediatly start playing once the program is running.
 * In the FXMLDocumentController the following have to exist:
 * 1. a GridPane and a Label, which have been created in the SceneBuilder through assigning a fx:id
 * 2. as a constant the hit probability, since in a later version this specification should be able to be changed via
 * the UI (as an integer value)
 * 3. an attribute for storing the current instance of the game
 * as methods at least initialize(..) see above
 * <p>
 * 4. initImages(GridPane grdPn) returns a two-dimensional array of ImageViews, which are all
 * children of gridPane and where each is assigned to a cell of the gridPane. The size of an ImageView correlates to a
 * cell of the gridPane and changes according to the change in size of the cell (see tips).
 * <p>
 * 5. startNewGame starts a new game and initialises everything accordingly.
 * <p>
 * 6.onMouseClickedMineField(..) is the event, which is triggered by a click on the GridPane.
 * Here, you must determine which mouse button was clicked on which cell
 * (see tips) and then react accordingly.
 * <p>
 * 7. onClickMnItmNew(..) is the event that is triggered when the menu item New
 * is selected. A new game is started.
 * <p>
 * 8. onClickMnItmClose(..) is the event that is triggered when the menu item Close is selected.
 * Here the program is terminated.
 *
 * @author chien-hsun, husnain
 */
public class FXMLDocumentController implements Initializable {

    /*
    used to increase and decrease the difficulty of the game
     */
    private static final int HIT_PROBABILITY = 4;
    @FXML
    private GridPane grdPnMineField;
    @FXML
    private Label outputLabel;
    private Minesweeper game;  //an attribute for storing the current instance of the game
    //TODO: done No other class attributes allowed then described in the task.

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //TODO: done Do this in start game, then you don't need the class attributes for gui and Image[][].
        startNewGame();
    }

    /**
     * Creates an array of imageviews corresponding to the gridPane. Each imageView becomes a child of the gridPane and
     * fills a cell. For proper resizing it is binded to the gridPanes width and height.
     *
     * @return an array of imageviews added to the gridPane
     */
    private ImageView[][] initImages(GridPane grdPn) {
        int colcount = grdPn.getColumnConstraints().size();
        int rowcount = grdPn.getRowConstraints().size();
        ImageView[][] imageViews = new ImageView[colcount][rowcount];
        // bind each Imageview to a cell of the gridpane
        int cellWidth = (int) grdPn.getWidth() / colcount;
        int cellHeight = (int) grdPn.getHeight() / rowcount;
        for (int x = 0; x < colcount; x++) {
            for (int y = 0; y < rowcount; y++) {
                //creates an empty imageview
                imageViews[x][y] = new ImageView();
                //image has to fit a cell and mustn't preserve ratio
                imageViews[x][y].setFitWidth(cellWidth);
                imageViews[x][y].setFitHeight(cellHeight);
                imageViews[x][y].setPreserveRatio(false);
                imageViews[x][y].setSmooth(true);
                //assign the correct indicees for this imageview
                GridPane.setConstraints(imageViews[x][y], x, y);
                //add the imageview to the cell
                grdPn.add(imageViews[x][y], x, y);
                //the image shall resize when the cell resizes
                imageViews[x][y].fitWidthProperty().bind(grdPn.widthProperty().divide(colcount));
                imageViews[x][y].fitHeightProperty().bind(grdPn.heightProperty().divide(rowcount));
            }
        }
        return imageViews;
    }

    /**
     * play whole game
     * create a common meth od that is used in both initialize and here to start the whole game
     * startNewGame starts a new game and initialises everything accordingly
     */
    @FXML
    private void startNewGame() {
        grdPnMineField.getChildren().clear();
        grdPnMineField.setPadding(Insets.EMPTY);
        ImageView[][] imageViews= initImages(grdPnMineField);
        JavaFXGUI gui = new JavaFXGUI(imageViews, outputLabel);
        game = new Minesweeper(gui, grdPnMineField.getColumnCount(), grdPnMineField.getRowCount(), HIT_PROBABILITY);

    }


    /**
     * @param actionEvent
     */
    @FXML
    private void onClickMnItmNew(ActionEvent actionEvent) {
        startNewGame();
    }

    /**
     * End the game when clicking Menu "Game/Close"
     * use a known node (here the gridPane because the menuitem isn't a node)
     * and get its stage
     *
     * @param actionEvent event responsible for calling this method (not used here).
     */
    @FXML
    private void onClickMnItmClose(ActionEvent actionEvent) {
        Stage stage = (Stage) grdPnMineField.getScene().getWindow();
        stage.close();
    }

    /**
     * reacts on clicking the gridPane.
     * onMouseClickedMineField(..) is the event, which is triggered by a click on the GridPane.
     * Here, you must determine which mouse button was clicked on which cell
     * (see tips) and then react accordingly.
     * <p>
     *
     * @param mouseEvent event responsible for calling this method
     */
    @FXML
    private void onMouseClickedMineField(MouseEvent mouseEvent) {
        int x = -1;
        int y = -1;
        boolean leftClicked = mouseEvent.getButton() == MouseButton.PRIMARY;
        boolean rightClicked = mouseEvent.getButton() == MouseButton.SECONDARY;
        //determine the imageview of the grid that contains the coordinates of the mouseclick
        //to determine the board-coordinates
        for (Node node : grdPnMineField.getChildren()) {
            if (node instanceof ImageView) {
                if (node.getBoundsInParent().contains(mouseEvent.getX(), mouseEvent.getY())) {
                    //to use following methods the columnIndex and rowIndex
                    //must have been set when adding the imageview to the grid
                    x = GridPane.getColumnIndex(node);
                    y = GridPane.getRowIndex(node);
                }
            }
        }
        assert (x >= 0 && y >= 0) : "dem Klick ist keine Koordinate zuzuordnen";
        System.out.println("clicked on " + x + " " + y);
        if (leftClicked) {
            game.uncover(x, y);
        } else if (rightClicked) {
            //TODO: done  Rightclick after the game has ended should not show the "game ended" window again.
            //react on rightclick
            game.suspectBomb(x, y);
        }
    }


}

