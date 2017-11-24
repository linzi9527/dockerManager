docker的Java版简单的管理工具
------------------------------------------------------------------
在作为docker远程服务的centos7机器中配置：

1、在/usr/lib/systemd/system/docker.service，配置远程访问。主要是在[Service]这个部分，加上下面两个参数

# vim /usr/lib/systemd/system/docker.service
修改这一行:
ExecStart=/usr/bin/dockerd -H tcp://0.0.0.0:2375 -H unix://var/run/docker.sock

2、docker重新读取配置文件，重新启动docker服务
# systemctl daemon-reload
# systemctl restart docker