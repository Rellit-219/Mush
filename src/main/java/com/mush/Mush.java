package com.mush;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.village.PointOfInterestType;
import net.minecraft.world.FoliageColors;
import net.minecraft.world.IBlockDisplayReader;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeColors;
import net.minecraft.world.biome.Biome.Category;
import net.minecraft.world.gen.GenerationStage.Decoration;
import net.minecraft.world.gen.feature.Feature;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.EntityInteract;
import net.minecraftforge.event.village.VillagerTradesEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DeferredWorkQueue;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import java.lang.reflect.Method;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mush.block.MBlocks;
import com.mush.client.renderer.entity.SledRenderer;
import com.mush.client.renderer.entity.SledWolfRenderer;
import com.mush.entity.ISledComponent;
import com.mush.entity.MEntityType;
import com.mush.entity.SledWolfEntity;
import com.mush.entity.item.SledEntity;
import com.mush.item.MItems;
import com.mush.registry.MRegistryHandler;
import com.mush.villager.MPointOfInterestType;
import com.mush.villager.MProfession;
import com.mush.villager.RandomTradeBuilder;
import com.mush.world.gen.feature.MFeatures;

@Mod("mush")
public class Mush {
	
	public static final String MODID = "mush";
	
	@SuppressWarnings("unused")
	private static final Logger LOGGER = LogManager.getLogger();

	public Mush() {
		
		MRegistryHandler.registerDeferred(FMLJavaModLoadingContext.get().getModEventBus());
		
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);
		FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);
		
		MinecraftForge.EVENT_BUS.register(this);
		
	}
	
	@SuppressWarnings("deprecation")
	private void setup(final FMLCommonSetupEvent event) {
		
		DeferredWorkQueue.runLater(() -> {
			
			GlobalEntityTypeAttributes.put(MEntityType.SLED.get(), SledEntity.registerSledAttributes().create());
			GlobalEntityTypeAttributes.put(MEntityType.SLED_WOLF.get(), SledWolfEntity.registerSledWolfAttributes().create());
			
			try {
				
				Method func_221052_a = ObfuscationReflectionHelper.findMethod(PointOfInterestType.class, "func_221052_a", PointOfInterestType.class);
				func_221052_a.invoke(null, MPointOfInterestType.DOG_BOWL.get());
				
			} catch (Exception e) {
				
				e.printStackTrace();
				
			}
			
		});
		
	}
	
	private void doClientStuff(final FMLClientSetupEvent event) {
		
		RenderingRegistry.registerEntityRenderingHandler(MEntityType.SLED.get(), new SledRenderer.RenderFactory());
		RenderingRegistry.registerEntityRenderingHandler(MEntityType.SLED_WOLF.get(), new SledWolfRenderer.RenderFactory());
		
		RenderTypeLookup.setRenderLayer(MBlocks.ASH_SAPLING.get(), RenderType.getCutoutMipped());
		RenderTypeLookup.setRenderLayer(MBlocks.POTTED_ASH_SAPLING.get(), RenderType.getCutoutMipped());
		
		BlockColors blockColors = Minecraft.getInstance().getBlockColors();
        ItemColors itemColors = Minecraft.getInstance().getItemColors();
		
        blockColors.register((state, world, blockPos, tintIndex) -> {
			
			return world != null && blockPos != null ? BiomeColors.getFoliageColor(world, blockPos) : FoliageColors.getDefault();
			
		}, MBlocks.ASH_LEAVES.get());
        
        itemColors.register((stack, tintIndex) -> {
			
			BlockState blockstate = ((BlockItem)stack.getItem()).getBlock().getDefaultState();
			
			return blockColors.getColor(blockstate, (IBlockDisplayReader)null, (BlockPos)null, tintIndex);
			
		}, MBlocks.ASH_LEAVES.get());
		
	}
	
	private void enqueueIMC(final InterModEnqueueEvent event) {
		
	}
	
	private void processIMC(final InterModProcessEvent event) {
		
	}
	
	@SubscribeEvent
	public void onServerStarting(FMLServerStartingEvent event) {
		
	}
	
	@SuppressWarnings("deprecation")
	@SubscribeEvent
    public void addVillagerTrades(VillagerTradesEvent event) {
		
		if (event.getType() == MProfession.MUSHER.get()) {
			
			event.getTrades().get(1).add(new RandomTradeBuilder(8, 5, 0.01F).setPrice(Item.getItemFromBlock(Blocks.SNOW_BLOCK), 24, 38).setForSale(Items.EMERALD, 1, 2).build());
			event.getTrades().get(1).add(new RandomTradeBuilder(4, 8, 0.02F).setPrice(Items.EMERALD, 1, 2).setForSale(Items.LEAD, 2, 4).build());
			event.getTrades().get(2).add(new RandomTradeBuilder(12, 8, 0.02F).setPrice(Items.EMERALD, 2, 3).setForSale(MItems.HARNESS.get(), 1, 2).build());
			event.getTrades().get(2).add(new RandomTradeBuilder(5, 5, 0.08F).setPrice(Item.getItemFromBlock(MBlocks.ASH_LOG.get()), 14, 34).setForSale(Items.EMERALD, 1, 2).build());
			event.getTrades().get(3).add(new RandomTradeBuilder(4, 5, 0.04F).setPrice(Items.EMERALD, 1, 1).setForSale(Items.BONE, 3, 10).build());
			event.getTrades().get(3).add(new RandomTradeBuilder(8, 8, 0.02F).setPrice(Items.EMERALD, 1, 1).setForSale(Item.getItemFromBlock(MBlocks.DOG_BOWL.get()), 1, 1).build());
			event.getTrades().get(4).add(new RandomTradeBuilder(4, 5, 0.01F).setPrice(Items.EMERALD, 1, 1).setForSale(Item.getItemFromBlock(MBlocks.ASH_SAPLING.get()), 2, 8).build());
			event.getTrades().get(4).add(new RandomTradeBuilder(3, 8, 0.04F).setPrice(Item.getItemFromBlock(Blocks.CAMPFIRE), 2, 9).setForSale(Items.EMERALD, 1, 1).build());
			event.getTrades().get(5).add(new RandomTradeBuilder(5, 8, 0.02F).setPrice(Items.EMERALD, 5, 12).setForSale(MItems.SLED.get(), 1, 1).build());
			
		}
		
	}
	
	@SubscribeEvent
    public void addVillagerCamp(BiomeLoadingEvent event) {
		
		if (event.getCategory() == Category.ICY || event.getCategory() == Category.TAIGA) {
			
			event.getGeneration().getFeatures(Decoration.VEGETAL_DECORATION).add(() -> Feature.TREE.withConfiguration(MFeatures.ASH_TREE_CONFIG).chance(2));
			
		}
		
	}
	
	@SubscribeEvent
	public void addStrippingWoodMechanic(PlayerInteractEvent.RightClickBlock event) {
		
		World world = event.getWorld();
		BlockPos blockPos = event.getPos();
		BlockState blockstate = world.getBlockState(blockPos);
		if (event.getItemStack().getItem() instanceof AxeItem && (blockstate.getBlock() == MBlocks.ASH_LOG.get() || blockstate.getBlock() == MBlocks.ASH_WOOD.get())) {
			
			PlayerEntity playerEntity = event.getPlayer();
			world.playSound(playerEntity, blockPos, SoundEvents.ITEM_AXE_STRIP, SoundCategory.BLOCKS, 1.0F, 1.0F);
			
			if (!world.isRemote) {
				
				if (blockstate.getBlock() == MBlocks.ASH_LOG.get()) {
					
					world.setBlockState(blockPos, MBlocks.STRIPPED_ASH_LOG.get().getDefaultState().with(RotatedPillarBlock.AXIS, blockstate.get(RotatedPillarBlock.AXIS)), 11);
					
				} else if (blockstate.getBlock() == MBlocks.ASH_WOOD.get()) {
					
					world.setBlockState(blockPos, MBlocks.STRIPPED_ASH_WOOD.get().getDefaultState().with(RotatedPillarBlock.AXIS, blockstate.get(RotatedPillarBlock.AXIS)), 11);
					
				}
				
				if (playerEntity != null) {
					
					event.getItemStack().damageItem(1, playerEntity, (player) -> {
						
						player.sendBreakAnimation(event.getHand());
						
					});
					
				}
				
			}
			
		}
		
	}
	
	@SubscribeEvent
	public void onLeadUsed(EntityInteract event) {
		
		if (!event.getWorld().isRemote()) {
			
			if (event.getTarget() instanceof SledWolfEntity) {
				
				if (event.getPlayer().isCrouching()) {
					
					if (((SledWolfEntity) event.getTarget()).getLeashHolder() != null && ((SledWolfEntity) event.getTarget()).getLeashHolder() instanceof ISledComponent) {
						
						((SledWolfEntity) event.getTarget()).clearLeashed(true, true);
						
					} else {
						
						WolfEntity wolf = EntityType.WOLF.create(event.getWorld());
						SledWolfEntity sledWolf = (SledWolfEntity) event.getTarget();
						wolf.setPosition(sledWolf.getPosX(), sledWolf.getPosY(), sledWolf.getPosZ());
						
						wolf.rotateTowards(sledWolf.rotationYaw, sledWolf.rotationPitch);
						
						wolf.setTamed(true);
						wolf.setTamedBy(event.getPlayer());
						wolf.setCollarColor(sledWolf.getCollarColor());
						wolf.func_233687_w_(sledWolf.isSitting());
						wolf.setHealth(sledWolf.getHealth());
						wolf.setCustomName(sledWolf.getCustomName());
						
						if (sledWolf.getLeashHolder() != null) {
							
							if (sledWolf.getLeashHolder() instanceof SledEntity) {
								
								wolf.clearLeashed(true, false);
								sledWolf.entityDropItem(Items.LEAD);
								
							} else {
								
								wolf.setLeashHolder(sledWolf.getLeashHolder(), true);
								
							}
							
						}
						
						sledWolf.entityDropItem(MItems.HARNESS.get());
						event.getWorld().addEntity(wolf);
						sledWolf.remove();
						
					}
					
				}
					
			} else if (event.getTarget() instanceof WolfEntity) {
				
				if (event.getItemStack().getItem() == MItems.HARNESS.get() && !((WolfEntity) event.getTarget()).isChild()) {
					
					if (((WolfEntity) event.getTarget()).getOwner() == event.getPlayer()) {
						
						SledWolfEntity sledWolf = new SledWolfEntity(event.getWorld());
						WolfEntity wolf = (WolfEntity) event.getTarget();
						sledWolf.setPosition(wolf.getPosX(), wolf.getPosY(), wolf.getPosZ());
						
						sledWolf.rotationYaw = wolf.rotationYaw;
						sledWolf.rotationPitch = wolf.rotationPitch;
						sledWolf.prevRotationYaw = sledWolf.rotationYaw;
						sledWolf.prevRotationPitch = sledWolf.rotationPitch;
						
						sledWolf.setTamed(true);
						sledWolf.setTamedBy(event.getPlayer());
						sledWolf.setCollarColor(wolf.getCollarColor());
						sledWolf.func_233687_w_(wolf.isSitting());
						sledWolf.setHealth(wolf.getHealth());
						sledWolf.setCustomName(wolf.getCustomName());
						
						if (wolf.getLeashHolder() != null) {
							
							sledWolf.setLeashHolder(wolf.getLeashHolder(), true);
							
						}
						
						event.getItemStack().shrink(1);
						
						event.getWorld().addEntity(sledWolf);
						wolf.remove();
						
					}
					
				}
				
			}
			
		}
		
	}
	
}
