package com.mush.entity.ai.goal;

import com.mush.entity.SledWolfEntity;

import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.goal.FollowOwnerGoal;
import net.minecraft.entity.passive.TameableEntity;

public class SWFollowOwnerGoal extends FollowOwnerGoal {
	
	private MobEntity entity;
	
	public SWFollowOwnerGoal(TameableEntity entity, double p_i225711_2_, float p_i225711_4_, float p_i225711_5_, boolean p_i225711_6_) {
		super(entity, p_i225711_2_, p_i225711_4_, p_i225711_5_, p_i225711_6_);
		
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