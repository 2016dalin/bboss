package com.frameworkset.common.hibernate.dao;


import java.util.List;
import com.frameworkset.util.ListInfo;
import org.hibernate.type.Type;
import java.io.Serializable;
import java.util.Collection;

/**
 * <p>Title: DAO</p>
 *
 * <p>Description: dao�Ľӿ�</p>
 *
 * <p>
 * bboss workgroup
 * </p>
 * <p>
 * Copyright (c) 2007
 * </p>
 * 
 * @Date 2009-6-1 ����08:58:51
 * @author biaoping.yin
 * @version 1.0
 */
public interface DAO extends Serializable{
	public void removeObject(Object o)
		throws DataAccessException;


	public Object getObject(Class clazz, Serializable id)
		throws DataAccessException;

	public List getObjects(Class clazz)
		throws DataAccessException;

	public void removeObject(Class clazz, Serializable id)
		throws DataAccessException;


	public void saveObject(Object o)
		throws DataAccessException
	;

	public void updateObject(Object o)
		throws DataAccessException
	;

	/**
	 * ��ѯ����Ϊclazz�����м�¼
	 * @param clazz Class
	 * @param start ��ҳ������ʼλ��
	 * @param maxSize ��ȡ��¼�������
	 * @return ListInfo ��װ����б���ܼ�¼��
	 * @throws DataAccessException
	 */
	public ListInfo getObjects(Class clazz,long start,int maxSize)
		throws DataAccessException
	;


	/**
	 * ִ��Ԥ�����ҳ��ѯ�����ҷ��ز�ѯ�������صķ�ҳ��Ϣ���ܼ�¼����
	 * @param sql String Ԥ�����ѯ���
	 * @param objs Object[] Ԥ�����ѯ����������
	 * @param types Type[] Ԥ�����ѯ��������������
	 * @param start ��ҳ������ʼλ��
	 * @param maxSize ��ȡ��¼�������
	 * @return ListInfo ��װ����б���ܼ�¼��
	 * @throws DataAccessException
	 * @deprecated ����������hibernate 2.x���ݶ�д������ķ���Ϊprotected List find(String sql, Object objs[], Pagination pagination)
	 */
	public ListInfo find(String sql, Object objs[], Type types[], long start,int maxSize)
		throws DataAccessException
	;
	/**
	 * ִ��Ԥ�����ҳ��ѯ�����ҷ��ز�ѯ�������صķ�ҳ��Ϣ���ܼ�¼����
	 * @param sql String Ԥ�����ѯ���
	 * @param objs Object[] Ԥ�����ѯ����������
	 * @param types Type[] Ԥ�����ѯ��������������
	 * @return ListInfo ��װ����б���ܼ�¼��
	 * @throws DataAccessException
	 * @deprecated ����������hibernate 2.x���ݶ�д������ķ���Ϊprotected List find(String sql, Object objs[])
	 */
	public List find(String sql, Object objs[], Type types[])
		throws DataAccessException
	;

	/**
	 * ִ��Ԥ�����ҳ��ѯ�����ҷ��ز�ѯ�������صķ�ҳ��Ϣ���ܼ�¼����
	 * @param sql String Ԥ�����ѯ���
	 * @param objs Object[] Ԥ�����ѯ����������
	 * @param types Type[] Ԥ�����ѯ��������������
	 *
	 * @return ListInfo ��װ����б���ܼ�¼��
	 * @throws DataAccessException
	 * @deprecated ����������hibernate 2.x���ݶ�д������ķ���Ϊprotected List find(String sql, Object objs[])
	 */
	public List find(String sql, Object objs[])
		throws DataAccessException
	;


	/**
	 * ִ��Ԥ�����ҳ��ѯ�����ҷ��ز�ѯ�������صķ�ҳ��Ϣ���ܼ�¼����
	 * @param sql String Ԥ�����ѯ���
	 * @param objs Object[] Ԥ�����ѯ����������
	 * @param start ��ҳ������ʼλ��
	 * @param maxSize ��ȡ��¼�������
	 * @return ListInfo ��װ����б���ܼ�¼��
	 * @throws DataAccessException
	 */
	public ListInfo find(String sql, Object objs[],long start,int maxSize)
		throws DataAccessException;


	/**
	 * ִ�з�ҳ��ѯ
	 * @param sql String ��ѯ���
	 * @param start ��ҳ������ʼλ��
	 * @param maxSize ��ȡ��¼�������
	 * @return ListInfo ��װ����б���ܼ�¼��
	 * @throws DataAccessException
	 */
	public ListInfo find(String sql, long start,int maxSize)
		throws DataAccessException;

	/**
	 * ִ�в�ѯ
	 * @param sql String ��ѯ���
	 * @return List ����б�
	 * @throws DataAccessException
	 */
	public List find(String sql)
		throws DataAccessException
	;


	/**
	 * ִ��Ԥ�����ѯ��䣬���ؽ���б�
	 * @param sql String Ԥ�������
	 * @param obj Object Ԥ�����ѯ����ֵ
	 * @param type Type Ԥ�����ѯ��������
	 * @param start ��ҳ������ʼλ��
	 * @param maxSize ��ȡ��¼�������
	 * @return ListInfo ��װ����б���ܼ�¼��
	 * @throws DataAccessException
	 * @deprecated ������Ϊ��hibernate 2.x���ݶ���Ƶ�,hibernate 3.x����ķ���Ϊprotected List find(String sql, Object obj, Pagination pagination)
	 */
	public ListInfo find(String sql, Object obj, Type type, long start, int maxSize)
		throws DataAccessException;

	/**
	 * ִ��Ԥ�����ѯ��䣬���ؽ���б�
	 * @param sql String Ԥ�������
	 * @param obj Object Ԥ�����ѯ����ֵ
	 * @param start ��ҳ������ʼλ��
	 * @param maxSize ��ȡ��¼�������
	 * @return ListInfo ��װ����б���ܼ�¼��
	 * @throws DataAccessException
	 */
	public ListInfo find(String sql, Object obj,long start, int maxSize)
		throws DataAccessException;




	/**
	 * ִ��Ԥ�����ѯ��䣬���ؽ���б�
	 * @param sql String Ԥ�������
	 * @param obj Object Ԥ�����ѯ����ֵ
	 * @return List ����б�
	 * @throws DataAccessException
	 */
	public List find(String sql, Object obj)
		throws DataAccessException;
	/**
	 * ִ��Ԥ����sql��䣬��ȡ��ѯ������ܼ�¼����objs[]�����Ų�ѯ����
	 * @param sql String
	 * @param objs Object[]
	 * @return long
	 * @throws DataAccessException
	 */
	public long loadTotalSize(String sql, Object objs[])
		throws DataAccessException;

	/**
	 * ��ȡ��¼����
	 * ��hibernate 2.x���ݵķ���
	 * @param sql String
	 * @param objs Object[]
	 * @param types Type[]
	 * @return long
	 * @throws DataAccessException
	 * @deprecated ����ķ���Ϊprotected long loadTotalSize(String sql, Object objs[])
	 */
	public long loadTotalSize(String sql, Object objs[], Type types[])
		throws DataAccessException
		;

	/**
	 * ��ȡhql�е�from�Ӿ�
	 * @param sql String
	 * @return String
	 */
	public String getCountSql(String sql);

	/**
	 * ��������
	 * @param objs Collection
	 * @return Object
	 */
	public void batchInsert(Collection objs);

	/**
	 * ��������
	 * @param objs Collection
	 */
	public void batchUpdate(Collection objs);

	/**
	 * ����ɾ��
	 * @param objs Collection
	 */
	public void batchDelete(Collection objs);




}
