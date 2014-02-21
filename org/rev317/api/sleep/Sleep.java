package org.rev317.api.sleep;

import org.parabot.environment.api.utils.Time;

public class Sleep {
	
	public static void sleep(Condition con, int time) {
		long start = System.currentTimeMillis();
		while (con.validate() && System.currentTimeMillis() - start < time)
			Time.sleep(10);
	}

}
