1.linux�°�װopenffice
�ο��ĵ�
http://wiki.openoffice.org/w/images/7/7e/Installation_Guide_OOo3.pdf
Ĭ�ϰ�װĿ¼��
/opt/openoffice.org3
2.linux�°�װswftools
����swftools 0.9.1�汾
http://wiki.swftools.org/wiki/Main_Page#Installation_guides
http://wiki.swftools.org/wiki/Installation
�ڰ�װswftools 0.9.2ʱ����ת����װswftools 0.9.1
Ĭ�ϰ�װĿ¼/usr/local/bin
/usr/local/share/swftools

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


