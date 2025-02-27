package earth.terrarium.common_storage_lib.fluid.util;

import com.mojang.serialization.Codec;
import earth.terrarium.common_storage_lib.resources.fluid.FluidResource;
import earth.terrarium.common_storage_lib.storage.base.CommonStorage;
import earth.terrarium.common_storage_lib.resources.ResourceStack;
import earth.terrarium.common_storage_lib.storage.base.StorageSlot;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public record FluidStorageData(List<ResourceStack<FluidResource>> stacks) {
    public static final Codec<FluidStorageData> CODEC = ResourceStack.FLUID_CODEC.listOf().xmap(FluidStorageData::new, FluidStorageData::stacks);

    public static final FluidStorageData EMPTY = new FluidStorageData(List.of());

    public static final Supplier<FluidStorageData> DEFAULT = () -> EMPTY;

    public static final StreamCodec<RegistryFriendlyByteBuf, FluidStorageData> NETWORK_CODEC = ByteBufCodecs.collection(size -> (List<ResourceStack<FluidResource>>) new ArrayList<ResourceStack<FluidResource>>(), ResourceStack.FLUID_STREAM_CODEC).map(FluidStorageData::new, FluidStorageData::stacks);

    public static FluidStorageData from(CommonStorage<FluidResource> container) {
        List<ResourceStack<FluidResource>> stacks = NonNullList.withSize(container.size(), ResourceStack.EMPTY_FLUID);
        for (int i = 0; i < container.size(); i++) {
            StorageSlot<FluidResource> slot = container.get(i);
            stacks.set(i, new ResourceStack<>(slot.getResource(), slot.getAmount()));
        }
        return new FluidStorageData(stacks);
    }
}
