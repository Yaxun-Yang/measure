package example.rfc;

/**
 * RFC and RFC´ Response for a Class
 * 클래스의 응답 집합은 클래스의 객체에 대한 메소드 호출의 결과로 
 * invoke되는 모든 메소드의 집합을 의미한다.
 * RFC = M + R
 * M = 클래스의 메소드 수
 * R = 클래스의 메소드에 의해 직접 호출 된 원격 메소드의 수
 * 
 * RFC '= M + R'
 * R '= 전체 호출 트리를 반복적으로 호출하는 원격 메소드 수
 * 
 * @author doni
 *
 */
public class ResponseForAClass {

	public ResponseForAClass() { // +1
		super(); // +1
	}

	public ResponseForAClass(String name) { // +1
		super();
	}
	
	public void todo(){ // +1
		CalleeClass callee = new CalleeClass(); // +1
		callee.printSomthing(); // +1
		callee.printParentSomthing(); // +1
	}
}
