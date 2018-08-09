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

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

public class StringLogger implements TagLogger<String> {

    private final String baseName;
    private final Set<String> tags;

    public StringLogger(String baseName) {
        this(baseName, Collections.emptySet());
    }

    public StringLogger(String baseName, Set<String> tags) {
        this.baseName = baseName;
        this.tags = tags;
    }

    @Override
    public Collection<String> getTags() {
        return tags;
    }

    @Override
    public String getBaseName() {
        return baseName;
    }

    // @Override
    // public void info(String tag, String message) {
    // Preconditions.checkArgument(tags.contains(tag));
    // TagLogger.super.info(tag, message);
    // }

    // default void warn(T tag, String message) {
    // LogsHelper.logMessage(getClass().getName(), tag, message, (logger, msg) -> logger.warn(msg));
    // }
    //
    // default void warn(String message) {
    // warn(null, message);
    // }

}
