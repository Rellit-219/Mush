package com.mush.item;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class MItemGroup {

	public static final ItemGroup MUSH = new ItemGroup("mush") {
	      @OnlyIn(Dist.CLIENT)
	      public ItemStack createIcon() {
	         return new ItemStack(MItems.SLED.get());
	      }
	   }.setBackgroundImageName("mush.png");
	
}