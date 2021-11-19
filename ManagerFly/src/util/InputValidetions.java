package util;




import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Month;


public class InputValidetions {

	/*----------------------------------------- GENERAL VALIDATIONS METHODS --------------------------------------------*/
		//validate positive numbers
		public static boolean validatePositivesNum(int num) {
			
			return (num > 0) ? true: false;
		} 
		
	/*----------------------------------------- AIRPORT VALIDATIONS METHODS --------------------------------------------*/
		//validate names {city ,country, person ...etc}
		public static boolean validateName(String s) {
			
			for(int i = 0; i < s.length(); i++) {
				
				if(s.charAt(i) < 'A' || s.charAt(i) > 'Z') {
					if(s.charAt(i) < 'a' || s.charAt(i) > 'z') {
						if(s.charAt(i) != ' ')
							return false;
					}
				}
			}
			return true;
		}
		
		//validate time zone of a airport (in range -12 -> 12 (int))
		public static boolean validateTimeZone(int timeZone) {
			
			if(timeZone > 12 || timeZone < -12)
				return false;
			return true;
		}
	/*----------------------------------------- FLIGHT VALIDATIONS METHODS --------------------------------------------*/		
		//check that the to pilots of the flight are not the same 
		public static boolean validateDiffPilots(String id1, String id2) {
			
			return (id1.equals(id2)) ? false: true;
		}
		
		//check that the two air ports of the flight are not the same 
		public static boolean validateDiffAirports(int dep, int land) {
					
			return (dep == land) ? false: true;
		}
		//check that landing time is after departure time
		public static boolean validateLandAferDep(LocalDateTime dep, LocalDateTime land) {
			
			return (land.isAfter(dep));
		}
		
		//check that departure date is at least 2 months after the flight create date in the system
		public static boolean validateDepDate(LocalDateTime dep){
			
			LocalDateTime today = LocalDateTime.now();
			if(dep.isAfter(today)) {
				long daysBetween = Duration.between(today, dep).toDays();
	            if(daysBetween >= 60) {        	
	            	return true;
	            }
			}
			
			return false;
		}
		
		// main method for testing
		public static void main(String args[]){
			 
			LocalDateTime d = LocalDateTime.of(2022, 
                    Month.JANUARY, 25, 19, 30, 40);
			
			System.out.println(validateDepDate(d));
		}
		
}
