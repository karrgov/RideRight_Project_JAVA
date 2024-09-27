package itinerary;

import java.util.Comparator;

public class CompareByCheapestJourneyComparator implements Comparator<Journey> {

    private final City destination;

    public CompareByCheapestJourneyComparator(City destination) {
        this.destination = destination;
    }

    @Override
    public int compare(Journey o1, Journey o2) {
        int result = o1.calculatePriceAfterTax().add(o1.to().getEstimatedPriceTo(this.destination)).
                compareTo(o2.calculatePriceAfterTax().add(o2.to().getEstimatedPriceTo(this.destination)));

        if(result == 0) {
            return o1.to().name().compareTo(o2.to().name());
        }
        return result;
    }

}
