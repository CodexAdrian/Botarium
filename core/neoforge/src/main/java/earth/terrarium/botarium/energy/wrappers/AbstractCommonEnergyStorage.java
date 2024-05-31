package earth.terrarium.botarium.energy.wrappers;

import earth.terrarium.botarium.storage.base.ValueStorage;
import net.minecraftforge.energy.IEnergyStorage;

public interface AbstractCommonEnergyStorage extends ValueStorage {
    IEnergyStorage storage();

    @Override
    default long getStoredAmount() {
        return storage().getEnergyStored();
    }

    @Override
    default long getCapacity() {
        return storage().getMaxEnergyStored();
    }

    @Override
    default boolean allowsInsertion() {
        return storage().canReceive();
    }

    @Override
    default boolean allowsExtraction() {
        return storage().canExtract();
    }

    @Override
    default long insert(long amount, boolean simulate) {
        return storage().receiveEnergy((int) amount, simulate);
    }

    @Override
    default long extract(long amount, boolean simulate) {
        return storage().extractEnergy((int) amount, simulate);
    }
}
