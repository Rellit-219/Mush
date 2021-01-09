package com.mush.entity;

import com.mush.Mush;
import com.mush.entity.item.SledEntity;

import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class MEntityType {
	
	public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, Mush.MODID);
	
	public static final RegistryObject<EntityType<SledEntity>> SLED = ENTITIES.register("sled", () -> EntityType.Builder.<SledEntity>create(SledEntity::new, EntityClassification.MISC).size(1.8F, 1.5F).build(Mush.MODID + ":sled"));
	public static final RegistryObject<EntityType<SledWolfEntity>> SLED_WOLF = ENTITIES.register("sled_wolf", () -> EntityType.Builder.<SledWolfEntity>create(SledWolfEntity::new, EntityClassification.CREATURE).size(0.6F, 0.85F).trackingRange(10).build(Mush.MODID + ":sled_wolf"));
	
}