package util;

import com.hp.ov.activator.resmgr.ExecutionDescriptor;
import com.hp.ov.activator.resmgr.par.PARPlugin;
import com.hp.ov.activator.resmgr.par.PluginException;

public class TimeoutPlugin extends PARPlugin {
	
	/**
 * 	 * 
 * 	 	 * @param op	
 * 	 	 	 * @param timeout - in milliseconds
 * 	 	 	 	 * @return
 * 	 	 	 	 	 * @throws PluginException
 * 	 	 	 	 	 	 */
	public ExecutionDescriptor task_doTimeout(int op, String timeout) throws PluginException {
		switch (op) {
		case DO_AND_CHECK:
			try {
				Thread.sleep(Long.parseLong(timeout));
				context.uploadData("Result", "Success");
				return getSuccessDescriptor();
			} catch (NumberFormatException e) {
				e.printStackTrace();
				context.uploadData("Result", "NumberFormatException: " + e.getMessage());
				return getErrorDescriptor("NumberFormatException: " + e.getMessage());
			} catch (InterruptedException e) {
				e.printStackTrace();
				context.uploadData("Result", "InterruptedException: " + e.getMessage());
				return getErrorDescriptor("InterruptedException: " + e.getMessage());
			}
		case UNDO_AND_CHECK:
			return getSuccessDescriptor();
		default:
			throw new PluginException("Operation not supported");
		}
	}
	
	/**
 * 	 * Returns <b>ExecutionDescriptor</b> with ERROR major and NONE minor status.
 * 	 	 *  
 * 	 	 	 * @param descr error description
 * 	 	 	 	 * @return "Error" <b>ExecutionDescriptor</b>
 * 	 	 	 	 	 */
	private ExecutionDescriptor getErrorDescriptor(String descr) {
		return new ExecutionDescriptor(
				ExecutionDescriptor.ERROR,
				ExecutionDescriptor.NONE,
				"", "", descr);
	}

	/**
 * 	 * Returns <b>ExecutionDescriptor</b> with "OK" major and "NONE" minor status.
 * 	 	 *  
 * 	 	 	 * @return "Success" <b>ExecutionDescriptor</b>
 * 	 	 	 	 */
	private ExecutionDescriptor getSuccessDescriptor() {
		return new ExecutionDescriptor(
				ExecutionDescriptor.OK,
				ExecutionDescriptor.NONE, 
				"", "", "Success");
	}
}

