package student;

import java.math.BigDecimal;

import operations.PackageOperations;

public class BV090555_PackageOperationsPair implements PackageOperations.Pair<Integer, BigDecimal> {
private  Integer a;
private  BigDecimal b;
	
	
	public BV090555_PackageOperationsPair(Integer a, BigDecimal b){
		
		this.a=a;
		this.b=b;
	}

	@Override
	public Integer getFirstParam() {
		
		return  a;
	}

	@Override
	public BigDecimal getSecondParam() {
	
		return  b;
	}
	


}
