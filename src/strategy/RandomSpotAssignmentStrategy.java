package strategy;

import models.*;

public class RandomSpotAssignmentStrategy implements SpotAssignmentStrategy {

    @Override
    public ParkingSpot getSpot(ParkingLot parkingLot, VehicleType vehicleType) {
        for (ParkingFloor parkingFloor : parkingLot.getParkingFloors()) {
            for (ParkingSpot parkingSpot : parkingFloor.getParkingSpots()) {
                if (parkingSpot.getSupportedVehicleTypes().contains(vehicleType) &&
                parkingLot.getParkingSpotStatus().equals(ParkingSpotStatus.EMPTY)) {
                    return parkingSpot;
                }
            }
        }

        return null;
    }
}
