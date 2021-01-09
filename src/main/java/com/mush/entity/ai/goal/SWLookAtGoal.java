package com.mush.entity.ai.goal;

import com.mush.entity.SledWolfEntity;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.goal.LookAtGoal;

public class SWLookAtGoal extends LookAtGoal {
	
	private MobEntity entity;
	
	public SWLookAtGoal(MobEntity entity, Class<? extends LivingEntity> p_i1631_2_, float p_i1631_3_) {
		super(entity, p_i1631_2_, p_i1631_3_);
		
		this.entity = entity;
		
	}
	
	@Override
	public boolean shouldExecute() {
		
		if (entity instanceof SledWolfEntity) {
			
			if (((SledWolfEntity) entity).getSledEntity() != null && ((SledWolfEntity) entity).getSledEntityUUID() != null) {
				
				if (((SledWolfEntity) entity).getSledEntity().getControllingPassenger() != null) {
					
					return false;
					
				}
				
			}
			
		}
		
		return super.shouldExecute();
		
	}
	
}