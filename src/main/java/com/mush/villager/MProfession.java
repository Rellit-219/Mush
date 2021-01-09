package com.mush.villager;

import com.google.common.collect.ImmutableSet;
import com.mush.Mush;
import net.minecraft.entity.merchant.villager.VillagerProfession;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class MProfession {
	
	public static final DeferredRegister<VillagerProfession> PROFESSIONS = DeferredRegister.create(ForgeRegistries.PROFESSIONS, Mush.MODID);
	
	public static final RegistryObject<VillagerProfession> MUSHER = PROFESSIONS.register("musher", () -> new VillagerProfession(Mush.MODID + ":musher", MPointOfInterestType.DOG_BOWL.get(), ImmutableSet.of(), ImmutableSet.of(), SoundEvents.BLOCK_SNOW_PLACE));
	
}