package com.mush.entity.ai.goal;

import com.mush.entity.SledWolfEntity;

import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.goal.OwnerHurtTargetGoal;
import net.minecraft.entity.passive.TameableEntity;

public class SWOwnerHurtTargetGoal extends OwnerHurtTargetGoal {
	
	private MobEntity entity;
	
	public SWOwnerHurtTargetGoal(TameableEntity entity) {
		super(entity);
		
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