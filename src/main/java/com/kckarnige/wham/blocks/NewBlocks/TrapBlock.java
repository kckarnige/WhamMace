package com.kckarnige.wham.blocks.NewBlocks;

import net.minecraft.block.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;

public class TrapBlock extends Block {
    public static final RegistryKey<DamageType> SPIKE_DMG = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, Identifier.of("wham", "spikes"));
    public static final RegistryKey<DamageType> SPIKE_FALL_DMG = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, Identifier.of("wham", "spikes_fall"));
    public TrapBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        BlockState floor = world.getBlockState(pos.down());
        return !floor.isOf(Blocks.AIR) && floor.isSideSolidFullSquare(world,pos,Direction.UP) || floor.isOf(Blocks.HOPPER);
    }

    @Override
    protected boolean canPathfindThrough(BlockState state, NavigationType type) {
        return true;
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (entity instanceof LivingEntity) {
            if (world instanceof ServerWorld serverWorld) {
                if (!entity.isSneaking() && entity.isOnGround()) {
                    entity.damage(serverWorld.getDamageSources().create(SPIKE_DMG), 1.5F);
                }
                entity.handleFallDamage(entity.fallDistance + 2.0F, 2.0F, entity.getDamageSources().create(SPIKE_FALL_DMG));
            }
        }
    }

    @Override
    protected VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return Block.createCuboidShape(1.5, 0.0, 1.5, 14.5, 3.0, 14.5);
    }

}
