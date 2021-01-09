package com.mush.block.tree;

import java.util.Random;

import com.mush.world.gen.feature.MFeatures;

import net.minecraft.block.trees.Tree;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;

public class AshTree extends Tree {

	@Override
	protected ConfiguredFeature<BaseTreeFeatureConfig, ?> getTreeFeature(Random random, boolean hasBeeNest) {
		
		return Feature.TREE.withConfiguration(MFeatures.ASH_TREE_CONFIG);
		
	}
	
}