package com.mush.registry;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

import com.mush.Mush;
import com.mush.block.MBlocks;
import com.mush.entity.MEntityType;
import com.mush.item.MItemGroup;
import com.mush.item.MItems;
import com.mush.villager.MPointOfInterestType;
import com.mush.villager.MProfession;
import com.mush.world.gen.biome.MBiome;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder(Mush.MODID)
@Mod.EventBusSubscriber(modid = Mush.MODID, bus=Mod.EventBusSubscriber.Bus.MOD)
public class MRegistryHandler {
	
	@SubscribeEvent
	public static void onRegisterItems(final RegistryEvent.Register<Item> event) {
		
		final IForgeRegistry<Item> registry = event.getRegistry();
		AtomicInteger blockItemCount = new AtomicInteger();
		
		MBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get).filter(MBlocks::shouldRegisterItemBlock).forEach(block -> {
			
			final Item.Properties properties = new Item.Properties().group(MItemGroup.MUSH);
			final BlockItem blockItem = new BlockItem(block, properties);
			blockItem.setRegistryName(Objects.requireNonNull(block.getRegistryName()));
			registry.register(blockItem);
			blockItemCount.getAndIncrement();
			
		});
		
	}
	

	public static void registerDeferred(IEventBus iEventBus) {
		
		MBlocks.BLOCKS.register(iEventBus);
		MItems.ITEMS.register(iEventBus);
		MPointOfInterestType.POI_TYPES.register(iEventBus);
		MProfession.PROFESSIONS.register(iEventBus);
		MEntityType.ENTITIES.register(iEventBus);
		//MFeatures.FEATURES.register(iEventBus);
		//MStructure.STRUCTURE_FEATURES.register(iEventBus);
		MBiome.BIOMES.register(iEventBus);
		
	}
	
}