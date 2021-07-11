package View;

import Logic.Logic;
import Pieces.Coordinates;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

public class View {

    /**
     * Size of the block of a piece.
     */
    public final int BLOCK_SIZE = 40;

    /**
     * Size of the sidebar pieces.
     */
    public final int SIDE_BLOCK_SIZE = 30;

    /**
     * Size of the frame borders.
     */
    public final int BORDER_SIZE = 3;

    /**
     * Size of the rounded edges.
     */
    public final int ROUND_EDGE = 10;

    /**
     * Distance of the display from the top.
     */
    public final int VERTICAL_DISTANCE = 30;

    /**
     * Widths of the sidebars.
     */
    public final int SIDE_BAR_WIDTHS = 180;

    /**
     * Height of the hold frame.
     */
    public final int HOLD_BAR_HEIGHT = 140;

    /**
     * Height of the queue frame.
     */
    public final int QUEUE_BAR_HEIGHT = 500;

    /**
     * Width of the grid.
     */
    public final int GRID_WIDTH = 400;

    /**
     * Height of the grid.
     */
    public final int GRID_HEIGHT = 800;

    /**
     * Width of the progress bar.
     */
    public final int PROGRESS_BAR_WIDTH = 20;

    /**
     * Height of the progress bar.
     */
    public final int PROGRESS_BAR_HEIGHT = GRID_HEIGHT;

    /**
     * Main display width.
     */
    public final int CANVAS_WIDTH = GRID_WIDTH + BORDER_SIZE * 3 + PROGRESS_BAR_WIDTH;

    /**
     * Main display height.
     */
    public final int CANVAS_HEIGHT = GRID_HEIGHT + BORDER_SIZE * 2;

    /**
     * Window width.
     */
    public final int HORIZONTAL_WIDTH = 1500;

    /**
     * Colour of the display frame.
     */
    public final Color frameColour = Color.rgb(70,70,70);

    /**
     * Colour of the main titles.
     */
    public final Color titleColor = Color.rgb(80, 80, 220);

    /**
     * Colour of the subtitles used in the menus.
     */
    public final Color subtitleColor = Color.rgb(150, 150, 255);

    /**
     * Stores the instance of the logic behind the game functionality.
     */
    private final Logic gameLogic;

    /**
     * Stores the instance of the scene to display the game.
     */
    private final Stage stage;

    /**
     * Graphics for the menu screen.
     */
    private GraphicsContext menuGraphics;

    /**
     * Menu scene.
     */
    private Scene menuScene;

    /**
     * Hold Bar Graphics
     */
    private GraphicsContext holdGraphics;

    /**
     * Game grid graphics.
     */
    private GraphicsContext displayGraphics;

    /**
     * Queue Bar Graphics.
     */
    private GraphicsContext queueGraphics;

    /**
     * Stats Bar Graphics.
     */
    private GraphicsContext statsGraphics;

    /**
     * Menu Canvas.
     */
    private Canvas menuCanvas;

    /**
     * Queue Canvas.
     */
    private Canvas queueCanvas;

    /**
     * Display Canvas.
     */
    private Canvas displayCanvas;

    /**
     * Game display Scene/
     */
    private Scene gameScene;

    /**
     * About display scene.
     */
    private Scene aboutScene;

    /**
     * Graphics context for the about display.
     */
    private GraphicsContext aboutGraphics;

    /**
     * Canvas or the about display.
     */
    private Canvas aboutCanvas;

    /**
     * About display scene.
     */
    private Scene settingsScene;

    /**
     * Graphics context for the about display.
     */
    private GraphicsContext settingsGraphics;

    /**
     * Canvas or the about display.
     */
    private Canvas settingsCanvas;

    /**
     * Background image.
     */
    private BackgroundImage background;

    /**
     * Stores the big LemonMilk font size.
     */
    private Font bigFont;

    /**
     * Stores the small LemonMilk font size.
     */
    private Font smallFont;

    /**
     * Stores the title LemonMilk font size.
     */
    private Font titleFont;

    /**
     * Stores the subtitle LemonMilk font size.
     */
    private Font subtitleFont;

    /**
     * Stores the big LemonMilk font size.
     */
    private Font menuFont;

    /**
     * Initialises the instance of the view class which will update the interface.
     */
    public View(Logic gameLogic, Stage stage) {
        this.gameLogic = gameLogic;
        this.stage = stage;

        loadResources();
        initialiseMenu();
        initialiseGame();
        initialiseAbout();
        initialiseSettings();

        setMenuScene();
    }

    /**
     * Loads all the images and fonts that will be used in the program.
     */
    public void loadResources() {

        this.background = new BackgroundImage(new Image("Images/Wallpaper.png",1920, 1080,false,true),
                BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                BackgroundSize.DEFAULT);

        try {
            this.bigFont = Font.loadFont(new FileInputStream("src/Font/StatsFont.ttf"), 60);
            this.smallFont = Font.loadFont(new FileInputStream("src/Font/StatsFont.ttf"), 35);
            this.titleFont = Font.loadFont(new FileInputStream("src/Font/StatsFont.ttf"), 200);
            this.subtitleFont = Font.loadFont(new FileInputStream("src/Font/StatsFont.ttf"), 25);
            this.menuFont = Font.loadFont(new FileInputStream("src/Font/StatsFont.ttf"), 30);
        } catch (Exception e) {
            System.out.println("Resources unable to load.");
        }
    }

    /**
     * Creates the scene for the main menu.
     */
    public void initialiseMenu() {
        HBox mainMenu = new HBox();

        Group menuRoot = new Group();
        this.menuCanvas = new Canvas(1920,1080);
        this.menuGraphics = menuCanvas.getGraphicsContext2D();
        menuRoot.getChildren().add(menuCanvas);

        titleDefault();

        mainMenu.getChildren().add(menuRoot);
        mainMenu.setAlignment(Pos.BASELINE_CENTER);

        this.menuScene = new Scene(mainMenu, HORIZONTAL_WIDTH,
                CANVAS_HEIGHT + VERTICAL_DISTANCE * 2, Color.BLACK);
    }

    /**
     * Displays the main menu to the screen.
     */
    public void setMenuScene() {
        titleDefault();
        this.gameLogic.setNoGame();
        this.stage.setScene(menuScene);
    }

    /**
     * Builds the default layout of the main menu screen.
     */
    public void titleDefault() {
        this.menuGraphics.clearRect(0,0, 1920, 1080);

        this.menuGraphics.setFont(titleFont);
        this.menuGraphics.setFill(titleColor);
        this.menuGraphics.fillText("Junstris", 600, 530);

        this.menuGraphics.setFont(subtitleFont);
        this.menuGraphics.setFill(subtitleColor);
        this.menuGraphics.fillText("Tetris", 1500, 570);

        this.menuGraphics.setFill(Color.rgb(50, 95, 115));
        this.menuGraphics.fillRoundRect(300, 720, 400, 100, 5, 5);

        this.menuGraphics.setFill(Color.rgb(15, 60, 90));
        this.menuGraphics.fillRect(302, 722, 396, 96);

        this.menuGraphics.setFill(Color.rgb(100, 145, 165));
        this.menuGraphics.fillText("100L", 315, 760);

        this.menuGraphics.setFill(Color.rgb(208, 0, 59));
        this.menuGraphics.fillRoundRect(600, 620, 200, 100, 5, 5);
        this.menuGraphics.fillRoundRect(700, 720, 200, 100, 5, 5);

        this.menuGraphics.setFill(Color.rgb(103, 0, 28));
        this.menuGraphics.fillRect(702, 722, 196, 96);
        this.menuGraphics.fillRect(602, 622, 196, 96);
        this.menuGraphics.fillRect(702, 622, 96, 196);

        this.menuGraphics.setFill(Color.rgb(230, 50, 100));
        this.menuGraphics.fillText("FreePlay", 615, 660);

        this.menuGraphics.setFill(Color.rgb(50, 65, 193));
        this.menuGraphics.fillRoundRect(300, 520, 100, 200, 5, 5);
        this.menuGraphics.fillRoundRect(300, 620, 300, 100, 5, 5);

        this.menuGraphics.setFill(Color.rgb(24, 32, 97));
        this.menuGraphics.fillRect(302, 522, 96, 196);
        this.menuGraphics.fillRect(302, 622, 296, 96);

        this.menuGraphics.setFill(Color.rgb(90, 100, 225));
        this.menuGraphics.fillText("40L", 315, 560);

        this.menuGraphics.setFill(Color.rgb(94, 179, 40));
        this.menuGraphics.fillRoundRect(300, 320, 100, 200, 5, 5);
        this.menuGraphics.fillRoundRect(400, 420, 100, 200, 5, 5);

        this.menuGraphics.setFill(Color.rgb(46, 89, 15));
        this.menuGraphics.fillRect(302, 322, 96, 196);
        this.menuGraphics.fillRect(402, 422, 96, 196);
        this.menuGraphics.fillRect(302, 422, 196, 96);

        this.menuGraphics.setFill(Color.rgb(94, 179, 40));
        this.menuGraphics.fillText("20L", 315, 360);

        this.menuGraphics.setFill(Color.rgb(115, 40, 90));
        this.menuGraphics.fillRoundRect(800, 620, 300, 100, 5, 5);
        this.menuGraphics.fillRoundRect(900, 620, 100, 200, 5, 5);

        this.menuGraphics.setFill(Color.rgb(75, 12, 60));
        this.menuGraphics.fillRect(802, 622, 296, 96);
        this.menuGraphics.fillRect(902, 622, 96, 196);

        this.menuGraphics.setFill(Color.rgb(130, 60, 110));
        this.menuGraphics.fillText("Settings", 815, 660);

        this.menuGraphics.setFill(Color.rgb(220, 80, 30));
        this.menuGraphics.fillRoundRect(1000, 720, 300, 100, 5, 5);
        this.menuGraphics.fillRoundRect(1200, 620, 100, 200, 5, 5);

        this.menuGraphics.setFill(Color.rgb(110, 40, 10));
        this.menuGraphics.fillRect(1002, 722, 296, 96);
        this.menuGraphics.fillRect(1202, 622, 96, 196);

        this.menuGraphics.setFill(Color.rgb(230, 90, 45));
        this.menuGraphics.fillText("About", 1015, 760);

        this.menuGraphics.setFill(Color.rgb(222, 156, 43));
        this.menuGraphics.fillRoundRect(1400, 620, 200, 200, 5, 5);

        this.menuGraphics.setFill(Color.rgb(110, 78, 17));
        this.menuGraphics.fillRect(1402, 622, 196, 196);

        this.menuGraphics.setFill(Color.rgb(230, 170, 60));
        this.menuGraphics.fillText("Exit", 1415, 660);
    }

    /**
     * Change in the display when the Hundred Line button is hovered over.
     */
    public void hundredLineHover() {
        this.menuGraphics.setFill(Color.rgb(70, 105, 140));
        this.menuGraphics.fillRoundRect(290, 710, 420, 120, 5, 5);

        this.menuGraphics.setFill(Color.rgb(25, 70, 100));
        this.menuGraphics.fillRect(292, 712, 416, 116);

        this.menuGraphics.setFill(Color.rgb(140, 185, 185));
        this.menuGraphics.fillText("100L", 305, 750);
    }

    /**
     * Change in the display when the Forty Line button is hovered over.
     */
    public void fortyLineHover() {
        this.menuGraphics.setFill(Color.rgb(65, 80, 205));
        this.menuGraphics.fillRoundRect(290, 510, 120, 220, 5, 5);
        this.menuGraphics.fillRoundRect(290, 610, 320, 120, 5, 5);

        this.menuGraphics.setFill(Color.rgb(40, 50, 110));
        this.menuGraphics.fillRect(292, 512, 116, 196);
        this.menuGraphics.fillRect(292, 612, 316, 116);

        this.menuGraphics.setFill(Color.rgb(120, 130, 255));
        this.menuGraphics.fillText("40L", 305, 550);
    }

    /**
     * Change in the display when the Twenty Line button is hovered over.
     */
    public void twentyLineHover() {
        this.menuGraphics.setFill(Color.rgb(105, 190, 50));
        this.menuGraphics.fillRoundRect(290, 310, 120, 220, 5, 5);
        this.menuGraphics.fillRoundRect(390, 410, 120, 220, 5, 5);

        this.menuGraphics.setFill(Color.rgb(60, 100, 30));
        this.menuGraphics.fillRect(292, 312, 116, 216);
        this.menuGraphics.fillRect(392, 412, 116, 216);
        this.menuGraphics.fillRect(292, 412, 216, 116);

        this.menuGraphics.setFill(Color.rgb(130, 220, 100));
        this.menuGraphics.fillText("20L", 305, 350);
    }

    /**
     * Change in the display when the FreePlay button is hovered over.
     */
    public void freePlayHover() {
        this.menuGraphics.setFill(Color.rgb(220, 20, 75));
        this.menuGraphics.fillRoundRect(590, 610, 220, 120, 5, 5);
        this.menuGraphics.fillRoundRect(690, 710, 220, 120, 5, 5);

        this.menuGraphics.setFill(Color.rgb(120, 20, 45));
        this.menuGraphics.fillRect(692, 712, 216, 116);
        this.menuGraphics.fillRect(592, 612, 216, 116);
        this.menuGraphics.fillRect(692, 612, 116, 216);

        this.menuGraphics.setFill(Color.rgb(250, 70, 120));
        this.menuGraphics.fillText("FreePlay", 605, 650);
    }

    /**
     * Change in the display when the Settings button is hovered over.
     */
    public void settingsHover() {
        this.menuGraphics.setFill(Color.rgb(130, 55, 105));
        this.menuGraphics.fillRoundRect(790, 610, 320, 120, 5, 5);
        this.menuGraphics.fillRoundRect(890, 610, 120, 220, 5, 5);

        this.menuGraphics.setFill(Color.rgb(90, 30, 80));
        this.menuGraphics.fillRect(792, 612, 316, 116);
        this.menuGraphics.fillRect(892, 612, 116, 216);

        this.menuGraphics.setFill(Color.rgb(170, 100, 150));
        this.menuGraphics.fillText("Settings", 805, 650);
    }

    /**
     * Change in the display when the About button is hovered over.
     */
    public void aboutHover() {
        this.menuGraphics.setFill(Color.rgb(240, 100, 50));
        this.menuGraphics.fillRoundRect(990, 710, 320, 120, 5, 5);
        this.menuGraphics.fillRoundRect(1190, 610, 120, 220, 5, 5);

        this.menuGraphics.setFill(Color.rgb(130, 60, 30));
        this.menuGraphics.fillRect(992, 712, 316, 116);
        this.menuGraphics.fillRect(1192, 612, 116, 216);

        this.menuGraphics.setFill(Color.rgb(240, 150, 100));
        this.menuGraphics.fillText("About", 1005, 750);
    }

    /**
     * Change in the display when the Exit   button is hovered over.
     */
    public void exitHover() {
        this.menuGraphics.setFill(Color.rgb(240, 175, 63));
        this.menuGraphics.fillRoundRect(1390, 610, 220, 220, 5, 5);

        this.menuGraphics.setFill(Color.rgb(130, 100, 40));
        this.menuGraphics.fillRect(1392, 612, 216, 216);

        this.menuGraphics.setFill(Color.rgb(250, 200, 150));
        this.menuGraphics.fillText("Exit", 1405, 650);
    }

    /**
     * Creates the scene for the game functionality.
     */
    public void initialiseGame() {
        HBox display = new HBox();

        VBox leftBox = new VBox();

        Group holdRoot = new Group();
        Canvas holdCanvas = new Canvas(SIDE_BAR_WIDTHS + BORDER_SIZE,BORDER_SIZE * 2 + VERTICAL_DISTANCE + HOLD_BAR_HEIGHT);
        this.holdGraphics = holdCanvas.getGraphicsContext2D();
        holdRoot.getChildren().add(holdCanvas);

        Group statsRoot = new Group();
        Canvas statsCanvas = new Canvas(SIDE_BAR_WIDTHS + BORDER_SIZE, CANVAS_HEIGHT - BORDER_SIZE * 2 - HOLD_BAR_HEIGHT);
        this.statsGraphics = statsCanvas.getGraphicsContext2D();
        statsRoot.getChildren().add(statsCanvas);

        leftBox.getChildren().addAll(holdRoot, statsRoot);
        leftBox.setAlignment(Pos.TOP_CENTER);

        Group gameBoardRoot = new Group();
        this.displayCanvas = new Canvas(CANVAS_WIDTH, CANVAS_HEIGHT + VERTICAL_DISTANCE);
        this.displayGraphics = displayCanvas.getGraphicsContext2D();
        gameBoardRoot.getChildren().add(displayCanvas);

        Group queueRoot = new Group();
        this.queueCanvas = new Canvas(SIDE_BAR_WIDTHS + BORDER_SIZE, CANVAS_HEIGHT + VERTICAL_DISTANCE);
        this.queueGraphics = queueCanvas.getGraphicsContext2D();
        queueRoot.getChildren().add(queueCanvas);

        display.getChildren().addAll(leftBox, gameBoardRoot, queueRoot);

        createFrame();
        resetCanvas();
        displayCurrentPiece();
        createProgressBar();
        inGameMenuDefault();
        updateStats();
        updateHoldBar();
        updateQueueBar();
        createStatsBar();
        updateStats();

        display.setBackground(new Background(background));
        display.setAlignment(Pos.TOP_CENTER);

        this.gameScene = new Scene(display, HORIZONTAL_WIDTH
                ,CANVAS_HEIGHT + VERTICAL_DISTANCE * 2);
    }

    /**
     * Sets the scene to display the game.
     */
    public void setGameScene() {
        inGameMenuDefault();
        this.stage.setScene(gameScene);
    }

    /**
     * Returns the main menu display canvas.
     *
     * @return menu canvas.
     */
    public Canvas getTitleCanvas() {
        return this.menuCanvas;
    }

    /**
     * Returns the side menu display canvas.
     *
     * @return queue canvas.
     */
    public Canvas getQueueCanvas() {
        return this.queueCanvas;
    }

    /**
     * Gets the game scene.
     */
    public Scene getGameScene() {
        return this.gameScene;
    }

    /**
     * Creates the option menu next to the game display.
     */
    public void inGameMenuDefault() {
        queueGraphics.setFill(Color.rgb(50,50,50));
        queueGraphics.clearRect(0, 550, 200, 500);

        queueGraphics.setFont(menuFont);
        queueGraphics.fillText("Menu", 20, 600);
        queueGraphics.fillText("Restart", 20, 660);
        queueGraphics.fillText("Settings", 20, 720);
        queueGraphics.fillText("Exit", 20, 780);
    }

    /**
     * Change in the display when the menu button is hovered.
     */
    public void menuHover() {
        queueGraphics.setFill(Color.rgb(200,200,200));

        queueGraphics.setFont(menuFont);
        queueGraphics.fillText("Menu", 20, 600);
    }

    /**
     * Change in the display when the restart button is hovered.
     */
    public void restartHover() {
        queueGraphics.setFill(Color.rgb(200,200,200));

        queueGraphics.setFont(menuFont);
        queueGraphics.fillText("Restart", 20, 660);
    }

    /**
     * Change in the display when the settings button is hovered.
     */
    public void settingsGameHover() {
        queueGraphics.setFill(Color.rgb(200,200,200));

        queueGraphics.setFont(menuFont);
        queueGraphics.fillText("Settings", 20, 720);
    }

    /**
     * Change in the display when the exit button is hovered.
     */
    public void exitGameHover() {
        queueGraphics.setFill(Color.rgb(200,200,200));

        queueGraphics.setFont(menuFont);
        queueGraphics.fillText("Exit", 20, 780);
    }

    /**
     * Creates the countdown for game start.
     */
    public void createCountdown() {
        displayGraphics.setGlobalAlpha(0.02);
        displayGraphics.setFill(Color.rgb(80, 80, 220));
        displayGraphics.fillRect(BORDER_SIZE, VERTICAL_DISTANCE + BORDER_SIZE + 300,
                GRID_WIDTH, 200);

        displayGraphics.setGlobalAlpha(1);
    }

    /**
     * Creates the countdown text.
     */
    public void createCountdownText(String second) {
        displayGraphics.setFill(Color.DARKGREY);
        displayGraphics.setFont(titleFont);
        displayGraphics.setTextAlign(TextAlignment.CENTER);
        displayGraphics.fillText(second, (BORDER_SIZE + GRID_WIDTH/2 + 100),510);
        displayGraphics.setTextAlign(TextAlignment.LEFT);
    }

    /**
     * Creates the frame for the display.
     */
    public void createFrame() {
        holdGraphics.setFill(frameColour);
        holdGraphics.fillRoundRect(0, VERTICAL_DISTANCE, SIDE_BAR_WIDTHS + BORDER_SIZE * 2,
                HOLD_BAR_HEIGHT + BORDER_SIZE * 2, ROUND_EDGE, ROUND_EDGE);

        displayGraphics.setFill(frameColour);
        displayGraphics.fillRoundRect(0, VERTICAL_DISTANCE, GRID_WIDTH + BORDER_SIZE * 3 + PROGRESS_BAR_WIDTH,
                GRID_HEIGHT + BORDER_SIZE * 2, ROUND_EDGE, ROUND_EDGE);
        displayGraphics.fillRect(0, VERTICAL_DISTANCE, GRID_WIDTH + BORDER_SIZE * 3 + PROGRESS_BAR_WIDTH, ROUND_EDGE);

        queueGraphics.setFill(frameColour);
        queueGraphics.fillRoundRect(0,VERTICAL_DISTANCE, SIDE_BAR_WIDTHS + BORDER_SIZE,
                QUEUE_BAR_HEIGHT + BORDER_SIZE * 2, ROUND_EDGE, ROUND_EDGE);
        queueGraphics.fillRect(0,VERTICAL_DISTANCE, BORDER_SIZE,
                QUEUE_BAR_HEIGHT + BORDER_SIZE * 2);
    }

    /**
     * Draws a block of the colour passed in at the location.
     * @param row row position
     * @param column column position
     * @param color colour of the block
     */
    public void drawBlock(int row, int column, Color color) {
        if (row < 0) {
            return;
        }
        displayGraphics.setFill(color);
        displayGraphics.fillRect(column * BLOCK_SIZE + BORDER_SIZE,
                row * BLOCK_SIZE + BORDER_SIZE + VERTICAL_DISTANCE,
                BLOCK_SIZE, BLOCK_SIZE);
    }

    /**
     * Resets the state of the canvas to display only the existing pieces.
     */
    public void resetCanvas() {
        displayGraphics.clearRect(BORDER_SIZE + 1, BORDER_SIZE + VERTICAL_DISTANCE,
                GRID_WIDTH - 1, GRID_HEIGHT);
        displayGraphics.setGlobalAlpha(0.8);
        displayGraphics.setFill(Color.BLACK);
        displayGraphics.fillRect(BORDER_SIZE, BORDER_SIZE + VERTICAL_DISTANCE,
                GRID_WIDTH, GRID_HEIGHT);
        displayGraphics.setGlobalAlpha(1);
        for (Map.Entry<Coordinates, Color> existingBlocks : gameLogic.getOccupiedBlocks().entrySet()) {
            drawBlock(existingBlocks.getKey().getRow(), existingBlocks.getKey().getColumn(), existingBlocks.getValue());
        }
    }

    /**
     * Displays the state of the game on game end.
     */
    public void displayGameEnd() {
        for (Coordinates coordinates : gameLogic.getCurrentPiecePositions()) {
            drawBlock(coordinates.getRow(), coordinates.getColumn(), Color.GRAY);
        }

        for (Map.Entry<Coordinates, Color> existingBlocks : gameLogic.getOccupiedBlocks().entrySet()) {
            drawBlock(existingBlocks.getKey().getRow(), existingBlocks.getKey().getColumn(), Color.GRAY);
        }

        displayGraphics.setFill(Color.rgb(80, 80, 220));
        displayGraphics.fillRect(BORDER_SIZE, VERTICAL_DISTANCE + BORDER_SIZE + 300,
                GRID_WIDTH, 200);

        displayGraphics.setFill(Color.BLACK);
        displayGraphics.setFont(bigFont);
        displayGraphics.setTextAlign(TextAlignment.CENTER);
        List<Long> times = gameLogic.getTimes();
        String time = "" + times.get(4) + ":" + times.get(3) + times.get(2) + ":" + times.get(1) + times.get(0);
        displayGraphics.fillText(time, (BORDER_SIZE + GRID_WIDTH/2),450);
        displayGraphics.setTextAlign(TextAlignment.LEFT);
    }

    /**
     * Displays the current piece.
     */
    public void displayCurrentPiece() {
        List<Coordinates> currentPositions = gameLogic.getCurrentPiecePositions();
        int dropDistance = this.gameLogic.ghostPieceDistance();
        if (!gameLogic.getGameEnd()) {
            for (Coordinates coordinates : currentPositions) {
                drawBlock(coordinates.getRow() + dropDistance, coordinates.getColumn(),
                        gameLogic.getCurrentPiece().getGhostColour());
            }
        }

        for (Coordinates coordinates : currentPositions) {
            drawBlock(coordinates.getRow(), coordinates.getColumn(), gameLogic.getCurrentPiece().getColour());
        }
    }

    /**
     * Updates the progress bar.
     */
    public void updateProgressBar() {
        displayGraphics.setFill(Color.BLACK);
        displayGraphics.fillRoundRect(GRID_WIDTH + BORDER_SIZE * 2, BORDER_SIZE + VERTICAL_DISTANCE,
                PROGRESS_BAR_WIDTH, PROGRESS_BAR_HEIGHT, ROUND_EDGE, ROUND_EDGE);

        double remaining = gameLogic.getLinesToClear() - gameLogic.getLinesCleared();
        double toClear = gameLogic.getLinesToClear();
        double percentage = (toClear - remaining) / toClear;
        if (percentage > 1) {
            percentage = 1;
        }

        displayGraphics.setFill(Color.rgb(80, 80, 220));
        displayGraphics.fillRoundRect(GRID_WIDTH + BORDER_SIZE * 2,
                BORDER_SIZE + VERTICAL_DISTANCE + PROGRESS_BAR_HEIGHT * (1 - percentage),
                PROGRESS_BAR_WIDTH, PROGRESS_BAR_HEIGHT * percentage, 0, 0);

    }

    /**
     * Creates the progress bar.
     */
    public void createProgressBar() {
        displayGraphics.setFill(Color.BLACK);
        displayGraphics.fillRoundRect(GRID_WIDTH + BORDER_SIZE * 2, BORDER_SIZE + VERTICAL_DISTANCE,
                PROGRESS_BAR_WIDTH, PROGRESS_BAR_HEIGHT, ROUND_EDGE, ROUND_EDGE);
    }

    /**
     * Updates the state of the hold bar to the display.
     */
    public void createStatsBar() {
        statsGraphics.setFont(subtitleFont);
        statsGraphics.setTextAlign(TextAlignment.RIGHT);
        statsGraphics.setFill(Color.rgb(50,50,50));

        statsGraphics.fillText("Lines", 170, 425);
        statsGraphics.fillText("PPS", 170, 550);
        statsGraphics.fillText("Time", 170, 650);

        statsGraphics.setTextAlign(TextAlignment.LEFT);

    }

    /**
     * Updates the values of the statistics of the game.
     */
    public void updateStats() {
        statsGraphics.setTextAlign(TextAlignment.RIGHT);
        statsGraphics.setFont(bigFont);
        statsGraphics.setFill(Color.WHITE);

        statsGraphics.clearRect(0, 340, 200, 55);
        statsGraphics.fillText("" + this.gameLogic.getLinesCleared(), 170, 390);

        DecimalFormat df = new DecimalFormat("0.00");

        statsGraphics.clearRect(0, 465, 200, 55);
        if (gameLogic.getTime() != 0) {
            double PPS = (((double)this.gameLogic.piecesPlaced()) / gameLogic.getTime());
            statsGraphics.fillText("" + df.format(PPS), 170, 515);
        } else {
            statsGraphics.fillText("0.00", 170, 515);
        }

        statsGraphics.clearRect(0, 580, 200, 40);
        statsGraphics.setFont(smallFont);
        statsGraphics.fillText("" + this.gameLogic.getTimes().get(0), 170, 615);
        statsGraphics.fillText("" + this.gameLogic.getTimes().get(1), 145, 615);
        statsGraphics.fillText(":", 120, 615);
        statsGraphics.fillText("" + this.gameLogic.getTimes().get(2), 110, 615);
        statsGraphics.fillText("" + this.gameLogic.getTimes().get(3), 85, 615);
        statsGraphics.fillText(":", 55, 615);
        statsGraphics.fillText("" + this.gameLogic.getTimes().get(4), 45, 615);

        statsGraphics.setTextAlign(TextAlignment.LEFT);
    }

    /**
     * Updates the state of the hold bar to the display.
     */
    public void createHoldBar() {
        if (this.gameLogic.isHoldCheck()) {
            holdGraphics.setFill(Color.rgb(40,15,15));
        } else {
            holdGraphics.setFill(Color.BLACK);
        }
        holdGraphics.fillRoundRect(BORDER_SIZE, VERTICAL_DISTANCE + BORDER_SIZE, SIDE_BAR_WIDTHS, HOLD_BAR_HEIGHT, ROUND_EDGE, ROUND_EDGE);
    }

    public void updateHoldBar() {
        createHoldBar();
        if (this.gameLogic.getHoldPiece() == null) {
            return;
        }
        for (Coordinates coordinates : this.gameLogic.getHoldPiece().getCoordinates()) {
            boolean horizontal = this.gameLogic.getHoldPiece().shiftRequired();
            boolean vertical = this.gameLogic.getHoldPiece().verticalShift();
            drawHoldBlocks(coordinates.getRow(), coordinates.getColumn(), gameLogic.getHoldPiece().getColour(), horizontal, vertical);
        }
    }

    /**
     * Draws the blocks of the pieces in the hold display.
     *
     * @param row row of the block
     * @param column column of the block
     * @param color colour of the piece
     * @param horizontal horizontal shift
     * @param vertical vertial shift
     */
    public void drawHoldBlocks(int row, int column, Color color, boolean horizontal, boolean vertical) {
        holdGraphics.setFill(color);
        if (horizontal) {
            holdGraphics.fillRect(column * SIDE_BLOCK_SIZE + SIDE_BLOCK_SIZE + SIDE_BLOCK_SIZE/2 + BORDER_SIZE,
                    (row + 1) * SIDE_BLOCK_SIZE + SIDE_BLOCK_SIZE/2 + VERTICAL_DISTANCE, SIDE_BLOCK_SIZE, SIDE_BLOCK_SIZE);
        } else if (vertical){
            holdGraphics.fillRect(column * SIDE_BLOCK_SIZE + SIDE_BLOCK_SIZE + BORDER_SIZE,
                    (row) * SIDE_BLOCK_SIZE + SIDE_BLOCK_SIZE + SIDE_BLOCK_SIZE/2 + VERTICAL_DISTANCE, SIDE_BLOCK_SIZE, SIDE_BLOCK_SIZE);
        } else {
            holdGraphics.fillRect(column * SIDE_BLOCK_SIZE + SIDE_BLOCK_SIZE + BORDER_SIZE,
                    (row + 1) * SIDE_BLOCK_SIZE + VERTICAL_DISTANCE, SIDE_BLOCK_SIZE, SIDE_BLOCK_SIZE);
        }
    }

    /**
     * Updates the state of the queue bar to the display.
     */
    public void updateQueueBar() {
        queueGraphics.setFill(Color.BLACK);
        queueGraphics.fillRoundRect(0,VERTICAL_DISTANCE + BORDER_SIZE,
                SIDE_BAR_WIDTHS, QUEUE_BAR_HEIGHT, ROUND_EDGE, ROUND_EDGE);
        for (int i = 0; i < 5; i++) {
            boolean shift = this.gameLogic.getQueue().get(i).shiftRequired();
            for (Coordinates coordinates : this.gameLogic.getQueue().get(i).getCoordinates()) {
                drawQueueBlocks(coordinates.getRow() + i * 3, coordinates.getColumn(),
                        gameLogic.getQueue().get(i).getColour(), shift);
            }
        }
    }

    /**
     * Draws the blocks of the pieces in the queue display.
     *
     * @param row row of the block
     * @param column column of the block
     * @param color colour of the piece
     * @param shift horizontal shift
     */
    public void drawQueueBlocks(int row, int column, Color color, boolean shift) {
        queueGraphics.setFill(color);
        if (shift) {
            queueGraphics.fillRect(column * SIDE_BLOCK_SIZE + SIDE_BLOCK_SIZE + SIDE_BLOCK_SIZE/2,
                    row * SIDE_BLOCK_SIZE + SIDE_BLOCK_SIZE + VERTICAL_DISTANCE + BORDER_SIZE, SIDE_BLOCK_SIZE, SIDE_BLOCK_SIZE);
        } else {
            queueGraphics.fillRect(column * SIDE_BLOCK_SIZE + SIDE_BLOCK_SIZE,
                    row * SIDE_BLOCK_SIZE + SIDE_BLOCK_SIZE + VERTICAL_DISTANCE + BORDER_SIZE, SIDE_BLOCK_SIZE, SIDE_BLOCK_SIZE);
        }
    }

    /**
     * Creates the scene for the settings display.
     */
    public void initialiseSettings() {
        HBox settingsBox = new HBox();

        Group menuRoot = new Group();
        this.settingsCanvas = new Canvas(1920,1080);
        this.settingsGraphics = settingsCanvas.getGraphicsContext2D();
        menuRoot.getChildren().add(settingsCanvas);

        settingsDefault();

        settingsBox.getChildren().add(menuRoot);
        settingsBox.setAlignment(Pos.BASELINE_CENTER);

        this.settingsScene = new Scene(settingsBox, HORIZONTAL_WIDTH,
                CANVAS_HEIGHT + VERTICAL_DISTANCE * 2, Color.BLACK);
    }

    /**
     * Creates the change in display once the back button is hovered over.
     */
    public void backSettingsHover() {
        this.settingsGraphics.setFill(Color.rgb(240, 175, 63));
        this.settingsGraphics.fillRoundRect(1390, 610, 220, 220, 5, 5);

        this.settingsGraphics.setFill(Color.rgb(130, 100, 40));
        this.settingsGraphics.fillRect(1392, 612, 216, 216);

        this.settingsGraphics.setFill(Color.rgb(250, 200, 150));
        this.settingsGraphics.fillText("Back", 1405, 650);
    }

    /**
     * Default layout of the settings display.
     */
    public void settingsDefault() {
        this.settingsGraphics.clearRect(0,0,1920, 1080);
        this.settingsGraphics.setFill(titleColor);
        this.settingsGraphics.setFont(bigFont);
        this.settingsGraphics.fillText("Settings", 300, 120);

        this.settingsGraphics.setFont(subtitleFont);

        this.settingsGraphics.setFill(Color.rgb(70, 70, 130));
        this.settingsGraphics.fillText("Move Left", 300, 180);
        this.settingsGraphics.fillText("Move Right", 300, 240);
        this.settingsGraphics.fillText("Soft Drop", 300, 300);
        this.settingsGraphics.fillText("Hard Drop", 300, 360);
        this.settingsGraphics.fillText("Hold Piece", 300, 420);
        this.settingsGraphics.fillText("Clockwise Rotate", 300, 480);
        this.settingsGraphics.fillText("Anticlockwise Rotate", 300, 540);
        this.settingsGraphics.fillText("Flip Rotate", 300, 600);
        this.settingsGraphics.fillText("Restart Game", 300, 660);

        this.settingsGraphics.setFill(Color.rgb(80, 80, 160));
        this.settingsGraphics.setTextAlign(TextAlignment.CENTER);
        this.settingsGraphics.fillText(gameLogic.getLeftMoveCode().toString(), 700, 180);
        this.settingsGraphics.fillText(gameLogic.getRightMoveCode().toString(), 700, 240);
        this.settingsGraphics.fillText(gameLogic.getSoftDropCode().toString(), 700, 300);
        this.settingsGraphics.fillText(gameLogic.getDropCode().toString(), 700, 360);
        this.settingsGraphics.fillText(gameLogic.getHoldCode().toString(), 700, 420);
        this.settingsGraphics.fillText(gameLogic.getClockwiseRotateCode().toString(), 700, 480);
        this.settingsGraphics.fillText(gameLogic.getAnticlockwiseRotateCode().toString(), 700, 540);
        this.settingsGraphics.fillText(gameLogic.getFlipRotateCode().toString(), 700, 600);
        this.settingsGraphics.fillText(gameLogic.getRestartCode().toString(), 700, 660);

        this.settingsGraphics.setTextAlign(TextAlignment.LEFT);

        this.settingsGraphics.setFill(Color.rgb(222, 156, 43));
        this.settingsGraphics.fillRoundRect(1400, 620, 200, 200, 5, 5);

        this.settingsGraphics.setFill(Color.rgb(110, 78, 17));
        this.settingsGraphics.fillRect(1402, 622, 196, 196);

        this.settingsGraphics.setFill(Color.rgb(230, 170, 60));
        this.settingsGraphics.fillText("Back", 1415, 660);
    }

    /**
     * Move Left Button Hover.
     */
    public void moveLeftHover() {
        this.settingsGraphics.setFont(subtitleFont);
        this.settingsGraphics.setTextAlign(TextAlignment.CENTER);
        this.settingsGraphics.setFill(Color.rgb(130, 130, 240));
        this.settingsGraphics.fillText(gameLogic.getLeftMoveCode().toString(), 700, 180);
        this.settingsGraphics.setTextAlign(TextAlignment.LEFT);
    }

    /**
     * Move Right Button Hover.
     */
    public void moveRightHover() {
        this.settingsGraphics.setFont(subtitleFont);
        this.settingsGraphics.setTextAlign(TextAlignment.CENTER);
        this.settingsGraphics.setFill(Color.rgb(130, 130, 240));
        this.settingsGraphics.fillText(gameLogic.getRightMoveCode().toString(), 700, 240);
        this.settingsGraphics.setTextAlign(TextAlignment.LEFT);
    }

    /**
     * Soft Drop Button Hover.
     */
    public void softDropHover() {
        this.settingsGraphics.setFont(subtitleFont);
        this.settingsGraphics.setTextAlign(TextAlignment.CENTER);
        this.settingsGraphics.setFill(Color.rgb(130, 130, 240));
        this.settingsGraphics.fillText(gameLogic.getSoftDropCode().toString(), 700, 300);
        this.settingsGraphics.setTextAlign(TextAlignment.LEFT);
    }

    /**
     * Hard Drop Button Hover.
     */
    public void hardDropHover() {
        this.settingsGraphics.setFont(subtitleFont);
        this.settingsGraphics.setTextAlign(TextAlignment.CENTER);
        this.settingsGraphics.setFill(Color.rgb(130, 130, 240));
        this.settingsGraphics.fillText(gameLogic.getDropCode().toString(), 700, 360);
        this.settingsGraphics.setTextAlign(TextAlignment.LEFT);
    }

    /**
     * Hard Drop Button Hover.
     */
    public void holdPieceHover() {
        this.settingsGraphics.setFont(subtitleFont);
        this.settingsGraphics.setTextAlign(TextAlignment.CENTER);
        this.settingsGraphics.setFill(Color.rgb(130, 130, 240));
        this.settingsGraphics.fillText(gameLogic.getHoldCode().toString(), 700, 420);
        this.settingsGraphics.setTextAlign(TextAlignment.LEFT);
    }

    /**
     * Clockwise Rotate Button Hover.
     */
    public void clockwiseRotateHover() {
        this.settingsGraphics.setFont(subtitleFont);
        this.settingsGraphics.setTextAlign(TextAlignment.CENTER);
        this.settingsGraphics.setFill(Color.rgb(130, 130, 240));
        this.settingsGraphics.fillText(gameLogic.getClockwiseRotateCode().toString(), 700, 480);
        this.settingsGraphics.setTextAlign(TextAlignment.LEFT);
    }

    /**
     * Anticlockwise Rotate Button Hover.
     */
    public void anticlockwiseRotateHover() {
        this.settingsGraphics.setFont(subtitleFont);
        this.settingsGraphics.setTextAlign(TextAlignment.CENTER);
        this.settingsGraphics.setFill(Color.rgb(130, 130, 240));
        this.settingsGraphics.fillText(gameLogic.getAnticlockwiseRotateCode().toString(), 700, 540);
        this.settingsGraphics.setTextAlign(TextAlignment.LEFT);
    }

    /**
     * Flip Rotate Button Hover.
     */
    public void flipClockwiseRotateHover() {
        this.settingsGraphics.setFont(subtitleFont);
        this.settingsGraphics.setTextAlign(TextAlignment.CENTER);
        this.settingsGraphics.setFill(Color.rgb(130, 130, 240));
        this.settingsGraphics.fillText(gameLogic.getFlipRotateCode().toString(), 700, 600);
        this.settingsGraphics.setTextAlign(TextAlignment.LEFT);
    }

    /**
     * Restart Button Hover.
     */
    public void restartCodeHover() {
        this.settingsGraphics.setFont(subtitleFont);
        this.settingsGraphics.setTextAlign(TextAlignment.CENTER);
        this.settingsGraphics.setFill(Color.rgb(130, 130, 240));
        this.settingsGraphics.fillText(gameLogic.getRestartCode().toString(), 700, 660);
        this.settingsGraphics.setTextAlign(TextAlignment.LEFT);
    }

    /**
     * Sets the current scene to the settings page.
     */
    public void setSettingScene() {
        settingsDefault();
        this.gameLogic.setNoGame();
        this.stage.setScene(settingsScene);
    }

    /**
     * Returns the settings scene.
     *
     * @return Settings scene
     */
    public Scene getSettingsScene() {
        return this.settingsScene;
    }

    /**
     * Returns the settings canvas, used to initialise the controls of the canvas.
     *
     * @return Settings canvas
     */
    public Canvas getSettingsCanvas() {
        return this.settingsCanvas;
    }

    /**
     * Initialises the about screen.
     */
    public void initialiseAbout() {
        HBox aboutBox = new HBox();

        Group menuRoot = new Group();
        this.aboutCanvas = new Canvas(1920,1080);
        this.aboutGraphics = aboutCanvas.getGraphicsContext2D();
        menuRoot.getChildren().add(aboutCanvas);

        aboutDefault();

        aboutBox.getChildren().add(menuRoot);
        aboutBox.setAlignment(Pos.BASELINE_CENTER);

        this.aboutScene = new Scene(aboutBox, HORIZONTAL_WIDTH,
                CANVAS_HEIGHT + VERTICAL_DISTANCE * 2, Color.BLACK);
    }

    /**
     * Creates the change in display once the back button is hovered over.
     */
    public void backAboutHover() {
        this.aboutGraphics.setFill(Color.rgb(240, 175, 63));
        this.aboutGraphics.fillRoundRect(1390, 610, 220, 220, 5, 5);

        this.aboutGraphics.setFill(Color.rgb(130, 100, 40));
        this.aboutGraphics.fillRect(1392, 612, 216, 216);

        this.aboutGraphics.setFill(Color.rgb(250, 200, 150));
        this.aboutGraphics.fillText("Back", 1405, 650);
    }

    /**
     * Default layout of the about display.
     */
    public void aboutDefault() {
        this.aboutGraphics.clearRect(0,0,1920, 1080);
        this.aboutGraphics.setFill(titleColor);
        this.aboutGraphics.setFont(bigFont);
        this.aboutGraphics.fillText("About", 300, 120);

        this.aboutGraphics.fillText("References", 300, 450);

        this.aboutGraphics.setFill(Color.rgb(70, 70, 130));
        this.aboutGraphics.setFont(subtitleFont);

        this.aboutGraphics.fillText("Project started 17/06/2021 by Jonoym.",
                300, 170);

        this.aboutGraphics.fillText("This project was made to help practice Java and interfaces.",
                300, 215);

        this.aboutGraphics.fillText("There are still many bugs but overall should be functioning (sometimes).",
                300, 260);

        this.aboutGraphics.fillText("The font used is LemonMilk Light by marsnev.",
                300, 500);

        this.aboutGraphics.fillText("The background image used was from Unsplash.",
                300, 545);

        this.aboutGraphics.fillText("Sounds were provided user u/SingleInfinity.",
                300, 590);

        this.aboutGraphics.setFont(subtitleFont);
        this.aboutGraphics.setFill(Color.rgb(222, 156, 43));
        this.aboutGraphics.fillRoundRect(1400, 620, 200, 200, 5, 5);

        this.aboutGraphics.setFill(Color.rgb(110, 78, 17));
        this.aboutGraphics.fillRect(1402, 622, 196, 196);

        this.aboutGraphics.setFill(Color.rgb(230, 170, 60));
        this.aboutGraphics.fillText("Back", 1415, 660);
    }

    /**
     * Sets the current scene to display the about page.
     */
    public void setAboutScene() {
        aboutDefault();
        this.gameLogic.setNoGame();
        this.stage.setScene(aboutScene);
    }

    /**
     * Returns the about page canvas so controls can be initialised on them.
     *
     * @return About canvas
     */
    public Canvas getAboutCanvas() {
        return this.aboutCanvas;
    }


    /**
     * Returns the stage of the game.
     */
    public Stage getStage() {
        return this.stage;
    }
    /**
     * Closes the window of the game.
     */
    public void closeGame() {
        this.stage.close();
        System.exit(0);
    }
}
