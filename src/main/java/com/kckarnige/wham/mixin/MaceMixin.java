package com.kckarnige.wham.mixin;

import com.kckarnige.wham.config.MidnightConfigStuff;
import com.kckarnige.wham.enchantments.WhamEnchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.WindChargeEntity;
import net.minecraft.item.Item;
import net.minecraft.item.MaceItem;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
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
    public ActionResult use(World world, PlayerEntity player, Hand hand) {
        if (!world.isClient()) {
            if (Objects.equals(String.valueOf(player.getFacing()), "down")) {
                HitResult hit = player.raycast(2.5, 0, false); // 20 is distance used by the DebugHud for "looking at block", false means ignore fluids
                if (hit.getType() == HitResult.Type.BLOCK) {
                    BlockHitResult blockHit = (BlockHitResult) hit;
                    if (Objects.equals(String.valueOf(blockHit.getSide()), "up")) {
                        if (!(player.getStackInHand(hand).getMaxDamage() * 0.70 >= player.getStackInHand(hand).getMaxDamage() - player.getStackInHand(hand).getDamage())) {
                            WindChargeEntity windCharge = new WindChargeEntity(EntityType.WIND_CHARGE, world);
                            windCharge.setPosition(player.getPos());
                            windCharge.setVelocity(0.0, -2.0, 0.0);

                            int WindSlamLV = EnchantmentHelper.getLevel(world.getRegistryManager().getOrThrow(RegistryKeys.ENCHANTMENT).getOrThrow(WhamEnchantment.WIND_BOUNCE), player.getStackInHand(hand));
                            // The higher tier "Wind Slam", the less damage the mace takes when used. "Unbreaking" enchantment lets the mace be used even longer.
                            if (WindSlamLV != 0) {
                                player.getItemCooldownManager().set(player.getStackInHand(hand), 10);
                                world.spawnEntity(windCharge);
                                switch (WindSlamLV) {
                                    case 1:
                                        // 4 uses until repair needed (8 with Unbreaking I)
                                        player.getStackInHand(hand).damage(38, player, LivingEntity.getSlotForHand(hand));
                                        player.swingHand(hand, true);
                                        return ActionResult.SUCCESS;
                                    case 2:
                                        // 6 uses until repair needed (12 with Unbreaking I)
                                        player.getStackInHand(hand).damage(25, player, LivingEntity.getSlotForHand(hand));
                                        player.swingHand(hand, true);
                                        return ActionResult.SUCCESS;
                                }
                            } else {
                                if (MidnightConfigStuff.DEFAULT_BOUNCE) {
                                    // 2 uses until repair needed (4 with Unbreaking I)
                                    player.getStackInHand(hand).damage(78, player, LivingEntity.getSlotForHand(hand));
                                    player.getItemCooldownManager().set(player.getStackInHand(hand), 10);
                                    world.spawnEntity(windCharge);
                                    player.swingHand(hand, true);
                                    return ActionResult.SUCCESS;
                                }
                            }
                        }
                    }
                }
            }
        }
        return ActionResult.FAIL;
    }
}