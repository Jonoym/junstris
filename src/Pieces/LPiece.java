package Pieces;

import javafx.scene.paint.Color;

public class LPiece extends Piece {

    public LPiece() {
        super(Color.rgb(220, 80, 30),
                Color.rgb(110, 40, 10), true, false);

        this.stateZero.add(new Coordinates(0, 2));
        this.stateZero.add(new Coordinates(1, 0));
        this.stateZero.add(new Coordinates(1, 1));
        this.stateZero.add(new Coordinates(1, 2));

        this.stateOne.add(new Coordinates(0, 1));
        this.stateOne.add(new Coordinates(1, 1));
        this.stateOne.add(new Coordinates(2, 1));
        this.stateOne.add(new Coordinates(2, 2));

        this.stateTwo.add(new Coordinates(1, 0));
        this.stateTwo.add(new Coordinates(1, 1));
        this.stateTwo.add(new Coordinates(1, 2));
        this.stateTwo.add(new Coordinates(2, 0));

        this.stateThree.add(new Coordinates(0, 0));
        this.stateThree.add(new Coordinates(0, 1));
        this.stateThree.add(new Coordinates(1, 1));
        this.stateThree.add(new Coordinates(2, 1));

        this.currentState = 0;
    }
}

