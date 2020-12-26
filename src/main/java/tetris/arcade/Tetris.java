package tetris.arcade;

import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;

/** Represents a Tetris block. */
public class Tetris {
    Color shade;
    Rectangle one;
    Rectangle two;
    Rectangle three;
    Rectangle four;
    int variable = 1;
    private String label;

    /**
     * Represents a tetris block.
     *
     * @param one The specified rectangle block
     * @param two The specified rectangle block
     * @param three The specified rectangle block
     * @param four The specified rectangle block
     * @param s the name of the block
     */
    public Tetris(Rectangle one, Rectangle two, Rectangle three, Rectangle four, String s) {
        this.one = one;
        this.two = two;
        this.three = three;
        this.four = four;
        this.label = s;

        //Assigns colors to all cases of the tetris blocks
        switch (s) {
        case "h":
            shade = Color.LIGHTYELLOW;
            break;
        case "g":
            shade = Color.DARKCYAN;
            break;
        case "o":
            shade = Color.PALEVIOLETRED; //
            break;
        case "k":
            shade = Color.DARKORANGE;//
            break;
        case "c":
            shade = Color.DEEPSKYBLUE; //
            break;
        case "z":
            shade = Color.PURPLE;
            break;
        case "d":
            shade = Color.SADDLEBROWN;
            break;
        } //switch
        this.one.setFill(shade);
        this.two.setFill(shade);
        this.three.setFill(shade);
        this.four.setFill(shade);
    } //Tetris constructor


    /**
     * Returns the name of the block.
     *
     * @return label of the block
     */
    public String getName() {
        return label;
    } //getName

    /**
     * Changes the orientation of the block, depending on user keypress.
     */
    public void changeForm() {
        if (variable != 4) {
            variable++;
        } else {
            variable = 1;
        } //if
    } //changeForm
} //Tetris
