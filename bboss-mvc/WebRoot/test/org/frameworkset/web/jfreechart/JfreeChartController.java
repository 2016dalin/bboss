package org.frameworkset.web.jfreechart;

import java.awt.Color;
import java.awt.Font;
import java.io.IOException;
import java.io.StringReader;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.AxisLocation;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.entity.StandardEntityCollection;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.labels.StandardPieToolTipGenerator;
import org.jfree.chart.labels.StandardXYToolTipGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.LegendTitle;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;
import org.jfree.ui.RectangleInsets;
import org.jfree.ui.TextAnchor;

public class JfreeChartController {

	public String showjfree() {

		return "/jfreechart/jfreechart";
	}

	public void getmaphot(HttpServletRequest request,
			HttpServletResponse response) {

		try {
			int width = 500, height = 300;
			JFreeChart chart = (JFreeChart) request.getSession().getAttribute(
					"chart");
			ChartRenderingInfo info = (ChartRenderingInfo)request.getSession().getAttribute(
			"chartinfo");
//			chart.createBufferedImage(width, height, info);

			// ��ȡ�ȵ�map
			String strimg = ChartUtilities.getImageMap("maphot", info);

			// ����xml
			SAXReader reader = new SAXReader();
			StringReader r = new StringReader(strimg);

			Document document = reader.read(r);
			List<Node> nodes = document.selectNodes("/map/area");
			for (int t = 0; t < nodes.size(); t++) {
				Element ele1 = (Element) nodes.get(t);
				ele1.addAttribute("onmouseover", "showTooltip(this,event)");
				ele1.addAttribute("onmouseout", "UnTip(this)");

				ele1.addAttribute("spanid", "spanid");
			}

			strimg = document.asXML();
			response.setContentType("text/html; charset=GBK");
			response.getWriter().print(strimg);

		} catch (IOException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void jfreechart(HttpServletRequest request,
			HttpServletResponse response) {
		HttpSession session = request.getSession();
		int series1Color = toIntHex(request.getParameter("s1c"), 0x9bd2fb);
		int series1OutlineColor = toIntHex(request.getParameter("s1o"),
				0x0665aa);
		int series2Color = toIntHex(request.getParameter("s2c"), 0xFF0606);
		int series2OutlineColor = toIntHex(request.getParameter("s2o"),
				0x9d0000);
		int backgroundColor = toIntHex(request.getParameter("bc"), 0xFFFFFF);
		int gridColor = toIntHex(request.getParameter("gc"), 0);

		response.setHeader("Content-type", "image/png");

		XYDataset dataset = this.createDateSet();// �������ݼ�
		String fileName = null;
		// ����JFreeChart
		JFreeChart chart = ChartFactory.createTimeSeriesChart(
				"JFreeChartʱ����������ͼ", "Date", "Price", dataset, true, false,
				false);
		// ����JFreeChart����ʾ����,��ͼ���ⲿ���ֽ��е���

		// chart.setBackgroundPaint(Color.pink);// ��������ͼ����ɫ ���������С����״
		Font font = new Font("����", Font.BOLD, 16);
		TextTitle title = new TextTitle("JFreeChartʱ����������ͼ", font);
		chart.setTitle(title);
		// ������
		TextTitle subtitle = new TextTitle(" ", new Font("����", Font.BOLD, 12));
		chart.addSubtitle(subtitle);
		chart.setTitle(title); // ����

		// ����ͼʾ�����ַ�
		// TimeSeries s1 = new TimeSeries("��ʷ����", Day.class);�������ַ�
		LegendTitle legengTitle = chart.getLegend();
		legengTitle.setItemFont(font);

		// XYPlot plot = (XYPlot) chart.getPlot();// ��ȡͼ�εĻ���
		// plot.setBackgroundPaint(Color.lightGray);// �������񱳾�ɫ
		// plot.setDomainGridlinePaint(Color.green);// ������������(Domain��)��ɫ
		// plot.setRangeGridlinePaint(Color.green);// �������������ɫ
		// plot.setAxisOffset(new RectangleInsets(5.0, 5.0, 5.0, 5.0));//
		// ��������ͼ��xy��ľ���
		// plot.setDomainCrosshairVisible(true);
		// plot.setRangeCrosshairVisible(true);

		if (chart != null) {
			chart.setBackgroundPaint(new Color(backgroundColor));
			// ((XYAreaRenderer)
			// chart.getXYPlot().getRenderer()).setOutline(true);
			chart.getXYPlot().getRenderer().setSeriesPaint(0,
					Color.red);
			chart.getXYPlot().getRenderer().setSeriesOutlinePaint(0,
					new Color(series1OutlineColor));
			chart.getXYPlot().getRenderer().setSeriesPaint(1,
					new Color(series2Color));
			chart.getXYPlot().getRenderer().setSeriesOutlinePaint(1,
					new Color(series2OutlineColor));
			chart.getXYPlot().setDomainGridlinePaint(new Color(gridColor));
			chart.getXYPlot().setRangeGridlinePaint(new Color(gridColor));
			chart.getXYPlot().setDomainAxis(0, new DateAxis());
			chart.getXYPlot().setDomainAxis(1, new DateAxis());
			chart.getXYPlot().setInsets(new RectangleInsets(-15, 0, 0, 10));
		}

		XYItemRenderer r = chart.getXYPlot().getRenderer();
		if (r instanceof XYLineAndShapeRenderer) {
			XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) r;
			renderer.setBaseShapesVisible(true);
			renderer.setBaseShapesFilled(true);
			renderer.setShapesVisible(true);// ���������Ƿ���ʾ���ݵ�
		}
		// ����Y��

		NumberAxis numAxis = (NumberAxis) chart.getXYPlot().getRangeAxis();
		NumberFormat numFormater = NumberFormat.getNumberInstance();
		numFormater.setMinimumFractionDigits(2);
		numAxis.setNumberFormatOverride(numFormater);

		// ������ʾ��Ϣ
		StandardXYToolTipGenerator tipGenerator = new StandardXYToolTipGenerator(
				"��ʷ��Ϣ({1} 16:00,{2})", new SimpleDateFormat("MM-dd"),
				numFormater);
		r.setToolTipGenerator(tipGenerator);

		// ����X�ᣨ�����ᣩ
		DateAxis axis = (DateAxis) chart.getXYPlot().getDomainAxis();
		axis.setDateFormatOverride(new SimpleDateFormat("MM-dd"));
		ChartRenderingInfo info = new ChartRenderingInfo(
				new StandardEntityCollection());
		try {
			int width = 500, height = 300;
			response.getOutputStream().write(
					ChartUtilities.encodeAsPNG(chart.createBufferedImage(width,
							height, info)));
			// Write the image map to the PrintWriter
			session.setAttribute("chart", chart);
			session.setAttribute("chartinfo", info);

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * ��������ͼ����������ݼ�
	 * 
	 * @return �������ݼ�
	 */
	private XYDataset createDateSet() {
		TimeSeriesCollection dataset = new TimeSeriesCollection();// ʱ���������ݼ���
		TimeSeries s1 = new TimeSeries("��ʷ����", Day.class);// ����ʱ������Դ��ÿһ��//TimeSeries��ͼ����һ������
		// s1.add(new Day(day,month,year),value),������ݵ���Ϣ
		s1.add(new Day(1, 2, 2009), 123.51);
		s1.add(new Day(2, 2, 2009), 122.1);
		s1.add(new Day(3, 2, 2009), 120.86);
		s1.add(new Day(4, 2, 2009), 122.50);
		s1.add(new Day(5, 2, 2009), 123.12);
		s1.add(new Day(6, 2, 2009), 123.9);
		s1.add(new Day(7, 2, 2009), 124.47);
		s1.add(new Day(8, 2, 2009), 124.08);
		s1.add(new Day(9, 2, 2009), 123.55);
		s1.add(new Day(10, 2, 2009), 122.53);
		s1.add(new Day(11, 2, 2009), 123.43);
		s1.add(new Day(12, 2, 2009), 122.73);
		dataset.addSeries(s1);
		dataset.setDomainIsPointsInTime(true);
		return dataset;
	}

	public int toIntHex(String num, int defaultValue) {
		try {
			if (num != null && num.startsWith("#"))
				num = num.substring(1);
			return Integer.parseInt(num, 16);
		} catch (Exception e) {
			return defaultValue;
		}
	}

	
	public void getbarmaphot(HttpServletRequest request,
			HttpServletResponse response) {

		try {
			int width = 500, height = 300;
			JFreeChart chart = (JFreeChart) request.getSession().getAttribute(
					"chartbar");
			ChartRenderingInfo info = (ChartRenderingInfo) request.getSession().getAttribute("chartbarinfor");
			chart.createBufferedImage(width, height, info);

			// ��ȡ�ȵ�map
			String strimg = ChartUtilities.getImageMap("mapbarhot", info);
System.out.println(strimg);
			// ����xml
			SAXReader reader = new SAXReader();
			StringReader r = new StringReader(strimg);

			Document document = reader.read(r);
			List<Node> nodes = document.selectNodes("/map/area");
			for (int t = 0; t < nodes.size(); t++) {
				Element ele1 = (Element) nodes.get(t);
				ele1.addAttribute("onmouseover", "showTooltip(this,event)");
				ele1.addAttribute("onmouseout", "UnTip(this)");

				ele1.addAttribute("spanid", "spanid");
			}

			strimg = document.asXML();
			response.setContentType("text/html; charset=GBK");
			response.getWriter().print(strimg);

		} catch (IOException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	
	
	public void chartbar(HttpServletRequest request,
			HttpServletResponse response) {

		int series1Color = toIntHex(request.getParameter("s1c"), 0x9bd2fb);
		int series1OutlineColor = toIntHex(request.getParameter("s1o"),0x0665aa);
		int series2Color = toIntHex(request.getParameter("s2c"), 0xFF0606);
		int series2OutlineColor = toIntHex(request.getParameter("s2o"),0x9d0000);
		int backgroundColor = toIntHex(request.getParameter("bc"), 0xFFFFFF);
		int gridColor = toIntHex(request.getParameter("gc"), 0);
		response.setHeader("Content-type", "image/png");
		CategoryDataset dataset = createDataset();// �������ݼ�

		JFreeChart chart = ChartFactory.createBarChart("���ֲ�Ʒ����ͳ��", // ͼƬ����
				"category", // ���������
				"number", // ���������
				dataset, // ���ڳ�ͼ�����ݼ�
				PlotOrientation.VERTICAL, // ���ӷ���
				true, // �Ƿ����Legend
				true, // �Ƿ����tooltips
				true); // �Ƿ����urls
//		 chart.setBackgroundPaint(Color.WHITE);

//		if (chart != null) {
			chart.setBackgroundPaint(new Color(backgroundColor));
//			// ((XYAreaRenderer)
//			// chart.getXYPlot().getRenderer()).setOutline(true);
//			chart.getXYPlot().getRenderer().setSeriesPaint(0,
//					new Color(series1Color));
//			chart.getXYPlot().getRenderer().setSeriesOutlinePaint(0,
//					new Color(series1OutlineColor));
//			chart.getXYPlot().getRenderer().setSeriesPaint(1,
//					new Color(series2Color));
//			chart.getXYPlot().getRenderer().setSeriesOutlinePaint(1,
//					new Color(series2OutlineColor));
//			chart.getXYPlot().setDomainGridlinePaint(new Color(gridColor));
//			chart.getXYPlot().setRangeGridlinePaint(new Color(gridColor));
//			chart.getXYPlot().setDomainAxis(0, new DateAxis());
//			chart.getXYPlot().setDomainAxis(1, new DateAxis());
//			chart.getXYPlot().setInsets(new RectangleInsets(-15, 0, 0, 10));
//		}
		// ���ñ�������
		chart.getTitle().setFont(new Font(" ���� ", Font.CENTER_BASELINE, 12));

		CategoryPlot plot = chart.getCategoryPlot();

		/**//*
			 * //û������ʱ��ʾ����Ϣ plot.setNoDataMessage("���ݻ�δ¼�룡");
			 * plot.setNoDataMessageFont(new Font("����", Font.CENTER_BASELINE,
			 * 15));
			 */

		// ����������
		CategoryAxis domainAxis = plot.getDomainAxis();
		// ���ú���������ʾ����ҵ�����������
		domainAxis.setTickLabelFont(new Font(" ���� ", Font.PLAIN, 9));
		plot.setDomainAxis(domainAxis);

		// ����������
		ValueAxis rangeAxis = (ValueAxis) plot.getRangeAxis();
		// ������ߵ�һ�� Item ��ͼƬ���˵ľ���
		rangeAxis.setUpperMargin(0.15);
		// ������͵�һ�� Item ��ͼƬ�׶˵ľ���
		rangeAxis.setLowerMargin(0.15);
		plot.setRangeAxis(rangeAxis);

		BarRenderer renderer = new BarRenderer();
		renderer.setBaseOutlinePaint(Color.BLACK);
		/**//*
			 * // ����ͼ�ϵ����� renderer.setSeriesItemLabelFont(0, font);
			 * renderer.setSeriesItemLabelFont(1, font);
			 */
		// ����legend������
		renderer.setBaseLegendTextFont(new Font(" ���� ",
				Font.LAYOUT_RIGHT_TO_LEFT, 10));
		// ���� Wall ����ɫ
		// renderer.setWallPaint(Color.gray);
		// ����ÿ��������ɫ
		renderer.setSeriesPaint(0, new Color(133, 210, 38));
		renderer.setSeriesPaint(1, new Color(0, 131, 249));
		// �������� Outline ��ɫ
		renderer.setSeriesOutlinePaint(0, Color.BLACK);
		renderer.setSeriesOutlinePaint(1, Color.BLACK);
		// ����ÿ��ƽ������֮�����
		renderer.setItemMargin(0.03);

		// ��ʾÿ��������ֵ�����޸ĸ���ֵ����������
		renderer
				.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
		// ע�⣺�˾�ܹؼ������޴˾䣬�����ֵ���ʾ�ᱻ���ǣ���������û����ʾ����������
		renderer.setBasePositiveItemLabelPosition(new ItemLabelPosition(
				ItemLabelAnchor.OUTSIDE12, TextAnchor.BASELINE_CENTER));
		// ��������ͼ�ϵ�����ƫ��ֵ
		renderer.setItemLabelAnchorOffset(10D);
		renderer.setBaseItemLabelsVisible(true);
		renderer.setBaseItemLabelFont(new Font("����", Font.PLAIN,
				9));

		plot.setRenderer(renderer);

		// ��������͸����
		plot.setForegroundAlpha(0.6f);
		// ���ú���������������ʾλ��
		plot.setDomainAxisLocation(AxisLocation.BOTTOM_OR_LEFT);
		plot.setRangeAxisLocation(AxisLocation.BOTTOM_OR_LEFT);

		
		ChartRenderingInfo info = new ChartRenderingInfo(
				new StandardEntityCollection());
		try {
			int width = 500, height = 300;
			response.getOutputStream().write(
					ChartUtilities.encodeAsPNG(chart.createBufferedImage(width,
							height, info)));
			// Write the image map to the PrintWriter
			request.getSession().setAttribute("chartbar", chart);
			request.getSession().setAttribute("chartbarinfor", info);

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private CategoryDataset createDataset() {

		final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		dataset.addValue(25.0, "Series 1", "Category 1");
		dataset.addValue(34.0, "Series 1", "Category 2");
		dataset.addValue(19.0, "Series 2", "Category 1");
		dataset.addValue(29.0, "Series 2", "Category 2");
		dataset.addValue(41.0, "Series 3", "Category 1");
		dataset.addValue(33.0, "Series 3", "Category 2");
		return dataset;

	}

	
	
	
	
	 public  void piechart3D(HttpServletRequest request,HttpServletResponse response){   
	        PieDataset dataset = getDataSet();   
	        JFreeChart chart = ChartFactory.createPieChart3D(   
	                " ��Ŀ���ȷֲ�",// ͼ�����   
	                dataset,// data   
	                true,// include legend   
	                true, false);   
	        PiePlot3D plot = (PiePlot3D) chart.getPlot();   
	        // ͼƬ����ʾ�ٷֱ�:Ĭ�Ϸ�ʽ   
	        plot.setLabelGenerator(new StandardPieSectionLabelGenerator(   
	                StandardPieToolTipGenerator.DEFAULT_TOOLTIP_FORMAT));   
	        // ͼƬ����ʾ�ٷֱ�:�Զ��巽ʽ��{0} ��ʾѡ� {1} ��ʾ��ֵ��   
	        //{2} ��ʾ��ռ���� ,С�������λ   
	        plot.setLabelGenerator(new StandardPieSectionLabelGenerator(   
	                "{0}={1}({2})", NumberFormat.getNumberInstance(),   
	                new DecimalFormat("0.00%")));   
	        // ͼ����ʾ�ٷֱ�:�Զ��巽ʽ�� {0} ��ʾѡ� {1} ��ʾ��ֵ�� {2} ��ʾ��ռ����   
	        plot.setLegendLabelGenerator(new StandardPieSectionLabelGenerator(   
	                        "{0}"));   
	        // ָ��ͼƬ��͸����(0.0-1.0)   
	        plot.setForegroundAlpha(1.0f);   
	        // ָ����ʾ�ı�ͼ��Բ��(true)����Բ��(false)   
	        plot.setCircular(true);   
	        // ����ͼ�Ϸ����ǩlabel�����壬�����������   
	        plot.setLabelFont(new Font("����", Font.PLAIN, 12));   
	        // ����ͼ�Ϸ����ǩlabel������ȣ������plot�İٷֱ�   
	        plot.setMaximumLabelWidth(0.2);   
	        // ����3D��ͼ��Z��߶ȣ�0.0��1.0��   
	        plot.setDepthFactor(0.07);   
	        //������ʼ�Ƕȣ�Ĭ��ֵΪ90����Ϊ0ʱ����ʼֵ��3���ӷ���   
	        plot.setStartAngle(45);   
	  
	        // ����ͼ��������壬�����������   
	        TextTitle textTitle = chart.getTitle();   
	        textTitle.setFont(new Font("����", Font.PLAIN, 20));   
	  
	        // ���ñ���ɫΪ��ɫ   
	        chart.setBackgroundPaint(Color.white);   
	        // ����Legend�������壬�����������   
	        chart.getLegend().setItemFont(new Font("����", Font.PLAIN, 12));   
	        ChartRenderingInfo info = new ChartRenderingInfo(
					new StandardEntityCollection());
	        // ���ͼƬ���ļ�   
	        try {
				int width = 500, height = 300;
				response.getOutputStream().write(
						ChartUtilities.encodeAsPNG(chart.createBufferedImage(width,
								height, info)));
				// Write the image map to the PrintWriter
				request.getSession().setAttribute("chartpie3D", chart);
				request.getSession().setAttribute("chartpieinfo3D", info);

			} catch (IOException e) {
				e.printStackTrace();
			}
	    }   
	  
	 public void getpiemaphot3D(HttpServletRequest request,
				HttpServletResponse response) {

			try {
				int width = 500, height = 300;
				JFreeChart chart = (JFreeChart) request.getSession().getAttribute(
						"chartpie3D");
				ChartRenderingInfo info = (ChartRenderingInfo) request.getSession().getAttribute("chartpieinfo3D");
				chart.createBufferedImage(width, height, info);

				// ��ȡ�ȵ�map
				String strimg = ChartUtilities.getImageMap("mappiehot3D", info);
	System.out.println(strimg);
				// ����xml
				SAXReader reader = new SAXReader();
				StringReader r = new StringReader(strimg);

				Document document = reader.read(r);
				List<Node> nodes = document.selectNodes("/map/area");
				for (int t = 0; t < nodes.size(); t++) {
					Element ele1 = (Element) nodes.get(t);
					ele1.addAttribute("onmouseover", "showTooltip(this,event)");
					ele1.addAttribute("onmouseout", "UnTip(this)");

					ele1.addAttribute("spanid", "spanid");
				}

				strimg = document.asXML();
				response.setContentType("text/html; charset=GBK");
				response.getWriter().print(strimg);

			} catch (IOException e) {
				e.printStackTrace();
			} catch (DocumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	 
	 
	    public  void piechart(HttpServletRequest request,HttpServletResponse response){   
	        PieDataset dataset = getDataSet();   
	        JFreeChart chart = ChartFactory.createPieChart(" ��Ŀ���ȷֲ�",// ͼ�����   
	                dataset,// data   
	                true,// include legend   
	                true, false);   
	        PiePlot plot = (PiePlot) chart.getPlot();   
	        // ͼƬ����ʾ�ٷֱ�:Ĭ�Ϸ�ʽ   
	        plot.setLabelGenerator(new StandardPieSectionLabelGenerator(   
	                StandardPieToolTipGenerator.DEFAULT_TOOLTIP_FORMAT));   
	        // ͼƬ����ʾ�ٷֱ�:�Զ��巽ʽ��{0} ��ʾѡ� {1} ��ʾ��ֵ��   
	        //{2} ��ʾ��ռ���� ,С�������λ   
	        plot.setLabelGenerator(new StandardPieSectionLabelGenerator(   
	                "{0}={1}({2})", NumberFormat.getNumberInstance(),   
	                new DecimalFormat("0.00%")));   
	        // ͼ����ʾ�ٷֱ�:�Զ��巽ʽ�� {0} ��ʾѡ� {1} ��ʾ��ֵ�� {2} ��ʾ��ռ����   
	        plot.setLegendLabelGenerator(new StandardPieSectionLabelGenerator(   
	                        "{0}"));   
	        // ָ��ͼƬ��͸����(0.0-1.0)   
	        plot.setForegroundAlpha(1.0f);   
	        // ָ����ʾ�ı�ͼ��Բ��(true)����Բ��(false)   
	        plot.setCircular(true);   
	        // ����ͼ�Ϸ����ǩlabel�����壬�����������   
	        plot.setLabelFont(new Font("����", Font.PLAIN, 12));   
	        // ����ͼ�Ϸ����ǩlabel������ȣ������plot�İٷֱ�   
	        plot.setMaximumLabelWidth(0.2);   
	        //������ʼ�Ƕȣ�Ĭ��ֵΪ90����Ϊ0ʱ����ʼֵ��3���ӷ���   
	        plot.setStartAngle(45);   
	  
	        /**  
	         * ����ĳһ��͹������һ������ΪdataSet��key���˷���ֻ��PiePlot����Ч  
	         */  
	        plot.setExplodePercent(" ��ά", 0.2);   
	  
	        // ����ͼ��������壬�����������   
	        TextTitle textTitle = chart.getTitle();   
	        textTitle.setFont(new Font("����", Font.PLAIN, 20));   
	  
	        // ���ñ���ɫΪ��ɫ   
//	        chart.setBackgroundPaint(Color.white);   
	        // ����Legend�������壬�����������   
	        chart.getLegend().setItemFont(new Font("����", Font.PLAIN, 12));   
	  
	        // ���ͼƬ���ļ�   
	        ChartRenderingInfo info = new ChartRenderingInfo(
					new StandardEntityCollection());
			try {
				int width = 500, height = 300;
				response.getOutputStream().write(
						ChartUtilities.encodeAsPNG(chart.createBufferedImage(width,
								height, info)));
				// Write the image map to the PrintWriter
				request.getSession().setAttribute("chartpie", chart);
				request.getSession().setAttribute("chartpieinfo", info);

			} catch (IOException e) {
				e.printStackTrace();
			}
	    }   
	  
	    public void getpiemaphot(HttpServletRequest request,
				HttpServletResponse response) {

			try {
				int width = 500, height = 300;
				JFreeChart chart = (JFreeChart) request.getSession().getAttribute(
						"chartpie");
				ChartRenderingInfo info = (ChartRenderingInfo) request.getSession().getAttribute("chartpieinfo");
				chart.createBufferedImage(width, height, info);

				// ��ȡ�ȵ�map
				String strimg = ChartUtilities.getImageMap("mappiehot", info);
	System.out.println(strimg);
				// ����xml
				SAXReader reader = new SAXReader();
				StringReader r = new StringReader(strimg);

				Document document = reader.read(r);
				List<Node> nodes = document.selectNodes("/map/area");
				for (int t = 0; t < nodes.size(); t++) {
					Element ele1 = (Element) nodes.get(t);
					ele1.addAttribute("onmouseover", "showTooltip(this,event)");
					ele1.addAttribute("onmouseout", "UnTip(this)");

					ele1.addAttribute("spanid", "spanid");
				}

				strimg = document.asXML();
				response.setContentType("text/html; charset=GBK");
				response.getWriter().print(strimg);

			} catch (IOException e) {
				e.printStackTrace();
			} catch (DocumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	    
	    public  PieDataset getDataSet() {   
	        DefaultPieDataset dataset = new DefaultPieDataset();   
	        dataset.setValue(" �г�ǰ��", new Double(10));   
	        dataset.setValue(" ����", new Double(15));   
	        dataset.setValue(" �ƻ�", new Double(10));   
	        dataset.setValue(" ���������", new Double(10));   
	        dataset.setValue(" ִ�п���", new Double(35));   
	        dataset.setValue(" ��β", new Double(10));   
	        dataset.setValue(" ��ά", new Double(10));   
	        return dataset;   
	    }  
	
	    
	    
	    
	
	  public void chartbar2(HttpServletRequest request,HttpServletResponse response){
		  JFreeChart chart=ChartFactory.createBarChart3D(
	                "ͼ������ͳ��ͼ",
	                "ͼ��",//Ŀ¼�����ʾ��ǩ
	                "����",//��ֵ�����ʾ��ǩ
	                getDataSet1(),
	                PlotOrientation.VERTICAL,//����ͼ����
	                false,
	                false,
	                false        
	        );
	        
	        //���ñ���
	        chart.setTitle(new TextTitle("ͼ������ͳ��ͼ",new Font("����",Font.ITALIC,22)));
	        //����ͼ����
	        CategoryPlot plot=(CategoryPlot)chart.getPlot();
	        
	        CategoryAxis categoryAxis=plot.getDomainAxis();//ȡ�ú���
	        categoryAxis.setLabelFont(new Font("����",Font.BOLD,22));//���ú�����ʾ��ǩ������
	        categoryAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);//�����ǩ�ԣ�������б
	        categoryAxis.setTickLabelFont(new Font("����",Font.BOLD,18));//�����ǩ����
	        
	        NumberAxis numberAxis=(NumberAxis)plot.getRangeAxis();//ȡ������
	        numberAxis.setLabelFont(new Font("����",Font.BOLD,42));//����������ʾ��ǩ����
	        ChartRenderingInfo info = new ChartRenderingInfo(
					new StandardEntityCollection());
			try {
				int width = 500, height = 300;
				response.getOutputStream().write(
						ChartUtilities.encodeAsPNG(chart.createBufferedImage(width,
								height, info)));
				// Write the image map to the PrintWriter
				request.getSession().setAttribute("chartbar1", chart);
				request.getSession().setAttribute("chartbarinfo1", info);

			} catch (IOException e) {
				e.printStackTrace();
			}
	  }
	  public void getpiemaphot1(HttpServletRequest request,
				HttpServletResponse response) {

			try {
				int width = 500, height = 300;
				JFreeChart chart = (JFreeChart) request.getSession().getAttribute(
						"chartbar1");
				ChartRenderingInfo info = (ChartRenderingInfo) request.getSession().getAttribute("chartbarinfo1");
				chart.createBufferedImage(width, height, info);

				// ��ȡ�ȵ�map
				String strimg = ChartUtilities.getImageMap("mapbarhot2", info);
	System.out.println(strimg);
				// ����xml
				SAXReader reader = new SAXReader();
				StringReader r = new StringReader(strimg);

				Document document = reader.read(r);
				List<Node> nodes = document.selectNodes("/map/area");
				for (int t = 0; t < nodes.size(); t++) {
					Element ele1 = (Element) nodes.get(t);
					ele1.addAttribute("onmouseover", "showTooltip(this,event)");
					ele1.addAttribute("onmouseout", "UnTip(this)");

					ele1.addAttribute("spanid", "spanid");
				}

				strimg = document.asXML();
				response.setContentType("text/html; charset=GBK");
				response.getWriter().print(strimg);

			} catch (IOException e) {
				e.printStackTrace();
			} catch (DocumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	   public CategoryDataset getDataSet1(){
	        DefaultCategoryDataset dataset=new DefaultCategoryDataset();
	        dataset.addValue(47000,"", "Spring2.0����");
	        dataset.addValue(38000,"","��������J2EE");
	        dataset.addValue(31000, "", "JavaScriptȨ��ָ��");
	        dataset.addValue(25000, "", "Ajax In Action");
	        return dataset;
	    }


	
	
}
