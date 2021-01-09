package com.mush.entity;

import java.util.Optional;
import java.util.UUID;

import com.mush.entity.ai.goal.FollowSledComponentGoal;
import com.mush.entity.ai.goal.LookAtSledComponentGoal;
import com.mush.entity.ai.goal.SWFollowOwnerGoal;
import com.mush.entity.ai.goal.SWLeapAtTargetGoal;
import com.mush.entity.ai.goal.SWLookAtGoal;
import com.mush.entity.ai.goal.SWLookRandomlyGoal;
import com.mush.entity.ai.goal.SWMeleeAttackGoal;
import com.mush.entity.ai.goal.SWNearestAttackableTargetGoal;
import com.mush.entity.ai.goal.SWNonTamedTargetGoal;
import com.mush.entity.ai.goal.SWOwnerHurtByTargetGoal;
import com.mush.entity.ai.goal.SWOwnerHurtTargetGoal;
import com.mush.entity.ai.goal.SWWaterAvoidingRandomWalkingGoal;
import com.mush.entity.ai.goal.SledLeaderControllingGoal;
import com.mush.entity.item.SledEntity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap.MutableAttribute;
import net.minecraft.entity.ai.goal.BegGoal;
import net.minecraft.entity.ai.goal.BreedGoal;
import net.minecraft.entity.ai.goal.HurtByTargetGoal;
import net.minecraft.entity.ai.goal.ResetAngerGoal;
import net.minecraft.entity.ai.goal.SitGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.monster.AbstractSkeletonEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.TurtleEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.passive.horse.LlamaEntity;
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
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.NetworkHooks;

public class SledWolfEntity extends WolfEntity implements ISledComponent {
	
	protected static final DataParameter<Optional<UUID>> SLED_UNIQUE_ID = EntityDataManager.createKey(SledWolfEntity.class, DataSerializers.OPTIONAL_UNIQUE_ID);
	protected static final DataParameter<Optional<UUID>> FOLLOWING_UNIQUE_ID = EntityDataManager.createKey(SledWolfEntity.class, DataSerializers.OPTIONAL_UNIQUE_ID);
	
	private float controllingRotation;
	
	private SledWolfEntity followingEntity;
	private SledEntity sledEntity;
	
	public SledWolfEntity(EntityType<? extends WolfEntity> type, World worldIn) {
		super(type, worldIn);
		
	}
	
	public SledWolfEntity(World world) {
		super(MEntityType.SLED_WOLF.get(), world);
		
		controllingRotation = rotationYaw;
		
	}
	
	protected void registerData() {
		super.registerData();
		
		this.dataManager.register(SLED_UNIQUE_ID, Optional.empty());
		this.dataManager.register(FOLLOWING_UNIQUE_ID, Optional.empty());
		
	}
	
	@Override
	public IPacket<?> createSpawnPacket() {
		
		return NetworkHooks.getEntitySpawningPacket(this);
		
	}
	
	@SuppressWarnings("deprecation")
	public void tick() {
		super.tick();
		
		if (!getEntityWorld().isRemote()) {
			
			if (getFollowingEntityUUID() != null && getFollowingEntity() == null) {
				
				setFollowingEntity((SledWolfEntity) ((ServerWorld) getEntityWorld()).getEntityByUuid(getFollowingEntityUUID()));
				
			}
			
			if (getFollowingEntityUUID() == null && getFollowingEntity() != null) {
				
				setFollowingEntityUUID(getFollowingEntity().getUniqueID());
				
			}
			
			if (getSledEntityUUID() != null && getSledEntity() == null) {
				
				setSledEntity((SledEntity) ((ServerWorld) getEntityWorld()).getEntityByUuid(getSledEntityUUID()));
				
			}
			
			if (getSledEntityUUID() == null && getSledEntity() != null) {
				
				setSledEntityUUID(getSledEntity().getUniqueID());
				
			}
			
			if (getFollowingEntity() != null) {
				
				if (getFollowingEntity().removed || getFollowingEntity().getLeashHolder() != this) {
					
					setFollowingEntityUUID(null);
					setFollowingEntity(null);
					
					if (getSledEntity() != null) {
						
						getSledEntity().recalculateWolfCount();
						
					}
					
				} else if (getFollowingEntity() == getLeashHolder()) {
					
					setFollowingEntityUUID(null);
					setFollowingEntity(null);
					
					if (getSledEntity() != null) {
						
						getSledEntity().recalculateWolfCount();
						
					}
					
				}
				
			}
			
			if (getLeashHolder() != null) {
				
				if (getSledEntity() != null) {
					
					if (getSledEntity().removed) {
						
						clearLeashed(true, true);
						getSledEntity().recalculateWolfCount();
						setSledEntity(null);
						setSledEntityUUID(null);
						
					}
					
					if (getLeashHolder() instanceof SledWolfEntity) {
						
						if (((SledWolfEntity) getLeashHolder()).getSledEntity() == null && ((SledWolfEntity) getLeashHolder()).getSledEntityUUID() == null) {
							
							clearLeashed(true, true);
							getSledEntity().recalculateWolfCount();
							setSledEntity(null);
							setSledEntityUUID(null);
							
						} else if (!(((MobEntity) getLeashHolder()).getLeashHolder() instanceof SledEntity) && !(((MobEntity) getLeashHolder()).getLeashHolder() instanceof SledWolfEntity) && ((MobEntity) getLeashHolder()).getLeashHolder() != null) {
							
							clearLeashed(true, true);
							getSledEntity().recalculateWolfCount();
							setSledEntity(null);
							setSledEntityUUID(null);
							
						}
						
					}
					
				} else if (getLeashHolder() instanceof SledEntity) {
					
					clearLeashed(true, true);
					
					if (getSledEntity() != null) {
						
						getSledEntity().recalculateWolfCount();
						
					}
					
				} else if (getLeashHolder() instanceof SledWolfEntity) {
					
					clearLeashed(true, true);
					
					if (getSledEntity() != null) {
						
						getSledEntity().recalculateWolfCount();
						
					}
					
				}
				
			} else {
				
				if (getSledEntity() != null) {
					
					getSledEntity().recalculateWolfCount();
					setSledEntity(null);
					setSledEntityUUID(null);
					
				}
				
			}
			
			if (getSledEntity() != null && getFollowingEntity() == null) {
				
				float f = 0.0F;
				
				if (getSledEntity().rightInputDown) {
					
					controllingRotation -= 4;
					
				}
				
				if (getSledEntity().leftInputDown) {
					
					controllingRotation += 4;
					
				}
				
				rotationYaw = controllingRotation;
				
				if (getSledEntity().rightInputDown != getSledEntity().leftInputDown && !getSledEntity().forwardInputDown && !getSledEntity().backInputDown) {
					
					prevRotationYaw = rotationYaw;
					renderYawOffset = rotationYaw;
					rotationYawHead = renderYawOffset;
					
				}
				
				if (getSledEntity().forwardInputDown) {
					
					f += (0.05F + (getSledEntity().getDataManager().get(SledEntity.WOLF_COUNT) * 0.025F));
					
				}
				
				this.setMotion(this.getMotion().add((double)(MathHelper.sin(-this.rotationYaw * ((float)Math.PI / 180F)) * f), 0.0D, (double)(MathHelper.cos(this.rotationYaw * ((float)Math.PI / 180F)) * f)));
				
			}
			
		}
		
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
							
							if (getSledEntity() != null) {
								
								if (getFollowingEntity() == null && getSledEntity().getDataManager().get(SledEntity.WOLF_COUNT) < getSledEntity().getDataManager().get(SledEntity.MAXIMUM_WOLF_COUNT)) {
									
									mobentity.setLeashHolder(this, true);
									setFollowingEntityUUID(mobentity.getUniqueID());
									mobentity.setSledEntityUUID(getSledEntityUUID());
									getSledEntity().recalculateWolfCount();
									
									return ActionResultType.SUCCESS;
									
								}
								
							}
							
						}
						
					}
					
				}
				
				return ActionResultType.SUCCESS;
				
			}
			
		}
		
		return super.func_230254_b_(player, hand);
		
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	protected void registerGoals() {
		
		this.goalSelector.addGoal(0, new FollowSledComponentGoal(this, 1.0D, 3.0F, 64.0F, false));
		this.goalSelector.addGoal(0, new SledLeaderControllingGoal(this, 1.0D, 3.0F, 64.0F, false));
		this.goalSelector.addGoal(5, new LookAtSledComponentGoal(this));
		this.goalSelector.addGoal(1, new SwimGoal(this));
		this.goalSelector.addGoal(2, new SitGoal(this));
		this.goalSelector.addGoal(3, new SledWolfEntity.AvoidEntityGoal(this, LlamaEntity.class, 24.0F, 1.5D, 1.5D));
		this.goalSelector.addGoal(4, new SWLeapAtTargetGoal(this, 0.4F));
		this.goalSelector.addGoal(5, new SWMeleeAttackGoal(this, 1.0D, true));
		this.goalSelector.addGoal(6, new SWFollowOwnerGoal(this, 1.0D, 10.0F, 2.0F, false));
		this.goalSelector.addGoal(7, new BreedGoal(this, 1.0D));
		this.goalSelector.addGoal(8, new SWWaterAvoidingRandomWalkingGoal(this, 1.0D));
		this.goalSelector.addGoal(9, new BegGoal(this, 8.0F));
		this.goalSelector.addGoal(10, new SWLookAtGoal(this, PlayerEntity.class, 8.0F));
		this.goalSelector.addGoal(10, new SWLookRandomlyGoal(this));
		this.targetSelector.addGoal(1, new SWOwnerHurtByTargetGoal(this));
		this.targetSelector.addGoal(2, new SWOwnerHurtTargetGoal(this));
		this.targetSelector.addGoal(3, (new HurtByTargetGoal(this)).setCallsForHelp());
		this.targetSelector.addGoal(4, new SWNearestAttackableTargetGoal<>(this, PlayerEntity.class, 10, true, false, this::func_233680_b_));
		this.targetSelector.addGoal(5, new SWNonTamedTargetGoal<>(this, AnimalEntity.class, false, TARGET_ENTITIES));
		this.targetSelector.addGoal(6, new SWNonTamedTargetGoal<>(this, TurtleEntity.class, false, TurtleEntity.TARGET_DRY_BABY));
		this.targetSelector.addGoal(7, new SWNearestAttackableTargetGoal<>(this, AbstractSkeletonEntity.class, false));
		this.targetSelector.addGoal(8, new ResetAngerGoal<>(this, true));
		
	}
	
	public void writeAdditional(CompoundNBT compound) {
		super.writeAdditional(compound);
		
		this.writeSledComponentNBT(compound);
		
	}
	
	public void readAdditional(CompoundNBT compound) {
		super.readAdditional(compound);
		
		this.readSledComponentNBT((ServerWorld)this.world, compound);
		
	}
	
	public static MutableAttribute registerSledWolfAttributes() {
		
		return WolfEntity.func_234233_eS_();
		
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
		
		return dataManager.get(SLED_UNIQUE_ID).orElse(null);
		
	}

	@Override
	public SledEntity getSledEntity() {
		
		return sledEntity;
		
	}

	@Override
	public void setFollowingEntityUUID(UUID uuid) {
		
		dataManager.set(FOLLOWING_UNIQUE_ID, Optional.ofNullable(uuid));
		
		if (!getEntityWorld().isRemote()) {
			
			setFollowingEntity((SledWolfEntity) ((ServerWorld) getEntityWorld()).getEntityByUuid(uuid));
			
		}
		
	}
	
	@Override
	public void setSledEntityUUID(UUID uuid) {
		
		dataManager.set(SLED_UNIQUE_ID, Optional.ofNullable(uuid));
		
		if (!getEntityWorld().isRemote()) {
			
			setSledEntity((SledEntity) ((ServerWorld) getEntityWorld()).getEntityByUuid(uuid));
			
		}
		
	}

	@Override
	public void setFollowingEntity(SledWolfEntity entity) {
		
		followingEntity = entity;
		
	}

	@Override
	public void setSledEntity(SledEntity entity) {
		
		sledEntity = entity;
		
	}
	
	class AvoidEntityGoal<T extends LivingEntity> extends net.minecraft.entity.ai.goal.AvoidEntityGoal<T> {
		
		private final SledWolfEntity wolf;
		
		public AvoidEntityGoal(SledWolfEntity wolfIn, Class<T> entityClassToAvoidIn, float avoidDistanceIn, double farSpeedIn, double nearSpeedIn) {
			super(wolfIn, entityClassToAvoidIn, avoidDistanceIn, farSpeedIn, nearSpeedIn);
			
			this.wolf = wolfIn;
			
		}
		
		public boolean shouldExecute() {
			
			if (super.shouldExecute() && this.avoidTarget instanceof LlamaEntity) {
				
				return !this.wolf.isTamed() && this.avoidLlama((LlamaEntity)this.avoidTarget);
				
			} else {
				
				return false;
				
			}
			
		}
		
		private boolean avoidLlama(LlamaEntity llamaIn) {
			
			return llamaIn.getStrength() >= SledWolfEntity.this.rand.nextInt(5);
			
		}
		
		public void startExecuting() {
			
			SledWolfEntity.this.setAttackTarget((LivingEntity)null);
			
			super.startExecuting();
			
		}
		
		public void tick() {
			
			SledWolfEntity.this.setAttackTarget((LivingEntity)null);
			super.tick();
			
		}
		
	}
	
}