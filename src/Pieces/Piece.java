package Pieces;

import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.List;

public abstract class Piece {

    private Color colour;

    private Color ghostColour;

    private boolean horizontalShift;

    private boolean verticalShift;

    protected int currentState;

    protected List<Coordinates> stateZero = new ArrayList<>();

    protected List<Coordinates> stateOne = new ArrayList<>();

    protected List<Coordinates> stateTwo = new ArrayList<>();

    protected List<Coordinates> stateThree = new ArrayList<>();

    public Piece(Color colour, Color ghostColour, boolean horizontalShift, boolean verticalShift) {
        this.colour = colour;
        this.ghostColour = ghostColour;
        this.horizontalShift = horizontalShift;
        this.verticalShift = verticalShift;
    }

    public void clockwiseRotate() {
        this.currentState = (this.currentState + 1) % 4;
    }

    public void anticlockwiseRotate() {
        this.currentState = (this.currentState + 3) % 4;
    }

    public void flipRotate() {
        this.currentState = (this.currentState + 2) % 4;
    }

    public List<Coordinates> getCoordinates() {
        switch (this.currentState) {
            case 0:
                return this.stateZero;
            case 1:
                return this.stateOne;
            case 2:
                return this.stateTwo;
            case 3:
                return this.stateThree;
        }
        return null;
    }

    public List<Coordinates> clockwiseRotatePreview() {
        switch ((this.currentState + 1) % 4) {
            case 0:
                return this.stateZero;
            case 1:
                return this.stateOne;
            case 2:
                return this.stateTwo;
            case 3:
                return this.stateThree;
        }
        return null;
    }

    public List<Coordinates> anticlockwiseRotatePreview() {
        switch ((this.currentState + 3) % 4) {
            case 0:
                return this.stateZero;
            case 1:
                return this.stateOne;
            case 2:
                return this.stateTwo;
            case 3:
                return this.stateThree;
        }
        return null;
    }

    public List<Coordinates> flipRotatePreview() {
        switch ((this.currentState + 2) % 4) {
            case 0:
                return this.stateZero;
            case 1:
                return this.stateOne;
            case 2:
                return this.stateTwo;
            case 3:
                return this.stateThree;
        }
        return null;
    }

    public Color getColour() {
        return this.colour;
    }

    public Color getGhostColour() {
        return this.ghostColour;
    }

    public void resetState() {
        this.currentState = 0;
    }

    public boolean shiftRequired() {
        return this.horizontalShift;
    }

    public boolean verticalShift() {
        return this.verticalShift;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Piece)) {
            return false;
        }
        Piece otherPiece = (Piece) object;
        return this.getClass() == otherPiece.getClass();
    }

}
