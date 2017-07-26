/**
 * Copyright 2012 SPeCS Research Group.
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

package pt.up.fe.specs.util.utilities;

import java.io.File;

import pt.up.fe.specs.util.SpecsIo;

/**
 * @author Joao Bispo
 * 
 */
public abstract class PersistenceFormat {

    /**
     * Writes an object to a file.
     * 
     * @param outputFile
     * @param anObject
     * @param aux
     * @return
     */
    public boolean write(File outputFile, Object anObject) {
	String contents = to(anObject);
	return SpecsIo.write(outputFile, contents);
    }

    /**
     * Reads an object from a file.
     * 
     * @param inputFile
     * @param classOfObject
     * @param aux
     * @return
     */
    public <T> T read(File inputFile, Class<T> classOfObject) {
	String contents = SpecsIo.read(inputFile);
	return from(contents, classOfObject);
    }

    public abstract String to(Object anObject);

    public abstract <T> T from(String contents, Class<T> classOfObject);

    public abstract String getExtension();
}
