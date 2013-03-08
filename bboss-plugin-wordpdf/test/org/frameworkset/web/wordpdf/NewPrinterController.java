package org.frameworkset.web.wordpdf;
import java.io.File;

import org.frameworkset.http.FileBlob;
import org.frameworkset.http.converter.wordpdf.FileConvertor;
import org.frameworkset.util.annotations.ResponseBody;

public class NewPrinterController {
	private String flashpaperWorkDir ;
	private String swftoolWorkDir;
	private String officeHome;
	public @ResponseBody FileBlob getPDF() throws Exception  {
		System.out.println("--------------����ִ�е��˴�------------------");
		
		String[] bookMarks = new String[] { "DealerName", "Name", "CgName",
				"TypeName", "OrderQty", "CoolCode", "ChassisCode", "CustPrice",
				"CustAmt", "sumall", "EarnestPayDays", "EarnestAmt",
				"StageDate", "FirstAmt", "DepositPercent", "Deposit",
				"ServiceChargePercent", "ServiceCharge", "NotarizationFee",
				"InsuranceTerm", "Insurance", "ReinsuranceDeposit",
				"FinanceAmt", "FinanceFC", "LackAmtPayDate",
				"LackAmtFinalPayDate", "ReceiverName", "ReceiverID",   
				"ReceiverTel", "Insurer","authoriate" };
		String[] mapValue = new String[] { "��˾", "��˾",
				"��", "dd 52E(6)", "2", "����", "V09660", "300.00",
				"600.00", "½����Ԫ��", "7", "100", "2012��8��31��", "60", "5", "3",
				"10", "6", "10", "5", "10", "21", "540", "10", "2012��8��31��",
				"2012��8��31��", "��", "430111199910102121", "13800138200", "��","bboss" };
		 
		String wordtemplate = "D:\\workspace\\bbossgroups-3.6.0\\bboss-plugin-wordpdf\\plugin\\wordpdf\\anjie.doc";
		String wordfile = "d:\\anjie_test.doc";
		String pdftmp = "d:\\contracttemp.pdf";
		String topdfpath = "d:\\contract.pdf";
		String[] mergePdfFiles = new String[]{"D:\\workspace\\bbossgroups-3.6.0\\bboss-plugin-wordpdf\\plugin\\wordpdf\\contract_part2.pdf" } ;
		long waittimes = 1000;
		FileConvertor.realWordConvertorByFlashPager(flashpaperWorkDir, wordtemplate, wordfile, bookMarks, mapValue,pdftmp, waittimes);
		FileConvertor.mergePdfFiles(mergePdfFiles, pdftmp, topdfpath, FileConvertor.MERGE_AFTER);
	
		FileBlob fileblob = new FileBlob(topdfpath,FileBlob.BROWSER);
		return fileblob;
		

	}
	public @ResponseBody FileBlob getPDFTemp() throws Exception  {
		System.out.println("--------------����ִ�е��˴�------------------");
		String[] bookMarks = new String[] { "DealerName", "Name", "CgName",
				"TypeName", "OrderQty", "CoolCode", "ChassisCode", "CustPrice",
				"CustAmt", "sumall", "EarnestPayDays", "EarnestAmt",
				"StageDate", "FirstAmt", "DepositPercent", "Deposit",
				"ServiceChargePercent", "ServiceCharge", "NotarizationFee",
				"InsuranceTerm", "Insurance", "ReinsuranceDeposit",
				"FinanceAmt", "FinanceFC", "LackAmtPayDate",
				"LackAmtFinalPayDate", "ReceiverName", "ReceiverID",   
				"ReceiverTel", "Insurer","authoriate" };
		String[] mapValue = new String[] { "��˾", "��˾",
				"��", "dd 52E(6)", "2", "����", "V09660", "300.00",
				"600.00", "½����Ԫ��", "7", "100", "2012��8��31��", "60", "5", "3",
				"10", "6", "10", "5", "10", "21", "540", "10", "2012��8��31��",
				"2012��8��31��", "��", "430111199910102121", "13800138200", "��","bboss" };
		
		String wordtemplate = "D:\\workspace\\bbossgroups-3.6.0\\bboss-plugin-wordpdf\\plugin\\wordpdf\\anjie.doc";
		String wordfile = "d:\\anjie_test.doc";
		String topdfpath = "d:\\contract.pdf";
		long waittimes = 1000;
		FileConvertor.realWordConvertorByFlashPager(flashpaperWorkDir, wordtemplate, wordfile, bookMarks, mapValue,topdfpath, waittimes);
		FileBlob fileblob = new FileBlob(topdfpath,FileBlob.BROWSER);
		return fileblob;
		

	}
	public @ResponseBody FileBlob getSWFTemp() throws Exception  {
		System.out.println("--------------����ִ�е��˴�------------------");
		String[] bookMarks = new String[] { "DealerName", "Name", "CgName",
				"TypeName", "OrderQty", "CoolCode", "ChassisCode", "CustPrice",
				"CustAmt", "sumall", "EarnestPayDays", "EarnestAmt",
				"StageDate", "FirstAmt", "DepositPercent", "Deposit",
				"ServiceChargePercent", "ServiceCharge", "NotarizationFee",
				"InsuranceTerm", "Insurance", "ReinsuranceDeposit",
				"FinanceAmt", "FinanceFC", "LackAmtPayDate",
				"LackAmtFinalPayDate", "ReceiverName", "ReceiverID",   
				"ReceiverTel", "Insurer","authoriate" };
		String[] mapValue = new String[] { "��˾", "��˾",
				"��", "dd 52E(6)", "2", "����", "V09660", "300.00",
				"600.00", "½����Ԫ��", "7", "100", "2012��8��31��", "60", "5", "3",
				"10", "6", "10", "5", "10", "21", "540", "10", "2012��8��31��",
				"2012��8��31��", "��", "430111199910102121", "13800138200", "��","bboss" };
		String hetongbianhao="20121222";
		String wordtemplate = "D:\\workspace\\bbossgroups-3.6.0\\bboss-plugin-wordpdf\\plugin\\wordpdf\\anjie.doc";
		String wordfile = "d:\\test\\anjie_test.doc";
		String toswfpath = "d:\\test\\contract_"+hetongbianhao+".swf";
		File f = new File(toswfpath);
		if(!f.exists())
		{
			long waittimes = 1000;
			FileConvertor.realWordConvertorByFlashPager(flashpaperWorkDir, wordtemplate, wordfile, bookMarks, mapValue,toswfpath, waittimes);
			
		}
		FileBlob fileblob = new FileBlob(toswfpath,FileBlob.BROWSER);
		return fileblob;
		

	}
	public @ResponseBody FileBlob getSWFTempUseOpenOffice() throws Exception  {
		System.out.println("--------------����ִ�е��˴�------------------");
		String[] bookMarks = new String[] { "DealerName", "Name", "CgName",
				"TypeName", "OrderQty", "CoolCode", "ChassisCode", "CustPrice",
				"CustAmt", "sumall", "EarnestPayDays", "EarnestAmt",
				"StageDate", "FirstAmt", "DepositPercent", "Deposit",
				"ServiceChargePercent", "ServiceCharge", "NotarizationFee",
				"InsuranceTerm", "Insurance", "ReinsuranceDeposit",
				"FinanceAmt", "FinanceFC", "LackAmtPayDate",
				"LackAmtFinalPayDate", "ReceiverName", "ReceiverID",   
				"ReceiverTel", "Insurer","authoriate" };
		String[] bookdatas = new String[] { "��˾", "��˾",
				"��", "dd 52E(6)", "2", "����", "V09660", "300.00",
				"600.00", "½����Ԫ��", "7", "100", "2012��8��31��", "60", "5", "3",
				"10", "6", "10", "5", "10", "21", "540", "10", "2012��8��31��",
				"2012��8��31��", "��", "430111199910102121", "13800138200", "��","bboss" };
		String hetongbianhao="20121222";
		String wordtemplate = "D:\\workspace\\bbossgroups-3.6.0\\bboss-plugin-wordpdf\\plugin\\wordpdf\\anjie.doc";
		String pdfpath = "d:\\test\\anjieswftools_"+hetongbianhao+".pdf";
		String wordfile = "d:\\test\\anjie_testswftools"+hetongbianhao+".doc";
		String toswfpath = "d:\\test\\contractswftools_"+hetongbianhao+".swf";
//		String officeHome = "E:\\Program Files\\OpenOffice.org 3";
		File f = new File(toswfpath);
		if(!f.exists())
		{
//			FileConvertor.initXComponentContext(officeHome);
			FileConvertor.init( officeHome);
			FileConvertor.getRealWord(wordtemplate, wordfile,bookMarks, bookdatas);
			FileConvertor.wordToPDFByOpenOffice(wordfile, pdfpath);
			FileConvertor.swftoolsConvert(swftoolWorkDir, pdfpath, toswfpath);
//			FileConvertor.realWordConvertorBySWFTool( swftoolWorkDir,  wordtemplate,
//					 wordfile,  bookMarks, bookdatas, pdfpath,
//					 toswfpath);
			
		}
		FileBlob fileblob = new FileBlob(toswfpath,FileBlob.BROWSER);
		return fileblob;
		

	}
	public @ResponseBody
	FileBlob contractPrint()
			throws Exception {
		
		String hetongbianhao = "123";
		String newfilepath = "d:/test/";
		String wordtemplate ="D:/workspace/bbossgroups-3.6.0/bboss-plugin-wordpdf/contract_mb.doc";
		File file=new File(newfilepath +hetongbianhao);
		if(!file.exists()){
			file.mkdirs();
		}
		String pdfpath = newfilepath +hetongbianhao+"/"+hetongbianhao
				+ ".pdf";
		String wordfile = newfilepath+ hetongbianhao+"/"+hetongbianhao
				+ ".doc";
		String toswfpath = newfilepath+ hetongbianhao+"/"+hetongbianhao
				+ ".swf";
		File f = new File(toswfpath);
		String[] bookMarks = new String[] { "s1", "s2", "s3", "s4", "s5", "s6",
				"s7", "s8", "s9", "s10", "s11", "s12", "s13", "s14", "s15",
				"s16", "s17", "s18", "s19", "s20", "s21", "s22" };
		String s1 = "123";// ��ȡ�����������Ϣ
		String s2 = "Custom_name";// ��ȡ�������������Ϣ�������Ϣ
		String s3 = "Identification_card()";// ��ȡ�������������Ϣ�¸��˻�����Ϣ�����֤��Ϣ
		String s4 = "Mobile_phone()";// ��ȡ�������������Ϣ�¸��˻�����Ϣ���ֻ���Ϣ
		String s5 = "Live_address()";// ��ȡ�������������Ϣ�¸��˻�����Ϣ���־�ס��ַ
		String s6 = "" + "Loan_amount()";// ��ȡ�����������̵��ܾ���������������д��
		String s7 = "" + "Loan_amount()";// ��ȡ�����������̵��ܾ�������������Сд��
		String s8 = "" + "Time_limit()";// ��ȡ�����������̵��ܾ�������������������
		String s9 = "Loan_purpose()";// ��ȡ�������������Ϣ�еĽ����;
		String s10 = "" + "Interest_rate()";// ��ȡ�����������̵��ܾ�����������������
		String s11 = "" + "Bank_name()";// ��ȡ�������������Ϣ�Ŀ�������Ϣ
		String s12 = "Custom_name()";// ��ȡ�������������Ϣ�Ľ����
		String s13 = "Bank_account()";// ��ȡ�������������Ϣ�������˺�
		String s14 = "" + "Time_limit()";// ��ȡ�����������̵��ܾ�������������������
		String s15 = "" + "Repayment_date()";// ��ȡ�������������Ϣ��ÿ�»�����
		String s16 = "" + "Bank_name()";// ��ȡ�������������Ϣ�Ŀ�������Ϣ
		String s17 = "Custom_name()";// ��ȡ�������������Ϣ�Ľ����
		String s18 = "Bank_account()";// ��ȡ�������������Ϣ�������˺�
		String s19 = "1%";// ��ȡ�ֵ����ȡÿ�շ�Ϣ����Ϊ1��
		String s20 = "4%";// ��ȡ�ֵ����ȡ��ǰ����ʱ��6����֮�ڵ�ΥԼ��4%
		String s21 = "3%";// ��ȡ�ֵ����ȡ��ǰ����ʱ������6���µ�ΥԼ��Ϊ3%
		String s22 = "1%";// ��ȡ�ֵ��ˢ������������1%
		String[] bookdatas = new String[] { s1, s2, s3, s4, s5, s6, s7, s8, s9,
				s10, s11, s12, s13, s14, s15, s16, s17, s18, s19, s20, s21, s22 };
		if (!f.exists()) {
			//FileConvertor.initXComponentContext(openofficeHomeDir);
			FileConvertor.init(officeHome);
			FileConvertor.getRealWordByOpenoffice(wordtemplate, wordfile,
					bookMarks, bookdatas);
			FileConvertor.wordToPDFByOpenOffice(wordfile, pdfpath);
			FileConvertor.swftoolsConvert(swftoolWorkDir, pdfpath, toswfpath);
		}
		FileBlob fileblob = new FileBlob(toswfpath, FileBlob.BROWSER);
		return fileblob;
	}
	public @ResponseBody FileBlob getSWFTempAllUseOpenOffice() throws Exception  {
		System.out.println("--------------����ִ�е��˴�------------------");
		String[] bookMarks = new String[] { "DealerName", "Name", "CgName",
				"TypeName", "OrderQty", "CoolCode", "ChassisCode", "CustPrice",
				"CustAmt", "sumall", "EarnestPayDays", "EarnestAmt",
				"StageDate", "FirstAmt", "DepositPercent", "Deposit",
				"ServiceChargePercent", "ServiceCharge", "NotarizationFee",
				"InsuranceTerm", "Insurance", "ReinsuranceDeposit",
				"FinanceAmt", "FinanceFC", "LackAmtPayDate",
				"LackAmtFinalPayDate", "ReceiverName", "ReceiverID",   
				"ReceiverTel", "Insurer","authoriate" };
		String[] bookdatas = new String[] { "��˾", "��˾",
				"��", "dd 52E(6)", "2", "����", "V09660", "300.00",
				"600.00", "½����Ԫ��", "7", "100", "2012��8��31��", "60", "5", "3",
				"10", "6", "10", "5", "10", "21", "540", "10", "2012��8��31��",
				"2012��8��31��", "��", "430111199910102121", "13800138200", "��","bboss" };
		String hetongbianhao = "20121222";
	    String wordtemplate = "/opt/tomcat/wordpdf/anjie.doc";
	    String pdfpath = "/opt/tomcat/test/anjieswftools_" + hetongbianhao + ".pdf";
	    String wordfile = "/opt/tomcat/test/anjie_testswftools" + hetongbianhao + ".doc";
	    String toswfpath = "/opt/tomcat/test/contractswftools_" + hetongbianhao + ".swf";

//		String officeHome = "E:\\Program Files\\OpenOffice.org 3";
		File f = new File(toswfpath);
		if(!f.exists())
		{
			System.out.println("officeHome__________:" + officeHome);
			FileConvertor.init( officeHome);
						
			FileConvertor.getRealWordByOpenoffice(wordtemplate, wordfile,bookMarks, bookdatas);
			FileConvertor.wordToPDFByOpenOffice(wordfile, pdfpath);
			FileConvertor.swftoolsConvert(swftoolWorkDir, pdfpath, toswfpath);
//			FileConvertor.realWordConvertorBySWFTool( swftoolWorkDir,  wordtemplate,
//					 wordfile,  bookMarks, bookdatas, pdfpath,
//					 toswfpath);
			
		}
		FileBlob fileblob = new FileBlob(toswfpath,FileBlob.BROWSER);
		return fileblob;
		

	}
	
	public @ResponseBody FileBlob getWordToPDFByOpenOffice() throws Exception  {
		System.out.println("--------------����ִ�е��˴�------------------");
		String hetongbianhao="20131222";
		String pdfpath = "/opt/tomcat/test/anjieswftools_"+hetongbianhao+".pdf";
		String wordfile = "/opt/tomcat/wordpdf/anjie.doc";
//		String officeHome = "E:\\Program Files\\OpenOffice.org 3";
		{
			System.out.println("officeHome__________:" + officeHome);
			FileConvertor.init( officeHome);
			System.out.println("officeHome__________:" + officeHome);
			
			FileConvertor.wordToPDFByOpenOffice(wordfile, pdfpath);
//			FileConvertor.realWordConvertorBySWFTool( swftoolWorkDir,  wordtemplate,
//					 wordfile,  bookMarks, bookdatas, pdfpath,
//					 toswfpath);
			
		}
		FileBlob fileblob = new FileBlob(pdfpath,FileBlob.DOWNLOAD);
		return fileblob;
		

	}
	
	public @ResponseBody FileBlob getWordToSWFByOpenOffice() throws Exception  {
		System.out.println("--------------����ִ�е��˴�------------------");
		String hetongbianhao="20131222";
		String pdfpath = "/opt/tomcat/test/anjieswftools_"+hetongbianhao+".swf";
		String wordfile = "/opt/tomcat/wordpdf/anjie.doc";
//		String officeHome = "E:\\Program Files\\OpenOffice.org 3";
		{
			System.out.println("officeHome__________:" + officeHome);
			FileConvertor.init( officeHome);
			System.out.println("officeHome__________:" + officeHome);
			
			FileConvertor.wordToPDFByOpenOffice(wordfile, pdfpath);
//			FileConvertor.realWordConvertorBySWFTool( swftoolWorkDir,  wordtemplate,
//					 wordfile,  bookMarks, bookdatas, pdfpath,
//					 toswfpath);
			
		}
		FileBlob fileblob = new FileBlob(pdfpath,FileBlob.DOWNLOAD);
		return fileblob;
		

	}
	public @ResponseBody File returnFile()
	{
		return new File("D:\\workspace\\bbossgroups-3.6.0\\bboss-plugin-wordpdf\\plugin\\wordpdf\\anjie.doc");
	}
	public @ResponseBody FileBlob downPDFTemp() throws Exception  {
		System.out.println("--------------����ִ�е��˴�------------------");
		
		String[] bookMarks = new String[] { "DealerName", "Name", "CgName",
				"TypeName", "OrderQty", "CoolCode", "ChassisCode", "CustPrice",
				"CustAmt", "sumall", "EarnestPayDays", "EarnestAmt",
				"StageDate", "FirstAmt", "DepositPercent", "Deposit",
				"ServiceChargePercent", "ServiceCharge", "NotarizationFee",
				"InsuranceTerm", "Insurance", "ReinsuranceDeposit",
				"FinanceAmt", "FinanceFC", "LackAmtPayDate",
				"LackAmtFinalPayDate", "ReceiverName", "ReceiverID",   
				"ReceiverTel", "Insurer","authoriate" };
		String[] mapValue = new String[] { "��˾", "��˾",
				"��", "dd 52E(6)", "2", "����", "V09660", "300.00",
				"600.00", "½����Ԫ��", "7", "100", "2012��8��31��", "60", "5", "3",
				"10", "6", "10", "5", "10", "21", "540", "10", "2012��8��31��",
				"2012��8��31��", "��", "430111199910102121", "13800138200", "��","bboss" };
		String wordtemplate = "D:\\workspace\\bbossgroups-3.6.0\\bboss-plugin-wordpdf\\plugin\\wordpdf\\anjie.doc";
		String wordfile = "d:\\anjie_test.doc";
		String pdftmp = "d:\\contracttemp.pdf";
		String topdfpath = "d:\\contract.pdf";
		String[] mergePdfFiles = new String[]{"D:\\workspace\\bbossgroups-3.6.0\\bboss-plugin-wordpdf\\plugin\\wordpdf\\contract_part2.pdf" } ;
		long waittimes = 1000;
		FileConvertor.realWordConvertorByFlashPager(flashpaperWorkDir, wordtemplate, wordfile, bookMarks, mapValue,pdftmp, waittimes);
		FileConvertor.mergePdfFiles(mergePdfFiles, pdftmp, topdfpath, FileConvertor.MERGE_AFTER);
	
		FileBlob fileblob = new FileBlob(topdfpath,FileBlob.DOWNLOAD);
		return fileblob;
		

	}
	public @ResponseBody FileBlob getWord() throws Exception  {
		System.out.println("--------------����ִ�е��˴�------------------");
		String[] bookMarks = new String[] { "DealerName", "Name", "CgName",
				"TypeName", "OrderQty", "CoolCode", "ChassisCode", "CustPrice",
				"CustAmt", "sumall", "EarnestPayDays", "EarnestAmt",
				"StageDate", "FirstAmt", "DepositPercent", "Deposit",
				"ServiceChargePercent", "ServiceCharge", "NotarizationFee",
				"InsuranceTerm", "Insurance", "ReinsuranceDeposit",
				"FinanceAmt", "FinanceFC", "LackAmtPayDate",
				"LackAmtFinalPayDate", "ReceiverName", "ReceiverID",   
				"ReceiverTel", "Insurer","authoriate" };
		String[] mapValue = new String[] { "��˾", "��˾",
				"��", "dd 52E(6)", "2", "����sdfsdf", "V09660ffff", "300.00",
				"600.00", "½����Ԫ��", "7", "100", "2012��8��31��", "60", "5", "3",
				"10", "6", "10", "5", "10", "21", "540", "10", "2012��8��31��",
				"2012��8��31��", "��", "430111199910102121", "13800138200", "��","bboss" };
		String wordtemplate = "D:\\workspace\\bbossgroups-3.6.0\\bboss-plugin-wordpdf\\plugin\\wordpdf\\anjie.doc";
		String wordfile = "d:\\anjie_test.doc";
		FileConvertor.getRealWord(wordtemplate, wordfile, bookMarks,mapValue);
	
		FileBlob fileblob = new FileBlob(wordfile,FileBlob.BROWSER);
		return fileblob;
		

	}
	
	public @ResponseBody FileBlob getWordTemp() throws Exception  {
		System.out.println("--------------����ִ�е��˴�------------------");
		String[] bookMarks = new String[] { "DealerName", "Name", "CgName",
				"TypeName", "OrderQty", "CoolCode", "ChassisCode", "CustPrice",
				"CustAmt", "sumall", "EarnestPayDays", "EarnestAmt",
				"StageDate", "FirstAmt", "DepositPercent", "Deposit",
				"ServiceChargePercent", "ServiceCharge", "NotarizationFee",
				"InsuranceTerm", "Insurance", "ReinsuranceDeposit",
				"FinanceAmt", "FinanceFC", "LackAmtPayDate",
				"LackAmtFinalPayDate", "ReceiverName", "ReceiverID",   
				"ReceiverTel", "Insurer","authoriate" };
		String[] mapValue = new String[] { "��˾", "��˾",
				"��", "dd 52E(6)", "2", "����sdfsdf", "V09660ffff", "300.00",
				"600.00", "½����Ԫ��", "7", "100", "2012��8��31��", "60", "5", "3",
				"10", "6", "10", "5", "10", "21", "540", "10", "2012��8��31��",
				"2012��8��31��", "��", "430111199910102121", "13800138200", "��","bboss" };
		String wordtemplate = "D:\\workspace\\bbossgroups-3.6.0\\bboss-plugin-wordpdf\\plugin\\wordpdf\\anjie.doc";
		String wordfile = "d:\\anjie_test.doc";
		FileConvertor.getRealWord(wordtemplate, wordfile, bookMarks,mapValue);
	
		FileBlob fileblob = new FileBlob(wordfile,FileBlob.BROWSER);
		return fileblob;
		
		

	}
	
	public @ResponseBody FileBlob downWordTemp() throws Exception  {
		System.out.println("--------------����ִ�е��˴�------------------");
		String[] bookMarks = new String[] { "DealerName", "Name", "CgName",
				"TypeName", "OrderQty", "CoolCode", "ChassisCode", "CustPrice",
				"CustAmt", "sumall", "EarnestPayDays", "EarnestAmt",
				"StageDate", "FirstAmt", "DepositPercent", "Deposit",
				"ServiceChargePercent", "ServiceCharge", "NotarizationFee",
				"InsuranceTerm", "Insurance", "ReinsuranceDeposit",
				"FinanceAmt", "FinanceFC", "LackAmtPayDate",
				"LackAmtFinalPayDate", "ReceiverName", "ReceiverID",   
				"ReceiverTel", "Insurer","authoriate" };
		String[] mapValue = new String[] { "��˾", "��˾",
				"��", "dd 52E(6)", "2", "����sdfsdf", "V09660ffff", "300.00",
				"600.00", "½����Ԫ��", "7", "100", "2012��8��31��", "60", "5", "3",
				"10", "6", "10", "5", "10", "21", "540", "10", "2012��8��31��",
				"2012��8��31��", "��", "430111199910102121", "13800138200", "��","bboss" };
		String wordtemplate = "D:\\workspace\\bbossgroups-3.6.0\\bboss-plugin-wordpdf\\plugin\\wordpdf\\anjie.doc";
		String wordfile = "d:\\anjie_test.doc";
		FileConvertor.getRealWord(wordtemplate, wordfile, bookMarks,mapValue);
	
		FileBlob fileblob = new FileBlob(wordfile,FileBlob.DOWNLOAD);
		return fileblob;
		
		

	}
	public static void main(String[] args)
	{
		FileConvertor.init("E:\\Program Files\\LibreOffice 3.6");
		FileConvertor.wordToPDFByOpenOffice("D:\\workspace\\SanyPDP\\�ĵ�\\��ά����\\GSP-MMS\\LmGzjMaterialBarcodeMaintAction.do.htm", "d:\\mms.pdf");
	}
}