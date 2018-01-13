#nati
基于netty4和thrift 0.10.0的框架

开发背景 官方的netty+thrift框架是基于netty3、thrift0.9的框架(官方版: https://github.com/facebook/nifty)，一直没有升级到netty4、thrift0.10，因此自己动手做一个升级版，初期想参考nifty源码来实现，读了一下nifty的源码后发现，nifty在处理数据流时，是基于thrift序列化协议实现了相应的handler，需要对thrift序列化协议非常熟悉，知道每一个位置都是哪些字节，如何解析，因此想实现一个简单版本的，核心类TNatiWorker参考了Thrift的TServer的实现，代码比较简单