package com.mush.block;

import java.util.Set;

import com.google.common.base.Supplier;
import com.mush.Mush;
import com.mush.block.tree.AshTree;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.DoorBlock;
import net.minecraft.block.FenceBlock;
import net.minecraft.block.FenceGateBlock;
import net.minecraft.block.FlowerPotBlock;
import net.minecraft.block.LeavesBlock;
import net.minecraft.block.PressurePlateBlock;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.block.SaplingBlock;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.StairsBlock;
import net.minecraft.block.TrapDoorBlock;
import net.minecraft.block.WoodButtonBlock;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.entity.EntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class MBlocks {
	
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Mush.MODID);
	public static final Set<RegistryObject<Block>> PURE_BLOCKS = new java.util.HashSet<>();
	
	public static final RegistryObject<Block> DOG_BOWL = register("dog_bowl", () -> new DogBowlBlock(AbstractBlock.Properties.create(Material.IRON).setRequiresTool().doesNotBlockMovement().hardnessAndResistance(0.5F).sound(SoundType.METAL)));
	public static final RegistryObject<Block> ASH_PLANKS = register("ash_planks", () -> new Block(AbstractBlock.Properties.create(Material.WOOD, MaterialColor.SAND).hardnessAndResistance(2.0F, 3.0F).sound(SoundType.WOOD)));
	public static final RegistryObject<Block> ASH_LOG = register("ash_log", () -> createLogBlock(MaterialColor.WOOD, MaterialColor.OBSIDIAN));
	public static final RegistryObject<Block> STRIPPED_ASH_LOG = register("stripped_ash_log", () -> createLogBlock(MaterialColor.SAND, MaterialColor.SAND));
	public static final RegistryObject<Block> STRIPPED_ASH_WOOD = register("stripped_ash_wood", () -> createLogBlock(MaterialColor.SAND, MaterialColor.SAND));
	public static final RegistryObject<Block> ASH_WOOD = register("ash_wood", () -> createLogBlock(MaterialColor.WOOD, MaterialColor.OBSIDIAN));
	public static final RegistryObject<Block> ASH_LEAVES = register("ash_leaves", () -> createLeavesBlock());
	public static final RegistryObject<Block> ASH_SLAB = register("ash_slab", () -> new SlabBlock(AbstractBlock.Properties.create(Material.WOOD, MaterialColor.SAND).hardnessAndResistance(2.0F, 3.0F).sound(SoundType.WOOD)));
	public static final RegistryObject<Block> PETRIFIED_ASH_SLAB = register("petrified_ash_slab", () -> new SlabBlock(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.SAND).setRequiresTool().hardnessAndResistance(2.0F, 6.0F)));
	@SuppressWarnings("deprecation")
	public static final RegistryObject<Block> ASH_STAIRS = register("ash_stairs", () -> new StairsBlock(ASH_PLANKS.get().getDefaultState(), AbstractBlock.Properties.from(ASH_PLANKS.get())));
	public static final RegistryObject<Block> ASH_PRESSURE_PLATE = register("ash_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, AbstractBlock.Properties.create(Material.WOOD, ASH_PLANKS.get().getMaterialColor()).doesNotBlockMovement().hardnessAndResistance(0.5F).sound(SoundType.WOOD)));
	public static final RegistryObject<Block> ASH_FENCE = register("ash_fence", () -> new FenceBlock(AbstractBlock.Properties.create(Material.WOOD, ASH_PLANKS.get().getMaterialColor()).hardnessAndResistance(2.0F, 3.0F).sound(SoundType.WOOD)));
	public static final RegistryObject<Block> ASH_TRAPDOOR = register("ash_trapdoor", () -> new TrapDoorBlock(AbstractBlock.Properties.create(Material.WOOD, MaterialColor.SAND).hardnessAndResistance(3.0F).sound(SoundType.WOOD).notSolid().setAllowsSpawn(MBlocks::neverAllowSpawn)));
	public static final RegistryObject<Block> ASH_FENCE_GATE = register("ash_fence_gate", () -> new FenceGateBlock(AbstractBlock.Properties.create(Material.WOOD, ASH_PLANKS.get().getMaterialColor()).hardnessAndResistance(2.0F, 3.0F).sound(SoundType.WOOD)));
	public static final RegistryObject<Block> ASH_BUTTON = register("ash_button", () -> new WoodButtonBlock(AbstractBlock.Properties.create(Material.MISCELLANEOUS).doesNotBlockMovement().hardnessAndResistance(0.5F).sound(SoundType.WOOD)));
	public static final RegistryObject<Block> ASH_DOOR = register("ash_door", () -> new DoorBlock(AbstractBlock.Properties.create(Material.WOOD, ASH_PLANKS.get().getMaterialColor()).hardnessAndResistance(3.0F).sound(SoundType.WOOD).notSolid()), false);
	public static final RegistryObject<Block> ASH_SAPLING = register("ash_sapling", () -> new SaplingBlock(new AshTree(), AbstractBlock.Properties.create(Material.PLANTS).doesNotBlockMovement().tickRandomly().zeroHardnessAndResistance().sound(SoundType.PLANT)));
	@SuppressWarnings("deprecation")
	public static final RegistryObject<Block> POTTED_ASH_SAPLING = register("potted_ash_sapling", () -> new FlowerPotBlock(ASH_SAPLING.get(), AbstractBlock.Properties.create(Material.MISCELLANEOUS).zeroHardnessAndResistance().notSolid()), false);
	
	private static RegistryObject<Block> register(String name, Supplier<? extends Block> sup, boolean hasItem) {
		
		RegistryObject<Block> registryObject = BLOCKS.register(name, sup);
		
		if (!hasItem) {
			
			PURE_BLOCKS.add(registryObject);
		
		}
		
		return registryObject;
		
	}
	
	private static RegistryObject<Block> register(String name, Supplier<? extends Block> sup) {
		
		return register(name, sup, true);
		
	}
	
	public static boolean shouldRegisterItemBlock(Block block) {
		
		for (RegistryObject<Block> pureBlock : PURE_BLOCKS) {
			
			if (pureBlock.get() == block) {
				
				return false;
				
			}
		}
		
		return true;
		
	}
	
	private static Boolean neverAllowSpawn(BlockState state, IBlockReader reader, BlockPos pos, EntityType<?> entity) {
		
		return (boolean)false;
		
	}
	
	@SuppressWarnings("unused")
	private static Boolean alwaysAllowSpawn(BlockState state, IBlockReader reader, BlockPos pos, EntityType<?> entity) {
		
		return (boolean)true;
		
	}
	
	private static Boolean allowsSpawnOnLeaves(BlockState state, IBlockReader reader, BlockPos pos, EntityType<?> entity) {
		
		return entity == EntityType.OCELOT || entity == EntityType.PARROT;
		
	}
	
	private static boolean isntSolid(BlockState state, IBlockReader reader, BlockPos pos) {
		
		return false;
		
	}
	
	private static RotatedPillarBlock createLogBlock(MaterialColor topColor, MaterialColor barkColor) {
		
		return new RotatedPillarBlock(AbstractBlock.Properties.create(Material.WOOD, (state) -> {
			
			return state.get(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? topColor : barkColor;
			
		}).hardnessAndResistance(2.0F).sound(SoundType.WOOD));
		
	}
	
	private static LeavesBlock createLeavesBlock() {
		
		return new LeavesBlock(AbstractBlock.Properties.create(Material.LEAVES).hardnessAndResistance(0.2F).tickRandomly().sound(SoundType.PLANT).notSolid().setAllowsSpawn(MBlocks::allowsSpawnOnLeaves).setSuffocates(MBlocks::isntSolid).setBlocksVision(MBlocks::isntSolid));
		
	}
	
}