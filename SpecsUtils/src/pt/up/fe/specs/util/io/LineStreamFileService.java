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

package pt.up.fe.specs.util.io;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import pt.up.fe.specs.util.utilities.LineStream;

public class LineStreamFileService implements FileService {

    static class CachedInfo implements AutoCloseable {
        private LineStream stream;
        private int currentLineNumber;
        private String currentLine;

        public static CachedInfo newInstance(File file) {
            LineStream lineStream = LineStream.newInstance(file);
            return new CachedInfo(lineStream, 1, lineStream.nextLine());
        }

        private CachedInfo(LineStream stream, int currentLineNumber, String currentLine) {
            this.stream = stream;
            this.currentLineNumber = currentLineNumber;
            this.currentLine = currentLine;
        }

        public LineStream getStream() {
            return stream;
        }

        public void setFile(File file) {
            // Close previous stream
            this.stream.close();
            this.stream = LineStream.newInstance(file);
            currentLine = stream.nextLine();
            currentLineNumber = 1;
        }

        public int getCurrentLineNumber() {
            return currentLineNumber;
        }

        public String getCurrentLine() {
            return currentLine;
        }

        public void nextLine() {
            currentLine = stream.nextLine();
            currentLineNumber++;
        }

        @Override
        public void close() throws Exception {
            stream.close();
        }

    }

    private final Map<File, CachedInfo> cache;

    public LineStreamFileService() {
        cache = new HashMap<>();
    }

    @Override
    public String getLine(File file, int line) {
        // Check if file is already in the cache
        CachedInfo cachedInfo = cache.get(file);

        if (cachedInfo == null) {
            cachedInfo = CachedInfo.newInstance(file);
            cache.put(file, cachedInfo);
        }

        // If current line is before asked line, reload file
        if (cachedInfo.getCurrentLineNumber() > line) {
            // The method automatically closes the previous stream and updates the fields
            cachedInfo.setFile(file);

        }

        // Advance as many lines up to the needed line
        int linesToAdvance = line - cachedInfo.getCurrentLineNumber();
        for (int i = 0; i < linesToAdvance; i++) {
            cachedInfo.nextLine();
        }

        return cachedInfo.getCurrentLine();
    }

    @Override
    public void close() throws Exception {
        for (CachedInfo cachedInfo : cache.values()) {
            cachedInfo.close();
        }
    }

}
