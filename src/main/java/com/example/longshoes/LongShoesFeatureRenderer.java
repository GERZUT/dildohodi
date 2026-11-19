package com.example.longshoes;

import net.minecraft.client.model.ModelData;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.model.ModelPartBuilder;
import net.minecraft.client.model.ModelTransform;
import net.minecraft.client.model.PlayerEntityModel;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.command.OrderedRenderCommandQueue;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.state.PlayerEntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

/**
 * Draws a long pointed shoe on each of the player's feet.
 */
public class LongShoesFeatureRenderer extends FeatureRenderer<PlayerEntityRenderState, PlayerEntityModel> {

    private static final Identifier TEXTURE = Identifier.of("longshoes", "textures/entity/long_shoes.png");

    private final ModelPart shoe;

    public LongShoesFeatureRenderer(FeatureRendererContext<PlayerEntityRenderState, PlayerEntityModel> context) {
        super(context);
        this.shoe = buildShoe();
    }

    /**
     * Coordinates are in the leg's local pixel space: origin at the leg pivot,
     * Y points DOWN (the foot sole is at y = 12), negative Z is the front.
     * The toe is a stack of boxes that get narrower and lower toward the tip.
     * To make it even longer, add more segments or make the Z values more negative.
     */
    private static ModelPart buildShoe() {
        ModelData data = new ModelData();
        data.getRoot().addChild("shoe",
                ModelPartBuilder.create()
                        .uv(0, 0)
                        // heel + main body
                        .cuboid(-2.3f, 9.6f, -2.3f, 4.6f, 2.6f, 4.6f)
                        // toe segments, going forward (-Z), each narrower and lower than the last.
                        // Total toe length is about 41 pixels (~2.5 blocks).
                        .cuboid(-2.0f, 9.9f, -9.0f, 4.0f, 2.3f, 6.7f)
                        .cuboid(-1.7f, 10.2f, -17.0f, 3.4f, 2.0f, 8.0f)
                        .cuboid(-1.35f, 10.5f, -25.0f, 2.7f, 1.7f, 8.0f)
                        .cuboid(-1.0f, 10.8f, -33.0f, 2.0f, 1.4f, 8.0f)
                        .cuboid(-0.65f, 11.1f, -41.0f, 1.3f, 1.1f, 8.0f),
                ModelTransform.NONE);
        return TexturedModelData.of(data, 64, 64).createModel().getChild("shoe");
    }

    @Override
    public void render(MatrixStack matrices, OrderedRenderCommandQueue queue, int light,
                       PlayerEntityRenderState state, float limbAngle, float limbDistance) {
        if (state.invisible) {
            return;
        }

        PlayerEntityModel model = this.getContextModel();
        // Make sure the legs are posed for this exact render state (walking, sneaking, swimming...).
        model.setAngles(state);

        RenderLayer layer = RenderLayer.getEntityCutoutNoCull(TEXTURE);
        renderShoe(matrices, queue, light, model.leftLeg, layer);
        renderShoe(matrices, queue, light, model.rightLeg, layer);
    }

    private void renderShoe(MatrixStack matrices, OrderedRenderCommandQueue queue, int light,
                            ModelPart leg, RenderLayer layer) {
        matrices.push();
        leg.applyTransform(matrices);
        queue.submitModelPart(this.shoe, matrices, layer, light, OverlayTexture.DEFAULT_UV, null);
        matrices.pop();
    }
}
