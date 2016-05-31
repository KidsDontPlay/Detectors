package mrriegel.detectors;

import java.awt.Color;
import java.util.Set;

import mrriegel.detectors.tile.TileBase;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import org.lwjgl.opengl.GL11;

import com.google.common.collect.Sets;

public class Render {

	@SubscribeEvent
	public void highlight(RenderWorldLastEvent event) {
		Minecraft mc = Minecraft.getMinecraft();

		EntityPlayerSP player = mc.thePlayer;

		long c = (System.currentTimeMillis() / 15l) % 360l;
		Color color = Color.getHSBColor(c / 360f, 1f, 1f);
		Set<BlockPos> set = Sets.newHashSet();
		color = Color.RED;

		for (TileEntity t : mc.theWorld.tickableTileEntities)
			if (t instanceof TileBase && ((TileBase) t).useBlockPosSet() && ((TileBase) t).isVisible())
				for (BlockPos p : ((TileBase) t).getBlockPosSet()) {
					set.add(p);
				}
		// renderBlockOverlays(event, player, set, 1f, .3f, 1f, 0.001f);
		renderBlockOverlays(event, player, set, color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f, 0.001f);

	}

	public static void renderBlockOverlays(RenderWorldLastEvent event, EntityPlayerSP player, Set<BlockPos> positions, float r, float g, float b, float offset) {
		if (positions == null || positions.size() == 0)
			return;

		double doubleX = player.lastTickPosX + (player.posX - player.lastTickPosX) * event.getPartialTicks();
		double doubleY = player.lastTickPosY + (player.posY - player.lastTickPosY) * event.getPartialTicks();
		double doubleZ = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * event.getPartialTicks();

		GlStateManager.pushAttrib();
		GlStateManager.disableTexture2D();
		GlStateManager.disableLighting();
		GlStateManager.enableBlend();

		GlStateManager.pushMatrix();
		GlStateManager.translate(-doubleX, -doubleY, -doubleZ);
		GlStateManager.pushAttrib();
		if (player.isSneaking())
			GL11.glDisable(GL11.GL_DEPTH_TEST);
		renderCubes(positions, offset, r, g, b, .6f);
		GlStateManager.disableBlend();
		renderOutlines(positions, 1.5f, offset + 0.001f, 0f, 0f, 0f, 1f);
		GlStateManager.popAttrib();

		// get around vanilla minecraft opengl bug by resetting attribs pre-pop
		GlStateManager.enableTexture2D();
		GlStateManager.enableLighting();
		GlStateManager.color(1f, 1f, 1f, 1f);

		GlStateManager.popMatrix();
		GlStateManager.popAttrib();
	}

	private static void renderCubes(Set<BlockPos> positions, float offset, float r, float g, float b, float a) {
		RenderHelper.enableStandardItemLighting();
		Tessellator t = Tessellator.getInstance();
		VertexBuffer renderer = t.getBuffer();
		renderer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION);
		GlStateManager.color(r, g, b, a);
		for (BlockPos coordinate : positions) {
			float x = coordinate.getX();
			float y = coordinate.getY();
			float z = coordinate.getZ();

			renderSingleCube(renderer, x, y, z, offset);
		}
		t.draw();
		RenderHelper.disableStandardItemLighting();
	}

	private static void renderSingleCube(VertexBuffer renderer, float x, float y, float z, float o) {
		// offsetting
		x = x - o;
		y = y - o;
		z = z - o;
		o = 1f + (o * 2f);

		// front
		renderer.pos(x + o, y + o, z + o).endVertex();
		renderer.pos(x, y + o, z + o).endVertex();
		renderer.pos(x, y, z + o).endVertex();
		renderer.pos(x + o, y, z + o).endVertex();

		// back
		renderer.pos(x, y, z).endVertex();
		renderer.pos(x, y + o, z).endVertex();
		renderer.pos(x + o, y + o, z).endVertex();
		renderer.pos(x + o, y, z).endVertex();

		// left
		renderer.pos(x, y + o, z + o).endVertex();
		renderer.pos(x, y + o, z).endVertex();
		renderer.pos(x, y, z).endVertex();
		renderer.pos(x, y, z + o).endVertex();

		// right
		renderer.pos(x + o, y, z).endVertex();
		renderer.pos(x + o, y + o, z).endVertex();
		renderer.pos(x + o, y + o, z + o).endVertex();
		renderer.pos(x + o, y, z + o).endVertex();

		// top
		renderer.pos(x, y + o, z).endVertex();
		renderer.pos(x, y + o, z + o).endVertex();
		renderer.pos(x + o, y + o, z + o).endVertex();
		renderer.pos(x + o, y + o, z).endVertex();

		// bottom
		renderer.pos(x + o, y, z + o).endVertex();
		renderer.pos(x, y, z + o).endVertex();
		renderer.pos(x, y, z).endVertex();
		renderer.pos(x + o, y, z).endVertex();
	}

	private static void renderOutlines(Set<BlockPos> positions, float thickness, float offset, float r, float g, float b, float a) {
		RenderHelper.enableStandardItemLighting();
		Tessellator tessellator = Tessellator.getInstance();
		VertexBuffer worldRenderer = tessellator.getBuffer();
		worldRenderer.begin(GL11.GL_LINES, DefaultVertexFormats.POSITION);
		GlStateManager.color(r, g, b, a);
		GL11.glLineWidth(thickness);

		for (BlockPos coordinate : positions) {
			float x = coordinate.getX();
			float y = coordinate.getY();
			float z = coordinate.getZ();

			renderSingleOutline(worldRenderer, x, y, z, offset);
		}

		tessellator.draw();
		RenderHelper.disableStandardItemLighting();
	}

	private static void renderSingleOutline(VertexBuffer renderer, float ox, float oy, float oz, float off) {
		ox = ox - off;
		oy = oy - off;
		oz = oz - off;
		off = 1f + (off * 2f);

		renderer.pos(ox, oy, oz).endVertex();
		renderer.pos(ox + off, oy, oz).endVertex();

		renderer.pos(ox, oy, oz).endVertex();
		renderer.pos(ox, oy + off, oz).endVertex();

		renderer.pos(ox, oy, oz).endVertex();
		renderer.pos(ox, oy, oz + off).endVertex();

		renderer.pos(ox + off, oy + off, oz + off).endVertex();
		renderer.pos(ox, oy + off, oz + off).endVertex();

		renderer.pos(ox + off, oy + off, oz + off).endVertex();
		renderer.pos(ox + off, oy, oz + off).endVertex();

		renderer.pos(ox + off, oy + off, oz + off).endVertex();
		renderer.pos(ox + off, oy + off, oz).endVertex();

		renderer.pos(ox, oy + off, oz).endVertex();
		renderer.pos(ox, oy + off, oz + off).endVertex();

		renderer.pos(ox, oy + off, oz).endVertex();
		renderer.pos(ox + off, oy + off, oz).endVertex();

		renderer.pos(ox + off, oy, oz).endVertex();
		renderer.pos(ox + off, oy, oz + off).endVertex();

		renderer.pos(ox + off, oy, oz).endVertex();
		renderer.pos(ox + off, oy + off, oz).endVertex();

		renderer.pos(ox, oy, oz + off).endVertex();
		renderer.pos(ox + off, oy, oz + off).endVertex();

		renderer.pos(ox, oy, oz + off).endVertex();
		renderer.pos(ox, oy + off, oz + off).endVertex();
	}
}
