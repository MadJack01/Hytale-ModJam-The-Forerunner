package com.light06.plugin.ForerunnerGolem.Handler;

import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.light06.plugin.ForerunnerGolem.Events.TriggerBossEvent;
import com.light06.plugin.ForerunnerGolem.UI.BossHealthHud;

import java.util.function.Consumer;

public class TriggerBossHandler implements Consumer<TriggerBossEvent> {

    @Override
    public void accept(TriggerBossEvent event) {
        if (!event.playerRef().isValid()) return;

        var store = event.playerRef().getStore();
        Player player = store.getComponent(event.playerRef(), Player.getComponentType());
        PlayerRef pRef = store.getComponent(event.playerRef(), PlayerRef.getComponentType());
        if (player == null || pRef == null) { return; }
        BossHealthHud.setHudManager(player, pRef);
    }
}
