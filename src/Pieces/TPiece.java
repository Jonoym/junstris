package Pieces;

import javafx.scene.paint.Color;

public class TPiece extends Piece {

    public TPiece() {
        super(Color.rgb(171, 20, 136),
                Color.rgb(85, 12, 68), true, false);

        this.stateZero.add(new Coordinates(0, 1));
        this.stateZero.add(new Coordinates(1, 0));
        this.stateZero.add(new Coordinates(1, 1));
        this.stateZero.add(new Coordinates(1, 2));

        this.stateOne.add(new Coordinates(0, 1));
        this.stateOne.add(new Coordinates(1, 1));
        this.stateOne.add(new Coordinates(1, 2));
        this.stateOne.add(new Coordinates(2, 1));

        this.stateTwo.add(new Coordinates(1, 0));
        this.stateTwo.add(new Coordinates(1, 1));
        this.stateTwo.add(new Coordinates(1, 2));
        this.stateTwo.add(new Coordinates(2, 1));

        this.stateThree.add(new Coordinates(0, 1));
        this.stateThree.add(new Coordinates(1, 0));
        this.stateThree.add(new Coordinates(1, 1));
        this.stateThree.add(new Coordinates(2, 1));

        this.currentState = 0;
    }
}
