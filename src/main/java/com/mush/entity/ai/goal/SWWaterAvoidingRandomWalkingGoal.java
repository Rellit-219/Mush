package com.mush.entity.ai.goal;

import com.mush.entity.SledWolfEntity;

import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.goal.WaterAvoidingRandomWalkingGoal;

public class SWWaterAvoidingRandomWalkingGoal extends WaterAvoidingRandomWalkingGoal {
	
	private MobEntity entity;
	
	public SWWaterAvoidingRandomWalkingGoal(CreatureEntity entity, double p_i47301_2_) {
		super(entity, p_i47301_2_);
		
		this.entity = entity;
		
	}

	@Override
	public boolean shouldExecute() {
		
		if (entity instanceof SledWolfEntity) {
			
			if (((SledWolfEntity) entity).getFollowingEntity() != null && ((SledWolfEntity) entity).getFollowingEntityUUID() != null) {
				
				return false;
				
			}
			
		}
		
		return super.shouldExecute();
		
	}
	
}