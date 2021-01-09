package com.mush.entity.ai.goal;

import java.util.function.Predicate;

import com.mush.entity.SledWolfEntity;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;

public class SWNearestAttackableTargetGoal<T extends LivingEntity> extends NearestAttackableTargetGoal<T> {
	
	private MobEntity entity;
	
	public SWNearestAttackableTargetGoal(MobEntity entity, Class<T> p_i50315_2_, int p_i50315_3_, boolean p_i50315_4_, boolean p_i50315_5_, Predicate<LivingEntity> p_i50315_6_) {
		super(entity, p_i50315_2_, p_i50315_3_, p_i50315_4_, p_i50315_5_, p_i50315_6_);
		
		this.entity = entity;
		
	}
	
	public SWNearestAttackableTargetGoal(MobEntity entity, Class<T> p_i50315_2_, boolean p_i50315_4_) {
		super(entity, p_i50315_2_, p_i50315_4_);
		
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