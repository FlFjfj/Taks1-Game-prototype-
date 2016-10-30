package com.fjfj.testvr.utility;

import java.io.DataInputStream;
import java.io.InputStream;

public class EndianDataInputStream extends DataInputStream
{
    public EndianDataInputStream(InputStream in)
    {
        super(in);
    }

    public String read4ByteString( ) throws Exception
    {
        byte[] bytes = new byte[4];
        readFully(bytes);
        return new String( bytes, "US-ASCII" );
    }

    public short readShortLittleEndian( ) throws Exception
    {
        int result = readUnsignedByte();
        result |= readUnsignedByte() << 8;
        return (short)result;
    }

    public int readIntLittleEndian( ) throws Exception
    {
        int result = readUnsignedByte();
        result |= readUnsignedByte() << 8;
        result |= readUnsignedByte() << 16;
        result |= readUnsignedByte() << 24;
        return result;
    }

}
