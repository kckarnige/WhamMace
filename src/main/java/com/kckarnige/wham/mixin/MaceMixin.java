package com.kckarnige.wham.mixin;

import com.kckarnige.wham.wham;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MaceItem;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
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
                HitResult hit = player.raycast(2, 0, false); // 20 is distance used by the DebugHud for "looking at block", false means ignore fluids
                if (hit.getType() == HitResult.Type.BLOCK) {
                    BlockHitResult blockHit = (BlockHitResult) hit;
                    if (Objects.equals(String.valueOf(blockHit.getSide()), "up")) {
                        if (!(player.getStackInHand(hand).getMaxDamage() - 120 == player.getStackInHand(hand).getMaxDamage() - player.getStackInHand(hand).getDamage())) {
                            wham.LOGGER.info(String.valueOf(player.getStackInHand(hand).getMaxDamage() - player.getStackInHand(hand).getDamage()+" of "+player.getStackInHand(hand).getMaxDamage()));
                            world.playSound(null, player.getBlockPos(), SoundEvent.of(Identifier.of("minecraft:entity.wind_charge.wind_burst")), SoundCategory.PLAYERS);
                            world.addParticle(ParticleTypes.HEART, player.getX(), player.getY() + 2.0, player.getZ(), 0.0, 0.0, 0.0);
                            player.addStatusEffect(new StatusEffectInstance(StatusEffects.LEVITATION, 4, 20, false, false, false));
                            player.getStackInHand(hand).damage(40, player, LivingEntity.getSlotForHand(hand));
                            return TypedActionResult.success(player.getStackInHand(hand));
                        }
                    }
                }
            }
        }
        return TypedActionResult.fail(player.getStackInHand(hand));
    }
}