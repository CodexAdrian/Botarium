package earth.terrarium.botarium.energy.lookup;

import earth.terrarium.botarium.context.ItemContext;
import earth.terrarium.botarium.lookup.ItemLookup;
import earth.terrarium.botarium.storage.common.CommonValueStorage;
import earth.terrarium.botarium.storage.context.CommonItemContext;
import earth.terrarium.botarium.storage.context.FabricItemContext;
import earth.terrarium.botarium.storage.fabric.FabricLongStorage;
import earth.terrarium.botarium.storage.base.ValueStorage;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import team.reborn.energy.api.EnergyStorage;

import java.util.function.Consumer;

public class EnergyItemLookup implements ItemLookup<ValueStorage, ItemContext> {

    @Override
    public @Nullable ValueStorage find(ItemStack stack, ItemContext context) {
        EnergyStorage storage = EnergyStorage.ITEM.find(stack, new FabricItemContext(context));
        if (storage == null) {
            return null;
        }
        if (storage instanceof FabricLongStorage(ValueStorage rootContainer, var ignored)) {
            return rootContainer;
        }
        return new CommonValueStorage(storage);
    }

    @Override
    public void onRegister(Consumer<ItemRegistrar<ValueStorage, ItemContext>> registrar) {
        registrar.accept(this::registerSelf);
    }

    @Override
    public void registerSelf(ItemGetter<ValueStorage, ItemContext> getter, Item... items) {
        EnergyStorage.ITEM.registerForItems((stack, context) -> {
            ValueStorage container = getter.getContainer(stack, new CommonItemContext(context));
            return new FabricLongStorage(container);
        }, items);
    }
}
