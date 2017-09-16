/*
 * Decompiled with CFR 0_122.
 */
package com.badlogic.gdx.utils;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.SerializationException;
import com.badlogic.gdx.utils.StreamUtils;
import com.badlogic.gdx.utils.StringBuilder;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

public class XmlReader {
    private final Array<Element> elements = new Array(8);
    private Element root;
    private Element current;
    private final StringBuilder textBuffer = new StringBuilder(64);
    private static final byte[] _xml_actions = XmlReader.init__xml_actions_0();
    private static final byte[] _xml_key_offsets = XmlReader.init__xml_key_offsets_0();
    private static final char[] _xml_trans_keys = XmlReader.init__xml_trans_keys_0();
    private static final byte[] _xml_single_lengths = XmlReader.init__xml_single_lengths_0();
    private static final byte[] _xml_range_lengths = XmlReader.init__xml_range_lengths_0();
    private static final short[] _xml_index_offsets = XmlReader.init__xml_index_offsets_0();
    private static final byte[] _xml_indicies = XmlReader.init__xml_indicies_0();
    private static final byte[] _xml_trans_targs = XmlReader.init__xml_trans_targs_0();
    private static final byte[] _xml_trans_actions = XmlReader.init__xml_trans_actions_0();
    static final int xml_start = 1;
    static final int xml_first_final = 34;
    static final int xml_error = 0;
    static final int xml_en_elementBody = 15;
    static final int xml_en_main = 1;

    public Element parse(String xml) {
        char[] data = xml.toCharArray();
        return this.parse(data, 0, data.length);
    }

    public Element parse(Reader reader) throws IOException {
        try {
            int length22;
            char[] data = new char[1024];
            int offset = 0;
            while ((length22 = reader.read(data, offset, data.length - offset)) != -1) {
                if (length22 == 0) {
                    char[] newData = new char[data.length * 2];
                    System.arraycopy(data, 0, newData, 0, data.length);
                    data = newData;
                    continue;
                }
                offset += length22;
            }
            Element length22 = this.parse(data, 0, offset);
            return length22;
        }
        catch (IOException ex) {
            throw new SerializationException(ex);
        }
        finally {
            StreamUtils.closeQuietly(reader);
        }
    }

    public Element parse(InputStream input) throws IOException {
        try {
            Element element = this.parse(new InputStreamReader(input, "UTF-8"));
            return element;
        }
        catch (IOException ex) {
            throw new SerializationException(ex);
        }
        finally {
            StreamUtils.closeQuietly(input);
        }
    }

    public Element parse(FileHandle file) throws IOException {
        try {
            return this.parse(file.reader("UTF-8"));
        }
        catch (Exception ex) {
            throw new SerializationException("Error parsing file: " + file, ex);
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public Element parse(char[] data, int offset, int length) {
        p = offset;
        pe = length;
        s = 0;
        attributeName = null;
        hasBody = false;
        cs = 1;
        _trans = 0;
        _goto_targ = 0;
        block18 : do {
            switch (_goto_targ) {
                case 0: {
                    if (p == pe) {
                        _goto_targ = 4;
                        continue block18;
                    }
                    if (cs == 0) {
                        _goto_targ = 5;
                        continue block18;
                    }
                }
                case 1: {
                    _keys = XmlReader._xml_key_offsets[cs];
                    _trans = XmlReader._xml_index_offsets[cs];
                    _klen = XmlReader._xml_single_lengths[cs];
                    if (_klen <= 0) ** GOTO lbl39
                    _lower = _keys;
                    _upper = _keys + _klen - 1;
                    do {
                        if (_upper >= _lower) ** GOTO lbl30
                        _keys += _klen;
                        _trans += _klen;
                        ** GOTO lbl39
lbl30: // 1 sources:
                        _mid = _lower + (_upper - _lower >> 1);
                        if (data[p] < XmlReader._xml_trans_keys[_mid]) {
                            _upper = _mid - 1;
                            continue;
                        }
                        if (data[p] <= XmlReader._xml_trans_keys[_mid]) break;
                        _lower = _mid + 1;
                    } while (true);
                    _trans += _mid - _keys;
                    ** GOTO lbl54
lbl39: // 2 sources:
                    if ((_klen = XmlReader._xml_range_lengths[cs]) <= 0) ** GOTO lbl54
                    _lower = _keys;
                    _upper = _keys + (_klen << 1) - 2;
                    do {
                        if (_upper >= _lower) ** GOTO lbl46
                        _trans += _klen;
                        ** GOTO lbl54
lbl46: // 1 sources:
                        _mid = _lower + (_upper - _lower >> 1 & -2);
                        if (data[p] < XmlReader._xml_trans_keys[_mid]) {
                            _upper = _mid - 2;
                            continue;
                        }
                        if (data[p] <= XmlReader._xml_trans_keys[_mid + 1]) break;
                        _lower = _mid + 2;
                    } while (true);
                    _trans += _mid - _keys >> 1;
lbl54: // 4 sources:
                    _trans = XmlReader._xml_indicies[_trans];
                    cs = XmlReader._xml_trans_targs[_trans];
                    if (XmlReader._xml_trans_actions[_trans] != 0) {
                        _acts = XmlReader._xml_trans_actions[_trans];
                        _nacts = XmlReader._xml_actions[_acts++];
                        while (_nacts-- > 0) {
                            switch (XmlReader._xml_actions[_acts++]) {
                                case 0: {
                                    s = p;
                                    break;
                                }
                                case 1: {
                                    c = data[s];
                                    if (c == '?' || c == '!') {
                                        if (data[s + 1] == '[' && data[s + 2] == 'C' && data[s + 3] == 'D' && data[s + 4] == 'A' && data[s + 5] == 'T' && data[s + 6] == 'A' && data[s + 7] == '[') {
                                            p = (s += 8) + 2;
                                            while (data[p - 2] != ']' || data[p - 1] != ']' || data[p] != '>') {
                                                ++p;
                                            }
                                            this.text(new String(data, s, p - s - 2));
                                        } else if (c == '!' && data[s + 1] == '-' && data[s + 2] == '-') {
                                            p = s + 3;
                                            while (data[p] != '-' || data[p + 1] != '-' || data[p + 2] != '>') {
                                                ++p;
                                            }
                                            p += 2;
                                        } else {
                                            while (data[p] != '>') {
                                                ++p;
                                            }
                                        }
                                        cs = 15;
                                        _goto_targ = 2;
                                        continue block18;
                                    }
                                    hasBody = true;
                                    this.open(new String(data, s, p - s));
                                    break;
                                }
                                case 2: {
                                    hasBody = false;
                                    this.close();
                                    cs = 15;
                                    _goto_targ = 2;
                                    continue block18;
                                }
                                case 3: {
                                    this.close();
                                    cs = 15;
                                    _goto_targ = 2;
                                    continue block18;
                                }
                                case 4: {
                                    if (!hasBody) break;
                                    cs = 15;
                                    _goto_targ = 2;
                                    continue block18;
                                }
                                case 5: {
                                    attributeName = new String(data, s, p - s);
                                    break;
                                }
                                case 6: {
                                    this.attribute(attributeName, new String(data, s, p - s));
                                    break;
                                }
                                case 7: {
                                    block25 : for (end = p; end != s; --end) {
                                        switch (data[end - 1]) {
                                            case '\t': 
                                            case '\n': 
                                            case '\r': 
                                            case ' ': {
                                                continue block25;
                                            }
                                            default: {
                                                break block25;
                                            }
                                        }
                                    }
                                    current = s;
                                    entityFound = false;
                                    block26 : while (current != end) {
                                        if (data[current++] != '&') continue;
                                        entityStart = current;
                                        while (current != end) {
                                            if (data[current++] != ';') continue;
                                            this.textBuffer.append(data, s, entityStart - s - 1);
                                            name = new String(data, entityStart, current - entityStart - 1);
                                            value = this.entity(name);
                                            this.textBuffer.append(value != null ? value : name);
                                            s = current;
                                            entityFound = true;
                                            continue block26;
                                        }
                                    }
                                    this.text(new String(data, s, end - s));
                                }
                            }
                        }
                    }
                }
                case 2: {
                    if (cs == 0) {
                        _goto_targ = 5;
                        continue block18;
                    }
                    if (++p == pe) break block18;
                    _goto_targ = 1;
                    continue block18;
                }
            }
            break;
        } while (true);
        if (p < pe) {
            lineNumber = 1;
            i = 0;
            while (i < p) {
                if (data[i] == '\n') {
                    ++lineNumber;
                }
                ++i;
            }
            throw new SerializationException("Error parsing XML on line " + lineNumber + " near: " + new String(data, p, Math.min(32, pe - p)));
        }
        if (this.elements.size != 0) {
            element = this.elements.peek();
            this.elements.clear();
            throw new SerializationException("Error parsing XML, unclosed element: " + element.getName());
        }
        root = this.root;
        this.root = null;
        return root;
    }

    private static byte[] init__xml_actions_0() {
        return new byte[]{0, 1, 0, 1, 1, 1, 2, 1, 3, 1, 4, 1, 5, 1, 6, 1, 7, 2, 0, 6, 2, 1, 4, 2, 2, 4};
    }

    private static byte[] init__xml_key_offsets_0() {
        return new byte[]{0, 0, 4, 9, 14, 20, 26, 30, 35, 36, 37, 42, 46, 50, 51, 52, 56, 57, 62, 67, 73, 79, 83, 88, 89, 90, 95, 99, 103, 104, 108, 109, 110, 111, 112, 115};
    }

    private static char[] init__xml_trans_keys_0() {
        return new char[]{' ', '<', '\t', '\r', ' ', '/', '>', '\t', '\r', ' ', '/', '>', '\t', '\r', ' ', '/', '=', '>', '\t', '\r', ' ', '/', '=', '>', '\t', '\r', ' ', '=', '\t', '\r', ' ', '\"', '\'', '\t', '\r', '\"', '\"', ' ', '/', '>', '\t', '\r', ' ', '>', '\t', '\r', ' ', '>', '\t', '\r', '\'', '\'', ' ', '<', '\t', '\r', '<', ' ', '/', '>', '\t', '\r', ' ', '/', '>', '\t', '\r', ' ', '/', '=', '>', '\t', '\r', ' ', '/', '=', '>', '\t', '\r', ' ', '=', '\t', '\r', ' ', '\"', '\'', '\t', '\r', '\"', '\"', ' ', '/', '>', '\t', '\r', ' ', '>', '\t', '\r', ' ', '>', '\t', '\r', '<', ' ', '/', '\t', '\r', '>', '>', '\'', '\'', ' ', '\t', '\r', '\u0000'};
    }

    private static byte[] init__xml_single_lengths_0() {
        return new byte[]{0, 2, 3, 3, 4, 4, 2, 3, 1, 1, 3, 2, 2, 1, 1, 2, 1, 3, 3, 4, 4, 2, 3, 1, 1, 3, 2, 2, 1, 2, 1, 1, 1, 1, 1, 0};
    }

    private static byte[] init__xml_range_lengths_0() {
        return new byte[]{0, 1, 1, 1, 1, 1, 1, 1, 0, 0, 1, 1, 1, 0, 0, 1, 0, 1, 1, 1, 1, 1, 1, 0, 0, 1, 1, 1, 0, 1, 0, 0, 0, 0, 1, 0};
    }

    private static short[] init__xml_index_offsets_0() {
        return new short[]{0, 0, 4, 9, 14, 20, 26, 30, 35, 37, 39, 44, 48, 52, 54, 56, 60, 62, 67, 72, 78, 84, 88, 93, 95, 97, 102, 106, 110, 112, 116, 118, 120, 122, 124, 127};
    }

    private static byte[] init__xml_indicies_0() {
        return new byte[]{0, 2, 0, 1, 2, 1, 1, 2, 3, 5, 6, 7, 5, 4, 9, 10, 1, 11, 9, 8, 13, 1, 14, 1, 13, 12, 15, 16, 15, 1, 16, 17, 18, 16, 1, 20, 19, 22, 21, 9, 10, 11, 9, 1, 23, 24, 23, 1, 25, 11, 25, 1, 20, 26, 22, 27, 29, 30, 29, 28, 32, 31, 30, 34, 1, 30, 33, 36, 37, 38, 36, 35, 40, 41, 1, 42, 40, 39, 44, 1, 45, 1, 44, 43, 46, 47, 46, 1, 47, 48, 49, 47, 1, 51, 50, 53, 52, 40, 41, 42, 40, 1, 54, 55, 54, 1, 56, 42, 56, 1, 57, 1, 57, 34, 57, 1, 1, 58, 59, 58, 51, 60, 53, 61, 62, 62, 1, 1, 0};
    }

    private static byte[] init__xml_trans_targs_0() {
        return new byte[]{1, 0, 2, 3, 3, 4, 11, 34, 5, 4, 11, 34, 5, 6, 7, 6, 7, 8, 13, 9, 10, 9, 10, 12, 34, 12, 14, 14, 16, 15, 17, 16, 17, 18, 30, 18, 19, 26, 28, 20, 19, 26, 28, 20, 21, 22, 21, 22, 23, 32, 24, 25, 24, 25, 27, 28, 27, 29, 31, 35, 33, 33, 34};
    }

    private static byte[] init__xml_trans_actions_0() {
        return new byte[]{0, 0, 0, 1, 0, 3, 3, 20, 1, 0, 0, 9, 0, 11, 11, 0, 0, 0, 0, 1, 17, 0, 13, 5, 23, 0, 1, 0, 1, 0, 0, 0, 15, 1, 0, 0, 3, 3, 20, 1, 0, 0, 9, 0, 11, 11, 0, 0, 0, 0, 1, 17, 0, 13, 5, 23, 0, 0, 0, 7, 1, 0, 0};
    }

    protected void open(String name) {
        Element child = new Element(name, this.current);
        Element parent = this.current;
        if (parent != null) {
            parent.addChild(child);
        }
        this.elements.add(child);
        this.current = child;
    }

    protected void attribute(String name, String value) {
        this.current.setAttribute(name, value);
    }

    protected String entity(String name) {
        if (name.equals("lt")) {
            return "<";
        }
        if (name.equals("gt")) {
            return ">";
        }
        if (name.equals("amp")) {
            return "&";
        }
        if (name.equals("apos")) {
            return "'";
        }
        if (name.equals("quot")) {
            return "\"";
        }
        if (name.startsWith("#x")) {
            return Character.toString((char)Integer.parseInt(name.substring(2), 16));
        }
        return null;
    }

    protected void text(String text) {
        String existing = this.current.getText();
        this.current.setText(existing != null ? existing + text : text);
    }

    protected void close() {
        this.root = this.elements.pop();
        this.current = this.elements.size > 0 ? this.elements.peek() : null;
    }

    public static class Element {
        private final String name;
        private ObjectMap<String, String> attributes;
        private Array<Element> children;
        private String text;
        private Element parent;

        public Element(String name, Element parent) {
            this.name = name;
            this.parent = parent;
        }

        public String getName() {
            return this.name;
        }

        public ObjectMap<String, String> getAttributes() {
            return this.attributes;
        }

        public String getAttribute(String name) {
            if (this.attributes == null) {
                throw new GdxRuntimeException("Element " + name + " doesn't have attribute: " + name);
            }
            String value = this.attributes.get(name);
            if (value == null) {
                throw new GdxRuntimeException("Element " + name + " doesn't have attribute: " + name);
            }
            return value;
        }

        public String getAttribute(String name, String defaultValue) {
            if (this.attributes == null) {
                return defaultValue;
            }
            String value = this.attributes.get(name);
            if (value == null) {
                return defaultValue;
            }
            return value;
        }

        public void setAttribute(String name, String value) {
            if (this.attributes == null) {
                this.attributes = new ObjectMap(8);
            }
            this.attributes.put(name, value);
        }

        public int getChildCount() {
            if (this.children == null) {
                return 0;
            }
            return this.children.size;
        }

        public Element getChild(int index) {
            if (this.children == null) {
                throw new GdxRuntimeException("Element has no children: " + this.name);
            }
            return this.children.get(index);
        }

        public void addChild(Element element) {
            if (this.children == null) {
                this.children = new Array(8);
            }
            this.children.add(element);
        }

        public String getText() {
            return this.text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public void removeChild(int index) {
            if (this.children != null) {
                this.children.removeIndex(index);
            }
        }

        public void removeChild(Element child) {
            if (this.children != null) {
                this.children.removeValue(child, true);
            }
        }

        public void remove() {
            this.parent.removeChild(this);
        }

        public Element getParent() {
            return this.parent;
        }

        public String toString() {
            return this.toString("");
        }

        public String toString(String indent) {
            StringBuilder buffer = new StringBuilder(128);
            buffer.append(indent);
            buffer.append('<');
            buffer.append(this.name);
            if (this.attributes != null) {
                for (ObjectMap.Entry entry : this.attributes.entries()) {
                    buffer.append(' ');
                    buffer.append((String)entry.key);
                    buffer.append("=\"");
                    buffer.append((String)entry.value);
                    buffer.append('\"');
                }
            }
            if (this.children == null && (this.text == null || this.text.length() == 0)) {
                buffer.append("/>");
            } else {
                buffer.append(">\n");
                String childIndent = indent + '\t';
                if (this.text != null && this.text.length() > 0) {
                    buffer.append(childIndent);
                    buffer.append(this.text);
                    buffer.append('\n');
                }
                if (this.children != null) {
                    for (Element child : this.children) {
                        buffer.append(child.toString(childIndent));
                        buffer.append('\n');
                    }
                }
                buffer.append(indent);
                buffer.append("</");
                buffer.append(this.name);
                buffer.append('>');
            }
            return buffer.toString();
        }

        public Element getChildByName(String name) {
            if (this.children == null) {
                return null;
            }
            for (int i = 0; i < this.children.size; ++i) {
                Element element = this.children.get(i);
                if (!element.name.equals(name)) continue;
                return element;
            }
            return null;
        }

        public Element getChildByNameRecursive(String name) {
            if (this.children == null) {
                return null;
            }
            for (int i = 0; i < this.children.size; ++i) {
                Element element = this.children.get(i);
                if (element.name.equals(name)) {
                    return element;
                }
                Element found = element.getChildByNameRecursive(name);
                if (found == null) continue;
                return found;
            }
            return null;
        }

        public Array<Element> getChildrenByName(String name) {
            Array<Element> result = new Array<Element>();
            if (this.children == null) {
                return result;
            }
            for (int i = 0; i < this.children.size; ++i) {
                Element child = this.children.get(i);
                if (!child.name.equals(name)) continue;
                result.add(child);
            }
            return result;
        }

        public Array<Element> getChildrenByNameRecursively(String name) {
            Array<Element> result = new Array<Element>();
            this.getChildrenByNameRecursively(name, result);
            return result;
        }

        private void getChildrenByNameRecursively(String name, Array<Element> result) {
            if (this.children == null) {
                return;
            }
            for (int i = 0; i < this.children.size; ++i) {
                Element child = this.children.get(i);
                if (child.name.equals(name)) {
                    result.add(child);
                }
                child.getChildrenByNameRecursively(name, result);
            }
        }

        public float getFloatAttribute(String name) {
            return Float.parseFloat(this.getAttribute(name));
        }

        public float getFloatAttribute(String name, float defaultValue) {
            String value = this.getAttribute(name, null);
            if (value == null) {
                return defaultValue;
            }
            return Float.parseFloat(value);
        }

        public int getIntAttribute(String name) {
            return Integer.parseInt(this.getAttribute(name));
        }

        public int getIntAttribute(String name, int defaultValue) {
            String value = this.getAttribute(name, null);
            if (value == null) {
                return defaultValue;
            }
            return Integer.parseInt(value);
        }

        public boolean getBooleanAttribute(String name) {
            return Boolean.parseBoolean(this.getAttribute(name));
        }

        public boolean getBooleanAttribute(String name, boolean defaultValue) {
            String value = this.getAttribute(name, null);
            if (value == null) {
                return defaultValue;
            }
            return Boolean.parseBoolean(value);
        }

        public String get(String name) {
            String value = this.get(name, null);
            if (value == null) {
                throw new GdxRuntimeException("Element " + this.name + " doesn't have attribute or child: " + name);
            }
            return value;
        }

        public String get(String name, String defaultValue) {
            String value;
            if (this.attributes != null && (value = this.attributes.get(name)) != null) {
                return value;
            }
            Element child = this.getChildByName(name);
            if (child == null) {
                return defaultValue;
            }
            String value2 = child.getText();
            if (value2 == null) {
                return defaultValue;
            }
            return value2;
        }

        public int getInt(String name) {
            String value = this.get(name, null);
            if (value == null) {
                throw new GdxRuntimeException("Element " + this.name + " doesn't have attribute or child: " + name);
            }
            return Integer.parseInt(value);
        }

        public int getInt(String name, int defaultValue) {
            String value = this.get(name, null);
            if (value == null) {
                return defaultValue;
            }
            return Integer.parseInt(value);
        }

        public float getFloat(String name) {
            String value = this.get(name, null);
            if (value == null) {
                throw new GdxRuntimeException("Element " + this.name + " doesn't have attribute or child: " + name);
            }
            return Float.parseFloat(value);
        }

        public float getFloat(String name, float defaultValue) {
            String value = this.get(name, null);
            if (value == null) {
                return defaultValue;
            }
            return Float.parseFloat(value);
        }

        public boolean getBoolean(String name) {
            String value = this.get(name, null);
            if (value == null) {
                throw new GdxRuntimeException("Element " + this.name + " doesn't have attribute or child: " + name);
            }
            return Boolean.parseBoolean(value);
        }

        public boolean getBoolean(String name, boolean defaultValue) {
            String value = this.get(name, null);
            if (value == null) {
                return defaultValue;
            }
            return Boolean.parseBoolean(value);
        }
    }

}

