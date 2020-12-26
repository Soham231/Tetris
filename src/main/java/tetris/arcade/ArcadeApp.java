package tetris.arcade;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Application;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.Scene;

/** The main code for the CS1302 ArcadeApp Project. */
public class ArcadeApp extends Application {
    public static final int SHIFT = 25;
    public static final int S = 25;
    public ArrayList<Node> rex = new ArrayList<Node>();
    public ArrayList<Integer> draw = new ArrayList<Integer>();
    public ArrayList<Node> newRex = new ArrayList<Node>();
    public static int xtotal = S * 12;
    public static int ytotal = S * 24;
    public BackgroundFill bf2 = new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY);
    private static Pane mainPane = new Pane();
    private static Tetris tetObj;
    public Stage stage;
    public Button button;
    private static Scene scene = new Scene(mainPane, xtotal, ytotal + 30);
    public static int[][] grid = new int[xtotal / S][ytotal / S];
    private static int sky = 0;
    private static boolean status = true;
    private static Tetris tetFunction = Controls.createShape();
    public int full = 0;
    private static int linesNo;
    public int period = 300;

    /**
     * Represents the main menu for the Arcade.
     *
     * @return a scene
     */
    public Scene mainMenu() {
        button = new Button("Start Game");
        Button controls = new Button("Controls");
        ImageView iv = new ImageView(new Image("file:src/main/resources/Tetris_logo.png"));
        iv.setFitWidth(305);
        iv.setTranslateX(80);
        iv.setTranslateY(0);
        iv.setPreserveRatio(true);
        button.setTranslateX(-115);
        button.setTranslateY(230);
        controls.setTranslateX(-185);
        controls.setTranslateY(270);
        button.setOnAction(e -> stage.setScene(scene));
        controls.setOnAction(e -> controls());

        HBox hbox = new HBox(iv, button, controls);
        VBox vbox = new VBox(hbox);
        BackgroundFill bf = new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY);
        vbox.setBackground(new Background(bf));
        Scene scene1 = new Scene(vbox, 460, 310);
        return scene1;
    } //mainMenu

    /**
     * The start method to run the initial program and set the scene(s).
     *
     * @param stage1 The stage
     */
    @Override
    public void start(Stage stage1) throws Exception {
        for (int[] element : grid) {
            Arrays.fill(element, 0);
        } //for
        //sets the layout for the game
        Line line = new Line(0, ytotal, xtotal + 150, ytotal);
        line.setStyle("-fx-stroke: white;");
        Text levelNum = new Text("Level: ");
        levelNum.setStyle("-fx-font: 25 ubuntu;");
        levelNum.setY(620);
        levelNum.setX(0);
        levelNum.setFill(Color.RED);
        mainPane.getChildren().addAll(line, levelNum);

        Tetris num = tetFunction;
        mainPane.getChildren().addAll(num.one, num.two, num.three, num.four);
        mainPane.setBackground(new Background(bf2));
        shiftWhenKeyPress(num);
        tetObj = num;
        tetFunction = Controls.createShape();
        stage1.setScene(mainMenu());
        scene.setFill(Color.BLACK);
        button.setOnAction(e -> stage1.setScene(scene));
        stage1.setTitle("Arcade!");
        stage1.show();

        Timer drop = new Timer();
        TimerTask timerTask = new TimerTask() {
            public void run() {
                Platform.runLater(new Runnable() {
                    public void run() {
                        if (tetObj.one.getY() == 0 || tetObj.two.getY() == 0
                                || tetObj.three.getY() == 0 || tetObj.four.getY() == 0) {
                            sky++;
                        } else {
                            sky = 0;
                        } //if
                        if (sky == 2) {
                            Text end = new Text("You Lost!");
                            end.setFill(Color.GHOSTWHITE);
                            end.setStyle("-fx-font: 70 Times;");
                            end.setY(250);
                            end.setX(10);
                            mainPane.getChildren().add(end);
                            status = false;
                        } //if
                        if (status) {
                            moveShapeDown(tetObj);
                            levelNum.setText("Level: " + Integer.toString(linesNo));
                            if (linesNo > 0) {
                                levelNum.setText("Level: " + Integer.toString(linesNo));
                                period -= 50;
                            } //if
                        } //if
                    } //run
                }); //runLater
            } //run
        }; //task
        drop.schedule(timerTask, 0, period -= 50);
    } //start

    /**
     * The control menu to explain the control for the game.
     */
    private void controls() {
        FlowPane flowpane = new FlowPane();
        Text info = new Text("RIGHT and LEFT Arrow Keys to Move\n UP Arrow Key to rotate shape" +
                "\n DOWN Arrow key to speed up shape on its way down");
        flowpane.getChildren().add(info);
        Stage stage2 = new Stage();
        Scene scene2 = new Scene(flowpane);
        stage2.setScene(scene2);
        stage2.setTitle("Controls");
        stage2.sizeToScene();
        stage2.showAndWait();
    } //controls

    /**
     * Sets actions for the keypress.
     *
     * @param tetris Specified Tetris function
     */
    private void shiftWhenKeyPress(Tetris tetris) {
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case RIGHT:
                        Controls.turnRight(tetris);
                        break;
                    case DOWN:
                        moveShapeDown(tetris);
                        break;
                    case LEFT:
                        Controls.turnLeft(tetris);
                        break;
                    case UP:
                        turn(tetris);
                        break;
                } //switch
            } //handle
        }); //scene
    } //shiftWhenKeyPress

    /**
     * The different turn orientations for each different block.
     *
     * @param tet The specified tetris block to turn
     */
    private void turn(Tetris tet) {
        switch (tet.getName()) {
        case "h":
            turnCaseH(tet);
            break;
        case "g":
            turnCaseG(tet);
            break;
        case "o":
            break;
        case "k":
            turnCaseK(tet);
            break;
        case "c":
            turnCaseC(tet);
            break;
        case "z":
            turnCaseZ(tet);
            break;
        case "d":
            turnCaseD(tet);
            break;
        } //switch
    } //turn

    /**
     * Turning case for block labeled I.
     *
     * @param tet The specified tetris block
     */
    private void turnCaseD(Tetris tet) {
        int posNum = tet.variable;
        Rectangle first = tet.one;
        Rectangle sec = tet.two;
        Rectangle fourth = tet.four;
        if (posNum == 1 && checkR(first, 2, 2) && checkR(sec, 1, 1) && checkR(fourth, -1, -1)) {
            shiftUp(tet.one);
            shiftUp(tet.one);
            shiftRight(tet.one);
            shiftRight(tet.one);
            shiftUp(tet.two);
            shiftRight(tet.two);
            shiftDown(tet.four);
            shiftLeft(tet.four);
            tet.changeForm();
        } //if
        if (posNum == 2 && checkR(first, -2, -2) && checkR(sec, -1, -1) && checkR(fourth, 1, 1)) {
            shiftDown(tet.one);
            shiftDown(tet.one);
            shiftLeft(tet.one);
            shiftLeft(tet.one);
            shiftDown(tet.two);
            shiftLeft(tet.two);
            shiftUp(tet.four);
            shiftRight(tet.four);
            tet.changeForm();
        } //if
        if (posNum == 3 && checkR(first, 2, 2) && checkR(sec, 1, 1) && checkR(fourth, -1, -1)) {
            shiftUp(tet.one);
            shiftUp(tet.one);
            shiftRight(tet.one);
            shiftRight(tet.one);
            shiftUp(tet.two);
            shiftRight(tet.two);
            shiftDown(tet.four);
            shiftLeft(tet.four);
            tet.changeForm();
        } //if
        if (posNum == 4 && checkR(first, -2, -2) && checkR(sec, -1, -1) && checkR(fourth, 1, 1)) {
            shiftDown(tet.one);
            shiftDown(tet.one);
            shiftLeft(tet.one);
            shiftLeft(tet.one);
            shiftDown(tet.two);
            shiftLeft(tet.two);
            shiftUp(tet.four);
            shiftRight(tet.four);
            tet.changeForm();
        } //if
    } //turnCaseI

    /**
     * Turning case for block labeled J.
     *
     * @param tet The specified tetris block
     */
    private void turnCaseH(Tetris tet) {
        int posNum = tet.variable;
        Rectangle first = tet.one;
        Rectangle third = tet.three;
        Rectangle fourth = tet.four;
        if (posNum == 1 && checkR(first, 1, -1) && checkR(third, -1, -1)
            && checkR(fourth, -2, -2)) {
            shiftRight(tet.one);
            shiftDown(tet.one);
            shiftDown(tet.three);
            shiftLeft(tet.three);
            shiftDown(tet.four);
            shiftDown(tet.four);
            shiftLeft(tet.four);
            shiftLeft(tet.four);
            tet.changeForm();
            //break;
        } //if
        if (posNum == 2 && checkR(first, -1, -1) && checkR(third, -1, 1) && checkR(fourth, -2, 2)) {
            shiftDown(tet.one);
            shiftLeft(tet.one);
            shiftLeft(tet.three);
            shiftUp(tet.three);
            shiftLeft(tet.four);
            shiftLeft(tet.four);
            shiftUp(tet.four);
            shiftUp(tet.four);
            tet.changeForm();
            //break;
        } //if
        if (posNum == 3 && checkR(first, -1, 1) && checkR(third, 1, 1) && checkR(fourth, 2, 2)) {
            shiftLeft(tet.one);
            shiftUp(tet.one);
            shiftUp(tet.three);
            shiftRight(tet.three);
            shiftUp(tet.four);
            shiftUp(tet.four);
            shiftRight(tet.four);
            shiftRight(tet.four);
            tet.changeForm();
            //break;
        } //if
        if (posNum == 4 && checkR(first, 1, 1) && checkR(third, 1, -1) && checkR(fourth, 2, -2)) {
            shiftUp(tet.one);
            shiftRight(tet.one);
            shiftRight(tet.three);
            shiftDown(tet.three);
            shiftRight(tet.four);
            shiftRight(tet.four);
            shiftDown(tet.four);
            shiftDown(tet.four);
            tet.changeForm();
            //break;
        } //if
        //break;
    } //turnCaseJ

    /**
     * Turning case for block labeled L.
     *
     * @param tet The specified tetris block
     */
    private void turnCaseG(Tetris tet) {
        int posNum = tet.variable;
        Rectangle first = tet.one;
        Rectangle sec = tet.two;
        Rectangle third = tet.three;
        if (posNum == 1 && checkR(first, 1, -1) && checkR(third, 1, 1) && checkR(sec, 2, 2)) {
            shiftRight(tet.one);
            shiftDown(tet.one);
            shiftUp(tet.three);
            shiftRight(tet.three);
            shiftUp(tet.two);
            shiftUp(tet.two);
            shiftRight(tet.two);
            shiftRight(tet.two);
            tet.changeForm();
            //break;
        } //if
        if (posNum == 2 && checkR(first, -1, -1) && checkR(sec, 2, -2) && checkR(third, 1, -1)) {
            shiftDown(tet.one);
            shiftLeft(tet.one);
            shiftRight(tet.two);
            shiftRight(tet.two);
            shiftDown(tet.two);
            shiftDown(tet.two);
            shiftRight(tet.three);
            shiftDown(tet.three);
            tet.changeForm();
            //break;
        } //if
        if (posNum == 3 && checkR(first, -1, 1) && checkR(third, -1, -1) && checkR(sec, -2, -2)) {
            shiftLeft(tet.one);
            shiftUp(tet.one);
            shiftDown(tet.three);
            shiftLeft(tet.three);
            shiftDown(tet.two);
            shiftDown(tet.two);
            shiftLeft(tet.two);
            shiftLeft(tet.two);
            tet.changeForm();
            //break;
        } //if
        if (posNum == 4 && checkR(first, 1, 1) && checkR(sec, -2, 2) && checkR(third, -1, 1)) {
            shiftUp(tet.one);
            shiftRight(tet.one);
            shiftLeft(tet.two);
            shiftLeft(tet.two);
            shiftUp(tet.two);
            shiftUp(tet.two);
            shiftLeft(tet.three);
            shiftUp(tet.three);
            tet.changeForm();
            //break;
        } //if
        //break;
    } //turnCaseJ

    /**
     * Turning case for block labeled S.
     *
     * @param tet The specified tetris block
     */
    private void turnCaseK(Tetris tet) {
        int posNum = tet.variable;
        Rectangle first = tet.one;
        Rectangle third = tet.three;
        Rectangle fourth = tet.four;
        if (posNum == 1 && checkR(first, -1, -1) && checkR(third, -1, 1) && checkR(fourth, 0, 2)) {
            shiftDown(tet.one);
            shiftLeft(tet.one);
            shiftLeft(tet.three);
            shiftUp(tet.three);
            shiftUp(tet.four);
            shiftUp(tet.four);
            tet.changeForm();
            //break;
        } //if
        if (posNum == 2 && checkR(first, 1, 1) && checkR(third, 1, -1) && checkR(fourth, 0, -2)) {
            shiftUp(tet.one);
            shiftRight(tet.one);
            shiftRight(tet.three);
            shiftDown(tet.three);
            shiftDown(tet.four);
            shiftDown(tet.four);
            tet.changeForm();
            //break;
        } //if
        if (posNum == 3 && checkR(first, -1, -1) && checkR(third, -1, 1) && checkR(fourth, 0, 2)) {
            shiftDown(tet.one);
            shiftLeft(tet.one);
            shiftLeft(tet.three);
            shiftUp(tet.three);
            shiftUp(tet.four);
            shiftUp(tet.four);
            tet.changeForm();
            //break;
        } //if
        if (posNum == 4 && checkR(first, 1, 1) && checkR(third, 1, -1) && checkR(fourth, 0, -2)) {
            shiftUp(tet.one);
            shiftRight(tet.one);
            shiftRight(tet.three);
            shiftDown(tet.three);
            shiftDown(tet.four);
            shiftDown(tet.four);
            tet.changeForm();
            //break;
        } //if
    } //turnCaseS

    /**
     * Turning case for block labeled T.
     *
     * @param tet The specified tetris block
     */
    private void turnCaseC(Tetris tet) {
        int posNum = tet.variable;
        Rectangle first = tet.one;
        Rectangle third = tet.three;
        Rectangle fourth = tet.four;
        if (posNum == 1 && checkR(first, 1, 1) && checkR(fourth, -1, -1) && checkR(third, -1, 1)) {
            shiftUp(tet.one);
            shiftRight(tet.one);
            shiftDown(tet.four);
            shiftLeft(tet.four);
            shiftLeft(tet.three);
            shiftUp(tet.three);
            tet.changeForm();
            //break;
        } //if
        if (posNum == 2 && checkR(first, 1, -1) && checkR(fourth, -1, 1) && checkR(third, 1, 1)) {
            shiftRight(tet.one);
            shiftDown(tet.one);
            shiftLeft(tet.four);
            shiftUp(tet.four);
            shiftUp(tet.three);
            shiftRight(tet.three);
            tet.changeForm();
            //break;
        } //if
        if (posNum == 3 && checkR(first, -1, -1) && checkR(fourth, 1, 1) && checkR(third, 1, -1)) {
            shiftDown(tet.one);
            shiftLeft(tet.one);
            shiftUp(tet.four);
            shiftRight(tet.four);
            shiftRight(tet.three);
            shiftDown(tet.three);
            tet.changeForm();
            //break;
        } //if
        if (posNum == 4 && checkR(first, -1, 1) && checkR(fourth, 1, -1) && checkR(third, -1, -1)) {
            shiftLeft(tet.one);
            shiftUp(tet.one);
            shiftRight(tet.four);
            shiftDown(tet.four);
            shiftDown(tet.three);
            shiftLeft(tet.three);
            tet.changeForm();
            //break;
        } //if
    } //turnCaseT

    /**
     * Turning case for block labeled Z.
     *
     * @param tet The specified tetris block
     */
    private void turnCaseZ(Tetris tet) {
        int posNum = tet.variable;
        Rectangle sec = tet.two;
        Rectangle third = tet.three;
        Rectangle fourth = tet.four;
        if (posNum == 1 && checkR(sec, 1, 1) && checkR(third, -1, 1) && checkR(fourth, -2, 0)) {
            shiftUp(tet.two);
            shiftRight(tet.two);
            shiftLeft(tet.three);
            shiftUp(tet.three);
            shiftLeft(tet.four);
            shiftLeft(tet.four);
            tet.changeForm();
        } //if
        if (posNum == 2 && checkR(sec, -1, -1) && checkR(third, 1, -1) && checkR(fourth, 2, 0)) {
            shiftDown(tet.two);
            shiftLeft(tet.two);
            shiftRight(tet.three);
            shiftDown(tet.three);
            shiftRight(tet.four);
            shiftRight(tet.four);
            tet.changeForm();
        } //if
        if (posNum == 3 && checkR(sec, 1, 1) && checkR(third, -1, 1) && checkR(fourth, -2, 0)) {
            shiftUp(tet.two);
            shiftRight(tet.two);
            shiftLeft(tet.three);
            shiftUp(tet.three);
            shiftLeft(tet.four);
            shiftLeft(tet.four);
            tet.changeForm();
        } //if
        if (posNum == 4 && checkR(sec, -1, -1) && checkR(third, 1, -1) && checkR(fourth, 2, 0)) {
            shiftDown(tet.two);
            shiftLeft(tet.two);
            shiftRight(tet.three);
            shiftDown(tet.three);
            shiftRight(tet.four);
            shiftRight(tet.four);
            tet.changeForm();
        } //if
    } //turnCaseZ

    /**
     * Method to delete rows once it has been filled.
     *
     * @param pane The specified pane
     */
    private void deleteRows(Pane pane) {
        //If grid row is full (aka = to 1) then increments full variable
        for (int i = 0; i < grid[0].length; i++) {
            for (int j = 0; j < grid.length; j++) {
                if (grid[j][i] == 1) {
                    full++;
                } //if
            } //for
            if (full == grid.length) {
                draw.add(i);
            } //if
            full = 0;
        } //for
        //Adds each instance of a Rectangle to the list
        if (draw.size() > 0) {
            do {
                for (Node n : pane.getChildren()) {
                    if (n instanceof Rectangle) {
                        rex.add(n);
                    } //if
                } //for
                linesNo++;
                for (Node n : rex) {
                    Rectangle a = (Rectangle) n;
                    if (a.getY() == draw.get(0) * S) {
                        grid[(int) a.getX() / S][(int) a.getY() / S] = 0;
                        pane.getChildren().remove(n);
                    } else {
                        newRex.add(n);
                    } //if
                } //for
                //Adds the new rectangles left over after the removal to a list
                for (Node n : newRex) {
                    Rectangle a = (Rectangle) n;
                    if (a.getY() < draw.get(0) * S) {
                        grid[(int) a.getX() / S][(int) a.getY() / S] = 0;
                        a.setY(a.getY() + S);
                    } //if
                } //for
                //Clears the rest of the lists and adds remaining elements to new list and onto grid
                draw.remove(0);
                rex.clear();
                newRex.clear();
                for (Node node : pane.getChildren()) {
                    if (node instanceof Rectangle) {
                        rex.add(node);
                    } //if
                } //for
                for (Node n : rex) {
                    Rectangle a = (Rectangle) n;
                    try {
                        grid[(int) a.getX() / S][(int) a.getY() / S] = 1;
                    } catch (ArrayIndexOutOfBoundsException e) {
                        e.printStackTrace();
                    } //try
                } //for
                rex.clear();
            } while (draw.size() > 0);
        } //if
    } //deleteRows

    /**
     * Method to move the block down.
     *
     * @param r The specified Rectangle
     */
    private void shiftDown(Rectangle r) {
        if (r.getY() + SHIFT < ytotal) {
            r.setY(r.getY() + SHIFT);
        } //if
    } //shiftDown

    /**
     * Method to move the block to the right.
     *
     * @param r The specified Rectangle
     */
    private void shiftRight(Rectangle r) {
        if (r.getX() + SHIFT <= xtotal - S) {
            r.setX(r.getX() + SHIFT);
        } //if
    } //shiftRight

    /**
     * Method to move the block to the left.
     *
     * @param r The specified Rectangle
     */
    private void shiftLeft(Rectangle r) {
        if (r.getX() - SHIFT >= 0) {
            r.setX(r.getX() - SHIFT);
        } //if
    } //shiftLeft

    /**
     * Method to move the block up.
     *
     * @param r The specified Rectangle
     */
    private void shiftUp(Rectangle r) {
        if (r.getY() - SHIFT > 0) {
            r.setY(r.getY() - SHIFT);
        } //if
    } //shiftUp

    /**
     * Method to check what position each block is in.
     *
     * @param r The specified rectangle
     * @param x the next specified x location
     * @param y the next specified y location
     * @return true if blocks have space around them to rotate, false otherwise
     */
    private boolean checkR(Rectangle r, int x, int y) {
        boolean xb = false;
        boolean yb = false;
        if (x >= 0) {
            xb = r.getX() + x * SHIFT <= xtotal - S;
        } //if
        if (x < 0) {
            xb = r.getX() + x * SHIFT >= 0;
        } //if
        if (y >= 0) {
            yb = r.getY() - y * SHIFT > 0;
        } //if
        if (y < 0) {
            yb = r.getY() + y * SHIFT < ytotal;
        } //if
        return grid[((int) r.getX() / S) + x][((int) r.getY() / S) - y] == 0
                && xb && yb;
    } //checkR

    /**
     * Method to move the block down when down arrow is pressed to speed up process.
     *
     * @param tet The specified Tetris block
     */
    private void moveShapeDown(Tetris tet) {
        if (tet.one.getY() == ytotal - S || tet.two.getY() == ytotal - S
                || tet.three.getY() == ytotal - S
                || tet.four.getY() == ytotal - S || shiftOne(tet) || shiftTwo(tet)
                || shiftThree(tet) || shiftFour(tet)) {
            grid[(int) tet.one.getX() / S][(int) tet.one.getY() / S] = 1;
            grid[(int) tet.two.getX() / S][(int) tet.two.getY() / S] = 1;
            grid[(int) tet.three.getX() / S][(int) tet.three.getY() / S] = 1;
            grid[(int) tet.four.getX() / S][(int) tet.four.getY() / S] = 1;
            deleteRows(mainPane);

            Tetris a = tetFunction;
            tetFunction = Controls.createShape();
            tetObj = a;
            mainPane.getChildren().addAll(a.one, a.two, a.three, a.four);
            shiftWhenKeyPress(a);
        } //if
        if (tet.one.getY() + SHIFT < ytotal && tet.two.getY()
                + SHIFT < ytotal && tet.three.getY() + SHIFT < ytotal
                && tet.four.getY() + SHIFT < ytotal) {
            int movea = grid[(int) tet.one.getX() / S]
                    [((int) tet.one.getY() / S) + 1];
            int moveb = grid[(int) tet.two.getX() / S]
                    [((int) tet.two.getY() / S) + 1];
            int movec = grid[(int) tet.three.getX() / S]
                    [((int) tet.three.getY() / S) + 1];
            int moved = grid[(int) tet.four.getX() / S]
                    [((int) tet.four.getY() / S) + 1];
            if (movea == 0 && movea == moveb && moveb == movec && movec == moved) {
                tet.one.setY(tet.one.getY() + SHIFT);
                tet.two.setY(tet.two.getY() + SHIFT);
                tet.three.setY(tet.three.getY() + SHIFT);
                tet.four.setY(tet.four.getY() + SHIFT);
            } //if
        } //if
    } //moveShapeDown

    /**
     * Method to shift the block down.
     *
     * @param tet The specified Tetris function
     * @return true if block has space beneath it, false otherwise
     */
    private boolean shiftOne(Tetris tet) {
        return (grid[(int) tet.one.getX() / S][((int) tet.one.getY() / S) + 1]
                == 1);
    } //shiftOne

    /**
     * Method to shift the block down.
     *
     * @return true if block has space beneath it, false otherwise
     * @param tet The specified Tetris function
     */
    private boolean shiftTwo(Tetris tet) {
        return (grid[(int) tet.two.getX() / S][((int) tet.two.getY() / S) + 1]
                == 1);
    } //shiftTwo

    /**
     * Method to shift the block down.
     *
     * @return true if block has space beneath it, false otherwise
     * @param tet The specified Tetris function
     */
    private boolean shiftThree(Tetris tet) {
        return (grid[(int) tet.three.getX() / S][((int) tet.three.getY() / S) + 1]
                == 1);
    } //shiftThree

    /**
     * Method to shift the block down.
     *
     * @return true if block has space beneath it, false otherwise
     * @param tet The specified Tetris function
     */
    private boolean shiftFour(Tetris tet) {
        return (grid[(int) tet.four.getX() / S][((int) tet.four.getY() / S) + 1]
                == 1);
    } //shiftFour
} //AracdeApp
