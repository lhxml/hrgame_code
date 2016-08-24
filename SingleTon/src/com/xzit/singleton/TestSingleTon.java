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
//1�������ִ���޸Ķ���Ĳ���������£�����ִ��һ����ȡ����������û�н���ͬ���ı�Ҫ��
//2����֤�������̰߳�ȫʹ��synchronized�����ʲô�������⣿
//3����ʹ��synchronized����ʲô��ʽ����֤�̰߳�ȫ��
//4�������´������������������Σ���ʲô��ʽ�ش��ʹ���Թٸе��Ƚ����⣿
//���ܶ�ʱ������ͨ������Ϊ���������������ǱȽϺķ���Դ�ģ�������ʵ�ʻ�������̷߳��������ֻ�� instance = new Singleton(); ��һ�䣬
//Ϊ�˽��� synchronized �����ܷ����Ӱ�죬ֻ����instance = new Singleton()

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
//�����Ͳ����˶��μ�飬���Ƕ��μ���������ڱȽ����ε����⣬����Peter Haggar
//��DeveloperWorks�ϵ�һƪ���£��Զ��μ��Ľ��ͷǳ�����ϸ����˫�ؼ����������
//�������������ġ����ҵ��ǣ���ʵ��ȫ��ͬ��˫�ؼ�������������ǣ������ܱ�֤�����ڵ�
//��������ദ�����������˳�����С�˫�ؼ������ʧ�ܵ����Ⲣ������� JVM �е�ʵ��
//bug�����ǹ���� Java ƽ̨�ڴ�ģ�͡��ڴ�ģ��������ν�ġ�����д�롱����Ҳ����Щϰ
//��ʧ�ܵ�һ����Ҫԭ�򡣡�
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
