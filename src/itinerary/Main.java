package itinerary;

import java.math.BigDecimal;
import java.util.List;

import itinerary.exception.CityNotKnownException;
import itinerary.exception.NoPathToDestinationException;
import itinerary.vehicle.VehicleType;

public class Main {

    private static void tryAnotherJourney(RideRight rideRight, City from, City to) {
        try {
            System.out.println("Attempting another journey from " + from.name() + " to " + to.name() + ":");
            rideRight.printJourneys(rideRight.findCheapestPath(from, to, true));
        } catch (Exception e) {
            System.out.println("Attempted journey also failed: " + e.getMessage());
        }
    }

    public static void main(String[] args) {

        City sofia = new City("Sofia", new Location(0, 2000));
        City plovdiv = new City("Plovdiv", new Location(4000, 1000));
        City varna = new City("Varna", new Location(9000, 3000));
        City burgas = new City("Burgas", new Location(9000, 1000));
        City ruse = new City("Ruse", new Location(7000, 4000));
        City blagoevgrad = new City("Blagoevgrad", new Location(0, 1000));
        City kardzhali = new City("Kardzhali", new Location(3000, 0));
        City tarnovo = new City("Tarnovo", new Location(5000, 3000));

        List<Journey> schedule = List.of(
                new Journey(VehicleType.BUS, sofia, blagoevgrad, new BigDecimal("20")),
                new Journey(VehicleType.BUS, blagoevgrad, sofia, new BigDecimal("20")),
                new Journey(VehicleType.BUS, sofia, plovdiv, new BigDecimal("90")),
                new Journey(VehicleType.BUS, plovdiv, sofia, new BigDecimal("90")),
                new Journey(VehicleType.BUS, plovdiv, kardzhali, new BigDecimal("50")),
                new Journey(VehicleType.BUS, kardzhali, plovdiv, new BigDecimal("50")),
                new Journey(VehicleType.BUS, plovdiv, burgas, new BigDecimal("90")),
                new Journey(VehicleType.BUS, burgas, plovdiv, new BigDecimal("90")),
                new Journey(VehicleType.BUS, burgas, varna, new BigDecimal("60")),
                new Journey(VehicleType.BUS, varna, burgas, new BigDecimal("60")),
                new Journey(VehicleType.BUS, sofia, tarnovo, new BigDecimal("150")),
                new Journey(VehicleType.BUS, tarnovo, sofia, new BigDecimal("150")),
                new Journey(VehicleType.BUS, plovdiv, tarnovo, new BigDecimal("40")),
                new Journey(VehicleType.BUS, tarnovo, plovdiv, new BigDecimal("40")),
                new Journey(VehicleType.BUS, tarnovo, ruse, new BigDecimal("70")),
                new Journey(VehicleType.BUS, ruse, tarnovo, new BigDecimal("70")),
                new Journey(VehicleType.BUS, varna, ruse, new BigDecimal("70")),
                new Journey(VehicleType.BUS, ruse, varna, new BigDecimal("70")),
                new Journey(VehicleType.PLANE, varna, burgas, new BigDecimal("200")),
                new Journey(VehicleType.PLANE, burgas, varna, new BigDecimal("200")),
                new Journey(VehicleType.PLANE, burgas, sofia, new BigDecimal("150")),
                new Journey(VehicleType.PLANE, sofia, burgas, new BigDecimal("250")),
                new Journey(VehicleType.PLANE, varna, sofia, new BigDecimal("290")),
                new Journey(VehicleType.PLANE, sofia, varna, new BigDecimal("300"))
        );

        RideRight rideRight = new RideRight(schedule);

        try {
            rideRight.printJourneys(rideRight.findCheapestPath(varna, kardzhali, true));
            System.out.println();
//            rideRight.printJourneys(rideRight.findCheapestPath(varna, kardzhali, false));
            rideRight.printJourneys(rideRight.findCheapestPath(varna, burgas, false));
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid input: " + e.getMessage());
        } catch (CityNotKnownException e) {
            System.out.println("City not known in schedule: " + e.getMessage());
            tryAnotherJourney(rideRight, burgas, sofia);
        } catch (NoPathToDestinationException e) {
            System.out.println("No path to destination: " + e.getMessage());
//            System.out.println("Please consider choosing a different city or allow transfers.");
            tryAnotherJourney(rideRight, burgas, sofia);
        }

    }

}
