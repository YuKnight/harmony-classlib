/*
 *  Copyright 2005 The Apache Software Foundation or its licensors, as applicable.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

/**
* @author Vera Y. Petrashkova
* @version $Revision$
*/

package java.security.spec;

import java.security.spec.InvalidKeySpecException;

import org.apache.harmony.security.test.PerformanceTest;


/**
 * Tests for <code>InvalidKeySpecException</code> class constructors and
 * methods.
 * 
 */
public class InvalidKeySpecExceptionTest extends PerformanceTest {

    public static void main(String[] args) {
    }

    /*
     * @see TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();
    }

    /*
     * @see TestCase#tearDown()
     */
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Constructor for InvalidKeySpecExceptionTests.
     * 
     * @param arg0
     */
    public InvalidKeySpecExceptionTest(String arg0) {
        super(arg0);
    }

    private static String[] msgs = {
            "",
            "Check new message",
            "Check new message Check new message Check new message Check new message Check new message" };

    private static Throwable tCause = new Throwable("Throwable for exception");

    private static String errNotExc = "Exception is not InvalidKeySpecException";

    static String createErr(Exception tE, Exception eE) {
        return "InvalidKeySpecException: ".concat(tE.toString()).concat(
                " is not equal to caught exception: ").concat(eE.toString());
    }

    /**
     * Test for <code>InvalidKeySpecException()</code> constructor Assertion:
     * constructs InvalidKeySpecException with no detail message
     */
    public void testInvalidKeySpecException01() {
        logln("==test_01: InvalidKeySpecException==");

        InvalidKeySpecException tE = new InvalidKeySpecException();
        assertTrue(errNotExc, tE instanceof InvalidKeySpecException);
        assertNull("getMessage() must return null.", tE.getMessage());
        assertNull("getCause() must return null", tE.getCause());
        try {
            throw tE;
        } catch (Exception e) {
            assertTrue(createErr(tE, e), tE.equals(e));
        }
    }

    /**
     * Test for <code>InvalidKeySpecException(String)</code> constructor
     * Assertion: constructs InvalidKeySpecException with detail message msg.
     * Parameter <code>msg</code> is not null.
     */
    public void testInvalidKeySpecException02() {
        logln("==test_02: InvalidKeySpecException==");

        InvalidKeySpecException tE;
        for (int i = 0; i < msgs.length; i++) {
            tE = new InvalidKeySpecException(msgs[i]);
            assertTrue(errNotExc.concat(" (msg: ").concat(msgs[i]).concat(")"),
                    tE instanceof InvalidKeySpecException);
            assertEquals("getMessage() must return: ".concat(msgs[i]), tE
                    .getMessage(), msgs[i]);
            assertNull("getCause() must return null", tE.getCause());
            try {
                throw tE;
            } catch (Exception e) {
                assertTrue(createErr(tE, e), tE.equals(e));
            }
        }
    }

    /**
     * Test for <code>InvalidKeySpecException(String)</code> constructor
     * Assertion: constructs InvalidKeySpecException when <code>msg</code> is
     * null
     */
    public void testInvalidKeySpecException03() {
        logln("==test_03: InvalidKeySpecException==");

        String msg = null;
        InvalidKeySpecException tE = new InvalidKeySpecException(msg);
        assertTrue(errNotExc, tE instanceof InvalidKeySpecException);
        assertNull("getMessage() must return null.", tE.getMessage());
        assertNull("getCause() must return null", tE.getCause());
        try {
            throw tE;
        } catch (Exception e) {
            assertTrue(createErr(tE, e), tE.equals(e));
        }
    }

    /**
     * Test for <code>InvalidKeySpecException(Throwable)</code> constructor
     * Assertion: constructs InvalidKeySpecException when <code>cause</code>
     * is null
     */
    public void testInvalidKeySpecException04() {
        logln("==test_04: InvalidKeySpecException==");

        Throwable cause = null;
        InvalidKeySpecException tE = new InvalidKeySpecException(cause);
        assertTrue(errNotExc, tE instanceof InvalidKeySpecException);
        assertNull("getMessage() must return null.", tE.getMessage());
        assertNull("getCause() must return null", tE.getCause());
        try {
            throw tE;
        } catch (Exception e) {
            assertTrue(createErr(tE, e), tE.equals(e));
        }
    }

    /**
     * Test for <code>InvalidKeySpecException(Throwable)</code> constructor
     * Assertion: constructs InvalidKeySpecException when <code>cause</code>
     * is not null
     */
    public void testInvalidKeySpecException05() {
        logln("==test_05: InvalidKeySpecException==");

        InvalidKeySpecException tE = new InvalidKeySpecException(tCause);
        assertTrue(errNotExc, tE instanceof InvalidKeySpecException);
        if (tE.getMessage() != null) {
            String toS = tCause.toString();
            String getM = tE.getMessage();
            assertTrue("getMessage() should contain ".concat(toS), (getM
                    .indexOf(toS) != -1));
        }
        assertNotNull("getCause() must not return null", tE.getCause());
        assertEquals("getCause() must return ".concat(tCause.toString()), tE
                .getCause(), tCause);
        try {
            throw tE;
        } catch (Exception e) {
            assertTrue(createErr(tE, e), tE.equals(e));
        }
    }

    /**
     * Test for <code>InvalidKeySpecException(String, Throwable)</code>
     * constructor Assertion: constructs InvalidKeySpecException when
     * <code>cause</code> is null <code>msg</code> is null
     */
    public void testInvalidKeySpecException06() {
        logln("==test_06: InvalidKeySpecException==");

        InvalidKeySpecException tE = new InvalidKeySpecException(null, null);
        assertTrue(errNotExc, tE instanceof InvalidKeySpecException);
        assertNull("getMessage() must return null", tE.getMessage());
        assertNull("getCause() must return null", tE.getCause());
        try {
            throw tE;
        } catch (Exception e) {
            assertTrue(createErr(tE, e), tE.equals(e));
        }
    }

    /**
     * Test for <code>InvalidKeySpecException(String, Throwable)</code>
     * constructor Assertion: constructs InvalidKeySpecException when
     * <code>cause</code> is null <code>msg</code> is not null
     */
    public void testInvalidKeySpecException07() {
        logln("==test_07: InvalidKeySpecException==");

        InvalidKeySpecException tE;
        for (int i = 0; i < msgs.length; i++) {
            tE = new InvalidKeySpecException(msgs[i], null);
            assertTrue(errNotExc.concat(" (msg: ").concat(msgs[i]).concat(")"),
                    tE instanceof InvalidKeySpecException);
            assertEquals("getMessage() must return: ".concat(msgs[i]), tE
                    .getMessage(), msgs[i]);
            assertNull("getCause() must return null", tE.getCause());
            try {
                throw tE;
            } catch (Exception e) {
                assertTrue(createErr(tE, e), tE.equals(e));
            }
        }
    }

    /**
     * Test for <code>InvalidKeySpecException(String, Throwable)</code>
     * constructor Assertion: constructs InvalidKeySpecException when
     * <code>cause</code> is not null <code>msg</code> is null
     */
    public void testInvalidKeySpecException08() {
        logln("==test_08: InvalidKeySpecException==");

        InvalidKeySpecException tE = new InvalidKeySpecException(null, tCause);
        assertTrue(errNotExc, tE instanceof InvalidKeySpecException);
        if (tE.getMessage() != null) {
            String toS = tCause.toString();
            String getM = tE.getMessage();
            assertTrue("getMessage() must should ".concat(toS), (getM
                    .indexOf(toS) != -1));
        }
        assertNotNull("getCause() must not return null", tE.getCause());
        assertEquals("getCause() must return ".concat(tCause.toString()), tE
                .getCause(), tCause);
        try {
            throw tE;
        } catch (Exception e) {
            assertTrue(createErr(tE, e), tE.equals(e));
        }
    }

    /**
     * Test for <code>InvalidKeySpecException(String, Throwable)</code>
     * constructor Assertion: constructs InvalidKeySpecException when
     * <code>cause</code> is not null <code>msg</code> is not null
     */
    public void testInvalidKeySpecException09() {
        logln("==test_09: InvalidKeySpecException==");

        InvalidKeySpecException tE;
        for (int i = 0; i < msgs.length; i++) {
            tE = new InvalidKeySpecException(msgs[i], tCause);
            assertTrue(errNotExc.concat(" (msg: ").concat(msgs[i]).concat(")"),
                    tE instanceof InvalidKeySpecException);
            String getM = tE.getMessage();
            String toS = tCause.toString();
            if (msgs[i].length() > 0) {
                assertTrue("getMessage() must contain ".concat(msgs[i]), getM
                        .indexOf(msgs[i]) != -1);
                if (!getM.equals(msgs[i])) {
                    assertTrue("getMessage() should contain ".concat(toS), getM
                            .indexOf(toS) != -1);
                }
            }
            assertNotNull("getCause() must not return null", tE.getCause());
            assertEquals("getCause() must return ".concat(tCause.toString()),
                    tE.getCause(), tCause);

            try {
                throw tE;
            } catch (Exception e) {
                assertTrue(createErr(tE, e), tE.equals(e));
            }
        }
    }
}