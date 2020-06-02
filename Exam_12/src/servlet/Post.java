package servlet;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Post {
	String name;
	String gender;
	String birthday ;
	String number;
	
	String date;
	String destination;
	String filename ;
	String trip;
	
	
	public Post(){
		
	}
	public Post(String name, String gender, String birthday,String number, String date, String destination,String filename, String trip){
		this.name=name;
		this.gender=gender;
		this.birthday=birthday;
		this.number=number;
		this.date=date;
		this.destination=destination;
		this.filename=filename;
		this.trip=trip;
	}
}
