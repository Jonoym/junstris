package Controller;

import Logic.Logic;
import View.View;
import javafx.application.Platform;
import javafx.scene.input.KeyCode;

import java.util.Timer;
import java.util.TimerTask;

public class Controller {

    /**
     * The view that will display the game state.
     */
    private final View view;

    /**
     * Stores the instance of the logic which can be manipulated by controls.
     */
    private final Logic gameLogic;

    /**
     * Number of lines to clear.
     */

    private int gameMode;

    /**
     * When left move has been pressed.
     */
    private boolean leftMove;

    /**
     * When left move has been held down.
     */
    private boolean leftMoveDAS;

    /**
     * Time since the last left move.
     */
    private long lastLeft;

    /**
     * When right move has been pressed.
     */
    private boolean rightMove;

    /**
     * When right move has been held down.
     */
    private boolean rightMoveDAS;

    /**
     * Time since the last right move.
     */
    private long lastRight;

    /**
     * When soft drop has been pressed.
     */
    private boolean softDrop;

    /**
     * When soft drop has been held down.
     */
    private boolean softDropDAS;

    /**
     * Time since the last soft drop.
     */
    private long lastSoftDrop;

    /**
     * Time since the last automatic drop.
     */
    private long lastAutoDrop;

    /**
     * When space bar has been pressed.
     */
    private boolean placePiece;

    /**
     * When shift has been pressed.
     */
    private boolean holdPiece;

    /**
     * When up has been pressed.
     */
    private boolean clockwiseRotate;

    /**
     * When Z has been pressed.
     */
    private boolean anticlockwiseRotate;

    /**
     * When X has been pressed.
     */
    private boolean flipRotate;

    /**
     * The timer which loops the controls.
     */
    private final Timer updateTimer;

    /**
     * The timer which loops the key settings change
     */
    private final Timer settingsTimer;

    /**
     * Counts the soft drop press.
     */
    private int softDropCount;

    /**
     * Counts the left press.
     */
    private int leftCount;

    /**
     * Counts the right press.
     */
    private int rightCount;

    /**
     * Restart game check.
     */
    private boolean restartGame;

    /**
     * Checks for a key press.
     */
    private KeyCode keyPress;

    /**
     * Waits for a key press.
     */
    private boolean keyPressWait;

    /**
     * Loop Count for the Countdown.
     */
    private int loopCount;

    /**
     * Initialises the Controller class to handle inputs to the game.
     *
     * @param view the display view
     * @param logic the logic of the game
     */
    public Controller(View view, Logic logic) {
        this.view = view;
        this.gameLogic = logic;

        this.leftMove = false;
        this.rightMove = false;
        this.softDrop = false;

        this.leftMoveDAS = false;
        this.rightMoveDAS = false;
        this.softDropDAS = false;

        this.restartGame = false;

        this.updateTimer = new Timer();
        this.settingsTimer = new Timer();
        this.keyPressWait = false;

        initialiseControls();
    }

    /**
     * Start a new game.
     */
    public void startGame(int lines) {
        this.gameLogic.startGame(lines);
        this.view.setGameScene();
        this.view.resetCanvas();
        this.view.updateHoldBar();
        this.view.updateProgressBar();
        this.view.updateQueueBar();

        Timer gameStartTimer = new Timer();

        loopCount = 0;
        this.view.resetCanvas();
        this.view.createCountdownText("3");
        firstCountDown(loopCount);

        Timer secondCountDown = new Timer();

        Timer thirdCountDown = new Timer();

        secondCountDown.schedule(new TimerTask() {
            @Override
            public void run() {
                loopCount = 0;
                view.resetCanvas();
                view.createCountdownText("2");
                secondCountDown(loopCount);
            }
        }, 1000);

        thirdCountDown.schedule(new TimerTask() {
            @Override
            public void run() {
                loopCount = 0;
                view.resetCanvas();
                view.createCountdownText("1");
                thirdCountDown(loopCount);
            }
        }, 2000);


        gameStartTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                gameLogic.setGameStart();
                updatePieceDisplay();
                updates();
                lastAutoDrop = System.currentTimeMillis();

                placePiece = false;
                softDrop = false;
                leftMove = false;
                rightMove = false;
                holdPiece = false;
                clockwiseRotate = false;
                anticlockwiseRotate = false;
                flipRotate = false;
                gameLogic.setStartTime();
            }
        }, 3000);
    }

    /**
     * Initialises the controls and links them to the required scene.
     */
    public void initialiseControls() {
        this.view.getGameScene().setOnKeyPressed(event -> {
            if (event.getCode() == gameLogic.getSoftDropCode()) {
                this.softDropCount++;
                this.softDrop = true;
                this.lastSoftDrop = System.currentTimeMillis();
                this.lastAutoDrop = System.currentTimeMillis();
            } else if (event.getCode() == gameLogic.getLeftMoveCode()) {
                this.leftCount++;
                this.leftMove = true;
                this.lastLeft = System.currentTimeMillis();
            } else if (event.getCode() == gameLogic.getRightMoveCode()) {
                this.rightCount++;
                this.rightMove = true;
                this.lastRight = System.currentTimeMillis();
            } else if (event.getCode() == gameLogic.getDropCode()) {
                this.placePiece = true;
                this.lastAutoDrop = System.currentTimeMillis();
            } else if (event.getCode() == gameLogic.getHoldCode()) {
                this.holdPiece = true;
                this.lastAutoDrop = System.currentTimeMillis();
            } else if (event.getCode() == gameLogic.getClockwiseRotateCode()) {
                this.clockwiseRotate = true;
            } else if (event.getCode() == gameLogic.getAnticlockwiseRotateCode()) {
                this.anticlockwiseRotate = true;
            } else if (event.getCode() == gameLogic.getFlipRotateCode()) {
                this.flipRotate = true;
            } else if(event.getCode() == gameLogic.getRestartCode()) {
                startGame(gameLogic.getLinesToClear());
            }
        });

        this.view.getStage().setOnCloseRequest(event -> this.view.closeGame());

        this.view.getGameScene().setOnKeyReleased(event -> {
            if (event.getCode() == gameLogic.getSoftDropCode()) {
                this.softDrop = false;
                this.softDropDAS = false;
                this.softDropCount = 0;
            } else if (event.getCode() == gameLogic.getLeftMoveCode()) {
                this.leftMove = false;
                this.leftMoveDAS = false;
                this.leftCount = 0;
            } else if (event.getCode() == gameLogic.getRightMoveCode()) {
                this.rightMove = false;
                this.rightMoveDAS = false;
                this.rightCount = 0;
            }
        });

        this.view.getTitleCanvas().setOnMouseMoved(event -> buttonHover(event.getX(), event.getY()));
        this.view.getTitleCanvas().setOnMouseClicked(event -> buttonPressed(event.getX(), event.getY()));

        this.view.getQueueCanvas().setOnMouseMoved(event -> buttonHoverGame(event.getX(), event.getY()));
        this.view.getQueueCanvas().setOnMouseClicked(event -> buttonPressedGame(event.getX(), event.getY()));

        this.view.getAboutCanvas().setOnMouseMoved(event -> aboutHover(event.getX(), event.getY()));
        this.view.getAboutCanvas().setOnMouseClicked(event -> aboutPress(event.getX(), event.getY()));

        this.view.getSettingsCanvas().setOnMouseMoved(event -> settingsHover(event.getX(), event.getY()));
        this.view.getSettingsCanvas().setOnMouseClicked(event -> settingsPress(event.getX(), event.getY()));
        this.view.getSettingsScene().setOnKeyPressed(event -> keyPress = event.getCode());
    }

    /**
     * Button hover on the main menu display.
     *
     * @param x position of the cursor
     * @param y position of the cursor
     */
    public void buttonHover(double x, double y) {
        this.view.titleDefault();
        if (x > 300 && x < 700 && y > 720 && y < 820) {
            this.view.hundredLineHover();
        } else if ((x > 600 && x < 800 && y > 620 && y < 720) ||
                (x > 700 && x < 900 && y > 720 && y < 820)) {
            this.view.freePlayHover();
        } else if ((x > 300 && x < 400 && y > 520 && y < 620) ||
                (x > 300 && x < 600 && y > 620 && y < 720)) {
            this.view.fortyLineHover();
        } else if ((x > 300 && x < 400 && y > 320 && y < 520) ||
                (x > 400 && x < 500 && y > 420 && y < 620)) {
            this.view.twentyLineHover();
        } else if ((x > 800 && x < 1100 && y > 620 && y < 720) ||
                (x > 900 && x < 1000 && y > 720 && y < 820)) {
            this.view.settingsHover();
        } else if ((x > 1000 && x < 1300 && y > 720 && y < 820) ||
                (x > 1200 && x < 1300 && y > 620 && y < 720)) {
            this.view.aboutHover();
        } else if (x > 1400 && x < 1600 && y > 620 && y < 820) {
            this.view.exitHover();
        }
    }

    /**
     * Mouse hover on the about display.
     *
     * @param x position of the cursor
     * @param y position of the cursor
     */
    public void aboutHover(double x, double y) {
        this.view.aboutDefault();
        if (x > 1400 && x < 1600 && y > 620 && y < 820) {
            this.view.backAboutHover();
        }
    }

    /**
     * Button press on the about display.
     *
     * @param x position of the cursor
     * @param y position of the cursor
     */
    public void aboutPress(double x, double y) {
        if (x > 1400 && x < 1600 && y > 620 && y < 820) {
            this.view.setMenuScene();
        }
    }

    /**
     * Mouse hover on the settings display.
     *
     * @param x position of the cursor
     * @param y position of the cursor
     */
    public void settingsHover(double x, double y) {
        if (!keyPressWait) {
            this.view.settingsDefault();
            if (x > 1400 && x < 1600 && y > 620 && y < 820) {
                this.view.backSettingsHover();
            } else if (x > 650 && x < 770 && y > 150 && y < 190) {
                this.view.moveLeftHover();
            } else if (x > 650 && x < 770 && y > 210 && y < 250) {
                this.view.moveRightHover();
            } else if (x > 650 && x < 770 && y > 270 && y < 310) {
                this.view.softDropHover();
            } else if (x > 650 && x < 770 && y > 330 && y < 370) {
                this.view.hardDropHover();
            } else if (x > 650 && x < 770 && y > 390 && y < 430) {
                this.view.holdPieceHover();
            } else if (x > 650 && x < 770 && y > 450 && y < 490) {
                this.view.clockwiseRotateHover();
            } else if (x > 650 && x < 770 && y > 510 && y < 550) {
                this.view.anticlockwiseRotateHover();
            } else if (x > 650 && x < 770 && y > 570 && y < 610) {
                this.view.flipClockwiseRotateHover();
            } else if (x > 650 && x < 770 && y > 630 && y < 670) {
                this.view.restartCodeHover();
            }
        }
    }

    /**
     * Changes the left move key.
     */
    public void setLeftMove() {
        keyPressWait = true;
        if (keyPress == null) {
            settingsTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    setLeftMove();
                }
            }, 50);
        } else {
            keyPressWait = false;
            this.gameLogic.setLeftMoveCode(keyPress);
            this.view.settingsDefault();
        }
    }

    /**
     * Changes the right move key.
     */
    public void setRightMove() {
        keyPressWait = true;
        if (keyPress == null) {
            settingsTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    setRightMove();
                }
            }, 50);
        } else {
            keyPressWait = false;
            this.gameLogic.setRightMoveCode(keyPress);
            this.view.settingsDefault();
        }
    }

    /**
     * Changes the soft drop key.
     */
    public void setSoftDrop() {
        keyPressWait = true;
        if (keyPress == null) {
            settingsTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    setSoftDrop();
                }
            }, 50);
        } else {
            keyPressWait = false;
            this.gameLogic.setSoftDropCode(keyPress);
            this.view.settingsDefault();
        }
    }

    /**
     * Changes the hard drop key.
     */
    public void setHardDrop() {
        keyPressWait = true;
        if (keyPress == null) {
            settingsTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    setHardDrop();
                }
            }, 50);
        } else {
            keyPressWait = false;
            this.gameLogic.setDropCode(keyPress);
            this.view.settingsDefault();
        }
    }

    /**
     * Changes the hold key.
     */
    public void setHoldPiece() {
        keyPressWait = true;
        if (keyPress == null) {
            settingsTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    setHoldPiece();
                }
            }, 50);
        } else {
            keyPressWait = false;
            this.gameLogic.setHoldCode(keyPress);
            this.view.settingsDefault();
        }
    }

    /**
     * Changes the clockwise rotate key.
     */
    public void setClockwiseRotate() {
        keyPressWait = true;
        if (keyPress == null) {
            settingsTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    setClockwiseRotate();
                }
            }, 50);
        } else {
            keyPressWait = false;
            this.gameLogic.setClockwiseRotateCode(keyPress);
            this.view.settingsDefault();
        }
    }

    /**
     * Changes the anticlockwise rotate key.
     */
    public void setAntiClockwiseRotate() {
        keyPressWait = true;
        if (keyPress == null) {
            settingsTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    setAntiClockwiseRotate();
                }
            }, 50);
        } else {
            keyPressWait = false;
            this.gameLogic.setAnticlockwiseRotateCode(keyPress);
            this.view.settingsDefault();
        }
    }

    /**
     * Changes the flip rotate key.
     */
    public void setFlipRotate() {
        keyPressWait = true;
        if (keyPress == null) {
            settingsTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    setFlipRotate();
                }
            }, 50);
        } else {
            keyPressWait = false;
            this.gameLogic.setFlipRotateCode(keyPress);
            this.view.settingsDefault();
        }
    }

    /**
     * Changes the restart key.
     */
    public void setRestartGame() {
        keyPressWait = true;
        if (keyPress == null) {
            settingsTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    setRestartGame();
                }
            }, 50);
        } else {
            keyPressWait = false;
            this.gameLogic.setRestartCode(keyPress);
            this.view.settingsDefault();
        }
    }

    /**
     * Mouse press on the settings display.
     *
     * @param x position of the cursor
     * @param y position of the cursor
     */
    public void settingsPress(double x, double y) {
        this.keyPress = null;
        if (x > 1400 && x < 1600 && y > 620 && y < 820) {
            this.view.setMenuScene();
        } else if (x > 650 && x < 770 && y > 150 && y < 190) {
            this.view.moveLeftHover();
            setLeftMove();
        } else if (x > 650 && x < 770 && y > 210 && y < 250) {
            this.view.moveRightHover();
            setRightMove();
        } else if (x > 650 && x < 770 && y > 270 && y < 310) {
            this.view.softDropHover();
            setSoftDrop();
        } else if (x > 650 && x < 770 && y > 330 && y < 370) {
            this.view.hardDropHover();
            setHardDrop();
        } else if (x > 650 && x < 770 && y > 390 && y < 430) {
            this.view.holdPieceHover();
            setHoldPiece();
        } else if (x > 650 && x < 770 && y > 450 && y < 490) {
            this.view.clockwiseRotateHover();
            setClockwiseRotate();
        } else if (x > 650 && x < 770 && y > 510 && y < 550) {
            this.view.anticlockwiseRotateHover();
            setAntiClockwiseRotate();
        } else if (x > 650 && x < 770 && y > 570 && y < 610) {
            this.view.flipClockwiseRotateHover();
            setFlipRotate();
        } else if (x > 650 && x < 770 && y > 630 && y < 670) {
            this.view.restartHover();
            setRestartGame();
        }
    }

    /**
     * Button press on the main menu display.
     *
     * @param x position of the cursor
     * @param y position of the cursor
     */
    public void buttonPressed(double x, double y) {
        if (x > 300 && x < 700 && y > 720 && y < 820) {
            gameMode = 100;
            startGame(gameMode);
        } else if ((x > 600 && x < 800 && y > 620 && y < 720) ||
                (x > 700 && x < 900 && y > 720 && y < 820)) {
//            startFreePlay();
        } else if ((x > 300 && x < 400 && y > 520 && y < 620) ||
                (x > 300 && x < 600 && y > 620 && y < 720)) {
            gameMode = 40;
            startGame(gameMode);
        } else if ((x > 300 && x < 400 && y > 320 && y < 520) ||
                (x > 400 && x < 500 && y > 420 && y < 620)) {
            gameMode = 5;
            startGame(gameMode);
        } else if ((x > 800 && x < 1100 && y > 620 && y < 720) ||
                (x > 900 && x < 1000 && y > 720 && y < 820)) {
            this.view.setSettingScene();
        } else if ((x > 1000 && x < 1300 && y > 720 && y < 820) ||
                (x > 1200 && x < 1300 && y > 620 && y < 720)) {
            this.view.setAboutScene();
        } else if (x > 1400 && x < 1600 && y > 620 && y < 820) {
            this.view.closeGame();
        }
    }

    /**
     * Button hover on the side menu.
     *
     * @param x position of the cursor
     * @param y position of the cursor
     */
    public void buttonHoverGame(double x, double y) {
        this.view.inGameMenuDefault();
        if (x > 20 && x < 120 && y > 570 && y < 610) {
            this.view.menuHover();
        } else if (x > 20 && x < 155 && y > 630 && y < 670) {
            this.view.restartHover();
        } else if (x > 20 && x < 170 && y > 690 && y < 730) {
            this.view.settingsGameHover();
        } else if (x > 20 && x < 90 && y > 750 && y < 790) {
            this.view.exitGameHover();
        }
    }

    /**
     * Button press on the side menu.
     *
     * @param x position of the cursor
     * @param y position of the cursor
     */
    public void buttonPressedGame(double x, double y) {
        if (x > 20 && x < 120 && y > 570 && y < 610) {
            this.view.setMenuScene();
        } else if (x > 20 && x < 155 && y > 630 && y < 670) {
            startGame(gameLogic.getLinesToClear());
        } else if (x > 20 && x < 170 && y > 690 && y < 730) {
            this.view.setSettingScene();
        } else if (x > 20 && x < 90 && y > 750 && y < 790) {
            this.view.closeGame();
        }
    }

    /**
     * Handles the left movement of the piece.
     */
    public void moveLeft() {
        if (gameLogic.getGameStarted() && !gameLogic.getGameEnd()) {
            gameLogic.moveLeft();
            updatePieceDisplay();
        }
    }

    /**
     * Handles the right movement of the piece.
     */
    public void moveRight() {
        if (gameLogic.getGameStarted() && !gameLogic.getGameEnd()) {
            gameLogic.moveRight();
            updatePieceDisplay();
        }
    }

    /**
     * Handles the soft drop movement of the piece.
     */
    public void softDrop() {
        if (gameLogic.getGameStarted() && !gameLogic.getGameEnd()) {
            gameLogic.softDrop();
            updatePieceDisplay();
        }
    }

    /**
     * Handles the automatic placement of the piece when soft dropping.
     */
    public void autoPlace() {
        if (gameLogic.getGameStarted() && !gameLogic.getGameEnd()) {
            if (gameLogic.ghostPieceDistance() == 0) {
                placePiece();
            }
            gameLogic.softDrop();
            updatePieceDisplay();
        }
    }


    /**
     * Handles the clockwise rotation of the piece.
     */
    public void clockwiseRotate() {
        if (gameLogic.getGameStarted() && !gameLogic.getGameEnd()) {
            gameLogic.clockwiseRotate();
            updatePieceDisplay();
        }
    }

    /**
     * Handles the anticlockwise rotation of the piece.
     */
    public void anticlockwiseRotate() {
        if (gameLogic.getGameStarted() && !gameLogic.getGameEnd()) {
            gameLogic.anticlockwiseRotate();
            updatePieceDisplay();
        }
    }

    /**
     * Handles the flip rotation of the piece.
     */
    public void flipRotate() {
        if (gameLogic.getGameStarted() && !gameLogic.getGameEnd()) {
            gameLogic.flipRotate();
            updatePieceDisplay();
        }
    }

    /**
     * Handles piece placement.
     */
    public void placePiece() {
        if (gameLogic.getGameStarted() && !gameLogic.getGameEnd()) {
            gameLogic.placePiece();
            view.updateProgressBar();
            view.updateQueueBar();
            view.updateHoldBar();
            //timedSoftDrop();
            updatePieceDisplay();
        }
    }

    /**
     * Handles a piece hold.
     */
    public void hold() {
        if (gameLogic.getGameStarted()) {
            gameLogic.holdPiece();
            updatePieceDisplay();
            view.updateHoldBar();
            view.updateQueueBar();
        }
    }

    /**
     * Updates the display after a change has been made.
     */
    public void updatePieceDisplay() {
        view.resetCanvas();
        view.displayCurrentPiece();
        if (gameLogic.getGameEnd()) {
            view.displayGameEnd();
            view.updateStats();
        }
    }

    /**
     * Constantly checks for updates.
     */
    public void updates() {
        if (gameLogic.getGameStarted() && !gameLogic.getGameEnd()) {
            view.updateStats();
            long currentTime = System.currentTimeMillis();
            if (((currentTime - lastRight) > 175 && rightMove) || (currentTime - lastRight) > 20 && rightMoveDAS) {
                moveRight();
                lastRight = currentTime;
                rightMoveDAS = true;
                rightCount = 2;
            }

            if (rightCount == 1) {
                moveRight();
                lastRight = currentTime;
                rightCount++;
            }

            currentTime = System.currentTimeMillis();
            if (((currentTime - lastLeft) > 175 && leftMove) || (currentTime - lastLeft) > 20 && leftMoveDAS) {
                moveLeft();
                lastLeft = currentTime;
                leftMoveDAS = true;
                leftCount = 2;
            }

            if (leftCount == 1) {
                moveLeft();
                lastLeft = currentTime;
                leftCount++;
            }

            currentTime = System.currentTimeMillis();
            if (((currentTime - lastSoftDrop) > 175 && softDrop) || (currentTime - lastSoftDrop) > 20 && softDropDAS) {
                softDrop();
                lastSoftDrop = currentTime;
                softDropDAS = true;
                softDropCount = 2;
            }

            currentTime = System.currentTimeMillis();
            if (currentTime - lastAutoDrop > 1000) {
                autoPlace();
                lastAutoDrop = currentTime;
            }

            if (softDropCount == 1) {
                softDrop();
                lastSoftDrop = currentTime;
                softDropCount++;
            }

            if (placePiece) {
                placePiece();
                placePiece = false;
            }

            if (holdPiece) {
                hold();
                holdPiece = false;
            }

            if (clockwiseRotate) {
                clockwiseRotate();
                clockwiseRotate = false;
            }

            if (anticlockwiseRotate) {
                anticlockwiseRotate();
                anticlockwiseRotate = false;
            }

            if (flipRotate) {
                flipRotate();
                flipRotate = false;
            }
            if (restartGame) {
                restartGame = false;
                startGame(gameLogic.getLinesToClear());
            }
            updateTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    updates();
                }
            }, 4);
        }
    }

    public void firstCountDown(int count) {
        if (count < 40) {
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    firstCountDown(loopCount++);
                }
            }, 20);
            this.view.createCountdown();
        }
    }

    public void secondCountDown(int count) {
        if (count < 40) {
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    secondCountDown(loopCount++);
                }
            }, 20);
            this.view.createCountdown();
        }
    }

    public void thirdCountDown(int count) {
        if (count < 40) {
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    thirdCountDown(loopCount++);
                }
            }, 20);
            this.view.createCountdown();
        } else {
            this.view.resetCanvas();
        }
    }
}
