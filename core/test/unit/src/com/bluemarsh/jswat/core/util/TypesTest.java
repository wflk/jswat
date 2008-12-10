/*
 * The contents of this file are subject to the terms of the Common Development
 * and Distribution License (the License). You may not use this file except in
 * compliance with the License.
 *
 * You can obtain a copy of the License at http://www.netbeans.org/cddl.html
 * or http://www.netbeans.org/cddl.txt.
 *
 * When distributing Covered Code, include this CDDL Header Notice in each file
 * and include the License file at http://www.netbeans.org/cddl.txt.
 * If applicable, add the following below the CDDL Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 *
 * The Original Software is JSwat. The Initial Developer of the Original
 * Software is Nathan L. Fiedler. Portions created by Nathan L. Fiedler
 * are Copyright (C) 2003-2004. All Rights Reserved.
 *
 * Contributor(s): Nathan L. Fiedler.
 *
 * $Id: TypesTest.java 6 2007-05-16 07:14:24Z nfiedler $
 */

package com.bluemarsh.jswat.core.util;

import java.util.ArrayList;
import junit.extensions.*;
import junit.framework.*;

/**
 * Tests the Types utility class.
 */
public class TypesTest extends TestCase {

    public TypesTest(String name) {
        super(name);
    }

    public static Test suite() {
        return new TestSuite(TypesTest.class);
    }

    public static void main(String[] args) {
        junit.textui.TestRunner.run(suite());
    }

    public void test_Types_canWiden() {
        boolean widen = Types.canWiden("B", ArrayList.class);
        assertFalse("ArrayList cannot be widened to byte", widen);
        widen = Types.canWiden("B", Integer.class);
        assertFalse("Integer cannot be widened to byte", widen);
        widen = Types.canWiden("I", Double.class);
        assertFalse("Double cannot be widened to int", widen);
        widen = Types.canWiden("I", Byte.class);
        assertTrue("Byte should be widened to int", widen);
        widen = Types.canWiden("J", Integer.class);
        assertTrue("Integer should be widened to long", widen);
        widen = Types.canWiden("D", Float.class);
        assertTrue("Float should be widened to double", widen);
    }

    public void test_Types_cast() {
        Object rval = Types.cast("B", new Integer(100));
        assertTrue("rval is not a Byte", rval instanceof Byte);
        rval = Types.cast("Ljava/lang/Byte;", new Integer(100));
        assertTrue("rval is not a Byte", rval instanceof Byte);
        rval = Types.cast("F", new Double(1.234));
        assertTrue("rval is not a Float", rval instanceof Float);
        rval = Types.cast("Ljava/lang/Float;", new Double(1.234));
        assertTrue("rval is not a Float", rval instanceof Float);
    }

    public void test_Types_isCompatible() {
        // abstract class case
        boolean isit = Types.isCompatible("Ljava/lang/Number;", Byte.class);
        assertTrue("Byte is not a Number", isit);
        // interface case
        isit = Types.isCompatible("Ljava/util/List;", ArrayList.class);
        assertTrue("ArrayList is not a List", isit);
    }

    public void test_Types_jniToName() {
        String jni = Types.jniToName("Ljava/lang/String;");
        assertEquals("java.lang.String", jni);
        jni = Types.jniToName("Ljava/net/URL;");
        assertEquals("java.net.URL", jni);
        jni = Types.jniToName("Lcom/bluemarsh/jswat/Main;");
        assertEquals("com.bluemarsh.jswat.Main", jni);
    }

    public void test_Types_jniToTypeName() {
        String jni = Types.jniToTypeName("Ljava/lang/String;", false);
        assertEquals("java.lang.String", jni);
        jni = Types.jniToTypeName("Z", false);
        assertEquals("boolean", jni);
        jni = Types.jniToTypeName("F", false);
        assertEquals("float", jni);
        jni = Types.jniToTypeName("V", false);
        assertEquals("void", jni);
        jni = Types.jniToTypeName("[[I", false);
        assertEquals("int[][]", jni);
        jni = Types.jniToTypeName("[[I", true);
        assertEquals("int", jni);
        jni = Types.jniToTypeName("[Ljava/lang/String;", false);
        assertEquals("java.lang.String[]", jni);
        jni = Types.jniToTypeName("[Ljava/lang/String;", true);
        assertEquals("java.lang.String", jni);
    }

    public void test_Types_nameToJni() {
        String jni = Types.nameToJni("java.lang.String");
        assertEquals("Ljava/lang/String;", jni);
        jni = Types.nameToJni("java.net.URL");
        assertEquals("Ljava/net/URL;", jni);
        jni = Types.nameToJni("com.bluemarsh.jswat.Main");
        assertEquals("Lcom/bluemarsh/jswat/Main;", jni);
    }

    public void test_Types_typeNameToJNI() {
        // Primivite types
        String jni = Types.typeNameToJNI("boolean");
        assertEquals("Z", jni);
        jni = Types.typeNameToJNI("byte");
        assertEquals("B", jni);
        jni = Types.typeNameToJNI("char");
        assertEquals("C", jni);
        jni = Types.typeNameToJNI("double");
        assertEquals("D", jni);
        jni = Types.typeNameToJNI("float");
        assertEquals("F", jni);
        jni = Types.typeNameToJNI("int");
        assertEquals("I", jni);
        jni = Types.typeNameToJNI("long");
        assertEquals("J", jni);
        jni = Types.typeNameToJNI("short");
        assertEquals("S", jni);
        jni = Types.typeNameToJNI("void");
        assertEquals("V", jni);

        // Primivite array types
        jni = Types.typeNameToJNI("boolean[]");
        assertEquals("[Z", jni);
        jni = Types.typeNameToJNI("byte[]");
        assertEquals("[B", jni);
        jni = Types.typeNameToJNI("char[]");
        assertEquals("[C", jni);
        jni = Types.typeNameToJNI("double[]");
        assertEquals("[D", jni);
        jni = Types.typeNameToJNI("float[]");
        assertEquals("[F", jni);
        jni = Types.typeNameToJNI("int[]");
        assertEquals("[I", jni);
        jni = Types.typeNameToJNI("long[]");
        assertEquals("[J", jni);
        jni = Types.typeNameToJNI("short[]");
        assertEquals("[S", jni);
        jni = Types.typeNameToJNI("void[]");
        assertEquals("[V", jni);

        // Core classes
        jni = Types.typeNameToJNI("String");
        assertEquals("Ljava/lang/String;", jni);
        jni = Types.typeNameToJNI("Class");
        assertEquals("Ljava/lang/Class;", jni);
        jni = Types.typeNameToJNI("Math");
        assertEquals("Ljava/lang/Math;", jni);

        // Core class arrays
        jni = Types.typeNameToJNI("String[]");
        assertEquals("[Ljava/lang/String;", jni);
        jni = Types.typeNameToJNI("Class[]");
        assertEquals("[Ljava/lang/Class;", jni);
        jni = Types.typeNameToJNI("Math[]");
        assertEquals("[Ljava/lang/Math;", jni);

        // Multi-dimensional arrays
        jni = Types.typeNameToJNI("int[][]");
        assertEquals("[[I", jni);
        jni = Types.typeNameToJNI("String[][][]");
        assertEquals("[[[Ljava/lang/String;", jni);

        // Other classes
        jni = Types.typeNameToJNI("com.pkg.MyClass");
        assertEquals("Lcom/pkg/MyClass;", jni);
        jni = Types.typeNameToJNI("com.bluemarsh.jswat.Main");
        assertEquals("Lcom/bluemarsh/jswat/Main;", jni);
        jni = Types.typeNameToJNI("org.gnu.regex.Pattern");
        assertEquals("Lorg/gnu/regex/Pattern;", jni);

        // Other class arrays
        jni = Types.typeNameToJNI("com.pkg.MyClass[]");
        assertEquals("[Lcom/pkg/MyClass;", jni);
        jni = Types.typeNameToJNI("com.bluemarsh.jswat.Main[]");
        assertEquals("[Lcom/bluemarsh/jswat/Main;", jni);
        jni = Types.typeNameToJNI("org.gnu.regex.Pattern[]");
        assertEquals("[Lorg/gnu/regex/Pattern;", jni);
    }

    public void test_Types_wrapperToPrimitive() {
        String jni = Types.wrapperToPrimitive("Ljava/lang/Boolean;");
        assertEquals("Z", jni);
        jni = Types.wrapperToPrimitive("Ljava/lang/Byte;");
        assertEquals("B", jni);
        jni = Types.wrapperToPrimitive("Ljava/lang/Character;");
        assertEquals("C", jni);
        jni = Types.wrapperToPrimitive("Ljava/lang/Double;");
        assertEquals("D", jni);
        jni = Types.wrapperToPrimitive("Ljava/lang/Float;");
        assertEquals("F", jni);
        jni = Types.wrapperToPrimitive("Ljava/lang/Integer;");
        assertEquals("I", jni);
        jni = Types.wrapperToPrimitive("Ljava/lang/Long;");
        assertEquals("J", jni);
        jni = Types.wrapperToPrimitive("Ljava/lang/Short;");
        assertEquals("S", jni);
        jni = Types.wrapperToPrimitive("Ljava/lang/Void;");
        assertEquals("V", jni);
        jni = Types.wrapperToPrimitive("Ljava/lang/Math;");
        assertEquals("", jni);
    }
}