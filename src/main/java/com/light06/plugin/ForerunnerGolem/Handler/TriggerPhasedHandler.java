package com.light06.plugin.ForerunnerGolem.Handler;

import com.hypixel.hytale.builtin.buildertools.commands.EnvironmentCommand;
import com.hypixel.hytale.builtin.weather.commands.WeatherCommand;
import com.hypixel.hytale.builtin.weather.commands.WeatherSetCommand;
import com.hypixel.hytale.builtin.weather.resources.WeatherResource;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.command.commands.utility.sound.SoundCommand;
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
    }

    private void switchWeather(String nextPhase, World world, @Nonnull Store<EntityStore> store) {
        String forcedWeather = "Forerunner_Weather";

        WeatherResource weatherResource = store.getResource(WeatherResource.getResourceType());
        weatherResource.setForcedWeather(forcedWeather);

        WorldConfig config = world.getWorldConfig();
        config.setForcedWeather(forcedWeather);
        config.markChanged();
    }
}
