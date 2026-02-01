package com.light06.plugin.ForerunnerGolem;

import com.hypixel.hytale.server.core.event.events.player.AddPlayerToWorldEvent;
import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
import com.hypixel.hytale.server.core.plugin.event.PluginSetupEvent;
import com.hypixel.hytale.server.npc.NPCPlugin;
import com.light06.plugin.ForerunnerGolem.Commands.BossUiTestCommand;
import com.light06.plugin.ForerunnerGolem.CoreComponents.BuilderActionDispatchEventPhase;
import com.light06.plugin.ForerunnerGolem.Events.TriggerBossEvent;
import com.light06.plugin.ForerunnerGolem.Events.TriggerPhasedEvent;
import com.light06.plugin.ForerunnerGolem.Handler.TriggerBossHandler;
import com.light06.plugin.ForerunnerGolem.Handler.TriggerPhasedHandler;
import com.light06.plugin.ForerunnerGolem.Listeners.AddPlayerFromWorldEventListener;
import com.light06.plugin.ForerunnerGolem.Systems.BossTickingSystem;
import javax.annotation.Nonnull;

public class ForerunnerGolemPlugin extends JavaPlugin {
    public ForerunnerGolemPlugin(@Nonnull JavaPluginInit init) {
        super(init);
    }

    @Override
    protected void setup() {
        super.setup();
        this.getCommandRegistry().registerCommand(new BossUiTestCommand("bosstest", "Test Boss UI", false));
        this.getEntityStoreRegistry().registerSystem(new BossTickingSystem());

        this.getEventRegistry().registerGlobal(AddPlayerToWorldEvent.class, AddPlayerFromWorldEventListener::on);
        this.getEventRegistry().registerGlobal(TriggerBossEvent.class, new TriggerBossHandler());
        this.getEventRegistry().registerGlobal(TriggerPhasedEvent.class, new TriggerPhasedHandler());

        this.getEventRegistry().register(PluginSetupEvent.class, NPCPlugin.class, pluginSetupEvent -> {
            NPCPlugin.get().registerCoreComponentType("DispatchEventPhase", BuilderActionDispatchEventPhase::new);
        });
    }

    @Override
    protected void start() {
    }
}
