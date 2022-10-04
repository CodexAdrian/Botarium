package testmod;

import earth.terrarium.botarium.api.registry.RegistryHelpers;
import earth.terrarium.botarium.api.registry.RegistryHolder;
import net.minecraft.core.Registry;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.function.Supplier;

public class TestMod {
    public static final String MOD_ID = "testmod";

    public static final RegistryHolder<BlockEntityType<?>> BLOCK_ENTITIES = new RegistryHolder<>(Registry.BLOCK_ENTITY_TYPE, MOD_ID);
    public static final RegistryHolder<Block> BLOCKS = new RegistryHolder<>(Registry.BLOCK, MOD_ID);
    public static final RegistryHolder<Item> ITEMS = new RegistryHolder<>(Registry.ITEM, MOD_ID);

    public static final Supplier<Block> EXAMPLE_BLOCK = BLOCKS.register("block", () -> new TestBlock(BlockBehaviour.Properties.copy(Blocks.STONE)));
    public static final Supplier<BlockEntityType<?>> EXAMPLE_BLOCK_ENTITY = BLOCK_ENTITIES.register("block", () -> RegistryHelpers.createBlockEntityType(TestBlockEntity::new, EXAMPLE_BLOCK.get()));
    public static final Supplier<BlockItem> EXAMPLE_BLOCK_ITEM = ITEMS.register("block", () -> new BlockItem(EXAMPLE_BLOCK.get(), new Item.Properties()));
    public static final Supplier<Item> EXAMPLE_ITEM = ITEMS.register("item", () -> new TestItem(new Item.Properties().stacksTo(1)));

    public static void init() {
        BLOCK_ENTITIES.initialize();
        BLOCKS.initialize();
        ITEMS.initialize();
    }
}
