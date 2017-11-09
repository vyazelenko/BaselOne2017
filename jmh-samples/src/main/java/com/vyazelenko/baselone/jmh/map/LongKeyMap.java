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
package com.vyazelenko.baselone.jmh.map;

import org.jctools.maps.NonBlockingHashMapLong;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@State(Scope.Benchmark)
public class LongKeyMap {
    private ConcurrentHashMap<Long, Double> chm;
    private NonBlockingHashMapLong<Double> nbhm;
    private long key;

    @Setup
    public void setUp() {
        chm = new ConcurrentHashMap<>();
        nbhm = new NonBlockingHashMapLong<>();
        long[] data = generate(50_000);
        populate(chm, data);
        populate(nbhm, data);
        key = 6528321900546851391L;
    }

    private void populate(Map<Long, Double> map, long[] data) {
        for (long key : data) {
            map.put(key, Double.longBitsToDouble(key));
        }
        if (map.size() != data.length) {
            throw new AssertionError();
        }
    }

    private long[] generate(int size) {
        Random r = new Random(-7890);
        long[] result = new long[size];
        for (int i = 0; i < size; i++) {
            result[i] = Math.abs(r.nextLong());
        }
        return result;
    }

    @Benchmark
    public Double get_ConcurrentHashMap() {
        return chm.get(key);
    }

    @Benchmark
    public Double get_NonBlockingHashMapLong() {
        return nbhm.get(key);
    }
}
