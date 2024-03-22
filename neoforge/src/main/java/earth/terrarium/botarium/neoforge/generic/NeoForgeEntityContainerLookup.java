package earth.terrarium.botarium.neoforge.generic;

import earth.terrarium.botarium.Botarium;
import earth.terrarium.botarium.common.generic.base.EntityContainerLookup;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.neoforged.neoforge.capabilities.EntityCapability;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class NeoForgeEntityContainerLookup<T, C> implements EntityContainerLookup<T, C> {
    private final EntityCapability<T, C> capability;
    private final Map<Supplier<EntityType<?>>, EntityGetter<T, C>> blockEntityGetterMap = new HashMap<>();
    private Map<EntityType<?>, EntityGetter<T, C>> blockEntityMap = null;

    public NeoForgeEntityContainerLookup(EntityCapability<T, C> cap) {
        capability = cap;
    }

    public NeoForgeEntityContainerLookup(ResourceLocation name, Class<T> typeClass, Class<C> contextClass) {
        this(EntityCapability.create(name, typeClass, contextClass));
    }

    @Override
    public T find(Entity stack, @Nullable C context) {
        return stack.getCapability(capability, context);
    }

    @Override
    public void registerEntityTypes(EntityGetter<T, C> getter, Supplier<EntityType<?>>... containers) {
        for (Supplier<EntityType<?>> container : containers) {
            blockEntityGetterMap.put(container, getter);
        }
    }

    public void registerWithCaps(RegisterCapabilitiesEvent event) {
        getBlockEntityMap().forEach((blockEntityType, blockGetter) -> event.registerEntity(capability, blockEntityType, blockGetter::getContainer));
    }

    public Map<EntityType<?>, EntityGetter<T, C>> getBlockEntityMap() {
        blockEntityMap = Botarium.finalizeRegistration(blockEntityGetterMap, blockEntityMap);
        return blockEntityMap;
    }

    EntityCapability<T, C> getCapability() {
        return capability;
    }
}
