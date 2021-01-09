package com.mush.villager;

import com.mush.Mush;
import com.mush.block.MBlocks;
import net.minecraft.village.PointOfInterestType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class MPointOfInterestType {
	
	public static final DeferredRegister<PointOfInterestType> POI_TYPES = DeferredRegister.create(ForgeRegistries.POI_TYPES, Mush.MODID);
	
	public static final RegistryObject<PointOfInterestType> DOG_BOWL = POI_TYPES.register("dog_bowl", () -> new PointOfInterestType(Mush.MODID + ":dog_bowl", PointOfInterestType.getAllStates(MBlocks.DOG_BOWL.get()), 1, 1));
	
}