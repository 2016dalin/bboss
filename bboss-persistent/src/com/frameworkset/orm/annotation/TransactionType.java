package com.frameworkset.orm.annotation;

public enum TransactionType
    {
        /**
         * ʼ�մ���������
         */
        NEW_TRANSACTION,
        
        /**
         * ���û�����񴴽���������������뵱ǰ����
         */
        REQUIRED_TRANSACTION,
        /**
         * ������ͼ�������û�в���������,Ĭ�����
         */
        MAYBE_TRANSACTION,
        /**
         * û������
         */
        NO_TRANSACTION,
        
        /**
         * δ֪��������
         */
        UNKNOWN_TRANSACTION,
        
        /**
         * ��д�������ͣ�֧�����ݿ��д�����������������Ĳ���
         * �������񶼿��Կ���
         */
        RW_TRANSACTION
        
    }