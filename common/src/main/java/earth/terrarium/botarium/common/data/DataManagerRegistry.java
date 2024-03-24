package earth.terrarium.botarium.common.data;

import com.mojang.serialization.Codec;
import net.minecraft.resources.ResourceLocation;
import org.apache.commons.lang3.NotImplementedException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public interface DataManagerRegistry {
    static DataManagerRegistry create(String modid) {
        throw new NotImplementedException("DataManagerRegistry.create is not implemented");
    }

    <T> DataManager<T> register(@NotNull String name, @NotNull Supplier<T> factory, @Nullable Codec<T> codec, boolean copyOnDeath);

    <T> DataManagerBuilder<T> builder(Supplier<T> factory);

    void initialize();
}
