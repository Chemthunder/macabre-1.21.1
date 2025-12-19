package silly.chemthunder.macabre.client.entity;

import com.nitron.nitrogen.Nitrogen;
import com.nitron.nitrogen.render.RenderUtils;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import silly.chemthunder.macabre.entity.ShockwaveEntity;

public class ShockwaveRenderer extends EntityRenderer<ShockwaveEntity> {
    public ShockwaveRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
    }

    @Override
    public Identifier getTexture(ShockwaveEntity entity) {
        return null;
    }

    @Override
    public void render(ShockwaveEntity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        matrices.push();

        matrices.translate(-entity.getX(), -entity.getY(), -entity.getZ());
        RenderUtils.renderTexturedSphere(
                matrices,
                vertexConsumers.getBuffer(RenderLayer.getEntityTranslucent(Identifier.of(Nitrogen.MOD_ID, "textures/render/color.png"), true)),
                entity.getBlockPos(),
                entity.ageTicks,
                entity.ageTicks,
                0
        );


        matrices.pop();
        super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);
    }
}
