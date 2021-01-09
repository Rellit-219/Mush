package com.mush.world.gen.biome;

import com.mush.Mush;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeAmbience;
import net.minecraft.world.biome.BiomeGenerationSettings;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.biome.MoodSoundAmbience;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Features;
import net.minecraft.world.gen.feature.structure.StructureFeatures;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilders;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class MBiome {
	
	public static final DeferredRegister<Biome> BIOMES = DeferredRegister.create(ForgeRegistries.BIOMES, Mush.MODID);
	
	public static final RegistryObject<Biome> SNOW_PLAINS = BIOMES.register("snow_plains", () -> func_244219_a(0.125F, 0.05F, false, false));
	
	public static Biome func_244219_a(float p_244219_0_, float p_244219_1_, boolean p_244219_2_, boolean p_244219_3_) {
	      MobSpawnInfo.Builder mobspawninfo$builder = (new MobSpawnInfo.Builder()).withCreatureSpawnProbability(0.07F);
	      DefaultBiomeFeatures.withSnowyBiomeMobs(mobspawninfo$builder);
	      BiomeGenerationSettings.Builder biomegenerationsettings$builder = (new BiomeGenerationSettings.Builder()).withSurfaceBuilder(p_244219_2_ ? ConfiguredSurfaceBuilders.field_244180_l : ConfiguredSurfaceBuilders.field_244178_j);
	      //biomegenerationsettings$builder.withStructure(MStructure.VILLAGER_CAMP_STRUCTURE.get().withConfiguration((VillageConfig) new VillageConfig(() -> {
	     	//	return VillagerCampPieces.START;
	     	//}, 6))).withStructure(StructureFeatures.IGLOO);
	      
	      //DefaultBiomeFeatures.withSnowySpruces(biomegenerationsettings$builder);

	      biomegenerationsettings$builder.withStructure(p_244219_3_ ? StructureFeatures.RUINED_PORTAL_MOUNTAIN : StructureFeatures.RUINED_PORTAL);
	      DefaultBiomeFeatures.withStrongholdAndMineshaft(biomegenerationsettings$builder);
	      //DefaultBiomeFeatures.func_243742_f(biomegenerationsettings$builder);
	     // DefaultBiomeFeatures.func_243746_h(biomegenerationsettings$builder);
	      if (p_244219_2_) {
	         biomegenerationsettings$builder.withFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, Features.ICE_PATCH);
	      }
	      
	      DefaultBiomeFeatures.withFrozenTopLayer(biomegenerationsettings$builder);
	     // DefaultBiomeFeatures.func_243750_j(biomegenerationsettings$builder);
	     // DefaultBiomeFeatures.func_243754_n(biomegenerationsettings$builder);
	     // DefaultBiomeFeatures.func_243694_H(biomegenerationsettings$builder);
	     // DefaultBiomeFeatures.func_243707_U(biomegenerationsettings$builder);
	     // DefaultBiomeFeatures.func_243709_W(biomegenerationsettings$builder);
	     // DefaultBiomeFeatures.func_243712_Z(biomegenerationsettings$builder);
	     // DefaultBiomeFeatures.func_243717_aa(biomegenerationsettings$builder);
	    //  DefaultBiomeFeatures.func_243727_ak(biomegenerationsettings$builder);
	     // DefaultBiomeFeatures.func_243730_an(biomegenerationsettings$builder);
	      return (new Biome.Builder()).precipitation(Biome.RainType.SNOW).category(Biome.Category.ICY).depth(p_244219_0_).scale(p_244219_1_).temperature(0.0F).downfall(0.5F).setEffects((new BiomeAmbience.Builder()).setWaterColor(4159204).setWaterFogColor(329011).setFogColor(329011).withSkyColor(329011).withGrassColor(12638463).withFoliageColor(func_244206_a(0.0F)).setMoodSound(MoodSoundAmbience.DEFAULT_CAVE).build()).withMobSpawnSettings(mobspawninfo$builder.copy()).withGenerationSettings(biomegenerationsettings$builder.build()).build();
	   }
	
	private static int func_244206_a(float p_244206_0_) {
	      float lvt_1_1_ = p_244206_0_ / 3.0F;
	      lvt_1_1_ = MathHelper.clamp(lvt_1_1_, -1.0F, 1.0F);
	      return MathHelper.hsvToRGB(0.62222224F - lvt_1_1_ * 0.05F, 0.5F + lvt_1_1_ * 0.1F, 1.0F);
	   }
	
}