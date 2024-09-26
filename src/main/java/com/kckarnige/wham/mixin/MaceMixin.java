package com.kckarnige.wham.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.WindChargeEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MaceItem;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

import java.util.Objects;

@Mixin(MaceItem.class)
public abstract class MaceMixin extends Item {

    public MaceMixin(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        if (!world.isClient()) {
            if (Objects.equals(String.valueOf(player.getFacing()), "down")) {
                HitResult hit = player.raycast(2.5, 0, false); // 20 is distance used by the DebugHud for "looking at block", false means ignore fluids
                if (hit.getType() == HitResult.Type.BLOCK) {
                    BlockHitResult blockHit = (BlockHitResult) hit;
                    if (Objects.equals(String.valueOf(blockHit.getSide()), "up")) {
                        if (!(player.getStackInHand(hand).getMaxDamage() * 0.70 >= player.getStackInHand(hand).getMaxDamage() - player.getStackInHand(hand).getDamage())) {
                            player.getItemCooldownManager().set(this, 10);

                            WindChargeEntity windCharge = new WindChargeEntity(EntityType.WIND_CHARGE, world);
                            windCharge.setPosition(player.getPos());
                            windCharge.setVelocity(0.0,-2.0,0.0);
                            world.spawnEntity(windCharge);

                            player.getStackInHand(hand).damage(60, player, LivingEntity.getSlotForHand(hand));
                            return TypedActionResult.success(player.getStackInHand(hand));
                        }
                    }
                }
            }
        }
        return TypedActionResult.fail(player.getStackInHand(hand));
    }
}