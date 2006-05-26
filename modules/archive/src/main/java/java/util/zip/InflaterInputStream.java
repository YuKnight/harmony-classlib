/* Copyright 1998, 2005 The Apache Software Foundation or its licensors, as applicable
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

package java.util.zip;


import java.io.EOFException;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.harmony.luni.util.Msg;

/**
 * InflaterOuputStream read data which has been compressed using the DEFLATE
 * compression method.
 * 
 * @see Inflater
 * @see DeflaterOutputStream
 */
public class InflaterInputStream extends FilterInputStream {

	protected Inflater inf;

	protected byte[] buf;

	protected int len;

	boolean closed = false;

	boolean eof = false;

	static final int BUF_SIZE = 512;

	/**
	 * Constructs a new InflaterOutputStream on is
	 * 
	 * @param is The InputStream to read data from
	 */
	public InflaterInputStream(InputStream is) {
		this(is, new Inflater(), BUF_SIZE);
	}

	/**
	 * Constructs a new InflaterOutputStream on is, using the Inflater provided
	 * in inf.
	 * 
	 * @param is
	 *            The InputStream to read data from
	 * @param inf
	 *            The Inflater to use for decompression
	 */
	public InflaterInputStream(InputStream is, Inflater inf) {
		this(is, inf, BUF_SIZE);
	}

	/**
	 * Constructs a new InflaterOutputStream on is, using the Inflater provided
	 * in inf. The dsize of the inflation buffer is determined by bsize.
	 * 
	 * @param is
	 *            The InputStream to read data from
	 * @param inf
	 *            The Inflater to use for decompression
	 * @param bsize
	 *            sie of the inflation buffer
	 */
	public InflaterInputStream(InputStream is, Inflater inf, int bsize) {
		super(is);
		if (is == null || inf == null)
			throw new NullPointerException();
		if (bsize <= 0)
			throw new IllegalArgumentException();
		this.inf = inf;
		buf = new byte[bsize];
	}

	/**
	 * Reads a single byte of decompressed data.
	 * 
	 * @return byte read
	 * @exception IOException
	 *                If an error occurs reading
	 */
	public int read() throws IOException {
		byte[] b = new byte[1];
		if (read(b, 0, 1) == -1)
			return -1;
		return b[0] & 0xff;
	}

	/**
	 * Reads up to nbytes of decompressed data and stores it in buf starting at
	 * off.
	 * 
	 * @param buffer
	 *            Buffer to store into
	 * @param off
	 *            offset in buffer to store at
	 * @param nbytes
	 *            number of bytes to store
	 * @return Number of uncompressed bytes read
	 * @exception IOException
	 *                If an error occurs reading
	 */
	public int read(byte[] buffer, int off, int nbytes) throws IOException {
		/* [MSG "K0059", "Stream is closed"] */
		if (closed)
			throw new IOException(Msg.getString("K0059"));

		if (null == buffer)
			throw new NullPointerException();

		if (off < 0 || nbytes < 0 || off + nbytes > buffer.length)
			throw new IndexOutOfBoundsException();

		if (nbytes == 0)
			return 0;

		if (inf.finished()) {
			eof = true;
			return -1;
		}

		// avoid int overflow, check null buffer
		if (off <= buffer.length && nbytes >= 0 && off >= 0
				&& buffer.length - off >= nbytes) {
			do {
				if (inf.needsInput())
					fill();
				int result;
				try {
					result = inf.inflate(buffer, off, nbytes);
				} catch (DataFormatException e) {
					if (len == -1)
						throw new EOFException();
					throw new IOException(e.getMessage());
				}
				if (result > 0)
					return result;
				else if (inf.finished()) {
					eof = true;
					return -1;
				} else if (inf.needsDictionary())
					return -1;
				else if (len == -1)
					throw new EOFException();
				// If result == 0, fill() and try again
			} while (true);
		}
		throw new ArrayIndexOutOfBoundsException();
	}

	protected void fill() throws IOException {
		if (closed)
			throw new IOException(Msg.getString("K0059"));
		if ((len = in.read(buf)) > 0)
			inf.setInput(buf, 0, len);
	}

	/**
	 * Skips up to nbytes of uncompressed data.
	 * 
	 * @param nbytes
	 *            Number of bytes to skip
	 * @return Number of uncompressed bytes skipped
	 * @exception IOException
	 *                If an error occurs skipping
	 */
	public long skip(long nbytes) throws IOException {
		if (nbytes >= 0) {
			long count = 0, rem = 0;
			while (count < nbytes) {
				int x = read(buf, 0,
						(rem = nbytes - count) > buf.length ? buf.length
								: (int) rem);
				if (x == -1) {
					eof = true;
					return count;
				}
				count += x;
			}
			return count;
		}
		throw new IllegalArgumentException();
	}

	/**
	 * Returns 0 if this stream has been closed, 1 otherwise.
	 * 
	 * @exception IOException
	 *                If an error occurs
	 */
	public int available() throws IOException {
		if (closed)
			throw new IOException(Msg.getString("K0059"));
		if (eof)
			return 0;
		return 1;
	}

	/**
	 * Closes the stream
	 * 
	 * @exception IOException
	 *                If an error occurs closing the stream
	 */
	public void close() throws IOException {
		if (!closed) {
			inf.end();
			closed = true;
			eof = true;
			super.close();
		}
	}
	
    /**
	 * Marks the current position in the stream.
	 * 
	 * This implementation overrides the supertype implementation to do nothing
	 * at all.
	 * 
	 * @param readlimit
	 *            of no use
	 */
	public void mark(int readlimit) {
		// do nothing
	}

    /**
	 * Reset the position of the stream to the last mark position.
	 * 
	 * This implementation overrides the supertype implementation and always
	 * throws an {@link IOException IOException} when called.
	 * 
	 * @throws IOException
	 *             if the method is called
	 */
    public void reset() throws IOException{
        throw new IOException();
    }
    
    /**
	 * Answers whether the receiver implements mark semantics.  This type
	 * does not support mark, so always responds <code>false</code>.
	 * @return false 
	 */
	public boolean markSupported() {
		return false;
	}

}
