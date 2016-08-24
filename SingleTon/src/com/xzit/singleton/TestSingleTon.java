package com.xzit.singleton;

class LazySingleTon{
	private LazySingleTon(){
		
	}
	private static LazySingleTon single = null;
	public static LazySingleTon getSingleTon(){
		if(single == null){
			synchronized(LazySingleTon.class){
				if(single == null){
					single = new LazySingleTon();
				}
			}
		}
		return single;
	}
	
}
//1、如果不执行修改对象的操作的情况下，单单执行一个读取操作，还有没有进行同步的必要？
//2、保证单例的线程安全使用synchronized会产生什么样的问题？
//3、不使用synchronized，有什么方式来保证线程安全？
//4、假如下次再面试遇到这种情形，用什么方式回答会使面试官感到比较满意？
//但很多时候我们通常会认为锁定整个方法的是比较耗费资源的，代码中实际会产生多线程访问问题的只有 instance = new Singleton(); 这一句，
//为了降低 synchronized 块性能方面的影响，只锁定instance = new Singleton()

//class LazySingleTon{
//	private LazySingleTon(){
//		
//	}
//	private static LazySingleTon single = null;
//	public static synchronized LazySingleTon getSingleTon(){
//		if(single == null){
//			single = new LazySingleTon();
//		}
//		return single;
//	}
//	
//}

//class Singleton{   
//    private static Singleton instance=null;   
//    private Singleton(){}   
//    public static Singleton getInstance(){   
//        if(instance==null){   
//            synchronized(Singleton.class){   
//                instance=new Singleton();   
//            }   
//        }   
//        return instance;   
//    }   
//}   
//这样就产生了二次检查，但是二次检查自身会存在比较隐蔽的问题，查了Peter Haggar
//在DeveloperWorks上的一篇文章，对二次检查的解释非常的详细：“双重检查锁定背后
//的理论是完美的。不幸地是，现实完全不同。双重检查锁定的问题是：并不能保证它会在单
//处理器或多处理器计算机上顺利运行。双重检查锁定失败的问题并不归咎于 JVM 中的实现
//bug，而是归咎于 Java 平台内存模型。内存模型允许所谓的“无序写入”，这也是这些习
//语失败的一个主要原因。”
//public static Singleton getInstance(){     
//    if(instance == null){     
//        synchronize{     
//           if(instance == null){     
//              instance =  new Singleton();      
//           }     
//        }     
//    }     
//    return instance;  
//}

//class Singleton {    
//    private static class LazyHolder {    
//       private static final Singleton INSTANCE = new Singleton();    
//    }    
//    private Singleton (){}    
//    public static final Singleton getInstance() {    
//       return LazyHolder.INSTANCE;    
//    }    
//}
class SingleTon{
	private SingleTon(){
		
	}
	private static final SingleTon single = new SingleTon();
	public static SingleTon getInstance(){
		return single;
	}
}
public class TestSingleTon {
	

}
