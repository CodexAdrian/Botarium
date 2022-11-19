package earth.terrarium.botarium.api.energy;

import net.minecraft.core.Direction;

public interface StatefulEnergyContainer<T> extends EnergyContainer {

    /**
     * Called when the operation has been completed and the data has been updated.
     */
    void update(T updatable);

    /**
     * @param direction The {@link Direction} to get the container from.
     * @return A {@link StatefulEnergyContainer} for a given {@link Direction}.
     */
    default StatefulEnergyContainer getContainer(Direction direction) {
        return this;
    }
}
