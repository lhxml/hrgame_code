package com.xzit.xdxy;

interface OnClickListener{
	public void onclick(View view);
}
class Activity implements OnClickListener{
	private View btn;
	public Activity(View btn){
		btn.setOnClickListener(this);
	}
	public void onclick(View view) {
		System.out.println("hahaha");
	}
	
}
 class View{
	 OnClickListener listener;
	 public void setOnClickListener(OnClickListener l){
		 this.listener = l;
		 performOnClick();
	 }
	 public void performOnClick(){
		 listener.onclick(this);
	 }
	 
 }
public class TestCallBack {

	
	public static void main(String[] args) {
		View view = new View();
		Activity act = new Activity(view);
		
		

	}

}
