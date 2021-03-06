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
package com.vyazelenko.baselone.jmh;

import org.openjdk.jmh.annotations.*;

import java.util.ArrayList;
import java.util.Collection;

@State(Scope.Benchmark)
public class CollectionToList {
    @Param({"ArrayList", "HashSet"})
    private String collection;

    private Collection<Object> data;

    @Setup
    public void setUp() throws Exception {
        data = (Collection<Object>) Class.forName("java.util." + collection).newInstance();
        for (int i = 0; i < 10_000; i++) {
            data.add(new Object());
        }
    }

    @Benchmark
    public ArrayList<Object> new_ArrayList_from_Collection() {
        return new ArrayList<>(data);
    }

    @Benchmark
    public ArrayList<Object> new_ArrayList_addAll() {
        ArrayList<Object> result = new ArrayList<>(data.size());
        result.addAll(data);
        return result;
    }

    @Benchmark
    public ArrayList<Object> new_ArrayList_add() {
        ArrayList<Object> result = new ArrayList<>(data.size());
        for (Object o : data) {
            result.add(o);
        }
        return result;
    }
}
