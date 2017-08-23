/**
 * Copyright 2013 SPeCS Research Group.
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

package pt.up.fe.specs.eclipse.Tasks.Copy;

import java.io.File;

import pt.up.fe.specs.guihelper.FieldType;
import pt.up.fe.specs.guihelper.SetupAccess;
import pt.up.fe.specs.guihelper.Base.SetupFieldEnum;
import pt.up.fe.specs.guihelper.BaseTypes.SetupData;

/**
 * @author Joao Bispo
 * 
 */
public enum CopySetup implements SetupFieldEnum {

    DestinationFolder(FieldType.string),
    OutputJarFilename(FieldType.string);

    public static CopyData newData(SetupData setupData) {
	SetupAccess setup = new SetupAccess(setupData);

	File destinationFolder = setup.getFolderV2(null, DestinationFolder, false);
	//String destinationFolder = setup.getString(DestinationFolder);

	String outputJarFilename = setup.getString(OutputJarFilename);
	if(outputJarFilename.trim().isEmpty()) {
	    outputJarFilename = null;
	}
	
	return new CopyData(destinationFolder, outputJarFilename);
    }

    private CopySetup(FieldType fieldType) {
	this.fieldType = fieldType;
    }

    /**
     * INSTANCE VARIABLES
     */
    private final FieldType fieldType;

    /* (non-Javadoc)
     * @see pt.up.fe.specs.guihelper.Base.SetupFieldEnum#getType()
     */
    @Override
    public FieldType getType() {
	return fieldType;
    }

    /* (non-Javadoc)
     * @see pt.up.fe.specs.guihelper.Base.SetupFieldEnum#getSetupName()
     */
    @Override
    public String getSetupName() {
	return "Copy Task";
    }

    /* (non-Javadoc)
     * @see java.lang.Enum#toString()
     */
    @Override
    public String toString() {

	if (this == OutputJarFilename) {
	    return super.toString() + " (optional)";
	}

	return super.toString();
    }

}
