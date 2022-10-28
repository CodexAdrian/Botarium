package testmod;

import earth.terrarium.botarium.api.energy.EnergyBlock;
import earth.terrarium.botarium.api.energy.SimpleUpdatingEnergyContainer;
import earth.terrarium.botarium.api.energy.StatefulEnergyContainer;
import earth.terrarium.botarium.api.fluid.FluidHoldingBlock;
import earth.terrarium.botarium.api.fluid.FluidHooks;
import earth.terrarium.botarium.api.fluid.SimpleUpdatingFluidContainer;
import earth.terrarium.botarium.api.fluid.UpdatingFluidContainer;
import earth.terrarium.botarium.api.item.ItemContainerBlock;
import earth.terrarium.botarium.api.item.SerializbleContainer;
import earth.terrarium.botarium.api.item.SimpleItemContainer;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class ExampleBlockEntity extends BlockEntity implements EnergyBlock, FluidHoldingBlock, ItemContainerBlock {
    public SimpleUpdatingFluidContainer fluidContainer;
    private SimpleItemContainer itemContainer;
    private SimpleUpdatingEnergyContainer energyContainer;

    public ExampleBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(TestMod.EXAMPLE_BLOCK_ENTITY.get(), blockPos, blockState);
    }

    public ExampleBlockEntity(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState) {
        super(blockEntityType, blockPos, blockState);
    }

    @Override
    public final StatefulEnergyContainer getEnergyStorage() {
        return energyContainer == null ? this.energyContainer = new SimpleUpdatingEnergyContainer(this, 1000000) : this.energyContainer;
    }

    @Override
    public UpdatingFluidContainer getFluidContainer() {
        return fluidContainer == null ? this.fluidContainer = new SimpleUpdatingFluidContainer(this, FluidHooks.buckets(2), 1, (i, fluidHolder) -> true) : this.fluidContainer;
    }

    @Override
    public SerializbleContainer getContainer() {
        return itemContainer == null ? this.itemContainer = new SimpleItemContainer(this, 1) : this.itemContainer;
    }

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag tag = new CompoundTag();
        this.saveAdditional(tag);
        return tag;
    }

    @Override
    public void update() {
        this.setChanged();
        this.getLevel().sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), Block.UPDATE_ALL);
    }

    public void tick() {
    }
}
