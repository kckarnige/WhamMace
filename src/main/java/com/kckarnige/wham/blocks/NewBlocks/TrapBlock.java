package com.kckarnige.wham.blocks.NewBlocks;

import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.damage.DamageScaling;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class TrapBlock extends SweetBerryBushBlock {
    //public static final DamageType spikeDmgSrc = new DamageType("spikes", DamageScaling.WHEN_CAUSED_BY_LIVING_NON_PLAYER, 0.1F);
    //public static final DamageSource SPIKE_DMG = new DamageSource(RegistryEntry.of(spikeDmgSrc));
    public TrapBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (world instanceof ServerWorld serverWorld)
            if (entity instanceof LivingEntity livingEntity) {
                if (!livingEntity.isSneaking()) {
                    livingEntity.slowMovement(state, new Vec3d(0.8F, 0.75, 0.8F));
                    livingEntity.damage(serverWorld, world.getDamageSources().sweetBerryBush(),1.0f);
                }
            }
        super.onSteppedOn(world, pos, state, entity);
    }
}
