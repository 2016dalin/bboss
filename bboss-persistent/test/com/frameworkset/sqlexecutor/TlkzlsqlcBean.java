package com.frameworkset.sqlexecutor;

/**
 * <br>
 * �ٿ�ָ���������̱� ��Ӧ�� T_ZT_ZDRY_LKZLSQLC
 * 
 * @author: <a href="mailto:zhangye.lin@chinacreator.com">����Ҷ</a>
 */
public class TlkzlsqlcBean implements java.io.Serializable {
	private String bksqlcbh;// bksqlcbh �ٿ�ָ�����̱�� varchar2(27) true false true
	private String lkzlbh;// lkzlbh �ٿ�ָ���� varchar2(27) false false false
	private String lkzlspr;// lkzlspr �ٿ�ָ�������� varchar2(10) false false false
	private String lkzlspdwid;// lkzlspdwid �ٿ�ָ��������λ��� varchar2(12) false false false
	private String lkzlspdwmc;// lkzlspdwmc �ٿ�ָ��������λ���� varchar2(70) false false false
	private String lkzlspzt;// lkzlspzt �ٿ�ָ������״̬��1�������쵼������2���о��쵼������3���鱨�����쵼������ varchar2(1) false false false
	private String lkzlspsj;// lkzlspsj �ٿ�ָ������ʱ�� date false false false
	private String lkzlspyj;// lkzlspyj �ٿ�ָ�����������0����ͬ�⣬1��ͬ�⣩ varchar2(1) false false false
	private String lkzlspyjsm;// lkzlspyjsm �ٿ�ָ���������˵�� varchar2(400) false false false
	public String getBksqlcbh() {
		return bksqlcbh;
	}
	public void setBksqlcbh(String bksqlcbh) {
		this.bksqlcbh = bksqlcbh;
	}
	public String getLkzlbh() {
		return lkzlbh;
	}
	public void setLkzlbh(String lkzlbh) {
		this.lkzlbh = lkzlbh;
	}
	public String getLkzlspr() {
		return lkzlspr;
	}
	public void setLkzlspr(String lkzlspr) {
		this.lkzlspr = lkzlspr;
	}
	public String getLkzlspdwid() {
		return lkzlspdwid;
	}
	public void setLkzlspdwid(String lkzlspdwid) {
		this.lkzlspdwid = lkzlspdwid;
	}
	public String getLkzlspdwmc() {
		return lkzlspdwmc;
	}
	public void setLkzlspdwmc(String lkzlspdwmc) {
		this.lkzlspdwmc = lkzlspdwmc;
	}
	public String getLkzlspzt() {
		return lkzlspzt;
	}
	public void setLkzlspzt(String lkzlspzt) {
		this.lkzlspzt = lkzlspzt;
	}
	public String getLkzlspsj() {
		return lkzlspsj;
	}
	public void setLkzlspsj(String lkzlspsj) {
		this.lkzlspsj = lkzlspsj;
	}
	public String getLkzlspyj() {
		return lkzlspyj;
	}
	public void setLkzlspyj(String lkzlspyj) {
		this.lkzlspyj = lkzlspyj;
	}
	public String getLkzlspyjsm() {
		return lkzlspyjsm;
	}
	public void setLkzlspyjsm(String lkzlspyjsm) {
		this.lkzlspyjsm = lkzlspyjsm;
	}
}
