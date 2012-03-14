/*
 *  Copyright 2008 biaoping.yin
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.frameworkset.common.poolman.security;

import java.security.Key;
import java.security.Security;

import javax.crypto.Cipher;

/**
 * 
 * <p>Title: DESCipher.java</p>
 *
 * <p>Description: һ������DES�㷨�ļ��ܽ�����,����ͨ��������Կ��ʹ��Ĭ����Կ�����ַ������ֽ�������м��ܺͽ������㡣</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 * @Date 2012-3-14 ����3:23:03
 * @author biaoping.yin
 * @version 1.0
 */
public class DESCipher {

    private static final String DEFAULTKEY = "BBOSSGROUPS";

    private Cipher encryptCipher = null;

    private Cipher decryptCipher = null;

    /**
     * �������ֽ�����ת��Ϊ16����ֵ��ʾ��϶��ɵ��ַ�����
     * 
     * @param byte[] ��Ҫת����byte����
     * @return String ת������ַ���
     * @throws Exception
     *             JAVA�쳣
     */
    public static String byteGrpToHexStr(byte[] arrB) throws Exception {
        int iLen = arrB.length;
        StringBuffer tempSB = new StringBuffer(iLen * 2);
        for (int i = 0; i < iLen; i++) {
            int intTmp = arrB[i];
            while (intTmp < 0) {
                intTmp = intTmp + 256;
            }
            if (intTmp < 16) {
                tempSB.append("0");
            }
            tempSB.append(Integer.toString(intTmp, 16));
        }
        return tempSB.toString();
    }

    /**
     * ������16����ֵ��ʾ��϶��ɵ��ַ���ת��Ϊ�ֽ����顣
     * 
     * @param String
     *            ��Ҫת�����ַ���
     * @return byte[] ת�����byte����
     * @throws Exception
     *             JAVA�쳣
     */
    public static byte[] hexStrToByteGrp(String strIn) throws Exception {
        byte[] arrB = strIn.getBytes();
        int iLen = arrB.length;
        byte[] arrOut = new byte[iLen / 2];
        for (int i = 0; i < iLen; i = i + 2) {
            String strTmp = new String(arrB, i, 2);
            arrOut[i / 2] = (byte) Integer.parseInt(strTmp, 16);
        }
        return arrOut;
    }

    /**
     * Ĭ�Ϲ��췽����ʹ��Ĭ����Կ
     * 
     * @throws Exception
     */
    public DESCipher() throws Exception {
        this(DEFAULTKEY);
    }

    /**
     * ָ����Կ���췽��
     * 
     * @param String
     *            ָ������Կ
     * @throws Exception
     *             JAVA�쳣
     */
    public DESCipher(String strKey) throws Exception {
        Security.addProvider(new com.sun.crypto.provider.SunJCE());
        Key key = getKey(strKey.getBytes());
        encryptCipher = Cipher.getInstance("DES");
        encryptCipher.init(Cipher.ENCRYPT_MODE, key);
        decryptCipher = Cipher.getInstance("DES");
        decryptCipher.init(Cipher.DECRYPT_MODE, key);
    }

    /**
     * �����ֽ�����
     * 
     * @param byte[] ����ܵ��ֽ�����
     * @return byte[] ���ܺ���ֽ�����
     * @throws Exception
     *             JAVA�쳣
     */
    public byte[] encrypt(byte[] arrB) throws Exception {
        return encryptCipher.doFinal(arrB);
    }

    /**
     * �����ַ���
     * ���ַ���ת��Ϊ�ֽ����������encrypt(byte[])�������м��ܣ������õ��ֽ�����ת��Ϊ�ַ�������
     * 
     * @param String
     *            ����ܵ��ַ���
     * @return String ���ܺ���ַ���
     * @throws Exception
     *             JAVA�쳣
     */
    public String encrypt(String strIn) throws Exception {
        return byteGrpToHexStr(encrypt(strIn.getBytes()));
    }

    /**
     * �����ֽ�����
     * 
     * @param byte[] ����ܵ��ֽ�����
     * @return byte[] ���ܺ���ֽ�����
     * @throws Exception
     *             JAVA�쳣
     */
    public byte[] decrypt(byte[] arrB) throws Exception {
        return decryptCipher.doFinal(arrB);
    }

    /**
     * �����ַ���
     * �������ַ���ת��Ϊ�ֽ����������decrypt(String)�������н��ܣ������õ��Ľ����ַ�����ת��Ϊ�ַ����󷵻ء�
     * 
     * @param String
     *            ����ܵ��ַ���
     * @return String ���ܺ���ַ���
     * @throws Exception
     *             JAVA�쳣
     */
    public String decrypt(String strIn) throws Exception {
        return new String(decrypt(hexStrToByteGrp(strIn)));
    }

    /**
     * ��ָ���ַ���������Կ����Կ������ֽ����鳤��Ϊ8λ ����8λʱ���油0������8λֻȡǰ8λ
     * 
     * @param byte[] ���ɸ��ַ������ֽ�����
     * @return Key ���ɵ���Կ
     * @throws Exception
     *             JAVA�쳣
     */
    private Key getKey(byte[] arrBTmp) throws Exception {
        byte[] arrB = new byte[8];
        for (int i = 0; i < arrBTmp.length && i < arrB.length; i++) {
            arrB[i] = arrBTmp[i];
        }
        Key key = new javax.crypto.spec.SecretKeySpec(arrB, "DES");
        return key;
    }
    
    
}
