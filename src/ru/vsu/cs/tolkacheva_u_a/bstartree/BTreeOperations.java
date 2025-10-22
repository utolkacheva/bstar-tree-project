package ru.vsu.cs.tolkacheva_u_a.bstartree;

public interface BTreeOperations<T extends Comparable<T>>  {
    void insert(T key);
    boolean search(T key);
    void remove(T key);
    void clear();
    int size();
    int getHeight();
    boolean isEmpty();
}
