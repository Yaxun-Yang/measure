package example.wmc;


/**
 * WMC = Weight Methods Per Class
 * number of methods defined in class
 * 클래스에 정의 된 메소드의 수
 * 한 클래스에서의 메소드의 수는 설계 및 모델링 시에 결정될 수 있다. 여기에서 가중치는 메소드의 복잡도가 곱해진 것을 의미한다. 
 * 
 * @author doni
 * 
 * WMC = 가중치(복잡도) 포함시 -> 6
 * WMC = 가중치(복잡도) 불포함시 -> 4
 */
public class WeightMethodsPerClass {

	private String field_1;
	
	public WeightMethodsPerClass() {
	}

	public WeightMethodsPerClass(String field_1) {
		this.field_1 = field_1;
	}
	
	public void doTest(){
		if(field_1 == null){
			print("a");
		}else if("".equalsIgnoreCase(field_1)){
			print("b");
		}
	}

	private void print(String string) {
		
	}
}
