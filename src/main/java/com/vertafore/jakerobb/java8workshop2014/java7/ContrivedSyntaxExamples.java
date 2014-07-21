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
package com.vertafore.jakerobb.java8workshop2014.java7;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.io.BufferedReader;
import java.io.EOFException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class ContrivedSyntaxExamples {
    private static final Logger LOG = LoggerFactory.getLogger(ContrivedSyntaxExamples.class);

    private final List<String> diamondInitializer;
    private final int binaryLiteral;
    private final int underscoresInLiteral;

    public ContrivedSyntaxExamples() {
        diamondInitializer = new ArrayList<>();
        binaryLiteral = 0b11111111;
        underscoresInLiteral = 1_000_000_000;
    }

    public void diamondInMethodCalls() {
        diamondInitializer.addAll(new ArrayList<>());
    }

    public static boolean switchOnStrings(String input) {
        // contrived example
        switch (input) {
            case "A":
            case "B":
            case "C":
                return true;
            default:
                return false;
        }
    }

    public static void tryWithResources(DataSource datasource) {
        try (Connection connection = datasource.getConnection()) { // Connection implements AutoCloseable
            try (PreparedStatement statement = connection.prepareStatement("SELECT 1 FROM DUAL")) {
                statement.execute();
            } catch (SQLException e) {
                LOG.error("Something bad happened while querying.", e);
            }
        } catch (SQLException e) {
            LOG.error("Something bad happened while connecting.", e);
        }
        // no finally clauses (with annoying null-checks) needed!
    }

    public static CharSequence betterExceptionHandling() throws EOFException, FileNotFoundException {
        File file = new File("~/path/to/file");
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            StringBuilder fileContents = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                fileContents.append(line);
            }
            return fileContents;
        } catch (EOFException | FileNotFoundException e) { // two disparate types at a time!
            LOG.error("Something bad happened.", e);
            throw e;
        } catch (IOException e) {
            LOG.error("Something else bad happened, but we're going to eat it this time.", e);
            return new StringBuilder();
        }
    }

    public static int getRandom() {
        // to eliminate thread contention
        return ThreadLocalRandom.current().nextInt();
    }

    public List<String> getDiamondInitializer() {
        return diamondInitializer;
    }

    public int getBinaryLiteral() {
        return binaryLiteral;
    }

    public int getUnderscoresInLiteral() {
        return underscoresInLiteral;
    }
}
