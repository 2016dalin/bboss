CREATE PROCEDURE P_JHJD_GCJHData 
@HTBH varchar(12), 
@StartDate varchar(6), 
@EndDate varchar(6) 
AS--���̼ƻ�
set nocount onCreate Table #Ls_JiHua 
(  ׮�� varchar(20),  
ϸĿ��� varchar(6),  
ϸĿ���� varchar(30),  
��λ varchar(10),  
�������1 money,  
�������2 money,  
��ɽ�� money,  
����  varchar(6),  
ZH varchar(16))if @HTBH=''
begin
/*SELECT 
dbo.JHJD_GCJH.BDBH AS ��ͬ���, 
dbo.JHJD_GCJH.XMBH AS ϸĿ���,       
dbo.JHJD_XMQD.XMMC AS ϸĿ����, 
dbo.JHJD_XMQD.DW AS ��λ,       
dbo.JHJD_GCJH.ZH AS ׮��, 
dbo.JHJD_GCJH.RQ AS ����,       
dbo.JHJD_GCJH.WC AS �������, 
dbo.JHJD_GCJH.WC1 AS �������1,       
dbo.JHJD_GCJH.WCJE AS ��ɽ��
FROM dbo.JHJD_XMQD INNER JOIN      
dbo.JHJD_GCJH ON 
dbo.JHJD_XMQD.XMBH = dbo.JHJD_GCJH.XMBH
Where  (dbo.JHJD_GCJH.RQ>=@StartDate) 
and(dbo.JHJD_GCJH.RQ<=@endDate)
ORDER BY ��ͬ���, ����, 
LEFT(dbo.JHJD_GCJH.XMBH, 4),
 ׮��, 
 RIGHT(dbo.JHJD_GCJH.XMBH, 2)
 */
 SELECT dbo.JHJD_GCJH.BDBH AS ��ͬ���, 
 dbo.JHJD_GCJH.XMBH AS ϸĿ���,       
 dbo.JHJD_XMQD.XMMC AS ϸĿ����, 
 dbo.JHJD_XMQD.DW AS ��λ,       
 dbo.JHJD_GCJH.ZH AS ׮��, 
 dbo.JHJD_GCJH.RQ AS ����,       
 dbo.JHJD_GCJH.WC AS �������1, 
 dbo.JHJD_GCJH.WC1 AS �������2,       
 dbo.JHJD_GCJH.WCJE AS ��ɽ��
 FROM dbo.JHJD_XMQD INNER JOIN      
 dbo.JHJD_GCJH ON dbo.JHJD_XMQD.XMBH = dbo.JHJD_GCJH.XMBHWhere  
 (dbo.JHJD_GCJH.RQ>=@StartDate) 
 and(dbo.JHJD_GCJH.RQ<=@endDate)
 ORDER BY ��ͬ���, ����, LEFT(dbo.JHJD_GCJH.XMBH, 4), ׮��, RIGHT(dbo.JHJD_GCJH.XMBH, 2)endelsebegindeclare @StartYear intdeclare @StartMonth intdeclare @EndYear intdeclare @EndMonth intset @StartYear=cast(Left(@StartDate,4) as int)set @StartMonth=Cast(Right(@StartDate,2) as Int)set @EndYear=Cast(Left(@EndDate,4) as int)Set @EndMonth=Cast(Right(@EndDate,2) as Int)declare @RQ varchar(6)while (@StartYear*100+@StartMonth<=@EndYear*100+@EndMonth)begin  Set @RQ=Cast(@StartYear as Char(4))+Right('0'+Cast(@StartMonth as VarChar(2)),2)  insert into #Ls_JiHua (    ϸĿ����  ) values (Left(@RQ,4)+'��'+Right(@RQ,2)+'��   ·������')   insert into #Ls_JiHua (  ϸĿ��� ,  ϸĿ���� , ��λ, ���� )              select XMBH,XMMC,DW,@RQ from JHJD_XMQD where XMBH like 'LJ%' order by XMBH  insert into #Ls_JiHua (    ϸĿ����  ) values (Left(@RQ,4)+'��'+Right(@RQ,2)+'��   ·�湤��')   insert into #Ls_JiHua (  ϸĿ��� ,  ϸĿ���� , ��λ,  ���� )             select XMBH,XMMC,DW,@RQ from JHJD_XMQD where XMBH like 'LM%' order by XMBH insert into #Ls_JiHua (    ϸĿ����  ) values (Left(@RQ,4)+'��'+Right(@RQ,2)+'��   �ź�����')  insert into #Ls_JiHua (  ϸĿ��� ,  ϸĿ���� ,  ��λ, ����,ZH,׮�� )  select bbxx.XMBH,bbxx.XMMC,bbxx.DW,@RQ,bbxx.ZHBH,bbxx.zhmc from   (select JHJD_XMQD.*,zh.mc as zhmc,isnull(zh.zhbh,' ')as zhbh from JHJD_XMQD left join      (select mc,ssxmbh,bh as zhbh from JHJD_zh where bh like @HTBH+'%') AS zh on substring(JHJD_XMQD.XMbh,1,4)=substring(zh.ssxmbh,1,4) and JHJD_XMQD.XMbh<>zh.ssxmbh     where JHJD_XMQD.XMbh like 'QH%'and substring(JHJD_XMQD.XMBH,3,4)<>'0000') bbxx   order by substring(bbxx.xmbh,1,4),(case when left(bbxx.zhmc,1)<>'k'then 9999999 else (case when charindex('+',bbxx.zhmc)=0 then right(bbxx.zhmc,len(bbxx.zhmc)-1)*1000 else cast(substring(bbxx.zhmc,2,charindex('+',bbxx.zhmc)-2)*1000 as float)+substring(bbxx.zhmc,charindex('+',bbxx.zhmc)+1,len(bbxx.zhmc)-charindex('+',bbxx.zhmc)) end) end),bbxx.zhmc,substring(bbxx.xmbh,3,4) insert into #Ls_JiHua (    ϸĿ����  ) values (Left(@RQ,4)+'��'+Right(@RQ,2)+'��   ���湤��')  insert into #Ls_JiHua (  ϸĿ��� ,  ϸĿ���� ,  ��λ, ����,ZH,׮�� )  select bbxx.XMBH,bbxx.XMMC,bbxx.DW,@RQ,bbxx.ZHBH,bbxx.zhmc from   (select JHJD_XMQD.*,zh.mc as zhmc,isnull(zh.zhbh,' ')as zhbh from JHJD_XMQD left join      (select mc,ssxmbh,bh as zhbh from JHJD_zh where bh like @HTBH+'%') AS zh on substring(JHJD_XMQD.XMbh,1,4)=substring(zh.ssxmbh,1,4) and JHJD_XMQD.XMbh<>zh.ssxmbh     where JHJD_XMQD.XMbh like 'JC%'and substring(JHJD_XMQD.XMBH,3,4)<>'0000') bbxx   order by substring(bbxx.xmbh,1,4),(case when left(bbxx.zhmc,1)<>'k'then 9999999 else (case when charindex('+',bbxx.zhmc)=0 then right(bbxx.zhmc,len(bbxx.zhmc)-1)*1000 else cast(substring(bbxx.zhmc,2,charindex('+',bbxx.zhmc)-2)*1000 as float)+substring(bbxx.zhmc,charindex('+',bbxx.zhmc)+1,len(bbxx.zhmc)-charindex('+',bbxx.zhmc)) end) end),bbxx.zhmc,substring(bbxx.xmbh,3,4) Set @StartMonth=@StartMonth+1 if @StartMonth>12 begin  set  @StartYear=@StartYear+1  Set @StartMonth=1 endend--Update #Ls_JiHua set  �������=a.WC,�������1=a.WC1,��ɽ��=a.WCJE from JHJD_GCJH  a where  a.XMBH=ϸĿ��� and a.RQ=���� and a. ZH=#Ls_JiHua.ZHUpdate #Ls_JiHua set  �������1=a.WC,�������2=a.WC1,��ɽ��=a.WCJE from JHJD_GCJH  a where a.BDBH=@HTBH and  a.XMBH=ϸĿ���  and a.RQ=���� and  #Ls_JiHua.ϸĿ��� like 'LJ%'Update #Ls_JiHua set  �������1=a.WC,�������2=a.WC1,��ɽ��=a.WCJE from JHJD_GCJH  a where  a.BDBH=@HTBH and a.XMBH=ϸĿ���  and a.RQ=���� and  #Ls_JiHua.ϸĿ��� like 'LM%'Update #Ls_JiHua set  �������1=a.WC,�������2=a.WC1,��ɽ��=a.WCJE from JHJD_GCJH  a where a.BDBH=@HTBH and  a.XMBH=ϸĿ���  and a.RQ=���� and a. ZH=#Ls_JiHua.ZH and #Ls_JiHua.ϸĿ��� like 'QH%'Update #Ls_JiHua set  �������1=a.WC,�������2=a.WC1,��ɽ��=a.WCJE from JHJD_GCJH  a where a.BDBH=@HTBH and  a.XMBH=ϸĿ���  and a.RQ=���� and a. ZH=#Ls_JiHua.ZH and #Ls_JiHua.ϸĿ��� like 'JC%'/*SELECT dbo.JHJD_GCJH.BDBH AS ��ͬ���, dbo.JHJD_GCJH.XMBH AS ϸĿ���,       dbo.JHJD_XMQD.XMMC AS ϸĿ����, dbo.JHJD_XMQD.DW AS ��λ,       dbo.JHJD_GCJH.ZH AS ׮��, dbo.JHJD_GCJH.RQ AS ����,       dbo.JHJD_GCJH.WC AS �������, dbo.JHJD_GCJH.WC1 AS �������1,       dbo.JHJD_GCJH.WCJE AS ��ɽ��FROM dbo.JHJD_XMQD INNER JOIN      dbo.JHJD_GCJH ON dbo.JHJD_XMQD.XMBH = dbo.JHJD_GCJH.XMBHWhere (dbo.JHJD_GCJH.BDBH=@HTBH) and (dbo.JHJD_GCJH.RQ>=@StartDate) and(dbo.JHJD_GCJH.RQ<=@endDate)ORDER BY ��ͬ���, ����,  LEFT(dbo.JHJD_GCJH.XMBH, 4), ׮��, RIGHT(dbo.JHJD_GCJH.XMBH, 2)*/end/*select   ׮�� as zh,  ϸĿ��� as xmbh ,  ϸĿ���� as xmmc,  ��λ as dw,  �������1 as sl1 ,   �������2 as sl2,  ��ɽ�� as je,  ����  as rq  from #Ls_JiHua*/select   ׮�� ,  ϸĿ���  ,  ϸĿ���� ,  ��λ ,  �������1  ,   �������2 ,  ��ɽ�� ,  ����    from #Ls_JiHuaGO