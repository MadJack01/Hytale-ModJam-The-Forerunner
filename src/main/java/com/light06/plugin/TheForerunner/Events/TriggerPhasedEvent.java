package com.light06.plugin.TheForerunner.Events;

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.event.IEvent;
import com.hypixel.hytale.event.IEventDispatcher;
import com.hypixel.hytale.server.core.HytaleServer;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;

import javax.annotation.Nonnull;

public record TriggerPhasedEvent(
        @Nonnull Ref<EntityStore> npcRef, String nextPhase
) implements IEvent<Void> {
    public static void dispatch(Ref<EntityStore> npcRef, String nextPhase) {
        IEventDispatcher<TriggerPhasedEvent, TriggerPhasedEvent> dispatcher = HytaleServer.get().getEventBus().dispatchFor(TriggerPhasedEvent.class);

        if (dispatcher.hasListener()) {
            dispatcher.dispatch(new TriggerPhasedEvent(npcRef, nextPhase));
        }
    }
}
