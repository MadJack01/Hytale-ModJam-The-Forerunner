package com.light06.plugin.ForerunnerGolem.States;

import com.hypixel.hytale.codec.builder.BuilderCodec;
import com.hypixel.hytale.logger.HytaleLogger;
import com.hypixel.hytale.server.core.asset.type.blocktype.config.BlockType;
import com.hypixel.hytale.server.core.universe.world.meta.BlockState;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;

@SuppressWarnings("removal")
public class NeonBlockState extends BlockState {
    public static final BuilderCodec<NeonBlockState> CODEC = BuilderCodec.<NeonBlockState>builder(NeonBlockState.class, NeonBlockState::new, BlockState.BASE_CODEC).build();
    public static ArrayList<NeonBlockState> neonBlockStates;

    public boolean initialize(BlockType blockType) {
        if (neonBlockStates == null) {
            neonBlockStates = new ArrayList<>();
        }
        neonBlockStates.add(this);
        return super.initialize(blockType);
    }

    @Override
    public void onUnload() {
        super.onUnload();
        neonBlockStates.remove(this);
    }

    public BlockType getNeonBlockType() {
        if (getChunk() == null)
            return null;
        return getChunk().getBlockType(this.getBlockX(), this.getBlockY(), this.getBlockZ());
    }

    public void setState(String state) {
        getChunk().setBlockInteractionState(this.getBlockX(), this.getBlockY(), this.getBlockZ(), getNeonBlockType(), state, true);
    }

    public static void changeNeonBlockState() {
        HytaleLogger.getLogger().at(Level.INFO).log("'%d' Entries found in list", neonBlockStates.size());
        if (neonBlockStates == null || neonBlockStates.isEmpty()) { return; }
        for (NeonBlockState neonBlockState : neonBlockStates) {
            BlockType blockType = neonBlockState.getNeonBlockType();
            if (blockType == null) { continue; }
            switch (blockType.getId()) {
                case "*Forerunner_Neon_State_Definitions_Phase_1":
                    neonBlockState.setState("Phase_2");
                    break;
                case "*Forerunner_Neon_State_Definitions_Phase_2":
                    neonBlockState.setState("Phase_3");
                    break;
                case "*Forerunner_Neon_State_Definitions_Phase_3":
                    neonBlockState.setState("Phase_1");
                    break;
                default:
                    neonBlockState.setState("Phase_2");
                    break;
            }
        }
    }
}
