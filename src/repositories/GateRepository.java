package repositories;

import models.Gate;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class GateRepository {

    // Using Map as Dummy DB to store data where key is primary key and value is repository
    private Map<Long, Gate> gates = new HashMap<>();

    public Optional<Gate> findById(Long gateId) {
        if (gates.containsKey(gateId)) {
            return Optional.of(gates.get(gateId));
        }
        return Optional.empty();
    }
}
