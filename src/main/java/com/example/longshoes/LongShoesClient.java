package com.example.longshoes;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.LivingEntityFeatureRendererRegistrationCallback;
import net.minecraft.client.model.PlayerEntityModel;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.state.PlayerEntityRenderState;
import net.minecraft.entity.EntityType;

public class LongShoesClient implements ClientModInitializer {

    @Override
    @SuppressWarnings("unchecked")
    public void onInitializeClient() {
        // Called for every living-entity renderer (including both player renderers: wide and slim arms).
        LivingEntityFeatureRendererRegistrationCallback.EVENT.register(
                (entityType, entityRenderer, registrationHelper, context) -> {
                    if (entityType == EntityType.PLAYER) {
                        registrationHelper.register(new LongShoesFeatureRenderer(
                                (FeatureRendererContext<PlayerEntityRenderState, PlayerEntityModel>) entityRenderer));
                    }
                });
    }
}
