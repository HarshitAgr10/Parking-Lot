package services;

import exceptions.GateNotFoundException;
import models.*;
import repositories.GateRepository;
import repositories.ParkingLotRepository;
import repositories.TicketRepository;
import repositories.VehicleRepository;
import strategy.SpotAssignmentStrategy;
import strategy.SpotAssignmentStrategyFactory;

import javax.swing.text.html.Option;
import java.util.Date;
import java.util.Optional;

public class TicketService {

    private GateRepository gateRepository;
    private VehicleRepository vehicleRepository;
    private ParkingLotRepository parkingLotRepository;
    private TicketRepository ticketRepository;

    public TicketService(GateRepository gateRepository,
                         VehicleRepository vehicleRepository,
                         ParkingLotRepository parkingLotRepository,
                         TicketRepository ticketRepository) {
        this.gateRepository = gateRepository;
        this.vehicleRepository = vehicleRepository;
        this.parkingLotRepository = parkingLotRepository;
        this.ticketRepository = ticketRepository;
    }

    public Ticket issueTicket(VehicleType vehicleType, String vehicleNumber,
                              String vehicleOwnerName, Long gateId) throws GateNotFoundException {

        /*
        * 1. Create Ticket object
        * 2. Assign spot
        * 3. Assign date
        * 4. Return
        * */

        Ticket ticket = new Ticket();
        ticket.setEntryTime(new Date());

        Optional<Gate> gateOptional = gateRepository.findById(gateId);
        if (gateOptional.isEmpty()) {
            throw new GateNotFoundException();
        }
        Gate gate = gateOptional.get();
        ticket.setGeneratedAt(gate);
        ticket.setGeneratedBy(gate.getCurrentOperator());

        Vehicle savedVehicle;
        Optional<Vehicle> vehicleOptional = vehicleRepository
                .findVehicleByNumber(vehicleNumber);

        // Check if vehicle is entering the parking lot for first time
        if (vehicleOptional.isEmpty()) {
            // Set the vehicle and save it to vehicleRepository
            Vehicle vehicle = new Vehicle();
            vehicle.setOwnerName(vehicleOwnerName);
            vehicle.setVehicleType(vehicleType);
            vehicle.setNumber(vehicleNumber);

            savedVehicle = vehicleRepository.save(vehicle);

        } else {
            // If vehicle has entered parking lot previously also, get vehicle from vehicleRepository
            savedVehicle = vehicleOptional.get();
        }

        ticket.setVehicle(savedVehicle);
        ParkingLot parkingLot = parkingLotRepository.findByGate(gate);

        SpotAssignmentStrategy spotAssignmentStrategy = SpotAssignmentStrategyFactory
                .getSpotForType(parkingLot.getSpotAssignmentStrategyType());

        ticket.setAssignedSpot(
                spotAssignmentStrategy.getSpot(parkingLot, vehicleType)
        );

        return ticketRepository.save(ticket);
    }
}
