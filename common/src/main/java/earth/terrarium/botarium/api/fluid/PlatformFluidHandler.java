package earth.terrarium.botarium.api.fluid;

public interface PlatformFluidHandler {

    long insertFluid(FluidHolder fluid, boolean simulate);

    FluidHolder extractFluid(FluidHolder fluid, boolean simulate);

    int getTankAmount();

    FluidHolder getFluidInTank(int tank);

    /**
     * @return If the handler supports insertion.
     */
    boolean supportsInsertion();

    /**
     * @return If the handler supports extraction.
     */
    boolean supportsExtraction();
}
