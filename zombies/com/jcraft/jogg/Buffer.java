/*
 * Decompiled with CFR 0_122.
 */
package com.jcraft.jogg;

import java.io.PrintStream;

public class Buffer {
    private static final int BUFFER_INCREMENT = 256;
    private static final int[] mask = new int[]{0, 1, 3, 7, 15, 31, 63, 127, 255, 511, 1023, 2047, 4095, 8191, 16383, 32767, 65535, 131071, 262143, 524287, 1048575, 2097151, 4194303, 8388607, 16777215, 33554431, 67108863, 134217727, 268435455, 536870911, 1073741823, Integer.MAX_VALUE, -1};
    int ptr = 0;
    byte[] buffer = null;
    int endbit = 0;
    int endbyte = 0;
    int storage = 0;

    public void writeinit() {
        this.buffer = new byte[256];
        this.ptr = 0;
        this.buffer[0] = 0;
        this.storage = 256;
    }

    public void write(byte[] s) {
        for (int i = 0; i < s.length && s[i] != 0; ++i) {
            this.write(s[i], 8);
        }
    }

    public void read(byte[] s, int bytes) {
        int i = 0;
        while (bytes-- != 0) {
            s[i++] = (byte)this.read(8);
        }
    }

    void reset() {
        this.ptr = 0;
        this.buffer[0] = 0;
        this.endbyte = 0;
        this.endbit = 0;
    }

    public void writeclear() {
        this.buffer = null;
    }

    public void readinit(byte[] buf, int bytes) {
        this.readinit(buf, 0, bytes);
    }

    public void readinit(byte[] buf, int start, int bytes) {
        this.ptr = start;
        this.buffer = buf;
        this.endbyte = 0;
        this.endbit = 0;
        this.storage = bytes;
    }

    public void write(int value, int bits) {
        if (this.endbyte + 4 >= this.storage) {
            byte[] foo = new byte[this.storage + 256];
            System.arraycopy(this.buffer, 0, foo, 0, this.storage);
            this.buffer = foo;
            this.storage += 256;
        }
        byte[] arrby = this.buffer;
        int n = this.ptr;
        arrby[n] = (byte)(arrby[n] | (byte)((value &= mask[bits]) << this.endbit));
        if ((bits += this.endbit) >= 8) {
            this.buffer[this.ptr + 1] = (byte)(value >>> 8 - this.endbit);
            if (bits >= 16) {
                this.buffer[this.ptr + 2] = (byte)(value >>> 16 - this.endbit);
                if (bits >= 24) {
                    this.buffer[this.ptr + 3] = (byte)(value >>> 24 - this.endbit);
                    if (bits >= 32) {
                        this.buffer[this.ptr + 4] = this.endbit > 0 ? (byte)(value >>> 32 - this.endbit) : 0;
                    }
                }
            }
        }
        this.endbyte += bits / 8;
        this.ptr += bits / 8;
        this.endbit = bits & 7;
    }

    public int look(int bits) {
        int m = mask[bits];
        if (this.endbyte + 4 >= this.storage && this.endbyte + ((bits += this.endbit) - 1) / 8 >= this.storage) {
            return -1;
        }
        int ret = (this.buffer[this.ptr] & 255) >>> this.endbit;
        if (bits > 8) {
            ret |= (this.buffer[this.ptr + 1] & 255) << 8 - this.endbit;
            if (bits > 16) {
                ret |= (this.buffer[this.ptr + 2] & 255) << 16 - this.endbit;
                if (bits > 24) {
                    ret |= (this.buffer[this.ptr + 3] & 255) << 24 - this.endbit;
                    if (bits > 32 && this.endbit != 0) {
                        ret |= (this.buffer[this.ptr + 4] & 255) << 32 - this.endbit;
                    }
                }
            }
        }
        return m & ret;
    }

    public int look1() {
        if (this.endbyte >= this.storage) {
            return -1;
        }
        return this.buffer[this.ptr] >> this.endbit & 1;
    }

    public void adv(int bits) {
        this.ptr += (bits += this.endbit) / 8;
        this.endbyte += bits / 8;
        this.endbit = bits & 7;
    }

    public void adv1() {
        ++this.endbit;
        if (this.endbit > 7) {
            this.endbit = 0;
            ++this.ptr;
            ++this.endbyte;
        }
    }

    public int read(int bits) {
        int ret;
        int m = mask[bits];
        bits += this.endbit;
        if (this.endbyte + 4 >= this.storage) {
            ret = -1;
            if (this.endbyte + (bits - 1) / 8 >= this.storage) {
                this.ptr += bits / 8;
                this.endbyte += bits / 8;
                this.endbit = bits & 7;
                return ret;
            }
        }
        ret = (this.buffer[this.ptr] & 255) >>> this.endbit;
        if (bits > 8) {
            ret |= (this.buffer[this.ptr + 1] & 255) << 8 - this.endbit;
            if (bits > 16) {
                ret |= (this.buffer[this.ptr + 2] & 255) << 16 - this.endbit;
                if (bits > 24) {
                    ret |= (this.buffer[this.ptr + 3] & 255) << 24 - this.endbit;
                    if (bits > 32 && this.endbit != 0) {
                        ret |= (this.buffer[this.ptr + 4] & 255) << 32 - this.endbit;
                    }
                }
            }
        }
        this.ptr += bits / 8;
        this.endbyte += bits / 8;
        this.endbit = bits & 7;
        return ret &= m;
    }

    public int readB(int bits) {
        int ret;
        int m = 32 - bits;
        bits += this.endbit;
        if (this.endbyte + 4 >= this.storage) {
            ret = -1;
            if (this.endbyte * 8 + bits > this.storage * 8) {
                this.ptr += bits / 8;
                this.endbyte += bits / 8;
                this.endbit = bits & 7;
                return ret;
            }
        }
        ret = (this.buffer[this.ptr] & 255) << 24 + this.endbit;
        if (bits > 8) {
            ret |= (this.buffer[this.ptr + 1] & 255) << 16 + this.endbit;
            if (bits > 16) {
                ret |= (this.buffer[this.ptr + 2] & 255) << 8 + this.endbit;
                if (bits > 24) {
                    ret |= (this.buffer[this.ptr + 3] & 255) << this.endbit;
                    if (bits > 32 && this.endbit != 0) {
                        ret |= (this.buffer[this.ptr + 4] & 255) >> 8 - this.endbit;
                    }
                }
            }
        }
        ret = ret >>> (m >> 1) >>> (m + 1 >> 1);
        this.ptr += bits / 8;
        this.endbyte += bits / 8;
        this.endbit = bits & 7;
        return ret;
    }

    public int read1() {
        if (this.endbyte >= this.storage) {
            int ret = -1;
            ++this.endbit;
            if (this.endbit > 7) {
                this.endbit = 0;
                ++this.ptr;
                ++this.endbyte;
            }
            return ret;
        }
        int ret = this.buffer[this.ptr] >> this.endbit & 1;
        ++this.endbit;
        if (this.endbit > 7) {
            this.endbit = 0;
            ++this.ptr;
            ++this.endbyte;
        }
        return ret;
    }

    public int bytes() {
        return this.endbyte + (this.endbit + 7) / 8;
    }

    public int bits() {
        return this.endbyte * 8 + this.endbit;
    }

    public byte[] buffer() {
        return this.buffer;
    }

    public static int ilog(int v) {
        int ret = 0;
        while (v > 0) {
            ++ret;
            v >>>= 1;
        }
        return ret;
    }

    public static void report(String in) {
        System.err.println(in);
        System.exit(1);
    }
}

