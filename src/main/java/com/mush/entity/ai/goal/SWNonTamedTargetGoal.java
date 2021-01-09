package com.mush.entity.ai.goal;

import java.util.function.Predicate;

import com.mush.entity.SledWolfEntity;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.goal.NonTamedTargetGoal;
import net.minecraft.entity.passive.TameableEntity;

public class SWNonTamedTargetGoal<T extends LivingEntity> extends NonTamedTargetGoal<T> {
	
	private MobEntity entity;
	
	public SWNonTamedTargetGoal(TameableEntity entity, Class<T> p_i48571_2_, boolean p_i48571_3_, Predicate<LivingEntity> p_i48571_4_) {
		super(entity, p_i48571_2_, p_i48571_3_, p_i48571_4_);
		
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