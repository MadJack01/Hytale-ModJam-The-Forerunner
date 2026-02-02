package com.light06.plugin.TheForerunner.Handler;

import com.hypixel.hytale.builtin.weather.resources.WeatherResource;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.math.util.ChunkUtil;
import com.hypixel.hytale.math.vector.Vector3i;
import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.WorldConfig;
import com.hypixel.hytale.server.core.universe.world.chunk.WorldChunk;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.hypixel.hytale.server.npc.entities.NPCEntity;
import com.light06.plugin.TheForerunner.Events.TriggerPhasedEvent;

import javax.annotation.Nonnull;
import java.util.Objects;
import java.util.function.Consumer;

public class TriggerPhasedHandler implements Consumer<TriggerPhasedEvent> {

    @Override
    public void accept(TriggerPhasedEvent event) {
        if (!event.npcRef().isValid()) return;

        var store = event.npcRef().getStore();

        NPCEntity npcEntity = store.getComponent(event.npcRef(), Objects.requireNonNull(NPCEntity.getComponentType()));
        assert npcEntity != null;
        World world = npcEntity.getWorld();

        assert world != null;
        this.switchWeather(event.nextPhase(), world, store);
        this.replaceBlock(event.nextPhase(), event.npcRef(), world, store);
    }

    private void switchWeather(String nextPhase, World world, @Nonnull Store<EntityStore> store) {
        String forcedWeather = "Forerunner_Weather";

        if(nextPhase.equals("Forerunner_Golem2")) {
            forcedWeather = "Forerunner_Weather2";
        } else if (nextPhase.equals("Forerunner_Golem3")) {
            forcedWeather = "Forerunner_Weather3";
        }

        WeatherResource weatherResource = store.getResource(WeatherResource.getResourceType());
        weatherResource.setForcedWeather(forcedWeather);

        WorldConfig config = world.getWorldConfig();
        config.setForcedWeather(forcedWeather);
        config.markChanged();
    }

    private void replaceBlock(String nextPhase, Ref<EntityStore> npcRef, World world, @Nonnull Store<EntityStore> store) {
        TransformComponent transformComponent = store.getComponent(npcRef, TransformComponent.getComponentType());
        Vector3i npcPos = transformComponent.getPosition().toVector3i();

        int radius = 300;

        Vector3i startPosition = npcPos.clone().add(-radius, -80, -radius);
        Vector3i endPosition = npcPos.clone().add(radius, 150, radius);

        world.execute(() -> {
            String nextTint = "51ff00";

            if(nextPhase.equals("Forerunner_Golem2")) {
                nextTint = "e8ee0d";
            } else if (nextPhase.equals("Forerunner_Golem3")) {
                nextTint = "ff0000";
            }

            Vector3i min = Vector3i.min(startPosition, endPosition);
            Vector3i max = Vector3i.max(startPosition, endPosition);

            int minX = min.getX();
            int minZ = min.getZ();
            int maxX = max.getX();
            int maxZ = max.getZ();

            for(int cx = ChunkUtil.chunkCoordinate(minX); cx <= ChunkUtil.chunkCoordinate(maxX); ++cx) {
                for(int cz = ChunkUtil.chunkCoordinate(minZ); cz <= ChunkUtil.chunkCoordinate(maxZ); ++cz) {
                    int startX = Math.max(0, minX - ChunkUtil.minBlock(cx));
                    int startZ = Math.max(0, minZ - ChunkUtil.minBlock(cz));
                    int endX = Math.min(32, maxX - ChunkUtil.minBlock(cx));
                    int endZ = Math.min(32, maxZ - ChunkUtil.minBlock(cz));
                    WorldChunk chunk = world.getNonTickingChunk(ChunkUtil.indexChunk(cx, cz));

                    if(chunk == null || chunk.getBlockChunk() == null) {
                        continue;
                    }

                    for(int z = startZ; z < endZ; ++z) {
                        for(int x = startX; x < endX; ++x) {
                            chunk.getBlockChunk().setTint(x, z, Integer.parseInt(nextTint, 16));
                        }
                    }

                    world.getNotificationHandler().updateChunk(chunk.getIndex());
                }
            }
        });
    }
}
