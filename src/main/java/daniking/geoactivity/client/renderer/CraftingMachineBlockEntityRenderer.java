package daniking.geoactivity.client.renderer;

import daniking.geoactivity.common.block.CraftingMachineBlock;
import daniking.geoactivity.common.block.entity.CraftingMachineBlockEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3f;

@Environment(EnvType.CLIENT)
public class CraftingMachineBlockEntityRenderer implements BlockEntityRenderer<CraftingMachineBlockEntity> {

    @Override
    public void render(CraftingMachineBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {

        final String rotation = entity.getCachedState().get(CraftingMachineBlock.FACING).asString();

        int deg = switch (rotation) {
            case "east" -> 90;
            case "west" -> 270;
            case "north" -> 180;
            default -> 0;
        };

        float d = 4.3F; //decrease
        float offset = 3.5F;
        for (float x = -1f / (d / offset), i = 0; x <= 1f / (d / offset); x += 1f / (d / offset)) {
            for (float z = -1f / (d / offset); z <= 1f / (d / offset); z += 1f / (d / offset), ++i) {
                final ItemStack stack = entity.getStack((int) i);
                if (stack.isEmpty()) {
                    continue;
                }
                matrices.push();
                matrices.scale(1f / d, 1f / d, 1f / d);
                matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(deg));
                final boolean isBlock = Block.getBlockFromItem(stack.getItem()) != Blocks.AIR;
                float yOffset = isBlock ? 0.25F : 0.0F;
                matrices.translate(d/2f + z, d + yOffset, d/2f + x);

                switch (deg) {
                    case 0:
                        break;
                    case 90:
                        matrices.translate(-d, 0,0);
                        break;
                    case 180:
                        matrices.translate(-d, 0, -d);
                        break;
                    default:
                        matrices.translate(0, 0, -d);
                        break;
                }
                matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(90));
                matrices.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(180));
                if (!isBlock) {
                    matrices.scale(0.8F, 0.8F, 0.8F);
                }
                final int lightAbove = WorldRenderer.getLightmapCoordinates(entity.getWorld(), entity.getPos().up());
                MinecraftClient.getInstance().getItemRenderer().renderItem(stack, ModelTransformation.Mode.FIXED, lightAbove, overlay, matrices, vertexConsumers, 0);
                matrices.pop();
            }
        }
    }
}
