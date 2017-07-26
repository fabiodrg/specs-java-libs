/**
 * Copyright 2015 SPeCS Research Group.
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

package pt.up.fe.specs.util.exceptions;

public class CaseNotDefinedException extends UnsupportedOperationException {

    private static final long serialVersionUID = 1L;

    // public CaseNotDefinedException(Object origin) {
    // this(origin.getClass());
    // }

    public CaseNotDefinedException(Class<?> undefinedCase) {
        super(getDefaultMessageClass(undefinedCase.getName()));
    }

    public CaseNotDefinedException(Enum<?> anEnum) {
        super(getDefaultMessageEnum(anEnum));
    }

    // public CaseNotDefinedException(String message) {
    // super("Case not defined: " + message);
    // }

    private static String getDefaultMessageClass(String originClass) {
        return "Case not defined for class '" + originClass + "'";
    }

    private static String getDefaultMessageEnum(Enum<?> anEnum) {
        return "Case not defined for enum '" + anEnum.name() + "'";
    }

}
