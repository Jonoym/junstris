package Pieces;

public class Coordinates {

    private int row;

    private int column;

    public Coordinates(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public int getRow() {
        return this.row;
    }

    public int getColumn() {
        return this.column;
    }

    public void increaseRow() {
        this.row++;
    }

    public Coordinates addCoordinates(Coordinates other) {
        return new Coordinates(this.row + other.row,
                this.column + other.column);
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Coordinates)) {
            return false;
        }
        Coordinates otherCoordinate = (Coordinates) object;
        return this.row == otherCoordinate.getRow()
                && this.column == otherCoordinate.getColumn();
    }

    @Override
    public String toString() {
        return "(" + row + ", " + column + ")";
    }
}
