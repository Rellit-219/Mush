package com.mush.entity.ai.goal;

import java.util.EnumSet;

import com.mush.entity.ISledComponent;
import com.mush.entity.SledWolfEntity;
import com.mush.entity.item.SledEntity;

import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.pathfinding.FlyingPathNavigator;
import net.minecraft.pathfinding.GroundPathNavigator;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.util.math.BlockPos;

public class SledLeaderControllingGoal extends Goal {
	
	private final SledWolfEntity tameable;
	private final double followSpeed;
	private final PathNavigator navigator;
	private int timeToRecalcPath;
	private float oldWaterCost;

	public SledLeaderControllingGoal(SledWolfEntity p_i225711_1_, double p_i225711_2_, float p_i225711_4_, float p_i225711_5_, boolean p_i225711_6_) {
		
		this.tameable = p_i225711_1_;
		this.followSpeed = p_i225711_2_;
		this.navigator = p_i225711_1_.getNavigator();
		this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
		if (!(p_i225711_1_.getNavigator() instanceof GroundPathNavigator) && !(p_i225711_1_.getNavigator() instanceof FlyingPathNavigator)) {
			
			throw new IllegalArgumentException("Unsupported mob type for SledLeaderControllingGoal");
			
		}
		
	}
	
	public boolean shouldExecute() {
		if (tameable.getSledEntity() == null) {
			
			return false;
			
		} else if (!tameable.getSledEntity().isBeingRidden()) {
			
			return false;
			
		} else if (tameable.getFollowingEntity() != null) {
			
			return false;
			
		} else {
			
			return true;
			
		}
		
	}
	
	public boolean shouldContinueExecuting() {
		
		if (tameable.getSledEntity() != null) {
			
			if (!tameable.getSledEntity().isBeingRidden()) {
				
				return false;
				
			}
			
		} else {
			
			return false;
			
		}
		
		return true;
		
	}
	
	public void startExecuting() {
		
		this.timeToRecalcPath = 0;
		this.oldWaterCost = this.tameable.getPathPriority(PathNodeType.WATER);
		this.tameable.setPathPriority(PathNodeType.WATER, 0.0F);
		
	}
	
	public void resetTask() {
		
		this.navigator.clearPath();
		this.tameable.setPathPriority(PathNodeType.WATER, this.oldWaterCost);
		
	}

	public void tick() {
		
		BlockPos targetPos = tameable.getPosition();
		
		if (--this.timeToRecalcPath <= 0) {
			
			this.timeToRecalcPath = 10;
			
			if (!this.tameable.isPassenger()) {
				
				switch (tameable.getHorizontalFacing()) {
		 		
		 		case NORTH:
		 			targetPos = targetPos.north();
		 			break;
		 		case SOUTH:
		 			targetPos = targetPos.south();
		 			break;
		 		case WEST:
		 			targetPos = targetPos.west();
		 			break;
		 		case EAST:
		 			targetPos = targetPos.east();
		 			break;
		 		default:
		 			break;
		 		
		 		}
				
				if (tameable.getSledEntity().forwardInputDown) {
					
					this.navigator.tryMoveToXYZ(targetPos.getX(), targetPos.getY() + 1, targetPos.getZ(), (((ISledComponent) this.tameable).getSledEntity().getDataManager().get(SledEntity.WOLF_COUNT) * 0.25F) + this.followSpeed);
		 			
		 		}
		 		
			}
			
		}
		
	}
	
}