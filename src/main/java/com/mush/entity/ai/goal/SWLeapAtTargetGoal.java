package com.mush.entity.ai.goal;

import com.mush.entity.SledWolfEntity;

import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.goal.LeapAtTargetGoal;

public class SWLeapAtTargetGoal extends LeapAtTargetGoal {
	
	private MobEntity entity;
	
	public SWLeapAtTargetGoal(MobEntity entity, float p_i1630_2_) {
		super(entity, p_i1630_2_);
		
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