package revilo.multihelper.structure.elements;

import static gregapi.data.CS.*;

import net.minecraft.entity.Entity;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ChunkCoordinates;

import gregapi.tileentity.multiblocks.ITileEntityMultiBlockController;

public class StructureElementPart<T extends ITileEntityMultiBlockController> implements IStructureElement<T> {

    private final int aRegistryMeta, aRegistryID, aDesign, aMode;

    public StructureElementPart(int tRegistryMeta, int tRegistryID, int tDesign, int tMode) {
        this.aRegistryMeta = tRegistryMeta;
        this.aRegistryID = tRegistryID;
        this.aDesign = tDesign;
        this.aMode = tMode;
    }

    @Override
    public boolean check(T t, ChunkCoordinates aCoordinates, Entity aPlayer, IInventory aInventory, int tX, int tY,
        int tZ) {
        try {
            return ITileEntityMultiBlockController.Util.checkAndSetTarget(
                t,
                tX,
                tY,
                tZ,
                aRegistryMeta,
                aRegistryID,
                aDesign,
                aMode,
                aCoordinates,
                aPlayer,
                aInventory);
        } catch (Throwable e) {
            e.printStackTrace(ERR);
        }
        return F;
    }
}
