package earth.terrarium.botarium.common.heat;

import earth.terrarium.botarium.common.context.ItemContext;
import earth.terrarium.botarium.common.storage.base.UnitContainer;
import earth.terrarium.botarium.common.transfer.impl.FluidUnit;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public final class HeatProvider {
    private HeatProvider() {}

    public interface Block {
        HeatContainer getHeat(Level level, BlockPos pos, @Nullable BlockState state, @Nullable net.minecraft.world.level.block.entity.BlockEntity entity, @Nullable Direction direction);
    }

    public interface BlockEntity {
        HeatContainer getHeat(@Nullable Direction direction);
    }

    public interface Entity {
        HeatContainer getHeat();
    }

    public interface Item {
        HeatContainer getHeat(ItemStack stack, ItemContext context);
    }
}
