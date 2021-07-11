package Logic;

import Pieces.*;
import Audio.Audio;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Logic {

    /**
     * Number of columns of the display grid.
     */
    public final int COLUMNS = 10;

    /**
     * Number of rows of the display grid.
     */
    public final int ROWS = 20;

    /**
     * Piece start column.
     */
    public final int START_COLUMN = 4;

    /**
     * Piece start row.
     */
    public final int START_ROW = -2;

    /**
     * Length of the piece queue.
     */
    public final int QUEUE_LENGTH = 10;

    /**
     * Instance of the audio class to make sounds.
     */
    private Audio audio;

    /**
     * Current piece.
     */
    private Piece currentPiece;

    /**
     * Hold piece.
     */
    private Piece holdPiece;

    /**
     * Checks for hold available.
     */
    private boolean holdCheck;

    /**
     * Row of the current piece.
     */
    private int row;

    /**
     * Column of the current piece.
     */
    private int column;

    /**
     * Blocks that need to be displayed.
     */
    private Map<Coordinates, Color> occupiedBlocks;

    /**
     * List of the pieces in the queue.
     */
    private List<Piece> pieceQueue;

    /**
     * Check if the game has ended.
     */
    private boolean gameEnd;

    /**
     * Check if the game has started.
     */
    private boolean gameStart;

    /**
     * Number of lines required to clear.
     */
    private int linesToClear;

    /**
     * Number of lines cleared.
     */
    private int linesCleared;

    /**
     * Start time of the game.
     */
    private long startTime;

    /**
     * Number of pieces placed.
     */
    private int piecesPlaced;

    /**
     * Left Move Key Code.
     */
    private KeyCode leftMoveCode;

    /**
     * Right Move Key Code.
     */
    private KeyCode rightMoveCode;

    /**
     * Soft Drop Key Code.
     */
    private KeyCode softDropCode;

    /**
     * Hold Key Code.
     */
    private KeyCode holdCode;

    /**
     * Place Key Code.
     */
    private KeyCode dropCode;

    /**
     * Clockwise Rotate Key Code.
     */
    private KeyCode clockwiseRotateCode;

    /**
     * Anticlockwise Rotate Key Code.
     */
    private KeyCode anticlockwiseRotateCode;

    /**
     * Flip Rotate Key Code.
     */
    private KeyCode flipRotateCode;

    /**
     * Restart Key Code.
     */
    private KeyCode restartCode;

    /**
     * Constructor of a new instance of the logic class.
     */
    public Logic() {
        this.audio = new Audio();
        initialiseNewGame();

        this.leftMoveCode = KeyCode.LEFT;
        this.rightMoveCode = KeyCode.RIGHT;
        this.softDropCode = KeyCode.DOWN;
        this.holdCode = KeyCode.SHIFT;
        this.dropCode = KeyCode.SPACE;
        this.clockwiseRotateCode = KeyCode.UP;
        this.anticlockwiseRotateCode = KeyCode.Z;
        this.flipRotateCode = KeyCode.X;
        this.restartCode = KeyCode.F4;
    }

    /**
     * Initialises a new game and resets everything to default.
     */
    public void initialiseNewGame() {

        this.occupiedBlocks = new HashMap<Coordinates, Color>();
        this.pieceQueue = new ArrayList<>();
        for (int numPieces = 0; numPieces < QUEUE_LENGTH; numPieces++) {
            pieceQueue.add(generatePiece());
        }
        this.getNextPiece();
        this.currentPiece.resetState();

        this.holdCheck = false;
        this.gameEnd = false;
        this.gameStart = false;
        this.linesCleared = 0;
        this.piecesPlaced = 0;
        this.holdPiece = null;
    }

    /**
     * Pops the first piece from the queue and sets it to the current piece.
     */
    public void getNextPiece() {
        this.pieceQueue.add(generatePiece());
        this.currentPiece = this.pieceQueue.get(0);
        this.currentPiece.resetState();
        this.pieceQueue.remove(0);
        this.row = START_ROW;
        this.column = START_COLUMN;
    }

    /**
     * Generates a random piece to place into the queue, if the piece appears too many times
     * a new piece will be found instead.
     *
     * @return Piece to add to the queue
     */
    public Piece generatePiece() {
        int pieceNumber = ThreadLocalRandom.current().nextInt(0, 7);
        Piece returnPiece = null;
        switch (pieceNumber) {
            case 0:
                returnPiece = new IPiece();
                break;
            case 1:
                returnPiece = new JPiece();
                break;
            case 2:
                returnPiece = new LPiece();
                break;
            case 3:
                returnPiece = new OPiece();
                break;
            case 4:
                returnPiece = new ZPiece();
                break;
            case 5:
                returnPiece = new SPiece();
                break;
            case 6:
                returnPiece = new TPiece();
                break;
        }

        int pieceCount = 0;
        for (Piece queuePiece : pieceQueue) {
            if (returnPiece.equals(queuePiece)) {
                pieceCount++;
            }
        }
        if (pieceCount > 1) {
            return generatePiece();
        }
        return returnPiece;
    }

    /**
     * Starts the game depending on the lines to clear as a parameter.
     *
     * @param linesToClear number of lines needed to clear
     */
    public void startGame(int linesToClear) {
        initialiseNewGame();
        if (linesToClear == 0) {
        } else {
            this.linesToClear = linesToClear;
        }
    }

    /**
     * Sets the game status to started.
     */
    public void setGameStart() {
        this.gameStart = true;
    }

    /**
     * Sets the game status to started.
     */
    public void setNoGame() {
        this.gameStart = false;
        this.gameEnd = false;
    }

    /**
     * Returns whether the game has started yet.
     *
     * @return Game started yet
     */
    public boolean getGameStart() {
        return this.gameStart;
    }

    /**
     * Returns the current piece the player controls.
     *
     * @return Current Piece
     */
    public Piece getCurrentPiece() {
        return this.currentPiece;
    }

    /**
     * Returns a List of all the positions of the current piece relative to the grid.
     *
     * @return List of positions
     */
    public List<Coordinates> getCurrentPiecePositions() {
        List<Coordinates> coordinates = new ArrayList<>();
        for (Coordinates pieceCoordinates : currentPiece.getCoordinates()) {
            coordinates.add(new Coordinates(pieceCoordinates.getRow() + this.row,
                    pieceCoordinates.getColumn() + this.column));
        }
        return coordinates;
    }

    /**
     * Returns all the existing coordinates as a list.
     *
     * @return List of coordinates
     */
    public List<Coordinates> getExistingCoordinates() {
        List<Coordinates> coordinates = new ArrayList<>();
        for (Map.Entry<Coordinates, Color> blocks : occupiedBlocks.entrySet()) {
            coordinates.add(blocks.getKey());
        }
        return coordinates;
    }

    /**
     * Performed when a piece has been placed.
     */
    public void placePiece() {

        this.holdCheck = false;

        int dropDistanceFinal = ghostPieceDistance();

        for (Coordinates pieceCoordinates : getCurrentPiecePositions()) {
            occupiedBlocks.put(new Coordinates(pieceCoordinates.getRow() + dropDistanceFinal,
                    pieceCoordinates.getColumn()), currentPiece.getColour());
        }

        this.audio.placePiece();
        getNextPiece();
        clearLines();
        piecesPlaced++;
        if (!moveCheck(getCurrentPiecePositions(), 0, 0)) {
            gameEnd = true;
            gameStart = false;
        }

        if (linesToClear - linesCleared <= 0) {
            this.gameEnd = true;
            this.gameStart = false;
        }
    }

    /**
     * Checks if it is possible to clear any lines.
     */
    public void clearLines() {
        List<Coordinates> existingCoordinates = getExistingCoordinates();
        for (int i = 0; i < ROWS; i++) {
            int count = 0;
            for (Coordinates coordinates : existingCoordinates) {
                if (coordinates.getRow() == i) {
                    count++;
                }
            }
            if (count >= COLUMNS) {
                for (int col = 0; col < COLUMNS; col++) {
                    for (Coordinates coordinates : existingCoordinates) {
                        if (coordinates.equals(new Coordinates(i, col))) {
                            this.occupiedBlocks.remove(coordinates);
                        }
                    }
                }
                for (Coordinates coordinates : existingCoordinates) {
                    if (coordinates.getRow() < i) {
                        coordinates.increaseRow();
                    }
                }
                linesCleared++;
            }
        }
    }

    /**
     * Checks to see if it is possible to move the piece to the given position.
     *
     * @param checkPositions positions to check for collisions
     * @param row row change to the new position
     * @param column column change to the new position
     * @return if the move is possible
     */
    public boolean moveCheck(List<Coordinates> checkPositions, int row, int column) {
        for (Coordinates coordinates : checkPositions) {
            if ((coordinates.getRow()) + row > ROWS - 1 || coordinates.getColumn() + column < 0 ||
                    coordinates.getColumn() + column > 9) {
                return false;
            }
            for (Coordinates existingCoordinates : getExistingCoordinates()) {
                if (new Coordinates(coordinates.getRow() + row,
                        coordinates.getColumn() + column).equals(existingCoordinates)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Checks if it is possible to move the current piece to the right.
     * If possible, the piece will be moved.
     */
    public void moveRight() {
        if (moveCheck(getCurrentPiecePositions(), 0, 1)) {
            this.column++;
        }
    }

    /**
     * Checks if it is possible to move the current piece to the left.
     * If possible, the piece will be moved.
     */
    public void moveLeft() {
        if (moveCheck(getCurrentPiecePositions(), 0, -1)) {
            this.column--;
        }
    }

    /**
     * Checks if it is possible to move the current piece down.
     * If possible, the piece will be moved.
     */
    public void softDrop() {
        if (moveCheck(getCurrentPiecePositions(), 1, 0)) {
            this.row++;
        }
    }

    /**
     * Attempts to hold the current piece and swap it for the piece currently
     * being held.
     */
    public void holdPiece() {
        if (!holdCheck) {
            this.currentPiece.resetState();
            Piece temp = this.currentPiece;
            if (this.holdPiece != null) {
                this.currentPiece = this.holdPiece;
                this.holdPiece = temp;
            } else {
                this.holdPiece = currentPiece;
                getNextPiece();
            }
            this.row = START_ROW;
            this.column = START_COLUMN;
            this.holdCheck = true;
        }
    }

    /**
     * Order of the positions to check for a left rotate.
     *
     * @param checkPositions possible positions to check
     * @return if the move is possible
     */
    public boolean possibleAnticlockwiseRotateMoves(List<Coordinates> checkPositions) {
        if (moveCheck(checkPositions, 1, 1)) {
            this.column++;
            this.row++;
            return true;
        } else if (moveCheck(checkPositions, 1, -1)) {
            this.column--;
            this.row++;
            return true;
        } else if (moveCheck(checkPositions, -1, 0)) {
            this.row--;
            return true;
        } else if (moveCheck(checkPositions, 0, -1)) {
            this.column--;
            return true;
        } else if (moveCheck(checkPositions, 0, 1)) {
            this.column++;
            return true;
        } else if (moveCheck(checkPositions, 1, 0)) {
            this.row++;
            return true;
        } else if (moveCheck(checkPositions, -2, 0)) {
            this.row -= 2;
            return true;
        } else if (moveCheck(checkPositions, 0, -2)) {
            this.column -= 2;
            return true;
        } else if (moveCheck(checkPositions, 0, 2)) {
            this.column += 2;
            return true;
        } else if (moveCheck(checkPositions, -1, 1)) {
            this.column++;
            this.row--;
            return true;
        } else if (moveCheck(checkPositions, -1, -1)) {
            this.column--;
            this.row--;
            return true;
        }
        return false;
    }

    /**
     * Checks if it is possible to rotate the piece without any collisions with the
     * boundaries or existing pieces.
     *
     * @return if a move is possible
     */
    public boolean anticlockwiseRotateCheck() {
        List<Coordinates> preview = new ArrayList<>();
        for (Coordinates pieceCoordinates : currentPiece.anticlockwiseRotatePreview()) {
            preview.add(new Coordinates(pieceCoordinates.getRow() + this.row,
                    pieceCoordinates.getColumn() + this.column));
        }

        for (Coordinates coordinates : preview) {
            if ((coordinates.getRow() + 1) > ROWS || coordinates.getColumn() < 0
                    || coordinates.getColumn() > COLUMNS - 1) {
                return possibleClockwiseRotateMoves(preview);
            }
            for (Coordinates existingCoordinates : getExistingCoordinates()) {
                if (new Coordinates(coordinates.getRow(),
                        coordinates.getColumn()).equals(existingCoordinates)) {
                    return possibleClockwiseRotateMoves(preview);
                }
            }
        }
        return true;
    }

    /**
     * Attempts to rotate the piece.
     */
    public void anticlockwiseRotate() {
        if (anticlockwiseRotateCheck()) {
            this.currentPiece.anticlockwiseRotate();
        }
    }

    /**
     * Order of the positions to check for a right rotate.
     *
     * @param checkPositions possible positions to check
     * @return if the move is possible
     */
    public boolean possibleClockwiseRotateMoves(List<Coordinates> checkPositions) {
        if (moveCheck(checkPositions, 1, -1)) {
            this.column--;
            this.row++;
            return true;
        } else if (moveCheck(checkPositions, 1, 1)) {
            this.column++;
            this.row++;
            return true;
        } else if (moveCheck(checkPositions, -1, 0)) {
            this.row--;
            return true;
        } else if (moveCheck(checkPositions, 0, -1)) {
            this.column--;
            return true;
        } else if (moveCheck(checkPositions, 0, 1)) {
            this.column++;
            return true;
        } else if (moveCheck(checkPositions, 1, 0)) {
            this.row++;
            return true;
        } else if (moveCheck(checkPositions, -2, 0)) {
            this.row -= 2;
            return true;
        } else if (moveCheck(checkPositions, 0, -2)) {
            this.column -= 2;
            return true;
        } else if (moveCheck(checkPositions, 0, 2)) {
            this.column += 2;
            return true;
        } else if (moveCheck(checkPositions, -1, 1)) {
            this.column++;
            this.row--;
            return true;
        } else if (moveCheck(checkPositions, -1, -1)) {
            this.column--;
            this.row--;
            return true;
        }
        return false;
    }

    /**
     * Checks if it is possible to rotate the piece without any collisions with the
     * boundaries or existing pieces.
     *
     * @return if a move is possible
     */
    public boolean clockwiseRotateCheck() {
        List<Coordinates> preview = new ArrayList<>();
        for (Coordinates pieceCoordinates : currentPiece.clockwiseRotatePreview()) {
            preview.add(new Coordinates(pieceCoordinates.getRow() + this.row,
                    pieceCoordinates.getColumn() + this.column));
        }

        for (Coordinates coordinates : preview) {
            if ((coordinates.getRow() + 1) > ROWS || coordinates.getColumn() < 0
                    || coordinates.getColumn() > COLUMNS - 1) {
                return possibleClockwiseRotateMoves(preview);
            }
            for (Coordinates existingCoordinates : getExistingCoordinates()) {
                if (new Coordinates(coordinates.getRow(),
                        coordinates.getColumn()).equals(existingCoordinates)) {
                    return possibleClockwiseRotateMoves(preview);
                }
            }
        }
        return true;
    }

    /**
     * Attempts to rotate the piece.
     */
    public void clockwiseRotate() {
        if (clockwiseRotateCheck()) {
            this.currentPiece.clockwiseRotate();
        }
    }

    /**
     * Order of the positions to check for a flip rotate.
     *
     * @param checkPositions possible positions to check
     * @return if the move is possible
     */
    public boolean possibleFlipRotateMoves(List<Coordinates> checkPositions) {
         if (moveCheck(checkPositions, -1, 0)) {
            this.row--;
            return true;
        } else if (moveCheck(checkPositions, 0, -1)) {
            this.column--;
            return true;
        } else if (moveCheck(checkPositions, 0, 1)) {
            this.column++;
            return true;
        } else if (moveCheck(checkPositions, 1, 0)) {
            this.row++;
            return true;
        } else if (moveCheck(checkPositions, -2, 0)) {
            this.row -= 2;
            return true;
        } else if (moveCheck(checkPositions, 0, -2)) {
            this.column -= 2;
            return true;
        } else if (moveCheck(checkPositions, 0, 2)) {
            this.column += 2;
            return true;
        } else if (moveCheck(checkPositions, -1, 1)) {
            this.column++;
            this.row--;
            return true;
        } else if (moveCheck(checkPositions, -1, -1)) {
            this.column--;
            this.row--;
            return true;
        } else if (moveCheck(checkPositions, 1, -1)) {
            this.column--;
            this.row++;
            return true;
        } else if (moveCheck(checkPositions, 1, 1)) {
            this.column++;
            this.row++;
            return true;
        }
        return false;
    }

    /**
     * Checks if it is possible to rotate the piece without any collisions with the
     * boundaries or existing pieces.
     *
     * @return if a move is possible
     */
    public boolean flipRotateCheck() {
        List<Coordinates> preview = new ArrayList<>();
        for (Coordinates pieceCoordinates : currentPiece.flipRotatePreview()) {
            preview.add(new Coordinates(pieceCoordinates.getRow() + this.row,
                    pieceCoordinates.getColumn() + this.column));
        }

        for (Coordinates coordinates : preview) {
            if ((coordinates.getRow() + 1) > ROWS - 1 || coordinates.getColumn() < 0
                    || coordinates.getColumn() > COLUMNS - 1) {
                return possibleFlipRotateMoves(preview);
            }
            for (Coordinates existingCoordinates : getExistingCoordinates()) {
                if (new Coordinates(coordinates.getRow(),
                        coordinates.getColumn()).equals(existingCoordinates)) {
                    return possibleFlipRotateMoves(preview);
                }
            }
        }
        return true;
    }

    /**
     * Attempts to rotate the piece.
     */
    public void flipRotate() {
        if (flipRotateCheck()) {
            this.currentPiece.flipRotate();
        }
    }

    /**
     * Returns the shortest distance for a collision in the vertical distance,
     * this can be used to place the piece and create the ghost piece.
     *
     * @return number of rows to drop
     */
    public int ghostPieceDistance() {

        List<Coordinates> coordinates = getExistingCoordinates();

        boolean equalityCheck;
        int dropDistanceFinal = 22;
        int dropDistance;

        for (Coordinates pieceCoordinates : getCurrentPiecePositions()) {
            dropDistance = 0;
            equalityCheck = false;
            while (dropDistance < dropDistanceFinal) {
                for (Coordinates existingCoordinates : coordinates) {
                    if (new Coordinates(pieceCoordinates.getRow() + dropDistance,
                            pieceCoordinates.getColumn()).equals(existingCoordinates)) {
                        equalityCheck = true;
                    }
                }
                if (pieceCoordinates.getRow() + dropDistance > ROWS - 1) {
                    equalityCheck = true;
                }

                dropDistance++;
                if (equalityCheck) {
                    break;
                }
            }
            if (dropDistance < dropDistanceFinal) {
                dropDistanceFinal = dropDistance;
            }
        }
        dropDistanceFinal -= 2;
        return dropDistanceFinal;
    }

    /**
     * Returns if the game has ended or not.
     *
     * @return game ended
     */
    public boolean getGameEnd() {
        return this.gameEnd;
    }

    /**
     * Returns if the game has ended or not.
     *
     * @return game ended
     */
    public boolean getGameStarted() {
        return this.gameStart;
    }

    /**
     * Returns the number of lines needed to clear.
     *
     * @return lines to clear
     */
    public int getLinesToClear() {
        return this.linesToClear;
    }

    /**
     * Returns the piece that is currently being held.
     *
     * @return
     */
    public Piece getHoldPiece() {
        return this.holdPiece;
    }

    /**
     * Returns if a hold is available.
     *
     * @return if a piece can be held
     */
    public boolean isHoldCheck() {
        return this.holdCheck;
    }

    /**
     * Returns the queue.
     *
     * @return piece queue
     */
    public List<Piece> getQueue() {
        return this.pieceQueue;
    }

    /**
     * Returns the number of lines cleared.
     *
     * @return number of lines cleared
     */
    public int getLinesCleared() {
        return this.linesCleared;
    }

    /**
     * Returns the number of pieces placed.
     *
     * @return number of pieces placed
     */
    public int piecesPlaced() {
        return this.piecesPlaced;
    }

    /**
     * Sets the start time of the game.
     */
    public void setStartTime() {
        this.startTime = System.currentTimeMillis();
    }

    /**
     * Returns the time elapsed since the start of the game.
     *
     * @return time elapsed
     */
    public double getTime() {
        double time = (System.currentTimeMillis() - (double)startTime) / 1000;
        return time;
    }

    /**
     * Returns a map of all the existing blocks to be placed on the grid.
     */
    public Map<Coordinates, Color> getOccupiedBlocks() {
        return this.occupiedBlocks;
    }

    /**
     * Returns the time since game start in list format.
     */
    public List<Long> getTimes() {
        if (gameStart || gameEnd) {
            List<Long> times = new ArrayList<>();
            long time = System.currentTimeMillis() - startTime;
            times.add((time / 10) % 10);
            times.add((time / 100) % 10);
            times.add((time / 1000) % 10);
            times.add((time / 10000) % 6);
            times.add((time / 60000));
            return times;
        } else {
            long initial = 0;
            List<Long> times = new ArrayList<>();
            times.add(initial);
            times.add(initial);
            times.add(initial);
            times.add(initial);
            times.add(initial);
            return times;
        }
    }

    /**
     * Returns the Move Left Keycode.
     */
    public KeyCode getLeftMoveCode() {
        return this.leftMoveCode;
    }

    /**
     * Sets the Move Left Keycode.
     *
     * @param keyCode left move
     */
    public void setLeftMoveCode(KeyCode keyCode) {
        this.leftMoveCode = keyCode;
    }

    /**
     * Returns the Move Right Keycode.
     */
    public KeyCode getRightMoveCode() {
        return this.rightMoveCode;
    }

    /**
     * Sets the Move Right Keycode.
     *
     * @param keyCode right move
     */
    public void setRightMoveCode(KeyCode keyCode) {
        this.rightMoveCode = keyCode;
    }

    /**
     * Returns the Soft Drop Keycode.
     */
    public KeyCode getSoftDropCode() {
        return this.softDropCode;
    }

    /**
     * Sets the Soft Drop Keycode.
     *
     * @param keyCode soft drop
     */
    public void setSoftDropCode(KeyCode keyCode) {
        this.softDropCode = keyCode;
    }

    /**
     * Returns the Hold Keycode.
     */
    public KeyCode getHoldCode() {
        return this.holdCode;
    }

    /**
     * Sets the Hold Keycode.
     *
     * @param keyCode hold code
     */
    public void setHoldCode(KeyCode keyCode) {
        this.holdCode = keyCode;
    }

    /**
     * Returns the Drop Keycode.
     */
    public KeyCode getDropCode() {
        return this.dropCode;
    }

    /**
     * Sets the Drop Keycode.
     *
     * @param keyCode drop code
     */
    public void setDropCode(KeyCode keyCode) {
        this.dropCode = keyCode;
    }

    /**
     * Returns the Clockwise Rotate Keycode.
     */
    public KeyCode getClockwiseRotateCode() {
        return this.clockwiseRotateCode;
    }

    /**
     * Sets the Clockwise Rotate Keycode.
     *
     * @param keyCode clockwise rotate
     */
    public void setClockwiseRotateCode(KeyCode keyCode) {
        this.clockwiseRotateCode = keyCode;
    }

    /**
     * Returns the Anticlockwise Rotate Keycode.
     */
    public KeyCode getAnticlockwiseRotateCode() {
        return this.anticlockwiseRotateCode;
    }

    /**
     * Sets the Anticlockwise Rotate Keycode.
     *
     * @param keyCode anticlockwise rotate
     */
    public void setAnticlockwiseRotateCode(KeyCode keyCode) {
        this.anticlockwiseRotateCode = keyCode;
    }

    /**
     * Returns the Flip Rotate Keycode.
     */
    public KeyCode getFlipRotateCode() {
        return this.flipRotateCode;
    }

    /**
     * Sets the Flip Rotate Keycode.
     *
     * @param keyCode flip rotate
     */
    public void setFlipRotateCode(KeyCode keyCode) {
        this.flipRotateCode = keyCode;
    }

    /**
     * Returns the Restart Keycode.
     */
    public KeyCode getRestartCode() {
        return this.restartCode;
    }

    /**
     * Sets the Restart Keycode.
     *
     * @param keyCode restart
     */
    public void setRestartCode(KeyCode keyCode) {
        this.restartCode = keyCode;
    }
}
