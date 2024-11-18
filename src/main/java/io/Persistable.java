package io;

public interface Persistable {
    boolean saveTofile(String fileName);

    void restoreFromFile(String fileName);
}
