package com.light06.plugin.ForerunnerGolem;

import com.hypixel.hytale.server.core.event.events.player.AddPlayerToWorldEvent;
import com.hypixel.hytale.server.core.event.events.player.DrainPlayerFromWorldEvent;
import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
import com.light06.plugin.ForerunnerGolem.Commands.BossUiTestCommand;
import com.light06.plugin.ForerunnerGolem.Events.TriggerBossEvent;
import com.light06.plugin.ForerunnerGolem.Handler.TriggerBossHandler;
import com.light06.plugin.ForerunnerGolem.Listeners.AddPlayerFromWorldEventListener;
import com.light06.plugin.ForerunnerGolem.Systems.BossTickingSystem;
import javax.annotation.Nonnull;

public class ForerunnerGolemPlugin extends JavaPlugin {
    public ForerunnerGolemPlugin(@Nonnull JavaPluginInit init) {
        super(init);
    }

    protected void setup() {
        super.setup();
        this.getCommandRegistry().registerCommand(new BossUiTestCommand("bosstest", "Test Boss UI", false));
        this.getEntityStoreRegistry().registerSystem(new BossTickingSystem());

        this.getEventRegistry().registerGlobal(AddPlayerToWorldEvent.class, AddPlayerFromWorldEventListener::on);
        this.getEventRegistry().registerGlobal(TriggerBossEvent.class, new TriggerBossHandler());
    }
}
