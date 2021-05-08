package example.cbo;

/**
 * CBO = Coupling between Object Classes
 * number of classes to which a class is coupled
 * 클래스가 결합 된 클래스 수
 * Fan In : 자신을 사용하는 모듈의 수 + Fan Out: 자신이 호출하는 모듈의 수
 * @author SDS
 *
 * 동일한 클래스에 대한 다중 액세스는 하나의 액세스로 간주됩니다. 
 * 메서드 호출과 변수 참조 만 계산됩니다. 
 * 상수 사용, API 선언에 대한 호출, 이벤트 처리, 사용자 정의 유형 사용 및 
 * 객체 인스턴스화와 같은 다른 유형의 참조는 무시됩니다. 
 * 메서드 호출이 다형성 인 경우 (오버라이드 또는 오버로드로 인해) 
 * 호출이 들어갈 수있는 모든 클래스가 결합 된 수에 포함됩니다.
 */
public class CouplingBetweenObjectClasses //extends FanOutParent // +1
{
//	FanOutClassNormal dp1 = new FanOutClassNormal();  // +1
	
	public void todo(){
		
//		String str = dp1.getAppendString("a");
//		dp1.print(str);

//		FanOutClassStatic.print("s");  // +1
		
//		String fanoutfield = FanOutClassStaticField.fanoutfield; // +1

//		FanOutClassField focf = new FanOutClassField(); // +1
//		String fanoutfield = focf.fanoutfield;
		
//		FanOutPolymorphism fop = new FanOutPolymorphism(); // +1
//		fop.printPolymorphism();
//		fop.printPolymrphosmParent();
		
	}
}
