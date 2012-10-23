package com.frameworkset.sqlexecutor;

import java.util.HashMap;

import com.frameworkset.common.poolman.ConfigSQLExecutor;
import com.frameworkset.util.ListInfo;
/**
 * 
 * <p>Title: ApplyService.java</p>
 *
 * <p>Description: ��ҳ��ѯ����ʵ��������µķ�ҳ�ӿڽ��в��ԣ�
 * 3.6.0֮��İ汾ConfigSQLExecutor/SQLExecutor/PreparedDBUtil�����־ò������������һ���ҳ�ӿڣ�
 * ����ӿں�֮ǰ�ķ�ҳ�ӿڵ�������������һ��totalsize������Ҳ����˵����ͨ��totalsize�������ⲿ�����ܼ�¼����
 * �����ڷ�ҳ�����ڲ�����ִ���ܼ�¼����ѯ�������Ա�����ϵͳ����
 * 
 * </p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 * @Date 2012-10-18 ����5:06:43
 * @author biaoping.yin
 * @version 1.0
 */
public class ApplyService {

	private com.frameworkset.common.poolman.ConfigSQLExecutor executor = new ConfigSQLExecutor("com/frameworkset/sqlexecutor/purchaseApply.xml");
	
	/*******************************��bean��ʽ���ݲ�ѯ������ʼ*******************************/
	public ListInfo queryMaterailListInfoFirstStyleBean(int offset, int pagesize ,PurchaseApplyCondition condition) throws Exception {
		
		//ִ�з�ҳ��ѯ��queryMaterialList��Ӧ��ҳ��ѯ��䣬
		//����sql����ڷ�ҳ�����ڲ�ִ���ܼ�¼����ѯ���������ַ��ʹ�ü򵥣�Ч����Խϵ�
		//condition���������˲�ѯ����
		return executor.queryListInfoBean(HashMap.class, "queryMaterialList", offset, pagesize,condition);
	}
	
	public ListInfo queryMaterailListInfoSecondStyleBean(int offset, int pagesize ,PurchaseApplyCondition condition) throws Exception {
		//ִ���ܼ�¼��ѯ������totalSize�����У�queryCountMaterialList��Ӧһ���Ż�����ܼ�¼��ѯ���
		//condition���������˲�ѯ����
		long totalSize = executor.queryObjectBean(long.class, "queryCountMaterialList", condition);
		//ִ���ܼǷ�ҳ��ѯ��queryMaterialList��Ӧ��ҳ��ѯ��䣬ͨ��totalsize�������ⲿ�����ܼ�¼����
		//�����ڷ�ҳ�����ڲ�����ִ���ܼ�¼����ѯ�������Ա�����ϵͳ���ܣ����ַ��ʹ�ü򵥣�Ч����Ե�һ�ַ��ϸߣ�����Ҫ���������ܼ�¼����ѯsql
		//condition���������˲�ѯ����
		return executor.queryListInfoBean(HashMap.class, "queryMaterialList", offset, pagesize,totalSize ,condition);
	}
	
	
	public ListInfo queryMaterailListInfoThirdStyleBean(int offset, int pagesize ,PurchaseApplyCondition condition) throws Exception {
		//����sql�����ⲿ������ܼ�¼��sql�����з�ҳ�����ַ��ʹ�ü򵥣�Ч����ߣ�����Ҫ���������ܼ�¼����ѯsql
		ListInfo list = executor.queryListInfoBeanWithDBName(HashMap.class, "bspf","queryMaterialList", 0, 10,"queryCountMaterialList" ,condition);
		return list;
	}
	/*******************************��bean��ʽ���ݲ�ѯ��������*******************************/
	
	/*******************************�Դ�ͳ�󶨱�����ʽ���ݲ�ѯ������ʼ*******************************/
	public ListInfo queryMaterailListInfoFirstStyle(int offset, int pagesize ) throws Exception {
		
		//ִ�з�ҳ��ѯ��queryMaterialList��Ӧ��ҳ��ѯ��䣬
		//����sql����ڷ�ҳ�����ڲ�ִ���ܼ�¼����ѯ���������ַ��ʹ�ü򵥣�Ч����Խϵ�
		//condition���������˲�ѯ����
		return executor.queryListInfo(HashMap.class, "queryMaterialListbindParam", offset, pagesize);
	}
	
	public ListInfo queryMaterailListInfoSecondStyle(int offset, int pagesize ) throws Exception {
		//ִ���ܼ�¼��ѯ������totalSize�����У�queryCountMaterialList��Ӧһ���Ż�����ܼ�¼��ѯ���
		//condition���������˲�ѯ����
		long totalSize = executor.queryObject(long.class, "queryCountMaterialListbindParam");
		//ִ���ܼǷ�ҳ��ѯ��queryMaterialList��Ӧ��ҳ��ѯ��䣬ͨ��totalsize�������ⲿ�����ܼ�¼����
		//�����ڷ�ҳ�����ڲ�����ִ���ܼ�¼����ѯ�������Ա�����ϵͳ���ܣ����ַ��ʹ�ü򵥣�Ч����Ե�һ�ַ��ϸߣ�����Ҫ���������ܼ�¼����ѯsql
		//condition���������˲�ѯ����
		return executor.queryListInfoWithTotalsize(HashMap.class, "queryMaterialListbindParam", offset, pagesize,totalSize );
	}
	
	
	public ListInfo queryMaterailListInfoThirdStyle(int offset, int pagesize ) throws Exception {
		//����sql�����ⲿ������ܼ�¼��sql�����з�ҳ�����ַ��ʹ�ü򵥣�Ч����ߣ�����Ҫ���������ܼ�¼����ѯsql
		ListInfo list = executor.queryListInfoWithDBName2ndTotalsizesql(HashMap.class, "bspf","queryMaterialListbindParam", 0, 10,"queryCountMaterialListbindParam" );
		return list;
	}
	/*******************************�Դ�ͳ�󶨱�����ʽ���ݲ�ѯ��������*******************************/
}
