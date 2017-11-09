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

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

@State(Scope.Benchmark)
public class MapGet {
    private String key;
    private Map<String, Integer> treeMap;
    private Map<String, Integer> hashMap;
    private Map<String, Integer> ignoreCaseMap;

    @Setup
    public void setUp() {
        key = "GoodKey";
        treeMap = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        hashMap = new HashMap<>();
        ignoreCaseMap = new HashMap<>();
        String[] data = getData();
        for (String d : data) {
            treeMap.put(d, d.length());
            hashMap.put(d.toLowerCase(), d.length());
            ignoreCaseMap.put(d, d.length());
        }
    }

    private String[] getData() {
        return ("Microblogging and social networking sites that limit the number of characters in a message " +
                "(most famously Twitter, where the 140-character limit can be quite restrictive in languages that rely " +
                "on alphabets, including English) are potential outlets for medial capitals. Using CamelCase between " +
                "words reduces the number of spaces, GoodKey and thus the number of characters, in a given message, allowing " +
                "more content to fit into the limited space. Hashtags, especially long ones, often use CamelCase to " +
                "maintain readability (e.g. #CollegeStudentProblems is easier to read than #collegestudentproblems )."
                + "A collection of Concurrent and Highly Scalable Utilities. These are intended as direct replacements " +
                "for the java.util.* or java.util.concurrent.* collections but with better performance when many CPUs " +
                "are using the collection concurrently. baD_kEy")
                .split("\\s+");
    }

    @Benchmark
    public Integer HashMap_toLowerCase() {
        return hashMap.get(key.toLowerCase());
    }

    @Benchmark
    public Integer TreeMap() {
        return treeMap.get(key);
    }

    @Benchmark
    public Integer IgnoreCaseMap() {
        Integer val = ignoreCaseMap.get(key);
        if (val == null) {
            val = ignoreCaseMap.get(key.toLowerCase());
            if (val != null) {
                ignoreCaseMap.put(key, val);
            }
        }
        return val;
    }

}
