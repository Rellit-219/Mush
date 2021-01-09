package com.mush.entity.ai.goal;

import java.util.EnumSet;

import com.mush.entity.ISledComponent;
import com.mush.entity.SledWolfEntity;
import com.mush.entity.item.SledEntity;

import net.minecraft.block.BlockState;
import net.minecraft.block.LeavesBlock;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.pathfinding.FlyingPathNavigator;
import net.minecraft.pathfinding.GroundPathNavigator;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.pathfinding.WalkNodeProcessor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;

public class FollowSledComponentGoal extends Goal {
	
	private final MobEntity tameable;
	private MobEntity followingEntity;
	private final IWorldReader world;
	private final double followSpeed;
	private final PathNavigator navigator;
	private int timeToRecalcPath;
	private final float maxDist;
	private final float minDist;
	private float oldWaterCost;
	private final boolean field_226326_j_;

	public FollowSledComponentGoal(MobEntity tameable, double followSpeed, float minDist, float maxDist, boolean p_i225711_6_) {
		
		this.tameable = tameable;
		world = tameable.world;
		this.followSpeed = followSpeed;
		navigator = tameable.getNavigator();
		this.minDist = minDist;
		this.maxDist = maxDist;
		field_226326_j_ = p_i225711_6_;
		setMutexFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
		
		if (!(tameable.getNavigator() instanceof GroundPathNavigator) && !(tameable.getNavigator() instanceof FlyingPathNavigator)) {
			
			throw new IllegalArgumentException("Unsupported mob type for FollowSledComponentGoal");
			
		}
	}
	
	public boolean shouldExecute() {
		
		LivingEntity livingentity = (LivingEntity) ((ISledComponent) (tameable)).getFollowingEntity();
		
		if (livingentity == null) {
			
			return false;
			
		} else if (tameable.getDistanceSq(livingentity) < (double)(minDist * minDist)) {
			
			return false;
			
		} else if (tameable instanceof SledWolfEntity) {
			
			if (((SledWolfEntity) tameable).getSledEntity() == null) {
				
				return false;
				
			}
			
		}
		
		followingEntity = (MobEntity) livingentity;
		return true;
		
	}
	
	public boolean shouldContinueExecuting() {
		
		if (navigator.noPath()) {
			
			return false;
			
		} else {
			
			return !(tameable.getDistanceSq(followingEntity) <= (double)(maxDist * maxDist));
			
		}
		
	}
	
	public void startExecuting() {
		
		timeToRecalcPath = 0;
		oldWaterCost = tameable.getPathPriority(PathNodeType.WATER);
		tameable.setPathPriority(PathNodeType.WATER, 0.0F);
		
	}
	
	public void resetTask() {
		
		followingEntity = null;
		navigator.clearPath();
		tameable.setPathPriority(PathNodeType.WATER, oldWaterCost);
		
	}

	public void tick() {
		
		tameable.getLookController().setLookPositionWithEntity(followingEntity, 10.0F, (float)tameable.getVerticalFaceSpeed());
		
		if (tameable instanceof SledEntity) {
			
			if (tameable.getControllingPassenger() != null) {
				
			  ((SledEntity) tameable).updatePassenger(tameable.getControllingPassenger());
				
			}
			
		}
		
		if (--timeToRecalcPath <= 0) {
			
			timeToRecalcPath = 10;
			
			if (!tameable.isPassenger()) {
				
				if (tameable.getDistanceSq(followingEntity) >= 144.0D) {
					
					teleport();
					
				} else {
					
					if (followingEntity != null) {
						
						navigator.tryMoveToEntityLiving(followingEntity, (((ISledComponent) tameable).getSledEntity().getDataManager().get(SledEntity.WOLF_COUNT) * 0.25F) + followSpeed);
					 
					}
					
				}
				
			}
			
		}
		
	}
	
	private void teleport() {
		
		BlockPos blockpos = followingEntity.getPosition();

		for (int i = 0; i < 10; ++i) {
			
			int j = getRandomPosInRange(-3, 3);
			int k = getRandomPosInRange(-1, 1);
			int l = getRandomPosInRange(-3, 3);
			boolean flag = canTeleport(blockpos.getX() + j, blockpos.getY() + k, blockpos.getZ() + l);
			
			if (flag) {
				
				return;
				
			}
			
		}
		
	}
	
	private boolean canTeleport(int p_226328_1_, int p_226328_2_, int p_226328_3_) {
		
		if (Math.abs((double)p_226328_1_ - followingEntity.getPosX()) < 2.0D && Math.abs((double)p_226328_3_ - followingEntity.getPosZ()) < 2.0D) {
			
			return false;
			
		} else if (!canTeleportOnBlock(new BlockPos(p_226328_1_, p_226328_2_, p_226328_3_))) {
			
			return false;
			
		} else {
			
			tameable.setLocationAndAngles((double)p_226328_1_ + 0.5D, (double)p_226328_2_, (double)p_226328_3_ + 0.5D, tameable.rotationYaw, tameable.rotationPitch);
			navigator.clearPath();
			return true;
			
		}
		
	}
	
	private boolean canTeleportOnBlock(BlockPos blockPos) {
		
		PathNodeType pathnodetype = WalkNodeProcessor.func_237231_a_(world, blockPos.toMutable());
		
		if (pathnodetype != PathNodeType.WALKABLE) {
			
			return false;
			
		} else {
			
			BlockState blockstate = world.getBlockState(blockPos.down());
			if (!field_226326_j_ && blockstate.getBlock() instanceof LeavesBlock) {
				
				return false;
				
			} else {
				
				BlockPos blockpos = blockPos.subtract(tameable.getPosition());
				return world.hasNoCollisions(tameable, tameable.getBoundingBox().offset(blockpos));
				
			}
			
		}
		
	}
	
	private int getRandomPosInRange(int lower, int higher) {
		
		return tameable.getRNG().nextInt(higher - lower + 1) + lower;
		
	}
	
}