package example.cbo;

public class FanInClassDependency {
	
	public void todo(){
		CouplingBetweenObjectClasses c = new CouplingBetweenObjectClasses();
		c.todo();
	}
}
