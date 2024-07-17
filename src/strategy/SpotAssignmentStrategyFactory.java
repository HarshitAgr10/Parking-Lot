package strategy;

import models.SpotAssignmentStrategyType;

public class SpotAssignmentStrategyFactory {
    public static SpotAssignmentStrategy getSpotForType(
            SpotAssignmentStrategyType spotAssignmentStrategyType
    ) {
        // TODO:- Add if else conditions based on different strategy types
        return new RandomSpotAssignmentStrategy();
    }
}
