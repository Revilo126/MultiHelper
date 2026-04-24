package revilo.multihelper.structure.elements;

import static gregapi.data.CS.*;

import net.minecraft.entity.Entity;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ChunkCoordinates;

import gregapi.tileentity.multiblocks.ITileEntityMultiBlockController;
import revilo.multihelper.structure.logic.ICountedStructure;

public class StructureElementMultiParts<T extends ITileEntityMultiBlockController> implements IStructureElement<T> {

    private final String[] keys;
    private final IStructureElement<T>[] parts;

    public StructureElementMultiParts(String[] keys, IStructureElement<T>[] parts) {

        this.keys = keys;
        this.parts = parts;
    }

    @Override
    public boolean check(T t, ChunkCoordinates aCoordinates, Entity aPlayer, IInventory aInventory, int tX, int tY,
        int tZ) {

        if (!(t instanceof ICountedStructure)) {
            return F;
        }

        ICountedStructure machine = (ICountedStructure) t;

        for (int i = 0; i < parts.length; i++) {

            if (machine.getCount(keys[i]) > 0 && parts[i].check(t, aCoordinates, aPlayer, aInventory, tX, tY, tZ)) {

                machine.decCount(keys[i]);
                return T;
            }
        }

        return F;
    }

}
