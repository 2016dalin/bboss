package com.frameworkset.sqlexecutor;

import java.util.HashMap;

import org.junit.Test;

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
	@Test
	public void queryMaterailListInfoFirstStyleBean() throws Exception {
		
		//ִ�з�ҳ��ѯ��queryMaterialList��Ӧ��ҳ��ѯ��䣬
		//����sql����ڷ�ҳ�����ڲ�ִ���ܼ�¼����ѯ���������ַ��ʹ�ü򵥣�Ч����Խϵ�
		//condition���������˲�ѯ����
		int offset = 0; int pagesize = 10;
		PurchaseApplyCondition condition = new PurchaseApplyCondition();
		condition.setId("3952ce4f-fce7-4f9e-a92b-81ebdcbe57ed");
		ListInfo datas = executor.queryListInfoBean(HashMap.class, "queryMaterialList", offset, pagesize,condition);
		return ;
	}
	@Test
	public void queryMaterailListInfoSecondStyleBean() throws Exception {
		//ִ���ܼ�¼��ѯ������totalSize�����У�queryCountMaterialList��Ӧһ���Ż�����ܼ�¼��ѯ���
		//condition���������˲�ѯ����
		int offset = 0; int pagesize = 10;
		PurchaseApplyCondition condition = new PurchaseApplyCondition();
		condition.setId("3952ce4f-fce7-4f9e-a92b-81ebdcbe57ed");
		long totalSize = executor.queryObjectBean(long.class, "queryCountMaterialList", condition);
		//ִ���ܼǷ�ҳ��ѯ��queryMaterialList��Ӧ��ҳ��ѯ��䣬ͨ��totalsize�������ⲿ�����ܼ�¼����
		//�����ڷ�ҳ�����ڲ�����ִ���ܼ�¼����ѯ�������Ա�����ϵͳ���ܣ����ַ��ʹ�ü򵥣�Ч����Ե�һ�ַ��ϸߣ�����Ҫ���������ܼ�¼����ѯsql
		//condition���������˲�ѯ����
		ListInfo datas = executor.queryListInfoBean(HashMap.class, "queryMaterialList", offset, pagesize,totalSize ,condition);
		return;
	}
	
	
	public @Test void queryMaterailListInfoThirdStyleBean() throws Exception {
		//����sql�����ⲿ������ܼ�¼��sql�����з�ҳ�����ַ��ʹ�ü򵥣�Ч����ߣ�����Ҫ���������ܼ�¼����ѯsql
		PurchaseApplyCondition condition = new PurchaseApplyCondition();
		condition.setId("3952ce4f-fce7-4f9e-a92b-81ebdcbe57ed");
		ListInfo list = executor.queryListInfoBeanWithDBName(HashMap.class, "bspf","queryMaterialList", 0, 10,"queryCountMaterialList" ,condition);
		return ;
	}
	/*******************************��bean��ʽ���ݲ�ѯ��������*******************************/
	
	/*******************************�Դ�ͳ�󶨱�����ʽ���ݲ�ѯ������ʼ*******************************/
	public @Test void queryMaterailListInfoFirstStyle( ) throws Exception {
		
		//ִ�з�ҳ��ѯ��queryMaterialList��Ӧ��ҳ��ѯ��䣬
		//����sql����ڷ�ҳ�����ڲ�ִ���ܼ�¼����ѯ���������ַ��ʹ�ü򵥣�Ч����Խϵ�
		//condition���������˲�ѯ����
		ListInfo list = executor.queryListInfo(HashMap.class, "queryMaterialListbindParam", 0, 10, "3952ce4f-fce7-4f9e-a92b-81ebdcbe57ed");
		return;
	}
	
	public @Test void queryMaterailListInfoSecondStyle( ) throws Exception {
		//ִ���ܼ�¼��ѯ������totalSize�����У�queryCountMaterialList��Ӧһ���Ż�����ܼ�¼��ѯ���
		//condition���������˲�ѯ����
		long totalSize = executor.queryObject(long.class, "queryCountMaterialListbindParam", "3952ce4f-fce7-4f9e-a92b-81ebdcbe57ed");
		//ִ���ܼǷ�ҳ��ѯ��queryMaterialList��Ӧ��ҳ��ѯ��䣬ͨ��totalsize�������ⲿ�����ܼ�¼����
		//�����ڷ�ҳ�����ڲ�����ִ���ܼ�¼����ѯ�������Ա�����ϵͳ���ܣ����ַ��ʹ�ü򵥣�Ч����Ե�һ�ַ��ϸߣ�����Ҫ���������ܼ�¼����ѯsql
		//condition���������˲�ѯ����
		ListInfo datas = executor.queryListInfoWithTotalsize(HashMap.class, "queryMaterialListbindParam", 0, 10,totalSize , "3952ce4f-fce7-4f9e-a92b-81ebdcbe57ed");
		return;
	}
	
	
	public @Test void queryMaterailListInfoThirdStyle() throws Exception {
		//����sql�����ⲿ������ܼ�¼��sql�����з�ҳ�����ַ��ʹ�ü򵥣�Ч����ߣ�����Ҫ���������ܼ�¼����ѯsql
		ListInfo list = executor.queryListInfoWithDBName2ndTotalsizesql(HashMap.class, "bspf","queryMaterialListbindParam", 0, 10,"queryCountMaterialListbindParam", "3952ce4f-fce7-4f9e-a92b-81ebdcbe57ed");
		return ;
	}
	/*******************************�Դ�ͳ�󶨱�����ʽ���ݲ�ѯ��������*******************************/
}
