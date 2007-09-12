/*
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.apache.harmony.pack200;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class CpBands extends BandSet {
    
    public SegmentConstantPool getConstantPool() {
        return pool;
    }
    

    private final SegmentConstantPool pool = new SegmentConstantPool(this);

    private String[] cpClass;

    private String[] cpDescriptor;

    private double[] cpDouble;

    private String[] cpFieldClass;

    private Object cpFieldDescriptor;

    private float[] cpFloat;

    private String[] cpIMethodClass;

    private String[] cpIMethodDescriptor;

    private int[] cpInt;

    private long[] cpLong;

    private String[] cpMethodClass;

    private String[] cpMethodDescriptor;

    private String[] cpSignature;

    private String[] cpString;

    private String[] cpUTF8;

    
    public CpBands(Segment segment) {
        super(segment);
    }
    
    public void unpack(InputStream in) throws IOException, Pack200Exception {
        parseCpUtf8(in);
        parseCpInt(in);
        parseCpFloat(in);
        parseCpLong(in);
        parseCpDouble(in);
        parseCpString(in);
        parseCpClass(in);
        parseCpSignature(in);
        parseCpDescriptor(in);
        parseCpField(in);
        parseCpMethod(in);
        parseCpIMethod(in);
    }

    public void pack(OutputStream outputStream) {
        
    }
    
    /**
     * Parses the constant pool class names, using {@link #cpClassCount} to
     * populate {@link #cpClass} from {@link #cpUTF8}.
     * 
     * @param in
     *            the input stream to read from
     * @throws IOException
     *             if a problem occurs during reading from the underlying stream
     * @throws Pack200Exception
     *             if a problem occurs with an unexpected value or unsupported
     *             codec
     */
    private void parseCpClass(InputStream in) throws IOException,
            Pack200Exception {
        int cpClassCount = header.getCpClassCount();
        cpClass = parseReferences("cp_Class", in, Codec.UDELTA5, cpClassCount,
                cpUTF8);
    }


    /**
     * Parses the constant pool descriptor definitions, using
     * {@link #cpDescriptorCount} to populate {@link #cpDescriptor}. For ease
     * of use, the cpDescriptor is stored as a string of the form <i>name:type</i>,
     * largely to make it easier for representing field and method descriptors
     * (e.g. <code>out:java.lang.PrintStream</code>) in a way that is
     * compatible with passing String arrays.
     * 
     * @param in
     *            the input stream to read from
     * @throws IOException
     *             if a problem occurs during reading from the underlying stream
     * @throws Pack200Exception
     *             if a problem occurs with an unexpected value or unsupported
     *             codec
     */
    private void parseCpDescriptor(InputStream in) throws IOException,
            Pack200Exception {
        int cpDescriptorCount = header.getCpDescriptorCount();
        String[] cpDescriptorNames = parseReferences("cp_Descr_name", in,
                Codec.DELTA5, cpDescriptorCount, cpUTF8);
        String[] cpDescriptorTypes = parseReferences("cp_Descr_type", in,
                Codec.UDELTA5, cpDescriptorCount, cpSignature);
        cpDescriptor = new String[cpDescriptorCount];
        for (int i = 0; i < cpDescriptorCount; i++) {
            cpDescriptor[i] = cpDescriptorNames[i] + ":" + cpDescriptorTypes[i]; //$NON-NLS-1$
        }
    }

    private void parseCpDouble(InputStream in) throws IOException,
            Pack200Exception {
        int cpDoubleCount = header.getCpDoubleCount();
        cpDouble = new double[cpDoubleCount];
        long[] hiBits = decodeBandLong("cp_Double_hi", in, Codec.UDELTA5,
                cpDoubleCount);
        long[] loBits = decodeBandLong("cp_Double_lo", in, Codec.DELTA5,
                cpDoubleCount);
        for (int i = 0; i < cpDoubleCount; i++) {
            cpDouble[i] = Double.longBitsToDouble(hiBits[i] << 32 | loBits[i]);
        }
    }

    /**
     * Parses the constant pool field definitions, using {@link #cpFieldCount}
     * to populate {@link #cpFieldClass} and {@link #cpFieldDescriptor}.
     * 
     * @param in
     *            the input stream to read from
     * @throws IOException
     *             if a problem occurs during reading from the underlying stream
     * @throws Pack200Exception
     *             if a problem occurs with an unexpected value or unsupported
     *             codec
     */
    private void parseCpField(InputStream in) throws IOException,
            Pack200Exception {
        int cpFieldCount = header.getCpFieldCount();
        cpFieldClass = parseReferences("cp_Field_class", in, Codec.DELTA5,
                cpFieldCount, cpClass);
        cpFieldDescriptor = parseReferences("cp_Field_desc", in, Codec.UDELTA5,
                cpFieldCount, cpDescriptor);
    }

    private void parseCpFloat(InputStream in) throws IOException,
            Pack200Exception {
        int cpFloatCount = header.getCpFloatCount();
        cpFloat = new float[cpFloatCount];
        int floatBits[] = decodeBandInt("cp_Float", in, Codec.UDELTA5,
                cpFloatCount);
        for (int i = 0; i < cpFloatCount; i++) {
            cpFloat[i] = Float.intBitsToFloat(floatBits[i]);
        }
    }

    /**
     * Parses the constant pool interface method definitions, using
     * {@link #cpIMethodCount} to populate {@link #cpIMethodClass} and
     * {@link #cpIMethodDescriptor}.
     * 
     * @param in
     *            the input stream to read from
     * @throws IOException
     *             if a problem occurs during reading from the underlying stream
     * @throws Pack200Exception
     *             if a problem occurs with an unexpected value or unsupported
     *             codec
     */
    private void parseCpIMethod(InputStream in) throws IOException,
            Pack200Exception {
        int cpIMethodCount = header.getCpIMethodCount();
        cpIMethodClass = parseReferences("cp_Imethod_class", in, Codec.DELTA5,
                cpIMethodCount, cpClass);
        cpIMethodDescriptor = parseReferences("cp_Imethod_desc", in,
                Codec.UDELTA5, cpIMethodCount, cpDescriptor);
    }

    private void parseCpInt(InputStream in) throws IOException,
            Pack200Exception {
        int cpIntCount = header.getCpIntCount();
        cpInt = decodeBandInt("cpInt", in, Codec.UDELTA5, cpIntCount);
    }

    private void parseCpLong(InputStream in) throws IOException,
            Pack200Exception {
        int cpLongCount = header.getCpLongCount();
        cpLong = parseFlags("cp_Long", in, cpLongCount, new int[] { 1 },
                Codec.UDELTA5, Codec.DELTA5)[0];
    }

    /**
     * Parses the constant pool method definitions, using {@link #cpMethodCount}
     * to populate {@link #cpMethodClass} and {@link #cpMethodDescriptor}.
     * 
     * @param in
     *            the input stream to read from
     * @throws IOException
     *             if a problem occurs during reading from the underlying stream
     * @throws Pack200Exception
     *             if a problem occurs with an unexpected value or unsupported
     *             codec
     */
    private void parseCpMethod(InputStream in) throws IOException,
            Pack200Exception {
        int cpMethodCount = header.getCpMethodCount();
        cpMethodClass = parseReferences("cp_Method_class", in, Codec.DELTA5,
                cpMethodCount, cpClass);
        cpMethodDescriptor = parseReferences("cp_Method_desc", in,
                Codec.UDELTA5, cpMethodCount, cpDescriptor);
    }

    /**
     * Parses the constant pool signature classes, using
     * {@link #cpSignatureCount} to populate {@link #cpSignature}. A signature
     * form is akin to the bytecode representation of a class; Z for boolean, I
     * for int, [ for array etc. However, although classes are started with L,
     * the classname does not follow the form; instead, there is a separate
     * array of classes. So an array corresponding to
     * <code>public static void main(String args[])</code> has a form of
     * <code>[L(V)</code> and a classes array of
     * <code>[java.lang.String]</code>. The {@link #cpSignature} is a string
     * representation identical to the bytecode equivalent
     * <code>[Ljava/lang/String;(V)</code> TODO Check that the form is as
     * above and update other types e.g. J
     * 
     * @param in
     *            the input stream to read from
     * @throws IOException
     *             if a problem occurs during reading from the underlying stream
     * @throws Pack200Exception
     *             if a problem occurs with an unexpected value or unsupported
     *             codec
     */
    private void parseCpSignature(InputStream in) throws IOException,
            Pack200Exception {
        int cpSignatureCount = header.getCpSignatureCount();
        String[] cpSignatureForm = parseReferences("cp_Signature_form", in,
                Codec.DELTA5, cpSignatureCount, cpUTF8);
        cpSignature = new String[cpSignatureCount];
        long last = 0;
        for (int i = 0; i < cpSignatureCount; i++) {
            String form = cpSignatureForm[i];
            int len = form.length();
            StringBuffer signature = new StringBuffer(64);
            ArrayList list = new ArrayList();
            for (int j = 0; j < len; j++) {
                char c = form.charAt(j);
                signature.append(c);
                if (c == 'L') {
                    int index = (int) (last = Codec.UDELTA5.decode(in, last));
                    String className = cpClass[index];
                    list.add(className);
                    signature.append(className);
                }
            }
            cpSignature[i] = signature.toString();
        }
    }

    /**
     * Parses the constant pool strings, using {@link #cpStringCount} to
     * populate {@link #cpString} from indexes into {@link #cpUTF8}.
     * 
     * @param in
     *            the input stream to read from
     * @throws IOException
     *             if a problem occurs during reading from the underlying stream
     * @throws Pack200Exception
     *             if a problem occurs with an unexpected value or unsupported
     *             codec
     */
    private void parseCpString(InputStream in) throws IOException,
            Pack200Exception {
        int cpStringCount = header.getCpStringCount();
        cpString = new String[cpStringCount];
        long last = 0;
        for (int i = 0; i < cpStringCount; i++) {
            int index = (int) (last = Codec.UDELTA5.decode(in, last));
            cpString[i] = cpUTF8[index];
        }
    }

    private void parseCpUtf8(InputStream in) throws IOException,
            Pack200Exception {
        int cpUTF8Count = header.getCpUTF8Count();
        // TODO Update codec.decode -> decodeScalar
        cpUTF8 = new String[cpUTF8Count];
        cpUTF8[0] = ""; //$NON-NLS-1$
        int[] suffix = new int[cpUTF8Count];
        long last = 0;
        int[] prefix = decodeBandInt("cpUTF8Prefix", in, Codec.DELTA5, cpUTF8Count-2);
        int chars = 0;
        int bigSuffix = 0;
        for (int i = 1; i < cpUTF8Count; i++) {
            last = suffix[i] = (int) Codec.UNSIGNED5.decode(in);
            if (last == 0) {
                bigSuffix++;
            } else {
                chars += last;
            }
        }
        char data[] = new char[chars];
        for (int i = 0; i < data.length; i++) {
            data[i] = (char) Codec.CHAR3.decode(in);
        }
        // read in the big suffix data
        char bigSuffixData[][] = new char[bigSuffix][];
        last = 0;
        for (int i = 0; i < bigSuffix; i++) {
            last = (int) Codec.DELTA5.decode(in, last);
            bigSuffixData[i] = new char[(int) last];
        }
        // initialize big suffix data
        for (int i = 0; i < bigSuffix; i++) {
            char[] singleBigSuffixData = bigSuffixData[i];
            last = 0;
            for (int j = 0; j < singleBigSuffixData.length; j++) {
                last = singleBigSuffixData[j] = (char) Codec.DELTA5.decode(in,
                        last);
            }
        }
        // go through the strings
        chars = 0;
        bigSuffix = 0;
        for (int i = 1; i < cpUTF8Count; i++) {
            String lastString = cpUTF8[i - 1];
            if (suffix[i] == 0) {
                // The big suffix stuff hasn't been tested, and I'll be
                // surprised if it works first time w/o errors ...
                cpUTF8[i] = lastString.substring(0, i>1 ? prefix[i-2] : 0)
                        + new String(bigSuffixData[bigSuffix++]);
            } else {
                cpUTF8[i] = lastString.substring(0, i>1 ? prefix[i-2]: 0)
                        + new String(data, chars, suffix[i]);
                chars += suffix[i];
            }
        }
    }
    
    public String[] getCpClass() {
        return cpClass;
    }

    public String[] getCpDescriptor() {
        return cpDescriptor;
    }

    public double[] getCpDouble() {
        return cpDouble;
    }

    public String[] getCpFieldClass() {
        return cpFieldClass;
    }

    public Object getCpFieldDescriptor() {
        return cpFieldDescriptor;
    }

    public float[] getCpFloat() {
        return cpFloat;
    }

    public String[] getCpIMethodClass() {
        return cpIMethodClass;
    }

    public String[] getCpIMethodDescriptor() {
        return cpIMethodDescriptor;
    }

    public int[] getCpInt() {
        return cpInt;
    }

    public long[] getCpLong() {
        return cpLong;
    }

    public String[] getCpMethodClass() {
        return cpMethodClass;
    }

    public String[] getCpMethodDescriptor() {
        return cpMethodDescriptor;
    }

    public String[] getCpSignature() {
        return cpSignature;
    }

    public String[] getCpString() {
        return cpString;
    }

    public String[] getCpUTF8() {
        return cpUTF8;
    }

}
