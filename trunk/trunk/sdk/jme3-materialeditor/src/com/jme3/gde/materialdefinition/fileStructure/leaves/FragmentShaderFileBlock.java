/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jme3.gde.materialdefinition.fileStructure.leaves;

import com.jme3.util.blockparser.Statement;

/**
 *
 * @author Nehon
 */
public class FragmentShaderFileBlock extends ShaderFileBlock {

    public FragmentShaderFileBlock(int lineNumber, String line) {
        super(lineNumber, line);
    }

    public FragmentShaderFileBlock(Statement sta) {
        super(sta);
    }

    public FragmentShaderFileBlock(String language, String path, int lineNumber, String line) {
        super(language, path, lineNumber, line);
    }

    @Override
    protected void updateLine() {
        super.updateLine();
        line = "Fragment" + line;
    }
}
