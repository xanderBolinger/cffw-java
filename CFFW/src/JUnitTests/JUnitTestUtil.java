package JUnitTests;

public class JUnitTestUtil {
	public static boolean isJUnitTest() {
		for (StackTraceElement element : Thread.currentThread().getStackTrace()) {
			if (element.getClassName().startsWith("org.junit.")) {
				return true;
			}
		}
		return false;
	}
}
