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


