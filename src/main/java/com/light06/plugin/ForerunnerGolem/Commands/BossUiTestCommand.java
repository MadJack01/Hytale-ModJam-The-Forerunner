package com.light06.plugin.ForerunnerGolem.Commands;

import com.hypixel.hytale.builtin.buildertools.BuilderToolsPlugin;
import com.hypixel.hytale.builtin.buildertools.utils.Material;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.math.util.ChunkUtil;
import com.hypixel.hytale.math.vector.Vector3i;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.modules.entity.component.TransformComponent;
import com.hypixel.hytale.server.core.prefab.selection.standard.BlockSelection;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.chunk.WorldChunk;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.light06.plugin.ForerunnerGolem.Events.TriggerBossEvent;

import javax.annotation.Nonnull;
import java.util.concurrent.TimeUnit;

public class BossUiTestCommand extends AbstractPlayerCommand {
    public BossUiTestCommand(@Nonnull String name, @Nonnull String description, boolean requiresConfirmation) {
        super(name, description, requiresConfirmation);
    }

    @Override
    protected void execute(@Nonnull CommandContext commandContext, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
        Player player = store.getComponent(ref, Player.getComponentType());
        PlayerRef pRef = store.getComponent(ref, PlayerRef.getComponentType());
        if (player == null || pRef == null) { return; }
        TriggerBossEvent.dispatch(player, pRef);
/*
        BlockSelection blockSelection = new BlockSelection();

        TransformComponent transformComponent = store.getComponent(ref, TransformComponent.getComponentType());

        Vector3i playerPos = transformComponent.getPosition().toVector3i();

        int radius = 100;

        Material fromReplace = Material.fromKey("Forerunner_Neon");
        Material toReplace = Material.fromKey("Forerunner_Neon2");

        Vector3i startPosition = playerPos.clone().add(-radius, 0, -radius);
        Vector3i endPosition = playerPos.clone().add(radius, 0, radius);

        if(fromReplace == null || toReplace == null || player.getReference() == null) {
            return;
        }

        for (int y = 102; y < 350; y++) {
            blockSelection.setSelectionArea(startPosition, endPosition);
            startPosition.setY(y);
            endPosition.setY(y);
            BuilderToolsPlugin.getState(player, pRef).setSelection(blockSelection);
            BuilderToolsPlugin.getState(player, pRef).replace(player.getReference(), fromReplace, toReplace, store);

            try {
                TimeUnit.MILLISECONDS.sleep(4);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        BuilderToolsPlugin.getState(player, pRef).setSelection(new BlockSelection());*/
    }
}
