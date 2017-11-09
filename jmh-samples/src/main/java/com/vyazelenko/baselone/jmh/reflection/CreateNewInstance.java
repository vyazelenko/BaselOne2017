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

import java.lang.reflect.Constructor;
import java.util.Date;

@State(Scope.Benchmark)
public class CreateNewInstance {
    private Class<?> klass;
    private String className;
    private Constructor<?> contructor;
    private Date arg1;
    private String arg2;

    @Setup
    public void setUp() throws Exception {
        klass = C.class;
        className = klass.getName();
        contructor = klass.getConstructor(Date.class, String.class);
        arg1 = new Date();
        arg2 = arg1.toString() + " something else";
    }

    @Benchmark
    public Object load_class_find_constructor_allocate() throws Exception {
        Class<?> clazz = Class.forName(className);
        Constructor<?> ctr = clazz.getConstructor(Date.class, String.class);
        return ctr.newInstance(arg1, arg2);
    }

    @Benchmark
    public Object find_constructor_allocate() throws Exception {
        Constructor<?> ctr = klass.getConstructor(Date.class, String.class);
        return ctr.newInstance(arg1, arg2);
    }


    @Benchmark
    public Object conctructor_allocate() throws Exception {
        return contructor.newInstance(arg1, arg2);
    }

    @Benchmark
    public Object new_allocate() throws Exception {
        return new C(arg1, arg2);
    }
}
