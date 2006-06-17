/* Copyright 1998, 2004 The Apache Software Foundation or its licensors, as applicable
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package java.io;


/**
 * This End Of File (EOF) exception is thrown when a program encounters the end
 * of a file or stream during an operation.
 * 
 */
public class EOFException extends IOException {
    
    private static final long serialVersionUID = 6433858223774886977L;
    
	/**
	 * Constructs a new instance of this class with its walkback filled in.
	 * 
	 */
	public EOFException() {
		super();
	}

	/**
	 * Constructs a new instance of this class with its walkback and message
	 * filled in.
	 * 
	 * @param detailMessage
	 *            The detail message for the exception.
	 */
	public EOFException(String detailMessage) {
		super(detailMessage);
	}

}
