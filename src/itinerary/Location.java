package itinerary;

import java.math.BigDecimal;

public record Location(int x, int y) {

    public int getManhattanOfTwoLocations(Location other) {
        return Math.abs(this.x - other.x) + Math.abs(this.y - other.y);
    }

}
