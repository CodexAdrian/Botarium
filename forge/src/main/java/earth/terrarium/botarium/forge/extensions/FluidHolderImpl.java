package earth.terrarium.botarium.forge.extensions;

import earth.terrarium.botarium.common.fluid.base.FluidHolder;
import earth.terrarium.botarium.forge.fluid.ForgeFluidHolder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidType;
import net.msrandom.extensions.annotations.ClassExtension;
import net.msrandom.extensions.annotations.ImplementsBaseElement;
import org.apache.commons.lang3.NotImplementedException;

@ClassExtension(FluidHolder.class)
public interface FluidHolderImpl {
    @ImplementsBaseElement
    static FluidHolder of(Fluid fluid, long amount, CompoundTag tag) {
        return new ForgeFluidHolder(fluid, (int) amount, tag);
    }

    @ImplementsBaseElement
    static FluidHolder empty() {
        return ForgeFluidHolder.empty();
    }
}
