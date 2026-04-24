package revilo.multihelper.structure.elements;

import static gregapi.data.CS.*;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChunkCoordinates;

import gregapi.data.CS.SFX;
import gregapi.tileentity.multiblocks.ITileEntityMultiBlockController;
import gregapi.util.ST;
import gregapi.util.UT;
import gregapi.util.WD;

public class StructureElementBlock<T extends ITileEntityMultiBlockController> implements IStructureElement<T> {

    private Block element;
    private int aMeta;

    public StructureElementBlock(Block element) {
        this.element = element;
        this.aMeta = 0;
    }

    public StructureElementBlock(Block element, int aMeta) {
        this.element = element;
        this.aMeta = aMeta;
    }

    public static boolean checkAndSetTargetBlock(ITileEntityMultiBlockController aController, int aX, int aY, int aZ,
        Block aBlock, int aMeta, ChunkCoordinates aClickedAt, Entity aPlayer, IInventory aInventory) {
        if ((aInventory != null || aPlayer != null)
            && (aClickedAt == null || (Math.abs(aX - aClickedAt.posX) < 2 && Math.abs(aY - aClickedAt.posY) < 2
                && Math.abs(aZ - aClickedAt.posZ) < 2))) {
            ItemStack aStack = ST.make(aBlock, 1, aMeta);
            if (WD.easyRep(aController.getWorld(), aX, aY, aZ) && UT.Entities.canEdit(aPlayer, aX, aY, aZ, aStack)) {
                if (aInventory == null || UT.Entities.hasInfiniteItems(aPlayer)) {
                    if (WD.set(aController.getWorld(), aX, aY, aZ, ST.make(aBlock, 1, aMeta)) && aPlayer != null) {
                        UT.Sounds.send(SFX.MC_XP, aController.getWorld(), aX, aY, aZ);
                        return T;
                    }
                } else for (int i = aInventory.getSizeInventory() - 1; i >= 0; i--) {
                    ItemStack tStack = aInventory.getStackInSlot(i);
                    if (ST.equal(aStack, tStack, T) && ST.use(aPlayer, T, T, tStack, 1)) {
                        if (WD.set(aController.getWorld(), aX, aY, aZ, tStack) && aPlayer != null) {
                            UT.Sounds.send(SFX.MC_XP, aController.getWorld(), aX, aY, aZ);
                            return T;
                        }
                        break;
                    }
                }
            }
        }
        return F;
    }

    @Override
    public boolean check(T t, ChunkCoordinates aCoordinates, Entity aPlayer, IInventory aInventory, int tX, int tY,
        int tZ) {
        if (checkAndSetTargetBlock(t, tZ, tX, tY, element, aMeta, aCoordinates, aPlayer, aInventory)) return T;
        return F;
    }

}
