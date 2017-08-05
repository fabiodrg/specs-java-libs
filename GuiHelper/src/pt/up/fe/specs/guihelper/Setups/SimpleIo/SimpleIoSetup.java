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

package pt.up.fe.specs.guihelper.Setups.SimpleIo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import pt.up.fe.specs.guihelper.FieldType;
import pt.up.fe.specs.guihelper.SetupAccess;
import pt.up.fe.specs.guihelper.Base.SetupFieldEnum;
import pt.up.fe.specs.guihelper.BaseTypes.SetupData;
import pt.up.fe.specs.util.SpecsIo;
import pt.up.fe.specs.util.SpecsLogs;

/**
 *
 * @author Joao Bispo
 */
public enum SimpleIoSetup implements SetupFieldEnum {

    InputPath(FieldType.string),
    OutputFolder(FieldType.string);

    private SimpleIoSetup(FieldType optionType) {
	this.fieldType = optionType;
    }

    @Override
    public FieldType getType() {
	return fieldType;
    }

    @Override
    public String getSetupName() {
	return "Input/Output";
    }

    @Override
    public String toString() {
	if (this == InputPath) {
	    // return "InputElfs (FileOrFolder)";
	    return "InputFiles (FileOrFolder)";
	}

	return super.toString();
    }

    /**
     * Check if input is is single file or folder
     *
     * @param options
     * @return
     */
    public static SimpleIoData getGeneralIoData(SetupData options) {
	return getGeneralIoData(null, null, options);
    }

    /**
     * Check if input is is single file or folder
     * 
     * @param baseInputFolder
     * @param baseOutputFolder
     * @param options
     * @return
     */
    public static SimpleIoData getGeneralIoData(File baseInputFolder, File baseOutputFolder,
	    SetupData options) {

	SetupAccess setupAccess = new SetupAccess(options);
	String inputPath = setupAccess.getString(InputPath);
	File inputPathFile = new File(baseInputFolder, inputPath);
	File outputFolder = setupAccess.getFolder(baseOutputFolder, OutputFolder);

	return getGeneralIoData(inputPathFile.getPath(), outputFolder);
    }

    public static SimpleIoData getGeneralIoData(String inputPath, File outputFolder) {

	// Get Input
	File inputPathFile = new File(inputPath);
	if (!inputPathFile.exists()) {
	    SpecsLogs.msgInfo("Input path '" + inputPathFile + "' does not exist.");
	    return null;
	}

	// Determine if it is a file or a folder
	boolean isSingleFile = false;
	if (inputPathFile.isFile()) {
	    isSingleFile = true;
	}

	List<File> inputFiles = SimpleIoSetup.getFiles(inputPath, isSingleFile);

	if (outputFolder == null) {
	    SpecsLogs.getLogger().warning("Could not open folder '" + outputFolder + "'");
	    return null;
	}

	return new SimpleIoData(isSingleFile, inputPathFile, inputFiles, outputFolder);
    }

    public static List<File> getFiles(String inputPath, boolean isSingleFile) {
	// Is File mode
	if (isSingleFile) {
	    File inputFile = SpecsIo.existingFile(inputPath);
	    if (inputFile == null) {
		SpecsLogs.getLogger().warning("Could not open file.");
		return null;
	    }
	    List<File> files = new ArrayList<>();
	    files.add(inputFile);
	    return files;
	}
	// Is Folder mode
	File inputFolder = SpecsIo.mkdir(inputPath);
	if (inputFolder == null) {
	    SpecsLogs.getLogger().warning("Could not open folder.");
	    return null;
	}
	return SpecsIo.getFilesRecursive(inputFolder);
    }

    private final FieldType fieldType;
}
