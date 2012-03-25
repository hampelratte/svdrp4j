package org.hampelratte.svdrp.util;

import java.util.List;

import org.hampelratte.svdrp.responses.highlevel.Folder;
import org.hampelratte.svdrp.responses.highlevel.Recording;
import org.hampelratte.svdrp.responses.highlevel.TreeNode;

public class RecordingTreeBuilder {

    public static Folder buildTree(List<Recording> recordings) {
        Folder root = new Folder("Aufnahmen");

        for (Recording recording : recordings) {
            TreeNode subtree = buildSubTree(recording);
            root.merge(subtree);
        }

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
}
