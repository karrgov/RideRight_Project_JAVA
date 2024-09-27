package itinerary;

import java.math.BigDecimal;
import java.util.Objects;

public record City(String name, Location location) {

    private static final BigDecimal M_IN_KM = BigDecimal.valueOf(1000);
    private static final BigDecimal USD_PER_KM = BigDecimal.valueOf(20);

    public BigDecimal getEstimatedPriceTo(City other) {
        if(other == null) {
            throw new IllegalArgumentException("Destination city can not be bull!");
        }

        return  USD_PER_KM.
                multiply(BigDecimal.valueOf(this.location.getManhattanOfTwoLocations(other.location))).
                divide(M_IN_KM);
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) {
            return true;
        }

        if(obj == null || this.getClass() != obj.getClass()) {
            return false;
        }

        City city = (City) obj;
        return Objects.equals(this.name, city.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.name);
    }

    @Override
    public String toString() {
        return this.name;
    }
}
