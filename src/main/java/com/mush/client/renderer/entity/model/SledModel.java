package com.mush.client.renderer.entity.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.mush.entity.item.SledEntity;

import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;

//Made with Blockbench 3.7.4
//Exported for Minecraft version 1.15

public class SledModel extends EntityModel<SledEntity> {
	
	private final ModelRenderer sled;
	private final ModelRenderer left_ski;
	private final ModelRenderer ski_base_2_r1;
	private final ModelRenderer right_ski;
	private final ModelRenderer ski_base_2_r2;
	private final ModelRenderer base;
	private final ModelRenderer base_2_r1;
	private final ModelRenderer front;
	private final ModelRenderer front_r1;
	private final ModelRenderer front_left_r1;
	private final ModelRenderer front_right_r1;
	private final ModelRenderer storage;
	private final ModelRenderer right_side_r1;
	private final ModelRenderer base_5_r1;
	private final ModelRenderer base_3_r1;
	private final ModelRenderer base_2_r2;
	private final ModelRenderer back;
	private final ModelRenderer back_6_r1;
	private final ModelRenderer back_1_r1;

	public SledModel() {
		textureWidth = 80;
		textureHeight = 80;

		sled = new ModelRenderer(this);
		sled.setRotationPoint(0.0F, 24.0F, -16.0F);
		

		left_ski = new ModelRenderer(this);
		left_ski.setRotationPoint(0.0F, 0.0F, 0.0F);
		sled.addChild(left_ski);
		left_ski.setTextureOffset(4, 47).addBox(7.0F, -1.0F, 8.0F, 1.0F, 1.0F, 32.0F, 0.0F, false);

		ski_base_2_r1 = new ModelRenderer(this);
		ski_base_2_r1.setRotationPoint(8.0F, -3.0F, -7.0F);
		left_ski.addChild(ski_base_2_r1);
		setRotationAngle(ski_base_2_r1, -0.3927F, 0.0F, 0.0F);
		ski_base_2_r1.setTextureOffset(21, 64).addBox(-1.0F, -4.0F, 0.0F, 1.0F, 1.0F, 15.0F, 0.0F, false);

		right_ski = new ModelRenderer(this);
		right_ski.setRotationPoint(0.0F, 0.0F, 0.0F);
		sled.addChild(right_ski);
		right_ski.setTextureOffset(4, 47).addBox(-8.0F, -1.0F, 8.0F, 1.0F, 1.0F, 32.0F, 0.0F, false);

		ski_base_2_r2 = new ModelRenderer(this);
		ski_base_2_r2.setRotationPoint(8.0F, -3.0F, -7.0F);
		right_ski.addChild(ski_base_2_r2);
		setRotationAngle(ski_base_2_r2, -0.3927F, 0.0F, 0.0F);
		ski_base_2_r2.setTextureOffset(21, 64).addBox(-16.0F, -4.0F, 0.0F, 1.0F, 1.0F, 15.0F, 0.0F, false);

		base = new ModelRenderer(this);
		base.setRotationPoint(8.0F, 0.0F, -8.0F);
		sled.addChild(base);
		base.setTextureOffset(0, 20).addBox(-15.0F, -2.0F, 16.0F, 14.0F, 1.0F, 22.0F, 0.0F, false);

		base_2_r1 = new ModelRenderer(this);
		base_2_r1.setRotationPoint(0.0F, 11.0F, -7.0F);
		base.addChild(base_2_r1);
		setRotationAngle(base_2_r1, -0.3927F, 0.0F, 0.0F);
		base_2_r1.setTextureOffset(9, 29).addBox(-15.0F, -21.0F, 4.0F, 14.0F, 1.0F, 13.0F, 0.0F, false);

		front = new ModelRenderer(this);
		front.setRotationPoint(0.0F, 0.0F, 0.0F);
		sled.addChild(front);
		

		front_r1 = new ModelRenderer(this);
		front_r1.setRotationPoint(2.0F, -2.0F, 7.0F);
		front.addChild(front_r1);
		setRotationAngle(front_r1, 0.0F, -1.5708F, 0.0F);
		front_r1.setTextureOffset(32, 75).addBox(-16.0F, -5.0F, 0.0F, 1.0F, 1.0F, 4.0F, 0.0F, false);

		front_left_r1 = new ModelRenderer(this);
		front_left_r1.setRotationPoint(6.0F, -2.0F, 20.0F);
		front.addChild(front_left_r1);
		setRotationAngle(front_left_r1, 0.0F, 1.0472F, 0.0F);
		front_left_r1.setTextureOffset(29, 72).addBox(22.0F, -5.0F, -18.0F, 1.0F, 1.0F, 7.0F, 0.0F, false);

		front_right_r1 = new ModelRenderer(this);
		front_right_r1.setRotationPoint(6.0F, -2.0F, 5.0F);
		front.addChild(front_right_r1);
		setRotationAngle(front_right_r1, 0.0F, -1.0472F, 0.0F);
		front_right_r1.setTextureOffset(29, 72).addBox(-16.0F, -5.0F, 0.0F, 1.0F, 1.0F, 7.0F, 0.0F, false);

		storage = new ModelRenderer(this);
		storage.setRotationPoint(0.0F, -6.0F, 0.0F);
		sled.addChild(storage);
		storage.setTextureOffset(10, 30).addBox(-7.0F, -2.0F, 18.0F, 14.0F, 1.0F, 12.0F, 0.0F, false);
		storage.setTextureOffset(38, 59).addBox(-5.0F, -12.0F, 20.0F, 10.0F, 10.0F, 10.0F, 0.0F, false);
		storage.setTextureOffset(38, 65).addBox(-1.0F, -10.0F, 19.0F, 2.0F, 3.0F, 1.0F, 0.0F, false);

		right_side_r1 = new ModelRenderer(this);
		right_side_r1.setRotationPoint(-7.0F, 4.0F, -20.0F);
		storage.addChild(right_side_r1);
		setRotationAngle(right_side_r1, 0.2182F, 0.0F, 0.0F);
		right_side_r1.setTextureOffset(0, 43).addBox(-1.0F, -1.0F, 16.0F, 1.0F, 1.0F, 36.0F, 0.0F, false);
		right_side_r1.setTextureOffset(0, 43).addBox(14.0F, -1.0F, 16.0F, 1.0F, 1.0F, 36.0F, 0.0F, false);

		base_5_r1 = new ModelRenderer(this);
		base_5_r1.setRotationPoint(7.0F, 42.0F, -31.0F);
		storage.addChild(base_5_r1);
		setRotationAngle(base_5_r1, 0.0F, -1.5708F, 0.0F);
		base_5_r1.setTextureOffset(22, 65).addBox(48.0F, -41.0F, 0.0F, 1.0F, 1.0F, 14.0F, 0.0F, false);
		base_5_r1.setTextureOffset(22, 65).addBox(48.0F, -46.0F, 0.0F, 1.0F, 1.0F, 14.0F, 0.0F, false);

		base_3_r1 = new ModelRenderer(this);
		base_3_r1.setRotationPoint(48.0F, -5.0F, -82.0F);
		storage.addChild(base_3_r1);
		setRotationAngle(base_3_r1, -1.5708F, -1.5708F, 0.0F);
		base_3_r1.setTextureOffset(26, 69).addBox(99.0F, -41.0F, 0.0F, 1.0F, 1.0F, 10.0F, 0.0F, false);

		base_2_r2 = new ModelRenderer(this);
		base_2_r2.setRotationPoint(33.0F, -5.0F, -82.0F);
		storage.addChild(base_2_r2);
		setRotationAngle(base_2_r2, -1.5708F, -1.5708F, 0.0F);
		base_2_r2.setTextureOffset(26, 69).addBox(99.0F, -41.0F, 0.0F, 1.0F, 1.0F, 10.0F, 0.0F, false);

		back = new ModelRenderer(this);
		back.setRotationPoint(0.0F, 0.0F, 0.0F);
		sled.addChild(back);
		

		back_6_r1 = new ModelRenderer(this);
		back_6_r1.setRotationPoint(7.0F, 38.0F, -18.0F);
		back.addChild(back_6_r1);
		setRotationAngle(back_6_r1, 0.0F, -1.5708F, 0.0F);
		back_6_r1.setTextureOffset(22, 65).addBox(48.0F, -41.0F, 0.0F, 1.0F, 1.0F, 14.0F, 0.0F, false);
		back_6_r1.setTextureOffset(22, 65).addBox(48.0F, -47.0F, 0.0F, 1.0F, 1.0F, 14.0F, 0.0F, false);
		back_6_r1.setTextureOffset(22, 65).addBox(48.0F, -53.0F, 0.0F, 1.0F, 1.0F, 14.0F, 0.0F, false);
		back_6_r1.setTextureOffset(22, 65).addBox(48.0F, -59.0F, 0.0F, 1.0F, 1.0F, 14.0F, 0.0F, false);

		back_1_r1 = new ModelRenderer(this);
		back_1_r1.setRotationPoint(8.0F, -39.0F, 30.0F);
		back.addChild(back_1_r1);
		setRotationAngle(back_1_r1, -1.5708F, 0.0F, 0.0F);
		back_1_r1.setTextureOffset(14, 57).addBox(-1.0F, -1.0F, 16.0F, 1.0F, 1.0F, 22.0F, 0.0F, false);
		back_1_r1.setTextureOffset(14, 57).addBox(-16.0F, -1.0F, 16.0F, 1.0F, 1.0F, 22.0F, 0.0F, false);
		
	}

	@Override
	public void setRotationAngles(SledEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch){
		
	}

	@Override
	public void render(MatrixStack matrixStack, IVertexBuilder buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
		sled.render(matrixStack, buffer, packedLight, packedOverlay);
	}

	public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
		modelRenderer.rotateAngleX = x;
		modelRenderer.rotateAngleY = y;
		modelRenderer.rotateAngleZ = z;
	}
	
}