package iscteiul.ista.battleship;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Represents an abstract ship in the Battleship game.
 *
 * A ship has a category, an orientation, an initial position and a list
 * of positions occupied on the game board.
 *
 * This class provides common behaviour shared by all ship types,
 * including checking whether the ship is still floating, determining
 * its limits on the board, checking proximity to other ships and
 * receiving shots.
 */
public abstract class Ship implements IShip {

    private static final String GALEAO = "galeao";
    private static final String FRAGATA = "fragata";
    private static final String NAU = "nau";
    private static final String CARAVELA = "caravela";
    private static final String BARCA = "barca";

    /**
     * Creates a ship of the specified type.
     *
     * @param shipKind the type of ship to create
     * @param bearing the orientation of the ship
     * @param pos the initial position of the ship
     * @return the created ship, or {@code null} if the ship type is unknown
     */
    static Ship buildShip(String shipKind, Compass bearing, Position pos) {
        Ship s;

        switch (shipKind) {
            case BARCA:
                s = new Barge(bearing, pos);
                break;
            case CARAVELA:
                s = new Caravel(bearing, pos);
                break;
            case NAU:
                s = new Carrack(bearing, pos);
                break;
            case FRAGATA:
                s = new Frigate(bearing, pos);
                break;
            case GALEAO:
                s = new Galleon(bearing, pos);
                break;
            default:
                s = null;
        }

        return s;
    }

    private String category;
    private Compass bearing;
    private IPosition pos;
    protected List<IPosition> positions;

    /**
     * Creates a new ship.
     *
     * @param category the category of the ship
     * @param bearing the orientation of the ship
     * @param pos the initial position of the ship
     */
    public Ship(String category, Compass bearing, IPosition pos) {
        assert bearing != null;
        assert pos != null;

        this.category = category;
        this.bearing = bearing;
        this.pos = pos;
        positions = new ArrayList<>();
    }

    /**
     * Returns the category of this ship.
     *
     * @return the ship category
     */
    @Override
    public String getCategory() {
        return category;
    }

    /**
     * Returns the positions occupied by this ship.
     *
     * @return the list of occupied positions
     */
    public List<IPosition> getPositions() {
        return positions;
    }

    /**
     * Returns the initial position of this ship.
     *
     * @return the initial position
     */
    @Override
    public IPosition getPosition() {
        return pos;
    }

    /**
     * Returns the orientation of this ship.
     *
     * @return the ship orientation
     */
    @Override
    public Compass getBearing() {
        return bearing;
    }

    /**
     * Checks whether the ship is still floating.
     *
     * A ship is considered to be floating while at least one of its
     * positions has not been hit.
     *
     * @return {@code true} if at least one position has not been hit,
     *         {@code false} otherwise
     */
    @Override
    public boolean stillFloating() {
        for (int i = 0; i < getSize(); i++)
            if (!getPositions().get(i).isHit())
                return true;

        return false;
    }

    /**
     * Returns the row of the topmost position occupied by the ship.
     *
     * @return the smallest row value occupied by the ship
     */
    @Override
    public int getTopMostPos() {
        int top = getPositions().get(0).getRow();

        for (int i = 1; i < getSize(); i++)
            if (getPositions().get(i).getRow() < top)
                top = getPositions().get(i).getRow();

        return top;
    }

    /**
     * Returns the row of the bottommost position occupied by the ship.
     *
     * @return the largest row value occupied by the ship
     */
    @Override
    public int getBottomMostPos() {
        int bottom = getPositions().get(0).getRow();

        for (int i = 1; i < getSize(); i++)
            if (getPositions().get(i).getRow() > bottom)
                bottom = getPositions().get(i).getRow();

        return bottom;
    }

    /**
     * Returns the column of the leftmost position occupied by the ship.
     *
     * @return the smallest column value occupied by the ship
     */
    @Override
    public int getLeftMostPos() {
        int left = getPositions().get(0).getColumn();

        for (int i = 1; i < getSize(); i++)
            if (getPositions().get(i).getColumn() < left)
                left = getPositions().get(i).getColumn();

        return left;
    }

    /**
     * Returns the column of the rightmost position occupied by the ship.
     *
     * @return the largest column value occupied by the ship
     */
    @Override
    public int getRightMostPos() {
        int right = getPositions().get(0).getColumn();

        for (int i = 1; i < getSize(); i++)
            if (getPositions().get(i).getColumn() > right)
                right = getPositions().get(i).getColumn();

        return right;
    }

    /**
     * Checks whether this ship occupies the specified position.
     *
     * @param pos the position to check
     * @return {@code true} if the ship occupies the position,
     *         {@code false} otherwise
     */
    @Override
    public boolean occupies(IPosition pos) {
        assert pos != null;

        for (int i = 0; i < getSize(); i++)
            if (getPositions().get(i).equals(pos))
                return true;

        return false;
    }

    /**
     * Checks whether this ship is too close to another ship.
     *
     * @param other the other ship to check
     * @return {@code true} if one of the other ship's positions is too
     *         close to this ship, {@code false} otherwise
     */
    @Override
    public boolean tooCloseTo(IShip other) {
        assert other != null;

        Iterator<IPosition> otherPos = other.getPositions().iterator();

        while (otherPos.hasNext())
            if (tooCloseTo(otherPos.next()))
                return true;

        return false;
    }

    /**
     * Checks whether a position is adjacent to any position occupied
     * by this ship.
     *
     * @param pos the position to check
     * @return {@code true} if the position is adjacent to this ship,
     *         {@code false} otherwise
     */
    @Override
    public boolean tooCloseTo(IPosition pos) {
        for (int i = 0; i < this.getSize(); i++)
            if (getPositions().get(i).isAdjacentTo(pos))
                return true;

        return false;
    }

    /**
     * Shoots at the specified position.
     *
     * If the supplied position corresponds to one of the positions occupied
     * by this ship, that position is marked as hit.
     *
     * @param pos the position being shot
     */
    @Override
    public void shoot(IPosition pos) {
        assert pos != null;

        for (IPosition position : getPositions()) {
            if (position.equals(pos))
                position.shoot();
        }
    }

    /**
     * Returns a textual representation of the ship containing its
     * category, orientation and initial position.
     *
     * @return a textual representation of this ship
     */
    @Override
    public String toString() {
        return "[" + category + " " + bearing + " " + pos + "]";
    }
}
