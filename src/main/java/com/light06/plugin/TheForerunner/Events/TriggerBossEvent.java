package com.light06.plugin.TheForerunner.Events;

import com.hypixel.hytale.event.IEvent;
import com.hypixel.hytale.event.IEventDispatcher;
import com.hypixel.hytale.server.core.HytaleServer;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.universe.PlayerRef;

import javax.annotation.Nonnull;

public record TriggerBossEvent(
        @Nonnull Player player,
        @Nonnull PlayerRef playerRef,
        boolean isErased
) implements IEvent<Void> {
    public static void dispatch(Player player, PlayerRef playerRef, boolean isErased) {
        IEventDispatcher<TriggerBossEvent, TriggerBossEvent> dispatcher = HytaleServer.get().getEventBus().dispatchFor(TriggerBossEvent.class);

        if (dispatcher.hasListener()) {
            dispatcher.dispatch(new TriggerBossEvent(player, playerRef, isErased));
        }
    }
}
