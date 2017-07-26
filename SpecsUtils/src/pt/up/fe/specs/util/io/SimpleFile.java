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

package pt.up.fe.specs.util.io;

/**
 * Represents a file.
 * 
 * @author JoaoBispo
 *
 */
public interface SimpleFile {

    /**
     * 
     * @return the contents of the file
     */
    String getContents();

    /**
     *
     * @return the filename
     */
    String getFilename();

    /**
     * Default constructor.
     * 
     * @param filename
     * @param contents
     * @return
     */
    static SimpleFile newInstance(String filename, String contents) {
	return new SimpleFile() {

	    @Override
	    public String getContents() {
		return contents;
	    }

	    @Override
	    public String getFilename() {
		return filename;
	    }

	};
    }

    /**
     * Helper constructor that receives a file.
     * 
     * @param file
     * @return
     */
    /*
    static LoadedFile newInstance(File file) {
    return newInstance(file.getName(), IoUtils.read(file));
    }
     */
}
