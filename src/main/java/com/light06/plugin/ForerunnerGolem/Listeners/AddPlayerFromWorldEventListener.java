package com.light06.plugin.ForerunnerGolem.Listeners;

import com.hypixel.hytale.component.Holder;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.entity.entities.player.hud.HudManager;
import com.hypixel.hytale.server.core.event.events.player.AddPlayerToWorldEvent;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.Universe;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.light06.plugin.ForerunnerGolem.UI.EmptyHudUI;

public class AddPlayerFromWorldEventListener {
    public static void on(AddPlayerToWorldEvent event) {
        if (event.getWorld().getName().contains("forerunners")) {
            return;
        }

        Holder<EntityStore> holder = event.getHolder();

        Player player = holder.getComponent(Player.getComponentType());
        PlayerRef pRef = holder.getComponent(PlayerRef.getComponentType());

        if (player == null || pRef == null) { return; }

        HudManager hudManager = player.getHudManager();
        hudManager.setCustomHud(pRef, new EmptyHudUI(pRef));
    }
}
