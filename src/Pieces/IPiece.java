package Pieces;

import javafx.scene.paint.Color;

public class IPiece extends Piece {

    public IPiece() {
        super(Color.rgb(55, 157, 212),
                Color.rgb(24, 78, 105), false, false);

        this.stateZero.add(new Coordinates(1, 0));
        this.stateZero.add(new Coordinates(1, 1));
        this.stateZero.add(new Coordinates(1, 2));
        this.stateZero.add(new Coordinates(1, 3));

        this.stateOne.add(new Coordinates(0, 2));
        this.stateOne.add(new Coordinates(1, 2));
        this.stateOne.add(new Coordinates(2, 2));
        this.stateOne.add(new Coordinates(3, 2));

        this.stateTwo.add(new Coordinates(2, 0));
        this.stateTwo.add(new Coordinates(2, 1));
        this.stateTwo.add(new Coordinates(2, 2));
        this.stateTwo.add(new Coordinates(2, 3));

        this.stateThree.add(new Coordinates(0, 1));
        this.stateThree.add(new Coordinates(1, 1));
        this.stateThree.add(new Coordinates(2, 1));
        this.stateThree.add(new Coordinates(3, 1));

        this.currentState = 0;
    }
}
