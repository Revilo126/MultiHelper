package revilo.multihelper.structure;

import static gregapi.data.CS.*;

import net.minecraft.entity.Entity;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ChunkCoordinates;

import gregapi.tileentity.multiblocks.ITileEntityMultiBlockController;

public interface IStructured<T extends ITileEntityMultiBlockController> {

    public Definition<T> getStructure();

    static boolean tSuccess = F;

    default boolean check(T t, ChunkCoordinates aCoordinates, Entity aPlayer, IInventory aInventory, int x, int y,
        int z, byte mFacing) {
        return getStructure().check(t, aCoordinates, aPlayer, aInventory, x, y, z, mFacing);
    }
}
