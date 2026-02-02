package com.light06.plugin.ForerunnerGolem.Handler;

import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.light06.plugin.ForerunnerGolem.Events.TriggerBossEvent;
import com.light06.plugin.ForerunnerGolem.States.NeonBlockState;
import com.light06.plugin.ForerunnerGolem.UI.BossHealthHud;

import java.util.function.Consumer;

public class TriggerBossHandler implements Consumer<TriggerBossEvent> {

    @Override
    public void accept(TriggerBossEvent event) {
        if (!event.playerRef().isValid()) return;

        BossHealthHud.setHudManager(event.player(), event.playerRef(), event.isErased());
        NeonBlockState.changeNeonBlockState();
    }
}
