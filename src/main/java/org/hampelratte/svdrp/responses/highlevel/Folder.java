package org.hampelratte.svdrp.responses.highlevel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;


public class Folder implements TreeNode {
    private String title;

    private List<TreeNode> children = new ArrayList<TreeNode>();

    public Folder(String title) {
        super();

        if (!title.isEmpty()) {
            while (title.charAt(0) == ('%')) {
                title = title.substring(1);
            }
        }
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String getDisplayTitle() {
        return title;
    }

    public List<TreeNode> getChildren() {
        return children;
    }

    public void setChildren(List<TreeNode> children) {
        this.children = children;
    }

    /**
     * Merges the given subtree into this tree.
     * 
     * @param subtree
     */
    public void merge(TreeNode subtree) {
        if (subtree instanceof Folder) {

            List<Object[]> treesToMerge = new ArrayList<Object[]>();
            boolean matched = false;
            for (TreeNode child : getChildren()) {
                if (child instanceof Folder && child.getDisplayTitle().equals(subtree.getDisplayTitle())) {
                    treesToMerge.add(new Object[] { child, subtree });
                    matched = true;
                } else {
                    continue;
                }
            }

            if (!matched) {
                getChildren().add(subtree);
            }

            // merge trees, which have a matching name
            for (Iterator<Object[]> iterator = treesToMerge.iterator(); iterator.hasNext();) {
                Object[] trees = iterator.next();
                Folder childFolder = (Folder) trees[0];
                Folder subtreeFolder = (Folder) trees[1];
                for (TreeNode newChild : subtreeFolder.getChildren()) {
                    childFolder.merge(newChild);
                }
            }
        } else {
            getChildren().add(subtree);
        }
    }

    public void sort(Comparator<TreeNode> comparator) {
        Collections.sort(getChildren(), comparator);
        for (TreeNode child : getChildren()) {
            if (child instanceof Folder) {
                ((Folder) child).sort(comparator);
            }
        }
    }

    @Override
    public String toString() {
        return getDisplayTitle();
    }
}
