package com.mush.entity.item;

import java.util.Optional;
import java.util.UUID;

import javax.annotation.Nullable;

import com.mush.entity.ISledComponent;
import com.mush.entity.MEntityType;
import com.mush.entity.SledWolfEntity;
import com.mush.entity.ai.goal.FollowSledComponentGoal;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap.MutableAttribute;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkHooks;

public class SledEntity extends MobEntity implements ISledComponent {
	
	public static final DataParameter<Integer> WOLF_COUNT = EntityDataManager.createKey(SledEntity.class, DataSerializers.VARINT);
	public static final DataParameter<Integer> MAXIMUM_WOLF_COUNT = EntityDataManager.createKey(SledEntity.class, DataSerializers.VARINT);
	protected static final DataParameter<Optional<UUID>> FOLLOWING_UNIQUE_ID = EntityDataManager.createKey(SledEntity.class, DataSerializers.OPTIONAL_UNIQUE_ID);
	
	public boolean leftInputDown;
	public boolean rightInputDown;
	public boolean forwardInputDown;
	public boolean backInputDown;
	
	private float deltaRotation;
	
	private SledWolfEntity followingEntity;
	
	public SledEntity(EntityType<? extends MobEntity> entity, World world) {
		super(entity, world);
		
	}
	
	public SledEntity(World world) {
		super(MEntityType.SLED.get(), world);
		
	}
	
	protected void registerData() {
		super.registerData();
		
		this.dataManager.register(WOLF_COUNT, 0);
		this.dataManager.register(MAXIMUM_WOLF_COUNT, 4);
		this.dataManager.register(FOLLOWING_UNIQUE_ID, Optional.empty());
		
		recalculateWolfCount();
		
	}
	
	@Override
	public IPacket<?> createSpawnPacket() {
		
		return NetworkHooks.getEntitySpawningPacket(this);
		
	}
	
	@Override
	public UUID getFollowingEntityUUID() {
		
		return dataManager.get(FOLLOWING_UNIQUE_ID).orElse(null);
		
	}
	
	@Override
	public SledWolfEntity getFollowingEntity() {
		
		return followingEntity;
		
	}

	@Override
	public UUID getSledEntityUUID() {
		
		return getUniqueID();
		
	}

	@Override
	public SledEntity getSledEntity() {
		
		return this;
		
	}

	@Override
	public void setFollowingEntityUUID(UUID uuid) {
		
		dataManager.set(FOLLOWING_UNIQUE_ID, Optional.ofNullable(uuid));
		
	}

	@Override
	public void setSledEntityUUID(UUID uuid) {}

	@Override
	public void setFollowingEntity(SledWolfEntity entity) {
		
		followingEntity = entity;
		
	}

	@Override
	public void setSledEntity(SledEntity entity) {}
	
	public void writeAdditional(CompoundNBT compound) {
		super.writeAdditional(compound);
		
		compound.putInt("wolfCount", dataManager.get(WOLF_COUNT));
		compound.putInt("maximumWolfCount", dataManager.get(MAXIMUM_WOLF_COUNT));
		this.writeSledComponentNBT(compound);
		
	}
	
	public void readAdditional(CompoundNBT compound) {
		super.readAdditional(compound);
		
		dataManager.set(WOLF_COUNT, compound.getInt("wolfCount"));
		dataManager.set(MAXIMUM_WOLF_COUNT, compound.getInt("maximumWolfCount"));
		this.readSledComponentNBT((ServerWorld)this.world, compound);
		
	}
	
	public void tick() {
		super.tick();
		
		if (!getEntityWorld().isRemote()) {
			
			if (getFollowingEntityUUID() != null && getFollowingEntity() == null) {
				
				setFollowingEntity((SledWolfEntity) ((ServerWorld) getEntityWorld()).getEntityByUuid(getFollowingEntityUUID()));
				
			}
			
			if (getFollowingEntityUUID() == null && getFollowingEntity()!= null) {
				
				setFollowingEntityUUID(getFollowingEntity().getUniqueID());
				
			}
			
			if (this.getFollowingEntity() != null) {
				
				if (getFollowingEntity().getLeashHolder() != this) {
					
					setFollowingEntity(null);
					setFollowingEntityUUID(null);
					dataManager.set(WOLF_COUNT, 0);
					
				}
				
			}
			
			if (this.isBeingRidden()) {
				
				LivingEntity livingentity = (LivingEntity)this.getPassengers().get(0);
				
				if (livingentity != null) {
					
					updateInputs(livingentity.moveStrafing < 0, livingentity.moveStrafing > 0, livingentity.moveForward > 0, livingentity.moveForward < 0);
					
				}
				
				if (getFollowingEntity() == null && getFollowingEntityUUID() == null) {
					
					controlBoat();
					
				}
				
			}
			
		}
		
	}
	
	@Override
	@Nullable
	public Entity getControllingPassenger() {
		
		if (this.isBeingRidden()) {
			
			return this.getPassengers().get(0);
			
		} else {
			
			return null;
			
		}
		
	}
	
	public void publicSetRotation(float yaw, float pitch) {
		
		this.setRotation(yaw, pitch);
		
	}
	
	@Override
	public double getMountedYOffset() {
		
		return 0.5D;
		
	}
	
	@Override
	public ActionResultType func_230254_b_(PlayerEntity player, Hand hand) {
		
		if (!getEntityWorld().isRemote()) {
			
			if (player.getHeldItem(hand).getItem() == Items.LEAD || player.isSneaking()) {
				
				double i = getPosX();
				double j = getPosY();
				double k = getPosZ();
				
				for (SledWolfEntity mobentity : world.getEntitiesWithinAABB(SledWolfEntity.class, new AxisAlignedBB((double)i - 7.0D, (double)j - 7.0D, (double)k - 7.0D, (double)i + 7.0D, (double)j + 7.0D, (double)k + 7.0D))) {
					
					if (mobentity.getOwner() == player) {
						
						if (mobentity.getLeashHolder() == player) {
							
							if (getFollowingEntity() == null) {
								
								mobentity.setLeashHolder(this, true);
								setFollowingEntityUUID(mobentity.getUniqueID());
								mobentity.setSledEntityUUID(this.getUniqueID());
								getSledEntity().recalculateWolfCount();
								
								return ActionResultType.SUCCESS;
								
							}
							
						}
						
					}
					
				}
				
				return ActionResultType.SUCCESS;
				
			}
			
		}
		
		player.startRiding(this);
		
		return ActionResultType.SUCCESS;
		
	}
	
	@Override
	public boolean shouldRiderFaceForward(PlayerEntity player) {
		
		return true;
		
	}
	
	@Override
	public boolean canBeLeashedTo(PlayerEntity player) {
		
		return false;
		
	}
	
	@Override
	public boolean shouldRiderSit() {
		
		return false;
		
	}
	
	public void recalculateWolfCount() {
		
		int wolfCount = 0;
		
		SledWolfEntity entity = getFollowingEntity();
		
		while (entity != null) {
			
			wolfCount++;
			entity = (SledWolfEntity) ((ServerWorld) getEntityWorld()).getEntityByUuid(entity.getFollowingEntityUUID());
			
		}
		
		dataManager.set(WOLF_COUNT, wolfCount);
		
	}
	
	private void controlBoat() {
		
		if (this.isBeingRidden() && getFollowingEntity() == null) {
			
			float f = 0.0F;
			
			if (this.leftInputDown) {
				
				this.deltaRotation = 1;
				
			}
			
			if (this.rightInputDown) {
				
				this.deltaRotation = -1;
				
			}
			
			if (!this.leftInputDown && !this.rightInputDown) {
				
				this.deltaRotation = 0;
				
			}
			
			this.rotationYaw += this.deltaRotation;
			
			if (this.rightInputDown != this.leftInputDown && !this.forwardInputDown && !this.backInputDown) {
				
				prevRotationYaw = rotationYaw;
				publicSetRotation(rotationYaw, rotationPitch);
				renderYawOffset = rotationYaw;
				rotationYawHead = renderYawOffset;
				
			}
			
			if (this.forwardInputDown) {
				
				f += 0.02F;
				
			}

			if (this.backInputDown) {
				
				f -= 0.02F;
				
			}

			this.setMotion(this.getMotion().add((double)(MathHelper.sin(-this.rotationYaw * ((float)Math.PI / 180F)) * f), 0.0D, (double)(MathHelper.cos(this.rotationYaw * ((float)Math.PI / 180F)) * f)));
			
		}
		
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void updatePassenger(Entity passenger) {
		
		if (this.isPassenger(passenger)) {
			
			float f = 0.0F;
			float f1 = (float)((this.removed ? (double)0.01F : this.getMountedYOffset()) + passenger.getYOffset());
			
			if (this.getPassengers().size() > 1) {
				
				int i = this.getPassengers().indexOf(passenger);
				
				if (i == 0) {
					
					f = 0.2F;
					
				} else {
					
					f = -0.6F;
					
				}
				
				if (passenger instanceof AnimalEntity) {
					
					f = (float)((double)f + 0.2D);
					
				}
				
			}
			
			if (passenger instanceof PlayerEntity) {
				
				f = -1.2F;
				
			}
			
			Vector3d vector3d = (new Vector3d((double)f, 0.0D, 0.0D)).rotateYaw(-this.rotationYaw * ((float)Math.PI / 180F) - ((float)Math.PI / 2F));
			passenger.setPosition(this.getPosX() + vector3d.x, this.getPosY() + (double)f1, this.getPosZ() + vector3d.z);
			passenger.rotationYaw += this.deltaRotation;
			passenger.setRotationYawHead(passenger.getRotationYawHead() + this.deltaRotation);
			this.applyYawToEntity(passenger);
			
			if (passenger instanceof AnimalEntity && this.getPassengers().size() > 1) {
			   
				int j = passenger.getEntityId() % 2 == 0 ? 90 : 270;
				passenger.setRenderYawOffset(((AnimalEntity)passenger).renderYawOffset + (float)j);
				passenger.setRotationYawHead(passenger.getRotationYawHead() + (float)j);
				
			}
			
		}
		
	}
	
	@Override
	public Vector3d getLeashPosition(float partialTicks) {
		
		//Vector3d vector = (new Vector3d((double)1.2F + this.getPosX(), this.getPosY() + 0.0D, this.getPosZ() + 0.0D)).rotateYaw(-this.rotationYaw * ((float)Math.PI / 180F) - ((float)Math.PI / 2F));
		
		//return new Vector3d(this.getPosX(), getPosY() + 0.2F, this.getPosZ()).rotateYaw(-this.rotationYaw * ((float)Math.PI / 180F) - ((float)Math.PI / 2F));
		
		float f = MathHelper.lerp(partialTicks, this.prevRenderYawOffset, this.renderYawOffset) * ((float)Math.PI / 180F);
		Vector3d vector3d = new Vector3d(0.0D, 0.38D, 1.5D);
		
		return this.func_242282_l(partialTicks).add(vector3d.rotateYaw(-f));
		
	}
	
	protected void applyYawToEntity(Entity entityToUpdate) {
		
		entityToUpdate.setRenderYawOffset(this.rotationYaw);
		float f = MathHelper.wrapDegrees(entityToUpdate.rotationYaw - this.rotationYaw);
		float f1 = MathHelper.clamp(f, -105.0F, 105.0F);
		entityToUpdate.prevRotationYaw += f1 - f;
		entityToUpdate.rotationYaw += f1 - f;
		entityToUpdate.setRotationYawHead(entityToUpdate.rotationYaw);
		
	}
	
	@OnlyIn(Dist.CLIENT)
	public void applyOrientationToEntity(Entity entityToUpdate) {
		
		this.applyYawToEntity(entityToUpdate);
		
	}
	
	public void updateInputs(boolean leftInputDown, boolean rightInputDown, boolean forwardInputDown, boolean backInputDown) {
		
		this.leftInputDown = leftInputDown;
		this.rightInputDown = rightInputDown;
		this.forwardInputDown = forwardInputDown;
		this.backInputDown = backInputDown;
		
	}
	
	@Override
	protected void registerGoals() {
		
		this.goalSelector.addGoal(0, new SwimGoal(this));
		this.goalSelector.addGoal(0, new FollowSledComponentGoal(this, 0.6, 3.0F, 64.0F, false));
		//this.goalSelector.addGoal(5, new LookAtSledComponentGoal(this));
		
	}
	
	public static MutableAttribute registerSledAttributes() {
		
		return MobEntity.func_233666_p_().createMutableAttribute(Attributes.MAX_HEALTH, 2.5D).createMutableAttribute(Attributes.MOVEMENT_SPEED, 1.0D);
		
	}
	
}