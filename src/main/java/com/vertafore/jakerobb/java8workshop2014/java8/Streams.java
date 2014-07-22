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
package com.vertafore.jakerobb.java8workshop2014.java8;

import com.google.common.collect.ImmutableList;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.IntSupplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author jrobb
 * @version $Id$
 */
public class Streams {

    public void fromList() {
        List<Integer> myList = ImmutableList.of(1,2,3,4,5,6,7,8,9,10);
        List<Integer> myFilteredList = myList.stream().filter(input -> input < 5).collect(Collectors.toList());
    }


    public void intStream() {
        int[] filteredArray = IntStream.range(1, 10).filter(input -> input < 5).toArray();
        Set<Integer> myFilteredSet = IntStream.range(1,10).filter(input -> input < 5).boxed().collect(Collectors.toSet());
        Set<Integer> myFilteredSet2 = IntStream.range(1,10).boxed().filter(input -> input < 5).collect(Collectors.toSet());

        IntStream myRandomStream = IntStream.generate(new RandomIntSupplier());
        myRandomStream.filter(input -> input < 5).forEach(System.out::println);

        // Spliterators

    }

    private static class RandomIntSupplier implements IntSupplier {
        /**
         * Gets a result.
         *
         * @return a result
         */
        @Override
        public int getAsInt() {
            return ThreadLocalRandom.current().nextInt();
        }
    }

}
