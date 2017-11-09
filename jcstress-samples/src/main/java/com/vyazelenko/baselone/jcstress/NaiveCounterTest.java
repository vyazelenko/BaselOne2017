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
package com.vyazelenko.baselone.jcstress;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NaiveCounterTest {
    private static ExecutorService executor = Executors.newFixedThreadPool(2);

    private static void test(Counter counter) throws InterruptedException {
        System.out.println(counter.getClass().getSimpleName());
        CountDownLatch startGate = new CountDownLatch(3);
        CountDownLatch endGate = new CountDownLatch(2);
        for (int i = 0; i < 2; i++) {
            executor.submit(() -> {
                startGate.countDown();
                startGate.await();
                System.out.println(counter.increment());
                endGate.countDown();
                return null;
            });
        }
        startGate.countDown();
        endGate.await();
    }

    public static void main(String[] args) throws InterruptedException {
        test(new BrokenCounter());
        test(new AtomicCounter());
    }
}
