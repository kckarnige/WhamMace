package com.kckarnige.wham.mixin.client;

import com.kckarnige.wham.config.MainConfig;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import static com.kckarnige.wham.wham.MOD_ID;

@Mixin(ItemRenderer.class)
public abstract class ItemRendererMixin {
    @ModifyVariable(method = "renderItem", at = @At(value = "HEAD"), argsOnly = true)
    public BakedModel useMaceModel(BakedModel value, ItemStack stack, ModelTransformationMode renderMode, boolean leftHanded, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        if (MainConfig.MACE_MODEL.getBoolean()) {
        if (stack.isOf(Items.MACE) && renderMode != ModelTransformationMode.GUI && renderMode != ModelTransformationMode.GROUND && renderMode != ModelTransformationMode.FIXED) {
            if (MainConfig.MACE_WINDU_4EVS.getBoolean()) {
                return ((ItemRendererAccessor) this).macebut3d$getModels().getModelManager().getModel(ModelIdentifier.ofInventoryVariant(Identifier.of(MOD_ID, "mace_hand_wind")));
            } else {
                if (!(stack.getMaxDamage() * 0.70 >= stack.getMaxDamage() - stack.getDamage()) && MainConfig.MACE_WINDU.getBoolean()) {
                    return ((ItemRendererAccessor) this).macebut3d$getModels().getModelManager().getModel(ModelIdentifier.ofInventoryVariant(Identifier.of(MOD_ID, "mace_hand_wind")));
                } else {
                    return ((ItemRendererAccessor) this).macebut3d$getModels().getModelManager().getModel(ModelIdentifier.ofInventoryVariant(Identifier.of(MOD_ID, "mace_hand")));
                }
            }
        }}
        return value;
    }
}
