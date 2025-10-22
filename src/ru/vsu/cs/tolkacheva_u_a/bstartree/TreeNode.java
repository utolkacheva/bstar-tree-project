package ru.vsu.cs.tolkacheva_u_a.bstartree;

import java.util.List;

public interface TreeNode<T> {
    List<T> getKeys();
    List<? extends TreeNode<T>> getChildren();
    boolean isLeaf();
    TreeNode<T> getParent();
}
