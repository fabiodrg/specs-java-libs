/*
 * Copyright 2011 SPeCS Research Group.
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

package pt.up.fe.specs.util.asm.processor;

import pt.up.fe.specs.util.SpecsLogs;
import pt.up.fe.specs.util.SpecsStrings;

/**
 * Utility methods related to Registers.
 *
 * @author Joao Bispo
 */
public class RegisterUtils {

    public static String buildRegisterBit(RegisterId regId, int bitPosition) {
	// return regId.getName() + REGISTER_BIT_OPEN + bitPosition + REGISTER_BIT_CLOSE;
	return regId.getName() + RegisterUtils.REGISTER_BIT_START + bitPosition;
    }

    /**
     *
     * <p>
     * Example: if given the string MSR[29], returns 29.
     * 
     * @param registerFlagName
     * @return
     */
    public static Integer decodeFlagBit(String registerFlagName) {
	// int beginIndex = registerFlagName.indexOf(REGISTER_BIT_OPEN);
	int beginIndex = registerFlagName.indexOf(RegisterUtils.REGISTER_BIT_START);
	// int endIndex = registerFlagName.indexOf(REGISTER_BIT_CLOSE);

	// if(beginIndex == -1 || endIndex == -1) {
	if (beginIndex == -1) {
	    SpecsLogs.getLogger().
		    warning("Flag '" + registerFlagName + "' does not represent "
			    + "a valid flag.");
	    return null;
	}

	// String bitNumber = registerFlagName.substring(beginIndex+1, endIndex);
	String bitNumber = registerFlagName.substring(beginIndex + 1);
	return SpecsStrings.parseInteger(bitNumber);
    }

    /**
     * <p>
     * Example: if given the string MSR[29], returns MSR.
     * 
     * @param registerFlagName
     * @return
     */
    public static String decodeFlagName(String registerFlagName) {
	// int beginIndex = registerFlagName.indexOf(REGISTER_BIT_OPEN);
	int beginIndex = registerFlagName.indexOf(RegisterUtils.REGISTER_BIT_START);
	if (beginIndex == -1) {
	    SpecsLogs.getLogger().
		    warning("Flag '" + registerFlagName + "' does not represent "
			    + "a valid flag.");
	    return null;
	}

	return registerFlagName.substring(0, beginIndex);
    }

    private static final String REGISTER_BIT_START = "_";
    // private static final String REGISTER_BIT_OPEN = "[";
    // private static final String REGISTER_BIT_CLOSE = "]";
}
