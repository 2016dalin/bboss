1.linux�°�װopenffice
�ο��ĵ�
http://wiki.openoffice.org/w/images/7/7e/Installation_Guide_OOo3.pdf
Ĭ�ϰ�װĿ¼��
/opt/openoffice.org3
2.linux�°�װswftools����freetype
����swftools 0.9.1�汾
http://www.swftools.org/swftools-0.9.1.tar.gz
http://wiki.swftools.org/wiki/Main_Page#Installation_guides
http://wiki.swftools.org/wiki/Installation
�ڰ�װswftools 0.9.2ʱ����ת����װswftools 0.9.1
Ĭ�ϰ�װĿ¼
/usr/local/share/swftools
http://www.foolabs.com/xpdf/download.html
zlib ��װ
jpeg.c:462: error: conflicting types for ��jpeg_load_from_mem��

make[2]: g++: Command not found
http://www.rpmfind.net/linux/rpm2html/search.php?query=gcc-c%2B%2B
��װ��gcc-c++-4.4.6-4.el6.i686.rpm libstdc++-devel-4.4.6-4.el6.i686.rpm

s -Wformat -O -fomit-frame-pointer  modules/swfaction.c -o modules/swfaction.o
In file included from modules/.././bitio.h:23,
                 from modules/../rfxswf.h:37,
                 from modules/swfaction.c:24:
modules/.././types.h:39:2: error: #error "no way to define 64 bit integer"
modules/.././types.h:42:2: error: #error "don't know how to define 32 bit integer"
modules/.././types.h:45:2: error: #error "don't know how to define 16 bit integer"
modules/.././types.h:48:2: error: #error "don't know how to define 8 bit integer"
make[1]: *** [modules/swfaction.o] Error 1
make[1]: Leaving directory `/opt/openoffice/tools/swftools-0.9.1/lib'
make: *** [all] Error 2

rm -f config.cache
 LDFLAGS="-L/usr/local/lib" CPPFLAGS="-I/usr/local/include" ./configure

Furthermore, a new installation of jpeglib (the following assumes it's in /usr/local/lib) often requires doing a: 
 ranlib /usr/local/lib/libjpeg.a
 ldconfig /usr/local/lib

./soffice --invisible --convert-to pdf:writer_pdf_Export --outdir "/opt/tomcat/test" "/opt/tomcat/test/anjie_testswftools20121222.doc"
./soffice --invisible --convert-to pdf:writer_pdf_Export --outdir "/opt/tomcat/test" "/opt/tomcat/wordpdf/anjie.doc"
./soffice --invisible --convert-to swf:draw_flash_Export --outdir "/opt/tomcat/test" "/opt/tomcat/wordpdf/anjie.doc"

   �����ѹ���������ص�swftoolsĿ¼����
./configure��ȷ��û������������Ĵ���
make
make install
     ���ܳ��ֵ��쳣
�޸�һ��Դ�ļ�����
���ʱ����������
jpeg.c:463: error: conflicting types for ��jpeg_load_from_mem��
jpeg.h:15: error: previous declaration of ��jpeg_load_from_mem�� was here
make[1]: *** [jpeg.o] Error 1
ԭ���Ǻ����Ķ����ͷ�ļ��������е��ͻ�������ʽ�Ƚϼ򵥣��޸� swftools-0.9.1\swftools-0.9.1\lib\jpeg.c �� 463�У�
��Ϊ��
int jpeg_load_from_mem(unsigned char*_data, int size, unsigned char**dest, int*width, int*height)
�������ɣ�

����giflib 5.0.3��װswftools�����´���
pe -Wno-write-strings -Wformat -O -fomit-frame-pointer  gif2swf.c -o gif2swf.o
gif2swf.c: In function ��MovieAddFrame��:
gif2swf.c:233: error: too few arguments to function ��DGifOpenFileName��
gif2swf.c:239: warning: implicit declaration of function ��PrintGifError��
gif2swf.c: In function ��CheckInputFile��:
gif2swf.c:491: error: too few arguments to function ��DGifOpenFileName��
make[1]: *** [gif2swf.o] Error 1
make[1]: Leaving directory `/opt/openoffice/tools/swftools-0.9.1/src'
make: *** [all] Error 2

ж��giflib 5.0.3 ��װgiflib-4.1.6.tar.gz������

3.jdk��װ
����linux�汾jdk 64λ
��װ
./jdk-6u31-linux-x64-rpm.bin 
���û�������
vi /etc/profile
JAVA_HOME=/opt/jdk1.5
PATH=$JAVA_HOME/bin:$PATH
CLASSPATH=.$JAVA_HOME/lib/dt.jar:$JAVA_HOME/lib/tools.jar export JAVA_HOME,PATH,CLASSPATH
�û���������Ч
source /etc/profile

��java��ִ��openoffice����������ʱ�����´���
WriterDoc: Start up or connect to the remote service manager.
CE> /opt/openoffice.org3/program/../basis-link/ure-link/bin/javaldx: /lib64/libc.so.6: version `GLIBC_2.7' not found (required by /opt/openoffice.org/ure/bin/../lib/libuno_sal.so.3)
CE> /opt/openoffice.org3/program/../basis-link/ure-link/bin/javaldx: /lib64/libc.so.6: version `GLIBC_2.7' not found (required by /opt/openoffice.org/ure/bin/../lib/libxml2.so.2)
CE> /opt/openoffice.org3/program/soffice.bin: /lib64/libc.so.6: version `GLIBC_2.7' not found (required by /opt/openoffice.org3/program/../basis-link/ure-link/lib/libuno_sal.so.3)
CE> /opt/openoffice.org3/program/soffice.bin: /lib64/libc.so.6: version `GLIBC_2.11' not found (required by /opt/openoffice.org3/program/../basis-link/program/libsvt.so)
CE> /opt/openoffice.org3/program/soffice.bin: /lib64/libc.so.6: version `GLIBC_2.7' not found (required by /opt/openoffice.org3/program/../basis-link/program/libsvt.so)
CE> /opt/openoffice.org3/program/soffice.bin: /lib64/libc.so.6: version `GLIBC_2.11' not found (required by /opt/openoffice.org3/program/../basis-link/program/libvcl.so)
CE> /opt/openoffice.org3/program/soffice.bin: /lib64/libc.so.6: version `GLIBC_2.7' not found (required by /opt/openoffice.org3/program/../basis-link/program/../ure-link/lib/libxml2.so.2)
./soffice -accept="socket,host=localhost,port=2002;urp;StarOffice.Service.Manager" 
ж��OpenOffice
yum remove openoffice.org-core ������ȫж�� OpenOffice.org

rpm -e 'rpm -qa |grep openoffice' 'rpm -qa |grep ooobasis'
yum remove ooobasis3.4*
yum remove openoffice.org3*

��װOpenOffice from source��
��װdmake
��װant
http://kobyla.info/distfiles/ archive zip
http://kobyla.info/distfiles/ zlib


wget http://www.cpan.org/modules/by-module/Compress/Compress-Raw-Zlib-2.040.tar.gz
tar xvzf Compress-Raw-Zlib-2.040.tar.gz
cd Compress-Raw-Zlib-2.040
perl Makefile.PL
make
make install

Archive::Zip
perl Makefile.PL &&
make &&
make test
make install


configure: error: cups/cups.h could not be found. libcupsys2-dev or cups???-devel missing?
tar jxvf cups-1.6.1-source.tar.bz2  

 CFLAGS "-I/usr/local/cups-1.6.1"
	 CPPFLAGS "-I/usr/local/cups-1.6.1"
	 CXXFLAGS "-I/usr/local/cups-1.6.1"
	 DSOFLAGS "-L/usr/local/cups-1.6.1"
	 LDFLAGS "-L/usr/local/cups-1.6.1"
 ./configure --prefix=/usr/local/cups-1.6.1
make
make install

then openoffice configure is :
./configure --with-use-shell=bash --prefix=/usr/local/cups-1.6.1
./configure --disable-odk --disable-binfilter --includedir=/usr/local/cups-1.6.1/include/cups/
./configure --disable-odk --disable-binfilter --disable-cups

checking for gperf... no
checking for gperf... no
configure: error: gperf not found but needed. Install it and/or specify --with-gperf=/path/to/it.

./configure --prefix=/usr &&
make
make install 

then openoffice configure is:
�ȿ���junit-4.jar��/usr/share/java/���棬Ȼ��ִ������ָ���
./configure --disable-odk --disable-binfilter --disable-cups --with-junit=/usr/share/java/junit-4.jar

ִ�гɹ�����/opt/openoffice/source/aoo-3.4.1/main��ִ�У�
./bootstrap 

����
[root@localhost main]# ./bootstrap 
Can't locate Digest/SHA.pm in @INC (@INC contains: /usr/lib64/perl5/site_perl/5.8.8/x86_64-linux-thread-multi /usr/lib64/perl5/site_perl/5.8.7/x86_64-linux-thread-multi /usr/lib64/perl5/site_perl/5.8.6/x86_64-linux-thread-multi /usr/lib64/perl5/site_perl/5.8.5/x86_64-linux-thread-multi /usr/lib/perl5/site_perl/5.8.8 /usr/lib/perl5/site_perl/5.8.7 /usr/lib/perl5/site_perl/5.8.6 /usr/lib/perl5/site_perl/5.8.5 /usr/lib/perl5/site_perl /usr/lib64/perl5/vendor_perl/5.8.8/x86_64-linux-thread-multi /usr/lib64/perl5/vendor_perl/5.8.7/x86_64-linux-thread-multi /usr/lib64/perl5/vendor_perl/5.8.6/x86_64-linux-thread-multi /usr/lib64/perl5/vendor_perl/5.8.5/x86_64-linux-thread-multi /usr/lib/perl5/vendor_perl/5.8.8 /usr/lib/perl5/vendor_perl/5.8.7 /usr/lib/perl5/vendor_perl/5.8.6 /usr/lib/perl5/vendor_perl/5.8.5 /usr/lib/perl5/vendor_perl /usr/lib64/perl5/5.8.8/x86_64-linux-thread-multi /usr/lib/perl5/5.8.8 .) at /opt/openoffice/source/aoo-3.4.1/main/solenv/bin/download_external_dependencies.pl line 66.
BEGIN failed--compilation aborted at /opt/openoffice/source/aoo-3.4.1/main/solenv/bin/download_external_dependencies.pl line 66.

dmake is located in search path

bundling of dictionaries is disabled.

source LinuxX86-64Env.Set.sh

�ڷ�����JVM���������м�����������
-Djava.library.path=��OpenOffice��װ·����/URE/bin

windows:
	��sigar-x86-winnt.dll���롮OpenOffice��װ·����/URE/bin/��
linux:
	��libsigar-x86-linux.so���롮OpenOffice��װ·����/URE/bin/��

ע��linux��OpenOffice��װ·������Ϊ2������ע��˴�ʹ�õ�·����Ҫ����/ure/bin�ļ���



vmware ip����

Ethernet adapter VMware Network Adapter VMnet8:

        Connection-specific DNS Suffix  . :
        IP Address. . . . . . . . . . . . : 192.168.172.1
        Subnet Mask . . . . . . . . . . . : 255.255.255.0
        Default Gateway . . . . . . . . . :
        
��װ�����裺
���غͰ�װVMWare9
http://tieba.baidu.com/p/1954912175  
redhat linux 6.3
http://rhel.ieesee.net/uingei/rhel-server-6.3-i386-dvd.iso    
zlib����
http://zlib.net/zlib-1.2.7.tar.gz
�Ⱥ�װ��libstdc++-devel-4.4.6-4.el6.i686.rpm  gcc-c++-4.4.6-4.el6.i686.rpm ����װ������redhat��iso�ļ��У� 
openoffice
http://nchc.dl.sourceforge.net/project/openofficeorg.mirror/stable/3.4.1/Apache_OpenOffice_incubating_3.4.1_Linux_x86_install-rpm_en-US.tar.gz 
http://nchc.dl.sourceforge.net/project/openofficeorg.mirror/localized/zh-CN/3.4.1/Apache_OpenOffice_incubating_3.4.1_Linux_x86_langpack-rpm_zh-CN.tar.gz
swftools
http://nchc.dl.sourceforge.net/project/giflib/giflib-4.x/giflib-4.1.6/giflib-4.1.6.tar.bz2
http://www.ijg.org/files/jpegsrc.v8d.tar.gz
http://nchc.dl.sourceforge.net/project/freetype/freetype2/2.4.11/freetype-2.4.11.tar.bz2
http://mirror.neu.edu.cn/CTAN/support/xpdf/xpdf-3.03.tar.gz
http://www.swftools.org/swftools-0.9.1.tar.gz
http://mirror.neu.edu.cn/CTAN/support/xpdf/xpdf-chinese-simplified.tar.gz

tomcat
http://labs.mop.com/apache-mirror/tomcat/tomcat-7/v7.0.34/bin/apache-tomcat-7.0.34.tar.gz
jdk
http://download.oracle.com/otn-pub/java/jdk/7/jdk-7-linux-i586.rpm?AuthParam=1357270716_4f2a8c648b324a344e516c82720c5df1

flash player linux
http://fpdownload.macromedia.com/get/flashplayer/pdc/11.2.202.258/flash-plugin-11.2.202.258-release.i386.rpm
bboss-wordpdf���-����
ִ��bboss-wordpdf�����µ�run.batָ�������Ϻ��ڽ�distrib�µ�bboss-office.war������tomcat��webapps�¼��ɣ�ͬʱ�޸�
WEB-INF/bboss-wordpdf.xml�е����ԣ�
f:swftoolWorkDir="/usr/local/bin/" 	
f:officeHome = "/opt/openoffice.org3/"	
swftoolWorkDir�İ�װĿ¼������������pdf2swfָ��
officeHome��װĿ¼������������programĿ¼

ͬʱ��plugin/wordpdf/anjie.doc�����ļ�������/opt/tomcat/wordpdfĿ¼���档
��/opt/tomcat�´���testĿ¼

����tomcat���������������:http://localhost:8080/bboss-office/FlexPaper_2.0.3/index_ooo.html,���ɲ鿴Ч����ͬʱ����Ҫ��linux�°�װflash player

-------------------------------------------------------------------------------------------------------------------------------------------------
1.libreoffice��װ��
���أ�
��װ����
���İ�
��װ��
��ѹ
rpm -ivh *.rpm
rpm -ivh desktop

���wordתpdf������������
�������壺��Windows�µ�����C:\Windows\Fonts�µ������ļ�������/usr/share/fonts/windowsĿ¼��
��Ȩ
cd /usr/share/fonts/windows
chmod 644 -R .
�������建�棺
sudo fc-cache -fv


		
