������jar����
frameworkset.jar ditchnet-tabs-taglib.jar

---------------------------------
bboss-util�������̣�
---------------------------------

bboss-util->bbossaop [bboss-aop.jar]

bboss-util<-active-ext [frameworkset-util.jar]
bboss-util<-bboss-ws [frameworkset-util.jar]
bboss-util<-bbossaop [frameworkset-util.jar]
bboss-util<-bbossevent [frameworkset-util.jar]
bboss-util<-cms_baseline [frameworkset-util.jar]

bboss-util<-bboss-persistent [frameworkset-util.jar]
bboss-util<-bboss-taglib [frameworkset-util.jar]
bboss-util<-kettle [frameworkset-util.jar]
bboss-util<-portal [frameworkset-util.jar]
bboss-util<-cas server [frameworkset-util.jar]
#######update function list since bbossgroups-3.5 begin###########
o ValueObjectUtil����cast(Object obj,Class toType)�������������������Ͷ���objת��Ϊ�����Ͷ���֧���������ͺ���ͨ����
o ���ӱ���������Ԫ�ء�list/set��mapԪ�ر�����������,ʹ�÷������£�
 String url = "http://localhost:80/detail.html?user=#[account[0][0]]&password=#[password->aaa[0]->bb->cc[0]]love";
         URLStruction a = com.frameworkset.util.VariableHandler.parserSQLStruction(url);
List<String> tokens = a.getTokens();
	 		for (int k = 0; k < tokens.size(); k++) {
	 			System.out.println("tokens[" + k + "]:" + tokens.get(k));
	 		}
	 		List<Variable> variables = a.getVariables();
	
	 		for (int k = 0; k < variables.size(); k++) {
	
	 			Variable as = variables.get(k);
	
	 			System.out.println("�������ƣ�" + as.getVariableName());
	 			System.out.println("������Ӧλ�ã�" + as.getPosition());
	 			//��������Ƕ�Ӧ���������list��set��map��Ԫ�ص�Ӧ�ã��������Ӧ��Ԫ�������±���Ϣ
	 			List<Index> idxs = as.getIndexs();
	 			if(idxs != null)
	 			{
	 				for(int h = 0; h < idxs.size(); h ++)
	 				{
	 					Index idx = idxs.get(h);
	 					if(idx.getInt_idx() > 0)
	 					{
	 						System.out.println("Ԫ�������±꣺"+idx.getInt_idx());
	 					}
	 					else
	 					{
	 						System.out.println("map key��"+idx.getString_idx());
	 					}
	 				}
	 			}
	
	 		}
o �޸�ValueObjectUtil����ת���������̰߳�ȫ���⣬�������Ϊ�����ͬ���û�����ת��ʱ��ʱ���õ�����Ԥ�ڵĽ��
����frameworkset-util.jar���Խ���������
/bboss-util/src/com/frameworkset/util/ValueObjectUtil.java
o SimpleStringUtil�������Ӹ�ʽ��Exception��ΪString�ķ���
o UTF8Convertor��������ָ��Ŀ¼��ָ��������Ŀ¼���б�ָ���ļ������б�ı���ת������
o ��������������������ļ���ie 6���޷����ص�����
o util���ܣ������ַ������뼯ʶ����

o �ϲ�StringUtil��SimpleStringUtil�еĲ��ַ�����StringUtilֻ������HttpServletRequest��صĵķ���
o ValueObjectUtil��������һϵ�����ݱȽϺ���
#######update function list since bbossgroups-3.4 begin###########
o ClassInfo��������isprimary��������ʶ��Ӧ�������Ƿ���bboss����Ļ����������ͷ���
o ���ӻ�ȡ�����������ƵĹ�����LocalVariableTableParameterNameDiscoverer
��ȡLocalVariableTableParameterNameDiscoverer�ķ������£�
ParameterNameDiscoverer parameterNameDiscoverer = ClassUtil.getParameterNameDiscoverer();
��ȡ�����������Ƶķ������£�
ParameterNameDiscoverer parameterNameDiscoverer = ClassUtil.getParameterNameDiscoverer();
Method method = ClassInfo.class.getMethod("getDeclaredMethod", String.class);
String[] names = parameterNameDiscoverer.getParameterNames(method);
o ����asm 4.0�汾
#######update function list since bbossgroups-3.3 begin###########
------2011-10-14------------
o �Ż�ValueObjectUtil�и����������ƻ�ȡ�ֶ�ֵ�÷������Ľ����ܡ�
------2011-09-19------------
o ����Velocityģ������ģ��·�����û��ƣ���bboss-aop.jar/aop.properties�ļ�������approot���ã�
����ָʾӦ�������ĵľ���·����
approot=D:/workspace/bbossgroups-3.2/bboss-mvc/WebRoot
�Ա��ܹ����ҵ���Ӧ��ģ���Ŀ¼
���ڱ�ǩ����ʹ����vm�ļ�����Щ�ļ������approot��/WEB-INF/templatesĿ¼���棬��˱��뱣֤Velocity������������ȷ���ҵ�
���Ŀ¼����tomcat�����ܹ��Զ��ҵ��ģ�������weblogic���������޷��Զ��ҵ����Ŀ¼�������Ҫ��bboss-aop.jar/aop.properties�ļ�������approot����

o ����VelocityUtil�࣬����Ҳ���velocity.properties�ļ�������

o ��������ת�����ƣ�֧��lob�ֶ���File/byte[]/String���͵�ת��
#######update function list since bbossgroups-3.2 begin###########
------2011-07-19------------
o StringUtil���������ļ����ط�����
StringUtil.sendFile(request, response, record
							.getString("filename"), record
							.getBlob("filecontent"));
StringUtil.sendFile(request, response, file);							
------2011-06-14------------
o ֧��������BigDecimalת��������������BigDecimal����ת������

#######update function list since bbossgroups-3.1 begin###########

------2011-06-09------------
o ������ַ�������������ת����̨���쳣��ȱ��
------2011-05-06------------
o �޸���ҳ��ǩż���Ҳ���vmģ���ļ���©��
����©���޸��ĳ���Ϊ��
/bboss-util/src/com/frameworkset/util/VelocityUtil.java
	
#######update function list since bbossgroups-3.1 end###########

#######update function list since bbossgroups-3.0 begin###########
to do list:
��ʱ���Ҳ���vmģ���ļ�

------2011-04-07------------
o  �޸�DaemonThread���̣�֧�ִ��ⲿָ��ˢ���ļ���Դ��ʱ������
------2011-04-05------------
o  ֧���ַ�����ö������ת�����ַ���������ö����������ת������
------2011-03-02------------
o  �޸�double����������int����ת�������⣬��������ֵת��Ϊ����Ĺ��ܣ�֧��������������֮����໥ת��
#######update function list since bbossgroups-3.0 end###########

------------------------------------------------------------------------
update function list in bbossgroups-2.0-rc2 since bbossgroups-2.0-rc1
------------------------------------------------------------------------
o �޸� com/frameworkset/util/TransferObjectFactory.java�ж���ֵ������֧��isXXXX��ʽ��ȡ����ֵ������
/bboss-util/src/com/frameworkset/util/TransferObjectFactory.java
----------------------------------------
bbossgroups-2.0-rc2 - 2010-11-28
----------------------------------------
o ����ValueObjectUtil��getValue������������Ϊ����get��������ֵΪnull���ظ�����Boolean����������
����getMethodByPropertyName����������public static Object getValueByMethod(Object obj, Method method, Object[] params)����
----------------------------------------
bbossgroups-2.0-rc2 - 2010-08-31
----------------------------------------
o �޸�velocityģ��û����ȷ��ʼ�������⣬��Ҫ����VelocityUtil��ִ��evaluteʱû���б�
  �����Ƿ��Ѿ���ʼ��

----------------------------------------
bbossgroups-2.0-rc2 - 2010-08-23
----------------------------------------
o �޸�com.frameworkset.util.VariableHandler�б�������bug,
Ĭ��default_regex �޸�Ϊ "\\$\\{.+?)\\}"��ȡ���봮�еı�������Ϊ���鷵��

------------------------------------------------------------------------
update function list in bbossgroups-2.0-rc1 since bbossgroups-2.0-rc
------------------------------------------------------------------------
----------------------------------------
bbossgroups-2.0-rc1 - 2010-07-23
----------------------------------------
o VelocityUtil�����Ӷ��ַ���ģ��Ľ�������
--------------------------
2010-03-10
--------------------------

o ������������������������ȡ���滻�ӿ�

--------------------------
2010-01-07
----------------------
o ��spring��صĳ�����룬�����γɰ�frameworkset-spring.jar,ԭ���İ���������frameworkset-util.jar
o ���ant�����ű������������ļ�:build.xml
 

--------------------------
2009-12-15
----------------------
o ����������������
com.frameworkset.util.VariableHandler
����˵����
���Ը���Ĭ�ϵ�����ʽdefault_regex = "\\$\\{([^\\}]+)\\}"��ȡ���봮�еı�������Ϊ���鷵��
public static String[] variableParser(String inputString)
���Ը���ָ��������ʽ��ȡ���봮�еı�������Ϊ���鷵��
public static String[] variableParser(String inputString,String regex)
���Ը���ָ���ı�����ǰ�����ͺ󵼷���ȡ���봮�еı�������Ϊ���鷵��
 public static String[] variableParser(String inputString,String pretoken,String endtoken)


--------------------------

2009-09-28
----------------------
o   bboss-util/src/com/frameworkset/util/ValueObjectUtil.java
booleanֵת��ʱ���ܹ�����0,��1

o �޸�VelocityUtil.java
o �޸�com.frameworkset.util.ValueObjectUtil
	�޸�com.frameworkset.common.util.ValueObjectUtilʹ����com.frameworkset.util.ValueObjectUtil�Ĺ��ܱ���һ��
o ���Ӳ���������test

o ����bsh-2.0b4.jar����ʵ��ValueObjectUtil�и���������ת���Ĺ���

