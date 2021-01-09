package com.mush.entity.ai.goal;

import com.mush.entity.SledWolfEntity;

import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;

public class SWLookRandomlyGoal extends LookRandomlyGoal {
	
	private MobEntity entity;
	
	public SWLookRandomlyGoal(MobEntity entity) {
		super(entity);
		
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