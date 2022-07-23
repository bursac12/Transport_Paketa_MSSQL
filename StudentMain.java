package student;



import operations.*;
import tests.TestHandler;
import tests.TestRunner;


public class StudentMain {


	
	
    public static void main(String[] args) {
        CityOperations cityOperations =new BV090555_CityOperations(); 
        DistrictOperations districtOperations =new BV090555_DistrictOperations();
        CourierOperations courierOperations = new BV090555_CourierOperations(); 
        CourierRequestOperation courierRequestOperation = new BV090555_CourierRequestOperation();
        GeneralOperations generalOperations = new BV090555_GeneralOperations();
        UserOperations userOperations = new BV090555_UserOperations();
        VehicleOperations vehicleOperations = new BV090555_VehicleOperations();
        PackageOperations packageOperations = new BV090555_PackageOperations();

        TestHandler.createInstance(
                cityOperations,
                courierOperations,
                courierRequestOperation,
                districtOperations,
                generalOperations,
                userOperations,
                vehicleOperations,
                packageOperations);

        TestRunner.runTests();

		   

   
   }
    

	
}
