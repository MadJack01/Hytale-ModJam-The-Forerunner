package com.light06.plugin.ForerunnerGolem.UI;

import com.hypixel.hytale.protocol.packets.interface_.NotificationStyle;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.entity.entities.Player;
import com.hypixel.hytale.server.core.entity.entities.player.hud.CustomUIHud;
import com.hypixel.hytale.server.core.entity.entities.player.hud.HudManager;
import com.hypixel.hytale.server.core.ui.builder.UICommandBuilder;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.util.EventTitleUtil;
import com.hypixel.hytale.server.core.util.NotificationUtil;

import javax.annotation.Nonnull;
import java.util.concurrent.CompletableFuture;

public class BossHealthHud extends CustomUIHud {
    String bossName;
    PlayerRef playerRef;
    double percentagePv;

    public BossHealthHud(@Nonnull PlayerRef playerRef, String bossName, double percentagePv) {
        super(playerRef);
        this.playerRef = playerRef;
        this.bossName = bossName;
        this.percentagePv = percentagePv;
    }

    @Override
    protected void build(@Nonnull UICommandBuilder builder) {
        builder.append("bosshealth.ui");
        builder.set("#BossName.Text", bossName);
        builder.set("#BossHealthBar.Value", percentagePv);
    }

    public static void setHudManager(@Nonnull Player player, @Nonnull PlayerRef pRef) {
        HudManager hudManager = player.getHudManager();
        String bossName = "Golem test name";

        CompletableFuture.runAsync(()-> {
            if (!(hudManager.getCustomHud() instanceof BossHealthHud)) {
                hudManager.setCustomHud(pRef, new BossHealthHud(pRef, bossName, 1));
                EventTitleUtil.showEventTitleToPlayer(pRef,
                        Message.raw("BOSS FIGHT"), Message.raw(bossName),
                        true, "ui/icons/skull.png", 3.0f, 0.5f, 0.5f);

                // Show notification
                NotificationUtil.sendNotification(
                        pRef.getPacketHandler(),
                        Message.raw("A powerful enemy approaches!"),
                        NotificationStyle.Danger);
            } else {
                if (hudManager != null) {
                    hudManager.setCustomHud(pRef, new EmptyHudUI(pRef));
                }
            }
        });
    }
}
