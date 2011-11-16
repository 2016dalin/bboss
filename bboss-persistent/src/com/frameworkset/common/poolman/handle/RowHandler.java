package com.frameworkset.common.poolman.handle;

import com.frameworkset.common.poolman.Record;
import com.frameworkset.common.poolman.sql.PoolManResultSetMetaData;
import com.frameworkset.common.poolman.util.SQLUtil;
import com.frameworkset.orm.engine.model.SchemaType;

/**
 * 
 * 
 * <p>Title: RowHandler.java</p>
 *
 * <p>Description: �д�����</p>
 *
 * <p>Copyright: Copyright (c) 2007</p>
 *
 * @Date Oct 22, 2008 9:35:14 PM
 * @author biaoping.yin
 * @version 1.0
 */
public abstract class RowHandler<T> {
	/**
	 * ���Ѿ�����õ��м�¼���д�����߼�
	 * @param rowValue ��Ҫ���record��¼�е�����Ϣ�Ķ���Ŀǰ��������Ҫ�����ͣ���ͨjavaֵ����StringBuffer��������ƴ���Զ����xml����
	 * @param record ����˵�ǰ��¼����Ϣ��������ѯ�б��е���������Ϣ�����磺select id,name from test,��ôRecord�д������id��name��ֵ
	 *               ����ͨ�����µķ�ʽ��ȡ��
	 *               record.getInt("id");//��������id�Ĵ�Сд������
	 *               record.getInt("name");//��������name�Ĵ�Сд������
	 */
	public abstract void handleRow(T rowValue,Record record) throws Exception;
	
//	public abstract T handleField_(Record record) throws Exception;
	
	protected PoolManResultSetMetaData meta;
        protected String dbname; 
        
        public void init(PoolManResultSetMetaData meta,String dbname)
        {
            this.meta = meta;
            this.dbname = dbname;
          
        }
        
        public void destroy()
        {
            this.meta = null;
            this.dbname = null;
        }
        
        public SchemaType getSchemaType(int clindex)
        {
           if(meta == null)
           {
               throw new RowHandlerException("Դ���ݶ���[meta]δ��ʼ��,�޷������д���.");
           }
            try {
                int sqltype = meta.getColumnType(clindex);
                SchemaType schemaType = SQLUtil.getSchemaType(dbname, sqltype); 
                return schemaType;
            } catch (Exception e) {
                
//                e.printStackTrace();
                throw new RowHandlerException(e);
            }
            
        }
        public SchemaType getSchemaType(String colName)
        {
           if(meta == null)
           {
               throw new RowHandlerException("Դ���ݶ���[meta]δ��ʼ��,�޷������д���.");
           }
            try {
                int index = seekIndex(colName);
                int sqltype = meta.getColumnType(index + 1);
                SchemaType schemaType = SQLUtil.getSchemaType(dbname, sqltype); 
                return schemaType;
            }
            catch(RowHandlerException e)
            {
                throw e;
            }
            catch (Exception e) {
                
//                e.printStackTrace();
                throw new RowHandlerException(e);
            }
            
        }
        private int seekIndex(String colName)
        {
//            String temp = colName.toUpperCase();
//            String[] columnLabel_uppers = meta.get_columnLabel_upper();
//            for(int i = 0; i < columnLabel_uppers.length ; i ++)
//            {
//                if(columnLabel_uppers[i].equals(temp))
//                    return i;
//            }
//            throw new RowHandlerException("��ѯ����в�������[" + colName + "].");
        	return meta.seekIndex(colName);
        }
	

}
