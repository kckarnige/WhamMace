package com.kckarnige.wham.mixin;

import com.kckarnige.wham.config.MainConfig;
import com.kckarnige.wham.enchantments.WhamEnchantment;
import com.kckarnige.wham.wham;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.WindChargeEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MaceItem;
import net.minecraft.registry.RegistryKeys;
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
                            WindChargeEntity windCharge = new WindChargeEntity(EntityType.WIND_CHARGE, world);
                            windCharge.setPosition(player.getPos());
                            windCharge.setVelocity(0.0, -2.0, 0.0);

                            if (MainConfig.DEFAULT_BOUNCE.getBoolean() && !player.isSneaking()) {
                                // 4 uses until repair needed (8 with Unbreaking I, 17 with Unbreaking III)
                                player.getStackInHand(hand).damage(38, player, LivingEntity.getSlotForHand(hand));
                                player.getItemCooldownManager().set(this, 10);
                                world.spawnEntity(windCharge);
                                return TypedActionResult.success(player.getStackInHand(hand));
                            } else if (MainConfig.DEFAULT_BOUNCE.getBoolean() && !MainConfig.BIG_BOUNCE.getBoolean()) {
                                // 4 uses until repair needed (8 with Unbreaking I, 17 with Unbreaking III)
                                player.getStackInHand(hand).damage(38, player, LivingEntity.getSlotForHand(hand));
                                player.getItemCooldownManager().set(this, 10);
                                world.spawnEntity(windCharge);
                                return TypedActionResult.success(player.getStackInHand(hand));
                            }
                            // The higher tier "Wind Slam", the less damage the mace takes when used. "Unbreaking" enchantment lets the mace be used even longer.
                            int WindSlamLV = EnchantmentHelper.getLevel(world.getRegistryManager().getWrapperOrThrow(RegistryKeys.ENCHANTMENT).getOrThrow(WhamEnchantment.WIND_BOUNCE), player.getStackInHand(hand));
                            if (WindSlamLV != 0) {

                                if (player.isSneaking() && MainConfig.BIG_BOUNCE.getBoolean()) {
                                    player.getItemCooldownManager().set(this, 30);

                                    WindChargeEntity windCharge2 = new WindChargeEntity(EntityType.BREEZE_WIND_CHARGE, world);
                                    windCharge2.setPosition(player.getPos());
                                    windCharge2.setVelocity(0.0, -2.0, 0.0);
                                    world.spawnEntity(windCharge2);
                                    world.spawnEntity(windCharge);

                                    switch (WindSlamLV) {
                                        case 1:
                                            player.getStackInHand(hand).damage(76, player, LivingEntity.getSlotForHand(hand));
                                            return TypedActionResult.success(player.getStackInHand(hand));
                                        case 2:
                                            player.getStackInHand(hand).damage(64, player, LivingEntity.getSlotForHand(hand));
                                            return TypedActionResult.success(player.getStackInHand(hand));
                                    }
                                }
                                else {
                                    player.getItemCooldownManager().set(this, 10);
                                    world.spawnEntity(windCharge);


                                    if (MainConfig.DEFAULT_BOUNCE.getBoolean()) {
                                        switch (WindSlamLV) {
                                            case 1:
                                                // 5 uses until repair needed (10 with Unbreaking I, 19 with Unbreaking III)
                                                player.getStackInHand(hand).damage(32, player, LivingEntity.getSlotForHand(hand));
                                                return TypedActionResult.success(player.getStackInHand(hand));
                                            case 2:
                                                // 6 uses until repair needed (12 with Unbreaking I, 23 with Unbreaking III)
                                                player.getStackInHand(hand).damage(25, player, LivingEntity.getSlotForHand(hand));
                                                return TypedActionResult.success(player.getStackInHand(hand));
                                        }
                                    } else {
                                        switch (WindSlamLV) {
                                            case 1:
                                                // 4 uses until repair needed (8 with Unbreaking I, 17 with Unbreaking III)
                                                player.getStackInHand(hand).damage(38, player, LivingEntity.getSlotForHand(hand));
                                                return TypedActionResult.success(player.getStackInHand(hand));
                                            case 2:
                                                // 5 uses until repair needed (10 with Unbreaking I, 19 with Unbreaking III)
                                                player.getStackInHand(hand).damage(32, player, LivingEntity.getSlotForHand(hand));
                                                return TypedActionResult.success(player.getStackInHand(hand));
                                        }
                                    }

                                }
                            }
                        }
                    }
                }
            }
        }
        return TypedActionResult.fail(player.getStackInHand(hand));
    }
}