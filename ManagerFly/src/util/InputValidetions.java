package util;




import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import control.ReportsLogic;


public class InputValidetions {

	/*----------------------------------------- GENERAL VALIDATIONS METHODS --------------------------------------------*/
		/**
		 * validate that a given number is positive
		 * @param num
		 * @return true if positive
		 */
		public static boolean validatePositivesNum(int num) {
			
			return (num > 0) ? true: false;
		} 
		
		/**
		 * check that last is after first for(flight, employee, shift dates...etc)
		 * @param first date
		 * @param last date
		 * @return true if positive
		 */
		public static boolean validateLastAferFirst(LocalDateTime first, LocalDateTime last) {
			
			return (last.isAfter(first));
		}
		
		/**
		 * validate names {city ,country, person ...etc}
		 * @param string
		 * @return true if valid
		 */
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
		
		/**
		 * validate positive Integers or 0 Integers from text fields
		 * @param string
		 * @return true if valid
		 */
		public static boolean validatePositiveIntegerOrZero(String s) {
			
			for(int i = 0; i < s.length(); i++) {
				
				if(s.charAt(i) < '0' || s.charAt(i) > '9') {
					return false;
					}
				}
			return true;
		}
		
		
		
	/*----------------------------------------- AIRPORT VALIDATIONS METHODS --------------------------------------------*/
		
		/**
		 * validate time zone of a airport (in range -12 -> 12 (int))
		 * @param timeZone
		 * @return true if valid
		 */
		public static boolean validateTimeZone(int timeZone) {
			
			if(timeZone > 12 || timeZone < -12)
				return false;
			return true;
		}
	/*----------------------------------------- Plane VALIDATIONS METHODS --------------------------------------------*/
		/**
		 * validate Plane tail num
		 * @param string
		 * @return true if valid
		 */
		public static boolean validateTailNum(String s) {
			if( s.length() > 2 && s.charAt(0) >= 'A' && s.charAt(0) <= 'Z' && s.charAt(1) == '-') {
				String subS = s.substring(2);
				for(int i = 0; i < subS.length(); i++) {
					
					if(subS.charAt(i) < 'A' || subS.charAt(i) > 'Z') {
						if(subS.charAt(i) < 'a' || subS.charAt(i) > 'z') {
							if(subS.charAt(i) < '0' || subS.charAt(i) > '9')
								return false;
						}
					}
				}
				return true;
			}
			return false;
		}
	/*----------------------------------------- FLIGHT VALIDATIONS METHODS --------------------------------------------*/		
		/**
		 * check that the 2 pilots of the flight are not the same 
		 * @param id1 of pilot 1
		 * @param id2 f pilot 2
		 * @return false if are not equal
		 */
		public static boolean validateDiffPilots(String id1, String id2) {
			
			return (id1.equals(id2)) ? false: true;
		}
		
		/**
		 * check that the two air ports of the flight are not the same 
		 * @param departure airport
		 * @param landing airport
		 * @return false if are not the same
		 */
		public static boolean validateDiffAirports(int dep, int land) {
					
			return (dep == land) ? false: true;
		}
		
		/**
		 * check that departure date is at least 2 months after the flight create date in the system
		 * @param departure date
		 * @return true if valid
		 */
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
		
}
