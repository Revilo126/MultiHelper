package revilo.multihelper.structure.elements;

import net.minecraft.entity.Entity;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ChunkCoordinates;

import gregapi.tileentity.multiblocks.ITileEntityMultiBlockController;

public interface IStructureElement<T extends ITileEntityMultiBlockController> {

    boolean check(T t, ChunkCoordinates aCoordinates, Entity aPlayer, IInventory aInventory, int tX, int tY, int tZ);

}
