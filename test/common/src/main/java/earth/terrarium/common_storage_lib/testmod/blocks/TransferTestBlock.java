package earth.terrarium.common_storage_lib.testmod.blocks;

import com.mojang.serialization.MapCodec;
import earth.terrarium.common_storage_lib.testmod.TestMod;
import earth.terrarium.common_storage_lib.testmod.blockentities.TransferTestBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class TransferTestBlock extends BaseEntityBlock {
    public static final MapCodec<TransferTestBlock> CODEC = simpleCodec(TransferTestBlock::new);

    public TransferTestBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected @NotNull MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new TransferTestBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        return createTickerHelper(blockEntityType, TestMod.TRANSFER_BLOCK_ENTITY.get(), (level1, blockPos, blockState, blockEntity) -> blockEntity.tick());
    }
}
