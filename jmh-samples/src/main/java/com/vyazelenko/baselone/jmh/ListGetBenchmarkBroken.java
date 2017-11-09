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

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ListGetBenchmarkBroken {
    private static final int ITERATIONS = 1_000_000_000;

    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        long startTime = System.nanoTime();
        for (int i = 0; i < ITERATIONS; i++) {
            list.get(3);
        }
        long endTime = System.nanoTime();
        long duration = endTime - startTime;
        System.out.println("Executed in "
                + TimeUnit.NANOSECONDS.toMillis(duration) + "ms, " +
                +((double) duration / ITERATIONS) + " ns/op");
    }
}
