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
                 * С������С���еķ�����������ע��ص��ӿ� 
                 * ����൱��A�����B�ķ���C 
                 */  
				li.executeMessage(Wang.this, question);
			}
			
		}).start();	
		  //С����������ҵ��绰��ȥ�������������ˣ�ڿ��ȥ��  
        play();  
	}
	public void play(){
		System.out.println("��Ҫ���ȥ��");  
	}
	public void solve(String result) {
		System.out.println("С�����С���Ĵ���--->" + result);  
	}
	
}
class Li{
	public void executeMessage(CallBack callback,String question){
		System.out.println("С���ʵ�����---��"+question);
		//ģ��С����Լ���������Ҫ�ܳ�ʱ��
		//�첽�ص�
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
