package com.mush.entity;

import java.util.UUID;

import javax.annotation.Nullable;

import com.mush.entity.item.SledEntity;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.server.ServerWorld;

public interface ISledComponent {
	
	@Nullable
	UUID getFollowingEntityUUID();
	
	@Nullable
	SledWolfEntity getFollowingEntity();
	
	@Nullable
	UUID getSledEntityUUID();
	
	@Nullable
	SledEntity getSledEntity();
	
	void setFollowingEntityUUID(@Nullable UUID uuid);
	
	void setSledEntityUUID(@Nullable UUID uuid);
	
	default void writeSledComponentNBT(CompoundNBT compound) {
		
		if (this.getFollowingEntityUUID() != null) {
			
			compound.putUniqueId("Following", this.getFollowingEntityUUID());
			
		}
		
		if (this.getSledEntityUUID() != null) {
			
			compound.putUniqueId("Sled", this.getSledEntityUUID());
			
		}
		
	}
	
	default void readSledComponentNBT(ServerWorld world, CompoundNBT compound) {
		
		if (!compound.hasUniqueId("Following")) {
			
			this.setFollowingEntityUUID((UUID)null);
			
		} else {
			
			UUID uuid = compound.getUniqueId("Following");
			this.setFollowingEntityUUID(uuid);
			setFollowingEntity((SledWolfEntity) world.getEntityByUuid(uuid));
			
		}
		
		if (!compound.hasUniqueId("Sled")) {
			
			this.setSledEntityUUID((UUID)null);
			
		} else {
			
			UUID uuid = compound.getUniqueId("Sled");
			this.setSledEntityUUID(uuid);
			setSledEntity((SledEntity) world.getEntityByUuid(uuid));
			
		}
		
	}
	
	void setFollowingEntity(@Nullable SledWolfEntity entity);
	
	void setSledEntity(@Nullable SledEntity entity);
	
}