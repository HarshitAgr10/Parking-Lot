package strategy;


import models.ParkingLot;
import models.ParkingSpot;
import models.VehicleType;

public interface SpotAssignmentStrategy {
    public ParkingSpot getSpot(ParkingLot parkingLot, VehicleType vehicleType);
}
