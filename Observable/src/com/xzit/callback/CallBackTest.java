package com.xzit.callback;


interface CallBack{
	public void solve(String result);
}
class Wang implements CallBack{
	private Li li;
	public Wang(Li li){
		this.li = li;
	}
	public void askQuestion(final String question){
		new Thread(new Runnable(){

			public void run() {
				/** 
                 * 小王调用小李中的方法，在这里注册回调接口 
                 * 这就相当于A类调用B的方法C 
                 */  
				li.executeMessage(Wang.this, question);
			}
			
		}).start();	
		  //小网问完问题挂掉电话就去干其他的事情了，诳街去了  
        play();  
	}
	public void play(){
		System.out.println("我要逛街去了");  
	}
	public void solve(String result) {
		System.out.println("小李告诉小王的答案是--->" + result);  
	}
	
}
class Li{
	public void executeMessage(CallBack callback,String question){
		System.out.println("小王问的问题---》"+question);
		//模拟小李办自己的事情需要很长时间
		//异步回调
		for(int i=0;i<10000;i++){
			
		}
		String result = "2";
		callback.solve(result);
	}
}

public class CallBackTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		

	}

}
