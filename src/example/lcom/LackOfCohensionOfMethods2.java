package example.lcom;

/**
 * LCOM1 Lack of Cohesion of Methods
 * 메소드 전체 조합에서 필드 교집합이 없는 개수 = P
 * 메소드 전체 조합에서 필드 교집합이 있는 개수 = Q
 * then LCOM = |P| - |Q|, if |P| > |Q|
 * =0 otherwise
 * 
 * @author doni
 *
 * lcom = |4|-|2| = 2
 */
public class LackOfCohensionOfMethods2 {
	private int a;
	private int b;
	private int c;
	private int d;
	private int e;
	
	
	public LackOfCohensionOfMethods2() {
		a = 1;
	}

	private void method1(){
		a = 2;
		b = 1;
	}
	
	private void metho2(){
		c = 3;
		d = 1;
	}
	
	private void metho3(){
		d = 3;
		e = 1;
	}
	
}
