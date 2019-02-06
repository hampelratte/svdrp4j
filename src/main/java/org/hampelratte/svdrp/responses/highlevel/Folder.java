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
package org.hampelratte.svdrp.responses.highlevel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class Folder implements TreeNode {
    private String title;
    private String displayTitle;

    private List<TreeNode> children = new ArrayList<TreeNode>();

    public Folder(String title) {
        super();
        setTitle(title);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;

        displayTitle = title;
        if (!displayTitle.isEmpty()) {
            while (displayTitle.charAt(0) == ('%')) {
                displayTitle = displayTitle.substring(1);
            }
        }
    }

    @Override
    public String getDisplayTitle() {
        return displayTitle;
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
     * @param subtree to merge
     * @return the merge point as Folder
     */
    public Folder merge(TreeNode subtree) {
        Folder mergePoint = null;
        if (subtree instanceof Folder) {

            List<Object[]> treesToMerge = new ArrayList<Object[]>();
            boolean matched = false;
            for (TreeNode child : getChildren()) {
                if (child instanceof Folder && child.getDisplayTitle().equals(subtree.getDisplayTitle())) {
                    treesToMerge.add(new Object[] { child, subtree });
                    matched = true;
                    mergePoint = (Folder) child;
                } else {
                    continue;
                }
            }

            if (!matched) {
                getChildren().add(subtree);
                mergePoint = this;
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
            mergePoint = this;
        }
        return mergePoint;
    }

    @Override
    public String toString() {
        return getDisplayTitle();
    }
}
