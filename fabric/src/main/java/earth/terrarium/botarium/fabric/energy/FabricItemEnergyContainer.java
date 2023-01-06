package earth.terrarium.botarium.fabric.energy;

import earth.terrarium.botarium.common.menu.base.EnergyContainer;
import net.fabricmc.fabric.api.transfer.v1.context.ContainerItemContext;
import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.transaction.TransactionContext;
import net.fabricmc.fabric.api.transfer.v1.transaction.base.SnapshotParticipant;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import team.reborn.energy.api.EnergyStorage;

@SuppressWarnings("UnstableApiUsage")
public class FabricItemEnergyContainer extends SnapshotParticipant<CompoundTag> implements EnergyStorage {
    private final ContainerItemContext ctx;
    private final EnergyContainer container;

    public FabricItemEnergyContainer(ContainerItemContext ctx, EnergyContainer container) {
        this.ctx = ctx;
        CompoundTag nbt = ctx.getItemVariant().getNbt();
        if(nbt != null) container.deserialize(nbt);
        this.container = container;
    }

    @Override
    public long insert(long maxAmount, TransactionContext transaction) {
        long inserted = container.insertEnergy(maxAmount, false);
        setChanged(transaction);
        return inserted;
    }

    @Override
    public long extract(long maxAmount, TransactionContext transaction) {
        long l = container.extractEnergy(maxAmount, false);
        setChanged(transaction);
        return l;
    }

    @Override
    public long getAmount() {
        return container.getStoredEnergy();
    }

    @Override
    public long getCapacity() {
        return container.getMaxCapacity();
    }

    public void setChanged(TransactionContext transaction) {
        ItemStack stack = ctx.getItemVariant().toStack();
        this.container.serialize(stack.getOrCreateTag());
        this.updateSnapshots(transaction);
        ctx.exchange(ItemVariant.of(stack), ctx.getAmount(), transaction);
    }

    @Override
    protected CompoundTag createSnapshot() {
        return this.container.serialize(new CompoundTag());
    }

    @Override
    protected void readSnapshot(CompoundTag snapshot) {
        this.container.deserialize(snapshot);
    }
}
