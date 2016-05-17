package mrriegel.detectors;

import java.awt.Color;
import java.util.Set;

import mrriegel.detectors.tile.TileAnimalChecker;
import mrriegel.detectors.tile.TileBase;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import org.lwjgl.opengl.GL11;

import com.google.common.collect.Sets;

public class Render {

	@SubscribeEvent
	public void highlight(RenderWorldLastEvent event) {
		Minecraft mc = Minecraft.getMinecraft();

		EntityPlayerSP player = mc.thePlayer;

		double doubleX = player.lastTickPosX + (player.posX - player.lastTickPosX) * event.getPartialTicks();
		double doubleY = player.lastTickPosY + (player.posY - player.lastTickPosY) * event.getPartialTicks();
		double doubleZ = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * event.getPartialTicks();

		GlStateManager.pushMatrix();

		GlStateManager.disableTexture2D();
		GlStateManager.disableLighting();
		GlStateManager.translate(-doubleX, -doubleY, -doubleZ);
		// code

		long c = (System.currentTimeMillis() / 15l) % 360l;
		Color color = Color.getHSBColor(c / 360f, 1f, 1f);
		// Color color = Color.RED;
		for (TileEntity t : mc.theWorld.tickableTileEntities)
			if (t instanceof TileBase && ((TileBase) t).useBlockPosSet() && ((TileBase) t).isVisible())
				for (BlockPos p : ((TileBase) t).getSet()) {
					float x = p.getX(), y = p.getY(), z = p.getZ();
					// RenderHelper.enableStandardItemLighting();
					Tessellator tessellator = Tessellator.getInstance();
					VertexBuffer renderer = tessellator.getBuffer();
					renderer.begin(GL11.GL_LINES, DefaultVertexFormats.POSITION);
					GlStateManager.color(color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f, 1f);
					GL11.glLineWidth(2.5f);
					GlStateManager.pushAttrib();
					GL11.glDisable(GL11.GL_DEPTH_TEST);

					float offset = 1f;
					renderer.pos(x, y, z).endVertex();
					renderer.pos(x + offset, y, z).endVertex();

					renderer.pos(x, y, z).endVertex();
					renderer.pos(x, y + offset, z).endVertex();

					renderer.pos(x, y, z).endVertex();
					renderer.pos(x, y, z + offset).endVertex();

					renderer.pos(x + offset, y + offset, z + offset).endVertex();
					renderer.pos(x, y + offset, z + offset).endVertex();

					renderer.pos(x + offset, y + offset, z + offset).endVertex();
					renderer.pos(x + offset, y, z + offset).endVertex();

					renderer.pos(x + offset, y + offset, z + offset).endVertex();
					renderer.pos(x + offset, y + offset, z).endVertex();

					renderer.pos(x, y + offset, z).endVertex();
					renderer.pos(x, y + offset, z + offset).endVertex();

					renderer.pos(x, y + offset, z).endVertex();
					renderer.pos(x + offset, y + offset, z).endVertex();

					renderer.pos(x + offset, y, z).endVertex();
					renderer.pos(x + offset, y, z + offset).endVertex();

					renderer.pos(x + offset, y, z).endVertex();
					renderer.pos(x + offset, y + offset, z).endVertex();

					renderer.pos(x, y, z + offset).endVertex();
					renderer.pos(x + offset, y, z + offset).endVertex();

					renderer.pos(x, y, z + offset).endVertex();
					renderer.pos(x, y + offset, z + offset).endVertex();

					tessellator.draw();
					GlStateManager.popAttrib();
					// RenderHelper.disableStandardItemLighting();

				}

		GlStateManager.enableTexture2D();
		GlStateManager.enableLighting();
		GlStateManager.color(1f, 1f, 1f, 1f);

		GlStateManager.popMatrix();
	}
}
