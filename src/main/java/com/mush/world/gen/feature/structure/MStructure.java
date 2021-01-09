package com.mush.world.gen.feature.structure;

import com.mush.Mush;

import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class MStructure {
	
	public static final DeferredRegister<Structure<?>> STRUCTURE_FEATURES = DeferredRegister.create(ForgeRegistries.STRUCTURE_FEATURES, Mush.MODID);
	
	public static final RegistryObject<Structure<NoFeatureConfig>> VILLAGER_CAMP_STRUCTURE = STRUCTURE_FEATURES.register("villager_camp", () -> new VillagerCampStructure(NoFeatureConfig.field_236558_a_));
	
}