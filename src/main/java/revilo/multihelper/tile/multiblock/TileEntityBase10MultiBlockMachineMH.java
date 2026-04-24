package revilo.multihelper.tile.multiblock;

import static gregapi.data.CS.*;

import net.minecraft.entity.Entity;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ChunkCoordinates;

import gregapi.tileentity.multiblocks.TileEntityBase10MultiBlockMachine;
import revilo.multihelper.structure.Definition;
import revilo.multihelper.structure.IStructured;

public abstract class TileEntityBase10MultiBlockMachineMH<T extends TileEntityBase10MultiBlockMachineMH<T>>
    extends TileEntityBase10MultiBlockMachine implements IStructured<T> {

    protected boolean tSuccess = F;

    public abstract Definition<T> getStructure();

    @SuppressWarnings("unchecked")
    @Override
    public boolean checkStructure2(ChunkCoordinates aCoordinates, Entity aPlayer, IInventory aInventory) {
        tSuccess = check((T) this, aCoordinates, aPlayer, aInventory, getX(), getY(), getZ(), mFacing);
        return tSuccess;
    }

    @Override
    public boolean isInsideStructure(int aX, int aY, int aZ) {
        int baseX = getOffsetXN(mFacing);
        int baseY = yCoord;
        int baseZ = getOffsetZN(mFacing);

        return getStructure().isInside(aX, aY, aZ, baseX, baseY, baseZ);
    }

}
