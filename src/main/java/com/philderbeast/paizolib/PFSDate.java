/**
* @Author: Ben Jordan
* @Author: Phillip Ledger <Philderbeast>
* @Date:   2016-04-06T19:54:42+10:00
* @Email:  phillip@philderbeast.com
* @Last modified by:   Philderbeast
* @Last modified time: 2016-08-14T08:17:13+10:00
*/
package com.philderbeast.paizolib;

class PFSDate {
	protected String year;
	protected String month;
	protected String day;

	PFSDate(String inDate){
		if (inDate.contains(",")) {
			String workingDate = inDate.replace(',',' ');
			String[] sepDate = workingDate.split("\\s+");

			month = sepDate[0];
			day = sepDate[1];
			year = sepDate[2];
		} else if (inDate.contains("/")) {
			String[] sepDate = inDate.split("\\s");
			sepDate = sepDate[0].split("/");
			day = sepDate[0];

			switch (Integer.parseInt(sepDate[1])){
				case 1:
					month = "Jan";
					break;
				case 2:
					month = "Feb";
					break;
				case 3:
					month = "Mar";
					break;
				case 4:
					month = "Apr";
					break;
				case 5:
					month = "May";
					break;
				case 6:
					month = "Jun";
					break;
				case 7:
					month = "Jul";
					break;
				case 8:
					month = "Aug";
					break;
				case 9:
					month = "Sep";
					break;
				case 10:
					month = "Oct";
					break;
				case 11:
					month = "Nov";
					break;
				case 12:
					month = "Dec";
					break;
			}

			year = sepDate[2];
		}
	}

	public static int getMonthLength(int month){
		int monthLength = 0;

		switch (month) {
			case 0:
				monthLength = 31;
				break;
			case 1:
				monthLength = 28;
				break;
			case 2:
				monthLength = 31;
				break;
			case 3:
				monthLength = 30;
				break;
			case 4:
				monthLength = 31;
				break;
			case 5:
				monthLength = 30;
				break;
			case 6:
				monthLength = 31;
				break;
			case 7:
				monthLength = 31;
				break;
			case 8:
				monthLength = 30;
				break;
			case 9:
				monthLength = 31;
				break;
			case 10:
				monthLength = 30;
				break;
			case 11:
				monthLength = 31;
				break;
		}

		return monthLength;
	}

	/**compareTo(PFSDate compDate
	*This method compares the PFSDate object to a supplied PFSDate object.
	*It will return a positive number if the supplied PFSDate is earlier than this
	*PFSDate object.
	*It will return a negative number if the supplied PFSDate is later than this
	*PFSDate object.
	*It will return zero (0) if the supplied PFSDate is the same as this
	*PFSDate object.
	* This method takes an input of PFSDate, converts it to a string and then
	*splits out the year, month and day values. Once this is done it compares the
	*year values.
	*/
	public int compareTo(PFSDate compDate){
		int rtnInt = 0;
		int monthInt = 0;
		String compStr = compDate.toString();
		String workingDate = compStr.replace(',',' ');
		String[] sepDate = workingDate.split("\\s+");

		if ((Integer.parseInt(year)- Integer.parseInt(sepDate[2])) == 0) {
			if (month.equals(sepDate[0])) {
				rtnInt = Integer.parseInt(day) - Integer.parseInt(sepDate[1]);
			} else {
				switch(month){
					case "Jan":
						monthInt = 1;
						break;
					case "Feb":
						monthInt = 2;
						break;
					case "Mar":
						monthInt = 3;
						break;
					case "Apr":
						monthInt = 4;
						break;
					case "May":
						monthInt = 5;
						break;
					case "Jun":
						monthInt = 6;
						break;
					case "Jul":
						monthInt = 7;
						break;
					case "Aug":
						monthInt = 8;
						break;
					case "Sep":
						monthInt = 9;
						break;
					case "Oct":
						monthInt = 10;
						break;
					case "Nov":
						monthInt = 11;
						break;
					case "Dec":
						monthInt = 12;
						break;
				}

				switch(sepDate[0]){
					case "Jan":
						rtnInt = monthInt - 1;
						break;
					case "Feb":
						rtnInt = monthInt - 2;
						break;
					case "Mar":
						rtnInt = monthInt - 3;
						break;
					case "Apr":
						rtnInt = monthInt - 4;
						break;
					case "May":
						rtnInt = monthInt - 5;
						break;
					case "Jun":
						rtnInt = monthInt - 6;
						break;
					case "Jul":
						rtnInt = monthInt - 7;
						break;
					case "Aug":
						rtnInt = monthInt - 8;
						break;
					case "Sep":
						rtnInt = monthInt - 9;
						break;
					case "Oct":
						rtnInt = monthInt - 10;
						break;
					case "Nov":
						rtnInt = monthInt - 11;
						break;
					case "Dec":
						rtnInt = monthInt - 12;
						break;
				}
			}
		} else {
			rtnInt = Integer.parseInt(year) - Integer.parseInt(sepDate[2]);
		}

		return rtnInt;

	}

	public boolean sameMonth(PFSDate compDate){
		String compStr = compDate.toString();
		String workingDate = compStr.replace(',',' ');
		String[] sepDate = workingDate.split("\\s+");

		return (month.equals(sepDate[0]) && year.equals(sepDate[2]));
	}

	public String toString(){
		return month + " " + day + ", " + year;
	}
}
