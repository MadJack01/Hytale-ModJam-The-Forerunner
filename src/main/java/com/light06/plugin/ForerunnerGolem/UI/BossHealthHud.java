package com.light06.plugin.ForerunnerGolem.UI;

import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.entity.entities.player.hud.CustomUIHud;
import com.hypixel.hytale.server.core.ui.builder.UICommandBuilder;
import com.hypixel.hytale.server.core.universe.PlayerRef;

import javax.annotation.Nonnull;

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
}
