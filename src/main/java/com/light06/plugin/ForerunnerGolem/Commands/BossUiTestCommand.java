package com.light06.plugin.ForerunnerGolem.Commands;

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.protocol.packets.interface_.NotificationStyle;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.entity.entities.player.hud.HudManager;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import com.hypixel.hytale.server.core.util.EventTitleUtil;
import com.hypixel.hytale.server.core.util.NotificationUtil;
import com.light06.plugin.ForerunnerGolem.UI.BossHealthHud;
import com.light06.plugin.ForerunnerGolem.UI.EmptyHudUI;

import javax.annotation.Nonnull;
import java.util.concurrent.CompletableFuture;

public class BossUiTestCommand extends AbstractPlayerCommand {
    public BossUiTestCommand(@Nonnull String name, @Nonnull String description, boolean requiresConfirmation) {
        super(name, description, requiresConfirmation);
    }

    @Override
    protected void execute(@Nonnull CommandContext commandContext, @Nonnull Store<EntityStore> store, @Nonnull Ref<EntityStore> ref, @Nonnull PlayerRef playerRef, @Nonnull World world) {
        Player player = commandContext.senderAs(Player.class);
        HudManager hudManager = player.getHudManager();
        String bossName = "Golem test name";

        CompletableFuture.runAsync(()-> {
            if (!(hudManager.getCustomHud() instanceof BossHealthHud)) {
                hudManager.setCustomHud(playerRef, new BossHealthHud(playerRef, bossName, 1));
                EventTitleUtil.showEventTitleToPlayer(playerRef,
                        Message.raw("BOSS FIGHT"), Message.raw(bossName),
                        true, "ui/icons/skull.png", 3.0f, 0.5f, 0.5f);

                // Show notification
                NotificationUtil.sendNotification(
                        playerRef.getPacketHandler(),
                        Message.raw("A powerful enemy approaches!"),
                        NotificationStyle.Danger);
            } else {
                if (hudManager != null) {
                    hudManager.setCustomHud(playerRef, new EmptyHudUI(playerRef));
                }
            }
        }, world);
    }
}
