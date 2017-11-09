/*
 * MIT License
 *
 * Copyright (c) 2017 Dmitry Vyazelenko
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.vyazelenko.baselone.jmh.reflection;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;

import java.lang.reflect.Field;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@State(Scope.Benchmark)
public class GetFields {
    private Class<?> klass;
    private ConcurrentMap<Class<?>, Field[]> fieldsCache;

    @Setup
    public void setUp() {
        klass = C.class;
        fieldsCache = new ConcurrentHashMap<>();
    }

    @Benchmark
    public Field[] getDeclaredFields() {
        return klass.getDeclaredFields();
    }

    @Benchmark
    public Field[] getDeclaredFields_cached() {
        Field[] result = fieldsCache.get(klass);
        if (result == null) {
            result = klass.getDeclaredFields();
            Field[] tmp = fieldsCache.putIfAbsent(klass, result);
            if (tmp != null) {
                return tmp;
            }
        }
        return result;
    }
}
