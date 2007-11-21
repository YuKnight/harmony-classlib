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
package org.apache.harmony.pack200.bytecode.forms;

import org.apache.harmony.pack200.bytecode.ByteCode;
import org.apache.harmony.pack200.bytecode.OperandManager;

/**
 * This class implements the byte code form for those
 * bytecodes which have float references (and only
 * float references). This excludes iinc (which has its
 * own form, IincForm).
 */
public class LocalForm extends ByteCodeForm {

    public LocalForm(int opcode, String name) {
        super(opcode, name);
        // TODO Auto-generated constructor stub
    }

    public LocalForm(int opcode, String name, int[] rewrite) {
        super(opcode, name, rewrite);
    }

    public int getOperandType() {
        return TYPE_LOCAL;
    }
    
    public boolean hasLocalOperand() {
        return true;
    }

    /* (non-Javadoc)
     * @see org.apache.harmony.pack200.bytecode.forms.ByteCodeForm#setByteCodeOperands(org.apache.harmony.pack200.bytecode.ByteCode, org.apache.harmony.pack200.bytecode.OperandTable, org.apache.harmony.pack200.SegmentConstantPool)
     */
    public void setByteCodeOperands(ByteCode byteCode,
            OperandManager operandManager) {
        byteCode.setOperandBytes(new int[]{operandManager.nextLocal()});
    }
}