package org.hampelratte.svdrp.util;

import java.util.Iterator;
import java.util.List;

import org.hampelratte.svdrp.responses.highlevel.Folder;
import org.hampelratte.svdrp.responses.highlevel.Recording;
import org.hampelratte.svdrp.responses.highlevel.TreeNode;
import org.hampelratte.svdrp.sorting.RecordingSortStrategy;

public class RecordingTreeBuilder {

    public static Folder buildTree(List<Recording> recordings, RecordingSortStrategy sortStrategy) {
        sortStrategy.sort(recordings);
        Folder root = buildTree(recordings);
        mergeConsecutiveFolders(root);
        return root;
    }

    public static Folder buildTree(List<Recording> recordings) {
        Folder root = new Folder("Aufnahmen");
        for (Recording recording : recordings) {
            TreeNode subtree = buildSubTree(recording);
            root.getChildren().add(subtree);
        }
        mergeConsecutiveFolders(root);
        return root;
    }

    private static TreeNode buildSubTree(Recording recording) {
        String title = recording.getTitle();
        String[] folders = title.split("~");

        if (folders.length > 1) {
            Folder subtree = new Folder(folders[0]);
            Folder currentFolder = subtree;
            for (int i = 1; i < folders.length; i++) {
                if (i < folders.length - 1) {
                    Folder child = new Folder(folders[i]);
                    currentFolder.getChildren().add(child);
                    currentFolder = child;
                } else {
                    currentFolder.getChildren().add(recording);
                }
            }
            return subtree;
        } else {
            return recording;
        }
    }

    private static void mergeConsecutiveFolders(Folder folder) {
        int merges = 1;
        while (merges > 0) {
            merges = 0;
            Iterator<TreeNode> iter = folder.getChildren().iterator();
            while (iter.hasNext()) {
                TreeNode current = iter.next();
                if (current instanceof Folder) {
                    Folder currentFolder = (Folder) current;
                    mergeConsecutiveFolders(currentFolder);
                    if (iter.hasNext()) {
                        TreeNode next = iter.next();
                        if (next instanceof Folder) {
                            if (current.getDisplayTitle().equals(next.getDisplayTitle())) {
                                merges++;
                                Folder nextFolder = (Folder) next;
                                currentFolder.getChildren().addAll(nextFolder.getChildren());
                                iter.remove();
                            }
                        }
                    }
                }
            }
        }
    }

    // ############### for debugging ##################
    // public static void printTree(TreeNode node, String indent) {
    // System.err.println(indent + node.getDisplayTitle());
    // if (node instanceof Folder) {
    // Folder folder = (Folder) node;
    // for (TreeNode child : folder.getChildren()) {
    // printTree(child, indent + "  ");
    // }
    // }
    //
    // }
}
