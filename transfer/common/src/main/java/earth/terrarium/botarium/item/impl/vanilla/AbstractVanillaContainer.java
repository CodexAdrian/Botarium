package earth.terrarium.botarium.item.impl.vanilla;

import earth.terrarium.botarium.item.base.ItemUnit;
import earth.terrarium.botarium.storage.base.CommonStorage;
import earth.terrarium.botarium.storage.base.StorageSlot;
import earth.terrarium.botarium.storage.util.TransferUtil;
import net.minecraft.world.Container;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractVanillaContainer implements CommonStorage<ItemUnit> {
    Container container;
    List<VanillaDelegatingSlot> slots;

    public AbstractVanillaContainer(Container container) {
        this.container = container;
        this.slots = new ArrayList<>();
        for (int i = 0; i < container.getContainerSize(); i++) {
            slots.add(new VanillaDelegatingSlot(this, i));
        }
    }

    @Override
    public long extract(ItemUnit unit, long amount, boolean simulate) {
        return TransferUtil.extractSlots(this, unit, amount, simulate);
    }

    @Override
    public long insert(ItemUnit unit, long amount, boolean simulate) {
        return TransferUtil.insertSlots(this, unit, amount, simulate);
    }

    @Override
    public int getSlotCount() {
        return container.getContainerSize();
    }

    @Override
    public @NotNull StorageSlot<ItemUnit> getSlot(int slot) {
        return slots.get(slot);
    }
}
