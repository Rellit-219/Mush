package com.mush.world.gen.feature.structure;

import com.mojang.serialization.Codec;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.GenerationStage.Decoration;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.StructureStart;
import net.minecraft.world.gen.feature.template.TemplateManager;

public class VillagerCampStructure extends Structure<NoFeatureConfig> {
	
	public VillagerCampStructure(Codec<NoFeatureConfig> p_i231975_1_) {
		super(p_i231975_1_);
		
	}

	protected boolean func_230365_b_() {
		
		return false;
		
	}
	
	public Structure.IStartFactory<NoFeatureConfig> getStartFactory() {
		
		return VillagerCampStructure.Start::new;
		
	}
	
	@Override
	public GenerationStage.Decoration getDecorationStage() {
		
		return Decoration.SURFACE_STRUCTURES;
		
	}
	
	public static class Start extends StructureStart<NoFeatureConfig> {
		
		public Start(Structure<NoFeatureConfig> p_i225815_1_, int p_i225815_2_, int p_i225815_3_, MutableBoundingBox p_i225815_4_, int p_i225815_5_, long p_i225815_6_) {
			
			super(p_i225815_1_, p_i225815_2_, p_i225815_3_, p_i225815_4_, p_i225815_5_, p_i225815_6_);
			
		}
		
		@Override
		public void func_230364_a_(DynamicRegistries registries, ChunkGenerator generator, TemplateManager templateManager, int chunkX, int chunkZ, Biome p_230364_6_, NoFeatureConfig p_230364_7_) {
			
			BlockPos blockpos = new BlockPos(chunkX * 16, 90, chunkZ * 16);
			VillagerCampPieces.startStructure(registries, generator, templateManager, blockpos, this.components, this.rand);
			this.recalculateStructureSize();
			
		}
		
	}
	
}