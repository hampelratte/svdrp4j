/*
 * Copyright (c) Henrik Niehaus
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 * 3. Neither the name of the project (Lazy Bones) nor the names of its
 *    contributors may be used to endorse or promote products derived from this
 *    software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
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
