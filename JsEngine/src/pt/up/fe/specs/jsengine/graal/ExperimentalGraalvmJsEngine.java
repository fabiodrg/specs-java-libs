/**
 * Copyright 2019 SPeCS.
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

package pt.up.fe.specs.jsengine.graal;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import javax.script.Bindings;

import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.HostAccess;
import org.graalvm.polyglot.PolyglotException;
import org.graalvm.polyglot.Value;

import com.oracle.truffle.js.scriptengine.GraalJSScriptEngine;

import pt.up.fe.specs.jsengine.ForOfType;
import pt.up.fe.specs.jsengine.JsEngine;
import pt.up.fe.specs.jsengine.JsEngineResource;
import pt.up.fe.specs.util.SpecsLogs;
import pt.up.fe.specs.util.exceptions.NotImplementedException;

import org.graalvm.polyglot.Source;

import java.io.File;
import java.net.URL;
import java.nio.file.Paths;
import java.io.IOException;
import java.net.URISyntaxException;


public class ExperimentalGraalvmJsEngine implements JsEngine {

    private static final String NEW_ARRAY = "[]"; // Faster
    private static final String NEW_MAP = "a = {}; a";

    private final Context context;
    private int evalCounter = 0;

    private final Set<String> forbiddenClasses;

    public ExperimentalGraalvmJsEngine() {
        this(Collections.emptyList());
    }
    
    public ExperimentalGraalvmJsEngine(Collection<Class<?>> blacklistedClasses) {

        this.forbiddenClasses = blacklistedClasses.stream().map(Class::getName).collect(Collectors.toSet());

        context = Context.newBuilder("js")
        		.allowAllAccess(true)
        		.allowHostClassLookup(name -> !forbiddenClasses.contains(name))
        		.build();

        // Load Java compatibility layer
        eval(JsEngineResource.JAVA_COMPATIBILITY.read());

        try {
        //try (final Context jsContext = Context.newBuilder("js").allowAllAccess(true).fileSystem().build()) {
            //final URL resource = this.getClass().getClassLoader().getResource("file1.js");
            //final File file = Paths.get(resource.toURI()).toFile();
        	final File file = Paths.get("/home/lmsousa/Documents/Clava-Cenas/build/file1.js").toFile();
            
            final Source source = Source.newBuilder("js", file).mimeType("application/javascript+module").build();
            //final String src = "import {a} from \"/home/lmsousa/Documents/Clava-Cenas/build/file2.js\"; a();";
            //final Source source = Source.newBuilder("js", src, "tester.js").mimeType("application/javascript+module").build();
            final Value value = context.eval(source);

            System.out.println(value);
        } catch (final IOException e) {
            e.printStackTrace();
        } catch (final PolyglotException e) {
        	e.printStackTrace();
        }// catch (final URISyntaxException e) {
        //    e.printStackTrace();
        //}

    }


    @Override
    public Value eval(String code) {
    	evalCounter++;
    	System.out.println("----- " + String.valueOf(evalCounter) + ".lara" + " -----");
    		
        try {
        	Source source;
        	if (evalCounter == 69) {
        		final File file = Paths.get("/home/lmsousa/Documents/Clava-Cenas/build/weaved-lara.js").toFile();
        		source = Source.newBuilder("js", file).mimeType("application/javascript+module").build();
        	}
        	else {
        		source = Source.newBuilder("js", code, String.valueOf(evalCounter) + ".lara").mimeType("application/javascript+module").build();
        	}
            Value value = context.eval(source);

            return value;
        } catch (final IOException e) {
            e.printStackTrace();
        } catch (final PolyglotException e) {
        	e.printStackTrace();
        }
		return null;
    }

    @Override
    public ForOfType getForOfType() {
        return ForOfType.NATIVE;
    }

    @Override
    public Object newNativeArray() {
        return eval(NEW_ARRAY);
    }

    @Override
    public Object newNativeMap() {
        return eval(NEW_MAP);
    }

    @Override
    public Value toNativeArray(Object[] values) {
        Value array = eval(NEW_ARRAY);
        for (int i = 0; i < values.length; i++) {
            array.setArrayElement(i, values[i]);
        }

        return array;
    }

    @Deprecated
    public Object toNativeArrayV2(Object[] values) {
        return toNativeArray(values);
    }

    /**
     * Based on this site: http://programmaticallyspeaking.com/nashorns-jsobject-in-context.html
     *
     * @return
     */
    @Override
    public Object getUndefined() {
        var array = context.eval("js", "[undefined]");

        return array.getArrayElement(0);
    }

    @Override
    public String stringify(Object object) {
        Value json = eval("JSON");

        return json.invokeMember("stringify", object).toString();
        // return json.invokeMember("stringify", asValue(object).asProxyObject()).toString();
    }

    @Override
    public Value getBindings() {
    	final String className = getClass().getName();
    	final String methodName = new Object () {} .getClass().getEnclosingMethod().getName();
    	throw new RuntimeException("Do not use '" + methodName + "()' from " + className);
    }

    /**
     * Adds the members in the given scope before evaluating the code.
     */
    @Override
    public Object eval(String code, Object scope) {
    	final String className = getClass().getName();
    	final String methodName = new Object () {} .getClass().getEnclosingMethod().getName();
    	throw new RuntimeException("Do not use '" + methodName + "()' from " + className);
    }

    public Value asValue(Object object) {
    	final String className = getClass().getName();
    	final String methodName = new Object () {} .getClass().getEnclosingMethod().getName();
    	throw new RuntimeException("Do not use '" + methodName + "()' from " + className);
    }

    @Override
    public boolean asBoolean(Object value) {
    	final String className = getClass().getName();
    	final String methodName = new Object () {} .getClass().getEnclosingMethod().getName();
    	throw new RuntimeException("Do not use '" + methodName + "()' from " + className);
    }

    @Override
    public void nashornWarning(String message) {
    	final String className = getClass().getName();
    	final String methodName = new Object () {} .getClass().getEnclosingMethod().getName();
    	throw new RuntimeException("Do not use '" + methodName + "()' from " + className);
    }

    @Override
    public boolean isArray(Object object) {
    	final String className = getClass().getName();
    	final String methodName = new Object () {} .getClass().getEnclosingMethod().getName();
    	throw new RuntimeException("Do not use '" + methodName + "()' from " + className);
    }

    @Override
    public boolean isUndefined(Object object) {
    	final String className = getClass().getName();
    	final String methodName = new Object () {} .getClass().getEnclosingMethod().getName();
    	throw new RuntimeException("Do not use '" + methodName + "()' from " + className);
    }

    @Override
    public Collection<Object> getValues(Object object) {
    	final String className = getClass().getName();
    	final String methodName = new Object () {} .getClass().getEnclosingMethod().getName();
    	throw new RuntimeException("Do not use '" + methodName + "()' from " + className);
    }

    @Override
    public <T> T convert(Object object, Class<T> targetClass) {
    	final String className = getClass().getName();
    	final String methodName = new Object () {} .getClass().getEnclosingMethod().getName();
    	throw new RuntimeException("Do not use '" + methodName + "()' from " + className);
    }

    /// Bindings-like operations

    @Override
    public Object put(Object bindings, String key, Object value) {
    	final String className = getClass().getName();
    	final String methodName = new Object () {} .getClass().getEnclosingMethod().getName();
    	throw new RuntimeException("Do not use '" + methodName + "()' from " + className);
    }

    @Override
    public Object remove(Object bindings, String key) {
    	final String className = getClass().getName();
    	final String methodName = new Object () {} .getClass().getEnclosingMethod().getName();
    	throw new RuntimeException("Do not use '" + methodName + "()' from " + className);
    }

    @Override
    public Set<String> keySet(Object bindings) {
    	final String className = getClass().getName();
    	final String methodName = new Object () {} .getClass().getEnclosingMethod().getName();
    	throw new RuntimeException("Do not use '" + methodName + "()' from " + className);
    }

    @Override
    public Object get(Object bindings, String key) {
    	final String className = getClass().getName();
    	final String methodName = new Object () {} .getClass().getEnclosingMethod().getName();
    	throw new RuntimeException("Do not use '" + methodName + "()' from " + className);
    }

    /// Engine related engine-scope operations

    @Override
    public void put(String key, Object value) {
    	context.getBindings("js").putMember(key, value);
    }

    @Override
    public boolean supportsProperties() {
    	final String className = getClass().getName();
    	final String methodName = new Object () {} .getClass().getEnclosingMethod().getName();
    	throw new RuntimeException("Do not use '" + methodName + "()' from " + className);
    }

}
