package com.shiji.listen;

//事件对象
public class Event{
	
  private Person person;
  
  public Event(Person person) {
      super();
      this.person = person;
  }

  public Event() {
      super();
  }

  public Person getPerson() {
      return person;
  }

  public void setPerson(Person person) {
      this.person = person;
  }
  
}
