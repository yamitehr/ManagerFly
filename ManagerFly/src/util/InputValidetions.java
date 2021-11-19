package util;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

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
		public static boolean validateLandAferDep(Date dep, Date land) {
			
			return (land.after(dep));
		}
		
		//check that departure date is at least 2 months after the flight create date in the system
		public static boolean validateDepDate(Date dep) {
			
			Date today = new Date();
			if(dep.after(today)) {
				
				Calendar depDate = new GregorianCalendar(dep.getYear(), dep.getMonth(), dep.getDay());
		        Calendar now = new GregorianCalendar();
		        now.setTime(new Date());
		        
		        int yearsInBetween = depDate.get(Calendar.YEAR) 
		                                - now.get(Calendar.YEAR);
		        int monthsDiff = depDate.get(Calendar.MONTH) 
		                                - now.get(Calendar.MONTH);
		        long InMonths = yearsInBetween*12 + monthsDiff - 1;
		        System.out.println(InMonths);
		        if(InMonths >= 2)
		        	return true;

			}
			return false;
		}
			
		public static void main(String args[]) {
			 
			Date d = new Date(2022, 1, 20, 13, 15, 40);
			 
			System.out.println(validateDepDate(d));
		}
		
}
