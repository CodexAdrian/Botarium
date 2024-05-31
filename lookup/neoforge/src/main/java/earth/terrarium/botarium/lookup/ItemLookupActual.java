package earth.terrarium.botarium.lookup;

import earth.terrarium.botarium.lookup.impl.NeoItemLookup;
import net.minecraft.resources.ResourceLocation;
import net.msrandom.multiplatform.annotations.Actual;

public interface ItemLookupActual {
    @Actual
    static <T, C> ItemLookup<T, C> create(ResourceLocation name, Class<T> typeClass, Class<C> contextClass) {
        NeoItemLookup<T, C> lookup = new NeoItemLookup<>(name);
        RegistryEventListener.registerItem(lookup);
        return lookup;
    }
}
