/*
 * Decompiled with CFR 0_122.
 */
package com.badlogic.gdx.backends.lwjgl;

import com.badlogic.gdx.utils.BufferUtils;
import com.badlogic.gdx.utils.GdxRuntimeException;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import org.lwjgl.opengl.EXTFramebufferObject;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;

class LwjglGL20
implements com.badlogic.gdx.graphics.GL20 {
    private ByteBuffer buffer = null;
    private FloatBuffer floatBuffer = null;
    private IntBuffer intBuffer = null;

    LwjglGL20() {
    }

    private void ensureBufferCapacity(int numBytes) {
        if (this.buffer == null || this.buffer.capacity() < numBytes) {
            this.buffer = BufferUtils.newByteBuffer(numBytes);
            this.floatBuffer = this.buffer.asFloatBuffer();
            this.intBuffer = this.buffer.asIntBuffer();
        }
    }

    private FloatBuffer toFloatBuffer(float[] v, int offset, int count) {
        this.ensureBufferCapacity(count << 2);
        this.floatBuffer.clear();
        BufferUtils.copy(v, this.floatBuffer, count, offset);
        return this.floatBuffer;
    }

    private IntBuffer toIntBuffer(int[] v, int offset, int count) {
        this.ensureBufferCapacity(count << 2);
        this.intBuffer.clear();
        BufferUtils.copy(v, count, offset, (Buffer)this.intBuffer);
        return this.intBuffer;
    }

    @Override
    public void glActiveTexture(int texture) {
        GL13.glActiveTexture(texture);
    }

    @Override
    public void glAttachShader(int program, int shader) {
        GL20.glAttachShader(program, shader);
    }

    @Override
    public void glBindAttribLocation(int program, int index, String name) {
        GL20.glBindAttribLocation(program, index, name);
    }

    @Override
    public void glBindBuffer(int target, int buffer) {
        GL15.glBindBuffer(target, buffer);
    }

    @Override
    public void glBindFramebuffer(int target, int framebuffer) {
        EXTFramebufferObject.glBindFramebufferEXT(target, framebuffer);
    }

    @Override
    public void glBindRenderbuffer(int target, int renderbuffer) {
        EXTFramebufferObject.glBindRenderbufferEXT(target, renderbuffer);
    }

    @Override
    public void glBindTexture(int target, int texture) {
        GL11.glBindTexture(target, texture);
    }

    @Override
    public void glBlendColor(float red, float green, float blue, float alpha) {
        GL14.glBlendColor(red, green, blue, alpha);
    }

    @Override
    public void glBlendEquation(int mode) {
        GL14.glBlendEquation(mode);
    }

    @Override
    public void glBlendEquationSeparate(int modeRGB, int modeAlpha) {
        GL20.glBlendEquationSeparate(modeRGB, modeAlpha);
    }

    @Override
    public void glBlendFunc(int sfactor, int dfactor) {
        GL11.glBlendFunc(sfactor, dfactor);
    }

    @Override
    public void glBlendFuncSeparate(int srcRGB, int dstRGB, int srcAlpha, int dstAlpha) {
        GL14.glBlendFuncSeparate(srcRGB, dstRGB, srcAlpha, dstAlpha);
    }

    @Override
    public void glBufferData(int target, int size, Buffer data, int usage) {
        if (data == null) {
            GL15.glBufferData(target, size, usage);
        } else if (data instanceof ByteBuffer) {
            GL15.glBufferData(target, (ByteBuffer)data, usage);
        } else if (data instanceof IntBuffer) {
            GL15.glBufferData(target, (IntBuffer)data, usage);
        } else if (data instanceof FloatBuffer) {
            GL15.glBufferData(target, (FloatBuffer)data, usage);
        } else if (data instanceof DoubleBuffer) {
            GL15.glBufferData(target, (DoubleBuffer)data, usage);
        } else if (data instanceof ShortBuffer) {
            GL15.glBufferData(target, (ShortBuffer)data, usage);
        }
    }

    @Override
    public void glBufferSubData(int target, int offset, int size, Buffer data) {
        if (data == null) {
            throw new GdxRuntimeException("Using null for the data not possible, blame LWJGL");
        }
        if (data instanceof ByteBuffer) {
            GL15.glBufferSubData(target, (long)offset, (ByteBuffer)data);
        } else if (data instanceof IntBuffer) {
            GL15.glBufferSubData(target, (long)offset, (IntBuffer)data);
        } else if (data instanceof FloatBuffer) {
            GL15.glBufferSubData(target, (long)offset, (FloatBuffer)data);
        } else if (data instanceof DoubleBuffer) {
            GL15.glBufferSubData(target, (long)offset, (DoubleBuffer)data);
        } else if (data instanceof ShortBuffer) {
            GL15.glBufferSubData(target, (long)offset, (ShortBuffer)data);
        }
    }

    @Override
    public int glCheckFramebufferStatus(int target) {
        return EXTFramebufferObject.glCheckFramebufferStatusEXT(target);
    }

    @Override
    public void glClear(int mask) {
        GL11.glClear(mask);
    }

    @Override
    public void glClearColor(float red, float green, float blue, float alpha) {
        GL11.glClearColor(red, green, blue, alpha);
    }

    @Override
    public void glClearDepthf(float depth) {
        GL11.glClearDepth(depth);
    }

    @Override
    public void glClearStencil(int s) {
        GL11.glClearStencil(s);
    }

    @Override
    public void glColorMask(boolean red, boolean green, boolean blue, boolean alpha) {
        GL11.glColorMask(red, green, blue, alpha);
    }

    @Override
    public void glCompileShader(int shader) {
        GL20.glCompileShader(shader);
    }

    @Override
    public void glCompressedTexImage2D(int target, int level, int internalformat, int width, int height, int border, int imageSize, Buffer data) {
        if (!(data instanceof ByteBuffer)) {
            throw new GdxRuntimeException("Can't use " + data.getClass().getName() + " with this method. Use ByteBuffer instead.");
        }
        GL13.glCompressedTexImage2D(target, level, internalformat, width, height, border, (ByteBuffer)data);
    }

    @Override
    public void glCompressedTexSubImage2D(int target, int level, int xoffset, int yoffset, int width, int height, int format, int imageSize, Buffer data) {
        throw new GdxRuntimeException("not implemented");
    }

    @Override
    public void glCopyTexImage2D(int target, int level, int internalformat, int x, int y, int width, int height, int border) {
        GL11.glCopyTexImage2D(target, level, internalformat, x, y, width, height, border);
    }

    @Override
    public void glCopyTexSubImage2D(int target, int level, int xoffset, int yoffset, int x, int y, int width, int height) {
        GL11.glCopyTexSubImage2D(target, level, xoffset, yoffset, x, y, width, height);
    }

    @Override
    public int glCreateProgram() {
        return GL20.glCreateProgram();
    }

    @Override
    public int glCreateShader(int type) {
        return GL20.glCreateShader(type);
    }

    @Override
    public void glCullFace(int mode) {
        GL11.glCullFace(mode);
    }

    @Override
    public void glDeleteBuffers(int n, IntBuffer buffers) {
        GL15.glDeleteBuffers(buffers);
    }

    @Override
    public void glDeleteBuffer(int buffer) {
        GL15.glDeleteBuffers(buffer);
    }

    @Override
    public void glDeleteFramebuffers(int n, IntBuffer framebuffers) {
        EXTFramebufferObject.glDeleteFramebuffersEXT(framebuffers);
    }

    @Override
    public void glDeleteFramebuffer(int framebuffer) {
        EXTFramebufferObject.glDeleteFramebuffersEXT(framebuffer);
    }

    @Override
    public void glDeleteProgram(int program) {
        GL20.glDeleteProgram(program);
    }

    @Override
    public void glDeleteRenderbuffers(int n, IntBuffer renderbuffers) {
        EXTFramebufferObject.glDeleteRenderbuffersEXT(renderbuffers);
    }

    @Override
    public void glDeleteRenderbuffer(int renderbuffer) {
        EXTFramebufferObject.glDeleteRenderbuffersEXT(renderbuffer);
    }

    @Override
    public void glDeleteShader(int shader) {
        GL20.glDeleteShader(shader);
    }

    @Override
    public void glDeleteTextures(int n, IntBuffer textures) {
        GL11.glDeleteTextures(textures);
    }

    @Override
    public void glDeleteTexture(int texture) {
        GL11.glDeleteTextures(texture);
    }

    @Override
    public void glDepthFunc(int func) {
        GL11.glDepthFunc(func);
    }

    @Override
    public void glDepthMask(boolean flag) {
        GL11.glDepthMask(flag);
    }

    @Override
    public void glDepthRangef(float zNear, float zFar) {
        GL11.glDepthRange(zNear, zFar);
    }

    @Override
    public void glDetachShader(int program, int shader) {
        GL20.glDetachShader(program, shader);
    }

    @Override
    public void glDisable(int cap) {
        GL11.glDisable(cap);
    }

    @Override
    public void glDisableVertexAttribArray(int index) {
        GL20.glDisableVertexAttribArray(index);
    }

    @Override
    public void glDrawArrays(int mode, int first, int count) {
        GL11.glDrawArrays(mode, first, count);
    }

    @Override
    public void glDrawElements(int mode, int count, int type, Buffer indices) {
        if (indices instanceof ShortBuffer && type == 5123) {
            GL11.glDrawElements(mode, (ShortBuffer)indices);
        } else if (indices instanceof ByteBuffer && type == 5123) {
            GL11.glDrawElements(mode, ((ByteBuffer)indices).asShortBuffer());
        } else if (indices instanceof ByteBuffer && type == 5121) {
            GL11.glDrawElements(mode, (ByteBuffer)indices);
        } else {
            throw new GdxRuntimeException("Can't use " + indices.getClass().getName() + " with this method. Use ShortBuffer or ByteBuffer instead. Blame LWJGL");
        }
    }

    @Override
    public void glEnable(int cap) {
        GL11.glEnable(cap);
    }

    @Override
    public void glEnableVertexAttribArray(int index) {
        GL20.glEnableVertexAttribArray(index);
    }

    @Override
    public void glFinish() {
        GL11.glFinish();
    }

    @Override
    public void glFlush() {
        GL11.glFlush();
    }

    @Override
    public void glFramebufferRenderbuffer(int target, int attachment, int renderbuffertarget, int renderbuffer) {
        EXTFramebufferObject.glFramebufferRenderbufferEXT(target, attachment, renderbuffertarget, renderbuffer);
    }

    @Override
    public void glFramebufferTexture2D(int target, int attachment, int textarget, int texture, int level) {
        EXTFramebufferObject.glFramebufferTexture2DEXT(target, attachment, textarget, texture, level);
    }

    @Override
    public void glFrontFace(int mode) {
        GL11.glFrontFace(mode);
    }

    @Override
    public void glGenBuffers(int n, IntBuffer buffers) {
        GL15.glGenBuffers(buffers);
    }

    @Override
    public int glGenBuffer() {
        return GL15.glGenBuffers();
    }

    @Override
    public void glGenFramebuffers(int n, IntBuffer framebuffers) {
        EXTFramebufferObject.glGenFramebuffersEXT(framebuffers);
    }

    @Override
    public int glGenFramebuffer() {
        return EXTFramebufferObject.glGenFramebuffersEXT();
    }

    @Override
    public void glGenRenderbuffers(int n, IntBuffer renderbuffers) {
        EXTFramebufferObject.glGenRenderbuffersEXT(renderbuffers);
    }

    @Override
    public int glGenRenderbuffer() {
        return EXTFramebufferObject.glGenRenderbuffersEXT();
    }

    @Override
    public void glGenTextures(int n, IntBuffer textures) {
        GL11.glGenTextures(textures);
    }

    @Override
    public int glGenTexture() {
        return GL11.glGenTextures();
    }

    @Override
    public void glGenerateMipmap(int target) {
        EXTFramebufferObject.glGenerateMipmapEXT(target);
    }

    @Override
    public String glGetActiveAttrib(int program, int index, IntBuffer size, Buffer type) {
        IntBuffer typeTmp = org.lwjgl.BufferUtils.createIntBuffer(2);
        String name = GL20.glGetActiveAttrib(program, index, 256, typeTmp);
        size.put(typeTmp.get(0));
        if (type instanceof IntBuffer) {
            ((IntBuffer)type).put(typeTmp.get(1));
        }
        return name;
    }

    @Override
    public String glGetActiveUniform(int program, int index, IntBuffer size, Buffer type) {
        IntBuffer typeTmp = org.lwjgl.BufferUtils.createIntBuffer(2);
        String name = GL20.glGetActiveUniform(program, index, 256, typeTmp);
        size.put(typeTmp.get(0));
        if (type instanceof IntBuffer) {
            ((IntBuffer)type).put(typeTmp.get(1));
        }
        return name;
    }

    @Override
    public void glGetAttachedShaders(int program, int maxcount, Buffer count, IntBuffer shaders) {
        GL20.glGetAttachedShaders(program, (IntBuffer)count, shaders);
    }

    @Override
    public int glGetAttribLocation(int program, String name) {
        return GL20.glGetAttribLocation(program, name);
    }

    @Override
    public void glGetBooleanv(int pname, Buffer params) {
        GL11.glGetBoolean(pname, (ByteBuffer)params);
    }

    @Override
    public void glGetBufferParameteriv(int target, int pname, IntBuffer params) {
        GL15.glGetBufferParameter(target, pname, params);
    }

    @Override
    public int glGetError() {
        return GL11.glGetError();
    }

    @Override
    public void glGetFloatv(int pname, FloatBuffer params) {
        GL11.glGetFloat(pname, params);
    }

    @Override
    public void glGetFramebufferAttachmentParameteriv(int target, int attachment, int pname, IntBuffer params) {
        EXTFramebufferObject.glGetFramebufferAttachmentParameterEXT(target, attachment, pname, params);
    }

    @Override
    public void glGetIntegerv(int pname, IntBuffer params) {
        GL11.glGetInteger(pname, params);
    }

    @Override
    public String glGetProgramInfoLog(int program) {
        ByteBuffer buffer = ByteBuffer.allocateDirect(10240);
        buffer.order(ByteOrder.nativeOrder());
        ByteBuffer tmp = ByteBuffer.allocateDirect(4);
        tmp.order(ByteOrder.nativeOrder());
        IntBuffer intBuffer = tmp.asIntBuffer();
        GL20.glGetProgramInfoLog(program, intBuffer, buffer);
        int numBytes = intBuffer.get(0);
        byte[] bytes = new byte[numBytes];
        buffer.get(bytes);
        return new String(bytes);
    }

    @Override
    public void glGetProgramiv(int program, int pname, IntBuffer params) {
        GL20.glGetProgram(program, pname, params);
    }

    @Override
    public void glGetRenderbufferParameteriv(int target, int pname, IntBuffer params) {
        EXTFramebufferObject.glGetRenderbufferParameterEXT(target, pname, params);
    }

    @Override
    public String glGetShaderInfoLog(int shader) {
        ByteBuffer buffer = ByteBuffer.allocateDirect(10240);
        buffer.order(ByteOrder.nativeOrder());
        ByteBuffer tmp = ByteBuffer.allocateDirect(4);
        tmp.order(ByteOrder.nativeOrder());
        IntBuffer intBuffer = tmp.asIntBuffer();
        GL20.glGetShaderInfoLog(shader, intBuffer, buffer);
        int numBytes = intBuffer.get(0);
        byte[] bytes = new byte[numBytes];
        buffer.get(bytes);
        return new String(bytes);
    }

    @Override
    public void glGetShaderPrecisionFormat(int shadertype, int precisiontype, IntBuffer range, IntBuffer precision) {
        throw new UnsupportedOperationException("unsupported, won't implement");
    }

    @Override
    public void glGetShaderiv(int shader, int pname, IntBuffer params) {
        GL20.glGetShader(shader, pname, params);
    }

    @Override
    public String glGetString(int name) {
        return GL11.glGetString(name);
    }

    @Override
    public void glGetTexParameterfv(int target, int pname, FloatBuffer params) {
        GL11.glGetTexParameter(target, pname, params);
    }

    @Override
    public void glGetTexParameteriv(int target, int pname, IntBuffer params) {
        GL11.glGetTexParameter(target, pname, params);
    }

    @Override
    public int glGetUniformLocation(int program, String name) {
        return GL20.glGetUniformLocation(program, name);
    }

    @Override
    public void glGetUniformfv(int program, int location, FloatBuffer params) {
        GL20.glGetUniform(program, location, params);
    }

    @Override
    public void glGetUniformiv(int program, int location, IntBuffer params) {
        GL20.glGetUniform(program, location, params);
    }

    @Override
    public void glGetVertexAttribPointerv(int index, int pname, Buffer pointer) {
        throw new UnsupportedOperationException("unsupported, won't implement");
    }

    @Override
    public void glGetVertexAttribfv(int index, int pname, FloatBuffer params) {
        GL20.glGetVertexAttrib(index, pname, params);
    }

    @Override
    public void glGetVertexAttribiv(int index, int pname, IntBuffer params) {
        GL20.glGetVertexAttrib(index, pname, params);
    }

    @Override
    public void glHint(int target, int mode) {
        GL11.glHint(target, mode);
    }

    @Override
    public boolean glIsBuffer(int buffer) {
        return GL15.glIsBuffer(buffer);
    }

    @Override
    public boolean glIsEnabled(int cap) {
        return GL11.glIsEnabled(cap);
    }

    @Override
    public boolean glIsFramebuffer(int framebuffer) {
        return EXTFramebufferObject.glIsFramebufferEXT(framebuffer);
    }

    @Override
    public boolean glIsProgram(int program) {
        return GL20.glIsProgram(program);
    }

    @Override
    public boolean glIsRenderbuffer(int renderbuffer) {
        return EXTFramebufferObject.glIsRenderbufferEXT(renderbuffer);
    }

    @Override
    public boolean glIsShader(int shader) {
        return GL20.glIsShader(shader);
    }

    @Override
    public boolean glIsTexture(int texture) {
        return GL11.glIsTexture(texture);
    }

    @Override
    public void glLineWidth(float width) {
        GL11.glLineWidth(width);
    }

    @Override
    public void glLinkProgram(int program) {
        GL20.glLinkProgram(program);
    }

    @Override
    public void glPixelStorei(int pname, int param) {
        GL11.glPixelStorei(pname, param);
    }

    @Override
    public void glPolygonOffset(float factor, float units) {
        GL11.glPolygonOffset(factor, units);
    }

    @Override
    public void glReadPixels(int x, int y, int width, int height, int format, int type, Buffer pixels) {
        if (pixels instanceof ByteBuffer) {
            GL11.glReadPixels(x, y, width, height, format, type, (ByteBuffer)pixels);
        } else if (pixels instanceof ShortBuffer) {
            GL11.glReadPixels(x, y, width, height, format, type, (ShortBuffer)pixels);
        } else if (pixels instanceof IntBuffer) {
            GL11.glReadPixels(x, y, width, height, format, type, (IntBuffer)pixels);
        } else if (pixels instanceof FloatBuffer) {
            GL11.glReadPixels(x, y, width, height, format, type, (FloatBuffer)pixels);
        } else {
            throw new GdxRuntimeException("Can't use " + pixels.getClass().getName() + " with this method. Use ByteBuffer, ShortBuffer, IntBuffer or FloatBuffer instead. Blame LWJGL");
        }
    }

    @Override
    public void glReleaseShaderCompiler() {
    }

    @Override
    public void glRenderbufferStorage(int target, int internalformat, int width, int height) {
        EXTFramebufferObject.glRenderbufferStorageEXT(target, internalformat, width, height);
    }

    @Override
    public void glSampleCoverage(float value, boolean invert) {
        GL13.glSampleCoverage(value, invert);
    }

    @Override
    public void glScissor(int x, int y, int width, int height) {
        GL11.glScissor(x, y, width, height);
    }

    @Override
    public void glShaderBinary(int n, IntBuffer shaders, int binaryformat, Buffer binary, int length) {
        throw new UnsupportedOperationException("unsupported, won't implement");
    }

    @Override
    public void glShaderSource(int shader, String string) {
        GL20.glShaderSource(shader, string);
    }

    @Override
    public void glStencilFunc(int func, int ref, int mask) {
        GL11.glStencilFunc(func, ref, mask);
    }

    @Override
    public void glStencilFuncSeparate(int face, int func, int ref, int mask) {
        GL20.glStencilFuncSeparate(face, func, ref, mask);
    }

    @Override
    public void glStencilMask(int mask) {
        GL11.glStencilMask(mask);
    }

    @Override
    public void glStencilMaskSeparate(int face, int mask) {
        GL20.glStencilMaskSeparate(face, mask);
    }

    @Override
    public void glStencilOp(int fail, int zfail, int zpass) {
        GL11.glStencilOp(fail, zfail, zpass);
    }

    @Override
    public void glStencilOpSeparate(int face, int fail, int zfail, int zpass) {
        GL20.glStencilOpSeparate(face, fail, zfail, zpass);
    }

    @Override
    public void glTexImage2D(int target, int level, int internalformat, int width, int height, int border, int format, int type, Buffer pixels) {
        if (pixels == null) {
            GL11.glTexImage2D(target, level, internalformat, width, height, border, format, type, (ByteBuffer)null);
        } else if (pixels instanceof ByteBuffer) {
            GL11.glTexImage2D(target, level, internalformat, width, height, border, format, type, (ByteBuffer)pixels);
        } else if (pixels instanceof ShortBuffer) {
            GL11.glTexImage2D(target, level, internalformat, width, height, border, format, type, (ShortBuffer)pixels);
        } else if (pixels instanceof IntBuffer) {
            GL11.glTexImage2D(target, level, internalformat, width, height, border, format, type, (IntBuffer)pixels);
        } else if (pixels instanceof FloatBuffer) {
            GL11.glTexImage2D(target, level, internalformat, width, height, border, format, type, (FloatBuffer)pixels);
        } else if (pixels instanceof DoubleBuffer) {
            GL11.glTexImage2D(target, level, internalformat, width, height, border, format, type, (DoubleBuffer)pixels);
        } else {
            throw new GdxRuntimeException("Can't use " + pixels.getClass().getName() + " with this method. Use ByteBuffer, ShortBuffer, IntBuffer, FloatBuffer or DoubleBuffer instead. Blame LWJGL");
        }
    }

    @Override
    public void glTexParameterf(int target, int pname, float param) {
        GL11.glTexParameterf(target, pname, param);
    }

    @Override
    public void glTexParameterfv(int target, int pname, FloatBuffer params) {
        GL11.glTexParameter(target, pname, params);
    }

    @Override
    public void glTexParameteri(int target, int pname, int param) {
        GL11.glTexParameteri(target, pname, param);
    }

    @Override
    public void glTexParameteriv(int target, int pname, IntBuffer params) {
        GL11.glTexParameter(target, pname, params);
    }

    @Override
    public void glTexSubImage2D(int target, int level, int xoffset, int yoffset, int width, int height, int format, int type, Buffer pixels) {
        if (pixels instanceof ByteBuffer) {
            GL11.glTexSubImage2D(target, level, xoffset, yoffset, width, height, format, type, (ByteBuffer)pixels);
        } else if (pixels instanceof ShortBuffer) {
            GL11.glTexSubImage2D(target, level, xoffset, yoffset, width, height, format, type, (ShortBuffer)pixels);
        } else if (pixels instanceof IntBuffer) {
            GL11.glTexSubImage2D(target, level, xoffset, yoffset, width, height, format, type, (IntBuffer)pixels);
        } else if (pixels instanceof FloatBuffer) {
            GL11.glTexSubImage2D(target, level, xoffset, yoffset, width, height, format, type, (FloatBuffer)pixels);
        } else if (pixels instanceof DoubleBuffer) {
            GL11.glTexSubImage2D(target, level, xoffset, yoffset, width, height, format, type, (DoubleBuffer)pixels);
        } else {
            throw new GdxRuntimeException("Can't use " + pixels.getClass().getName() + " with this method. Use ByteBuffer, ShortBuffer, IntBuffer, FloatBuffer or DoubleBuffer instead. Blame LWJGL");
        }
    }

    @Override
    public void glUniform1f(int location, float x) {
        GL20.glUniform1f(location, x);
    }

    @Override
    public void glUniform1fv(int location, int count, FloatBuffer v) {
        GL20.glUniform1(location, v);
    }

    @Override
    public void glUniform1fv(int location, int count, float[] v, int offset) {
        GL20.glUniform1(location, this.toFloatBuffer(v, offset, count));
    }

    @Override
    public void glUniform1i(int location, int x) {
        GL20.glUniform1i(location, x);
    }

    @Override
    public void glUniform1iv(int location, int count, IntBuffer v) {
        GL20.glUniform1(location, v);
    }

    @Override
    public void glUniform1iv(int location, int count, int[] v, int offset) {
        GL20.glUniform1(location, this.toIntBuffer(v, offset, count));
    }

    @Override
    public void glUniform2f(int location, float x, float y) {
        GL20.glUniform2f(location, x, y);
    }

    @Override
    public void glUniform2fv(int location, int count, FloatBuffer v) {
        GL20.glUniform2(location, v);
    }

    @Override
    public void glUniform2fv(int location, int count, float[] v, int offset) {
        GL20.glUniform2(location, this.toFloatBuffer(v, offset, count << 1));
    }

    @Override
    public void glUniform2i(int location, int x, int y) {
        GL20.glUniform2i(location, x, y);
    }

    @Override
    public void glUniform2iv(int location, int count, IntBuffer v) {
        GL20.glUniform2(location, v);
    }

    @Override
    public void glUniform2iv(int location, int count, int[] v, int offset) {
        GL20.glUniform2(location, this.toIntBuffer(v, offset, count << 1));
    }

    @Override
    public void glUniform3f(int location, float x, float y, float z) {
        GL20.glUniform3f(location, x, y, z);
    }

    @Override
    public void glUniform3fv(int location, int count, FloatBuffer v) {
        GL20.glUniform3(location, v);
    }

    @Override
    public void glUniform3fv(int location, int count, float[] v, int offset) {
        GL20.glUniform3(location, this.toFloatBuffer(v, offset, count * 3));
    }

    @Override
    public void glUniform3i(int location, int x, int y, int z) {
        GL20.glUniform3i(location, x, y, z);
    }

    @Override
    public void glUniform3iv(int location, int count, IntBuffer v) {
        GL20.glUniform3(location, v);
    }

    @Override
    public void glUniform3iv(int location, int count, int[] v, int offset) {
        GL20.glUniform3(location, this.toIntBuffer(v, offset, count * 3));
    }

    @Override
    public void glUniform4f(int location, float x, float y, float z, float w) {
        GL20.glUniform4f(location, x, y, z, w);
    }

    @Override
    public void glUniform4fv(int location, int count, FloatBuffer v) {
        GL20.glUniform4(location, v);
    }

    @Override
    public void glUniform4fv(int location, int count, float[] v, int offset) {
        GL20.glUniform4(location, this.toFloatBuffer(v, offset, count << 2));
    }

    @Override
    public void glUniform4i(int location, int x, int y, int z, int w) {
        GL20.glUniform4i(location, x, y, z, w);
    }

    @Override
    public void glUniform4iv(int location, int count, IntBuffer v) {
        GL20.glUniform4(location, v);
    }

    @Override
    public void glUniform4iv(int location, int count, int[] v, int offset) {
        GL20.glUniform4(location, this.toIntBuffer(v, offset, count << 2));
    }

    @Override
    public void glUniformMatrix2fv(int location, int count, boolean transpose, FloatBuffer value) {
        GL20.glUniformMatrix2(location, transpose, value);
    }

    @Override
    public void glUniformMatrix2fv(int location, int count, boolean transpose, float[] value, int offset) {
        GL20.glUniformMatrix2(location, transpose, this.toFloatBuffer(value, offset, count << 2));
    }

    @Override
    public void glUniformMatrix3fv(int location, int count, boolean transpose, FloatBuffer value) {
        GL20.glUniformMatrix3(location, transpose, value);
    }

    @Override
    public void glUniformMatrix3fv(int location, int count, boolean transpose, float[] value, int offset) {
        GL20.glUniformMatrix3(location, transpose, this.toFloatBuffer(value, offset, count * 9));
    }

    @Override
    public void glUniformMatrix4fv(int location, int count, boolean transpose, FloatBuffer value) {
        GL20.glUniformMatrix4(location, transpose, value);
    }

    @Override
    public void glUniformMatrix4fv(int location, int count, boolean transpose, float[] value, int offset) {
        GL20.glUniformMatrix4(location, transpose, this.toFloatBuffer(value, offset, count << 4));
    }

    @Override
    public void glUseProgram(int program) {
        GL20.glUseProgram(program);
    }

    @Override
    public void glValidateProgram(int program) {
        GL20.glValidateProgram(program);
    }

    @Override
    public void glVertexAttrib1f(int indx, float x) {
        GL20.glVertexAttrib1f(indx, x);
    }

    @Override
    public void glVertexAttrib1fv(int indx, FloatBuffer values) {
        GL20.glVertexAttrib1f(indx, values.get());
    }

    @Override
    public void glVertexAttrib2f(int indx, float x, float y) {
        GL20.glVertexAttrib2f(indx, x, y);
    }

    @Override
    public void glVertexAttrib2fv(int indx, FloatBuffer values) {
        GL20.glVertexAttrib2f(indx, values.get(), values.get());
    }

    @Override
    public void glVertexAttrib3f(int indx, float x, float y, float z) {
        GL20.glVertexAttrib3f(indx, x, y, z);
    }

    @Override
    public void glVertexAttrib3fv(int indx, FloatBuffer values) {
        GL20.glVertexAttrib3f(indx, values.get(), values.get(), values.get());
    }

    @Override
    public void glVertexAttrib4f(int indx, float x, float y, float z, float w) {
        GL20.glVertexAttrib4f(indx, x, y, z, w);
    }

    @Override
    public void glVertexAttrib4fv(int indx, FloatBuffer values) {
        GL20.glVertexAttrib4f(indx, values.get(), values.get(), values.get(), values.get());
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public void glVertexAttribPointer(int indx, int size, int type, boolean normalized, int stride, Buffer buffer) {
        if (buffer instanceof ByteBuffer) {
            if (type == 5120) {
                GL20.glVertexAttribPointer(indx, size, false, normalized, stride, (ByteBuffer)buffer);
                return;
            } else if (type == 5121) {
                GL20.glVertexAttribPointer(indx, size, true, normalized, stride, (ByteBuffer)buffer);
                return;
            } else if (type == 5122) {
                GL20.glVertexAttribPointer(indx, size, false, normalized, stride, ((ByteBuffer)buffer).asShortBuffer());
                return;
            } else if (type == 5123) {
                GL20.glVertexAttribPointer(indx, size, true, normalized, stride, ((ByteBuffer)buffer).asShortBuffer());
                return;
            } else {
                if (type != 5126) throw new GdxRuntimeException("Can't use " + buffer.getClass().getName() + " with type " + type + " with this method. Use ByteBuffer and one of GL_BYTE, GL_UNSIGNED_BYTE, GL_SHORT, GL_UNSIGNED_SHORT or GL_FLOAT for type. Blame LWJGL");
                GL20.glVertexAttribPointer(indx, size, normalized, stride, ((ByteBuffer)buffer).asFloatBuffer());
            }
            return;
        } else {
            if (!(buffer instanceof FloatBuffer)) throw new GdxRuntimeException("Can't use " + buffer.getClass().getName() + " with this method. Use ByteBuffer instead. Blame LWJGL");
            if (type != 5126) throw new GdxRuntimeException("Can't use " + buffer.getClass().getName() + " with type " + type + " with this method.");
            GL20.glVertexAttribPointer(indx, size, normalized, stride, (FloatBuffer)buffer);
        }
    }

    @Override
    public void glViewport(int x, int y, int width, int height) {
        GL11.glViewport(x, y, width, height);
    }

    @Override
    public void glDrawElements(int mode, int count, int type, int indices) {
        GL11.glDrawElements(mode, count, type, indices);
    }

    @Override
    public void glVertexAttribPointer(int indx, int size, int type, boolean normalized, int stride, int ptr) {
        GL20.glVertexAttribPointer(indx, size, type, normalized, stride, ptr);
    }
}

