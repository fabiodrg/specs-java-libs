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

package pt.up.fe.specs.util.stringsplitter;

import pt.up.fe.specs.util.SpecsStrings;
import pt.up.fe.specs.util.parsing.StringDecoder;
import pt.up.fe.specs.util.utilities.StringSlice;

public class StringSplitterRules {

    /**
     * Looks for a string defined by the StringSlice separator, or the complete string if no separator was found.
     * 
     * <p>
     * The default separator is a whitespace, as determined by the function
     * {@link java.lang.Character#isWhitespace(char)}.
     * 
     * @param string
     * @return
     */
    public static SplitResult<String> word(StringIterator string) {
        SplitResult<String> nextResult = string.next();
        // int endIndex = string.indexOfFirstWhiteSpace();
        // if (endIndex == -1) {
        // endIndex = string.length();
        // }
        //
        // String element = string.substring(0, endIndex).toString();
        //
        // // Update slice
        // string = string.substring(endIndex);

        return new SplitResult<>(nextResult.getModifiedSlice(), nextResult.getValue());
    }

    /**
     * Looks for a word (as defined by {@link StringSplitterRules#word(StringSlice)}) and tries to transform into an
     * object using the provided decoder.
     * 
     * @param string
     * @param decoder
     * @return
     */
    public static <T> SplitResult<T> object(StringIterator string, StringDecoder<T> decoder) {
        // Get word
        SplitResult<String> results = word(string);

        // Try to decode string
        T decodedObject = decoder.apply(results.getValue());

        if (decodedObject == null) {
            return null;
        }

        return new SplitResult<>(results.getModifiedSlice(), decodedObject);
    }

    /**
     * Looks for an integer at the beginning of the string.
     * 
     * @param string
     * @return
     */
    public static SplitResult<Integer> integer(StringIterator string) {
        return object(string, SpecsStrings::parseInteger);
    }

    /**
     * Looks for a double at the beginning of the string.
     * 
     * @param string
     * @return
     */
    public static SplitResult<Double> doubleNumber(StringIterator string) {
        return object(string, doubleString -> SpecsStrings.parseDouble(doubleString, false));
    }

    /**
     * Looks for a float at the beginning of the string.
     * 
     * @param string
     * @return
     */
    public static SplitResult<Float> floatNumber(StringIterator string) {
        return object(string, floatString -> SpecsStrings.parseFloat(floatString, false));
    }
}
