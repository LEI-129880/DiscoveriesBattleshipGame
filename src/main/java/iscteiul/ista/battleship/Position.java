package iscteiul.ista.battleship;

import java.util.Objects;

public class Position implements IPosition {

    private int row;
    private int column;
    private boolean isOccupied;
    private boolean isHit;

    public Position(int row, int column) {
        this.row = row;
        this.column = column;
        this.isOccupied = false;
        this.isHit = false;
    }

    @Override
    public int getRow() {
        return row;
    }

    @Override
    public int getColumn() {
        return column;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, column);
    }

    @Override
    public boolean equals(Object otherPosition) {
        if (this == otherPosition) {
            return true;
        }

        if (otherPosition instanceof IPosition) {
            IPosition other = (IPosition) otherPosition;

            return this.getRow() == other.getRow()
                    && this.getColumn() == other.getColumn();
        }

        return false;
    }

    @Override
    public boolean isAdjacentTo(IPosition other) {
        return Math.abs(this.getRow() - other.getRow()) <= 1
                && Math.abs(this.getColumn() - other.getColumn()) <= 1;
    }

    @Override
    public void occupy() {
        isOccupied = true;
    }

    @Override
    public void shoot() {
        isHit = true;
    }

    @Override
    public boolean isOccupied() {
        return isOccupied;
    }

    @Override
    public boolean isHit() {
        return isHit;
    }

    @Override
    public String toString() {
        return "Linha = " + row + " Coluna = " + column;
    }
}