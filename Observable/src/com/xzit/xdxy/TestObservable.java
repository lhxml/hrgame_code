package com.xzit.xdxy;

import java.util.ArrayList;
import java.util.List;

abstract class Subject{
	private List<Observer> list = new ArrayList<Observer>();
	public void attach(Observer observer){
		list.add(observer);
	}
	public void detach(Observer observer){
		list.remove(observer);
	}
	public void notifyObservers(String state){
		for(Observer observer:list){
			observer.update(state);
		}
	}
}
class ConcreteSubject extends Subject{
	private String state;
	public String getState(){
		return state;
	}
	public void change(String state){
		System.out.println("Subject=="+state);
		this.notifyObservers(state);
	}
}
interface Observer{
	public void update(String state);
}
class ConcreteObserver implements Observer{
	private String state;
	public void update(String state) {
		this.state = state;
		System.out.println("Observer=="+this.state);
	}
	
}
public class TestObservable {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ConcreteSubject sub = new ConcreteSubject();
		ConcreteObserver observer1 = new ConcreteObserver();
		ConcreteObserver observer2 = new ConcreteObserver();
		sub.attach(observer1);
		sub.attach(observer2);
		sub.change("¿Æ±È");
		
	}

}
