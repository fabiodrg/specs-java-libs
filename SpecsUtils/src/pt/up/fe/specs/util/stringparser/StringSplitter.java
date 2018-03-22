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

package pt.up.fe.specs.util.stringparser;

import java.util.function.Predicate;

import pt.up.fe.specs.util.utilities.StringSlice;

/**
 * StringSlice with splitting capabilities.
 * 
 * @author JoaoBispo
 *
 */
public class StringSplitter extends StringSlice {

    private static final Predicate<Character> DEFAULT_SEPARATOR = aChar -> Character.isWhitespace(aChar);

    public static class NextResult {
        private final StringSlice modifiedSlice;
        private final String word;

        public NextResult(StringSlice modifiedSlice, String word) {
            this.modifiedSlice = modifiedSlice;
            this.word = word;
        }

        public StringSlice getModifiedSlice() {
            return modifiedSlice;
        }

        public String getWord() {
            return word;
        }
    }

    private final boolean trim;
    private final boolean reverse;
    private final Predicate<Character> separator;

    // public StringSplitter(String value, boolean trim, boolean reverse,
    // Predicate<Character> separator) {
    // this(new StringSlice(value), trim, reverse, separator);
    // }
    //
    // public StringSplitter(String value, int start, int end, boolean trim, boolean reverse,
    // Predicate<Character> separator) {
    // this(new StringSlice(value, start, end), trim, reverse, separator);
    // }

    public StringSplitter(StringSlice stringSlice) {
        this(stringSlice, false, false, DEFAULT_SEPARATOR);
    }

    public StringSplitter(StringSlice stringSlice, boolean trim, boolean reverse,
            Predicate<Character> separator) {

        super(stringSlice);

        this.trim = trim;
        this.reverse = reverse;
        this.separator = separator;
    }

    public StringSplitter setTrim(boolean trim) {
        return new StringSplitter(this, trim, reverse, separator);
    }

    public StringSplitter setReverse(boolean reverse) {
        return new StringSplitter(this, trim, reverse, separator);
    }

    public StringSplitter setSeparator(Predicate<Character> separator) {
        return new StringSplitter(this, trim, reverse, separator);
    }

    /**
     * Parses a word according to the StringSlice defined rules (i.e., trim, reverse and separator).
     * <p>
     * If no separator is found, the result contains the remaining string.
     * 
     * @return
     */
    public NextResult next() {
        int internalSeparatorIndex = indexOfInternal(separator, reverse);

        NextResult result = reverse ? nextReverse(internalSeparatorIndex) : nextRegular(internalSeparatorIndex);

        if (trim) {
            return new NextResult(result.getModifiedSlice().trim(), result.getWord().trim());
        }

        return result;
    }

    private NextResult nextRegular(int internalSeparatorIndex) {

        if (internalSeparatorIndex == -1) {
            internalSeparatorIndex = endIndex;
        }

        String word = internal.substring(startIndex, internalSeparatorIndex);

        // // Trim word
        // if (trim) {
        // word = word.trim();
        // }

        int internalSliceStartIndex = internalSeparatorIndex + 1;

        // If bigger than endIndex, return empty StringSlice
        if (internalSliceStartIndex > endIndex) {
            return new NextResult(substring(length()), word);
        }

        StringSlice modifiedSlice = new StringSplitter(new StringSlice(internal, internalSliceStartIndex, endIndex),
                trim, reverse,
                separator);

        // // Trim modified slice
        // if (trim) {
        // modifiedSlice = modifiedSlice.trim();
        // }

        return new NextResult(modifiedSlice, word);

    }

    private NextResult nextReverse(int internalSeparatorIndex) {
        if (internalSeparatorIndex == -1) {
            internalSeparatorIndex = startIndex - 1;
        }

        String word = internal.substring(internalSeparatorIndex + 1, endIndex);

        int internalSliceEndIndex = internalSeparatorIndex;

        if (internalSliceEndIndex < startIndex) {
            StringSlice modifiedSlice = new StringSplitter(new StringSlice("", 0, 0), trim, reverse, separator);
            return new NextResult(modifiedSlice, word);
        }

        StringSlice modifiedSlice = new StringSplitter(new StringSlice(internal, startIndex, internalSliceEndIndex),
                trim, reverse,
                separator);

        return new NextResult(modifiedSlice, word);
    }

    /**
     * 
     * @param target
     * @param reverse
     * @return an index relative to the internal String
     */
    private int indexOfInternal(Predicate<Character> target, boolean reverse) {
        // Using internals
        // Test reverse order
        if (reverse) {
            for (int i = endIndex - 1; i >= startIndex; i--) {
                if (target.test(internal.charAt(i))) {
                    return i;
                }
            }
        }
        // Test original order
        else {
            for (int i = startIndex; i < endIndex; i++) {
                if (target.test(internal.charAt(i))) {
                    return i;
                }
            }
        }

        // Using class methods
        // // Test reverse order
        // if (reverse) {
        // for (int i = length() - 1; i >= 0; i--) {
        // if (target.test(charAtUnchecked(i))) {
        // return i;
        // }
        // }
        // }
        // // Test original order
        // else {
        // for (int i = 0; i < length(); i++) {
        // if (target.test(charAtUnchecked(i))) {
        // return i;
        // }
        // }
        // }

        return -1;
    }

    public StringSplitter set(StringSlice modifiedString) {
        return new StringSplitter(modifiedString, trim, reverse, separator);
    }

    @Override
    public StringSplitter trim() {
        return set(super.trim());
    }

    @Override
    public StringSplitter substring(int start) {
        return set(super.substring(start));
    }

    @Override
    public StringSplitter clear() {
        return set(super.clear());
    }

}