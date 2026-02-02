package com.light06.plugin.TheForerunner.CoreComponents;

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.hypixel.hytale.server.npc.asset.builder.BuilderSupport;
import com.hypixel.hytale.server.npc.corecomponents.ActionBase;
import com.hypixel.hytale.server.npc.role.Role;
import com.hypixel.hytale.server.npc.sensorinfo.InfoProvider;
import com.light06.plugin.TheForerunner.Events.TriggerPhasedEvent;

import javax.annotation.Nonnull;

public class ActionDispatchEventPhase extends ActionBase {

    protected final String nextPhase;

    public ActionDispatchEventPhase(@Nonnull BuilderActionDispatchEventPhase builder, @Nonnull BuilderSupport support) {
        super(builder);
        this.nextPhase = builder.getNextPhase(support);
    }

    public boolean execute(@Nonnull Ref<EntityStore> ref, @Nonnull Role role, InfoProvider sensorInfo, double dt, @Nonnull Store<EntityStore> store) {
        super.execute(ref, role, sensorInfo, dt, store);

        TriggerPhasedEvent.dispatch(ref, nextPhase);

        return true;
    }
}
