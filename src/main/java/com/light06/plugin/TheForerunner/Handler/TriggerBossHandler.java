package com.light06.plugin.TheForerunner.Handler;

import com.light06.plugin.TheForerunner.Events.TriggerBossEvent;
import com.light06.plugin.TheForerunner.UI.BossHealthHud;

import java.util.function.Consumer;

public class TriggerBossHandler implements Consumer<TriggerBossEvent> {

    @Override
    public void accept(TriggerBossEvent event) {
        if (!event.playerRef().isValid()) return;

        BossHealthHud.setHudManager(event.player(), event.playerRef(), event.isErased());
    }
}
