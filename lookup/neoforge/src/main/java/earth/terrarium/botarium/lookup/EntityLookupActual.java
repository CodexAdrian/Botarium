package earth.terrarium.botarium.lookup;

import earth.terrarium.botarium.lookup.impl.NeoEntityLookup;
import net.minecraft.resources.ResourceLocation;
import net.msrandom.multiplatform.annotations.Actual;

public interface EntityLookupActual {
    @Actual
    static <T, C> EntityLookup<T, C> create(ResourceLocation name, Class<T> typeClass, Class<C> contextClass) {
        NeoEntityLookup<T, C> lookup = new NeoEntityLookup<>(name);
        RegistryEventListener.registerEntity(lookup);
        return lookup;
    }
}
