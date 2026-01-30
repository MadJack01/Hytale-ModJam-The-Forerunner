package com.light06.plugin.ForerunnerGolem.Events;

import com.hypixel.hytale.builtin.portals.components.PortalDevice;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.system.EcsEvent;
import com.hypixel.hytale.event.IEvent;
import com.hypixel.hytale.event.IEventDispatcher;
import com.hypixel.hytale.protocol.packets.setup.WorldLoadFinished;
import com.hypixel.hytale.server.core.HytaleServer;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;

import javax.annotation.Nonnull;

public record OnWorldLoadEvent(
        @Nonnull Ref<EntityStore> worldRef
) implements IEvent<Void> {

    public static void dispatch(Ref<EntityStore> playerRef, long amount) {
        IEventDispatcher<OnWorldLoadEvent, OnWorldLoadEvent> dispatcher =
                HytaleServer.get().getEventBus().dispatchFor(OnWorldLoadEvent.class);

        if (dispatcher.hasListener()) {
            dispatcher.dispatch(new OnWorldLoadEvent(playerRef));
        }
    }
}