package revilo.multihelper.structure;

import static gregapi.data.CS.*;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.entity.Entity;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ChunkCoordinates;

import gregapi.tileentity.multiblocks.ITileEntityMultiBlockController;
import revilo.multihelper.structure.elements.IStructureElement;

public class Definition<T extends ITileEntityMultiBlockController> {

    public String[][] structure;
    private Map<Character, IStructureElement<T>> elements;
    public int offsetX, offsetY, offsetZ;

    public int getSizeX() {
        int max = 0;
        for (String[] layer : structure) for (String row : layer) max = Math.max(max, row.length());
        return max;
    }

    public int getSizeY() {
        int max = 0;
        for (String[] layer : structure) max = Math.max(max, layer.length);
        return max;
    }

    public int getSizeZ() {
        return structure.length;
    }

    public boolean isInside(int worldX, int worldY, int worldZ, int baseX, int baseY, int baseZ) {

        int minX = baseX - offsetX;
        int minY = baseY - offsetY;
        int minZ = baseZ - offsetZ;

        int maxX = minX + getSizeX() - 1;
        int maxY = minY + getSizeY() - 1;
        int maxZ = minZ + getSizeZ() - 1;

        return worldX >= minX && worldX <= maxX && worldY >= minY && worldY <= maxY && worldZ >= minZ && worldZ <= maxZ;
    }

    public static <T extends ITileEntityMultiBlockController> Builder<T> builder(String[][] structure) {
        return new Builder<>(structure);
    }

    public Definition(String[][] structure, Map<Character, IStructureElement<T>> elements) {
        this.structure = structure;
        this.elements = elements;
        findOffsets('-');
    }

    public int[] getOffsets() {
        return new int[] { offsetX, offsetY, offsetZ };
    }

    private void findOffsets(char anchor) {
        for (byte z = 0; z < structure.length; z++)
            for (byte y = 0; y < structure[z].length; y++) for (byte x = 0; x < structure[z][y].length(); x++) {
                if (structure[z][y].charAt(x) == anchor) {
                    this.offsetZ = z;
                    this.offsetY = y;
                    this.offsetX = x;
                    return;
                }
            }
        ERR.println("MultiHelper: No anchor found in structure!");
    }

    public static class Builder<T extends ITileEntityMultiBlockController> {

        private String[][] structure;
        private Map<Character, IStructureElement<T>> elements;

        public Builder(String[][] structure) {
            this.structure = structure;
            this.elements = new HashMap<>();
        }

        public Builder<T> addElement(char symbol, IStructureElement<T> part) {
            elements.putIfAbsent(symbol, part);
            return this;
        }

        public Definition<T> build() {
            Definition<T> def = new Definition<>(structure, elements);
            return def;
        }
    }

    public boolean check(T t, ChunkCoordinates aCoordinates, Entity aPlayer, IInventory aInventory, int tX, int tY,
        int tZ, byte mFacing) {
        boolean tSuccess = T;

        for (byte z = 0; z < structure.length; z++)
            for (byte y = 0; y < structure[z].length; y++) for (byte x = 0; x < structure[z][y].length(); x++) {
                char symbol = structure[z][y].charAt(x);
                if (symbol == '-' || symbol == ' ') continue;
                IStructureElement<T> element = elements.get(symbol);
                if (element == null) {
                    ERR.println(String.format("MultiHelper: Symbol '%s' is not defined!", symbol));
                    continue;
                }

                int dx = x - offsetX;
                int dz = z - offsetZ;

                int worldX = tX;
                int worldZ = tZ;

                int flippedOffsetY = getSizeY() - 1 - offsetY;
                int worldY = tY + (getSizeY() - 1 - y - flippedOffsetY);

                switch (mFacing) {
                    case SIDE_X_POS:
                        worldX -= dz;
                        worldZ += dx;
                        break;

                    case SIDE_X_NEG:
                        worldX += dz;
                        worldZ -= dx;
                        break;

                    case SIDE_Z_POS:
                        worldX -= dx;
                        worldZ -= dz;
                        break;

                    case SIDE_Z_NEG:
                    default:
                        worldX += dx;
                        worldZ += dz;
                        break;
                }

                // OUT.println(
                // String.format("MultiHelper: Checking %s at %d, %d, %d", symbol, worldX, worldY, worldZ));
                if (!element.check(t, aCoordinates, aPlayer, aInventory, worldX, worldY, worldZ)) {
                    /*
                     * ERR.println(
                     * String.format(
                     * "MultiHelper: Failed at %d, %d, %d, expected %s",
                     * worldX,
                     * worldY,
                     * worldZ,
                     * symbol));
                     */
                    tSuccess = F;
                }
            }
        return tSuccess;
    }
}
