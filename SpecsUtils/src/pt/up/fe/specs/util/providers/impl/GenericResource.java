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

package pt.up.fe.specs.util.providers.impl;

import pt.up.fe.specs.util.providers.ResourceProvider;

public class GenericResource implements ResourceProvider {

    private final String resource;
    private final String version;

    public GenericResource(String resource) {
        this(resource, ResourceProvider.getDefaultVersion());
    }

    public GenericResource(String resource, String version) {
        this.resource = resource;
        this.version = version;
    }

    @Override
    public String getResource() {
        return resource;
    }

    @Override
    public String getVersion() {
        return version;
    }
}
