/* $Id$
 * 
 * Copyright (c) 2005, Henrik Niehaus & Lazy Bones development team
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
package org.hampelratte.svdrp.commands;

import org.hampelratte.svdrp.Command;

/**
 * Send a command to a plugin.
 * 
 * @author <a href="hampelratte@users.berlios.de">hampelratte@users.berlios.de</a>
 */
public class PLUG extends Command {

    private boolean mainSwitch = false;
    private boolean helpSwitch = false;
    
    private String pluginName = "";
    private String pluginCommand = "";
    private String options = "";
    
    /**
     * Send a command to a plugin.
     * @param pluginName Name of the plugin to send the command to
     * @param pluginCommand Name of the command to call
     */
    public PLUG(String pluginName, String pluginCommand) {
        super();
        this.pluginName = pluginName;
        this.pluginCommand = pluginCommand;
    }

    /**
     * Send a command to a plugin.
     * @param pluginName Name of the plugin to send the command to
     * @param mainSwitch Inserts the keyword "MAIN" in the command
     * @param helpSwitch Inserts the keyword "HELP" in the command
     * @param pluginCommand Name of the command to call
     * @param options Options for the command
     */
    public PLUG(String pluginName, boolean mainSwitch, boolean helpSwitch, String pluginCommand, String options) {
        super();
        this.mainSwitch = mainSwitch;
        this.helpSwitch = helpSwitch;
        this.pluginName = pluginName;
        this.pluginCommand = pluginCommand;
        this.options = options;
    }

    @Override
    public String getCommand() {
        StringBuilder sb = new StringBuilder("PLUG ");
        
        // append plugin name
        sb.append(pluginName);
        
        // append "main" or "help"
        if(isMainSwitch()) {
            sb.append(" MAIN");
        } else if(isHelpSwitch()) {
            sb.append(" HELP");
        }
        
        // append command if not empty
        if(pluginCommand.length() > 0) {
            sb.append(" "); sb.append(pluginCommand);
        }
        
        // append options if not empty
        if(options.length() > 0) {
            sb.append(" "); sb.append(options);
        }
        
        return sb.toString();
    }

    @Override
    public String toString() {
        return "PLUG";
    }

    public boolean isMainSwitch() {
        return mainSwitch;
    }

    /**
     * Inserts the keyword "MAIN" in the command. Either helpSwitch or
     * mainSwitch can be enabled. If helpSwitch is enabled and you enable
     * mainSwitch afterwards, helpSwitch will be disabled
     * 
     * @param mainSwitch
     *            if set to true, the command will contain the keyword "MAIN"
     */
    public void setMainSwitch(boolean mainSwitch) {
        this.mainSwitch = mainSwitch;
        if(mainSwitch && helpSwitch) {
            helpSwitch = false;
        }
    }

    public boolean isHelpSwitch() {
        return helpSwitch;
    }

    /**
     * Inserts the keyword "HELP" in the command. Either helpSwitch or
     * mainSwitch can be enabled. If mainSwitch is enabled and you enable
     * helpSwitch afterwards, mainSwitch will be disabled
     * 
     * @param helpSwitch
     *            if set to true, the command will contain the keyword "HELP"
     */
    public void setHelpSwitch(boolean helpSwitch) {
        this.helpSwitch = helpSwitch;
        if(helpSwitch && mainSwitch) {
            mainSwitch = false;
        }
    }

    public String getPluginName() {
        return pluginName;
    }

    public void setPluginName(String pluginName) {
        this.pluginName = pluginName;
    }

    public String getOptions() {
        return options;
    }

    public void setOptions(String options) {
        this.options = options;
    }

    public String getPluginCommand() {
        return pluginCommand;
    }

    public void setPluginCommand(String pluginCommand) {
        this.pluginCommand = pluginCommand;
    }
}
