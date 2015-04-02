package SynonymGrouping;

/**
 * Created by keleigong on 3/20/15.
 */
class DBAccessor {
	public void execute() {
		System.out.println("Executing query");
	}
}

class DBAccessorAdapter {
	public  DBAccessor helper;

	public void execute() {
		System.out.println("Using Adapter 1");
		helper.execute();
	}
}

class NewDBAccessorAdapter {
	public  DBAccessorAdapter helper;

	public void execute() {
		System.out.println("Using Adapter 2");
		helper.execute();
	}
}

public class DBClient {
	public static void main(String[] args) {
		DBAccessor myObj1 = new DBAccessor();
		DBAccessorAdapter myObj2 = new DBAccessorAdapter();
		NewDBAccessorAdapter myObj3 = new   NewDBAccessorAdapter();
		myObj3.helper = myObj2  ;
		myObj2.helper = myObj1  ;
		myObj2.execute();
		myObj3.execute();
	}
}