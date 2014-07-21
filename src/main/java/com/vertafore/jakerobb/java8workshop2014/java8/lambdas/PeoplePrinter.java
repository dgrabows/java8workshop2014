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
package com.vertafore.jakerobb.java8workshop2014.java8.lambdas;

import java.util.Collection;
import java.util.function.Consumer;
import java.util.function.Predicate;

import static com.vertafore.jakerobb.java8workshop2014.java8.lambdas.Person.Sex.MALE;

/**
 * @author jrobb
 * @version $Id$
 */
public class PeoplePrinter {
    public static void olderThan(Iterable<Person> roster, int age) {
        for (Person p : roster) {
            if (p.getAge() >= age) {
                p.printPerson();
            }
        }
    }

    public static void withinAgeRange(Iterable<Person> roster, int low, int high) {
        for (Person p : roster) {
            if (low <= p.getAge() && p.getAge() < high) {
                p.printPerson();
            }
        }
    }

    public static void printPeople(Iterable<Person> roster, CheckPerson tester) {
        for (Person p : roster) {
            if (tester.test(p)) {
                p.printPerson();
            }
        }
    }

    private static class CheckPersonEligibleForSelectiveService implements CheckPerson {
        @Override
        public boolean test(Person p) {
            return p.getGender() == MALE &&
                   p.getAge() >= 18 &&
                   p.getAge() <= 25;
        }
    }

    public static void eligibleForSelectiveService(Iterable<Person> roster) {
        printPeople(roster, new CheckPersonEligibleForSelectiveService());
    }

    // finally, a lambda!
    public static void eligibleForSelectiveService2(Iterable<Person> roster) {
        printPeople(roster, (Person p) -> p.getGender() == MALE
                                          && p.getAge() >= 18
                                          && p.getAge() <= 25
        );
    }

    public static void eligibleForSelectiveService3(Iterable<Person> roster) {
        printPeople(roster, p -> p.getGender() == MALE
                                 && p.getAge() >= 18
                                 && p.getAge() <= 25
        );
    }

    public static <T> void print(Iterable<T> roster, Predicate<T> tester) {
        for (T t : roster) {
            if (tester.test(t)) {
                System.out.println(t.toString());
            }
        }
    }

    public static void eligibleForSelectiveService4(Iterable<Person> roster) {
        print(roster, p -> p.getGender() == MALE
                           && p.getAge() >= 18
                           && p.getAge() <= 25
        );
    }

    public static void processPersons(Iterable<Person> roster, Predicate<Person> tester, Consumer<Person> block) {
        for (Person p : roster) {
            if (tester.test(p)) {
                block.accept(p);
            }
        }
    }

    public static void eligibleForSelectiveService5(Iterable<Person> roster) {
        processPersons(roster,
                       p -> p.getGender() == Person.Sex.MALE
                            && p.getAge() >= 18
                            && p.getAge() <= 25,
                       p -> p.printPerson()
        );
    }

    public static void eligibleForSelectiveService6(Iterable<Person> roster) {
        processPersons(roster,
                       p -> p.getGender() == Person.Sex.MALE
                            && p.getAge() >= 18
                            && p.getAge() <= 25,
                       Person::printPerson
        );
    }

    public static void eligibleForSelectiveService7(Collection<Person> roster) {
        roster.stream().filter(p -> p.getGender() == Person.Sex.MALE
                                    && p.getAge() >= 18
                                    && p.getAge() <= 25)
                       .forEach(Person::printPerson);
    }

    public static void eligibleForSelectiveService8(Collection<Person> roster) {
        roster.parallelStream().filter(p -> p.getGender() == Person.Sex.MALE
                                            && p.getAge() >= 18
                                            && p.getAge() <= 25)
                               .forEach(Person::printPerson);
    }

}
