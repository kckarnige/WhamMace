package com.kckarnige.wham.mixin;

import com.kckarnige.wham.config.MidnightConfigStuff;
import com.kckarnige.wham.enchantments.WhamEnchantment;
import com.kckarnige.wham.items.ModComponents;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.WindChargeEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.MaceItem;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import java.util.List;
import java.util.Objects;

@Mixin(MaceItem.class)
public abstract class MaceMixin extends Item {

    public MaceMixin(Settings settings) {
        super(settings);
    }

    public void inventoryTick(ItemStack nullStack, World world, Entity entity, int slot, boolean selected) {
        if (!world.isClient()) {
            if (entity instanceof PlayerEntity player) {
                if (player.getInventory().getMainHandStack().isOf(Items.MACE)) {
                    if (player.getMainHandStack().getMaxDamage() * 0.70 >= player.getMainHandStack().getMaxDamage() - player.getMainHandStack().getDamage()) {
                        player.getMainHandStack().remove(ModComponents.WIND_BOUNCE_READY);
                    } else {
                        player.getMainHandStack().set(ModComponents.WIND_BOUNCE_READY, true);
                    }
                }
            }
        }
    }

    @Unique
    private ActionResult CompleteAction (PlayerEntity player, Hand hand) {
        player.swingHand(hand, true);
        return ActionResult.SUCCESS;
    }

    @Override
    public ActionResult use(World world, PlayerEntity player, Hand hand) {
        if (!world.isClient()) {
            if (Objects.equals(String.valueOf(player.getFacing()), "down")) {
                HitResult hit = player.raycast(2.5, 0, false);
                if (hit.getType() == HitResult.Type.BLOCK) {
                    BlockHitResult blockHit = (BlockHitResult) hit;
                    if (Objects.equals(String.valueOf(blockHit.getSide()), "up")) {
                        if (!(player.getStackInHand(hand).getMaxDamage() * 0.70 >= player.getStackInHand(hand).getMaxDamage() - player.getStackInHand(hand).getDamage())) {

                            WindChargeEntity windCharge = new WindChargeEntity(EntityType.WIND_CHARGE, world);
                            windCharge.setPosition(player.getPos());
                            windCharge.setVelocity(0.0, -2.0, 0.0);

                            int WindSlamLV = EnchantmentHelper.getLevel(world.getRegistryManager().getOrThrow(RegistryKeys.ENCHANTMENT).getOrThrow(WhamEnchantment.WIND_CONTROL), player.getStackInHand(hand));
                            // The higher tier "Wind Bounce", the less damage the mace takes when used. "Unbreaking" enchantment lets the mace be used even longer.
                            if (WindSlamLV != 0) {
                                player.getItemCooldownManager().set(player.getStackInHand(hand), 10);
                                world.spawnEntity(windCharge);
                                switch (WindSlamLV) {
                                    case 1:
                                        // 4 uses until repair needed (8 with Unbreaking I)
                                        player.getStackInHand(hand).damage(38, player, LivingEntity.getSlotForHand(hand));
                                        if (MidnightConfigStuff.AIR_LIFT && MidnightConfigStuff.LOWER_LEVEL_LIFT) {
                                            Box myBox = new Box(player.getBlockPos()).expand(2.5);
                                            Entity target = null;
                                            List<MobEntity> entitiyList = player.getWorld().getEntitiesByClass(MobEntity.class, myBox, LivingEntity::isAlive);
                                            try {
                                                target = entitiyList.getFirst();
                                            } catch (Exception ignored) {
                                                try {
                                                    target = world.getClosestPlayer(player, 2.5);
                                                } catch (Exception ignored2) {
                                                }
                                            }
                                            if (target != null) {
                                                if (player.canSee(target)) {
                                                    target.setVelocity(0, 0.25, 0);
                                                }
                                            }
                                        }
                                        return CompleteAction(player, hand);
                                    case 2:
                                        // 6 uses until repair needed (12 with Unbreaking I)
                                        player.getStackInHand(hand).damage(25, player, LivingEntity.getSlotForHand(hand));
                                        if (MidnightConfigStuff.AIR_LIFT) {
                                            Box myBox = new Box(player.getBlockPos()).expand(2.5f);
                                            Entity target = null;
                                            List<MobEntity> entitiyList = player.getWorld().getEntitiesByClass(MobEntity.class, myBox, LivingEntity::isAlive);
                                            try {
                                                target = entitiyList.getFirst();
                                            } catch (Exception ignored) {
                                                try {
                                                    target = world.getClosestPlayer(player, 2.5f);
                                                } catch (Exception ignored2) {
                                                }
                                            }
                                            if (target != null) {
                                                target.addVelocity(0, 0.5, 0);
                                                player.addVelocity(0, 0.25, 0);
                                            }
                                        }
                                        return CompleteAction(player, hand);
                                }
                            } else {
                                if (MidnightConfigStuff.DEFAULT_BOUNCE) {
                                    // 2 uses until repair needed (4 with Unbreaking I)
                                    player.getStackInHand(hand).damage(78, player, LivingEntity.getSlotForHand(hand));
                                    player.getItemCooldownManager().set(player.getStackInHand(hand), 10);
                                    world.spawnEntity(windCharge);
                                    return CompleteAction(player, hand);
                                }
                            }
                        }
                    }
                }
            }
        }
        return ActionResult.FAIL;
    }

    @Override
    public void postDamageEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (MidnightConfigStuff.AIR_SLAM) {
            if (!target.isOnGround() && attacker.isPlayer() && !attacker.isOnGround()) {
                target.addVelocity(0.0,-4.0,0.0);
                target.fallDistance = 40;
                target.playSound(SoundEvents.ITEM_MACE_SMASH_AIR);
                target.damage((ServerWorld) target.getWorld(),target.getWorld().getDamageSources().maceSmash(attacker),12f);
            }
        }
    }
}