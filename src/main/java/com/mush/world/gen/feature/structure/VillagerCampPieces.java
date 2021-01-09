package com.mush.world.gen.feature.structure;

import java.util.List;
import java.util.function.Supplier;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import com.mush.Mush;

import net.minecraft.block.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.jigsaw.JigsawManager;
import net.minecraft.world.gen.feature.jigsaw.JigsawPattern;
import net.minecraft.world.gen.feature.jigsaw.JigsawPattern.PlacementBehaviour;
import net.minecraft.world.gen.feature.jigsaw.JigsawPatternRegistry;
import net.minecraft.world.gen.feature.jigsaw.LegacySingleJigsawPiece;
import net.minecraft.world.gen.feature.jigsaw.SingleJigsawPiece;
import net.minecraft.world.gen.feature.structure.AbstractVillagePiece;
import net.minecraft.world.gen.feature.structure.StructurePiece;
import net.minecraft.world.gen.feature.structure.VillageConfig;
import net.minecraft.world.gen.feature.template.AlwaysTrueRuleTest;
import net.minecraft.world.gen.feature.template.ProcessorLists;
import net.minecraft.world.gen.feature.template.RandomBlockMatchRuleTest;
import net.minecraft.world.gen.feature.template.RuleEntry;
import net.minecraft.world.gen.feature.template.RuleStructureProcessor;
import net.minecraft.world.gen.feature.template.StructureProcessor;
import net.minecraft.world.gen.feature.template.StructureProcessorList;
import net.minecraft.world.gen.feature.template.Template;
import net.minecraft.world.gen.feature.template.TemplateManager;

public class VillagerCampPieces {
	
	public static final StructureProcessorList villagerCampBlocks = registerProcessorList("villager_camp", ImmutableList.of(new RuleStructureProcessor(ImmutableList.of(new RuleEntry(new RandomBlockMatchRuleTest(Blocks.SNOW, 0.1F), AlwaysTrueRuleTest.INSTANCE, Blocks.COARSE_DIRT.getDefaultState()), new RuleEntry(new RandomBlockMatchRuleTest(Blocks.LIGHT_GRAY_WOOL, 0.1F), AlwaysTrueRuleTest.INSTANCE, Blocks.GRAY_WOOL.getDefaultState()), new RuleEntry(new RandomBlockMatchRuleTest(Blocks.LIGHT_GRAY_BANNER, 0.1F), AlwaysTrueRuleTest.INSTANCE, Blocks.GRAY_BANNER.getDefaultState())))));
	
	public static final JigsawPattern START = JigsawPatternRegistry.func_244094_a(new JigsawPattern(new ResourceLocation(Mush.MODID, "villager_camp/camp_centers"), new ResourceLocation(Mush.MODID, "villager_camp/camp_centers"), ImmutableList.of(Pair.of(jigsawPiece("villager_camp/camp_centers/camp_center_1", villagerCampBlocks), 1)), JigsawPattern.PlacementBehaviour.RIGID));
	
	public static void startStructure(DynamicRegistries registries, ChunkGenerator chunkGeneratorIn, TemplateManager templateManagerIn, BlockPos posIn, List<StructurePiece> structurePieces, SharedSeedRandom random) {
		
		JigsawManager.func_242837_a(registries, new VillageConfig(() -> new JigsawPattern(new ResourceLocation(Mush.MODID, "villager_camp/camp_centers"), new ResourceLocation(Mush.MODID, "villager_camp/camp_centers"), ImmutableList.of(Pair.of(jigsawPiece("villager_camp/camp_centers/camp_center_1", villagerCampBlocks), 1)), JigsawPattern.PlacementBehaviour.RIGID), 7), AbstractVillagePiece::new, chunkGeneratorIn, templateManagerIn, posIn, structurePieces, random, false, false);
		
	}
	
	static {
		
		JigsawPatternRegistry.func_244094_a(new JigsawPattern(new ResourceLocation(Mush.MODID, "villager_camp/roads"), new ResourceLocation("empty"), ImmutableList.of(Pair.of(jigsawPiece("villager_camp/roads/straight_road_1", villagerCampBlocks), 3), Pair.of(jigsawPiece("villager_camp/roads/straight_road_2", villagerCampBlocks), 3), Pair.of(jigsawPiece("villager_camp/roads/straight_road_3", villagerCampBlocks), 3), Pair.of(jigsawPiece("villager_camp/roads/corner_road_1", villagerCampBlocks), 2), Pair.of(jigsawPiece("villager_camp/roads/corner_road_2", villagerCampBlocks), 2), Pair.of(jigsawPiece("villager_camp/roads/cross_road_1", villagerCampBlocks), 1), Pair.of(jigsawPiece("villager_camp/roads/cross_road_2", villagerCampBlocks), 1)), JigsawPattern.PlacementBehaviour.TERRAIN_MATCHING));
		JigsawPatternRegistry.func_244094_a(new JigsawPattern(new ResourceLocation(Mush.MODID, "villager_camp/tents"), new ResourceLocation("empty"), ImmutableList.of(Pair.of(jigsawPiece("villager_camp/tents/musher_tent", villagerCampBlocks), 3), Pair.of(jigsawPiece("villager_camp/tents/fletcher_tent", villagerCampBlocks), 3)), JigsawPattern.PlacementBehaviour.RIGID));
		
	}
	
	public static Function<JigsawPattern.PlacementBehaviour, LegacySingleJigsawPiece> jigsawPiece(String location) {
		
		return (p_242860_1_) -> {
			
			return new MLegacySingleJigsawPiece(Either.left(new ResourceLocation(Mush.MODID, location)), () -> {
				
				return ProcessorLists.field_244101_a;
				
			}, p_242860_1_);
			
		};
		
	}
	
	public static Function<JigsawPattern.PlacementBehaviour, LegacySingleJigsawPiece> jigsawPiece(String location, StructureProcessorList list) {
		
		return (p_242860_1_) -> {
			
			return new MLegacySingleJigsawPiece(Either.left(new ResourceLocation(Mush.MODID, location)), () -> list, p_242860_1_);
			
		};
		
	}
	
	private static StructureProcessorList registerProcessorList(String name, ImmutableList<StructureProcessor> list) {
		
		ResourceLocation resourcelocation = new ResourceLocation(Mush.MODID, name);
		StructureProcessorList structureprocessorlist = new StructureProcessorList(list);
		return WorldGenRegistries.register(WorldGenRegistries.STRUCTURE_PROCESSOR_LIST, resourcelocation, structureprocessorlist);
		
	}
	
	public static class MLegacySingleJigsawPiece extends LegacySingleJigsawPiece {

		public MLegacySingleJigsawPiece(Either<ResourceLocation, Template> p_i242007_1_, Supplier<StructureProcessorList> p_i242007_2_, PlacementBehaviour p_i242007_3_) {
			super(p_i242007_1_, p_i242007_2_, p_i242007_3_);
			
		}
		
	}
	
	public static class MSingleJigsawPiece extends SingleJigsawPiece {
		
		public MSingleJigsawPiece(Either<ResourceLocation, Template> p_i242007_1_, Supplier<StructureProcessorList> p_i242007_2_, PlacementBehaviour p_i242007_3_) {
			super(p_i242007_1_, p_i242007_2_, p_i242007_3_);
			
		}
		
	}
	
}