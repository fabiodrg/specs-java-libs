/**
 * Copyright 2017 SPeCS.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License. under the License.
 */

package pt.up.fe.specs.lang;

import org.apache.commons.lang3.SystemUtils;

/**
 * Wrappers around Apache commons-lang utility methods related to system platform identification.
 * 
 * <p>
 * TODO: Rename to ApachePlatforms
 * 
 * @author JoaoBispo
 *
 */
public class SpecsPlatforms {

    /**
     * Returns true if the operating system is a form of Windows.
     */
    public static boolean isWindows() {
        return SystemUtils.IS_OS_WINDOWS;
    }

    /**
     * Returns true if the operating system is a form of Linux.
     */
    public static boolean isLinux() {
        return SystemUtils.IS_OS_LINUX;
    }

    public static String getPlatformName() {
        return SystemUtils.OS_NAME;
    }

    /**
     * Returns true if the operating system is a form of Linux or Solaris.
     */
    public static boolean isUnix() {
        return SystemUtils.IS_OS_UNIX;
    }

    /*
    public static Process getShell() {
        String cmd = getShellCommand();
    
        ProcessBuilder builder = new ProcessBuilder(cmd);
    
        try {
            return builder.start();
        } catch (IOException e) {
            throw new RuntimeException("Could not start process " + cmd);
        }
    }
    
    public static String getShellCommand() {
        if (isWindows()) {
            return "cmd.exe /start";
        }
    
        if (isUnix()) {
            return "/bin/bash";
        }
    
        throw new RuntimeException("No shell defined for platform " + getPlatformName());
    }
    */

}
