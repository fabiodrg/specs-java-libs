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

package pt.up.fe.specs.util.classmap;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;

import pt.up.fe.specs.util.SpecsCheck;
import pt.up.fe.specs.util.exceptions.NotImplementedException;
import pt.up.fe.specs.util.utilities.ClassMapper;

/**
 * Maps a class T or subtype of T to a value V.
 * 
 * <p>
 * Use this class if you want to:<br>
 * 1) Use classes as keys and want the map to respect the hierarchy (e.g., a value mapped to class Number will be
 * returned if the key is the class Integer and there is no explicit mapping for the class Integer).<br>
 * 
 * @author JoaoBispo
 *
 * @param <T>
 * @param <V>
 */
public class ClassMap<T, V> {

    private final Map<Class<? extends T>, V> map;
    // private final boolean supportInterfaces;
    // Can be null
    private final V defaultValue;

    private final ClassMapper classMapper;

    public ClassMap() {
        this(new HashMap<>(), null, new ClassMapper());
    }

    public <ER extends V> ClassMap(ER defaultValue) {
        this(new HashMap<>(), defaultValue, new ClassMapper());
    }

    private ClassMap(Map<Class<? extends T>, V> map, V defaultValue,
            ClassMapper classMapper) {

        this.map = map;
        // this.supportInterfaces = supportInterfaces;
        this.defaultValue = defaultValue;
        this.classMapper = classMapper;
    }

    public ClassMap<T, V> copy() {
        return new ClassMap<>(new HashMap<>(this.map), this.defaultValue, this.classMapper);
    }

    /**
     * Associates the specified value with the specified key.
     * 
     * <p>
     * The key is always a class of a type that is a subtype of the type in the value.
     * <p>
     * Example: <br>
     * - put(Subclass.class, usesSuperClass), ok<br>
     * - put(Subclass.class, usesSubClass), ok<br>
     * - put(Superclass.class, usesSubClass), error<br>
     * 
     * @param aClass
     * @param value
     */
    public <ET extends T, K extends ET> V put(Class<K> aClass,
            V value) {

        // if (!this.supportInterfaces) {
        // if (aClass.isInterface()) {
        // SpecsLogs.warn("Support for interfaces is disabled, map is unchanged");
        // return null;
        // }
        // }

        classMapper.add(aClass);
        return this.map.put(aClass, value);
    }

    /**
     * 
     * @param key
     * @return the class that will be used to access the map, based on the given key
     */
    /*
    public <TK extends T> Optional<Class<?>> getEquivalentKey(Class<TK> key) {
        Class<?> currentKey = key;
    
        while (currentKey != null) {
            // Test key
            V result = this.map.get(currentKey);
            if (result != null) {
                return Optional.of(currentKey);
            }
    
            if (this.supportInterfaces) {
                for (Class<?> interf : currentKey.getInterfaces()) {
                    result = this.map.get(interf);
                    if (result != null) {
                        return Optional.of(interf);
                    }
                }
            }
    
            currentKey = currentKey.getSuperclass();
        }
    
        return Optional.empty();
    }
    */

    public <TK extends T> Optional<V> tryGet(Class<TK> key) {
        // Map given class to a class supported by this instance
        var mappedClass = classMapper.map(key);

        if (mappedClass.isPresent()) {
            var result = this.map.get(mappedClass.get());
            SpecsCheck.checkNotNull(result, () -> "Expected map to contain " + mappedClass.get());
            return Optional.of(result);
        }

        /*
        Class<?> currentKey = key;
        
        while (currentKey != null) {
            // Test key
            V result = this.map.get(currentKey);
            if (result != null) {
                return Optional.of(result);
            }
        
            if (this.supportInterfaces) {
                // System.out.println("INTERFACES OF " + currentKey + ": " +
                // Arrays.toString(currentKey.getInterfaces()));
                for (Class<?> interf : currentKey.getInterfaces()) {
                    result = this.map.get(interf);
                    if (result != null) {
                        return Optional.of(result);
                    }
                }
            }
        
            currentKey = currentKey.getSuperclass();
        }
        */
        // Return default value if present
        if (this.defaultValue != null) {
            return Optional.of(this.defaultValue);
        }

        return Optional.empty();

    }

    public <TK extends T> V get(Class<TK> key) {
        Optional<V> result = tryGet(key);

        // Found value, return it
        if (result.isPresent()) {
            return result.get();
        }

        throw new NotImplementedException("Function not defined for class '"
                + key + "'");
    }

    @SuppressWarnings("unchecked")
    public <TK extends T> V get(TK key) {
        return get((Class<TK>) key.getClass());
    }

    /**
     * Sets the default value, backed up by the same map.
     * 
     * @param defaultValue
     * @return
     */
    public ClassMap<T, V> setDefaultValue(V defaultValue) {
        return new ClassMap<>(this.map, defaultValue, this.classMapper);
    }

    public Set<Entry<Class<? extends T>, V>> entrySet() {
        return map.entrySet();
    }

    public Set<Class<? extends T>> keySet() {
        return map.keySet();
    }

}
