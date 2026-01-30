package com.light06.plugin.ForerunnerGolem.Systems;

import com.hypixel.hytale.component.ArchetypeChunk;
import com.hypixel.hytale.component.CommandBuffer;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.component.query.Query;
import com.hypixel.hytale.component.system.tick.EntityTickingSystem;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.entity.entities.player.hud.HudManager;
import com.hypixel.hytale.server.core.modules.entitystats.EntityStatMap;
import com.hypixel.hytale.server.core.modules.entitystats.EntityStatValue;
import com.hypixel.hytale.server.core.modules.entitystats.asset.DefaultEntityStatTypes;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.hypixel.hytale.server.npc.entities.NPCEntity;
import com.light06.plugin.ForerunnerGolem.UI.BossHealthHud;
import com.light06.plugin.ForerunnerGolem.UI.EmptyHudUI;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public class BossTickingSystem extends EntityTickingSystem<EntityStore> {

    @NullableDecl
    @Override
    public Query<EntityStore> getQuery() {
        return Query.any();
    }

    @Override
    public void tick(float v, int i, @NonNullDecl ArchetypeChunk<EntityStore> archetypeChunk, @NonNullDecl Store<EntityStore> store, @NonNullDecl CommandBuffer<EntityStore> commandBuffer) {
        NPCEntity npc = archetypeChunk.getComponent(i, NPCEntity.getComponentType());

        if(npc == null || !npc.getRoleName().startsWith("Golem")) {
            return;
        }

        EntityStatMap entityStatMap = archetypeChunk.getComponent(i, EntityStatMap.getComponentType());

        if(entityStatMap == null) {
            return;
        }

        EntityStatValue healthValue = entityStatMap.get(DefaultEntityStatTypes.getHealth());

        if(npc.getWorld() == null || healthValue == null) {
            return;
        }

        npc.getWorld().getPlayerRefs().forEach(playerRef -> {
            if(playerRef.getReference() == null) {
                return;
            }

            Player player = store.getComponent(playerRef.getReference(), Player.getComponentType());

            assert player != null;
            HudManager hudManager = player.getHudManager();

            BossHealthHud healthHud = new BossHealthHud(playerRef, "Golem test name", healthValue.asPercentage());

            if(healthValue.asPercentage() == 0) {
                hudManager.setCustomHud(playerRef, new EmptyHudUI(playerRef));
            } else {
                hudManager.setCustomHud(playerRef, healthHud);
            }
        });
    }
}
