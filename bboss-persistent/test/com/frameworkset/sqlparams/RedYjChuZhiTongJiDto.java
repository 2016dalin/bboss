package com.frameworkset.sqlparams;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * ��ɫԤ�����ù���ͳ�ƴ�ֵ����
 * @author ֣��ΰ
 *
 */
public class RedYjChuZhiTongJiDto {
	
	private int tjType;		//ͳ������
	private String startDate;  //��ʼ����
	private String endDate;    //��������
	
	private String rxtsjStartDate; //��ϵͳ�⿪ʼʱ��
	private String rxtsjEndDate;  //��ϵͳ�����ʱ��
	private String yjfbsjStartDate;  //Ԥ��������ʼʱ��
	private String yjfbsjEndDate;   //Ԥ����������ʱ��
	
	private int year;  //��
	private int jiDu; //����
	private int isDate; //�Ƿ�����������
	
	
	
	public String getRxtsjStartDate() {
		return rxtsjStartDate;
	}	
	private void setRxtsjStartDate(String rxtsjStartDate) {
		this.rxtsjStartDate = rxtsjStartDate;
	}
	
	public String getRxtsjEndDate() {
		return rxtsjEndDate;
	}	
	private void setRxtsjEndDate(String rxtsjEndDate) {
		this.rxtsjEndDate = rxtsjEndDate;
	}
	
	public String getYjfbsjStartDate() {
		return yjfbsjStartDate;
	}
	private void setYjfbsjStartDate(String yjfbsjStartDate) {
		this.yjfbsjStartDate = yjfbsjStartDate;
	}
	
	public String getYjfbsjEndDate() {
		return yjfbsjEndDate;
	}	
	private void setYjfbsjEndDate(String yjfbsjEndDate) {
		this.yjfbsjEndDate = yjfbsjEndDate;
	}
	
	
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
		try {
			this.setRxtsjStartDate(convertStrDateFmtToAnother(startDate,"yyyy-MM-dd" , "yyyyMMdd"));
			this.setYjfbsjStartDate(convertStrDateFmtToAnother(startDate, "yyyy-MM-dd", "yyyy/MM/dd"));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			startDate = null;
			this.rxtsjStartDate = null;
			this.yjfbsjStartDate = null;
			throw new RuntimeException("���ڸ�ʽ����");
		}
		
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
		try {
			this.setRxtsjEndDate(convertStrDateFmtToAnother(endDate,"yyyy-MM-dd" , "yyyyMMdd"));
			this.setYjfbsjEndDate(convertStrDateFmtToAnother(endDate, "yyyy-MM-dd", "yyyyMMdd"));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			endDate = null;
			this.rxtsjEndDate = null;
			this.yjfbsjEndDate = null;
			throw new RuntimeException("���ڸ�ʽ����");
		}
	}
	
	public int getTjType() {
		return tjType;
	}
	public void setTjType(int tjType) {
		this.tjType = tjType;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public int getJiDu() {
		return jiDu;
	}
	public void setJiDu(int jiDu) {
		this.jiDu = jiDu;
	}
	public int getIsDate() {
		return isDate;
	}
	public void setIsDate(int isDate) {
		this.isDate = isDate;
	}
	
	/**
	 * �������ַ�����һ�����ڸ�ʽתΪ��һ�����ڸ�ʽ
	 * @param strDate
	 * @param originalFmt ԭ�����ڸ�ʽ
	 * @param fmt Ҫת�������ڸ�ʽ
	 * @return
	 * @throws ParseException
	 */
	private String convertStrDateFmtToAnother(String strDate ,String originalFmt ,String fmt) throws ParseException{
		SimpleDateFormat formater = new SimpleDateFormat(originalFmt);
		Date d = formater.parse(strDate);
		formater = new SimpleDateFormat(fmt);
		return formater.format(d);
	}
	
	
}
