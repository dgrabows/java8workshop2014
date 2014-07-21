/*
 * Copyright 2014 Vertafore, Inc. All rights reserved.
 *
 * Disclaimers:
 * This software is provided "as is," without warranty of any kind, express
 * or implied, including but not limited to the warranties of merchantability,
 * fitness for a particular purpose and non-infringement. This source code
 * should not be relied upon as the sole basis for solving a problem whose
 * incorrect solution could result in injury to person or property. In no
 * event shall the author or contributors be held liable for any damages
 * arising in any way from the use of this software. The entire risk as to
 * the results and performance of this source code is assumed by the user.
 *
 * Permission is granted to use this software for internal use only, subject
 * to the following restrictions:
 * 1. This source code MUST retain the above copyright notice, disclaimer,
 * and this list of conditions.
 * 2. This source code may be used ONLY within the scope of the original
 * agreement under which this source code was provided and may not be
 * distributed to any third party without the express written consent of
 * Vertafore.
 * 3. This source code along with all obligations and rights under the
 * original License Agreement may not be assigned to any third party
 * without the expressed written consent of Vertafore, except that 
 * assignment may be made to a successor to the business or 
 * substantially all of its assets. All parties bind their successors,
 * executors, administrators, and assignees to all covenants of this
 * Agreement.
 *
 * All advertising materials mentioning features or use of this software
 * must display the following acknowledgment:
 * Trademark Disclaimer:
 * All patent, copyright, trademark and other intellectual property
 * rights included in the source code are owned exclusively by Vertafore,
 * Inc.
 */
package com.vertafore.jakerobb.java8workshop2014.java7.forkjoinpool;

import com.google.common.collect.ObjectArrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.RecursiveTask;
import java.util.concurrent.ThreadLocalRandom;

public class RandomDataGenerator extends RecursiveTask<Integer[]> {
    private static final Logger LOG = LoggerFactory.getLogger(RandomDataGenerator.class);

    private final int numRequested;
    private final int sequentialThreshold;

    public RandomDataGenerator(int numRequested, int sequentialThreshold) {
        this.numRequested = numRequested;
        this.sequentialThreshold = sequentialThreshold;
    }

    @Override
    protected Integer[] compute() {
        if (numRequested <= sequentialThreshold) {
            return computeDirectly();
        }
        final int split = numRequested / 2;
        final RandomDataGenerator left = new RandomDataGenerator(split, sequentialThreshold);
        left.fork();
        final RandomDataGenerator right = new RandomDataGenerator(numRequested - split, sequentialThreshold);
        return ObjectArrays.concat(right.compute(), left.join(), Integer.class);
    }

    private Integer[] computeDirectly() {
        LOG.info(String.format("%s generating %d randoms", Thread.currentThread(), numRequested));
        Integer[] data = new Integer[numRequested];
        for (int i = 0; i < numRequested; i++) {
            data[i] = ThreadLocalRandom.current().nextInt();
        }
        return data;
    }
}
