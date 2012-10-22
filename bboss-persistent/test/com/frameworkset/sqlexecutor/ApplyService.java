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
	
	/**
	 * 
	 * @param offset
	 * @param pagesize
	 * @param condition
	 * @return
	 * @throws Exception
	 */
	public ListInfo queryMaterailListInfo(int offset, int pagesize ,PurchaseApplyCondition condition) throws Exception {
		//ִ���ܼ�¼��ѯ������totalSize�����У�queryCountMaterialList��Ӧһ���Ż�����ܼ�¼��ѯ���
		//condition���������˲�ѯ����
		long totalSize = executor.queryObjectBean(long.class, "queryCountMaterialList", condition);
		//ִ���ܼǷ�ҳ��ѯ��queryMaterialList��Ӧ��ҳ��ѯ��䣬ͨ��totalsize�������ⲿ�����ܼ�¼����
		//�����ڷ�ҳ�����ڲ�����ִ���ܼ�¼����ѯ�������Ա�����ϵͳ����
		//condition���������˲�ѯ����
		return executor.queryListInfoBean(ListBean.class, "queryMaterialList", offset, pagesize,totalSize ,condition);
	}
	@Test
	public void queryMaterailListInfo1() throws Exception {
//		long totalSize = executor.queryObjectBeanWithDBName(long.class,Constant.DATASOURCE_NAME_COMMON, "queryCountMaterialList", condition);
		ListInfo list = executor.queryListInfoBeanWithDBName(HashMap.class, "bspf","queryMaterialList", 0, 10,"queryCountMaterialList" ,null);
	}
}
