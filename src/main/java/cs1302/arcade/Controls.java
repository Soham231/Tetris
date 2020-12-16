package cs1302.arcade;

import javafx.scene.shape.Rectangle;
import org.w3c.dom.css.Rect;

/** Specifies the controls for the rectangle blocks. */
public class Controls {
    public static int shift = ArcadeApp.SHIFT;
    public static int[][] grid = ArcadeApp.grid;
    public static int xtotal = ArcadeApp.xtotal;
    public static final int S = ArcadeApp.S;
    public static int block = (int) (Math.random() * 100);
    static String label;

    /**
     * Assigns what the function will do once the right arrow is clicked.
     * @param tet the tetris object
     */
    public static void turnRight(Tetris tet) {
        if (tet.one.getX() + shift <= xtotal - S
                && tet.two.getX() + shift <= xtotal - S
                && tet.three.getX() + shift <= xtotal - S
                && tet.four.getX() + shift <= xtotal - S) {
            int s1 = grid[((int) tet.one.getX() / S) + 1]
                    [((int) tet.one.getY() / S)];
            int s2 = grid[((int) tet.two.getX() / S) + 1]
                    [((int) tet.two.getY() / S)];
            int s3 = grid[((int) tet.three.getX() / S) + 1]
                    [((int) tet.three.getY() / S)];
            int s4 = grid[((int) tet.four.getX() / S) + 1]
                    [((int) tet.four.getY() / S)];
            if (s1 == 0 && s1 == s2 && s2 == s3 && s3 == s4) {
                tet.one.setX(tet.one.getX() + shift);
                tet.two.setX(tet.two.getX() + shift);
                tet.three.setX(tet.three.getX() + shift);
                tet.four.setX(tet.four.getX() + shift);
            } //if
        } //if
    } //turnRight

    /**
     * Assigns what the function will do once the right arrow is clicked.
     * @param tet the tetris object
     */
    public static void turnLeft(Tetris tet) {
        if (tet.one.getX() - shift >= 0
                && tet.two.getX() - shift >= 0
                && tet.three.getX() - shift >= 0
                && tet.four.getX() - shift >= 0) {
            int movea = grid[((int) tet.one.getX() / S) - 1]
                    [((int) tet.one.getY() / S)];
            int moveb = grid[((int) tet.two.getX() / S) - 1]
                    [((int) tet.two.getY() / S)];
            int movec = grid[((int) tet.three.getX() / S) - 1]
                    [((int) tet.three.getY() / S)];
            int moved = grid[((int) tet.four.getX() / S) - 1]
                    [((int) tet.four.getY() / S)];
            if (movea == 0 && movea == moveb && moveb == movec && movec == moved) {
                tet.one.setX(tet.one.getX() - shift);
                tet.two.setX(tet.two.getX() - shift);
                tet.three.setX(tet.three.getX() - shift);
                tet.four.setX(tet.four.getX() - shift);
            } //if
        } //if
    } //turnLeft

    /** Creates the actual tetris blocks in their respective shapes.
     * @return the tetris block
     */
    public static Tetris createShape() {
        int block = (int) (Math.random() * 100);
        Rectangle one = new Rectangle(S - 1, S - 1), two = new Rectangle(S - 1, S - 1),
                three = new Rectangle(S - 1, S - 1), four = new Rectangle(S - 1, S - 1);
        if (block < 15) {
            one.setX(xtotal / 2 - S);
            two.setX(xtotal / 2 - S);
            two.setY(S);
            three.setX(xtotal / 2);
            three.setY(S);
            four.setX(xtotal / 2 + S);
            four.setY(S);
            label = "h";
        } else if (block < 30) {
            one.setX(xtotal / 2 + S);
            two.setX(xtotal / 2 - S);
            two.setY(S);
            three.setX(xtotal / 2);
            three.setY(S);
            four.setX(xtotal / 2 + S);
            four.setY(S);
            label = "g";
        } else if (block < 45) {
            one.setX(xtotal / 2 - S);
            two.setX(xtotal / 2);
            three.setX(xtotal / 2 - S);
            three.setY(S);
            four.setX(xtotal / 2);
            four.setY(S);
            label = "o";
        } else if (block < 60) {
            one.setX(xtotal / 2 + S);
            two.setX(xtotal / 2);
            three.setX(xtotal / 2);
            three.setY(S);
            four.setX(xtotal / 2 - S);
            four.setY(S);
            label = "k";
        } else if (block < 75) {
            one.setX(xtotal / 2 - S);
            two.setX(xtotal / 2);
            three.setX(xtotal / 2);
            three.setY(S);
            four.setX(xtotal / 2 + S);
            label = "c";
        } else if (block < 90) {
            one.setX(xtotal / 2 + S);
            two.setX(xtotal / 2);
            three.setX(xtotal / 2 + S);
            three.setY(S);
            four.setX(xtotal / 2 + S + S);
            four.setY(S);
            label = "z";
        } else {
            createLastShape(one, two, three, four);
            label = "d";
        } //if
        return new Tetris(one, two, three, four, label);
    } //createShape

    /** Creates the last shape to the game.
     *
     * @param r1 the first rectangle object
     * @param r2 the second rectangle object
     * @param r3 the third rectangle object
     * @param r4 the fourth rectangle object
     * */
    private static void createLastShape(Rectangle r1, Rectangle r2, Rectangle r3, Rectangle r4) {
        r1.setX(xtotal / 2 - S - S);
        r2.setX(xtotal / 2 - S);
        r3.setX(xtotal / 2);
        r4.setX(xtotal / 2 + S);
    } //createLastShape

} //controls