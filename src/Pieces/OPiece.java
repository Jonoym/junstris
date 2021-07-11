package Pieces;

import javafx.scene.paint.Color;

public class OPiece extends Piece {

    public OPiece() {
        super(Color.rgb(222, 156, 43),
                Color.rgb(110, 78, 17), false, true);

        this.stateZero.add(new Coordinates(0, 1));
        this.stateZero.add(new Coordinates(1, 1));
        this.stateZero.add(new Coordinates(0, 2));
        this.stateZero.add(new Coordinates(1, 2));

        this.stateOne.add(new Coordinates(0, 1));
        this.stateOne.add(new Coordinates(1, 1));
        this.stateOne.add(new Coordinates(0, 2));
        this.stateOne.add(new Coordinates(1, 2));

        this.stateTwo.add(new Coordinates(0, 1));
        this.stateTwo.add(new Coordinates(1, 1));
        this.stateTwo.add(new Coordinates(0, 2));
        this.stateTwo.add(new Coordinates(1, 2));

        this.stateThree.add(new Coordinates(0, 1));
        this.stateThree.add(new Coordinates(1, 1));
        this.stateThree.add(new Coordinates(0, 2));
        this.stateThree.add(new Coordinates(1, 2));


        this.currentState = 0;
    }
}
