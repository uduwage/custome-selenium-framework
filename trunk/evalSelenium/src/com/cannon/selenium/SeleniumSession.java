/**
 * 
 */
package com.cannon.selenium;

import java.util.HashMap;

import org.apache.log4j.Logger;

import com.thoughtworks.selenium.SeleniumException;


/**
 * @author a83E1
 *
 */
public class SeleniumSession {

	private static final org.apache.log4j.Logger log = Logger.getLogger(SolventSelenium.class.getName());
	
	private static ThreadLocal<HashMap<String, SolventSelenium>> cache = 
		new ThreadLocal<HashMap<String,SolventSelenium>>() {
		protected synchronized HashMap<String, SolventSelenium> intialValue() {
			return new HashMap<String, SolventSelenium>();
		}
	};
	
	private static ThreadLocal<SolventSelenium> selenium = new ThreadLocal<SolventSelenium>();
	
	/**
	 * Retrieve the current Selenium Session
	 * @return
	 */
	public static SolventSelenium get() {
		return selenium.get();
	}
	
	/**
	 * Set the current Selenium Session
	 * @param instance
	 */
	public static void set(SolventSelenium instance) {
		selenium.set(instance);
	}
	
	/**
	 * Convienence method that ends the current session.
	 */
	public static void end() {
		selenium.get().stop();
	}
	
	/**
	 * Applies a name to the current session and stores it in the session cache of the current thread.
	 * @param name The name to apply to the session that cab be use later to retrieve the session from 
	 * the cache via the restore() method.
	 */
	public static void store(String name) {
		cache.get().put(name, selenium.get());
	}
	
	public static void restore(String name) {
		if(!cache.get().containsKey(name)) {
			throw new SeleniumException("Cannot resotre session named " + name + ". Did you store it in the first place?" +
					" HINT: " + "Use SeleniumSession.store(<name>) to store a session to be retrieved later by using restore().");
		} else {
			log.trace("Restoring session " + name);
			selenium.set(cache.get().get(name));
		}
	}
	
	
}
