package com.light06.plugin.ForerunnerGolem.Events;

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.event.IEvent;
import com.hypixel.hytale.event.IEventDispatcher;
import com.hypixel.hytale.server.core.HytaleServer;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;

import javax.annotation.Nonnull;

public record TriggerBossEvent(
        @Nonnull Ref<EntityStore> playerRef
) implements IEvent<Void> {
    public static void dispatch(Ref<EntityStore> playerRef) {
        IEventDispatcher<TriggerBossEvent, TriggerBossEvent> dispatcher = HytaleServer.get().getEventBus().dispatchFor(TriggerBossEvent.class);

        if (dispatcher.hasListener()) {
            dispatcher.dispatch(new TriggerBossEvent(playerRef));
        }
    }
}
