package itinerary;

import itinerary.exception.CityNotKnownException;
import itinerary.exception.NoPathToDestinationException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.SequencedCollection;
import java.util.Set;

public class RideRight implements ItineraryPlanner {

    private Map<City, List<Journey>> cityJourneys;

    public RideRight(List<Journey> schedule) {
        cityJourneys = new HashMap<>();
        for (Journey journey : schedule) {
            if (!cityJourneys.containsKey(journey.from())) {
                cityJourneys.put(journey.from(), new ArrayList<>());
            }
            cityJourneys.get(journey.from()).add(journey);
        }
    }

    @Override
    public SequencedCollection<Journey> findCheapestPath(City start, City destination, boolean allowTransfer)
            throws CityNotKnownException, NoPathToDestinationException {
        if (start == null || destination == null) {
            throw new IllegalArgumentException("Start or destination city can not be null!");
        }

        if (!cityJourneys.containsKey(start) || !cityJourneys.containsKey(destination)) {
            throw new CityNotKnownException("City could not be found in schedule!");
        }

        Queue<Journey> journeysFromCurrCity = new PriorityQueue<>(new CompareByCheapestJourneyComparator(destination));
        journeysFromCurrCity.addAll(cityJourneys.get(start));

        if (!allowTransfer && !doesJourneyToCityExist(journeysFromCurrCity, destination)) {
            throw new NoPathToDestinationException("There is not a path to the destination city!");
        }

        return aStarAlgorithm(start, destination, journeysFromCurrCity);
    }

    private SequencedCollection<Journey> aStarAlgorithm(City start, City destination, Queue<Journey> journeysFromCurrentCity)
            throws NoPathToDestinationException {
        Set<City> visited = new LinkedHashSet<>();
        visited.add(start);
        List<Journey> cheapestPath = new ArrayList<>();

        while (true) {
            if (visited.size() == cityJourneys.size() - 1
                    && !doesJourneyToCityExist(journeysFromCurrentCity, destination)) {
                throw new NoPathToDestinationException("There is not a path to the destination city!");
            }

            Journey next = journeysFromCurrentCity.poll();

            if (next == null) {
                throw new NullPointerException("Next journey can not be null!");
            }

            visited.add(next.to());
            cheapestPath.add(next);

            if (next.to().equals(destination)) {
                return cheapestPath;
            }

            journeysFromCurrentCity.clear();
            journeysFromCurrentCity.addAll(cityJourneys.get(next.to()));
        }
    }

    private static boolean doesJourneyToCityExist(Queue<Journey> journeys, City destination) {
        for (Journey journey : journeys) {
            if (journey.to().equals(destination)) {
                return true;
            }
        }
        return false;
    }

    public void printJourneys(SequencedCollection<Journey> journeys) {
        for(Journey curr : journeys) {
            System.out.print(curr);
        }
    }

}