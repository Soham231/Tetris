package cs1302.arcade;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Tetris {
    
    Color color;
    private String name;
    Rectangle one;
    Rectangle two;
    Rectangle three;
    Rectangle four;
    public int position = 1;


    public Tetris(Rectangle one, Rectangle two, Rectangle three, Rectangle four) {
        this.one = one;
        this.two = two;
        this.three = three;
        this.four = four;
    } //position

    public Tetris(Rectangle one, Rectangle two, Rectangle three, Rectangle four, String name) {
        this.one = one;
        this.two = two;
        this.three = three;
        this.four = four;
        this.name = name;

        switch (name) {
            case "i": 
                color = Color.SEAGREEN;
                break;
            case "z":
                color = Color.PALEGOLDENROD;
                break;
            case "t":
                color = Color.MEDIUMVIOLETRED;
                break;
            case "s":
                color = Color.LAWNGREEN;
                break;
            case "o":
                color = Color.DEEPSKYBLUE;
                break;
            case "l":
                color = Color.LIGHTPINK;
                break;
            case "j":
                color = Color.ROSYBROWN;
                break;
        } //switch
        this.one.setFill(color);
        this.two.setFill(color);
        this.three.setFill(color);
        this.four.setFill(color);
    } //position

    public void changePosition() {
        if (position != 4) {
            position++;
        } else {
            position = 1;
        } //if
    } //changeposition

    public String getName() {
        return name;
    } //getName

} //position
