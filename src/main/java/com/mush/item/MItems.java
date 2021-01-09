package com.mush.item;

import com.mush.Mush;
import com.mush.block.MBlocks;

import net.minecraft.item.Item;
import net.minecraft.item.TallBlockItem;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class MItems {
	
	public static final Item.Properties PROPERTIES = new Item.Properties().group(MItemGroup.MUSH);
	
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Mush.MODID);
	
	public static final RegistryObject<Item> SLED = ITEMS.register("sled", () -> new SledItem(PROPERTIES));
	public static final RegistryObject<Item> HARNESS = ITEMS.register("harness", () -> new Item(PROPERTIES));
	public static final RegistryObject<Item> ASH_DOOR = ITEMS.register("ash_door", () -> new TallBlockItem(MBlocks.ASH_DOOR.get(), PROPERTIES));
	
}