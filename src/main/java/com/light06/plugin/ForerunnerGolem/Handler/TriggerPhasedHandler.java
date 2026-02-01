package com.light06.plugin.ForerunnerGolem.Handler;

import com.hypixel.hytale.builtin.buildertools.BuilderToolsPlugin;
import com.hypixel.hytale.builtin.buildertools.utils.Material;
import com.hypixel.hytale.builtin.weather.resources.WeatherResource;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.math.vector.Vector3i;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
import com.hypixel.hytale.server.core.prefab.selection.standard.BlockSelection;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.WorldConfig;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.hypixel.hytale.server.npc.entities.NPCEntity;
import com.light06.plugin.ForerunnerGolem.Events.TriggerPhasedEvent;

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

        WeatherResource weatherResource = store.getResource(WeatherResource.getResourceType());
        weatherResource.setForcedWeather(forcedWeather);

        WorldConfig config = world.getWorldConfig();
        config.setForcedWeather(forcedWeather);
        config.markChanged();
    }

    private void replaceBlock(String nextPhase, Ref<EntityStore> npcRef, World world, @Nonnull Store<EntityStore> store) {
        PlayerRef playerRef = world.getPlayerRefs().stream().findFirst().get();
        Player player = store.getComponent(Objects.requireNonNull(playerRef.getReference()), Player.getComponentType());

        String fromReplace = "Forerunner_Neon";
        String toReplace = "Forerunner_Neon2";

        if (nextPhase.equals("Forerunner_Golem3")) {
            fromReplace = "Forerunner_Neon2";
            toReplace = "Forerunner_Neon3";
        }

        Material fromReplaceMaterial = Material.fromKey(fromReplace);
        Material toReplaceMateriel = Material.fromKey(toReplace);

        TransformComponent transformComponent = store.getComponent(npcRef, TransformComponent.getComponentType());
        Vector3i npcPos = transformComponent.getPosition().toVector3i();

        int radius = 500;

        Vector3i startPosition = npcPos.clone().add(-radius, 0, -radius);
        Vector3i endPosition = npcPos.clone().add(radius, 0, radius);

        BlockSelection blockSelection = new BlockSelection();

        if(fromReplaceMaterial == null || toReplaceMateriel == null) {
            return;
        }

        for (int y = 105; y < 350; y++) {
            startPosition.setY(y);
            endPosition.setY(y);

            blockSelection.setSelectionArea(startPosition, endPosition);
            BuilderToolsPlugin.getState(player, playerRef).setSelection(blockSelection);
            assert player.getReference() != null;
            BuilderToolsPlugin.getState(player, playerRef).replace(player.getReference(), fromReplaceMaterial, toReplaceMateriel, store);
        }
    }
}
