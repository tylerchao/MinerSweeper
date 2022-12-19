package gui;

import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import logic.GUIConnector;
import javafx.scene.image.ImageView;


/**
 * As attribute it needs to have a two-dimensional array of ImageViews and a label for the status.
 * These are passed in a constructor.
 * <p>
 * To avoid continuously loading the same images, store all the necessary images in their respective constants,
 * e.g. private static final Image IMG_BOMB = new Image(..);.
 * <p>
 * Additional package-private or protected methods may be added.
 *
 * @author chien-hsun, husnain
 */
public class JavaFXGUI implements GUIConnector {

    private ImageView[][] imageViews;
    private Label status;   // bottom field
    private static final String PATH = "gui/img/";
    private static final Image IMG_BOMB = new Image(PATH + "bomb_explodes.png");
    private static final Image IMG_COVERED = new Image(PATH + "covered.png");
    private static final Image IMG_1 = new Image(PATH + "img1.png");
    private static final Image IMG_2 = new Image(PATH + "img2.png");
    private static final Image IMG_3 = new Image(PATH + "img3.png");
    private static final Image IMG_4 = new Image(PATH + "img4.png");
    private static final Image IMG_5 = new Image(PATH + "img5.png");
    private static final Image IMG_6 = new Image(PATH + "img6.png");
    private static final Image IMG_7 = new Image(PATH + "img7.png");
    private static final Image IMG_8 = new Image(PATH + "img8.png");
    private static final Image IMG_Empty = new Image(PATH + "imgEmpty.png");
    private static final Image IMG_suspected = new Image(PATH + "marked_suspected.png");

    /**
     * constructor
     *
     * @param imageViews the array of image views
     * @param status     output label
     */
    public JavaFXGUI(ImageView[][] imageViews, Label status) {
        this.imageViews = imageViews;
        this.status = status;
    }

    /**
     * Covers the given cell again, so it is neither suspected nor can the user see if the cell contains a bomb or how
     * many bombs are neighboured.
     * mark the cell as in normal starting condition
     *
     * @param x x-coordinate of the position
     * @param y y-coordinate of the position
     */
    @Override
    public void coverCell(int x, int y) {
        imageViews[x][y].setImage(IMG_COVERED);
    }

    /**
     * Marks cell at the specified coordinate as suspected.
     * set the flag image on this position on the gui to make it suspected
     *
     * @param x x-coordinate of the position
     * @param y y-coordinate of the position
     */
    @Override
    public void markCellAsSuspected(int x, int y) {
        imageViews[x][y].setImage(IMG_suspected);
    }


    /**
     * Reveals what is hidden at the given position
     * if it contains bomb show me the whole field and finish the game and you will be loss and call the
     * method gameHasEnded to stop the game
     * if it doesn't contain a bomb then give me with how many neighbours this position is sharing his boundry
     *
     * @param x                 x-coordinate of the position
     * @param y                 y-coordinate of the position
     * @param isBomb            if a bomb is placed at the cell
     * @param neighbouringBombs how many neighbouring bombs this cell has
     */
    @Override
    public void uncoverCell(int x, int y, boolean isBomb, int neighbouringBombs) {
        if (isBomb) {
            imageViews[x][y].setImage(IMG_BOMB);
        } else {
            switch (neighbouringBombs) {
                case 1:
                    imageViews[x][y].setImage(IMG_1);
                    break;
                case 2:
                    imageViews[x][y].setImage(IMG_2);
                    break;
                case 3:
                    imageViews[x][y].setImage(IMG_3);
                    break;
                case 4:
                    imageViews[x][y].setImage(IMG_4);
                    break;
                case 5:
                    imageViews[x][y].setImage(IMG_5);
                    break;
                case 6:
                    imageViews[x][y].setImage(IMG_6);
                    break;
                case 7:
                    imageViews[x][y].setImage(IMG_7);
                    break;
                case 8:
                    imageViews[x][y].setImage(IMG_8);
                    break;
                default:
                    imageViews[x][y].setImage(IMG_Empty);
                    break;
            }
        }
    }

    /**
     * Called once the game has ended.
     *
     * @param won if the user won the game
     */
    @Override
    public void gameHasEnded(boolean won) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Game has ended");
        if (won) {
            alert.setHeaderText("You Won");
        } else {
            alert.setHeaderText("You lose");
        }
        alert.setContentText("If you want to you can start a new game");
        alert.showAndWait();
    }

    /**
     * Displays the number of bombs placed for a new game on the screen.
     * call the label from the gui and set this noOfBombs parameter to show it on gui
     *
     * @param noOfBombs number of Bombs placed
     */
    @Override
    public void displayNoOfBombs(int noOfBombs) {
        status.setText(" Number of bombs placed on the field :  " + noOfBombs);
    }


}
