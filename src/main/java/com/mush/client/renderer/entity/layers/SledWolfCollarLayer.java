package com.mush.client.renderer.entity.layers;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mush.entity.SledWolfEntity;

import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.WolfModel;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SledWolfCollarLayer extends LayerRenderer<SledWolfEntity, WolfModel<SledWolfEntity>> {
	
	private static final ResourceLocation WOLF_COLLAR = new ResourceLocation("textures/entity/wolf/wolf_collar.png");
	
	public SledWolfCollarLayer(IEntityRenderer<SledWolfEntity, WolfModel<SledWolfEntity>> p_i50926_1_) {
		super(p_i50926_1_);
		
	}
	
	@Override
	public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, SledWolfEntity entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
		
		if (entitylivingbaseIn.isTamed() && !entitylivingbaseIn.isInvisible()) {
			
			float[] afloat = entitylivingbaseIn.getCollarColor().getColorComponentValues();
			renderCutoutModel(this.getEntityModel(), WOLF_COLLAR, matrixStackIn, bufferIn, packedLightIn, entitylivingbaseIn, afloat[0], afloat[1], afloat[2]);
			
		}
		
	}
	
}