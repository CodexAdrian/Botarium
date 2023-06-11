package earth.terrarium.botarium.common.fluid.base;

import earth.terrarium.botarium.common.fluid.impl.SimpleFluidSnapshot;

/**
 * A snapshot of a {@link FluidContainer} that can be loaded into another container or used to restore a container to a previous state.
 * @see SimpleFluidSnapshot
 */
public interface FluidSnapshot {

    /**
     * Loads the snapshot into the given container
     * @param container The container to load the snapshot into
     */
    void loadSnapshot(FluidContainer container);
}
