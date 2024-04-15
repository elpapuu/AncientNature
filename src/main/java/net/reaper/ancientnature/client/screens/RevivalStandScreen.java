package net.reaper.ancientnature.client.screens;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextColor;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.reaper.ancientnature.AncientNature;
import net.reaper.ancientnature.common.menu.RevivalStandMenu;
import net.reaper.ancientnature.common.util.ScreenUtils;

public class RevivalStandScreen extends AbstractContainerScreen<RevivalStandMenu> {

    public static final ResourceLocation TEXTURE = AncientNature.modLoc("textures/gui/revivalstand_gui.png");

    public RevivalStandScreen(RevivalStandMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }

    @Override
    protected void renderLabels(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY) {
        pGuiGraphics.drawString(this.font, this.title, this.titleLabelX, this.titleLabelY, 4210752, false);
        pGuiGraphics.drawString(this.font, this.playerInventoryTitle, this.inventoryLabelX, this.inventoryLabelY, 4210752, false);
    }

    @Override
    protected void renderBg(GuiGraphics pGuiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        pGuiGraphics.blit(TEXTURE, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);
        ScreenUtils.renderImageAnimationBottomTop(pGuiGraphics, TEXTURE, this.leftPos + 7, this.topPos + 31, 186, 6, 8, 21, this.menu.getTileEntity().getFuel(), this.menu.getTileEntity().getMaxFuel());
        int amberScale = ScreenUtils.getScaledInt(this.menu.getTileEntity().getAmberProgress(), this.menu.getTileEntity().getMaxAmberProgress(), 40);
        pGuiGraphics.blit(TEXTURE, this.leftPos + 36, this.topPos + 21, 176, 28, amberScale, 9);
        int fossilScale = ScreenUtils.getScaledInt(this.menu.getTileEntity().getFossilProgress(), this.menu.getTileEntity().getMaxFossilProgress(), 28);
        pGuiGraphics.blit(TEXTURE, this.leftPos + 97, this.topPos + 16, 176, 0, 10, fossilScale);
    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        this.renderBackground(pGuiGraphics);
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        this.renderTooltip(pGuiGraphics, pMouseX, pMouseY);
    }
}
