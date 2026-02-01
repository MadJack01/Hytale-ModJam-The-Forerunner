package com.light06.plugin.ForerunnerGolem.Handler;

import com.hypixel.hytale.builtin.buildertools.BuilderToolsPlugin;
import com.hypixel.hytale.builtin.buildertools.utils.Material;
import com.hypixel.hytale.builtin.weather.resources.WeatherResource;
import com.hypixel.hytale.component.Holder;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.math.util.ChunkUtil;
import com.hypixel.hytale.math.vector.Vector3i;
import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
import com.hypixel.hytale.server.core.prefab.selection.standard.BlockSelection;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.WorldConfig;
import com.hypixel.hytale.server.core.universe.world.accessor.LocalCachedChunkAccessor;
import com.hypixel.hytale.server.core.universe.world.chunk.WorldChunk;
import com.hypixel.hytale.server.core.universe.world.storage.ChunkStore;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.hypixel.hytale.server.npc.entities.NPCEntity;
import com.light06.plugin.ForerunnerGolem.Events.TriggerPhasedEvent;

import javax.annotation.Nonnull;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
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

        WeatherResource weatherResource = store.getResource(WeatherResource.getResourceType());
        weatherResource.setForcedWeather(forcedWeather);

        WorldConfig config = world.getWorldConfig();
        config.setForcedWeather(forcedWeather);
        config.markChanged();
    }

    private void replaceBlock(String nextPhase, Ref<EntityStore> npcRef, World world, @Nonnull Store<EntityStore> store) {
        String fromReplace = "Forerunner_Neon";
        String toReplace = "Forerunner_Neon2";

        if (nextPhase.equals("Phase3")) {
            fromReplace = "Forerunner_Neon2";
            toReplace = "Forerunner_Neon3";
        }

        Material fromReplaceMaterial = Material.fromKey(fromReplace);
        Material toReplaceMateriel = Material.fromKey(toReplace);

        TransformComponent transformComponent = store.getComponent(npcRef, TransformComponent.getComponentType());
        Vector3i npcPos = transformComponent.getPosition().toVector3i();

        int radius = 1000;

        Vector3i startPosition = npcPos.clone().add(radius, 0, radius);
        Vector3i endPosition = npcPos.clone().add(radius, 0, radius);

        BlockSelection blockSelection = new BlockSelection();

        if(fromReplaceMaterial == null || toReplaceMateriel == null) {
            return;
        }

        for (int i = 50; i < 400; i++) {
            BlockSelection before = new BlockSelection();
            Vector3i min = Vector3i.min(startPosition, endPosition);
            Vector3i max = Vector3i.max(startPosition, endPosition);
            int xMin = min.getX();
            int xMax = max.getX();
            int yMin = min.getY();
            int yMax = max.getY();
            int zMin = min.getZ();
            int zMax = max.getZ();
            int width = xMax - xMin;
            int depth = zMax - zMin;
            int halfWidth = width / 2;
            int halfDepth = depth / 2;
            before.setPosition(xMin + halfWidth, yMin, zMin + halfDepth);
            before.setSelectionArea(min, max);
            BlockSelection after = new BlockSelection(before);
            LocalCachedChunkAccessor accessor = LocalCachedChunkAccessor.atWorldCoords(world, xMin + halfWidth, zMin + halfDepth, Math.max(width, depth));

            int totalBlocks = (width + 1) * (depth + 1) * (yMax - yMin + 1);
            int counter = 0;

            for(int x = xMin; x <= xMax; ++x) {
                for (int z = zMin; z <= zMax; ++z) {
                    WorldChunk chunk = accessor.getChunk(ChunkUtil.indexChunkFromBlock(x, z));

                    for (int y = yMax; y >= yMin; --y) {
                        int currentFiller = chunk.getFiller(x, y, z);
                        if (currentFiller != 0) {
                            ++counter;
                        } else {
                            int currentBlock = chunk.getBlock(x, y, z);
                            boolean shouldReplace = currentBlock == fromReplaceMaterial.getBlockId();

                            if (shouldReplace) {
                                Holder<ChunkStore> holder = chunk.getBlockComponentHolder(x, y, z);
                                Holder<ChunkStore> newHolder = BuilderToolsPlugin.createBlockComponent(chunk, x, y, z, toReplaceMateriel.getBlockId(), currentBlock, holder, true);
                                int rotation = chunk.getRotationIndex(x, y, z);
                                int supportValue = chunk.getSupportValue(x, y, z);
                                before.addBlockAtWorldPos(x, y, z, currentBlock, rotation, currentFiller, supportValue, holder);
                                after.addBlockAtWorldPos(x, y, z, toReplaceMateriel.getBlockId(), rotation, 0, 0, newHolder);
                                //this.replaceMultiBlockStructure(x, y, z, currentBlock, toReplaceMateriel.getBlockId(), rotation, accessor, before, after);
                            }
                        }
                    }
                }
            }

            startPosition.setY(i);
            endPosition.setY(i);

            try {
                TimeUnit.MILLISECONDS.sleep(4);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
