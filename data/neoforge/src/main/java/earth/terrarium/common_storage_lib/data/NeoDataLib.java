package earth.terrarium.common_storage_lib.data;

import earth.terrarium.common_storage_lib.data.network.BlockEntitySyncAllPacket;
import earth.terrarium.common_storage_lib.data.network.BlockEntitySyncPacket;
import earth.terrarium.common_storage_lib.data.network.EntitySyncAllPacket;
import earth.terrarium.common_storage_lib.data.network.EntitySyncPacket;
import earth.terrarium.common_storage_lib.data.sync.AttachmentData;
import earth.terrarium.common_storage_lib.data.sync.DataSyncSerializer;
import net.minecraft.core.Registry;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.registries.NewRegistryEvent;
import net.neoforged.neoforge.registries.RegistryBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

@Mod("common_storage_lib_data")
public class NeoDataLib {
    public static final String MOD_ID = "common_storage_lib_data";
    public static final ResourceKey<Registry<DataSyncSerializer<?>>> SYNC_SERIALIZERS_KEY = ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(MOD_ID, "sync_serializers"));
    public static final Registry<DataSyncSerializer<?>> SYNC_SERIALIZERS = new RegistryBuilder<>(SYNC_SERIALIZERS_KEY).create();
    public static StreamCodec<RegistryFriendlyByteBuf, AttachmentData<?>> SYNC_SERIALIZER_STREAM_CODEC = new StreamCodec<>() {
        @Override
        public void encode(@NotNull RegistryFriendlyByteBuf object, AttachmentData<?> object2) {
            object2.encode(object);
        }

        @Override
        public @NotNull AttachmentData<?> decode(RegistryFriendlyByteBuf object) {
            ResourceLocation key = object.readResourceLocation();
            DataSyncSerializer<?> serializer = SYNC_SERIALIZERS.get(key);
            if (serializer == null) {
                throw new IllegalStateException("Unknown sync serializer: " + key);
            }
            return serializer.decode(object);
        }
    };

    public NeoDataLib(IEventBus bus) {
        NeoForge.EVENT_BUS.addListener(this::syncAttachmentsToEntities);
        bus.addListener(this::registerSyncSerializer);
        bus.addListener(this::registerNetworkHandling);
    }

    @SubscribeEvent
    public void registerSyncSerializer(NewRegistryEvent event) {
        event.register(SYNC_SERIALIZERS);
    }

    @SubscribeEvent
    public void syncAttachmentsToEntities(PlayerEvent.StartTracking event) {
        PacketDistributor.sendToPlayer((ServerPlayer) event.getEntity(), EntitySyncAllPacket.of(event.getEntity()));
    }

    @SubscribeEvent
    public void registerNetworkHandling(RegisterPayloadHandlersEvent event) {
        event.registrar("1").playToClient(EntitySyncAllPacket.TYPE, EntitySyncAllPacket.CODEC, (packet, context) -> {
            Optional.ofNullable(context.player().level().getEntity(packet.entityId())).ifPresent(entity -> {
                packet.syncData().forEach(data -> data.updateTarget(entity));
            });
        });

        event.registrar("1").playToClient(BlockEntitySyncAllPacket.TYPE, BlockEntitySyncAllPacket.CODEC, (packet, context) -> {
            context.player().level().getBlockEntity(packet.pos(), packet.blockEntityType()).ifPresent(entity -> {
                packet.syncData().forEach(data -> data.updateTarget(entity));
            });
        });

        event.registrar("1").playToClient(EntitySyncPacket.TYPE, EntitySyncPacket.CODEC, (packet, context) -> {
            Optional.ofNullable(context.player().level().getEntity(packet.entityId())).ifPresent(entity -> {
                packet.syncData().updateTarget(entity);
            });
        });

        event.registrar("1").playToClient(BlockEntitySyncPacket.TYPE, BlockEntitySyncPacket.CODEC, (packet, context) -> {
            context.player().level().getBlockEntity(packet.pos(), packet.blockEntityType()).ifPresent(entity -> {
                packet.syncData().updateTarget(entity);
            });
        });
    }
}
