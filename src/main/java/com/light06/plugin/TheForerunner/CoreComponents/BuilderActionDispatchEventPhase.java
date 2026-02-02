package com.light06.plugin.TheForerunner.CoreComponents;

import com.google.gson.JsonElement;
import com.hypixel.hytale.server.npc.asset.builder.BuilderDescriptorState;
import com.hypixel.hytale.server.npc.asset.builder.BuilderSupport;
import com.hypixel.hytale.server.npc.asset.builder.holder.StringHolder;
import com.hypixel.hytale.server.npc.asset.builder.validators.StringNotEmptyValidator;
import com.hypixel.hytale.server.npc.corecomponents.builders.BuilderActionBase;

import javax.annotation.Nonnull;

public class BuilderActionDispatchEventPhase extends BuilderActionBase {
    protected final StringHolder nextPhase = new StringHolder();

    @Nonnull
    public ActionDispatchEventPhase build(@Nonnull BuilderSupport builderSupport) {
        return new ActionDispatchEventPhase(this, builderSupport);
    }

    @Nonnull
    public String getShortDescription() {
        return "Dispatch Event Phase";
    }

    @Nonnull
    public String getLongDescription() {
        return "Dispatch Event Phase";
    }

    @Nonnull
    public BuilderDescriptorState getBuilderDescriptorState() {
        return BuilderDescriptorState.Experimental;
    }

    @Nonnull
    public BuilderActionDispatchEventPhase readConfig(@Nonnull JsonElement data) {
        this.getString(data, "NextPhase", this.nextPhase, "Phase2", StringNotEmptyValidator.get(), BuilderDescriptorState.Stable, "The Next Phase", (String)null);
        return this;
    }

    public String getNextPhase(@Nonnull BuilderSupport support) {
        return this.nextPhase.get(support.getExecutionContext());
    }
}
