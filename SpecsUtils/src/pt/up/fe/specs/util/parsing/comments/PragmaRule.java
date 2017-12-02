/**
 * Copyright 2016 SPeCS.
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

package pt.up.fe.specs.util.parsing.comments;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import pt.up.fe.specs.util.SpecsLogs;

public class PragmaRule implements TextParserRule {

    private static final String PRAGMA = "#pragma";

    @Override
    public Optional<TextElement> apply(String line, Iterator<String> iterator) {

        // To calculate position of pragma
        String lastLine = line;

        // Check if line starts with '#pragma'
        String trimmedLine = line.trim();

        // First characters that can contain #pragma
        if (trimmedLine.length() < PRAGMA.length()) {
            return Optional.empty();
        }

        String probe = trimmedLine.substring(0, PRAGMA.length());
        if (!probe.toLowerCase().equals("#pragma")) {
            return Optional.empty();
        }

        // Found start of pragma. Try to find the end
        trimmedLine = trimmedLine.substring(PRAGMA.length()).trim();

        List<String> pragmaContents = new ArrayList<String>();

        while (trimmedLine.endsWith("\\")) {
            // Add line, without the ending '\'
            pragmaContents.add(trimmedLine.substring(0, trimmedLine.length() - 1));

            if (!iterator.hasNext()) {
                SpecsLogs.msgInfo("Could not parse #pragma, there is no more lines after '" + trimmedLine + "'");
                return Optional.empty();
            }

            // Get next line
            lastLine = iterator.next();
            trimmedLine = lastLine.trim();
        }

        // Add last non-broken line
        pragmaContents.add(trimmedLine);

        return Optional.of(TextElement.newInstance(TextElementType.PRAGMA,
                pragmaContents.stream().collect(Collectors.joining("\n"))));

    }

}
