https://github.com/bbossgroups/bbossgroups-3.5.git
todo

1.url��д������������¼���ָ����
	ȫ�ַ�Χ����
	����������
	��������������

2.�������������
��������Ϊ2����Σ�-�����ڿ�ܼ�����������������ȫ�����������ǵ�ʵ��ģʽ��

3.���ʻ���������
6.mvc��gwt��Ͽ������о�

#######update function list since bbossgroups-3.6 begin###########
o list/map��ǩ����softparser���ԣ����sessionKey��requestKey��pagecontextKey����classdataList���ݶ��󻺴棬
 �����ظ�ʹ��ʱ�ظ��������ݶ���Ĭ��ֵΪtrue
o ���mvc �ļ��ϴ����ie6����������,commons-fileupload-1.2.2��ie6���ļ��ϴ��������޷��ļ��ϴ�
����취����common file upload�İ汾���˵�1.2��������������
o ���ע��org.frameworkset.web.servlet.handler.annotations.ExcludeMethod����ע��������mvc�����������������ExcludeMethodע��ķ����Ͳ��ᱻ���뵽mvc�������ķ���ע�����
o ���ƿ��������������㷨���ų����Ե�get/set��������ǿϵͳ��ȫ��
#######update function list since bbossgroups-3.5 begin###########
o ����locale��ǩ,����������Ҵ��룬ʹ�÷�����
<pg:locale/>
�����en_US,zh_CN�ȵ�
o mvc���ʻ����õ���
��web.xml�ļ���DispatchServlet��������������
<init-param>
			<param-name>messagesources</param-name>
			<param-value>/WEB-INF/messages,/WEB-INF/messages1</param-value>
		</init-param>
		<init-param>
			<param-name>useCodeAsDefaultMessage</param-name>
			<param-value>true</param-value>
		</init-param>
messagesources����ָ�����ʻ������ļ��嵥
useCodeAsDefaultMessage����ָ�����û������Ӧ�������ļ����ҵ���Ӧ��code��Ӧ���������Ƿ�ֱ�����code��Ĭ��ֵΪtrue
Ϊtrueʱ�����false�����	
�������������£�
<servlet>
		<servlet-name>mvcdispather</servlet-name>
		<servlet-class>org.frameworkset.web.servlet.DispatchServlet</servlet-class>
		<init-param>
			<param-name>contextConfigLocation</param-name>
			<param-value>/WEB-INF/bboss-*.xml,/WEB-INF/conf/bboss-*.xml</param-value>
		</init-param>
		<init-param>
			<param-name>messagesources</param-name>
			<param-value>/WEB-INF/messages,/WEB-INF/messages1</param-value>
		</init-param>
		<init-param>
			<param-name>useCodeAsDefaultMessage</param-name>
			<param-value>true</param-value>
		</init-param>
		<load-on-startup>0</load-on-startup>
	</servlet>
o mvc������������Ӧ���MappingJacksonHttpMessageConverter֧��jsonp������Ӧ����վ����ͨѶЭ�飩
ʹ�÷������£�
�������ˣ�
�������˲�����ʵ�����̣�bestpractice\demoproject�У�����˳���Ϊ/demoproject/src/org/frameworkset/mvc/HelloWord.java

����˿���������ͨ���ƶ�ע��@ResponseBody��datatype����Ϊjsonp��ָʾmvc�����Ҫ����Ӧֵת��Ϊjsonp����+json�������ݵĺϷ�JavaScript�ű����ص��ͻ���
�Ա�ʵ�ֿ�վ�������󽻻���HelloWord.java�ṩ����������һ����Ӧ����ʹ��jquery������һ��Ӧ����JavaScript�������
public @ResponseBody(datatype="jsonp") JsonpBean jsonpwithjquery()
	{
		JsonpBean jsonpbean = new JsonpBean();
		jsonpbean.setPrice("91.42");
		jsonpbean.setSymbol("IBM jquery jsonp");
		return jsonpbean;

	}
	public @ResponseBody(datatype="jsonp") JsonpBean jsonp()
	{
		JsonpBean jsonpbean = new JsonpBean();
		jsonpbean.setPrice("91.42");
		jsonpbean.setSymbol("IBM");
		return jsonpbean;
	}
	�ֱ��Ӧ�����ַΪ��
	http://localhost:8081/demoproject/examples/jsonpwithjquery.page
	http://localhost:8081/demoproject/examples/jsonp.page	
�ͻ���ʵ��:
�ͻ���Ӧ�úͷ�����Ӧ������������ͬվ����߲�ͬ������
�ͻ��˲�����bboss-mvc��webӦ����,��Ӧ�ķ��ʵ�ַΪ��
http://localhost:8081/bboss-mvc/jsp/jsonp/testjsonp.jsp
testjsonp.jsp��صĴ������£�
<!-- ��ͨ��jsonp����ʾ����ʼ���������ص����� -->
	<script type="text/javascript">
        	function jsonpCallback(result)
        	{
				alert("aaa:" + result.symbol);//������վ ���󷵻ص�json���ݶ����symbol���Ե�ֵ
        	}
    	</script>
    <!-- ��ͨ��jsonp����ʾ��������һ��Ӧ��demoproject����mvc���󣬲�ָ����callback���������ֿ�����ָ����ָ���ص�����jsonpCallback-->
	<script type="text/javascript" src="http://localhost:8081/demoproject/examples/jsonp.page?jsonp_callback=jsonpCallback"></script>
	<!-- ��ͨ��jsonp����ʾ������-->
	
	<!-- ����jqueryʵ��jsonp����ʾ����ʼ-->
	<script src="<%=request.getContextPath() %>/include/jquery-1.4.2.min.js" type="text/javascript"></script>   
	<!-- ����jqueryʵ��jsonp����ʾ��--> 
	<script type="text/javascript">
        $(function() {
            $.getJSON("http://localhost:8081/demoproject/examples/jsonpwithjquery.page?jsonp_callback=?", function(data) {
            	alert("bbb:" + data.symbol);//������վ ���󷵻ص�json���ݶ����symbol���Ե�ֵ
            });
            //jsonp1337140657188({"postalcodes":[{"adminName2":"Westchester","adminCode2":"119","postalcode":"10504","adminCode1":"NY","countryCode":"US","lng":-73.700942,"placeName":"Armonk","lat":41.136002,"adminName1":"New York"}]});
            $.getJSON("http://www.geonames.org/postalCodeLookupJSON?postalcode=10504&country=US&callback=?", function(data) {
            	alert( data.postalcodes[0].adminName2);//����һ��������������õ�ʵ����ȷ���ܹ�������������վ ���󷵻ص�json���ݶ����������Եĵ�һ��Ԫ�ص�����adminName2��ֵ
            }); 
        });        
    </script>
	<!-- ����jqueryʵ��jsonp����ʾ������-->

����bboss mvc��jsonp�ṩ��Ĭ�ϵ�֧�֣������json��������ֱ���˲���jsonp_callback
http://localhost:8081/demoproject/examples/jsonpwithjquery.page?jsonp_callback=?
jsonpCallback=?������Ϊʹ����jquery��$.getJSON��������������󣬻ص������Ǹ�����������jquery��ܻ�Ϊ��������������һ������������ƣ�Ȼ��?�滻Ϊʵ�ʵĺ�������
�ύ����������
http://localhost:8081/demoproject/examples/jsonp.page?jsonp_callback=jsonpCallback
jsonp_callback=jsonpCallback�����Կ��������Ѿ�ֱ��ָ���˻ص����������ƣ�����֮ǰ�����ʵ������jsonpCallback()

��Ҫ˵�����ǣ��ص�������Ӧ�Ĳ�������jsonp_callback��MappingJacksonHttpMessageConverter���õ�Ĭ�ϵĻص������������ƣ�
���ǿ���ȫ�ֵظı�������������ƣ���bboss-mvc.xml�ļ�����MappingJacksonHttpMessageConverter������޸�f:jsonpCallback���Ե�ֵ���ɣ�
<property class="org.frameworkset.http.converter.json.MappingJacksonHttpMessageConverter"
					f:jsonpCallback="jsonp_callback"/>

	
ִ��ʵ����
����ֻ��Ҫ��bboss-mvc�µ�WebRoot��bestpractice\demoproject\WebRoot��Ӧ��Ӧ�ò���tomcat������tomcat��Ȼ���������������
http://localhost:8081/bboss-mvc/jsp/jsonp/testjsonp.jsp
�ȿ��Կ���ʵ�ʵ�Ч��	
o org.frameworkset.http.converter.FileMessageConvertor���֧������Resource�ӿڶ�Ӧ����Դ
Ŀǰ֧��Resource�ӿڵ�����ʵ�֣�
ClassPathResource -- ������Ӧclasspath�������Դ
ServletContextResource --������webӦ�ø�Ŀ¼����Ŀ¼�µ���Դ
FileSystemResource --�������ļ�ϵͳ���ļ���Դ
UrlResource --������url���Ӷ�Ӧ��Դ
ByteArrayResource--�����ڶ�������Դ

ʹ�÷������£�
public @ResponseBody
	Resource exportExeclTemplate(HttpServletRequest request, HttpServletResponse response, String excelType,
			String module) throws Exception {
		
		String fileName = "com/sany/mms/background/action/exceldata.xls";
		
		ClassPathResource classpath = new ClassPathResource(fileName);
		return classpath;
	}

------2012-05-15-------------
o �����ϴ��������ƣ�
1.֧��html5�����ϴ���Ŀǰֻ֧�ֵ����ϴ�һ���ļ�
2.����IgnoreFieldNameMultipartFile�ӿ�������ʶ��֪��input file�����Ƶ�����£����ϴ��ĸ����뷽���������߶������Խ��а�
public @ResponseBody String upload(IgnoreFieldNameMultipartFile[] filedata,String testparam) throws IllegalStateException, IOException
public @ResponseBody String upload(IgnoreFieldNameMultipartFile filedata,String testparam) throws IllegalStateException, IOException
������鿴����������
\bestpractice\demoproject\src\org\frameworkset\mvc\FileController.java
\bestpractice\demoproject\WebRoot\xheditor\demos\demo08.jsp

------2012-05-08------------- 
o mvc���ʻ��������ƣ�
����org.frameworkset.web.servlet.i18n.SessionLocalResolver�࣬�Ա��session�л�ȡ�û���¼ʱ�洢��Locale����Ĭ�ϵ�keyΪ
org.frameworkset.web.servlet.i18n.SESSION_LOCAL_KEY
����û���Ҫʹ��SessionLocalResolver������bboss-mvc.xml�ļ��������������ã�
 <property name="localeResolver" class="org.frameworkset.web.servlet.i18n.SessionLocalResolver"/>
�Ա㸲��Ĭ�ϵ�LocalResolver���org.frameworkset.web.servlet.i18n.AcceptHeaderLocaleResolver(Ĭ�ϸ��ݿͻ���������Locale��������Ӧ��code)

mvc���ʻ���ǩ��ʹ�÷�ʽ��
<pg:message  code="probe.jsp.wrongparams"/>

mvc���ʻ����messageSource�Ļ�ȡ������
org.frameworkset.spi.support.MessageSource messageSource = org.frameworkset.web.servlet.support.WebApplicationContextUtils.getWebApplicationContext();
������ο�����jspҳ�棺/bboss-mvc/WebRoot/jsp/demo.jsp

mvc���ʻ������ļ�·��Ĭ��Ϊ
/bboss-mvc/WebRoot/WEB-INF/messages.properties
/bboss-mvc/WebRoot/WEB-INF/messages_zh_CN.properties
/bboss-mvc/WebRoot/WEB-INF/messages_en_US.properties
�ȵ�
���Ҫ����Ĭ�ϵ����ã����޸�bboss-mvc.jar/org/frameworkset/web/servlet/DispatcherServlet.properties�ļ����ݣ�
messageSource.basename=/WEB-INF/messages
���ﲻ��Ҫ�����ұ�ʶ��ϵͳ���Զ���/WEB-INF/Ŀ¼�²��Ҷ�Ӧ�������Ե������ļ�������ж�������ļ������ö��ŷָ���磺
messageSource.basename=/WEB-INF/messages,/WEB-INF/messages1,/WEB-INF/messages2

���ʻ������ļ��ȼ��ػ������ã�
Ĭ��5�����ļ��Ƿ�Ķ�������иĶ�mvc��ܻ����¼������޸ĵ��ļ���û���޸ĵ��ļ��������¼���
��������θĻ������޸�bboss-aop.jar/aop.properties�ļ���ѡ��
#���ʻ������ļ�������ʱ��������λΪ���룬Ĭ��Ϊ5����
resourcefile.refresh_interval=5000
����>0�������ȼ��ػ��ƣ�<=0�������ȼ��ػ��ƣ����������뿪��������������ر�
o �Ľ�beaninfo��list��ǩ�쳣����ʽ����ϵͳ���쳣�������־�ļ��У���־����Ϊinfo��
o bboss mvc վ����Դ���ؿ��Ե���ļ���������
o ���������ļ���Դ�����б����ļ����ƽ�������
o ���Ӱ�ȫ��֤������,�ϳ�mvc��ȫ��֤������,�ù����������������ԣ�
	preventDispatchLoop = false;//ѭ����ת��⿪�أ�true���ã�false���ã�Ĭ��Ϊfalse
	http10Compatible = true; //whether to stay compatible with HTTP 1.0 clients,true��ʶ���ݣ�false��ʶ�����ݣ�Ĭ��Ϊtrue
	redirecturl = "/login.jsp"; //ָ�����ʧ���ض����ַ��Ĭ��Ϊlogin.jsp,����ȫ��֤���ʧ����ת��ָ����ҳ��

	/**
	 * ��ת��ʽ������������ֵ��Ĭ��Ϊredirect
	 * include
	 * redirect
	 * forward
	 */
	directtype = "redirect";

	
	//��ȷָ����Ҫ����ҳ�淶Χ������ö��ŷָ�����ѡ�����û��������ɨ������ҳ�棨����patternsExcludeָ�������ҳ�棩
	//������ָ������ͨ���*��ҳ���ַ������ģ��ƥ����ҳ��
	patternsInclude;
	//��ȷָ������Ҫ����ҳ�淶Χ������ö��ŷָ�����ѡ�����û��������ɨ������ҳ�����ɨ��patternsIncludeָ����ҳ��
	//������ָ������ͨ���*��ҳ���ַ������ģ��ƥ����ҳ��
	patternsExclude;
���÷������£�
 <filter> 
    <filter-name>securityFilter</filter-name> 
    <filter-class>org.frameworkset.web.interceptor.MyFirstAuthFilter</filter-class> 
    <init-param> 
      <param-name>patternsExclude</param-name> 
      <param-value> 
    	/login.jsp
       </param-value> 
    </init-param> 
    <init-param> 
      <param-name>redirecturl</param-name> 
      <param-value>/login.jsp</param-value> 
    </init-param> 
    <init-param> 
      <param-name>preventDispatchLoop</param-name> 
      <param-value>true</param-value> 
    </init-param>     
  </filter> 

//Ҫɨ���ҳ���������
  <filter-mapping> 
    <filter-name>securityFilter</filter-name> 
    <url-pattern>*.jsp</url-pattern> 
  </filter-mapping> 
  <filter-mapping> 
    <filter-name>securityFilter</filter-name> 
    <url-pattern>*.page</url-pattern> 
  </filter-mapping> 
  <filter-mapping> 
    <filter-name>securityFilter</filter-name> 
    <url-pattern>*.htm</url-pattern> 
  </filter-mapping>
  <filter-mapping> 
    <filter-name>securityFilter</filter-name> 
    <url-pattern>*.html</url-pattern> 
  </filter-mapping>
   <filter-mapping>  
        <filter-name>securityFilter</filter-name>  
        <url-pattern>*.ajax</url-pattern>  
    </filter-mapping>
    <filter-mapping>  
        <filter-name>securityFilter</filter-name>  
        <url-pattern>/rest/*</url-pattern>  
    </filter-mapping>
    
    
o �Ľ�StringHttpMessageConverterת����������responseCharset���ԣ�����ȫ��ָ��@ResponseBody String������Ӧ���ַ����룬
Ĭ��ֵΪ"ISO-8859-1"��ʹ�÷���Ϊf:responseCharset="UTF-8"�����磺
<property class="org.frameworkset.http.converter.StringHttpMessageConverter" f:responseCharset="UTF-8"/>
�������µ���Ӧ�ַ���������UTF-8���������
public  @ResponseBody String queryMutiSex(SexType[] sex)
{
	return "���";//����UTF-8����
}

����ڿ�������������ָ�����ַ����룬�򽫸���StringHttpMessageConverterת�����ϵ��ַ�����,���磺
public  @ResponseBody(charset="GBK") String queryMutiSex(SexType[] sex)
{
	return "���";//����GBK��Ӧ����
}


#######update function list since bbossgroups-3.4 begin###########
------2012-2-7------------
o �������ͣ�List,Map��,���û�����ݼ�¼����ֱ�ӷ��أ��޸�û����������·���һ���ռ�¼������
------2011-12-11------------
o �������µ�bboss aop��ܣ���������Ҫע���ҵ�����������Ҫset����
o �Ľ�����ע�⣬name����value���Բ����Ǳ���ѡ����û��ָ��name���ԣ���ʹ��
��������������Ϊname���Ե�ֵ��
RequestParam
PathVariable
CookieValue
RequestHeader
Attribute

Ϊ��ȷ���ܹ���ȷ��ȡ��mvc�����������Ĳ������ƣ������ڱ���java����ʱ��֤��class�ļ�������java���ر�����
eclipse����Ĭ�ϻ����ɣ�ant���빤��Ҳ�����ɡ�
ͬʱ����PathVariableע�⣬����ע�ⲻ���ǿ��������������ı���ע�⣬���û��ָ�����������Ͳ�����ֱ���÷������ƴ�request�л�ȡ������Ե�ֵ��
RequestParam
PathVariable
RequestHeader
Attribute
CookieValue

bboss�н綨�Ļ��������������£�
		String.class,int.class ,Integer.class,
		long.class,Long.class,
		java.sql.Timestamp.class,java.sql.Date.class,java.util.Date.class,
		boolean.class ,Boolean.class,
		BigFile.class,
		float.class ,Float.class,
		short.class ,Short.class,
		double.class,Double.class,
		char.class ,Character.class,
		
		byte.class ,Byte.class,
		BigDecimal.class

------2011-11-2------------
o �Ľ�jasonת������������ļ�����ת�����ĳ�ͻ
#######update function list since bbossgroups-3.3 begin###########
------2011-09-29-----------
o ����spi������ģ��
------2011-09-22-------------
o ֵ�����������������MultipartFile����ʱ����������Ǹ����ϴ���������MultipartFile�İ󶨲���
�����Ѻ���ʾ��
EvaluateMultipartFileParamWithNoName for type["+ type.getCanonicalName() +"] fail: form is not a multipart form,please check you form config.
------2011-09-18-------------
o ������ܼ��ʵ�������ʵ�ַ��
http://localhost:8080/bboss-mvc/monitor/spiFrame.jsp
���ԶԿ�ܹ���������mvc��������ȫ���������ԡ�sqlfile�е�sql����������Ϣ���м��
------2011-09-14-------------
o ȥ��������������������MultipartFile��MultipartFile[]�����RequestParamע��һ��ʹ�õ�����
#######update function list since bbossgroups-3.2 begin###########
------2011-07-24-------------
o ���������������󶨻�������MultipartFile��MultipartFile[]���Ͱ�֧��,�����RequestParamע��һ��ʹ�ã�ʹ�÷������£�
public String uploadFileWithMultipartFile(@RequestParam(name="upload1")  MultipartFile file,
			ModelMap model)
public String uploadFileWithMultipartFiles(@RequestParam(name="upload1")  MultipartFile[] files,
			ModelMap model)
o PO�����������ݰ󶨻�������MultipartFile��MultipartFile[]���Ͱ�֧��,���Ժ�RequestParamע��һ��ʹ�ã�Ҳ����ֱ������������ֱ�Ӱ󶨣�ʹ�÷������£�
public String uploadFileWithFileBean(FileBean files)

FileBean��һ���Զ����java bean���ṹ���£�
public class FileBean
{
	private MultipartFile upload1;
	@RequestParam(name="upload1")
	private MultipartFile[] uploads;
	@RequestParam(name="upload1")
	private MultipartFile anupload;
	//ʡ�����Ե�get/set����
}	
o ����@ResponseBodyע�⣬����ֱ�Ӷ��ļ����ع��ܵ�֧�֣�ֻҪ��������������File���󼴿�
o ������֤���������ܣ�������֤ʧ�ܺ���תҳ��ķ�ʽΪredirect��forward���֣�������������������directtype����
 ��ʵ�־������ת��ʽ��
 <property class="org.frameworkset.web.interceptor.MyFirstInterceptor">
     			<!-- ������֤�������������urlģʽ���� -->
     			<property name="patternsInclude">
     				<list componentType="string">
     					<property value="/**/*.htm"/>
     				</list>
     			</property>
     			<!-- ������֤���������������urlģʽ���� -->
     			<property name="patternsExclude">
     				<list componentType="string">
     					<property value="/*.html"/>
     				</list>
     			</property>
     			<property name="redirecturl" value="/login.jsp"/>
     			<property name="directtype" value="forward"/>
     		</property>
o �޸�mvc��ҳ��תҳ��Ϊ����ʱ������������ҳ������
o �޸�ResponseBodyָ�����ݷ������ͺ��ַ�������Ч������
#######update function list since bbossgroups-3.1 begin###########
------2011-06-09------------
o ע����������Բ������ע��@ControllerҲ���Ա����ʶ����
------2011-06-06------------
o ����responsebodyע�⹦��,����datatype��charset��������
datatype��json,xml��ֵ������ָ��������ݵ�content����
charset������ָ��reponse��Ӧ�ַ�����
��ϸʹ�÷�����ο���������
org.frameworkset.web.enumtest.EnumConvertController
org.frameworkset.web.http.converter.json.JsonController
------2011-05-28------------
o ֧��Map<Key,PO>���Ͳ����󶨻��ƣ�ͨ�����ֻ��ƿ��Էǳ�����ؽ����ύ������һ�����󼯺�����
����key��Ӧ���ֶ�ֵ���γ�Map��������
ʹ�÷������£�
public String mapbean(@MapKey("fieldName") Map<String,ListBean> beans, ModelMap model) {
		String sql = "INSERT INTO LISTBEAN (" + "ID," + "FIELDNAME,"
				+ "FIELDLABLE," + "FIELDTYPE," + "SORTORDER,"
				+ " ISPRIMARYKEY," + "REQUIRED," + "FIELDLENGTH,"
				+ "ISVALIDATED" + ")" + "VALUES"
				+ "(#[id],#[fieldName],#[fieldLable],#[fieldType],#[sortorder]"
				+ ",#[isprimaryKey],#[required],#[fieldLength],#[isvalidated])";
		TransactionManager tm = new TransactionManager();
		try {
			if(beans != null)
			{
				List temp =  convertMaptoList( beans);
				
				tm.begin();
				SQLExecutor.delete("delete from LISTBEAN");
				SQLExecutor.insertBeans(sql, temp);
				tm.commit();
				model.addAttribute("datas", temp);
			}
		} catch (Exception e) {
			try {
				tm.rollback();
			} catch (RollbackException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "path:mapbean";
	}
	������ο�����������
	/bboss-mvc/WebRoot/WEB-INF/bboss-mapbean.xml
------2011-05-11------------
o ����restfulʵ�ֻ��ƣ�����֧�ֶԿ��������������restfulȫ��ַӳ���ϵ���޸��˲���ȱ�ݣ�3.0�汾��Ҫ�����༶��ͷ��������ַ��ϲ���ʵ��
  restful���Ĺ��ܣ����Ҵ���ȱ�ݡ�
#######update function list since bbossgroups-3.1 end###########
#######update function list since bbossgroups-3.0 begin###########
------2011-04-30------------
o �󶨲���ע��ָ������ת����ʽ���Ա㱣֤��ԭʼ���ݸ�ʽ������ת��Ϊ��ȷ������
o ���Խ���������(java.util.Date/java.sql.Date/java.sql.Timestamp)ת��Ϊlong�������ݣ�Ҳ���Խ�long����ת��Ϊ��������(java.util.Date/java.sql.Date/java.sql.Timestamp)��
Ҳ���Խ���long�������������(java.util.Date/java.sql.Date/java.sql.Timestamp)������໥ת��
o �޸�mvc��ܿ�������������쳣��
	java.lang.IllegalArgumentException: Class must not be null
	at org.frameworkset.util.Assert.notNull(Assert.java:112)
	at org.frameworkset.util.annotations.AnnotationUtils.findAnnotation(AnnotationUtils.java:129)
	at org.frameworkset.web.servlet.handler.HandlerUtils.determineUrlsForHandler(HandlerUtils.java:1965)

------2011-04-14------------
o mvc�д��ݸ���ҳ��ǩ�ĵ���·���޸�Ϊ�������ĵľ��Ե�ַ��������ʹ��jqueryģʽ�ֲ���ҳʱ����ҳ�����Ե�ַ�ͷ�ҳ��Ӧ��ҳ������·����һ��ʱ��������ȷ��
���з�ҳ����
------2011-04-13------------
o ����������������Map���Ͳ����󶨻��ƣ����Խ�request�еĲ���ת��ΪMap���󣬵�����������ʱ��������ֵ��������뵥��ֵ
------2011-04-06------------
o ����mvc��������ļ����뷽ʽ
������,�ŷָ���������ļ�,���磺
<servlet>
	<servlet-name>mvcdispather</servlet-name>
	<servlet-class>org.frameworkset.web.servlet.DispatchServlet</servlet-class>
	<init-param>
		<param-name>contextConfigLocation</param-name>
		<param-value>/WEB-INF/bboss-*.xml,
					/WEB-INF/conf/bboss-*.xml</param-value>
	</init-param>
������������
</servlet>

------2011-04-05------------
o ��������������ö�����ͣ�ö���������Ͳ����İ󶨹���
------2011-03-31------------
o ��ת·������ͨ��path��Ԫ��ֱ��ָ����������ע��
����ʹ�÷������ο�demo
WebRoot/WEB-INF/bboss-path.xml
------2011-03-24------------
o �޸�RequestContext.getPathWithinHandlerMappingPath(request)��ȡƥ��·��ʱ����Ϊ���ҳ�����Ե�ַʱ����404�������⣬�޸ĵĳ������£�
�޸�֮ǰ

org.frameworkset.web.servlet.support.RequestContext

public static String getPathWithinHandlerMappingPath(HttpServletRequest request)
{
	
	return (String) request
	.getAttribute(org.frameworkset.web.servlet.HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
}
	�޸�֮��
	public static String getPathWithinHandlerMappingPath(HttpServletRequest request)
	{
		String urlpath = (String) request
		.getAttribute(org.frameworkset.web.servlet.HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
		 if(urlpath != null && urlpath.startsWith("/") )
		 {
			 urlpath = request.getContextPath() + urlpath;
		 }
		 return urlpath;
//		return (String) request
//		.getAttribute(org.frameworkset.web.servlet.HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
	}
   

------2011-03-02------------
 
o �޸��ļ��ϴ������ַ��������⣬swfuploadͳһ����utf-8���룬��˽�
jquery.MultiFile�ı���Ҳ�ƶ�Ϊutf-8
�ϴ���jspҳ��ı���ͳһ�ƶ�Ϊutf-8

#######update function list since bbossgroups-3.0 end###########
bbossgroups 2.1----bboss-mvc

***************************************************
2010-11-24 bboss-mvc ��ܹ�������
***************************************************
o ������ƥ��controller
o �෽��ƥ��controller
o ע��controller
o restful controller
o ���ʻ�
o ����
o ����У����Ϣչʾ��ǩ
o ���ݿ�У����
o json/rss/atom ֧��
o ��ҳ֧��


o ��Ϣת�����������
	o ����ע��
	o ����ע��
o ������	
o ����У��
 �����Ƿ���У��
У����Ϣ����
У���д
У�����
���ݰ�
У������д
У��ʧ����תҳ�����ú���Ч

����ʵ��

���ļ��ϴ�

��ͬ��Ӧ�÷�����jar�����أ�
�����·����ڲ���ͨ����tomcat 6.0��weblogic 11(��̬��δ����)��websphere 7��apusic
δͨ����������ren


