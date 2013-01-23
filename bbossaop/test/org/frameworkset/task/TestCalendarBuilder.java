/*
 *  Copyright 2008 biaoping.yin
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package org.frameworkset.task;

import java.util.GregorianCalendar;

import org.quartz.Calendar;
import org.quartz.impl.calendar.AnnualCalendar;

/**
 * <p>Title: TestCalendarBuilder.java</p> 
 * <p>Description: </p>
 * <p>bboss workgroup</p>
 * <p>Copyright (c) 2007</p>
 * @Date 2013-1-23 ����10:12:47
 * @author biaoping.yin
 * @version 1.0
 */
public class TestCalendarBuilder extends BaseCalendarBuilder {
	
	public TestCalendarBuilder() {
		// TODO Auto-generated constructor stub
	}



	public Calendar buildCalendar() {
		//������������ÿ��Ϊ���ڵģ�����ʹ��AnnualCalendar
		AnnualCalendar holidays = new AnnualCalendar();
		//��һ�Ͷ���
		java.util.Calendar laborDay = new GregorianCalendar();
		laborDay.add(java.util.Calendar.MONTH,5);
		laborDay.add(java.util.Calendar.DATE,1);
		holidays.setDayExcluded(laborDay, true); //�ų������ڣ��������Ϊfalse��Ϊ����
		//�����
		java.util.Calendar nationalDay = new GregorianCalendar();
		nationalDay.add(java.util.Calendar.MONTH,10);
		nationalDay.add(java.util.Calendar.DATE,1);
		holidays.setDayExcluded(nationalDay, true);//�ų�������
		return holidays;
		
	}

	

}
