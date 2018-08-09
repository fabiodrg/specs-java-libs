/**
 * Copyright 2018 SPeCS.
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

package pt.up.fe.specs.util.logging;

import java.util.Arrays;
import java.util.Collection;

@FunctionalInterface
public interface EnumLogger<T extends Enum<T>> extends TagLogger<T> {

    Class<T> getEnumClass();

    @Override
    default String getBaseName() {
        return getEnumClass().getName();
    }

    @Override
    default Collection<T> getTags() {
        return Arrays.asList(getEnumClass().getEnumConstants());
    }

    @Override
    default EnumLogger<T> addToIgnoreList(Class<?> aClass) {
        TagLogger.super.addToIgnoreList(aClass);

        return this;
    }

    static <T extends Enum<T>> EnumLogger<T> newInstance(Class<T> enumClass) {
        return () -> enumClass;
    }

    // default void warn(T tag, String message) {
    // LogsHelper.logMessage(getClass().getName(), tag, message, (logger, msg) -> logger.warn(msg));
    // }
    //
    // default void warn(String message) {
    // warn(null, message);
    // }

}
