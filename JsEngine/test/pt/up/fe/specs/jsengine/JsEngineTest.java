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

package pt.up.fe.specs.jsengine;

import static org.junit.Assert.*;

import javax.script.Bindings;
import javax.script.ScriptContext;
import javax.script.ScriptException;

import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.HostAccess;
import org.graalvm.polyglot.PolyglotAccess;
import org.graalvm.polyglot.Value;
import org.junit.Test;

import com.oracle.truffle.js.scriptengine.GraalJSScriptEngine;

import pt.up.fe.specs.jsengine.JsEngine;
import pt.up.fe.specs.jsengine.JsEngineType;
import pt.up.fe.specs.util.SpecsLogs;
import pt.up.fe.specs.util.lazy.Lazy;

public class JsEngineTest {

    private static final Lazy<JsEngine> GRAAL_JS = Lazy.newInstance(() -> JsEngineType.GRAALVM.newEngine());
    // private static final Lazy<JsEngine> NASHORN = Lazy.newInstance(() -> JsEngineType.NASHORN.newEngine());

    // @Test
    public void testModifyThis() {
        // Context jsContext = Context.create("js");
        // var value = jsContext.eval("js", "console.log('Hello from the project');"
        // + "Java.type('org.lara.interpreter.test.JsEngineTest');");

        Context.Builder contextBuilder = Context.newBuilder("js")
                .allowAllAccess(true)
                .allowHostAccess(HostAccess.ALL)
                .allowIO(true)
                .allowCreateThread(true)
                .allowNativeAccess(true)
                .allowPolyglotAccess(PolyglotAccess.ALL);
        var engine = GraalJSScriptEngine.create(null, contextBuilder);

        String script;

        // script = "console.log('Hello from the project');"
        // + "Java.type('org.lara.interpreter.test.JsEngineTest');";

        script = "var Accumulator = function() {\n" +
                "    this.value = 0;\n" +
                "};"
                + "Accumulator.prototype.add = function() {\n"
                + "this.value++;"
                + "console.log(this.value);\n"
                + "Java.type('org.lara.interpreter.test.JsEngineTest').testValue(this);"
                + "return this.value;"
                + "}\n"

                + "var acc = new Accumulator();"
                + "acc.add();"
                + "let user = {\r\n" +
                "  get name() {\r\n" +
                "    return this._name;\r\n" +
                "  },\r\n" +
                "\r\n" +
                "  set name(value) {\r\n" +
                "    if (value.length < 4) {\r\n" +
                "      console.log(\"Name is too short, need at least 4 characters\");\r\n" +
                "      return;\r\n" +
                "    }\r\n" +
                "    this._name = value;\r\n" +
                "  }\r\n" +
                "};\r\n" +
                "\r\n" +
                "user.name = \"Pete\";\r\n" +
                "console.log(user.name); // Pete\r\n" +
                "\r\n" +
                "user.name = \"\"; // Name is too short...";

        try {

            var value = engine.eval(script);
            System.out.println("VALUE: " + value);
            Bindings b = engine.getBindings(ScriptContext.ENGINE_SCOPE);
            b.put("acc2", 10);
            System.out.println("BINDINGS: " + b);

        } catch (ScriptException e) {
            SpecsLogs.msgWarn("Error message:\n", e);
        }
    }

    @Test
    public void testUndefined() {
        assertEquals("undefined", GRAAL_JS.get().getUndefined().toString());
    }

    @Test
    public void testStringify() {
        JsEngine engine = GRAAL_JS.get();
        // System.out.println("RESULT: " + engine.stringify(engine.eval("var test = {'a': 'aString', 'b':10};")));
        assertEquals("{\"a\":\"aString\",\"b\":10}",
                engine.stringify(engine.eval("var test = {a: 'aString', b: 10}; test;")));
    }

    @Test
    public void testEvalWithBindings() {
        JsEngine engine1 = GRAAL_JS.get();

        Object scope = engine1.eval("var a = {aString: 'Hello', aNumber: 10}; a;");
        assertEquals("Hello", engine1.eval("var b = this.aString; b;", scope).toString());
        assertEquals("undefined", engine1.eval("var b = this.aString; b;").toString());
    }

    public static void test(Bindings bindings) {
        System.out.println("INSIDE JAVA: " + bindings);
    }

    public static void testValue(Value value) {

        System.out.println("INSIDE JAVA (GRAAL VALUE): " + value);
        value.putMember("aString", "Hello");
        System.out.println("After adding: " + value);
    }

}
