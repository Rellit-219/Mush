package com.mush.entity.ai.goal;

import java.util.EnumSet;

import com.mush.entity.ISledComponent;
import com.mush.entity.SledWolfEntity;
import com.mush.entity.item.SledEntity;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.pathfinding.PathNodeType;

public class LookAtSledComponentGoal extends Goal {
	
	private final MobEntity tameable;
	private MobEntity followingEntity;
	
	public LookAtSledComponentGoal(MobEntity tameable) {
		
		this.tameable = tameable;
		setMutexFlags(EnumSet.of(Goal.Flag.LOOK));
		
	}
	
	public boolean shouldExecute() {
		
		LivingEntity livingentity = (LivingEntity) ((ISledComponent) (tameable)).getFollowingEntity();
		
		if (livingentity == null) {
			
			return false;
			
		} else if (tameable.getDistanceSq(livingentity) > 144) {
			
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
		
		if (tameable.getDistanceSq(followingEntity) > 144) {
			
			return false;
			
		} else {
			
			return true;
			
		}
		
	}
	
	public void startExecuting() {
		
		tameable.setPathPriority(PathNodeType.WATER, 0.0F);
		
	}
	
	public void resetTask() {
		
		followingEntity = null;
		
	}

	public void tick() {
		
		tameable.getLookController().setLookPositionWithEntity(followingEntity, 10.0F, (float)tameable.getVerticalFaceSpeed());
		
		if (tameable instanceof SledEntity) {
			
			if (tameable.getControllingPassenger() != null) {
				
				((SledEntity) tameable).updatePassenger(tameable.getControllingPassenger());
				
			}
			
		}
		
	}
	
}