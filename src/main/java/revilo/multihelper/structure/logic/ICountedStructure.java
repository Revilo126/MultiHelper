package revilo.multihelper.structure.logic;

public interface ICountedStructure {

    int getCount(String key);

    void setCount(String key, int value);

    void decCount(String key);
}
