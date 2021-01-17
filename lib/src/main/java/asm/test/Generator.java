package asm.test;

import java.io.FileOutputStream;
import java.io.IOException;

class Generator {
    public static void main(String[] args) throws Exception {
        toFile(ByteCodeDemoDump.dump(),"bytecode/ByteCodeDemo.class");
        toFile(MiniTestDump.dump(),"bytecode/MiniTest.class");
    }
    public static void toFile(byte[] code,String path){
        try {
            //将二进制流写到本地磁盘上
            FileOutputStream fos = new FileOutputStream(path);
            fos.write(code);
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }
    }
}
